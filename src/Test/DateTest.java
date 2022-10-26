package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Other.Date;

public class DateTest {
	
	/**
	 * @JeuxEssai Pour l'ensemble de cette classe de test le jeux d'essai sera une date qui a pour paramètre
	 * 9h et 30 minutes
	 */

	private Date date;
	
	@Before
	public void setUp() throws Exception {
		this.date = new Date(9,30);
	}

	@After
	public void tearDown() throws Exception {
		this.date = null;
	}

	/**
	 * On test les getters de la classe Date
	 * @ResultatAttendu Le nombre d'heure de la date doit être égale à 9
	 * @ResultatAttendu Le nombre de minutes de la date doit être à 30
	 */
	@Test
	public void testGetters() {
		assertEquals(9, this.date.getHeure());
		assertEquals(30, this.date.getMinute());
	}
	
	/**
	 * On test les setters de la classe Date
	 * @ResultatAttendu Le nouveau nombre d'heure de la date doit être égale à 11
	 * @ResultatAttendu Le nouveau nombre de minutes de la date doit être à 0
	 */
	@Test
	public void testSetters() {
		this.date.setHeure(11);
		this.date.setMinutes(0);
		assertEquals(this.date.getHeure(), 11);
		assertEquals(this.date.getMinute(), 0);
	}
	
	/**
	 * On test le toString de la classe Date
	 * @ResultatAttendu La chaine de caractère de la date doit correspondre à 9h30
	 */
	@Test
	public void testToString() {
		assertEquals("9h30", this.date.toString());
	}
	
}
