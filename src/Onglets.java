import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Onglets extends JTabbedPane implements ChangeListener {
	private static final long serialVersionUID = 1L;
	private ManageTeam panManageTeam = new ManageTeam(); // Panel Gestion des Equipe
	private ManageSelection panManageSelection = new ManageSelection(); // Panel Gestion des Equipe selectionnées pour le tournoi
	private ManagePoules panManagePoules = new ManagePoules(); // Panel Gestion des Poules
	private ManageEliminations panManageEliminations = new ManageEliminations(); // Panel Gestion des Poules
	
	public Onglets() {
		this.setBounds(0, 0,  Gvar.CURRENT_FEN_WIDTH - 7, Gvar.CURRENT_FEN_HEIGHT - (Gvar.ONG_HEADER_HEIGHT - Gvar.MARGE));
		this.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + Gvar.BUT_STR_LOAD_ManageTeam + "</body></html>", panManageTeam);
		this.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + Gvar.BUT_STR_LOAD_ManageSelection + "</body></html>", panManageSelection);
		this.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + Gvar.BUT_STR_LOAD_ManagePoules + "</body></html>", panManagePoules);
		this.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>" + Gvar.BUT_STR_LOAD_ManageEliminations + "</body></html>", panManageEliminations);
		this.addChangeListener(this);
	}
	
	public void ongletsSetBounds() {
		//Quand la fenetre est Resizable les bordure sont plus grosse
		//C'est cool on peu faire la difference visuellement entre une fenetre qu'on peu redimensionner et une fenetre sur laquelle on peu pas...
		//J'ai envie de dire SUPER !!! Je m'en moque royalement, doit y avoir une raison pour cela mais je vois pas laquelle, c'est juste fichetrement embetant ! 
		if (Main.fen.isResizable())
			this.setBounds(0, 0,  Gvar.CURRENT_FEN_WIDTH - 17, Gvar.CURRENT_FEN_HEIGHT - ((Gvar.ONG_HEADER_HEIGHT - Gvar.MARGE) + 10));
		else
			this.setBounds(0, 0,  Gvar.CURRENT_FEN_WIDTH - 7, Gvar.CURRENT_FEN_HEIGHT - (Gvar.ONG_HEADER_HEIGHT - Gvar.MARGE));
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

	public ManageEliminations getPanManageEliminations() {
		return panManageEliminations;
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		String TmpTitleTab = ((JTabbedPane)e.getSource()).getTitleAt(((JTabbedPane)e.getSource()).getSelectedIndex());
		if (TmpTitleTab.contains(Gvar.BUT_STR_LOAD_ManageSelection)) {
			panManageSelection.objSetBounds();
			Main.fen.getOnglets().getPanManageSelection().setListTeam();
		}
		else if (TmpTitleTab.contains(Gvar.BUT_STR_LOAD_ManageTeam))
			panManageTeam.objSetBounds();
		else if (TmpTitleTab.contains(Gvar.BUT_STR_LOAD_ManagePoules))
			panManagePoules.objSetBounds();
		else if (TmpTitleTab.contains(Gvar.BUT_STR_LOAD_ManageEliminations)) {
			panManageEliminations.objSetBounds();
			panManageEliminations.initElimination();
		}
	}
}
