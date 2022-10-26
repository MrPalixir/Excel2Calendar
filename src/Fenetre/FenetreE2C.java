package Fenetre;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Action.ConvertirAction;
import Action.ParcourirAction;
import Action.ParcourirRepertoiresAction;
import Action.SeanceAction;
import Bouton.BoutonE2C;
import Calendar.Excel;
import Panel.DragNDropPanel;


/**
 * <b> FenetreE2C est la classe qui construit la fenetre de de l'application Excel2Calendar</b>
 * 
 * @author Kieffer Ewen, Coratger Lucas, Petit Guillaume, Gadoullet Leo
 *
 */
@SuppressWarnings("serial")
public class FenetreE2C extends JFrame{
	
	
	/**
	 * La zone d'édition de texte permettant de renseigner et d'afficher le chemin du fichier .xlsx a convertir
	 * 
	 * @see FenetreE2C#getTextField()
	 * @see FenetreE2C#setTextField()
	 */
	private JTextField selectFileTextField;
	
	/**
	 * La zone d'edition de texte permettant de renseigner et d'afficher le chemin du dossier où sera enregistré le fichier .ical généré
	 * 
	 * @see FenetreE2C#getTextField2()
	 * @see FenetreE2C#setTextFieldDirectory(String)
	 */
	private JTextField selectDirectoryTextField;
	
	/**
	 * Mécanisme de sélection du fichier associé à la sélection du fichier .xlsx à convertir
	 * 
	 * @see FenetreE2C#getFileChooser()
	 */
	private JFileChooser fileChooser;
	
	/**
	 * Mécanisme de sélection de fichier associe à la sélection du dossier où sera enregistré le fichier .ical généré
	 * 
	 * @see FenetreE2C#getFileChooserDirectory()
	 */
	private JFileChooser fileChooserdirectory;
	
	/**
	 * Modèle permettant de modifier la liste des professeurs
	 * 
	 * @see FenetreE2C#jListeProfesseurs
	 */
	private final DefaultListModel<String> model;
	
	/**
	 * Liste des professeurs
	 * 
	 * @see FenetreE2C#getjListeProfesseurs()
	 * @see FenetreE2C#setjListeProfesseurs(List)
	 * @see FenetreE2C#getSelectionner()
	 * @see FenetreE2C#RafraichirListe()
	 */
	private JList<String> jListeProfesseurs;

	/**
	 * Couleur de fond de la fenêtre.
	 * 
	 */
	private static final Color COULEURDEFOND = new Color(240, 243, 189);
	
	/**
	 * Couleur des champs de texte de la fenetre
	 * 
	 */
	private static final Color COULEURDECHAMPS = new Color(238, 255, 238);
	
	/**
	 * Couleur de fond de la liste des professeurs
	 * 
	 */
	private static final Color COULEURDELISTE = new Color(238, 255, 238);
	
	/**
	 * Couleur du titre de la fenêtre
	 * 
	 */
	private static final Color COULEURDETITRE = new Color(42, 157, 143);
	
	/**
	 * Couleur principale des boutons de la fenêtre
	 * 
	 */
	private static  final Color COULEURDEBOUTONS = new Color(42, 157, 143);
	
	/**
	 * Constructeur FenetreE2C.
	 */
	public FenetreE2C() 
	{
		ImageIcon img = new ImageIcon("ressources/logo.png");
	    this.setIconImage(img.getImage());  
		this.selectFileTextField = new JTextField();
		this.model = new DefaultListModel<String>();
		this.jListeProfesseurs = new JList<String>(model);	
		
		// Si l'utilisateur appuie sur entrée lors de la saisie du fichier Excel, on raffraichit la liste des professeurs
		this.selectFileTextField.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
	            if (e.getKeyCode() == KeyEvent.VK_ENTER)
	            	rafraichirListe();
	         }
		});

		build();
	}
	
	/**
	 * Definit les paramètres de la fenêtre
	 */
	private void build() 
	{
		this.setTitle("Excel2Calendar");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(this.buildContentPane());	
		this.getContentPane().setBackground(FenetreE2C.COULEURDEFOND);
		this.getContentPane().setFont(new Font("Yu Gothic Medium", Font.PLAIN, 12));
		this.setBounds(100, 100, 450, 420);
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Retourne le panel principal de la fenetre avec tous ses éléments
	 * 
	 * @return Le panel principal
	 */
	private JPanel buildContentPane() 
	{	
		JPanel content = new DragNDropPanel(this);
	
		JPanel BouttonsPanel = new JPanel();
		BouttonsPanel.setBackground(FenetreE2C.COULEURDEFOND);
		BouttonsPanel.setBounds(10, 321, 414, 49);
		content.add(BouttonsPanel);
		BouttonsPanel.setLayout(null);
		
		JButton ConvertirButton = new BoutonE2C(FenetreE2C.COULEURDEBOUTONS.getRed(), FenetreE2C.COULEURDEBOUTONS.getGreen(), FenetreE2C.COULEURDEBOUTONS.getBlue(), new ConvertirAction(this, "Convertir"));
		ConvertirButton.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		ConvertirButton.setBounds(10, 11, 190, 25);
		BouttonsPanel.add(ConvertirButton);
		
		JButton NbSceancesButton = new BoutonE2C(FenetreE2C.COULEURDEBOUTONS.getRed(), FenetreE2C.COULEURDEBOUTONS.getGreen(), FenetreE2C.COULEURDEBOUTONS.getBlue(), new SeanceAction(this, "Calculer le nombre de s\u00E9ances"));
		NbSceancesButton.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		NbSceancesButton.setBounds(214, 11, 190, 25);
		BouttonsPanel.add(NbSceancesButton);
		
		JPanel HeaderPanel = new JPanel();
		HeaderPanel.setBackground(FenetreE2C.COULEURDEFOND);
		HeaderPanel.setBounds(10, 11, 414, 49);
		content.add(HeaderPanel);
		HeaderPanel.setLayout(null);
		
		JLabel LogoLabel = new JLabel("Excel2Calendar");
		LogoLabel.setForeground(FenetreE2C.COULEURDETITRE);
		LogoLabel.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 38));
		LogoLabel.setBounds(75, 5, 286, 40);
		HeaderPanel.add(LogoLabel);
		
		JPanel SelectFilePanel = new JPanel();
		SelectFilePanel.setBackground(FenetreE2C.COULEURDEFOND);
		SelectFilePanel.setBounds(10, 64, 414, 62);
		content.add(SelectFilePanel);
		SelectFilePanel.setLayout(null);
		
		selectFileTextField.setBackground(FenetreE2C.COULEURDECHAMPS);
		selectFileTextField.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		selectFileTextField.setColumns(10);
		selectFileTextField.setBounds(10, 32, 343, 25);
		selectFileTextField.setSelectionColor(FenetreE2C.COULEURDEBOUTONS);
		selectFileTextField.setBorder(null);	
		SelectFilePanel.add(selectFileTextField);
		
		JButton FileBrowser = new BoutonE2C(FenetreE2C.COULEURDEBOUTONS.getRed(), FenetreE2C.COULEURDEBOUTONS.getGreen(), FenetreE2C.COULEURDEBOUTONS.getBlue(), new ParcourirAction(this, "..."));
		FileBrowser.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		FileBrowser.setBounds(352, 32, 52, 25);
		SelectFilePanel.add(FileBrowser);
		
		JLabel lblEntrezLeFichier = new JLabel("Entrez le fichier Excel");
		lblEntrezLeFichier.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		lblEntrezLeFichier.setBounds(10, 18, 272, 14);
		SelectFilePanel.add(lblEntrezLeFichier);
		
		JPanel SelectDirectoryPanel = new JPanel();
		SelectDirectoryPanel.setBackground(FenetreE2C.COULEURDEFOND);
		SelectDirectoryPanel.setBounds(10, 256, 414, 54);
		content.add(SelectDirectoryPanel);
		SelectDirectoryPanel.setLayout(null);
		
		JButton DirectoryBrowser = new BoutonE2C(FenetreE2C.COULEURDEBOUTONS.getRed(), FenetreE2C.COULEURDEBOUTONS.getGreen(), FenetreE2C.COULEURDEBOUTONS.getBlue(), new ParcourirRepertoiresAction(this, "..."));
		DirectoryBrowser.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		DirectoryBrowser.setBounds(352, 26, 52, 25);
		SelectDirectoryPanel.add(DirectoryBrowser);
		
		selectDirectoryTextField = new JTextField();
		selectDirectoryTextField.setBackground(FenetreE2C.COULEURDECHAMPS);
		selectDirectoryTextField.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		selectDirectoryTextField.setColumns(10);
		selectDirectoryTextField.setBounds(10, 26, 343, 25);
		selectDirectoryTextField.setSelectionColor(FenetreE2C.COULEURDEBOUTONS);		
		selectDirectoryTextField.setBorder(null);	
		SelectDirectoryPanel.add(selectDirectoryTextField);
		
		JLabel lblChoisissezUnDossier = new JLabel("Choisissez un dossier de destination");
		lblChoisissezUnDossier.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		lblChoisissezUnDossier.setBounds(10, 12, 293, 14);
		SelectDirectoryPanel.add(lblChoisissezUnDossier);
		
		JPanel SelectTeacherPanel = new JPanel();
		SelectTeacherPanel.setBackground(FenetreE2C.COULEURDEFOND);
		SelectTeacherPanel.setBounds(10, 137, 414, 118);
		content.add(SelectTeacherPanel);
		SelectTeacherPanel.setLayout(null);
		
		JLabel lblChoisissezUnProfesseur = new JLabel("Choisissez un professeur");
		lblChoisissezUnProfesseur.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		lblChoisissezUnProfesseur.setBounds(10, 0, 238, 14);
		SelectTeacherPanel.add(lblChoisissezUnProfesseur);
		
		//FileChooser
		this.fileChooser = new JFileChooser();
		this.fileChooser.setBackground(new Color(236, 232, 217));	
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("extension","xlsx");
		this.fileChooser.setFileFilter(filter);
		//FileChooserDirectory
		this.fileChooserdirectory = new JFileChooser();
		this.fileChooserdirectory.setBackground(new Color(236, 232, 217));
		this.fileChooserdirectory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 15, 394, 100);
		scrollPane.setBorder(null);		
		SelectTeacherPanel.add(scrollPane);	
		scrollPane.setViewportView(this.jListeProfesseurs);
		
		this.jListeProfesseurs.setBackground(FenetreE2C.COULEURDELISTE);
		this.jListeProfesseurs.setFont(new Font("Yu Gothic Medium", Font.PLAIN, 11));
		this.jListeProfesseurs.setSelectionBackground(COULEURDEBOUTONS);
		
		return content;
	}

	/**
	 * @return La chaine de caractères contenu dans la zone de texte
	 */
	public String getTextField() {

		return this.selectFileTextField.getText();
	}

	/**
	 * @return Une référence sur la zone de saisie du fichier Excel
	 */
	public JTextField getTextField2() {

		return this.selectFileTextField;
	}

	/**
	 * Met à jour le chemin du fichier .xlsx à convertir dans la zone d'edition de texte
	 * 
	 * @param La nouvelle valeur du chemin du fichier .xlsx
	 */
	public void setTextField(String chemin) 
	{
		this.selectFileTextField.setText(chemin);
	}
	
	
	/**
	 * @return La chaine de caractères contenu dans la zone de saisie du dossier de destination
	 */
	public String getTextFieldDirectory() 
	{
		return this.selectDirectoryTextField.getText();
	}

	/**
	 * Met à jour le chemin du dossier de destination
	 * 
	 * @param La nouvelle valeur du chemin du dossier de destination
	 */
	public void setTextFieldDirectory(String chemin) {
		this.selectDirectoryTextField.setText(chemin);
	}
	
	/**
	 * @return Une référence sur la liste des professeurs
	 */
	public JList<String> getjListeProfesseurs() 
	{
		return this.jListeProfesseurs;
	}
	
	/**
	 * @return L'élément sélectionné par l'utilisateur dans la liste des professeurs
	 */
	public String getSelectionner() 
	{
		if (this.getjListeProfesseurs().getSelectedValue() != null) 
			return this.getjListeProfesseurs().getSelectedValue();
		
		return "Tous les professeurs";
	}
	
	/**
	 * @return Le fichier excel sélectionné dans l'explorateur de fichiers
	 */
	public JFileChooser getFileChooser() {
		return this.fileChooser;
	}
	
	/**
	 * @return Le dossier sélectionné dans l'explorateur de dossiers
	 */
	public JFileChooser getFileChooserDirectory() {
		return this.fileChooserdirectory;
	}
	
	/**
	 * Met à jour les professeurs de la liste des professeurs
	 * 
	 * @param La nouvelle liste des professeurs
	 */
	private void setjListeProfesseurs(List<String> liste) 
	{
		// On vide l'ancienne liste et on la remplace par la nouvelle
		this.model.clear();
		this.model.addElement("Tous les professeurs");
		
    	for(String s : liste) 
    		this.model.addElement(s);
	}
	
	/**
	 * Raffraîchit la liste des professeurs visible sur l'interface graphique
	 */
	public void rafraichirListe() 
	{
		this.model.clear();

		try {
			Excel excel = new Excel(this.getTextField(), "");
			
			String directory = this.getTextField();
		
			int index = directory.lastIndexOf("planning-");
			String tmp = directory.substring(0, index);
			this.setTextFieldDirectory(tmp);
			
			this.setjListeProfesseurs(excel.getProfesseurs());
			this.jListeProfesseurs.setSelectedIndex(0);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}