package Calendar;
import java.util.Calendar;

public class Evenement {
	
	/**
	 * <b> Event est la classe qui définit un évènement</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * L'heure de début de l'évènement
	 */
	private Calendar heureDebut;
	/**
	 * L'heure de fin de l'évènement
	 */
	private Calendar heureFin;
	/**
	 * Le module de l'évènement
	 */
	private String module;
	/**
	 * Le groupe de l'évènement
	 */
	private String groupe;
	/**
	 * Le professeur de l'évènement
	 */
	private String professeur;
	
	/**
	 * 
	 * @param heureDebut L'heure de début de l'évènement
	 * @param heureFin L'heure de fin de l'évènement
	 * @param module Le nom du module concerné par l'évènement
	 * @param groupe Le nom du groupe concerné par l'évènement
	 * @param professeur Le nom du professeur concerné par l'évènement
	 */
	public Evenement(Calendar heureDebut, Calendar heureFin, String module, String groupe, String professeur)
	{
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.module = module;
		this.groupe = groupe;
		this.professeur = professeur;
	}
	
	/**
	 * @return Le professeur concerné par l'évènement
	 */
	public String getProfesseur() {
		return professeur;
	}
	
	/**
	 * Met à jour le professeur
	 * @param Le nouveau professeur concerné par cette évènement
	 */
	public void setProfesseur(String professeur) {
		this.professeur = professeur;
	}
	
	/**
	 * @return L'heure de début de l'évènement
	 */
	public Calendar getHeureDebut() {
		return heureDebut;
	}

	/**
	 * Met à jour l'heure de début
	 * @param La nouvelle heure de début de l'évènement
	 */
	public void setHeureDebut(Calendar heureDebut) {
		this.heureDebut = heureDebut;
	}

	/**
	 * @return L'heure de fin de l'évènement
	 */
	public Calendar getHeureFin() {
		return heureFin;
	}

	/**
	 * Met à jour l'heure de fin
	 * @param La nouvelle heure de fin de l'évènement
	 */
	public void setHeureFin(Calendar heureFin) {
		this.heureFin = heureFin;
	}

	/**
	 * @return Le module de l'évènement
	 */
	public String getModule() {
		return module;
	}

	/**
	 * Met à jour le module de l'évènement
	 * @return Le nouveau module de l'évènement
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return Le groupe concerné par l'évènement
	 */
	public String getGroupe() {
		return groupe;
	}

	/**
	 * Met à jour le groupe concerné par l'évènement
	 * @return La nouvelle valeur du groupe
	 */
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	/**
	 * Affiche un évènement
	 */
	@Override
	public String toString() {
		return ("Date : " + this.heureDebut.get(Calendar.DAY_OF_MONTH)+"/"+(this.heureDebut.get(Calendar.MONTH)+1)+"/"+this.heureDebut.get(Calendar.YEAR)+ 
				" - Heure debut : " + this.heureDebut.get(Calendar.HOUR_OF_DAY)+"h"+this.heureDebut.get(Calendar.MINUTE) + " - "
				+ "Heure Fin : " + this.heureFin.get(Calendar.HOUR_OF_DAY)+"h"+this.heureFin.get(Calendar.MINUTE) + " - Cours : " + this.groupe 
				+ " - Module : " + this.module + " - Professeur : " + this.professeur + "\n");
	}
}
