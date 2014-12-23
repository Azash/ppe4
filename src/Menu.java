import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadManageTeam;
	private JButton butLoadManageSelection;
	
	public Menu() {
		this.setLayout(null);
		
		butLoadManageTeam = new JButton(Gvar.BUT_STR_LOAD_ManageTeam);
		butLoadManageTeam.addActionListener(this);
		butLoadManageTeam.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadManageTeam);
		
		butLoadManageSelection = new JButton(Gvar.BUT_STR_LOAD_ManageSelection);
		butLoadManageSelection.addActionListener(this);
		butLoadManageSelection.setBounds(Gvar.MARGE, butLoadManageTeam.getY() + butLoadManageTeam.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadManageSelection);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butLoadManageTeam) {
			Main.fen.getCl().show(Main.fen.getGlobalPan(), Gvar.BUT_STR_LOAD_ManageTeam);
			Gvar.CUR_PAN = Gvar.BUT_STR_LOAD_ManageTeam;
		}
		else if (e.getSource() == butLoadManageSelection) {
			Main.fen.getCl().show(Main.fen.getGlobalPan(), Gvar.BUT_STR_LOAD_ManageSelection);
			Gvar.CUR_PAN = Gvar.BUT_STR_LOAD_ManageSelection;
		}
	}

}
