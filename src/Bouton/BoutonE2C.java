package Bouton;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class BoutonE2C extends JButton {
	/**
	 * <b> BoutonE2C est une classe qui s'occupe de gérer les couleurs des boutons</b>
	 * 
	 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
	 *
	 */
	
	/**
	 * La fenêtre principale
	 */
	
	/**
	 * La couleur quand tu appuis sur un bouton
	 */
    private Color pressedColor;
    
    /**
     * La couleur quand on passe la souris sur un bouton
     */
    private Color rolloverColor;
    
    /**
     * La couleur de base
     */
    private Color normalColor;
    
    /**
     * Constructeur de la classe BoutonE2C
     * @param r Rouge
     * @param g Vert
     * @param b Bleu
     * @param absA L'action associé au bouton
     */
    public BoutonE2C (int r, int g, int b, AbstractAction absA) {
        super(absA);
        this.normalColor = new Color(r, g, b);
        this.pressedColor = new Color(r-25,g-40,b-40);
        this.rolloverColor = new Color(r-10, g-15, b-15);
        
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		this.setBorder(loweredetched);
        
        setBorderPainted(false);
        setFocusPainted(false);

        setContentAreaFilled(false);
        setOpaque(true);

        setBackground(normalColor);
        setForeground(Color.WHITE);
        setFont(new Font("Tahoma", Font.BOLD, 12));

        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (getModel().isPressed())
                    setBackground(pressedColor);
                else if (getModel().isRollover())
                    setBackground(rolloverColor);
                else
                    setBackground(normalColor);
            }
        });
    }
}