import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadManageTeam;
	
	public Menu() {
		this.setLayout(null);
		
		butLoadManageTeam = new JButton("Gestion des équipes");
		butLoadManageTeam.addActionListener(this);
		butLoadManageTeam.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadManageTeam);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butLoadManageTeam) {
			Main.fen.getCl().show(Main.fen.getGlobalPan(), Main.fen.getTitleManageTeam());
			Gvar.CUR_PAN = Main.fen.getTitleManageTeam();
		}
	}

}
