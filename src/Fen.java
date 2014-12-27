import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Fen extends JFrame {
	private static final long serialVersionUID = 1L;
	final JFrame Gfen = this;
	private ManageTeam panManageTeam = new ManageTeam(); // Panel Gestion des Equipe
	private ManageSelection panManageSelection = new ManageSelection(); // Panel Gestion des Equipe selectionnées pour le tournoi
	private ManagePoules panManagePoules = new ManagePoules(); // Panel Gestion des Poules
	private Menu panMenu = new Menu(); // Panel Menu
	private CardLayout cl = new CardLayout();
	private JPanel GlobalPan = new JPanel(cl); //Creation d'une sorte d'ensemble de Panel
	
	public Fen() {
		GlobalPan.add(panMenu, Gvar.BUT_STR_LOAD_Menu); // J'ajoute panMenu a l'ensemble des panel
		GlobalPan.add(panManageTeam, Gvar.BUT_STR_LOAD_ManageTeam); // J'ajoute panManageTeam a l'ensemble des panel
		GlobalPan.add(panManageSelection, Gvar.BUT_STR_LOAD_ManageSelection); // J'ajoute panManageSelection a l'ensemble des panel
		GlobalPan.add(panManagePoules, Gvar.BUT_STR_LOAD_ManagePoules); // J'ajoute panManagePoules a l'ensemble des panel
		cl.show(GlobalPan , Gvar.BUT_STR_LOAD_Menu); // Je selectionne le panel Menu
		this.add(GlobalPan); //Panel chargé dans la fenetre à l'ouverture du programme (donc un ensemble de Panel actuellement c'est T_Menu que j'affiche cf ligne d'au dessus)
		this.setTitle(Gvar.CUR_PAN);
		// Je parametre deux trois trucs de la fenetre
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setBounds(700, 200, Gvar.FEN_WIDTH, Gvar.FEN_HEIGHT);
//----------------------------------------------------------------------------------------------------------------------		
//---------------------------------------------- DEBUT GESTION DE FERMETURE DE LA FENETRE ------------------------------	
//----------------------------------------------------------------------------------------------------------------------	
		// Ecoute de la fenetre
		WindowListener exitListener = new WindowAdapter() {
			// Sur l'action de fermeture de la fenetre
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	if (/*Gvar.CUR_PAN == T_ManageTeam &&*/ Gvar.listData.getSize() > 0 && !panManageTeam.getIsAlreadySaved()) {
			        int confirm = JOptionPane.showOptionDialog(Gfen,
			                "Souhaitez vous sauvegarder la liste des équipes avant de quitter ?\nCette action ecrasera \"ListeDesEquipes.txt\"",
			                "Save Confirmation", JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null, null, null);
			        if (confirm == JOptionPane.YES_OPTION) {
							PrintWriter writer = null; // Ce bout de code est expliqué dans ManageTeam.java car copié collé
							try {
								writer = new PrintWriter(Gvar.FILE_NAME_TEAM, "UTF-8");
							} catch (FileNotFoundException | UnsupportedEncodingException e1) {
								e1.printStackTrace();
							}
							if (!writer.equals(null)) {
								for(int i = 0; i < Gvar.listData.getSize(); i++)
									writer.println(Gvar.listData.getElementAt(i).toString());
								writer.close();
							}
			        }
			        else {
			        	
			        }
		    	}
		    	if (/*Gvar.CUR_PAN == T_ManageTeam &&*/ Gvar.listSelected.getSize() > 0 && !panManageSelection.getIsAlreadySaved()) {
			        int confirm = JOptionPane.showOptionDialog(Gfen,
			                "Souhaitez vous sauvegarder la liste des équipes selectionnées avant de quitter ?\nCette action ecrasera \"ListeDesEquipesSelectionnees.txt\"",
			                "Save Confirmation", JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null, null, null);
			        if (confirm == JOptionPane.YES_OPTION) {
							PrintWriter writer = null; // Ce bout de code est expliqué dans ManageTeam.java car copié collé
							try {
								writer = new PrintWriter(Gvar.FILE_NAME_TEAM_SELECTED, "UTF-8");
							} catch (FileNotFoundException | UnsupportedEncodingException e1) {
								e1.printStackTrace();
							}
							if (!writer.equals(null)) {
								for(int i = 0; i < Gvar.listSelected.getSize(); i++)
									writer.println(Gvar.listSelected.getElementAt(i).toString());
								writer.close();
							}
			        }
			        else {
			        	
			        }
		    	}
		    }
		};		
		Gfen.addWindowListener(exitListener); // Ajout de l'écoute de le fenetre sur le fenetre
//----------------------------------------------------------------------------------------------------------------------		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
//----------------------------------------------------------------------------------------------------------------------	
//--------------------- ENSEMBLE DE GETTER QUI VONT ME PERMETTRE DE PERMUTER ENTRE LES DIFFERENT PANEL -----------------
//----------------------------------------------------------------------------------------------------------------------	
	public JPanel getGlobalPan() {
		return GlobalPan;
	}
	
	public CardLayout getCl() {
		return cl;
	}
	
	public ManageSelection getPanManageSelection() {
		return panManageSelection;
	}
	
	public ManagePoules getPanManagePoules() {
		return panManagePoules;
	}
	
	/*public JPanel getPanManageTeam() {
			return panManageTeam;
	}*/
//----------------------------------------------------------------------------------------------------------------------	
}