import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ManageSelection extends JPanel implements ActionListener, ListSelectionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> listTeam = new DefaultListModel<String>();
	private JButton butSelectTeam = new JButton(">");
	private JButton butUnselectTeam = new JButton("<");
	private JButton butSave = new JButton("Sauver");
	private JScrollPane scrollTeamList = new JScrollPane();
	private JScrollPane scrollTeamSelected = new JScrollPane();
	private JList<String> teamList = new JList<String>(listTeam);
	private JList<String> teamSelected = new JList<String>(Gvar.listSelected);
	private JLabel labTeamList = new JLabel("Liste des équipes disponibles :");
	private JLabel labTeamSelected = new JLabel("Liste des équipes sélèctionnées :");
	private int idSelected = -1;
	private int idTeam = -1;
	private boolean isAlreadySaved = true;
	private boolean ThereIsDoublon = false;
	private boolean ThereIsColon = false;
	
	public ManageSelection() {
		this.setLayout(null);
		
		//Initialisation du label equipes dispos
		labTeamList.setBounds(Gvar.MARGE, Gvar.MARGE, (Gvar.ONG_WIDTH - ((4 * Gvar.MARGE) + 50)) / 2, Gvar.LAB_HEIGHT);
		this.add(labTeamList);
		
		//Initialisation de la listbox affichant les équipes
		teamList.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamList.addMouseListener(this);
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		scrollTeamList.setViewportView(teamList);
		scrollTeamList.setBounds(Gvar.MARGE, labTeamList.getY() + labTeamList.getHeight(), labTeamList.getWidth(), Gvar.ONG_HEIGHT - ((3 * Gvar.MARGE) + Gvar.BUT_HEIGHT + Gvar.ONG_HEADER_HEIGHT + labTeamList.getHeight()));
		this.add(scrollTeamList);
		
		//Initialisation du bouton d'ajout a la liste des equipes selectionnee
		butSelectTeam.addActionListener(this);
		butSelectTeam.setBounds(scrollTeamList.getX() + scrollTeamList.getWidth() + Gvar.MARGE, scrollTeamList.getY(), Gvar.BUT_SQUARE_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butSelectTeam);
		
		//Initialisation du bouton de suppression de la liste des equipes selectionnee
		butUnselectTeam.addActionListener(this);
		butUnselectTeam.setBounds(butSelectTeam.getX(), butSelectTeam.getY() + butSelectTeam.getHeight() + Gvar.MARGE, butSelectTeam.getWidth(), butSelectTeam.getHeight());
		this.add(butUnselectTeam);
		
		//Initialisation du label equipes selectionnees
		labTeamSelected.setBounds(butSelectTeam.getX() + butSelectTeam.getWidth() + Gvar.MARGE, Gvar.MARGE, labTeamList.getWidth(), labTeamList.getHeight());
		this.add(labTeamSelected);
		
		//Initialisation de la listbox affichant les équipes SELECTIONNEES
		teamSelected.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamSelected.addMouseListener(this);
		teamSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		scrollTeamSelected.setViewportView(teamSelected);
		scrollTeamSelected.setBounds(butSelectTeam.getX() + butSelectTeam.getWidth() + Gvar.MARGE, scrollTeamList.getY(), labTeamSelected.getWidth(), scrollTeamList.getHeight());
		this.add(scrollTeamSelected);

		butSave.addActionListener(this);
		butSave.setBounds(Gvar.MARGE, scrollTeamSelected.getY() + scrollTeamSelected.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butSave);
		
		parseTxtFile(); // Si le fichier ListeDesEquipesSelectionnees.txt existe déjà je l'importe
		if (ThereIsDoublon)
			JOptionPane.showMessageDialog(this, "<html>Il semble qu'il y ai des doublons<br />Les doubons n'ont donc pas été pris en compte.</html>", "Fichier " + Gvar.FILE_NAME_TEAM_SELECTED + " altéré", JOptionPane.ERROR_MESSAGE);
		if (ThereIsColon)
			JOptionPane.showMessageDialog(this, "<html>Une ou plusieurs équipes semble contenir le caractère \":\"<br />Elles n'ont pas été prisent en compte.</html>", "Fichier " + Gvar.FILE_NAME_TEAM_SELECTED + " altéré", JOptionPane.ERROR_MESSAGE);
	}
	
	public void saveInTxtFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(Gvar.FILE_NAME_TEAM_SELECTED, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (!writer.equals(null)) {
			for(int i = 0; i < Gvar.listSelected.getSize(); i++)
				writer.println(Gvar.listSelected.get(i).toString());
			writer.close();
			isAlreadySaved = true;
		}
	}
	
	private void parseTxtFile() {
		File f = new File("./" + Gvar.FILE_NAME_TEAM_SELECTED);
		if(f.exists() && !f.isDirectory()) {
			String Line;
			int i = 0;
			BufferedReader Buffer;
			try {
				Buffer = new BufferedReader(new InputStreamReader(new FileInputStream(f))); 
				while((Line = Buffer.readLine()) != null) {
					if (!Line.isEmpty() && !isDoublon(Line) && !isColon(Line)) {
						Gvar.listSelected.add(i, Line);
						i++;
					}
				}
				Buffer.close();
			} catch (IOException e) { } 
			
		}
	}
	
	private boolean isDoublon(String StrToCheck) {
		int i = 0;
		while (i < Gvar.listSelected.getSize()) {
			if (StrToCheck.equals(Gvar.listSelected.get(i).toString())) {
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
	
	public void setListTeam() {
		listTeam.clear();
		for (int i = 0; i < Gvar.listData.getSize(); i++) {
			if (!isDoublon(Gvar.listData.getElementAt(i).toString()))
				listTeam.addElement(Gvar.listData.getElementAt(i).toString());
		}
	}
	
	public DefaultListModel<String> getlistSelected() {
		return Gvar.listSelected;
	}
	
	public boolean getIsAlreadySaved() {
		return isAlreadySaved;
	}
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butSelectTeam && idTeam != -1) {
			Gvar.listSelected.addElement(listTeam.get(idTeam).toString());
			listTeam.remove(idTeam);
			idTeam = -1;
			isAlreadySaved = false;
		}
		else if (e.getSource() == butUnselectTeam && idSelected != -1) {
			listTeam.addElement(Gvar.listSelected.get(idSelected).toString());
			Gvar.listSelected.remove(idSelected);
			idSelected = -1;
			isAlreadySaved = false;
		}
		else if (e.getSource() == butSave && Gvar.listSelected.getSize() > 0)
			saveInTxtFile();
	}
	
	//Declenché lorsque un element different de la liste est selectionné
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == teamList)
			idTeam = teamList.getSelectedIndex();
		else if (e.getSource() == teamSelected)
			idSelected = teamSelected.getSelectedIndex(); 
	}

	@Override
    public void mouseClicked(MouseEvent e) {
		/*if (e.getSource() instanceof JList<?>) {
			//JList<String> TmpJList = (JList<String>) e.getSource();*/
			
			if (e.getClickCount() == 2) {
				Rectangle r;
				if (e.getSource() == teamList) {
					//Si je clalcul r deux fois c'est parce qu'autrement j'ai un warning que j'arrive pas a enlever et j'aime pas ca
					r =  teamList.getCellBounds(0, teamList.getLastVisibleIndex()); // Pour etre sur qu'il clique sur un des elements et non pas sur rien
					if (r != null && r.contains(e.getPoint())) {
						int index = teamList.locationToIndex(e.getPoint());
						Gvar.listSelected.addElement(listTeam.get(index).toString());
						listTeam.remove(index);
						isAlreadySaved = false;
					}
				}
				else if (e.getSource() == teamSelected) {
					r =  teamList.getCellBounds(0, teamList.getLastVisibleIndex()); // Pour etre sur qu'il clique sur un des elements et non pas sur rien
					if (r != null && r.contains(e.getPoint())) {
						int index = teamSelected.locationToIndex(e.getPoint());
						listTeam.addElement(Gvar.listSelected.get(index).toString());
						Gvar.listSelected.remove(index);
						isAlreadySaved = false;
					}
				}
			}
		/*}*/
    }

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
