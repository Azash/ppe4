//import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	private boolean isAlreadySaved = true; // Est ce que la liste des équipes a subie une modification ? 
	
	//liste qui va contenir les équipes. Elle est affiché dans le JList (teamList)
	//Et c'est sur cette liste (listData) qu'on fait des ajouts ou des suppressions, PAS SUR la JList (teamList)
	//public DefaultListModel<String> listData = new DefaultListModel<String>(); 
	
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
		teamList = new JList(Gvar.listData);
		teamList.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		teamList.setBounds(butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE, Gvar.MARGE, Gvar.FEN_WIDTH - (butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE) - Gvar.MARGE, Gvar.FEN_HEIGHT - ((3 * Gvar.MARGE) + Gvar.BUT_HEIGHT) - 25);
		this.add(teamList);
		
		//Initialisation du bouton de suppression d'équipe
		butDelTeam = new JButton("Supprimer l'équipe sélèctionnée");
		butDelTeam.addActionListener(this);
		butDelTeam.setBounds(butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE, teamList.getY() + teamList.getHeight() + Gvar.MARGE, teamList.getWidth(), Gvar.BUT_HEIGHT);
		this.add(butDelTeam);
		
		//Initialisation bouton sauver
		butSave = new JButton("Sauver");
		butSave.addActionListener(this);
		butSave.setBounds(Gvar.MARGE, butAddTeam.getY() + butAddTeam.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butSave);
		
		//Initialisation bouton retour au menu
		butLoadMenu = new JButton(Gvar.BUT_STR_LOAD_Menu);
		butLoadMenu.addActionListener(this);
		butLoadMenu.setBounds(Gvar.MARGE, butDelTeam.getY(), Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadMenu);
		
		parseTxtFile(); // Si le fichier ListeDesEquipes existe déjà je l'importe
	}
	
	/*public void paintComponent(Graphics g) {
		
	}*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
			if ((e.getSource() == butAddTeam || e.getSource() == textBox) && !textBox.getText().equals("")) { // BOUTON AJOUTE CLIQUE
				isAlreadySaved = false;
				Gvar.listData.addElement(textBox.getText().toString());
				textBox.setText("");
				textBox.requestFocus();
			}
			else if (e.getSource() == butDelTeam && idSelected >= 0) { // BOUTON SUPPRIMER CLIQUE
				isAlreadySaved = false;
				//removeElementAt declenche la fonction valueChanged de la JList (teamList)
				Gvar.listData.remove(idSelected);
				textBox.requestFocus();
			}
			else if (e.getSource() == butSave && Gvar.listData.getSize() > 0) { // BOUTON SAUVEGARDE CLIQUE
				// Generé automatiquement : gestion des erreurs. Cependant ca ne devrai pas posé probleme :
				// si le fichier existe ca l'écrase, si il existe pas ca le crée
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(Gvar.FILE_NAME_TEAM, "UTF-8");
				} catch (FileNotFoundException | UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				if (!writer.equals(null)) {
					for(int i = 0; i < Gvar.listData.getSize(); i++)
						writer.println(Gvar.listData.get(i).toString());
					writer.close();
					isAlreadySaved = true;
				}
			}
			else if (e.getSource() == butLoadMenu) { // BOUTON SAUVEGARDE CLIQUE
				Main.fen.getCl().show(Main.fen.getGlobalPan(), Gvar.BUT_STR_LOAD_Menu);
				Gvar.CUR_PAN = Gvar.BUT_STR_LOAD_Menu;
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
	
	private void parseTxtFile() {
		File f = new File("./" + Gvar.FILE_NAME_TEAM);
		if(f.exists() && !f.isDirectory()) {
			String Line;
			int i = 0;
			BufferedReader Buffer;
			try {
				Buffer = new BufferedReader(new InputStreamReader(new FileInputStream(f))); 
				while((Line = Buffer.readLine()) != null) {
					if (Line != "") {
						Gvar.listData.add(i, Line);
						i++;
					}
				}
			} catch (IOException e) { } 
		}
	}
	
	public boolean getIsAlreadySaved() {
		return isAlreadySaved;
	}
}
