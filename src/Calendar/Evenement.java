package Calendar;
import java.util.Calendar;

public class Evenement {
	
	/**
	 * <b> Event est la classe qui d�finit un �v�nement</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * L'heure de d�but de l'�v�nement
	 */
	private Calendar heureDebut;
	/**
	 * L'heure de fin de l'�v�nement
	 */
	private Calendar heureFin;
	/**
	 * Le module de l'�v�nement
	 */
	private String module;
	/**
	 * Le groupe de l'�v�nement
	 */
	private String groupe;
	/**
	 * Le professeur de l'�v�nement
	 */
	private String professeur;
	
	/**
	 * 
	 * @param heureDebut L'heure de d�but de l'�v�nement
	 * @param heureFin L'heure de fin de l'�v�nement
	 * @param module Le nom du module concern� par l'�v�nement
	 * @param groupe Le nom du groupe concern� par l'�v�nement
	 * @param professeur Le nom du professeur concern� par l'�v�nement
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
	 * @return Le professeur concern� par l'�v�nement
	 */
	public String getProfesseur() {
		return professeur;
	}
	
	/**
	 * Met � jour le professeur
	 * @param Le nouveau professeur concern� par cette �v�nement
	 */
	public void setProfesseur(String professeur) {
		this.professeur = professeur;
	}
	
	/**
	 * @return L'heure de d�but de l'�v�nement
	 */
	public Calendar getHeureDebut() {
		return heureDebut;
	}

	/**
	 * Met � jour l'heure de d�but
	 * @param La nouvelle heure de d�but de l'�v�nement
	 */
	public void setHeureDebut(Calendar heureDebut) {
		this.heureDebut = heureDebut;
	}

	/**
	 * @return L'heure de fin de l'�v�nement
	 */
	public Calendar getHeureFin() {
		return heureFin;
	}

	/**
	 * Met � jour l'heure de fin
	 * @param La nouvelle heure de fin de l'�v�nement
	 */
	public void setHeureFin(Calendar heureFin) {
		this.heureFin = heureFin;
	}

	/**
	 * @return Le module de l'�v�nement
	 */
	public String getModule() {
		return module;
	}

	/**
	 * Met � jour le module de l'�v�nement
	 * @return Le nouveau module de l'�v�nement
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return Le groupe concern� par l'�v�nement
	 */
	public String getGroupe() {
		return groupe;
	}

	/**
	 * Met � jour le groupe concern� par l'�v�nement
	 * @return La nouvelle valeur du groupe
	 */
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	/**
	 * Affiche un �v�nement
	 */
	@Override
	public String toString() {
		return ("Date : " + this.heureDebut.get(Calendar.DAY_OF_MONTH)+"/"+(this.heureDebut.get(Calendar.MONTH)+1)+"/"+this.heureDebut.get(Calendar.YEAR)+ 
				" - Heure debut : " + this.heureDebut.get(Calendar.HOUR_OF_DAY)+"h"+this.heureDebut.get(Calendar.MINUTE) + " - "
				+ "Heure Fin : " + this.heureFin.get(Calendar.HOUR_OF_DAY)+"h"+this.heureFin.get(Calendar.MINUTE) + " - Cours : " + this.groupe 
				+ " - Module : " + this.module + " - Professeur : " + this.professeur + "\n");
	}
}
