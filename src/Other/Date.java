package Other;

public class Date {
	/**
	 * <b> Date est la classe qui décrit une tranche horaire</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * L'heure de la date
	 */
	private int heure;
	/**
	 * La minute de la date
	 */
	private int minute;
	
	/**
	 * @param heures L'heure de la date
	 * @param minutes Les minutes de la date
	 */
	public Date(int heure, int minute)
	{
		this.heure = heure;
		this.minute = minute;
	}
	
	/**
	 * @return Le nombre d'heure de la date
	 */
	public int getHeure() {
		return heure;
	}

	/**
	 * Met à jour le nombre d'heure de la date
	 * @return La nouvelle valeur de l'heure
	 */
	public void setHeure(int heure) {
		this.heure = heure;
	}

	/**
	 * @return Le nombre de minute de la date
	 */
	public int getMinute() {
		return minute;
	}

	/**
	 * Met à jour le nombre de minute de la date
	 * @return La nouvelle valeur du nombre de minutes
	 */
	public void setMinutes(int minute) {
		this.minute = minute;
	}
	
	/**
	 * Affiche une date
	 */
	@Override
	public String toString()
	{
		return (heure + "h" + minute);
	}
}
