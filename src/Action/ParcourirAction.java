package Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import Fenetre.FenetreE2C;



@SuppressWarnings("serial")
public class ParcourirAction extends AbstractAction{
	/**
	 * <b> ParcourirAction est la classe qui se déclenche lors que l'utilisateur clique sur le bouton Parcourir</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * La fenêtre principale
	 */
	private FenetreE2C fenetre;
	
	/**
	 * Le constructeur de la classe ParcourirAction
	 * @param f La fenêtre principale
	 * @param text Le texte se trouvant sur le bouton parcourir ici "..."
	 */
	public ParcourirAction(FenetreE2C f, String text)
	{
		super(text);
		this.fenetre = f;
	}

	/**
	 * Méthode se déclenchant lorsque l'utilisateur appuie sur le bouton Parcourir
	 * @param L'évènement de l'action
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		this.fenetre.getFileChooser().showOpenDialog(this.fenetre);
		
		if(this.fenetre.getFileChooser().getSelectedFile() == null)
			return;
		
		String path = this.fenetre.getFileChooser().getSelectedFile().getPath();
		// On remplace les \ par des / pour avoir des chemins identiques sur linux, windows et macosx
		path = path.replaceAll("\\\\", "/");
		
		// On récupère uniquement le nom du fichier dans le chemin
		int indexSlash = path.lastIndexOf("/");
		
		if(indexSlash == -1)
		{
			JOptionPane.showMessageDialog(null, "Le chemin du fichier est invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// On cherche le premier tiret dans le nom du fichier
		int index = path.substring(indexSlash).indexOf("-");
		
		if(index == -1)
		{
			JOptionPane.showMessageDialog(null, "Le nom du fichier est invalide\n\nVoici des noms de fichier valide :\n"
					+ "planning-2018.xlsx\ncalendrier-2018.xlsx", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// On charge le fichier Excel et on raffraichit la liste des professeurs correspondante
		try {
			this.fenetre.setTextField(path);	
		
			this.fenetre.rafraichirListe();
			
		}catch(Exception e) {}
	}
}
