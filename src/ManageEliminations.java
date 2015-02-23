//import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

 class ManageEliminations extends JPanel implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	//private Random ObjRandom = new Random();--------------------------------

	private DefaultListModel<String> QualifiedList = new DefaultListModel<String>();
	private JLayeredPane PanElimination = new JLayeredPane();
	private JScrollPane SEliminationContainer = new JScrollPane(PanElimination);
	private JButton butSave = new JButton("Sauver");
	private JButton butImport = new JButton("Importer");
	private ArrayList<Integer> SpaceSeparatorHeight = new ArrayList<Integer>();
	private ArrayList<JLabel> ListLabTeam = new ArrayList<JLabel>();
	private ArrayList<Integer> ListLabTeamNextPos = new ArrayList<Integer>();
	private ArrayList<Integer> ListLabTeamCurrPos = new ArrayList<Integer>();
	private boolean isAlreadySaved = true;
	private boolean isImport;
	//private ArrayList<JLabel> ListLabImage = new ArrayList<JLabel>();
	//private boolean isAlreadySaved = true;
	//private boolean ThereIsDoublon = false;
	//private boolean ThereIsColon = false;
	
	private ArrayList<ArrayList<JLabel>> Paliers = new ArrayList<ArrayList<JLabel>>();
	private ArrayList<ArrayList<String>> PaliersImport  = new ArrayList<ArrayList<String>>();
	
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
		butImport.addActionListener(this);
		
		this.add(SEliminationContainer);
		this.add(butSave);
		this.add(butImport);
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
		butImport.setBounds(Gvar.MARGE + butSave.getX() + butSave.getWidth(), SEliminationContainer.getY() + SEliminationContainer.getHeight() + Gvar.MARGE, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
	}
	
	public void initElimination() {
		PanElimination.removeAll();
		
		while (!Paliers.isEmpty()) {
			Paliers.remove(0);
		}
		System.out.println("");
		QualifiedList.removeAllElements();
		if (QualifiedList.size() == 0)
			QualifiedList = Main.fen.getOnglets().getPanManagePoules().getQualifiedList();
		for (int i = 0; i < QualifiedList.size(); i++) {
			QualifiedList.set(i, QualifiedList.get(i).replace("1 : ", ""));
			QualifiedList.set(i, QualifiedList.get(i).replace("2 : ", ""));
		}
		//copyListQualified();
		if (QualifiedList.size() > 0) {
			int PowerOfTwo = getPowerOfTwo(QualifiedList.size());
			//System.out.println("PowerOfTwo = " + PowerOfTwo);
			int ImgNum = 0;
			int Level = 2;
			int Height = 100;
			int i = 0;
			int MaxPrefferedHeight = 0;
			int MaxPrefferedWidth = 0;
			int Ytmp = 0;
			int CountPos = 0;
			while (Level <= PowerOfTwo) {
				System.out.println("--- Par " + Level + " ... " + PowerOfTwo);
				i = 0;
				Y = 0;
				CountPos = 0;
				ArrayList<JLabel> Labels = new ArrayList<JLabel>();
				ArrayList<Integer> PosInt = new ArrayList<Integer>();
				while (i < PowerOfTwo) {
					
					
					//System.out.println(i + "+" + ImgNum);
					JLabel TmpLab = new JLabel(new ImageIcon(getClass().getResource(ImgNum + ".png"))); 
					TmpLab.setBounds(Gvar.MARGE + (200 * ImgNum), Y, 200, Height);
					MaxPrefferedHeight = TmpLab.getHeight() + TmpLab.getY();
					MaxPrefferedWidth = TmpLab.getWidth() + TmpLab.getWidth() + TmpLab.getX();
					PanElimination.add(TmpLab, 0);
					System.out.println("Valeur de i : " + i + " " + CountPos);
					
					JLabel TmpTeamLab;
					TmpTeamLab = new JLabel("");
					TmpTeamLab.setBounds(Gvar.MARGE + (200 * ImgNum), Y + Ytmp + 7, 200, 20);
					TmpTeamLab.addMouseListener(this);
					TmpTeamLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					TmpTeamLab.setForeground(Color.black);
					ListLabTeam.add(TmpTeamLab);
					
					Labels.add(TmpTeamLab);
					
					PanElimination.add(TmpTeamLab, 0);
					//System.out.println("Bim 1 setted " + i);
					ListLabTeamNextPos.add(i);
					ListLabTeamCurrPos.add(i);

					TmpTeamLab = new JLabel("");
					TmpTeamLab.setBounds(Gvar.MARGE + (200 * ImgNum), Y + TmpLab.getHeight() - (43 + Ytmp), 200, 20);
					TmpTeamLab.addMouseListener(this);
					TmpTeamLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					TmpTeamLab.setForeground(Color.black);
					ListLabTeam.add(TmpTeamLab);
					
					Labels.add(TmpTeamLab);
					//Next.get(Next.size() - 1).set(1, CountPos);
					
					PanElimination.add(TmpTeamLab, 0);
					//System.out.println("Boum 2 setted " + i);
					ListLabTeamNextPos.add(i);
					ListLabTeamCurrPos.add(i + 1);
					
					Y += TmpLab.getHeight();
					i += Level;
					CountPos++;
					System.out.println("Next de i : " + i + " " + CountPos);
				}
				//System.out.println("??? " + Ytmp);
				if (Level == 2)
					Ytmp = 25;
				if (Level > 2)
					Ytmp = (Ytmp * 2) + 26;
				Paliers.add(Labels);
				PosInt.add(0);
				PosInt.add(0);
				
				ImgNum++;
				Level *= 2;
				Height *= 2;
				if (Level > PowerOfTwo) {
					ArrayList<JLabel> LastLabels = new ArrayList<JLabel>();
					JLabel TmpTeamLab;
					TmpTeamLab = new JLabel("");
					TmpTeamLab.setBounds(Gvar.MARGE + (200 * ImgNum), Ytmp + 7, 200, 20);
					TmpTeamLab.addMouseListener(this);
					TmpTeamLab.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					TmpTeamLab.setForeground(Color.black);
					ListLabTeam.add(TmpTeamLab);
					
					LastLabels.add(TmpTeamLab);
					
					PanElimination.add(TmpTeamLab, 0);
					Paliers.add(LastLabels);
				}
			}
			initQualifiedTeams();
			if  (ListLabTeam.size() > 0)
				PanElimination.setPreferredSize(new Dimension(MaxPrefferedWidth + Gvar.MARGE, MaxPrefferedHeight));
		}
		isImport = false;
		this.validate();
		this.repaint();
	}
	
	private void initQualifiedTeams() {
		JLabel TmpLab;
		int i;
		System.out.println("ICI 1");
		if (isImport) {
			System.out.println("Nb Palier " + Paliers.size());
			for (i = 0; i < Paliers.size(); i++) {
				System.out.println("Nb Team / Palier " + Paliers.get(i).size());
				for (int j = 0; j < Paliers.get(i).size(); j++) {
					TmpLab = Paliers.get(i).get(j);
					TmpLab.setText(PaliersImport.get(i).get(j));
				}
				/*System.out.println("///////////// " + QualifiedList.get(i));
				TmpLab = Paliers.get(0).get(i);
				
				//QualifiedList.remove(0);
				System.out.println("///////////// " + QualifiedList.get(i + 1));
				TmpLab = Paliers.get(0).get(Paliers.get(0).size() - (1 + i));
				TmpLab.setText(QualifiedList.get(1 + i));
				//QualifiedList.remove(0);*/
			}
			System.out.println("ICI 2");
		}
		else {
			for (i = 0; i < QualifiedList.size(); i += 2) {
				//System.out.println("///////////// " + QualifiedList.get(i));
				TmpLab = Paliers.get(0).get(i);
				TmpLab.setText(QualifiedList.get(i));
				//QualifiedList.remove(0);
				//System.out.println("///////////// " + QualifiedList.get(i + 1));
				TmpLab = Paliers.get(0).get(Paliers.get(0).size() - (1 + i));
				TmpLab.setText(QualifiedList.get(1 + i));
				//QualifiedList.remove(0);
			}
		}
	}
	
	private int getPowerOfTwo(int Nbr) {
		int PowerOfTwo = 1;
		while (PowerOfTwo < Nbr) {
			PowerOfTwo *= 2;
		}
		System.out.println(PowerOfTwo);
		return PowerOfTwo;
	}
	
	/*private void CreateTeamLabel(String TeamName) {
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
	}*/
	
	/*private void copyListQualified() {
		QualifiedList.clear();
		for (int i = 0; i < Main.fen.getOnglets().getPanManagePoules().getQualifiedList().size(); i++) {
			QualifiedList.addElement(Main.fen.getOnglets().getPanManagePoules().getQualifiedList().get(i));
		}
	}*/
	
	public void saveInTxtFile() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(Gvar.FILE_NAME_ELIMINATION, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (!writer.equals(null)) {
			for(int i = 0; i < Paliers.size(); i++) {
				if (i != 0)
					writer.println("[" + (i + 1) + "ème tour]");
				else
					writer.println("[" + (i + 1) + "er tour]");
				for (int j = 0; j < Paliers.get(i).size(); j++) {
					if (Paliers.get(i).get(j).getText().toString().equals(""))
						writer.println("-");
					else
						writer.println(Paliers.get(i).get(j).getText().toString());
					if (j % 2 != 0)
						writer.println("--------------");
					
				}
				writer.println("");
			}
			
			writer.close();
			isAlreadySaved = true;
			UploadToFtp(Gvar.FILE_NAME_ELIMINATION);
		}
	}
	
	private void parseTxtFile() {
		File f = new File("./" + Gvar.FILE_NAME_ELIMINATION);
		if(f.exists() && !f.isDirectory()) {
			isImport = true;
			while (!Paliers.isEmpty()) {
				Paliers.remove(0);
			}
			String Line;
			int i = 0;
			BufferedReader Buffer;
			try {
				System.out.println("IMPORT DU FICHIER DEBUT");
				boolean isFirstTour = false;
				Buffer = new BufferedReader(new InputStreamReader(new FileInputStream(f))); 
				ArrayList<String> LabelsStr = new ArrayList<String>();
				QualifiedList.removeAllElements();
				while((Line = Buffer.readLine()) != null) {
					if (Line.contains(" tour]") && Line.contains("er")) {
						System.out.println("Premier tour trouvé !!!");
						isFirstTour = true;
					}
					else if (Line.contains(" tour]")) {
						System.out.println("Nouveau TOUR !!!");
						isFirstTour = false;
						
						if (i > 0)
							PaliersImport.add(LabelsStr);
						LabelsStr = new ArrayList<String>();
							
						i++;
					}
						
					if (isFirstTour  && !Line.contains("------") && !Line.contains(" tour]") && !Line.isEmpty()) {
						if (Line.equals("-"))
							QualifiedList.addElement("");
						else
							QualifiedList.addElement(Line);
						System.out.println("QualifiedList " + QualifiedList.size() + " : " + QualifiedList.get(QualifiedList.size() - 1));
					}
					if(!Line.contains("------") && !Line.contains(" tour]") && !Line.isEmpty()) {
						if (Line.equals("-"))
							LabelsStr.add("");
						else
							LabelsStr.add(Line);
					}
					i++;

				}
				PaliersImport.add(LabelsStr);
				Buffer.close();
				initElimination();
				System.out.println("IMPORT DU FICHIER FINI");
				/*for( i = 0; i < QualifiedList.size(); i++) {
					System.out.println(QualifiedList.get(i));
				}*/
			} catch (IOException e) { } 
			
		}
	}
	
	/*private boolean isDoublon(String StrToCheck, DefaultListModel<String> ListToCheck) {
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
	
	public ArrayList<ArrayList<JLabel>> getListTeam() {
		return Paliers;
	}
	
	public boolean getIsAlreadySaved() {
		return isAlreadySaved;
	}
	
	
	public void UploadToFtp(String NameFile) {
        String server = "ftp.guillaumeboudy.com";
        int port = 21;
        String user = "guillaumc";
        String pass = "tfsgJXVZ";
        FTPClient client = new FTPClient();

        try {
        	System.out.println("Connexion en cours...");
        	client.connect(server, port);
        	System.out.println(" OK\nAuthentification en cours...");
        	client.login(user, pass);
        	System.out.println(" OK\nPassive Mode en cours...");
        	client.enterLocalPassiveMode();
        	System.out.println(" OK\nSet FilType en cours...");
        	client.setFileType(FTP.BINARY_FILE_TYPE);
        	System.out.println(" OK\nChange directory...");
        	client.changeWorkingDirectory("www/GestionTournoi/");
        	System.out.println(" OK\n...");
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File("./" + NameFile);
 
            InputStream inputStream = new FileInputStream(firstLocalFile);
 
            System.out.println("Start uploading first file");
            boolean done = client.storeFile(NameFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }
 
        } catch (FTPConnectionClosedException e) {          
            System.err.println("ERROR :: FTP Server Unreachable");       
        } catch (SocketException e) {
            System.err.println("ERROR :: FTP Server Unreachable");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            //e.printStackTrace();
        } catch (Exception e) {
        	System.err.println("ERROR : " + e.getMessage());
		} finally {
            try {
                if (client.isConnected()) {
                	client.logout();
                	client.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
	//Declenché lorsque un bouton est cliqué
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == butSave) {
			saveInTxtFile();
			//initElimination();
		}
		else if (e.getSource() == butImport) {
			parseTxtFile();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource() instanceof JLabel) {
			isAlreadySaved = false;
			if (e.getButton() == MouseEvent.BUTTON3) {
				System.out.println("RIGHT");
				JLabel TmpJlabel = (JLabel) e.getSource();
				TmpJlabel.setText("");
				TmpJlabel.setForeground(Color.black);
			}
			else {
				int i = 0;
				boolean Found = false;
				while (i < Paliers.size() && !Found) {
					int j = 0;
					while (j < Paliers.get(i).size() && !Found) {
						if (e.getSource() == Paliers.get(i).get(j)) {
							JLabel TmpJlabel;
							if (j % 2 == 0 && i + 1 < Paliers.size()) {
								TmpJlabel = (JLabel) Paliers.get(i).get(j + 1);
								TmpJlabel.setForeground(Color.gray);
							}
							else if (i + 1 < Paliers.size()){
								TmpJlabel = (JLabel) Paliers.get(i).get(j - 1);
								TmpJlabel.setForeground(Color.gray);
							}
							TmpJlabel = (JLabel) Paliers.get(i).get(j);
							TmpJlabel.setForeground(Color.blue);
							
							//System.out.println(TmpJlabel.getText().toString() + " --- j/2="+ (j/2));
							if (i + 1 < Paliers.size()) {
								JLabel TmpNextJlabel = (JLabel) Paliers.get(i + 1).get(j/2);
								if (TmpNextJlabel.getText().equals(""))
									TmpNextJlabel.setText(TmpJlabel.getText());
								System.out.println("Palier("+Paliers.size()+") : " + (i+1) + "\n NextObject(" + Paliers.get(i + 1).size() + ") : " + (j/2));
							}
							Found = true;
						}
						j++;
					}
					i++;
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
