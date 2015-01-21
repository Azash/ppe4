//import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;


 class ManageEliminations extends JPanel implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	//private Random ObjRandom = new Random();--------------------------------

	private DefaultListModel<String> QualifiedList = new DefaultListModel<String>();
	private JLayeredPane PanElimination = new JLayeredPane();
	private JScrollPane SEliminationContainer = new JScrollPane(PanElimination);
	private JButton butSave = new JButton("Sauver");
	private ArrayList<Integer> SpaceSeparatorHeight = new ArrayList<Integer>();
	private ArrayList<JLabel> ListLabTeam = new ArrayList<JLabel>();
	//private ArrayList<JLabel> ListLabImage = new ArrayList<JLabel>();
	//private boolean isAlreadySaved = true;
	//private boolean ThereIsDoublon = false;
	//private boolean ThereIsColon = false;
	
	private int X = Gvar.MARGE;
	private int Y = 0;	
	
	public ManageEliminations() {
		this.setLayout(null);
		PanElimination.setLayout(null);
		
		SpaceSeparatorHeight.add(0); // Liste qui contient les espaces entre deux branche differente de chaque palier
		
		//ListLabImage.add(new JLabel(new ImageIcon("0.png"))); // 2
		//ListLabImage.add(new JLabel(new ImageIcon("1.png"))); // 4
		//ListLabImage.add(new JLabel(new ImageIcon("2.png"))); // 8
		//ListLabImage.add(new JLabel(new ImageIcon("3.png"))); // 16
		
		// Configuration JSCROLL
		SEliminationContainer.setViewportView(PanElimination);
		//SPoulesContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//SEliminationContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		butSave.addActionListener(this);
		
		this.add(SEliminationContainer);
		this.add(butSave);
		
		objSetBounds(); //Positionne les objets dans le panel
		
		//parseTxtFile();
		
		//if (ThereIsDoublon)
		//	JOptionPane.showMessageDialog(this, "<html>Il semble qu'il y ai des doublons<br />Les doubons n'ont donc pas été pris en compte.</html>", "Fichier " + Gvar.FILE_NAME_POULES + " altéré", JOptionPane.ERROR_MESSAGE);
		//if (ThereIsColon)
		//	JOptionPane.showMessageDialog(this, "<html>Une ou plusieurs équipes semble contenir le caractère \":\"<br />Elles n'ont pas été prisent en compte.</html>", "Fichier " + Gvar.FILE_NAME_POULES + " altéré", JOptionPane.ERROR_MESSAGE);
		
	}
	
	public void objSetBounds() {
		SEliminationContainer.setBounds(0, 0, Gvar.ONG_WIDTH - 4, Gvar.ONG_HEIGHT - ((2*Gvar.MARGE) + (1*Gvar.BUT_HEIGHT) + Gvar.ONG_HEADER_HEIGHT));
		butSave.setBounds(Gvar.MARGE, SEliminationContainer.getY() + SEliminationContainer.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
	}
	
	public void initElimination() {
		PanElimination.removeAll();
		QualifiedList = Main.fen.getOnglets().getPanManagePoules().getQualifiedList();
		if (QualifiedList.size() > 0) {
			int PowerOfTwo = getPowerOfTwo(QualifiedList.size());
			//System.out.println("PowerOfTwo = " + PowerOfTwo);
			int i;
			int ImgNum = 0;
			int Level = 2;
			int Height = 100;
			int MaxPrefferedHeight = 0;
			int MaxPrefferedWidth = 0;
			int Ytmp = 0;
			while (Level <= PowerOfTwo) {
				System.out.println("--- Par " + Level + " ... " + PowerOfTwo);
				i = 0;
				Y = 0;
				while (i < PowerOfTwo) {
					//System.out.println(i + "+" + ImgNum);
					JLabel TmpLab = new JLabel(new ImageIcon(ImgNum + ".png")); 
					TmpLab.setBounds(Gvar.MARGE + (200 * ImgNum), Y, 200, Height);
					MaxPrefferedHeight = TmpLab.getHeight() + TmpLab.getY();
					MaxPrefferedWidth = TmpLab.getWidth() + TmpLab.getX();
					PanElimination.add(TmpLab, 0);
					System.out.println("Valeur de i : " + i);
					
					JLabel TmpTeamLab = new JLabel("Hé BIM ! " + i);
					TmpTeamLab.setBounds(Gvar.MARGE + (200 * ImgNum), Y + Ytmp + 7, 200, 20);
					TmpTeamLab.addMouseListener(this);
					TmpTeamLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					TmpTeamLab.setForeground(Color.black);
					ListLabTeam.add(TmpTeamLab);
					PanElimination.add(TmpTeamLab, 0);
					System.out.println("Bim 1 setted " + i);
					
					TmpTeamLab = new JLabel("BouM ! " + i);
					TmpTeamLab.setBounds(Gvar.MARGE + (200 * ImgNum), Y + TmpLab.getHeight() - (43 + Ytmp), 200, 20);
					TmpTeamLab.addMouseListener(this);
					TmpTeamLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					TmpTeamLab.setForeground(Color.black);
					ListLabTeam.add(TmpTeamLab);
					PanElimination.add(TmpTeamLab, 0);
					System.out.println("Boum 2 setted " + i);
					Y += TmpLab.getHeight();
					i += Level;
				}
				System.out.println("??? " + Ytmp);
				if (Level == 2)
					Ytmp = 25;
				if (Level > 2)
					Ytmp = (Ytmp * 2) + 26;
				ImgNum++;
				Level *= 2;
				Height *= 2;
			}
			Y = Gvar.MARGE / 2;
			for (i = 0; i < QualifiedList.size(); i++) {
				if (i % 2 == 0 && i > 0)
					Y += SpaceSeparatorHeight.get(0);
				//CreateTeamLabel(QualifiedList.get(i).toString());
				//Y += Gvar.BUT_HEIGHT + Gvar.MARGE;
			}
			/*Y = (Gvar.MARGE / 2) + (Gvar.BUT_HEIGHT + Gvar.MARGE);
			for (i = 0; i < PowerOfTwo / 2; i++) {
				if (i + (PowerOfTwo / 2) < QualifiedList.size()) {
					if (i % 2 == 0 && i > 0)
						Y += SpaceSeparatorHeight.get(0);
					CreateTeamLabel(QualifiedList.get(i + (PowerOfTwo / 2)).toString());
				}
			}*/
			if  (ListLabTeam.size() > 0)
				PanElimination.setPreferredSize(new Dimension(MaxPrefferedWidth + Gvar.MARGE, MaxPrefferedHeight));
		}
		this.validate();
		this.repaint();
	}
	
	private int getPowerOfTwo(int Nbr) {
		int PowerOfTwo = 1;
		while (PowerOfTwo < Nbr) {
			PowerOfTwo *= 2;
		}
		System.out.println(PowerOfTwo);
		return PowerOfTwo;
	}
	
	private void CreateTeamLabel(String TeamName) {
		//JButton TeamButton = new JButton(TeamName);
		JLabel TeamLabel = new JLabel(TeamName);
		TeamLabel.setBounds(X, Y, Gvar.BUT_WIDTH, Gvar.LAB_HEIGHT);
		TeamLabel.addMouseListener(this);
		TeamLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		TeamLabel.setForeground(Color.black);
		ListLabTeam.add(TeamLabel);
		//ListButTeam.add(TeamButton);
		PanElimination.add(TeamLabel, 0);
		Y += (Gvar.BUT_HEIGHT + Gvar.MARGE);
	}
	
	/*private void copyListTeamSelected() {
		ListTeamSelected.clear();
		for (int i = 0; i < Gvar.listSelected.size(); i++) {
			ListTeamSelected.addElement(Gvar.listSelected.getElementAt(i));
		}
	}*/
	
	/*public void saveInTxtFile() {
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
			
			String Line;
			int i = 0;
			BufferedReader Buffer;
			try {
				Buffer = new BufferedReader(new InputStreamReader(new FileInputStream(f))); 
				while((Line = Buffer.readLine()) != null) {
					if (!Line.isEmpty() && Line.contains("[Poule ")) {
						
					}
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
	
	private boolean isColon(String StrToCheck) {
		if (StrToCheck.contains(":")) {
			ThereIsColon = true;
			return true;
		}
		return false;
	}*/
	
	/*public boolean getIsAlreadySaved() {
		return isAlreadySaved;
	}*/
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butSave) {
			//saveInTxtFile();
			initElimination();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			int i = 0;
			boolean Found = false;
			//JOptionPane.showMessageDialog(this, TmpJList.getSelectedValue().toString());
			while (i < ListLabTeam.size() && !Found) {
				if (ListLabTeam.get(i) == e.getSource())
					Found = true;
				else
					i++;
			}
			if (Found) {
				System.out.println(ListLabTeam.get(i).getText().toString());
				ListLabTeam.get(i).setForeground(Color.blue);
				int TargetX = ListLabTeam.get(i).getX() + 200;
				int TargetY = 0;
				int PosX = ListLabTeam.get(i).getX();
				int PosY = ListLabTeam.get(i).getY();
				int AddY = 1;
				int ImgHeight = 102;
				int DefaultWidth = 200;
				while (PosX > DefaultWidth) {
					//if (PosX > 200)
						ImgHeight *= 2;
						DefaultWidth += 200;
					System.out.println("ValImgHeight : " + ImgHeight);
				}
				ImgHeight /= 2;
				
				if (i % 2 == 0 && i + 1 < ListLabTeam.size()) {
					ListLabTeam.get(i + 1).setForeground(Color.gray);
					TargetY = ListLabTeam.get(i).getY() + (ImgHeight / 2) ;
				}
				else if (i - 1 >= 0) {
					ListLabTeam.get(i - 1).setForeground(Color.gray);
					TargetY = ListLabTeam.get(i).getY() - (ImgHeight / 2) ;
					AddY = -1;
				}
				System.out.println(PosX + "/" + PosY + " to " + TargetX + "/" + TargetY);
				System.out.println("Inc : " + AddY);
				System.out.println("ImgHeight : " + ImgHeight);
				while (PosY != TargetY || PosX < TargetX) {
					ListLabTeam.get(i).setLocation(PosX, PosY);
					if (PosY != TargetY)
						PosY += AddY;
					if (PosX < TargetX)
						PosX++;
					/*try {
						System.out.println(PosX + " " + PosY);
						Thread.sleep(0);
						PanElimination.repaint();
						//this.repaint();
						//Main.fen.repaint();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}*/
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
