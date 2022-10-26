package Calendar;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

public class ICalendar {
	/**
	 * <b> ICalendar est la classe qui �crit le fichier iCalendar</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * Le calendrier final
	 */
	private Calendar calendar;
	
	/**
	 * Le fichier iCalendar
	 */
	private String fichierIcs;
	
	/**
	 * Construit un fichier iCalendar
	 * @param dossier Le dossier de destination du fichier iCalendar
	 * @Pr�condition Le dossier en entr�e doit �tre un dossier valide 
	 */
	public ICalendar(String fichierIcs)
	{
		if(fichierIcs == null)
		{
			JOptionPane.showMessageDialog(null, "Le dossier saisie n'est pas un dossier valide", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(fichierIcs.lastIndexOf("/") == -1)
		{
			JOptionPane.showMessageDialog(null, "Le dossier saisie n'est pas un dossier valide", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		this.fichierIcs = fichierIcs;
		
		// Calendrier ical4j 
		this.calendar = new Calendar();
		this.calendar.getProperties().add(new ProdId("Excel2Calendar"));
		this.calendar.getProperties().add(Version.VERSION_2_0);
		this.calendar.getProperties().add(CalScale.GREGORIAN);
	}
	
	/**
	 * @return Return le calendrier contenant les �v�nements de l'emploi du temps Excel
	 */
	public Calendar getCalendar()
	{
		return this.calendar;
	}
	
	/**
	 * Sauvegarde les informations contenu dans l'attribut calendar dans un fichier iCalendar
	 * @return True si la cr�ation du fichier iCalendar est un succ�s, false sinon
	 * @Pr�condition Le calendrier doit contenir au moins 1 �v�nement
	 * @Pr�condition Le dossier de destination doit �tre un dossier valide
	 * @PostCondition Cr�� le fichier .ics
	 */
	public boolean createICSFile()
	{
		if(this.fichierIcs == null)
			return false;
		
		// Sauvegarder le fichier n�cessite de contenir au moins un �l�ment
		try {
			FileOutputStream fout = new FileOutputStream(this.fichierIcs);
			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(this.calendar, fout);
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Le dossier saisie est invalide ou le dossier de destination n'est accessible qu'en lecture seule", "Erreur", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (ValidationException | IOException e) {
			JOptionPane.showMessageDialog(null, "Le fichier iCalendar contient 0 �v�nement", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		return true;
	}
	
	/**
	 * On parcourt tous les �v�nements et on les ajoute � notre fichier iCalendar
	 * @param event La liste des �v�nements contenu dans le fichier Excel
	 */
	public void addEvent(List<Evenement> event)
	{
		if(this.fichierIcs == null)
			return;
		
		for(Evenement e : event)
		{
			if(e == null)
				continue;
			
			addEntry(e);
		}
	}
	
	/**
	 * Ajouter un �v�nement � un fichier iCalendar
	 * @param event L'�v�nement � ajouter au fichier iCalendar
	 */
	private void addEntry(Evenement event)
	{
		// On d�finit l'heure de d�but et l'heure de fin de l'�v�nement
		java.util.Calendar dateDebut = event.getHeureDebut();
		java.util.Calendar dateFin = event.getHeureFin();
		
		// On donne un nom � l'�v�nement
		String eventName = event.getGroupe() + " - " + event.getModule() + " - " + event.getProfesseur();
		DateTime start = new DateTime(dateDebut.getTime());
		DateTime end = new DateTime(dateFin.getTime());
		VEvent e = new VEvent(start, end, eventName);
		
		// On donne un identifiant unique � l'�v�nement
		RandomUidGenerator t = new RandomUidGenerator();
		Uid uid = t.generateUid();
		e.getProperties().add(uid);
		
		// On ajoute l'�v�nement au fichier iCalendar
		calendar.getComponents().add(e);
	}
}
