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
import javax.swing.ImageIcon;
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
	
	private JButton butAddTeam = new JButton("Ajouter une �quipe");
	private JButton butDelTeam = new JButton("Supprimer l'�quipe s�l�ctionn�e");
	private JButton butSave = new JButton("Sauver");
	private JTextField textBox = new JTextField("", 10);		//textBox.addKeyListener(new KeyAdapter() {public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) System.out.println("rien"); }});
	private JList<String> teamList = new JList<String>(Gvar.listData);
	private boolean isAlreadySaved = true; // Est ce que la liste des �quipes a subie une modification ? 
	private boolean isRemoved = false;
	private boolean ThereIsColon = false;
	private boolean ThereIsDoublon = false;
	
	//Correspond au num�ro d'index du JList (teamList) s�l�ctionn� (par default -1 car rien n'est s�l�ctionn�)
	private int idSelected = -1;
	
	protected ImageIcon createImageIcon(String path,
            String description) {
java.net.URL imgURL = getClass().getResource(path);
if (imgURL != null) {
return new ImageIcon(imgURL, description);
} else {
System.err.println("Couldn't find file: " + path);
return null;
}
}
	
	public ManageTeam() {
		this.setLayout(null);
		
		//Configuration de la listbox affichant les �quipes
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		scrollTeamList.setViewportView(teamList);
		
		//butAddTeam.setBorder(new RoundedBorder(5));
		/*butAddTeam.setBackground(null);
		butAddTeam.setContentAreaFilled(false); 
		butAddTeam.setHorizontalTextPosition(SwingConstants.CENTER);
		butAddTeam.setIcon(createImageIcon("ButUnClick2.png", "toto"));
		butAddTeam.setBorderPainted(false);*/
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
		
		parseTxtFile(); // Si le fichier ListeDesEquipes.txt existe d�j� je l'importe
		if (ThereIsDoublon)
			JOptionPane.showMessageDialog(this, "<html>Il semble qu'il y ai des doublons<br />Les doubons n'ont donc pas �t� pris en compte.</html>", "Fichier " + Gvar.FILE_NAME_TEAM + " alt�r�", JOptionPane.ERROR_MESSAGE);
		if (ThereIsColon)
			JOptionPane.showMessageDialog(this, "<html>Une ou plusieurs �quipes semble contenir le caract�re \":\"<br />Elles n'ont pas �t� prisent en compte.</html>", "Fichier " + Gvar.FILE_NAME_TEAM + " alt�r�", JOptionPane.ERROR_MESSAGE);
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
	
	//Declench� lorsque un bouton est cliqu�
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
					JOptionPane.showMessageDialog(this, "Cette equipe existe d�j� !", "Doublon", JOptionPane.ERROR_MESSAGE);
				else if (ThereIsColon)
					JOptionPane.showMessageDialog(this, "Impossible d'ajouter une �quipe ayant la caract�re \":\" dans son nom", "Nom d'�quipe incorrect", JOptionPane.ERROR_MESSAGE);
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
	
	public void saveInTxtFile() {
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
