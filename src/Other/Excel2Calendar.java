package Other;

import javax.swing.SwingUtilities;

import Fenetre.FenetreE2C;

/**
 * <b> Excel2Calendar est la classe main</b>
 * 
 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
 *
 */

public class Excel2Calendar {
	public static void main(String[] args){
		//On ex�cute de mani�re asynchrone la fen�tre sur le thread de distribution des �v�nements AWT
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				FenetreE2C fenetre = new FenetreE2C();
				fenetre.setVisible(true);
			}
		});
	}
}