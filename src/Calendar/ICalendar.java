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
	 * <b> ICalendar est la classe qui écrit le fichier iCalendar</b>
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
	 * @Précondition Le dossier en entrée doit être un dossier valide 
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
	 * @return Return le calendrier contenant les évènements de l'emploi du temps Excel
	 */
	public Calendar getCalendar()
	{
		return this.calendar;
	}
	
	/**
	 * Sauvegarde les informations contenu dans l'attribut calendar dans un fichier iCalendar
	 * @return True si la création du fichier iCalendar est un succès, false sinon
	 * @Précondition Le calendrier doit contenir au moins 1 évènement
	 * @Précondition Le dossier de destination doit être un dossier valide
	 * @PostCondition Créé le fichier .ics
	 */
	public boolean createICSFile()
	{
		if(this.fichierIcs == null)
			return false;
		
		// Sauvegarder le fichier nécessite de contenir au moins un élément
		try {
			FileOutputStream fout = new FileOutputStream(this.fichierIcs);
			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(this.calendar, fout);
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Le dossier saisie est invalide ou le dossier de destination n'est accessible qu'en lecture seule", "Erreur", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (ValidationException | IOException e) {
			JOptionPane.showMessageDialog(null, "Le fichier iCalendar contient 0 évènement", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		
		return true;
	}
	
	/**
	 * On parcourt tous les évènements et on les ajoute à notre fichier iCalendar
	 * @param event La liste des évènements contenu dans le fichier Excel
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
	 * Ajouter un évènement à un fichier iCalendar
	 * @param event L'évènement à ajouter au fichier iCalendar
	 */
	private void addEntry(Evenement event)
	{
		// On définit l'heure de début et l'heure de fin de l'évènement
		java.util.Calendar dateDebut = event.getHeureDebut();
		java.util.Calendar dateFin = event.getHeureFin();
		
		// On donne un nom à l'évènement
		String eventName = event.getGroupe() + " - " + event.getModule() + " - " + event.getProfesseur();
		DateTime start = new DateTime(dateDebut.getTime());
		DateTime end = new DateTime(dateFin.getTime());
		VEvent e = new VEvent(start, end, eventName);
		
		// On donne un identifiant unique à l'évènement
		RandomUidGenerator t = new RandomUidGenerator();
		Uid uid = t.generateUid();
		e.getProperties().add(uid);
		
		// On ajoute l'évènement au fichier iCalendar
		calendar.getComponents().add(e);
	}
}
