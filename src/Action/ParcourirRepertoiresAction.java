package Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Fenetre.FenetreE2C;

@SuppressWarnings("serial")
public class ParcourirRepertoiresAction extends AbstractAction {
	/**
	 * <b> ParcourirRepertoiresAction est la classe qui se d�clenche lors que l'utilisateur clique sur le bouton Parcourir le dossier de destination</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * La fen�tre principale
	 */
	private FenetreE2C fenetre;
	
	/**
	 * Le constructeur de la classe ParcourirRepertoiresAction
	 * @param f La fen�tre principale
	 * @param text Le texte se trouvant sur le bouton parcourir ici "..."
	 */
	public ParcourirRepertoiresAction(FenetreE2C f, String text)
	{
		super(text);
		this.fenetre = f;
	}

	/**
	 * M�thode se d�clenchant lorsque l'utilisateur appuie sur le bouton parcourir
	 * @param L'�v�nement de l'action
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// On ouvre l'explorateur de dossiers
		this.fenetre.getFileChooserDirectory().showOpenDialog(this.fenetre);
		
		// On met � jour le dossier de destination et on remplace les \ par des /
		try {
			this.fenetre.setTextFieldDirectory((this.fenetre.getFileChooserDirectory().getSelectedFile().getPath().replaceAll("\\\\", "/") + "/"));
		}catch(Exception e) {}		
	}
}
