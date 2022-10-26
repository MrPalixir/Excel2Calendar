package Calendar;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Other.Date;

public class Excel {
	/**
	 * <b> Excel est la classe qui récupère les informations d'un évènement</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * Le fichier Excel
	 */
	private String fichier;
	/**
	 * Le professeur sélectionné
	 */
	private String selectionner;
	
	/**
	 * Un tableau contenant les heures de début et de fin de chaque tranche horaire
	 */
	private List<Date[]> heureDF;
	/**
	 * La liste des professeurs
	 */
	private List<String> professeurs;
	/**
	 * La liste des évènements de l'emploi du temps Excel
	 */
	private List<Evenement> event;
	/**
	 * L'instance du fichier Excel pour la librairie Apache Poi
	 */
	private XSSFWorkbook workBook;
	
	/**
	 * L'année de l'emploi du temps Excel (celle dans le nom du fichier)
	 */
	private int annee;
	
	/**
	 * Initialise la lecture d'un fichier Excel
	 * @param fichier Le fichier comportant l'emploi du temps Excel
	 * @param selectionner Le professeur sélectionné par l'utilisateur
	 * @Précondition Le fichier Excel en entrée doit être valide
	 */
	public Excel(String fichier, String selectionner)
	{	
		if(fichier == null || selectionner == null)
		{
			JOptionPane.showMessageDialog(null, "Le fichier saisie est invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		this.fichier = fichier;
		this.selectionner = selectionner;
		
		this.event = new LinkedList<Evenement>();
		this.heureDF = new ArrayList<Date[]>();
		this.professeurs = new ArrayList<String>();
		this.annee = -1;
		
		// On ajoute éléments car il y a 6 tranches d'horaire dans une journée
		for(int i = 0; i < 6; i++)
			this.heureDF.add(new Date[2]);
 
		try {
			// On ouvre le fichier contenant le planning puis on initialise l'ouverture des feuilles du fichier Excel
			InputStream inp = new FileInputStream(this.fichier);
			this.workBook = new XSSFWorkbook(inp);
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Le fichier Excel n'a pas été trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
			this.workBook = null;
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problème lors du chargement du fichier Excel", "Erreur", JOptionPane.ERROR_MESSAGE);
			this.workBook = null;
			return;
		}
		
		// On récupère les heures de début et de fin pour chaque tranche d'horaire ainsi que les professeurs et l'année
		recupererAnnee();
		recupererHeureDebut();
		recupererHeureFin();
		recupererProfesseur();
	}
	
	/**
	 * @return la liste des évènements de l'emploi du temps Excel
	 */
	public List<Evenement> getEvenement()
	{
		return this.event;
	}
	
	/**
	 * @return La liste des professeurs de l'emploi du temps Excel
	 */
	public List<String> getProfesseurs() 
	{
		return this.professeurs;
	}
	
	/**
	 * @return L'année se trouvant dans le nom du fichier Excel
	 */
	public int getAnnee()
	{
		return this.annee;
	}
	
	/**
	 * Récupère l'année du calendrier
	 */
	private void recupererAnnee()
	{
		int indexSlash = this.fichier.lastIndexOf("/");
		
		int index = this.fichier.substring(indexSlash).indexOf("-") + 1;
		
		String str = this.fichier.substring(indexSlash + index , indexSlash + index + 4);
		
		try {
			this.annee = Integer.parseInt(str);
		} 
		catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null, "Le nom du fichier est invalide\n\nVoici des noms de fichier valide :\n"
					+ "planning-2018.xlsx\ncalendrier-2018.xlsx", "Erreur", JOptionPane.ERROR_MESSAGE);
			this.workBook = null;
		}
	}
	
	/**
	 * Récupère la liste des professeurs d'un emploi du temps
	 * @Précondition : Le fichier Excel en entrée doit être un fichier valide
	 */
	private void recupererProfesseur()
	{	
		if(this.workBook == null)
			return;
		
		for(Row r : this.workBook.getSheetAt(1))
		{
			if(r.getRowNum() < 36)
				continue;
			
			for(Cell c : r)
			{
				c.setCellType(CellType.STRING);
				// On ne lit pas les cellules vides
				if((String)c.getStringCellValue() == "")
					continue;
				
				if(c.getColumnIndex() != 2)
					continue;
				
				String s = "";
				
				for(int i = 0; i < c.getStringCellValue().length(); i++)
				{
					if(c.getStringCellValue().charAt(i) == ',')
					{	
						i++;
						
						s = "";
						continue;
					}
						
					s += c.getStringCellValue().charAt(i);
					
					if((i == c.getStringCellValue().length() - 1) || (c.getStringCellValue().charAt(i+1) == ','))
						if(!this.professeurs.contains(s))
							this.professeurs.add(s);
				}
			}
		}
	}
	
	/**
	 * Récupère les heures de début pour chaque tranche d'horaire
	 * @Précondition : Le fichier Excel en entrée doit être un fichier valide
	 */
	private void recupererHeureDebut()
	{
		if(this.workBook == null)
			return;
		
		// On ne lit que les heures de début de la première feuille car elles sont identiques pour toutes les feuilles
		Row r = this.workBook.getSheetAt(1).getRow(2);
		
		// On parcourt toutes les tranches d'horaires
		for(Cell c : r)			
		{	
			String s = "", hours = "", mins = "";
			int heures = 0, minutes = 0;
			
			c.setCellType(CellType.STRING);
			// On ne lit pas les cellules vides
			if((String)c.getStringCellValue() == "")
				continue;
			
			// On extrait l'heure de début de la valeur de la cellule
			int finHeure = c.toString().indexOf(" ");
			
			for(int i = 0; i < c.toString().length(); i++)
			{
				if(i >= finHeure)
					break;
				
				s += c.toString().charAt(i);
			}
			
			// On extrait les heures et les minutes
			int separateur = s.toString().indexOf("h");
			
			for(int j = 0; j < s.length(); j++)
			{				
				if(j < separateur)
					hours += s.charAt(j);

				if(j > separateur)
					mins += s.charAt(j);
			}
			
			// On vérifie si les heures/minutes ne sont pas vides puis on transforme la chaîne de caractère en entier
			if(hours != "")
				heures = Integer.parseInt(hours);
			
			if(mins != "")
				minutes = Integer.parseInt(mins);
			
			// On ajoute l'heure de début à notre liste
			this.heureDF.get(c.getColumnIndex()-1)[0] = new Date(heures, minutes);
		}
	}
	
	/**
	 * Récupère les heures de fin pour chaque tranche d'horaire
	 * @Précondition : Le fichier Excel en entrée doit être un fichier valide
	 */
	private void recupererHeureFin()
	{
		if(this.workBook == null)
			return;
		
		// On ne lit que les heures de début de la première feuille car elles sont identiques pour toutes les feuilles
		Row r = this.workBook.getSheetAt(1).getRow(2);
		
		// On parcourt toutes les tranches d'horaires
		for(Cell c : r)			
		{	
			String s = "", hours = "", mins = "";
			int heures = 0, minutes = 0;
			
			c.setCellType(CellType.STRING);
			// On ne lit pas les cellules vides
			if((String)c.getStringCellValue() == "")
				continue;
			
			// On extrait l'heure de début de la valeur de la cellule
			int finHeure = c.toString().indexOf("-");
			
			for(int i = 0; i < c.toString().length(); i++)
			{
				if(i <= finHeure+1)
					continue;
				
				s += c.toString().charAt(i);
			}

			// On extrait les heures et les minutes
			int separateur = s.toString().indexOf("h");
			
			for(int j = 0; j < s.length(); j++)
			{				
				if(j < separateur)
					hours += s.charAt(j);

				if(j > separateur)
					mins += s.charAt(j);
			}
			
			// On vérifie si les heures/minutes ne sont pas vides puis on transforme la chaîne de caractère en entier
			if(hours != "")
				heures = Integer.parseInt(hours);
			
			if(mins != "")
				minutes = Integer.parseInt(mins);
			
			// On ajoute l'heure de fin à notre liste
			this.heureDF.get(c.getColumnIndex()-1)[1] = new Date(heures, minutes);
		}
	}
	
	/**
	 * Récupère le jour correspondant à une ligne du fichier Excel
	 * @param row La ligne pour laquelle on souhaite récupérer le jour
	 * @return Le jour de la semaine correspondant
	 */
	private int getDay(int row)
	{
		if(row > 2 && row < 9)
			return Calendar.MONDAY;
		
		if(row >= 9 && row < 15)
			return Calendar.TUESDAY;
		
		if(row >= 15 && row < 21)
			return Calendar.WEDNESDAY;
		
		if(row >= 21 && row < 27)
			return Calendar.THURSDAY;
		
		if(row >= 27 && row < 33)
			return Calendar.FRIDAY;
		
		return -1;
		
	}

	/**
	 * Récupère l'intégralité des évènements contenus dans l'emploi du temps Excel
	 * @Précondition Le fichier Excel en entrée doit être un fichier valide
	 */
	public boolean recupererEvenement()
	{
		if(this.workBook == null)
			return false;
		
		// On parcourt l'intégralité des feuilles de notre fichier Excel
		for(Sheet sheet : this.workBook)
		{	
			//La première feuille correspond à la feuille template
			if(sheet == this.workBook.getSheetAt(0))
				continue;
			
			// On lit la date de la semaine
			Row r = sheet.getRow(0);
			Cell c = r.getCell(3);
			
			String premierJourSemaine = "", mois = "";
			
			// On récupère le premier jour de la semaine et le mois
			int chaineJour = c.getStringCellValue().lastIndexOf(" ");
			int chaineMois = c.getStringCellValue().indexOf("/");
			
			for(int i = 0; i < c.getStringCellValue().length(); i++)
			{
				if(i > chaineJour && i < chaineMois)
					premierJourSemaine += c.getStringCellValue().charAt(i);
				
				if(i > chaineMois)
					mois += c.getStringCellValue().charAt(i);
			}
			
			// On parcourt l'intégralité des lignes de notre feuille
			for(Row row : sheet)
			{				
				// Certaines annotations se situent après la lignes 34, elles ne nous intérèssent pas lors du traitement
				if(row.getRowNum() > 34)
					break;
				
				// Les 2 premières lignes ne nous intérèssent pas
				if(row.getRowNum() <= 2)
					continue;
			
				Evenement evenement = null;
				
				// On parcourt l'intégralité des cellules de chaques lignes
				for(Cell cell : row)
				{
					cell.setCellType(CellType.STRING);
					
					// On ne lit ni la cellule contenant le jour ni les cellules vides
					if((cell.getColumnIndex() == 0) || (cell.getStringCellValue() == ""))
						continue;
					
					String cellValue = cell.getStringCellValue();
					
					String groupe = "", module = "", professeur = "";
					
					// On découpe la chaine contenant un évènement en plusieurs morceaux
					int premiereChaine = cellValue.indexOf(" ");
					int deuxiemeChaine = cellValue.indexOf(" ", premiereChaine + 1);
					int troisiemeChaine = cellValue.indexOf(" ", deuxiemeChaine + 1);
					int quatriemeChaine = cellValue.lastIndexOf(" ");
					
					// On récupère le groupe, le module et le professeur concerné par l'évènement
					for(int i = 0; i < cellValue.length(); i++)
					{
						if(i < premiereChaine)
							groupe += cellValue.charAt(i);
						
						if(i > deuxiemeChaine && i < troisiemeChaine)
							module += cellValue.charAt(i);
						
						if(i > quatriemeChaine)
							professeur += cellValue.charAt(i);
					}
					
					String prof[] = professeur.split("/");
					
					if(prof.length == 2)
					{
						if((!prof[1].equals(selectionner) && !prof[0].equals(selectionner) && !selectionner.equals("Tous les professeurs")))
							continue;
					}
					else
					{
						if((!selectionner.equals(professeur) && !selectionner.equals("Tous les professeurs")))
							continue;
					}
					
					// On ne récupère que les évènements concernant certains professeurs
					
					// On récupère les heures de début et de fin d'un enseignement
					Calendar heuresDebut = Calendar.getInstance();
					if(Integer.parseInt(mois) - 1 < 8)
						heuresDebut.set(Calendar.YEAR, this.annee+1);
					else
						heuresDebut.set(Calendar.YEAR, this.annee);
					
					heuresDebut.set(Calendar.HOUR_OF_DAY, this.heureDF.get(cell.getColumnIndex()-1)[0].getHeure());
					heuresDebut.set(Calendar.MINUTE, this.heureDF.get(cell.getColumnIndex()-1)[0].getMinute());
					heuresDebut.set(Calendar.MONTH, Integer.parseInt(mois) - 1);
					heuresDebut.set(Calendar.DAY_OF_WEEK, getDay(row.getRowNum()));
					heuresDebut.set(Calendar.DAY_OF_MONTH, Integer.parseInt(premierJourSemaine) + getDay(row.getRowNum()) - 2); // On enlève 2 car le Lundi est le jour n°2
					
					// On récupère l'année et si le mois est avant septembre alors on est forcément l'année d'après
					Calendar heuresFin = Calendar.getInstance();
					if(Integer.parseInt(mois) - 1 < 8)
						heuresFin.set(Calendar.YEAR, this.annee+1);
					else
						heuresFin.set(Calendar.YEAR, this.annee);
					
					heuresFin.set(Calendar.HOUR_OF_DAY, this.heureDF.get(cell.getColumnIndex()-1)[1].getHeure());
					heuresFin.set(Calendar.MINUTE, this.heureDF.get(cell.getColumnIndex()-1)[1].getMinute());	
					heuresFin.set(Calendar.MONTH, Integer.parseInt(mois) - 1);
					heuresFin.set(Calendar.DAY_OF_WEEK, getDay(row.getRowNum()));
					heuresFin.set(Calendar.DAY_OF_MONTH, Integer.parseInt(premierJourSemaine) + getDay(row.getRowNum()) - 2); 					// On enlève 2 car le Lundi est le jour n°2
					
					// On crée un évènement qui correspond à un enseignement
					evenement = new Evenement(heuresDebut, heuresFin, module, groupe, professeur);
					
					// On ajoute l'évènement à notre liste d'évènement
					if(this.event.contains(evenement))
						continue;
					
					this.event.add(evenement);
				}	
			}
		}
		
		return true;
	}
}
