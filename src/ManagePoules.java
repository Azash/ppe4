//import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


 class ManagePoules extends JPanel implements ActionListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
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
	private JButton butSave = new JButton("Sauver");
	
	private boolean isAlreadySaved = true;
	
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
		
		PoulesContainer.setLayout(null);
		SPoulesContainer.setViewportView(PoulesContainer);
		//SPoulesContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		SPoulesContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		SPoulesContainer.setBounds(0, cobListNbrTeampByPoule.getY() + cobListNbrTeampByPoule.getHeight() + Gvar.MARGE, Gvar.ONG_WIDTH - 4, Gvar.ONG_HEIGHT - ((4*Gvar.MARGE) + (2*Gvar.BUT_HEIGHT) + (Gvar.LAB_HEIGHT) + Gvar.ONG_HEADER_HEIGHT));
		this.add(SPoulesContainer);
		
		butSave.addActionListener(this);
		butSave.setBounds(Gvar.MARGE, SPoulesContainer.getY() + SPoulesContainer.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		this.add(butSave);
	}
	
	public void setPoules() {
		isAlreadySaved = false;
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
			double CheckNbrPoules = (double)ListTeamSelected.size() / (double)CobValue;
			int NbrPoules = (int)CheckNbrPoules;
			//JOptionPane.showMessageDialog(this, CheckNbrPoules + " - " + NbrPoules + " = " + ((double)CheckNbrPoules - (double)NbrPoules));
			//if ((double)CheckNbrPoules - (double)NbrPoules >= 0.5)
			//	NbrPoules++;
			//JOptionPane.showMessageDialog(this, NbrPoules);
			for (int i = 1; i <= NbrPoules; i++) {
				CreatePoule(i, X, Y, CobValue);
				PoulesContainer.add(LabPoules.get(i - 1));
				PoulesContainer.add(ScrollListPoules.get(i - 1));
				PoulesContainer.add(ButPoules.get(i - 1));
				if (i % 4 == 0) {
					X = Gvar.MARGE;
					Y += 3*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
				}
				else {
					X += Gvar.MARGE + Gvar.POULE_WIDTH;
				}
			}
			if ((double)CheckNbrPoules - (double)NbrPoules > 0)
				addTheRest(NbrPoules + 1, X, Y, CobValue);
			if (Gvar.listSelected.size() % CobValue != 0)
				LabSelectionNotMultipleOfPoule.setText("<html>Le nombre d'equipes par poule selectionné n'est pas un multiple du nombre total d'equipes.<br />Du coup ont a fait au mieux...</html>");
			else
				LabSelectionNotMultipleOfPoule.setText("");
			if (NbrPoules > 0)
				PoulesContainer.setPreferredSize(new Dimension(800, ButPoules.get(ButPoules.size() - 1).getY() + ButPoules.get(ButPoules.size() - 1).getHeight() + 4));
			this.validate();
			this.repaint();
		}
	}
	
	private void addTheRest(int i, int X, int Y, int NbrTeam) {
		if ((ListTeamSelected.size()) > (double)NbrTeam / 2.0 && ListTeamSelected.size() >= 3) {
			CreatePoule(i, X, Y, NbrTeam);
			PoulesContainer.add(LabPoules.get(i - 1));
			PoulesContainer.add(ScrollListPoules.get(i - 1));
			PoulesContainer.add(ButPoules.get(i - 1));
			if (i % 4 == 0) {
				X = Gvar.MARGE;
				Y += 3*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
			}
			else {
				X += Gvar.MARGE + Gvar.POULE_WIDTH;
			}
		}
		else {
			int RandomPos = 0;
			int j = 0;
			while (ListTeamSelected.size() > 0) {
				RandomPos = ObjRandom.nextInt(ListTeamSelected.size());
				ListTeam.get(j).addElement(ListTeamSelected.getElementAt(0));
				ListTeamSelected.remove(0);
				j++;
			}
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
		NewBut.setBounds(X, NewListScroll.getY() + NewListScroll.getHeight(), Gvar.POULE_WIDTH, Gvar.BUT_SLIM_HEIGHT);
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
		int i = 0;
		while (i < NbrTeam && ListTeamSelected.size() > 0) {
			RandomPos = ObjRandom.nextInt(ListTeamSelected.size());
			TmpListPoule.addElement(ListTeamSelected.getElementAt(0));
			ListTeamSelected.remove(0);
			i++;
		}
		/*if (ListTeamSelected.size() % NbrTeam != 0) {
			RandomPos = ObjRandom.nextInt(ListTeamSelected.size());
			TmpListPoule.addElement(ListTeamSelected.getElementAt(RandomPos));
			ListTeamSelected.remove(RandomPos);
		}*/
			
		return TmpListPoule;
	}
	
	public void saveInTxtFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(Gvar.FILE_NAME_POULES, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (!writer.equals(null)) {
			for(int i = 0; i < ListTeam.size(); i++) {
				writer.println("[Poule " + (i + 1) + "]");
				for (int j = 0; j < ListTeam.get(i).size(); j++)
					writer.println(ListTeam.get(i).getElementAt(j).toString());
				writer.println("");
			}
			writer.close();
			isAlreadySaved = true;
		}
	}
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cobListNbrTeampByPoule) {
			setPoules();
		}
		else if (e.getSource() == butSave && ListTeam.size() > 0) {
			saveInTxtFile();
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
				else if (ButPoules.get(i).getText().toString().equals("Cliquez sur le 2ème") && !ListPoules.get(i).getSelectedValue().toString().contains("1 : ")) {
					ButPoules.get(i).setText("Initaliser");
					ListTeam.get(i).set(ListPoules.get(i).getSelectedIndex(), "2 : " + ListPoules.get(i).getSelectedValue());
					ListPoules.get(i).clearSelection();
				}
			}
		}
	}

	public boolean getIsAlreadySaved() {
		return isAlreadySaved;
	}
	
	public ArrayList<DefaultListModel<String>> getListTeam() {
		return ListTeam;
	}
}
