import javax.swing.JFrame;
import javax.swing.JPanel;

class Fen extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel pan;
	private ManageTeam ManageTeamObj;
	
	public Fen() {
		ManageTeamObj = new ManageTeam();
		pan = ManageTeamObj.getManagePan();
		this.add(pan);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		setBounds(700, 200, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}