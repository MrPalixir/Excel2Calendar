package Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import Calendar.Excel;
import Fenetre.FenetreE2C;

@SuppressWarnings("serial")
public class SeanceAction extends AbstractAction {
	/**
	 * <b> SeanceAction est la classe qui se déclenche lors que l'utilisateur clique sur le bouton Calculer le nombre de séances</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * La fenêtre principale
	 */
	private FenetreE2C fenetre;
	
	/**
	 * Le constructeur de la classe SeanceAction
	 * @param f La fenêtre principale
	 * @param text Le texte se trouvant sur le bouton calculer le nombre de séances ici "Calculer le nombre de séances"
	 */
	public SeanceAction(FenetreE2C f, String text)
	{
		super(text);
		this.fenetre = f;
	}

	/**
	 * Méthode se déclenchant lorsque l'utilisateur clique sur le bouton calculer le nombre de séances
	 * @param L'évènement de l'action
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// On récupère les évènements du fichier Excel
		Excel ex = new Excel(this.fenetre.getTextField(), this.fenetre.getSelectionner());
		
		ex.recupererEvenement();
		
		// On affiche le nombre de séances effectués à l'utilisateur
		if(ex.getEvenement().size() > 0)
		{
			String tmp = "Pour " + this.fenetre.getSelectionner() + " :\n" + "Il y a au total " + ex.getEvenement().size() + " séances";
			
			JOptionPane.showMessageDialog(null, tmp, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
