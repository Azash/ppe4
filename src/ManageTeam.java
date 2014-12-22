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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


 class ManageTeam implements ActionListener, ListSelectionListener {
	//private static final long serialVersionUID = 1L;
	private JPanel pan;
	private JButton butAddTeam;
	private JButton butDelTeam;
	private JButton butSave;
	private JTextField textBox;
	private JList teamList;
	
	//liste qui va contenir les équipes. Elle est affiché dans le JList (teamList)
	//Et c'est sur cette liste (listData) qu'on fait des ajouts ou des suppressions, PAS SUR la JList (teamList)
	private DefaultListModel<String> listData = new DefaultListModel<String>(); 
	//Une marge défini pour ne pas réécrire 20 sur les position x, ou y quand on ne veux pas que les boutons etc... soit collés au bord 
	private static final int MARGE = 20;
	private static final int BUT_WIDTH = 160;
	private static final int BUT_HEIGHT = 20;
	
	//Correspond au numéro d'index du JList (teamList) sélèctionné (par default -1 car rien n'est sélèctionné)
	private int idSelected = -1;
	
	public ManageTeam() {
	}
	
	public JPanel getManagePan(int fenWidth, int fenHeight) {
		pan = new JPanel();
		pan.setLayout(null);
		
		//Initialisation de la zone de saisie
		textBox = new JTextField("", 10);		//textBox.addKeyListener(new KeyAdapter() {public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) System.out.println("rien"); }});
		textBox.addActionListener(this);
		textBox.setBounds(MARGE, MARGE, BUT_WIDTH, BUT_HEIGHT);
		pan.add(textBox);
		
		//Initialisation du bouton d'ajout d'équipe
		butAddTeam = new JButton("Ajouter une équipe");
		butAddTeam.addActionListener(this);
		butAddTeam.setBounds(MARGE, 50, BUT_WIDTH, BUT_HEIGHT);
		pan.add(butAddTeam);
		
		//Initialisation de la listbox affichant les équipes
		teamList = new JList(listData);
		System.out.println((2 * MARGE) + butAddTeam.getWidth());
		System.out.println(fenHeight - (2 * MARGE));
		teamList.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		teamList.setBounds(fenWidth - (300 + MARGE), MARGE, 300, fenHeight - (5 * MARGE));
		pan.add(teamList);
		
		//Initialisation du bouton de suppression d'équipe
		butDelTeam = new JButton("Supprimer l'équipe sélèctionnée");
		butDelTeam.addActionListener(this);
		butDelTeam.setBounds(fenWidth - (teamList.getWidth() + MARGE), fenHeight - (3 * MARGE), teamList.getWidth(), BUT_HEIGHT);
		pan.add(butDelTeam);
		
		butSave = new JButton("Sauvegarder");
		butSave.addActionListener(this);
		butSave.setBounds(MARGE, fenHeight - (3 * MARGE), BUT_WIDTH, BUT_HEIGHT);
		pan.add(butSave);
		
		pan.setVisible(true);
		
		return pan;
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
