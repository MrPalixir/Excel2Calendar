package Panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Fenetre.FenetreE2C;

@SuppressWarnings("serial")
public class DragNDropPanel extends JPanel implements DropTargetListener {
	/**
	 * <b> DragNDropPanel est la classe qui se d�clenche lors que l'utilisateur glisse un fichier sur la fen�tre principale</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * Les diff�rents �v�nement possible lors du glisser/d�poser
	 */
    public enum DragState 
    {
        Waiting,
        Accept,
        Reject
    }
	
	/**
	 * Le chemin du fichier Excel
	 */
	private String path;
	
	/**
	 * La fen�tre principale
	 */
	private FenetreE2C fenetre;
	
	/**
	 * L'�tat du drag and drop actuellement
	 */
    private DragState state = DragState.Waiting;
    
    /**
     * Le constructeur de la classe DragNDropPanel
     * @param f La fen�tre principale
     */
    public DragNDropPanel(FenetreE2C f) 
    {
    	this.fenetre = f;
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this, true);

        setBackground(Color.BLACK);
    }
    
    /**
     * D�finit les dimensions de la zone acceptant la drag and drop
     */
    @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(300, 300);
    }

    /**
     * Dessine les contours de la zone acceptant le drag and drop
     */
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
    }
    
    /**
     * M�thode appell� lorsque l'utilisateur glisse un fichier dans la zone acceptant le drag and drop
     */
    @Override
    public void dragEnter(DropTargetDragEvent dtde) 
    {
    	// On d�finit l'�tat de base de la zone comme n'acceptant pas de fichier
        state = DragState.Reject;
        
        // On r�cup�re l'objet concern� par l'op�ration de glisser en cours
        Transferable t = dtde.getTransferable();
        //On v�rifie si l'objet supporte le transfert de liste de fichier
        if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) 
        {
            try 
            {
            	// On r�cup�re la liste de fichier transf�r� par l'op�ration de glisser en cours
                Object td = t.getTransferData(DataFlavor.javaFileListFlavor);
                
                if (td instanceof List && td != null) 
                {
                	// On accepte l'op�ration de glisser en cours
                    state = DragState.Accept;
                    
                    // On parcourt tous les objets de la liste de fichier
                    for (Object value : (List<?>) td) 
                    {
                        if (value instanceof File) 
                        {
                            File file = (File) value;
                            String name = file.getName().toLowerCase();
                            
                            // Si ce n'est pas un fichier d'extension .xlsx on le refuse
                            if (!name.endsWith(".xlsx")) 
                            {
                                state = DragState.Reject;
                                break;
                            } 
                            else
                            	this.path = file.getPath();	
                        }
                    }
                }
            } 
            catch (UnsupportedFlavorException | IOException ex) 
            {
    			JOptionPane.showMessageDialog(null, "Le type de donn�e transf�r� n'est pas pris en charge", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        if (state == DragState.Accept) 
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        else 
            dtde.rejectDrag();
        
        repaint();
    }
    
    /**
     * M�thode appel�e lorsque l'utilisateur rel�che le bouton de la souris
     * @param La zone concern� par le glisser/d�poser
     */
    @Override
    public void dragExit(DropTargetEvent dtde) 
    {
    	this.setBorder(null);
        state = DragState.Waiting;
        
        repaint();
    }

    /**
     * M�thode appel�e lorsque le glisser en cours est valid�e (il s'agit d'un fichier d'extension .xlsx)
     * @param La zone concern� par le glisser/d�poser
     */
    @Override
    public void drop(DropTargetDropEvent dtde) 
    { 	
    	this.setBorder(null);
    	
    	this.path = this.path.replaceAll("\\\\", "/");
    	
    	int indexSlash = this.path.lastIndexOf("/");
		
		if(indexSlash == -1)
		{
			JOptionPane.showMessageDialog(null, "Le chemin du fichier est invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// On cherche le premier tiret dans le nom du fichier
		int index = this.path.substring(indexSlash).indexOf("-");

    	if(index == -1)
		{
			JOptionPane.showMessageDialog(null, "Le nom du fichier est invalide\n\nVoici des noms de fichier valide :\n"
					+ "planning-2018.xlsx\ncalendrier-2018.xlsx", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
    	
		this.fenetre.getTextField2().setText(this.path.replaceAll("\\\\", "/"));
		
		this.fenetre.rafraichirListe();
		
        this.state = DragState.Waiting;
        
        repaint();
    }
    
    /**
     * M�thode appel�e lorsque l'utilisateur passe la souris sur la zone de d�poser glisser avec un fichier s�lectionn�
     * @param arg0
     */
	@Override
	public void dragOver(DropTargetDragEvent arg0) 
	{}

	/**
	 * M�thode appel� si l'utilisateur a modifi� le geste de d�p�t actuel
	 * @param arg0
	 */
	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) 
	{}
}
