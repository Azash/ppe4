//import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


 class ManagePoules extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadMenu;
	private JButton butLoadClose;
	private JButton butCreatePoules;
	
	private ArrayList<JLabel> LabPoules = new ArrayList<JLabel>();
	private ArrayList<JList<String>> ListPoules = new ArrayList<JList<String>>();
	private ArrayList<DefaultListModel<String>> ListTeam = new ArrayList<DefaultListModel<String>>();
	private ArrayList<JScrollPane> ScrollListPoules = new ArrayList<JScrollPane>();
	private ArrayList<JButton> ButPoules = new ArrayList<JButton>();
	
	private DefaultListModel<String> ListTeamSelected = new DefaultListModel<String>();
	
	private DefaultComboBoxModel<String> ListNbrTeampByPoule = new DefaultComboBoxModel<String>();
	private JComboBox<String> cobListNbrTeampByPoule = new JComboBox<String>(ListNbrTeampByPoule);
	private Random ObjRandom = new Random();
	
	public ManagePoules() {
		this.setLayout(null);
		
		//Initialisation du menu deroulant
		for (int i = Gvar.MIN_TEAM_POULES; i <= Gvar.MAX_TEAM_POULES; i++)
			ListNbrTeampByPoule.addElement("" + i);
		cobListNbrTeampByPoule.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(cobListNbrTeampByPoule);
		
		//Initialisation du bouton d'ajout d'équipe
		butCreatePoules = new JButton("Générer les poules");
		butCreatePoules.addActionListener(this);
		butCreatePoules.setBounds(cobListNbrTeampByPoule.getX() + cobListNbrTeampByPoule.getWidth() + Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butCreatePoules);
		
		/*copyListTeamSelected();
		if (ListTeamSelected.size() > 0) {
			int X = Gvar.MARGE;
			int Y = cobListNbrTeampByPoule.getY() + cobListNbrTeampByPoule.getHeight() + Gvar.MARGE;
			int CobValue = Integer.parseInt(cobListNbrTeampByPoule.getSelectedItem().toString());
			int NbrPoule = ListTeamSelected.size() / CobValue;
			for (int i = 1; i <= NbrPoule; i++) {
				CreatePoule(i, X, Y, CobValue);
				this.add(LabPoules.get(i - 1));
				this.add(ScrollListPoules.get(i - 1));
				this.add(ButPoules.get(i - 1));
				if (i % 4 == 0) {
					X = Gvar.MARGE;
					Y += 2*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
				}
				else {
					X += Gvar.MARGE + Gvar.BUT_WIDTH;
				}
			}
		}*/
		
//-----------------------------------------------------------------------------------------
//ZONE A COPIER/COLLER POUR METTRE LES BOUTONS Menu ET Fermer DANS UN PANEL (Ne pas oublier le code dans actionPerformed et  de creer les 2 variables : JButton butLoadMenu, JButton butLoadClose)
//-----------------------------------------------------------------------------------------
		//Initialisation du bouton pour revenir au menu
		butLoadMenu = new JButton(Gvar.BUT_STR_LOAD_Menu);
		butLoadMenu.addActionListener(this);
		butLoadMenu.setBounds(Gvar.MARGE, Gvar.FEN_HEIGHT - Gvar.FOOTER_HEIGHT, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadMenu);
		
		//Initialisation du bouton pour quitter le programme
		butLoadClose = new JButton(Gvar.BUT_STR_LOAD_Close);
		butLoadClose.addActionListener(this);
		butLoadClose.setBounds(butLoadMenu.getX() + butLoadMenu.getWidth() + Gvar.MARGE, butLoadMenu.getY(), Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butLoadClose);
//-----------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------
	}
	
	public void setPoules() {
		for (int i = 0; i < LabPoules.size(); i++) {
			/*LabPoules.clear();
			ScrollListPoules.clear();
			ButPoules.clear();
			ListPoules.clear();
			ListTeam.clear();*/
			this.remove(LabPoules.get(i));
			this.remove(ButPoules.get(i));
			this.remove(ScrollListPoules.get(i));
		}
		ListPoules.clear();
		ListTeam.clear();
		
		copyListTeamSelected();
		if (ListTeamSelected.size() > 0) {
			int X = Gvar.MARGE;
			int Y = cobListNbrTeampByPoule.getY() + cobListNbrTeampByPoule.getHeight() + Gvar.MARGE;
			int CobValue = Integer.parseInt(cobListNbrTeampByPoule.getSelectedItem().toString());
			int NbrPoules = ListTeamSelected.size() / CobValue;
			for (int i = 1; i <= NbrPoules; i++) {
				CreatePoule(i, X, Y, CobValue);
				this.add(LabPoules.get(i - 1));
				this.add(ScrollListPoules.get(i - 1));
				this.add(ButPoules.get(i - 1));
				if (i % 4 == 0) {
					X = Gvar.MARGE;
					Y += 2*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
				}
				else {
					X += Gvar.MARGE + Gvar.BUT_WIDTH;
				}
			}
		}
	}
	
	private void CreatePoule(int Num, int X, int Y, int NbrTeam) {
		JLabel NewLab = new JLabel("Poule " + Num);
		NewLab.setBounds(X, Y, Gvar.BUT_WIDTH, Gvar.LAB_HEIGHT);
		LabPoules.add(NewLab);
		
		DefaultListModel<String> TmpList = DefineListCurrentPoule(NbrTeam); 
		
		ListTeam.add(TmpList);
		JList<String> NewList = new JList<String>(TmpList);
		//NewList.setModel();
		NewList.addListSelectionListener(this);
		NewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		ListPoules.add(NewList);
		JScrollPane NewListScroll = new JScrollPane();
		NewListScroll.setViewportView(NewList);
		NewListScroll.setBounds(X, NewLab.getY() + NewLab.getHeight(), Gvar.BUT_WIDTH, Gvar.POULE_HEIGHT);
		ScrollListPoules.add(NewListScroll);
		
		JButton NewBut = new JButton("Cliquez sur le gagnant");
		NewBut.addActionListener(this);
		NewBut.setBounds(X, NewListScroll.getY() + NewListScroll.getHeight(), Gvar.BUT_WIDTH, Gvar.MARGE);
		ButPoules.add(NewBut);
	}
	
	private void copyListTeamSelected() {
		ListTeamSelected.clear();
		for (int i = 0; i < Gvar.listSelected.size(); i++) {
			ListTeamSelected.addElement(Gvar.listSelected.getElementAt(i));
		}
	}
	
	private DefaultListModel<String> DefineListCurrentPoule(int NbrTeam) {
		DefaultListModel<String> TmpListPoule = new DefaultListModel<String>();
		int RandomPos = 0;
		for (int i = 0; i < NbrTeam; i++) {
			RandomPos = ObjRandom.nextInt(ListTeamSelected.size());
			TmpListPoule.addElement(ListTeamSelected.getElementAt(RandomPos));
			ListTeamSelected.remove(RandomPos);
		}
		return TmpListPoule;
	}
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butLoadMenu) {
			Main.fen.getCl().show(Main.fen.getGlobalPan(), Gvar.BUT_STR_LOAD_Menu);
			Gvar.CUR_PAN = Gvar.BUT_STR_LOAD_Menu;
			Main.fen.setTitle(Gvar.CUR_PAN);
		}
		else if (e.getSource() == butLoadClose) {
			Main.fen.dispatchEvent(new WindowEvent(Main.fen, WindowEvent.WINDOW_CLOSING));
		}
		else {
			int i = 0;
			boolean Found = false;
			//JOptionPane.showMessageDialog(this, TmpJList.getSelectedValue().toString());
			while (i < ButPoules.size() && !Found) {
				if (ButPoules.get(i) == e.getSource())
					Found = true;
				else
					i++;
			}
			if (ButPoules.get(i).getText().toString().equals("Initaliser")) {
				ButPoules.get(i).setText("Cliquez sur le gagnant");
				for (int j = 0; j < ListTeam.get(i).size(); j++) {
					if (ListTeam.get(i).getElementAt(j).toString().contains("1 : "))
						ListTeam.get(i).setElementAt(ListTeam.get(i).getElementAt(j).toString().replace("1 : ", ""), j);
					else if (ListTeam.get(i).getElementAt(j).toString().contains("2 : "))
						ListTeam.get(i).setElementAt(ListTeam.get(i).getElementAt(j).toString().replace("2 : ", ""), j); 
				}
			}
				
		}
	}
	
	//Declenché lorsque un element different de la liste est selectionné
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() instanceof JList) {
			int i = 0;
			boolean Found = false;
			//JOptionPane.showMessageDialog(this,  ((JList<String>) e.getSource()).getSelectedValue().toString());
			while (i < ListPoules.size() && !Found) {
				if (ListPoules.get(i) == e.getSource())
					Found = true;
				else
					i++;
			}
			if (ButPoules.get(i).getText().toString().equals("Cliquez sur le gagnant") && ListPoules.get(i).getSelectedIndex() >= 0) {
				ButPoules.get(i).setText("Selectionner le 2ème");
				ListTeam.get(i).set(ListPoules.get(i).getSelectedIndex(), "1 : " + ListPoules.get(i).getSelectedValue());
				ListPoules.get(i).clearSelection();
			}
			else if (ButPoules.get(i).getText().toString().equals("Selectionner le 2ème") && ListPoules.get(i).getSelectedIndex() >= 0) {
				ButPoules.get(i).setText("Initaliser");
				ListTeam.get(i).set(ListPoules.get(i).getSelectedIndex(), "2 : " + ListPoules.get(i).getSelectedValue());
				ListPoules.get(i).clearSelection();
			}
		}
	}
}
