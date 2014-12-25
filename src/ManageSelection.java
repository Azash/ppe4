import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class ManageSelection extends JPanel implements ActionListener, ListSelectionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadMenu;
	private JButton butSelectTeam;
	private JButton butUnselectTeam;
	private JButton butLoadClose;
	private JScrollPane scrollTeamList;
	private JScrollPane scrollTeamSelected;
	private JList teamList;
	private JList teamSelected;
	private JLabel labTeamList;
	private JLabel labTeamSelected;
	private DefaultListModel<String> listTeam = new DefaultListModel<String>();
	private DefaultListModel<String> listSelected = new DefaultListModel<String>();
	private int idSelected = -1;
	private int idTeam = -1;
	
	public ManageSelection() {
		this.setLayout(null);
		
		//Initialisation du label equipes dispos
		labTeamList = new JLabel("Liste des équipes disponibles :");
		labTeamList.setBounds(Gvar.MARGE, Gvar.MARGE, (Gvar.FEN_WIDTH - ((4 * Gvar.MARGE) + 20)) / 2 - Gvar.MARGE, Gvar.MARGE);
		this.add(labTeamList);
		
		//Initialisation de la listbox affichant les équipes
		teamList = new JList(listTeam);
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
		teamSelected = new JList(listSelected);
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
		for (int i = 0; i < Gvar.listData.getSize(); i++)
			listTeam.addElement(Gvar.listData.get(i).toString());
	}
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butSelectTeam && idTeam != -1) {
			listSelected.addElement(listTeam.get(idTeam).toString());
			listTeam.remove(idTeam);
			idTeam = -1;
		}
		else if (e.getSource() == butUnselectTeam && idSelected != -1) {
			listTeam.addElement(listSelected.get(idSelected).toString());
			listSelected.remove(idSelected);
			idSelected = -1;
		}
		else if (e.getSource() == butLoadMenu) {
			Main.fen.getCl().show(Main.fen.getGlobalPan(), Gvar.BUT_STR_LOAD_Menu);
			Gvar.CUR_PAN = Gvar.BUT_STR_LOAD_Menu;
		}
		else if (e.getSource() == butLoadClose) { // BOUTON SAUVEGARDE CLIQUE
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
		Rectangle r =  ((JList) e.getSource()).getCellBounds(0, ((JList) e.getSource()).getLastVisibleIndex()); // Pour etre sur qu'il clique sur un des elements et non pas sur rien
		if (e.getClickCount() == 2 && r != null && r.contains(e.getPoint())) {
			if (e.getSource() == teamList) {
				int index = teamList.locationToIndex(e.getPoint());
				listSelected.addElement(listTeam.get(index).toString());
				listTeam.remove(index);
			}
			else if (e.getSource() == teamSelected) {
				int index = teamSelected.locationToIndex(e.getPoint());
				listTeam.addElement(listSelected.get(index).toString());
				listSelected.remove(index);
			}
		}
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
