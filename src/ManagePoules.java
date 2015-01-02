//import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
	private DefaultComboBoxModel<String> ListNbrTeampByPoule = new DefaultComboBoxModel<String>();
	private JComboBox<String> cobListNbrTeampByPoule = new JComboBox<String>(ListNbrTeampByPoule);
	//private Random ObjRandom = new Random();
	
	private JPanel PoulesContainer = new JPanel();
	private JScrollPane SPoulesContainer = new JScrollPane(PoulesContainer);
	private JButton butSave = new JButton("Sauver");
	private JButton butGeneratePoules = new JButton("Générer");
	private boolean isAlreadySaved = true;
	private boolean ThereIsDoublon = false;
	private boolean ThereIsColon = false;
	
	public ManagePoules() {
		this.setLayout(null);

		//Ajout des element de la liste du menu deroulant
		for (int i = Gvar.MIN_TEAM_POULES; i <= Gvar.MAX_TEAM_POULES; i++)
			ListNbrTeampByPoule.addElement("" + i);
		
		// Configuration du JPANEL PoulesContainer
		PoulesContainer.setLayout(null);
		
		// Configuration de la ScrollPane SPoulesContainer
		SPoulesContainer.setViewportView(PoulesContainer);
		//SPoulesContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		SPoulesContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		cobListNbrTeampByPoule.addActionListener(this);
		butGeneratePoules.addActionListener(this);
		butSave.addActionListener(this);
		
		this.add(LabSelectionCob);
		this.add(cobListNbrTeampByPoule);
		this.add(butGeneratePoules);
		this.add(SPoulesContainer);
		this.add(butSave);

		objSetBounds(); //Positionne les objets dans le panel
		
		parseTxtFile();
		
		if (ThereIsDoublon)
			JOptionPane.showMessageDialog(this, "<html>Il semble qu'il y ai des doublons<br />Les doubons n'ont donc pas été pris en compte.</html>", "Fichier " + Gvar.FILE_NAME_POULES + " altéré", JOptionPane.ERROR_MESSAGE);
		if (ThereIsColon)
			JOptionPane.showMessageDialog(this, "<html>Une ou plusieurs équipes semble contenir le caractère \":\"<br />Elles n'ont pas été prisent en compte.</html>", "Fichier " + Gvar.FILE_NAME_POULES + " altéré", JOptionPane.ERROR_MESSAGE);
	}
	
	public void objSetBounds() {
		LabSelectionCob.setBounds(Gvar.MARGE, Gvar.MARGE, Gvar.BUT_WIDTH + 50, Gvar.LAB_HEIGHT);
		cobListNbrTeampByPoule.setBounds(Gvar.MARGE, LabSelectionCob.getY() + LabSelectionCob.getHeight(), Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		butGeneratePoules.setBounds(cobListNbrTeampByPoule.getX() + cobListNbrTeampByPoule.getWidth() + Gvar.MARGE, cobListNbrTeampByPoule.getY(), Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		SPoulesContainer.setBounds(0, cobListNbrTeampByPoule.getY() + cobListNbrTeampByPoule.getHeight() + Gvar.MARGE, Gvar.ONG_WIDTH - 4, Gvar.ONG_HEIGHT - ((4*Gvar.MARGE) + (2*Gvar.BUT_HEIGHT) + (Gvar.LAB_HEIGHT) + Gvar.ONG_HEADER_HEIGHT));
		butSave.setBounds(Gvar.MARGE, SPoulesContainer.getY() + SPoulesContainer.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		int X = Gvar.MARGE;
		int Y = 0;
		int NbrPouleByLine = 1;
		for (int i = 0; i < ButPoules.size(); i++) {
			LabPoules.get(i).setBounds(X, Y, Gvar.POULE_WIDTH, Gvar.LAB_HEIGHT);
			ScrollListPoules.get(i).setBounds(X, LabPoules.get(i).getY() + LabPoules.get(i).getHeight(), Gvar.POULE_WIDTH, Gvar.POULE_HEIGHT);
			ButPoules.get(i).setBounds(X, ScrollListPoules.get(i).getY() + ScrollListPoules.get(i).getHeight(), Gvar.POULE_WIDTH, Gvar.BUT_SLIM_HEIGHT);
			//ButPoules.get(0).setText((i + 1) * (Gvar.MARGE + Gvar.POULE_WIDTH) + " " + SPoulesContainer.getWidth());
			//JOptionPane.showMessageDialog(this, (i + 1) * (Gvar.MARGE + Gvar.POULE_WIDTH) + " " + SPoulesContainer.getWidth());
			NbrPouleByLine++;
			if (NbrPouleByLine * (Gvar.MARGE + Gvar.POULE_WIDTH) > SPoulesContainer.getWidth()) {
				X = Gvar.MARGE;
				Y += 3*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
				NbrPouleByLine = 1;
			}
			else {
				X += Gvar.MARGE + Gvar.POULE_WIDTH;
			}
			
		}
	}
	
	public DefaultListModel<String> getQualifiedList() {
		DefaultListModel<String> QualifiedList = new DefaultListModel<String>();
		int j;
		int BothAreFound;
		
		for (int i = 0; i < ListTeam.size(); i++) {
			j = 0;
			BothAreFound = 0;
			while (BothAreFound < 2 && j < ListTeam.get(i).size()) {
				if (ListTeam.get(i).get(j).contains("1 : ") || ListTeam.get(i).get(j).contains("2 : ")) {
					QualifiedList.addElement(ListTeam.get(i).get(j).toString());
					BothAreFound++;
				}
				j++;
			}
		}
		return QualifiedList;
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
			int NbrPouleByLine = 1;
			for (int i = 1; i <= NbrPoules; i++) {
				CreatePoule(i, X, Y, CobValue, DefineListCurrentPoule(CobValue));
				PoulesContainer.add(LabPoules.get(i - 1));
				PoulesContainer.add(ScrollListPoules.get(i - 1));
				PoulesContainer.add(ButPoules.get(i - 1));
				NbrPouleByLine++;
				if (NbrPouleByLine * (Gvar.MARGE + Gvar.POULE_WIDTH) > SPoulesContainer.getWidth()) {
					X = Gvar.MARGE;
					Y += 3*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
					NbrPouleByLine = 1;
				}
				else {
					X += Gvar.MARGE + Gvar.POULE_WIDTH;
				}
			}
			if ((double)CheckNbrPoules - (double)NbrPoules > 0)
				addTheRest(NbrPoules + 1, X, Y, CobValue, NbrPouleByLine);
			if (NbrPoules > 0)
				PoulesContainer.setPreferredSize(new Dimension(Main.fen.getWidth(), ButPoules.get(ButPoules.size() - 1).getY() + ButPoules.get(ButPoules.size() - 1).getHeight() + 1));
			this.validate();
			this.repaint();
		}
	}
	
	private void addTheRest(int i, int X, int Y, int NbrTeam, int NbrPouleByLine) {
		if ((ListTeamSelected.size()) > (double)NbrTeam / 2.0 && ListTeamSelected.size() >= 3) {
			CreatePoule(i, X, Y, NbrTeam, DefineListCurrentPoule(NbrTeam));
			PoulesContainer.add(LabPoules.get(i - 1));
			PoulesContainer.add(ScrollListPoules.get(i - 1));
			PoulesContainer.add(ButPoules.get(i - 1));
			NbrPouleByLine++;
			if (NbrPouleByLine * (Gvar.MARGE + Gvar.POULE_WIDTH) > SPoulesContainer.getWidth()) {
				X = Gvar.MARGE;
				Y += 3*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
				NbrPouleByLine = 1;
			}
			else {
				X += Gvar.MARGE + Gvar.POULE_WIDTH;
			}
		}
		else {
			//int RandomPos = 0;
			int j = 0;
			while (ListTeamSelected.size() > 0) {
				//RandomPos = ObjRandom.nextInt(ListTeamSelected.size());
				ListTeam.get(j).addElement(ListTeamSelected.getElementAt(0));
				ListTeamSelected.remove(0);
				j++;
			}
		}
			
	}
	
	private void CreatePoule(int Num, int X, int Y, int NbrTeam, DefaultListModel<String> TmpList) {
		JLabel NewLab = new JLabel("Poule " + Num);
		NewLab.setBounds(X, Y, Gvar.POULE_WIDTH, Gvar.LAB_HEIGHT);
		LabPoules.add(NewLab);
		
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
		//int RandomPos = 0; // On verra quand on sera sur que ca fonctionne, pour le moment ca me permet d'etre sur qu'il prend bien toutes les equipe que je selectionne
		int i = 0;
		while (i < NbrTeam && ListTeamSelected.size() > 0) {
			//RandomPos = ObjRandom.nextInt(ListTeamSelected.size());
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
	
	//Fonction a simplifier sinon je vomis (sutout que c'est presque le copier colle de setPoules
	private void parseTxtFile() {
		File f = new File("./" + Gvar.FILE_NAME_POULES);
		if(f.exists() && !f.isDirectory()) {
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
			ListTeamSelected.clear();
			
			String Line;
			int i = 0;
			BufferedReader Buffer;
			ArrayList<DefaultListModel<String>> TmpArray = new ArrayList<DefaultListModel<String>>();
			int X = Gvar.MARGE;
			int Y = 0;
			int RankSetted = 0;
			int NbrPouleByLine = 1;
			try {
				Buffer = new BufferedReader(new InputStreamReader(new FileInputStream(f))); 
				while((Line = Buffer.readLine()) != null) {
					if (!Line.isEmpty() && Line.contains("[Poule ")) {
						if (TmpArray.size() > 0) {
							CreatePoule(i, X, Y, 0, TmpArray.get(i - 1));
							PoulesContainer.add(LabPoules.get(i - 1));
							PoulesContainer.add(ScrollListPoules.get(i - 1));
							PoulesContainer.add(ButPoules.get(i - 1));
							NbrPouleByLine++;
							if (NbrPouleByLine * (Gvar.MARGE + Gvar.POULE_WIDTH) > SPoulesContainer.getWidth()) {
								X = Gvar.MARGE;
								Y += 3*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
								NbrPouleByLine = 1;
							}
							else {
								X += Gvar.MARGE + Gvar.POULE_WIDTH;
							}
							if (RankSetted == 1)
								ButPoules.get(i - 1).setText("Cliquez sur le 2ème");
							else if (RankSetted == 2)
								ButPoules.get(i - 1).setText("Initaliser");
						}
						TmpArray.add(new DefaultListModel<String>());
						RankSetted = 0;
						i++;
					}
					else if (!Line.isEmpty() && TmpArray.size() > 0) {
						if (!isDoublon(Line, ListTeamSelected) /*&& !isColon(Line)*/) {
							TmpArray.get(i - 1).addElement(Line);
							ListTeamSelected.addElement(Line);
							if (Line.contains("1 : ") && RankSetted == 0)
								RankSetted = 1;
							else if (Line.contains("2 : "))
								RankSetted = 2;
						}
					}
				}
				if (TmpArray.size() > 0) {
					CreatePoule(i, X, Y, 0, TmpArray.get(i - 1));
					PoulesContainer.add(LabPoules.get(i - 1));
					PoulesContainer.add(ScrollListPoules.get(i - 1));
					PoulesContainer.add(ButPoules.get(i - 1));
					NbrPouleByLine++;
					if (NbrPouleByLine * (Gvar.MARGE + Gvar.POULE_WIDTH) > SPoulesContainer.getWidth()) {
						X = Gvar.MARGE;
						Y += 3*Gvar.MARGE + Gvar.LAB_HEIGHT + Gvar.POULE_HEIGHT;
						NbrPouleByLine = 1;
					}
					else {
						X += Gvar.MARGE + Gvar.POULE_WIDTH;
					}
					if (RankSetted == 1)
						ButPoules.get(i - 1).setText("Cliquez sur le 2ème");
					else if (RankSetted == 2)
						ButPoules.get(i - 1).setText("Initaliser");
					PoulesContainer.setPreferredSize(new Dimension(Gvar.CURRENT_FEN_WIDTH, ButPoules.get(ButPoules.size() - 1).getY() + ButPoules.get(ButPoules.size() - 1).getHeight() + 1));
					this.validate();
					this.repaint();
				}
				Buffer.close();
			} catch (IOException e) { } 
			
		}
	}
	
	private boolean isDoublon(String StrToCheck, DefaultListModel<String> ListToCheck) {
		int i = 0;
		while (i <ListToCheck.size()) {
			if (StrToCheck.equals(ListToCheck.getElementAt(i).toString())) {
				ThereIsDoublon = true;
				return true;
			}
			i++;
		}
		return false;
	}
	
	/*private boolean isColon(String StrToCheck) {
		if (StrToCheck.contains(":")) {
			ThereIsColon = true;
			return true;
		}
		return false;
	}*/
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cobListNbrTeampByPoule) {
			//setPoules();
		}
		else if (e.getSource() == butSave && ListTeam.size() > 0) {
			saveInTxtFile();
		}
		else if (e.getSource() == butGeneratePoules) {
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
			if (Found) {
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
					isAlreadySaved = false;
				}
				else if (ButPoules.get(i).getText().toString().equals("Cliquez sur le 2ème") && !ListPoules.get(i).getSelectedValue().toString().contains("1 : ")) {
					ButPoules.get(i).setText("Initaliser");
					ListTeam.get(i).set(ListPoules.get(i).getSelectedIndex(), "2 : " + ListPoules.get(i).getSelectedValue());
					ListPoules.get(i).clearSelection();
					isAlreadySaved = false;
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
