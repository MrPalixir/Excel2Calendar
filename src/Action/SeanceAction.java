package Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import Calendar.Excel;
import Fenetre.FenetreE2C;

@SuppressWarnings("serial")
public class SeanceAction extends AbstractAction {
	/**
	 * <b> SeanceAction est la classe qui se d�clenche lors que l'utilisateur clique sur le bouton Calculer le nombre de s�ances</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * La fen�tre principale
	 */
	private FenetreE2C fenetre;
	
	/**
	 * Le constructeur de la classe SeanceAction
	 * @param f La fen�tre principale
	 * @param text Le texte se trouvant sur le bouton calculer le nombre de s�ances ici "Calculer le nombre de s�ances"
	 */
	public SeanceAction(FenetreE2C f, String text)
	{
		super(text);
		this.fenetre = f;
	}

	/**
	 * M�thode se d�clenchant lorsque l'utilisateur clique sur le bouton calculer le nombre de s�ances
	 * @param L'�v�nement de l'action
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		// On r�cup�re les �v�nements du fichier Excel
		Excel ex = new Excel(this.fenetre.getTextField(), this.fenetre.getSelectionner());
		
		ex.recupererEvenement();
		
		// On affiche le nombre de s�ances effectu�s � l'utilisateur
		if(ex.getEvenement().size() > 0)
		{
			String tmp = "Pour " + this.fenetre.getSelectionner() + " :\n" + "Il y a au total " + ex.getEvenement().size() + " s�ances";
			
			JOptionPane.showMessageDialog(null, tmp, "Information", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
