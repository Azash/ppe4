import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Onglets extends JTabbedPane implements ChangeListener {
	private static final long serialVersionUID = 1L;
	private ManageTeam panManageTeam = new ManageTeam(); // Panel Gestion des Equipe
	private ManageSelection panManageSelection = new ManageSelection(); // Panel Gestion des Equipe selectionnées pour le tournoi
	private ManagePoules panManagePoules = new ManagePoules(); // Panel Gestion des Poules
	
	public Onglets() {
		this.setBounds(0, 0, Gvar.ONG_WIDTH, Gvar.ONG_HEIGHT);
		this.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + Gvar.BUT_STR_LOAD_ManageTeam + "</body></html>", panManageTeam);
		this.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + Gvar.BUT_STR_LOAD_ManageSelection + "</body></html>", panManageSelection);
		this.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + Gvar.BUT_STR_LOAD_ManagePoules + "</body></html>", panManagePoules);
		this.addChangeListener(this);
	}
	
	
	public ManageSelection getPanManageSelection() {
		return panManageSelection;
	}
	
	public ManagePoules getPanManagePoules() {
		return panManagePoules;
	}
	
	public ManageTeam getPanManageTeam() {
		return panManageTeam;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		String TmpTitleTab = ((JTabbedPane)e.getSource()).getTitleAt(((JTabbedPane)e.getSource()).getSelectedIndex());
		if (TmpTitleTab.contains(Gvar.BUT_STR_LOAD_ManageSelection))
			Main.fen.getOnglets().getPanManageSelection().setListTeam();
		else if (TmpTitleTab.contains(Gvar.BUT_STR_LOAD_ManagePoules))
			Main.fen.getOnglets().getPanManagePoules().setPoules();
	}

}
