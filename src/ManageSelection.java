import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class ManageSelection extends JPanel implements ActionListener, ListSelectionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadMenu;
	private JButton butSelectTeam;
	private JButton butUnselectTeam;
	private JList teamList;
	private JList teamSelected;
	private DefaultListModel<String> listTeam = new DefaultListModel<String>();
	private DefaultListModel<String> listSelected = new DefaultListModel<String>();
	private int idSelected = -1;
	private int idTeam = -1;
	
	public ManageSelection() {
		this.setLayout(null);
		
		//Initialisation de la listbox affichant les équipes
		teamList = new JList(listTeam);
		teamList.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamList.addMouseListener(this);
		teamList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		teamList.setBounds(Gvar.MARGE, Gvar.MARGE, (Gvar.FEN_WIDTH - ((4 * Gvar.MARGE) + 20)) / 2 - Gvar.MARGE, Gvar.FEN_HEIGHT - ((3 * Gvar.MARGE) + Gvar.BUT_HEIGHT) - 25);
		this.add(teamList);
		
		butSelectTeam = new JButton(">");
		butSelectTeam.addActionListener(this);
		butSelectTeam.setBounds(teamList.getX() + teamList.getWidth() + Gvar.MARGE, Gvar.MARGE, 50, Gvar.BUT_HEIGHT);
		this.add(butSelectTeam);
		
		butUnselectTeam = new JButton("<");
		butUnselectTeam.addActionListener(this);
		butUnselectTeam.setBounds(butSelectTeam.getX(), butSelectTeam.getY() + butSelectTeam.getHeight() + Gvar.MARGE, 50, Gvar.BUT_HEIGHT);
		this.add(butUnselectTeam);
		
		//Initialisation de la listbox affichant les équipes SELECTIONNEES
		teamSelected = new JList(listSelected);
		teamSelected.addListSelectionListener(this); // Permet de savoir quand on selectionne un element different : valueChanged
		teamSelected.addMouseListener(this);
		teamSelected.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		teamSelected.setBounds(butSelectTeam.getX() + butSelectTeam.getWidth() + Gvar.MARGE, Gvar.MARGE, (Gvar.FEN_WIDTH - ((4 * Gvar.MARGE) + 20)) / 2 - Gvar.MARGE, Gvar.FEN_HEIGHT - ((3 * Gvar.MARGE) + Gvar.BUT_HEIGHT) - 25);
		this.add(teamSelected);
		
		butLoadMenu = new JButton(Gvar.BUT_STR_LOAD_Menu);
		butLoadMenu.addActionListener(this);
		butLoadMenu.setBounds(Gvar.MARGE, teamList.getY() + teamList.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadMenu);
		
		for (int i = 0; i < Gvar.listData.getSize(); i++)
			listTeam.addElement(Gvar.listData.get(i).toString());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butLoadMenu) {
			Main.fen.getCl().show(Main.fen.getGlobalPan(), Gvar.BUT_STR_LOAD_Menu);
			Gvar.CUR_PAN = Gvar.BUT_STR_LOAD_Menu;
		}
		else if (e.getSource() == butSelectTeam && idTeam != -1) {
			listSelected.addElement(listTeam.get(idTeam).toString());
			listTeam.remove(idTeam);
			idTeam = -1;
		}
		else if (e.getSource() == butUnselectTeam && idSelected != -1) {
			listTeam.addElement(listSelected.get(idSelected).toString());
			listSelected.remove(idSelected);
			idSelected = -1;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == teamList)
			idTeam = teamList.getSelectedIndex();
		else if (e.getSource() == teamSelected)
			idSelected = teamSelected.getSelectedIndex(); 
	}

	@Override
    public void mouseClicked(MouseEvent e) {
		Rectangle r =  ((JList) e.getSource()).getCellBounds(0, ((JList) e.getSource()).getLastVisibleIndex()); // Pour etre sur qu'il clique sur un des elements et non pas sur rien
		if (e.getClickCount() == 2 && r != null && r.contains(e.getPoint())) {
			if (e.getSource() == teamList) {
				int index = teamList.locationToIndex(e.getPoint());
				listSelected.addElement(listTeam.get(index).toString());
				listTeam.remove(index);
			}
			else if (e.getSource() == teamSelected) {
				int index = teamSelected.locationToIndex(e.getPoint());
				listTeam.addElement(listSelected.get(index).toString());
				listSelected.remove(index);
			}
		}
    }

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
