//import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


 class ManageTeam extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollTeamList;
	private JButton butAddTeam;
	private JButton butDelTeam;
	private JButton butLoadMenu;
	private JButton butLoadClose;
	private JButton butSave;
	private JTextField textBox;
	private JList teamList;
	private boolean isAlreadySaved = true; // Est ce que la liste des �quipes a subie une modification ? 
	private boolean isRemoved = false;
	//liste qui va contenir les �quipes. Elle est affich� dans le JList (teamList)
	//Et c'est sur cette liste (listData) qu'on fait des ajouts ou des suppressions, PAS SUR la JList (teamList)
	//public DefaultListModel<String> listData = new DefaultListModel<String>(); 
	
	//Correspond au num�ro d'index du JList (teamList) s�l�ctionn� (par default -1 car rien n'est s�l�ctionn�)
	private int idSelected = -1;
	
	public ManageTeam() {
		this.setLayout(null);
		
		//Initialisation de la zone de saisie
		textBox = new JTextField("", 10);		//textBox.addKeyListener(new KeyAdapter() {public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) System.out.println("rien"); }});
		textBox.addActionListener(this);
		textBox.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(textBox);
		
		//Initialisation du bouton d'ajout d'�quipe
		butAddTeam = new JButton("Ajouter une �quipe");
		butAddTeam.addActionListener(this);
		butAddTeam.setBounds(Gvar.MARGE, textBox.getY() + textBox.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butAddTeam);
			
		//Initialisation de la listbox affichant les �quipes
		teamList = new JList(Gvar.listData);
		teamList.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		scrollTeamList = new JScrollPane();
		scrollTeamList.setViewportView(teamList);
		scrollTeamList.setBounds(butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE, Gvar.MARGE, Gvar.FEN_WIDTH - (butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE) - Gvar.MARGE, Gvar.FEN_HEIGHT - ((3 * Gvar.MARGE) + Gvar.BUT_HEIGHT + Gvar.FOOTER_HEIGHT));
		this.add(scrollTeamList);
		
		//Initialisation du bouton de suppression d'�quipe
		butDelTeam = new JButton("Supprimer l'�quipe s�l�ctionn�e");
		butDelTeam.addActionListener(this);
		butDelTeam.setBounds(scrollTeamList.getX(), scrollTeamList.getY() + scrollTeamList.getHeight() + Gvar.MARGE, scrollTeamList.getWidth(), Gvar.BUT_HEIGHT);
		this.add(butDelTeam);
		
		//Initialisation bouton sauver
		butSave = new JButton("Sauver");
		butSave.addActionListener(this);
		butSave.setBounds(Gvar.MARGE, butAddTeam.getY() + butAddTeam.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butSave);
		
//-----------------------------------------------------------------------------------------
//ZONE A COPIER/COLLER POUR METTRE LES BOUTONS Menu ET Fermer DANS UN PANEL (Ne pas oublier le code dans actionPerformed et  de creer les 2 variables : JButton butLoadMenu, JButton butLoadClose)
//-----------------------------------------------------------------------------------------
		//Initialisation du bouton pour revenir au menu
		butLoadMenu = new JButton(Gvar.BUT_STR_LOAD_Menu);
		butLoadMenu.addActionListener(this);
		butLoadMenu.setBounds(Gvar.MARGE, Gvar.FEN_HEIGHT - Gvar.FOOTER_HEIGHT, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadMenu);
		
		//Initialisation du bouton pour quitter le programme
		butLoadClose = new JButton(Gvar.BUT_STR_LOAD_Close);
		butLoadClose.addActionListener(this);
		butLoadClose.setBounds(butLoadMenu.getX() + butLoadMenu.getWidth() + Gvar.MARGE, butLoadMenu.getY(), Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadClose);
//-----------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------
		
		parseTxtFile(); // Si le fichier ListeDesEquipes.txt existe d�j� je l'importe
	}
	
	/*public void paintComponent(Graphics g) {
		
	}*/
	
	//Declench� lorsque un bouton est cliqu�
	@Override
	public void actionPerformed(ActionEvent e) {
			if ((e.getSource() == butAddTeam || e.getSource() == textBox) && !textBox.getText().equals("")) { // BOUTON AJOUTE CLIQUE
				if (!isDoublon(textBox.getText().toString())) {
					isAlreadySaved = false;
					Gvar.listData.addElement(textBox.getText().toString());
					textBox.setText("");
					textBox.requestFocus();
				}
				else
					JOptionPane.showMessageDialog(this, "Cette equipe existe d�j� !");
			}
			else if (e.getSource() == butDelTeam && idSelected >= 0) { // BOUTON SUPPRIMER CLIQUE
				isAlreadySaved = false;
				isRemoved = true;
				//removeElementAt declenche la fonction valueChanged de la JList (teamList)
				Gvar.listData.remove(idSelected);
				if (idSelected < Gvar.listData.getSize())
					teamList.setSelectedIndex(idSelected);
				else if (Gvar.listData.getSize() >= 0) 
					teamList.setSelectedIndex(0);
				else
					textBox.requestFocus();
				isRemoved = false;
				idSelected = teamList.getSelectedIndex(); 
				//teamList.requestFocus();
			}
			else if (e.getSource() == butSave && Gvar.listData.getSize() > 0) { // BOUTON SAUVEGARDE CLIQUE
				// Gener� automatiquement : gestion des erreurs. Cependant ca ne devrai pas pos� probleme :
				// si le fichier existe ca l'�crase, si il existe pas ca le cr�e
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
			else if (e.getSource() == butLoadClose) { // BOUTON SAUVEGARDE CLIQUE
				Main.fen.dispatchEvent(new WindowEvent(Main.fen, WindowEvent.WINDOW_CLOSING));
			}
	}
	
	//Declench� lorsque un element different de la liste est selectionn�
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == teamList) {
			// En cas d'erreur ou de non selection retourne -1
			// Donc initialise idSelected a -1 a chaque fois qu'on supprime un element car plus rien est selectionn�
			if (!isRemoved)
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
					if (Line != "" && !isDoublon(Line)) {
						Gvar.listData.add(i, Line);
						i++;
					}
				}
				Buffer.close();
			} catch (IOException e) { } 
			
		}
	}
	
	private boolean isDoublon(String StrToCheck) {
		int i = 0;
		while (i < Gvar.listData.getSize()) {
			if (StrToCheck.contains(Gvar.listData.get(i).toString()))
				return true;
			i++;
		}
		return false;
	}
	
	public boolean getIsAlreadySaved() {
		return isAlreadySaved;
	}
}
