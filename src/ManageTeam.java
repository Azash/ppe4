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
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


 class ManageTeam extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollTeamList = new JScrollPane();
	private JButton butAddTeam = new JButton("Ajouter une équipe");
	private JButton butDelTeam = new JButton("Supprimer l'équipe sélèctionnée");
	private JButton butSave = new JButton("Sauver");
	private JTextField textBox = new JTextField("", 10);		//textBox.addKeyListener(new KeyAdapter() {public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) System.out.println("rien"); }});
	private JList<String> teamList = new JList<String>(Gvar.listData);
	private boolean isAlreadySaved = true; // Est ce que la liste des équipes a subie une modification ? 
	private boolean isRemoved = false;
	private boolean ThereIsColon = false;
	private boolean ThereIsDoublon = false;
	
	//Correspond au numéro d'index du JList (teamList) sélèctionné (par default -1 car rien n'est sélèctionné)
	private int idSelected = -1;
	
	public ManageTeam() {
		this.setLayout(null);
		
		//Configuration de la listbox affichant les équipes
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		scrollTeamList.setViewportView(teamList);
		
		textBox.addActionListener(this);
		butAddTeam.addActionListener(this);
		teamList.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		butDelTeam.addActionListener(this);
		butSave.addActionListener(this);
		
		this.add(textBox);
		this.add(butAddTeam);
		this.add(scrollTeamList);
		this.add(butDelTeam);
		this.add(butSave);
		
		objSetBounds();//Positionne les objets dans le panel
		
		parseTxtFile(); // Si le fichier ListeDesEquipes.txt existe déjà je l'importe
		if (ThereIsDoublon)
			JOptionPane.showMessageDialog(this, "<html>Il semble qu'il y ai des doublons<br />Les doubons n'ont donc pas été pris en compte.</html>", "Fichier " + Gvar.FILE_NAME_TEAM + " altéré", JOptionPane.ERROR_MESSAGE);
		if (ThereIsColon)
			JOptionPane.showMessageDialog(this, "<html>Une ou plusieurs équipes semble contenir le caractère \":\"<br />Elles n'ont pas été prisent en compte.</html>", "Fichier " + Gvar.FILE_NAME_TEAM + " altéré", JOptionPane.ERROR_MESSAGE);
	}
	
	public void objSetBounds() {
		textBox.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		butAddTeam.setBounds(Gvar.MARGE, textBox.getY() + textBox.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		scrollTeamList.setBounds(butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE, Gvar.MARGE, Gvar.ONG_WIDTH - (butAddTeam.getX() + butAddTeam.getWidth() + Gvar.MARGE) - Gvar.MARGE, Gvar.ONG_HEIGHT - ((3 * Gvar.MARGE) + Gvar.BUT_HEIGHT + Gvar.ONG_HEADER_HEIGHT));
		butDelTeam.setBounds(scrollTeamList.getX(), scrollTeamList.getY() + scrollTeamList.getHeight() + Gvar.MARGE, scrollTeamList.getWidth(), Gvar.BUT_HEIGHT);
		butSave.setBounds(Gvar.MARGE, butAddTeam.getY() + butAddTeam.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		
	}
	
	/*public void paintComponent(Graphics g) {
		else if (StrToCheck.contains(":")) {
				ThereIsColon = true;
				return true;
			}
	}*/
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
			if ((e.getSource() == butAddTeam || e.getSource() == textBox) && !textBox.getText().equals("")) { // BOUTON AJOUTE CLIQUE
				if (!(ThereIsDoublon = isDoublon(textBox.getText().toString())) && !(ThereIsColon = isColon(textBox.getText().toString()))) {
					isAlreadySaved = false;
					Gvar.listData.addElement(textBox.getText().toString());
					textBox.setText("");
					textBox.requestFocus();
				}
				else if (ThereIsDoublon)
					JOptionPane.showMessageDialog(this, "Cette equipe existe déjà !", "Doublon", JOptionPane.ERROR_MESSAGE);
				else if (ThereIsColon)
					JOptionPane.showMessageDialog(this, "Impossible d'ajouter une équipe ayant la caractère \":\" dans son nom", "Nom d'équipe incorrect", JOptionPane.ERROR_MESSAGE);
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
			else if (e.getSource() == butSave && Gvar.listData.getSize() > 0) // BOUTON SAUVEGARDE CLIQUE
				saveInTxtFile();
	}
	
	//Declenché lorsque un element different de la liste est selectionné
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == teamList) {
			// En cas d'erreur ou de non selection retourne -1
			// Donc initialise idSelected a -1 a chaque fois qu'on supprime un element car plus rien est selectionné
			if (!isRemoved)
				idSelected = teamList.getSelectedIndex(); 
		}
	}
	
	public void saveInTxtFile() {
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
	
	private void parseTxtFile() {
		File f = new File("./" + Gvar.FILE_NAME_TEAM);
		if(f.exists() && !f.isDirectory()) {
			String Line;
			int i = 0;
			BufferedReader Buffer;
			try {
				Buffer = new BufferedReader(new InputStreamReader(new FileInputStream(f))); 
				while((Line = Buffer.readLine()) != null) {
					if (!Line.isEmpty() && !isDoublon(Line) && !isColon(Line)) {
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
			if (StrToCheck.equals(Gvar.listData.get(i).toString())) {
				ThereIsDoublon = true;
				return true;
			}
			i++;
		}
		return false;
	}
	
	private boolean isColon(String StrToCheck) {
		if (StrToCheck.contains(":")) {
			ThereIsColon = true;
			return true;
		}
		return false;
	}
	
	public boolean getIsAlreadySaved() {
		return isAlreadySaved;
	}
}
