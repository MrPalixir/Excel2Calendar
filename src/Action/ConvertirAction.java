package Action;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import Calendar.Excel;
import Calendar.ICalendar;
import Fenetre.FenetreE2C;

@SuppressWarnings("serial")
public class ConvertirAction extends AbstractAction{
	/**
	 * <b> ConvertirAction est la classe qui se déclenche lors que l'utilisateur clique sur le bouton Convertir</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * La fenêtre principale
	 */
	private FenetreE2C fenetre;
	
	/**
	 * Le constructeur de la classe ConvertirAction
	 * @param f La fenêtre principale
	 * @param text Le texte se trouvant sur le bouton convertir ici "Convertir"
	 */
	public ConvertirAction(FenetreE2C f, String text)
	{
		super(text);
		this.fenetre = f;
	}

	/**
	 * Méthode se déclenchant lorsque l'utilisateur appuie sur le bouton Convertir
	 * @param L'évènement de l'action
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{	
		File dossier = new File(this.fenetre.getTextFieldDirectory());
		if(!dossier.isDirectory())
		{
			JOptionPane.showMessageDialog(null, "Le chemin du dossier de destination est invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String fichierExcel = this.fenetre.getTextField();
		String dossierDest = this.fenetre.getTextFieldDirectory();
		
		// On récupère les évènements de l'emploi du temps
		Excel excel = new Excel(fichierExcel, this.fenetre.getSelectionner());
		if(!excel.recupererEvenement())
			return;
		
		String nomFichier = fichierExcel.substring(fichierExcel.lastIndexOf("/"), fichierExcel.lastIndexOf(".xlsx"));
		
		// On ajoute au nom du fichier iCalendar le professeur sélectionné
		String fichierIcal = dossierDest;
		fichierIcal += nomFichier + "-" + this.fenetre.getSelectionner() + ".ics";

		// On crée le fichier iCalendar correspondant			
		ICalendar calendar = new ICalendar(fichierIcal);

		calendar.addEvent(excel.getEvenement());
		
		if(calendar.createICSFile())
		{
			String str = "Conversion réussi avec succès ! \n" + excel.getEvenement().size() + " évènements générés";
			JOptionPane.showMessageDialog(null, str, "Information", JOptionPane.INFORMATION_MESSAGE);
			
			try {
				Desktop.getDesktop().open(new File(dossierDest));
			} catch (IllegalArgumentException |IOException e) {
				JOptionPane.showMessageDialog(null, "Le chemin du dossier de destination est invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
