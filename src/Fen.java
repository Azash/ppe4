import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Fen extends JFrame {
	private static final long serialVersionUID = 1L;
	final JFrame Gfen = this;
	private ManageTeam panManageTeam;
	private Menu panMenu;
	private CardLayout cl = new CardLayout();
	private JPanel GlobalPan = new JPanel(cl); //Creation d'une sorte d'ensemble de Panel
	private final String T_Menu = "Menu";
	private final String T_ManageTeam = "Gestion d'équipe";
	
	public Fen() {
		panManageTeam = new ManageTeam(); // Panel Gestion des Equipe
		panMenu = new Menu(); // Panel Menu
		
		
		GlobalPan.add(panMenu, T_Menu); // J'ajoute panMenu a l'ensemble des panel
		GlobalPan.add(panManageTeam, T_ManageTeam); // J'ajoute panManageTeam a l'ensemble des panel
		cl.show(GlobalPan , T_Menu); // Je selectionne le panel Menu
		this.add(GlobalPan); //Panel chargé dans la fenetre à l'ouverture du programme (donc un ensemble de Panel actuellement c'est T_Menu que j'affiche cf ligne d'au dessus)
		
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
		    	DefaultListModel<String> TmpList = panManageTeam.getListData(); // Je recupere la list des equipes du JPanel panManageTeam
		    	if (/*Gvar.CUR_PAN == T_ManageTeam &&*/ TmpList.getSize() > 0) {
			        int confirm = JOptionPane.showOptionDialog(Gfen,
			                "Souhaitez vous sauvegarder la liste des équipe avant de quitter ?\nCette action ecrasera \"ListeDesEquipes.txt\"",
			                "Save Confirmation", JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null, null, null);
			        if (confirm == JOptionPane.YES_OPTION) {
							PrintWriter writer = null; // Ce bout de code est expliqué dans ManageTeam.java car copié collé
							try {
								writer = new PrintWriter("ListeDesEquipes.txt", "UTF-8");
							} catch (FileNotFoundException | UnsupportedEncodingException e1) {
								e1.printStackTrace();
							}
							if (!writer.equals(null)) {
								for(int i = 0; i < TmpList.getSize(); i++)
									writer.println(TmpList.get(i).toString());
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
	
	public String getTitleMenu() {
		return T_Menu;
	}
	
	public String getTitleManageTeam() {
		return T_ManageTeam;
	}
//----------------------------------------------------------------------------------------------------------------------	
}