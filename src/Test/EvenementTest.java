package Test;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Calendar.Evenement;

public class EvenementTest {
	
	/**
	 * @JeuxEssai Pour l'ensemble de cette classe de test le jeux d'essai sera un évènement à l'heure/minute actuelle
	 * pour le module InM3101, pour le groupe InS3B et par le professeur PMA
	 */
	
	private Evenement event;
	
	@Before
	public void setUp() throws Exception {
		this.event = new Evenement(Calendar.getInstance(), Calendar.getInstance(),"InM3101","InS3B","PMA");
	}

	@After
	public void tearDown() throws Exception {
		this.event = null;
	}
	
	/**
	 * On test les getters de la classe évènement
	 * @ResultatAttendu Le module doit être égale à InM3101
	 * @ResultatAttendu Le groupe doit être égale à InS3B
	 * @ResultatAttendu Le professeur doit être égale à PMA
	 * @ResultatAttendu L'heure de début de l'évènement doit être l'heure actuelle
	 * @ResultatAttendu L'heure de fin de l'évènement doit être l'heure actuelle
	 */
	@Test
	public void testGetters() {
		assertEquals("InM3101", this.event.getModule());
		assertEquals("InS3B", this.event.getGroupe());
		assertEquals("PMA", this.event.getProfesseur());
		assertEquals(Calendar.getInstance(), this.event.getHeureDebut());
		assertEquals(Calendar.getInstance(), this.event.getHeureFin());
	}
	
	/**
	 * On test les setters de la classe évènement
	 * @ResultatAttendu La nouvelle valeur du module doit être InM3103
	 * @ResultatAttendu La nouvelle valeur du groupe doit être InS3E
	 * @ResultatAttendu La nouvelle valeur du professeur doit être PM
	 * @ResultatAttendu La nouvelle valeur de l'heure de début doit être null
	 * @ResultatAttendu La nouvelle valeur de l'heure de fin doit être null
	 */
	@Test
	public void testSetters() {
		this.event.setModule("InM3103");
		this.event.setGroupe("InS3E");
		this.event.setProfesseur("PM");
		this.event.setHeureDebut(null);
		this.event.setHeureFin(null);
		assertEquals("InM3103", this.event.getModule());
		assertEquals("InS3E", this.event.getGroupe());
		assertEquals("PM", this.event.getProfesseur());
		assertEquals(null, this.event.getHeureDebut());
		assertEquals(null, this.event.getHeureFin());
	}

	
	/**
	 * On test les différentes valeur des dates(début et fin) de la classe évènement
	 * @ResultatAttendu Le mois de l'heure de début et de fin de l'évènementdoit être le mois actuel
	 * @ResultatAttendu L'annéee de début et de fin de l'évènement doit être l'année actuel
	 * @ResultatAttendu La minute de début et de fin de l'évènement doit être la minute actuelle
	 * @ResultatAttendu L'heure de début et de fin de l'évènement doit être l'heure actuelle
	 */
	@Test 
	public void testDate() 
	{
		Calendar cal = Calendar.getInstance();
		
		assertEquals(cal.get(Calendar.MONTH), this.event.getHeureFin().get(Calendar.MONTH));
		assertEquals(cal.get(Calendar.MONTH), this.event.getHeureDebut().get(Calendar.MONTH));
		
		assertEquals(cal.get(Calendar.DATE), this.event.getHeureFin().get(Calendar.DATE));
		assertEquals(cal.get(Calendar.DATE), this.event.getHeureDebut().get(Calendar.DATE));
		
		assertEquals(cal.get(Calendar.MINUTE), this.event.getHeureFin().get(Calendar.MINUTE));
		assertEquals(cal.get(Calendar.MINUTE), this.event.getHeureDebut().get(Calendar.MINUTE));
		
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), this.event.getHeureFin().get(Calendar.HOUR_OF_DAY));
		assertEquals(cal.get(Calendar.HOUR_OF_DAY), this.event.getHeureDebut().get(Calendar.HOUR_OF_DAY));
	}
}
