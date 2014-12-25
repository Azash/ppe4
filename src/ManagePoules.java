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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


 class ManagePoules extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadMenu;
	private JButton butLoadClose;
	
	public ManagePoules() {
		this.setLayout(null);
		
		JRadioButton birdButton = new JRadioButton(birdString);
	    birdButton.setMnemonic(KeyEvent.VK_B);
	    birdButton.setActionCommand(birdString);
	    birdButton.setSelected(true);
		
		/*//Initialisation du label equipes dispos
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
		scrollTeamList.setBounds(Gvar.MARGE, labTeamList.getY() + labTeamList.getHeight(), labTeamList.getWidth(), Gvar.FEN_HEIGHT - (Gvar.FOOTER_HEIGHT + (2 * Gvar.MARGE) + labTeamList.getHeight()));
		this.add(scrollTeamList);*/
		
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
	}
	
	/*public void paintComponent(Graphics g) {
		
	}*/
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
			/*if (e.getSource() == butLoadMenu) {
				
			}
			else*/ if (e.getSource() == butLoadMenu) {
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
		/*if (e.getSource() == teamList) {

		}*/
	}
}
