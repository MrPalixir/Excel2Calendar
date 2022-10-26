package Test;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Calendar.Evenement;

public class EvenementTest {
	
	/**
	 * @JeuxEssai Pour l'ensemble de cette classe de test le jeux d'essai sera un �v�nement � l'heure/minute actuelle
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
	 * On test les getters de la classe �v�nement
	 * @ResultatAttendu Le module doit �tre �gale � InM3101
	 * @ResultatAttendu Le groupe doit �tre �gale � InS3B
	 * @ResultatAttendu Le professeur doit �tre �gale � PMA
	 * @ResultatAttendu L'heure de d�but de l'�v�nement doit �tre l'heure actuelle
	 * @ResultatAttendu L'heure de fin de l'�v�nement doit �tre l'heure actuelle
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
	 * On test les setters de la classe �v�nement
	 * @ResultatAttendu La nouvelle valeur du module doit �tre InM3103
	 * @ResultatAttendu La nouvelle valeur du groupe doit �tre InS3E
	 * @ResultatAttendu La nouvelle valeur du professeur doit �tre PM
	 * @ResultatAttendu La nouvelle valeur de l'heure de d�but doit �tre null
	 * @ResultatAttendu La nouvelle valeur de l'heure de fin doit �tre null
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
	 * On test les diff�rentes valeur des dates(d�but et fin) de la classe �v�nement
	 * @ResultatAttendu Le mois de l'heure de d�but et de fin de l'�v�nementdoit �tre le mois actuel
	 * @ResultatAttendu L'ann�ee de d�but et de fin de l'�v�nement doit �tre l'ann�e actuel
	 * @ResultatAttendu La minute de d�but et de fin de l'�v�nement doit �tre la minute actuelle
	 * @ResultatAttendu L'heure de d�but et de fin de l'�v�nement doit �tre l'heure actuelle
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
