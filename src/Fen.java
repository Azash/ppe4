import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Fen extends JFrame implements ComponentListener {
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
		this.setResizable(true);
		this.setAlwaysOnTop(true);
		this.setSize(Gvar.DEFAULT_FEN_WIDTH, Gvar.DEFAULT_FEN_HEIGHT);
		this.addComponentListener(this);
		SetFrameToCenter();
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
			        	TabOnglets.getPanManagePoules().saveInTxtFile();
		    	}
		    }
		};		
		Gfen.addWindowListener(exitListener); // Ajout de l'écoute de le fenetre sur le fenetre
//----------------------------------------------------------------------------------------------------------------------		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void SetFrameToCenter() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) ((dimension.getWidth() - this.getWidth()) / 2), (int) ((dimension.getHeight() - this.getHeight()) / 2));
	}
	
	public JPanel getGlobalPan() {
		return GlobalPan;
	}

	public Onglets getOnglets() {
		return TabOnglets;
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		Gvar.CURRENT_FEN_WIDTH = this.getWidth();
		Gvar.CURRENT_FEN_HEIGHT = this.getHeight();
		Gvar.ONG_WIDTH = Gvar.CURRENT_FEN_WIDTH - 17;
		Gvar.ONG_HEIGHT = Gvar.CURRENT_FEN_HEIGHT - ((Gvar.ONG_HEADER_HEIGHT - Gvar.MARGE) + 10);
		TabOnglets.ongletsSetBounds();
		if (TabOnglets.getTitleAt(TabOnglets.getSelectedIndex()).contains(Gvar.BUT_STR_LOAD_ManageTeam))
			TabOnglets.getPanManageTeam().objSetBounds();
		else if (TabOnglets.getTitleAt(TabOnglets.getSelectedIndex()).contains(Gvar.BUT_STR_LOAD_ManageSelection))
			TabOnglets.getPanManageSelection().objSetBounds();
		else if (TabOnglets.getTitleAt(TabOnglets.getSelectedIndex()).contains(Gvar.BUT_STR_LOAD_ManagePoules))
			TabOnglets.getPanManagePoules().objSetBounds();
		else if (TabOnglets.getTitleAt(TabOnglets.getSelectedIndex()).contains(Gvar.BUT_STR_LOAD_ManageEliminations))
			TabOnglets.getPanManageEliminations().objSetBounds();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}