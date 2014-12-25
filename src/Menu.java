import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadManageTeam;
	private JButton butLoadManageSelection;
	private JButton butLoadClose;
	
	public Menu() {
		this.setLayout(null);
		
		//Initialisation du bouton pour acceder a la gestion des equipes
		butLoadManageTeam = new JButton(Gvar.BUT_STR_LOAD_ManageTeam);
		butLoadManageTeam.addActionListener(this);
		butLoadManageTeam.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadManageTeam);
		
		//Initialisation du bouton pour acceder a la selection des equipes
		butLoadManageSelection = new JButton(Gvar.BUT_STR_LOAD_ManageSelection);
		butLoadManageSelection.addActionListener(this);
		butLoadManageSelection.setBounds(Gvar.MARGE, butLoadManageTeam.getY() + butLoadManageTeam.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadManageSelection);
		
		//Initialisation du bouton pour quitter le programme
		butLoadClose = new JButton(Gvar.BUT_STR_LOAD_Close);
		butLoadClose.addActionListener(this);
		butLoadClose.setBounds(Gvar.MARGE, Gvar.FEN_HEIGHT - Gvar.FOOTER_HEIGHT, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadClose);
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
		else if (e.getSource() == butLoadClose) { // BOUTON SAUVEGARDE CLIQUE
			Main.fen.dispatchEvent(new WindowEvent(Main.fen, WindowEvent.WINDOW_CLOSING));
		}
	}

}
