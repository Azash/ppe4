import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JLabel;
import javax.swing.JList;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ManageSelection extends JPanel implements ActionListener, ListSelectionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadMenu;
	private JButton butSelectTeam;
	private JButton butUnselectTeam;
	private JButton butLoadClose;
	private JButton butSave;
	private JScrollPane scrollTeamList;
	private JScrollPane scrollTeamSelected;
	private JList<String> teamList;
	private JList<String> teamSelected;
	private JLabel labTeamList;
	private JLabel labTeamSelected;
	private DefaultListModel<String> listTeam = new DefaultListModel<String>();
	//public DefaultListModel<String> listSelected = new DefaultListModel<String>();
	private int idSelected = -1;
	private int idTeam = -1;
	private boolean isAlreadySaved = true;
	
	public ManageSelection() {
		this.setLayout(null);
		
		//Initialisation du label equipes dispos
		labTeamList = new JLabel("Liste des équipes disponibles :");
		labTeamList.setBounds(Gvar.MARGE, Gvar.MARGE, (Gvar.FEN_WIDTH - ((4 * Gvar.MARGE) + 20)) / 2 - Gvar.MARGE, Gvar.LAB_HEIGHT);
		this.add(labTeamList);
		
		//Initialisation de la listbox affichant les équipes
		teamList = new JList<String>(listTeam);
		teamList.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamList.addMouseListener(this);
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		scrollTeamList = new JScrollPane();
		scrollTeamList.setViewportView(teamList);
		scrollTeamList.setBounds(Gvar.MARGE, labTeamList.getY() + labTeamList.getHeight() /*+ Gvar.MARGE*/, labTeamList.getWidth(), Gvar.FEN_HEIGHT - (Gvar.FOOTER_HEIGHT + (2 * Gvar.MARGE) + labTeamList.getHeight()));
		this.add(scrollTeamList);
		
		//Initialisation du bouton d'ajout a la liste des equipes selectionnee
		butSelectTeam = new JButton(">");
		butSelectTeam.addActionListener(this);
		butSelectTeam.setBounds(scrollTeamList.getX() + scrollTeamList.getWidth() + Gvar.MARGE, scrollTeamList.getY(), 50, Gvar.BUT_HEIGHT);
		this.add(butSelectTeam);
		
		//Initialisation du bouton de suppression de la liste des equipes selectionnee
		butUnselectTeam = new JButton("<");
		butUnselectTeam.addActionListener(this);
		butUnselectTeam.setBounds(butSelectTeam.getX(), butSelectTeam.getY() + butSelectTeam.getHeight() + Gvar.MARGE, butSelectTeam.getWidth(), butSelectTeam.getHeight());
		this.add(butUnselectTeam);
		
		//Initialisation du label equipes selectionnees
		labTeamSelected = new JLabel("Liste des équipes sélèctionnées :");
		labTeamSelected.setBounds(butSelectTeam.getX() + butSelectTeam.getWidth() + Gvar.MARGE, Gvar.MARGE, labTeamList.getWidth(), labTeamList.getHeight());
		this.add(labTeamSelected);
		
		//Initialisation de la listbox affichant les équipes SELECTIONNEES
		teamSelected = new JList<String>(Gvar.listSelected);
		teamSelected.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamSelected.addMouseListener(this);
		teamSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		scrollTeamSelected = new JScrollPane();
		scrollTeamSelected.setViewportView(teamSelected);
		scrollTeamSelected.setBounds(butSelectTeam.getX() + butSelectTeam.getWidth() + Gvar.MARGE, scrollTeamList.getY(), labTeamSelected.getWidth(), scrollTeamList.getHeight());
		this.add(scrollTeamSelected);

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
		butSave = new JButton("Sauver");
		butSave.addActionListener(this);
		butSave.setBounds(butLoadClose.getX() + butLoadClose.getWidth() + Gvar.MARGE, butLoadMenu.getY(), Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butSave);
		
		parseTxtFile(); // Si le fichier ListeDesEquipesSelectionnees.txt existe déjà je l'importe
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
		else if (e.getSource() == butSave && Gvar.listSelected.getSize() > 0) {
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
		else if (e.getSource() == butLoadMenu) {
			Main.fen.getCl().show(Main.fen.getGlobalPan(), Gvar.BUT_STR_LOAD_Menu);
			Gvar.CUR_PAN = Gvar.BUT_STR_LOAD_Menu;
			Main.fen.setTitle(Gvar.CUR_PAN);
		}
		else if (e.getSource() == butLoadClose) {
			Main.fen.dispatchEvent(new WindowEvent(Main.fen, WindowEvent.WINDOW_CLOSING));
		}
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
	
	private void parseTxtFile() {
		File f = new File("./" + Gvar.FILE_NAME_TEAM_SELECTED);
		if(f.exists() && !f.isDirectory()) {
			String Line;
			int i = 0;
			BufferedReader Buffer;
			try {
				Buffer = new BufferedReader(new InputStreamReader(new FileInputStream(f))); 
				while((Line = Buffer.readLine()) != null) {
					if (Line != "" && !isDoublon(Line)) {
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
			if (StrToCheck.equals(Gvar.listSelected.get(i).toString()))
				return true;
			i++;
		}
		return false;
	}
	
	public void setListTeam() {
		listTeam.clear();
		for (int i = 0; i < Gvar.listData.getSize(); i++) {
			if (!isDoublonFromSelected(Gvar.listData.getElementAt(i).toString()))
				listTeam.addElement(Gvar.listData.getElementAt(i).toString());
		}
	}
	
	private boolean isDoublonFromSelected(String StrToCheck) {
		int i = 0;
		while (i < Gvar.listSelected.getSize()) {
			if (StrToCheck.equals(Gvar.listSelected.get(i).toString()))
				return true;
			i++;
		}
		return false;
	}
	
	public DefaultListModel<String> getlistSelected() {
		return Gvar.listSelected;
	}
	
	public boolean getIsAlreadySaved() {
		return isAlreadySaved;
	}
}
