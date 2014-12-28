import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Fen extends JFrame {
	private static final long serialVersionUID = 1L;
	final JFrame Gfen = this;
	
	private Onglets TabOnglets = new Onglets();
	private JPanel GlobalPan = new JPanel();
	
	public Fen() {
		GlobalPan.setLayout(null);
		GlobalPan.add(TabOnglets); //Ajout du JTabbedPane eu panel Global
		this.add(GlobalPan); //Ajout du panel global a la fenetre
		this.setTitle(Gvar.FEN_TITLE);
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
		    	// Liste de la gestion d'equipe modifiee
		    	if (Gvar.listData.getSize() > 0 && !TabOnglets.getPanManageTeam().getIsAlreadySaved()) {
			        int confirm = JOptionPane.showOptionDialog(Gfen,
			                "Souhaitez vous sauvegarder la liste des équipes avant de quitter ?\nCette action ecrasera \"" + Gvar.FILE_NAME_TEAM + "\"",
			                "Save Confirmation", JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null, null, null);
			        if (confirm == JOptionPane.YES_OPTION)
			        	TabOnglets.getPanManageTeam().saveInTxtFile();
		    	}
		    	// Liste de la gestion de la selection modifiee
		    	if (Gvar.listSelected.getSize() > 0 && !TabOnglets.getPanManageSelection().getIsAlreadySaved()) {
			        int confirm = JOptionPane.showOptionDialog(Gfen,
			                "Souhaitez vous sauvegarder la liste des équipes selectionnées avant de quitter ?\nCette action ecrasera \"" + Gvar.FILE_NAME_TEAM_SELECTED + "\"",
			                "Save Confirmation", JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null, null, null);
			        if (confirm == JOptionPane.YES_OPTION)
			        	TabOnglets.getPanManageSelection().saveInTxtFile();
		    	}
		    	// listes de la gestion des poules
		    	if (TabOnglets.getPanManagePoules().getListTeam().size() > 0 && !TabOnglets.getPanManagePoules().getIsAlreadySaved()) {
			        int confirm = JOptionPane.showOptionDialog(Gfen,
			                "Souhaitez vous sauvegarder la liste des poules avant de quitter ?\nCette action ecrasera \"" + Gvar.FILE_NAME_POULES + "\"",
			                "Save Confirmation", JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null, null, null);
			        if (confirm == JOptionPane.YES_OPTION)
			        	TabOnglets.getPanManageSelection().saveInTxtFile();
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

	public Onglets getOnglets() {
		return TabOnglets;
	}
	
//----------------------------------------------------------------------------------------------------------------------	
}