//import java.awt.Graphics;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


 class ManagePoules extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JButton butLoadMenu;
	private JButton butLoadClose;
	private ArrayList<JLabel> LabPoules = new ArrayList<JLabel>();
	private ArrayList<JList<String>> ListPoules = new ArrayList<JList<String>>();
	private ArrayList<DefaultListModel<String>> ListTeam = new ArrayList<DefaultListModel<String>>();
	private ArrayList<JScrollPane> ScrollListPoules = new ArrayList<JScrollPane>();
	private ArrayList<JButton> ButPoules = new ArrayList<JButton>();
	
	private DefaultListModel<String> ListTeamSelected = new DefaultListModel<String>();
	
	private JLabel LabSelectionCob = new JLabel("Nombre d'équipes par poules : ");
	private JLabel LabSelectionNotMultipleOfPoule = new JLabel("");
	private DefaultComboBoxModel<String> ListNbrTeampByPoule = new DefaultComboBoxModel<String>();
	private JComboBox<String> cobListNbrTeampByPoule = new JComboBox<String>(ListNbrTeampByPoule);
	private Random ObjRandom = new Random();
	
	private JPanel PoulesContainer = new JPanel();
	private JScrollPane SPoulesContainer = new JScrollPane(PoulesContainer);
	
	public ManagePoules() {
		this.setLayout(null);
		
		LabSelectionCob.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH + 50, Gvar.LAB_HEIGHT);
		this.add(LabSelectionCob);
		
		//Initialisation du menu deroulant
		for (int i = Gvar.MIN_TEAM_POULES; i <= Gvar.MAX_TEAM_POULES; i++)
			ListNbrTeampByPoule.addElement("" + i);
		cobListNbrTeampByPoule.addActionListener(this);
		cobListNbrTeampByPoule.setBounds(Gvar.MARGE, LabSelectionCob.getY() + LabSelectionCob.getHeight(), Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(cobListNbrTeampByPoule);
		
		LabSelectionNotMultipleOfPoule.setBounds(cobListNbrTeampByPoule.getX() + cobListNbrTeampByPoule.getWidth() + Gvar.MARGE, cobListNbrTeampByPoule.getY(), Gvar.BUT_WIDTH + 400, 50);
		this.add(LabSelectionNotMultipleOfPoule);
		
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
		PoulesContainer.setLayout(null);
		SPoulesContainer.setViewportView(PoulesContainer);
		//SPoulesContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		SPoulesContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		SPoulesContainer.setBounds(5, cobListNbrTeampByPoule.getY() + cobListNbrTeampByPoule.getHeight() + Gvar.MARGE, Gvar.FEN_WIDTH - (Gvar.MARGE), Gvar.FEN_HEIGHT - ((4*Gvar.MARGE) + 10 + (2*Gvar.BUT_HEIGHT) + (Gvar.LAB_HEIGHT)));
		
		this.add(SPoulesContainer);
		/*this.setLayout(null);
		
		JPanel Test = new JPanel();
		Test.setLayout(null);
		JLabel toto = new JLabel("toto");
		toto.setBounds(0, 0, Gvar.BUT_WIDTH, Gvar.LAB_HEIGHT);
		Test.setBounds(100, Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.LAB_HEIGHT);
		Test.add(toto);
		Test.setBackground(Color.gray);
		this.add(Test);
		
		JPanel Rien = new JPanel();
		Rien.setLayout(null);
		JLabel lol = new JLabel("rien");
		lol.setBounds(100, 200, Gvar.BUT_WIDTH, Gvar.LAB_HEIGHT);
		Rien.setBounds(100, 200, Gvar.BUT_WIDTH, Gvar.LAB_HEIGHT);
		Rien.add(lol);
		Rien.setBackground(Color.red);
		this.add(Rien);
		
		this.setBackground(Color.green);*/
	}
	
	public void setPoules() {
		for (int i = 0; i < LabPoules.size(); i++) {
			PoulesContainer.remove(LabPoules.get(i));
			PoulesContainer.remove(ButPoules.get(i));
			PoulesContainer.remove(ScrollListPoules.get(i));
		}
		LabPoules.clear();
		ButPoules.clear();
		ScrollListPoules.clear();
		ListTeam.clear();
		ListPoules.clear();
		
		copyListTeamSelected();
		if (ListTeamSelected.size() > 0) {
			int X = Gvar.MARGE;
			int Y = 0;
			int CobValue = Integer.parseInt(cobListNbrTeampByPoule.getSelectedItem().toString());
			int NbrPoules = ListTeamSelected.size() / CobValue;
			for (int i = 1; i <= NbrPoules; i++) {
				CreatePoule(i, X, Y, CobValue);
				PoulesContainer.add(LabPoules.get(i - 1));
				PoulesContainer.add(ScrollListPoules.get(i - 1));
				PoulesContainer.add(ButPoules.get(i - 1));
				if (i % 4 == 0) {
					X = Gvar.MARGE;
					Y += 2*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
				}
				else {
					X += Gvar.MARGE + Gvar.POULE_WIDTH;
				}
			}
			if (Gvar.listSelected.size() % CobValue != 0)
				LabSelectionNotMultipleOfPoule.setText("<html>Le nombre d'equipes par poule selectionné n'est pas un multiple du nombre total d'equipes.<br />Du coup ont a fait au mieux...</html>");
			else
				LabSelectionNotMultipleOfPoule.setText("");
			PoulesContainer.setPreferredSize(new Dimension(800, ButPoules.get(ButPoules.size() - 1).getY() + ButPoules.get(ButPoules.size() - 1).getHeight() + 4));
			this.validate();
			this.repaint();
		}
	}
	
	private void CreatePoule(int Num, int X, int Y, int NbrTeam) {
		JLabel NewLab = new JLabel("Poule " + Num);
		NewLab.setBounds(X, Y, Gvar.POULE_WIDTH, Gvar.LAB_HEIGHT);
		LabPoules.add(NewLab);
		
		DefaultListModel<String> TmpList = DefineListCurrentPoule(NbrTeam); 
		
		ListTeam.add(TmpList);
		JList<String> NewList = new JList<String>(TmpList);
		NewList.addListSelectionListener(this);
		NewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Desactive la selection multiple
		ListPoules.add(NewList);
		JScrollPane NewListScroll = new JScrollPane();
		NewListScroll.setViewportView(NewList);
		NewListScroll.setBounds(X, NewLab.getY() + NewLab.getHeight(), Gvar.POULE_WIDTH, Gvar.POULE_HEIGHT);
		ScrollListPoules.add(NewListScroll);
		
		JButton NewBut = new JButton("Cliquez sur le gagnant");
		NewBut.addActionListener(this);
		NewBut.setBounds(X, NewListScroll.getY() + NewListScroll.getHeight(), Gvar.POULE_WIDTH, Gvar.MARGE);
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
			TmpListPoule.addElement(ListTeamSelected.getElementAt(0));
			ListTeamSelected.remove(0);
		}
		if (ListTeamSelected.size() % NbrTeam != 0) {
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
		else if (e.getSource() == cobListNbrTeampByPoule) {
			setPoules();
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
			while (i < ListPoules.size() && !Found) {
				if (ListPoules.get(i) == e.getSource())
					Found = true;
				else
					i++;
			}
			if (Found && ListPoules.get(i).getSelectedIndex() >= 0) {
				if (ButPoules.get(i).getText().toString().equals("Cliquez sur le gagnant")) {
					ButPoules.get(i).setText("Cliquez sur le 2ème");
					ListTeam.get(i).set(ListPoules.get(i).getSelectedIndex(), "1 : " + ListPoules.get(i).getSelectedValue());
					ListPoules.get(i).clearSelection();
				}
				else if (ButPoules.get(i).getText().toString().equals("Cliquez sur le 2ème")) {
					ButPoules.get(i).setText("Initaliser");
					ListTeam.get(i).set(ListPoules.get(i).getSelectedIndex(), "2 : " + ListPoules.get(i).getSelectedValue());
					ListPoules.get(i).clearSelection();
				}
			}
		}
	}
}
