//import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


 class ManageTeam extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JButton butAddTeam;
	private JButton butDelTeam;
	private JButton butLoadMenu;
	private JButton butSave;
	private JTextField textBox;
	private JList teamList;
	
	//liste qui va contenir les équipes. Elle est affiché dans le JList (teamList)
	//Et c'est sur cette liste (listData) qu'on fait des ajouts ou des suppressions, PAS SUR la JList (teamList)
	private DefaultListModel<String> listData = new DefaultListModel<String>(); 
	
	//Correspond au numéro d'index du JList (teamList) sélèctionné (par default -1 car rien n'est sélèctionné)
	private int idSelected = -1;
	
	public ManageTeam() {
		this.setLayout(null);
		
		//Initialisation de la zone de saisie
		textBox = new JTextField("", 10);		//textBox.addKeyListener(new KeyAdapter() {public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) System.out.println("rien"); }});
		textBox.addActionListener(this);
		textBox.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(textBox);
		
		//Initialisation du bouton d'ajout d'équipe
		butAddTeam = new JButton("Ajouter une équipe");
		butAddTeam.addActionListener(this);
		butAddTeam.setBounds(Gvar.MARGE, textBox.getY() + textBox.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butAddTeam);
		
		//Initialisation de la listbox affichant les équipes
		teamList = new JList(listData);
		System.out.println((2 * Gvar.MARGE) + butAddTeam.getWidth());
		System.out.println(Gvar.FEN_HEIGHT - (2 * Gvar.MARGE));
		teamList.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		teamList.setBounds(butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE, Gvar.MARGE, Gvar.FEN_WIDTH - (butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE) - Gvar.MARGE, Gvar.FEN_HEIGHT - ((3 * Gvar.MARGE) + Gvar.BUT_HEIGHT) - 25);
		this.add(teamList);
		
		//Initialisation du bouton de suppression d'équipe
		butDelTeam = new JButton("Supprimer l'équipe sélèctionnée");
		butDelTeam.addActionListener(this);
		butDelTeam.setBounds(butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE, teamList.getY() + teamList.getHeight() + Gvar.MARGE, teamList.getWidth(), Gvar.BUT_HEIGHT);
		this.add(butDelTeam);
		
		butSave = new JButton("Sauvegarder");
		butSave.addActionListener(this);
		butSave.setBounds(Gvar.MARGE, butAddTeam.getY() + butAddTeam.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butSave);
		
		butLoadMenu = new JButton("Revenir au menu");
		butLoadMenu.addActionListener(this);
		butLoadMenu.setBounds(Gvar.MARGE, butSave.getY() + butSave.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadMenu);
	}
	
	/*public void paintComponent(Graphics g) {
		
	}*/
	
	public DefaultListModel<String> getListData() {
		return listData;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			if ((e.getSource() == butAddTeam || e.getSource() == textBox) && !textBox.getText().equals("")) { // BOUTON AJOUTE CLIQUE
				listData.addElement(textBox.getText().toString());
				textBox.setText("");
				textBox.requestFocus();
			}
			else if (e.getSource() == butDelTeam && idSelected >= 0) { // BOUTON SUPPRIMER CLIQUE
					//removeElementAt declenche la fonction valueChanged de la JList (teamList)
					listData.remove(idSelected);
					textBox.requestFocus();
			}
			else if (e.getSource() == butSave && listData.getSize() > 0) { // BOUTON SAUVEGARDE CLIQUE
				// Generé automatiquement : gestion des erreurs. Cependant ca ne devrai pas posé probleme :
				// si le fichier existe ca l'écrase, si il existe pas ca le crée
				PrintWriter writer = null;
				try {
					writer = new PrintWriter("ListeDesEquipes.txt", "UTF-8");
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				if (!writer.equals(null)) {
					for(int i = 0; i < listData.getSize(); i++)
						writer.println(listData.get(i).toString());
					writer.close();
				}
			}
			else if (e.getSource() == butLoadMenu) { // BOUTON SAUVEGARDE CLIQUE
				Main.fen.getCl().show(Main.fen.getGlobalPan(), Main.fen.getTitleMenu());
				Gvar.CUR_PAN = Main.fen.getTitleMenu();
			}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == teamList) {
			// En cas d'erreur ou de non selection retourne -1
			// Donc initialise idSelected a -1 a chaque fois qu'on supprime un element car plus rien est selectionné
			idSelected = teamList.getSelectedIndex(); 
		}
	}
}
