//import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


 class ManageEliminations extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	//private Random ObjRandom = new Random();--------------------------------

	private DefaultListModel<String> QualifiedList = new DefaultListModel<String>();
	private JLayeredPane PanElimination = new JLayeredPane();
	private JScrollPane SEliminationContainer = new JScrollPane(PanElimination);
	private JButton butSave = new JButton("Sauver");
	private ArrayList<Integer> SpaceSeparatorHeight = new ArrayList<Integer>();
	private ArrayList<JButton> ListButTeam = new ArrayList<JButton>();
	private ArrayList<JLabel> ListLabImage = new ArrayList<JLabel>();
	//private boolean isAlreadySaved = true;
	//private boolean ThereIsDoublon = false;
	//private boolean ThereIsColon = false;
	
	private int X = Gvar.MARGE;
	private int Y = Gvar.MARGE;	
	
	public ManageEliminations() {
		this.setLayout(null);
		PanElimination.setLayout(null);
		
		SpaceSeparatorHeight.add(30); // Liste qui contient les espaces entre deux branche differente de chaque palier
		ListLabImage.add(new JLabel(new ImageIcon("0.png")));
		ListLabImage.get(0).setBounds(Gvar.MARGE, Gvar.MARGE, 200, 90);
		PanElimination.setComponentZOrder(ListLabImage.get(0), new Integer(0));
		//PanElimination.add(ListLabImage.get(0));
		
		// Configuration JSCROLL
		SEliminationContainer.setViewportView(PanElimination);
		//SPoulesContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		SEliminationContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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
	
	private void initElimination() {
		QualifiedList = Main.fen.getOnglets().getPanManagePoules().getQualifiedList();
		for (int i = 0; i < QualifiedList.size(); i++) {
			if (i % 2 == 0 && i > 0)
				Y += SpaceSeparatorHeight.get(0);
			CreateTeamButton(QualifiedList.get(i).toString());
		}
		PanElimination.setPreferredSize(new Dimension(Main.fen.getWidth(), ListButTeam.get(ListButTeam.size() - 1).getY() + ListButTeam.get(ListButTeam.size() - 1).getHeight() + 1));
		this.validate();
		this.repaint();
	}
	
	private void CreateTeamButton(String TeamName) {
		JButton TeamButton = new JButton(TeamName);
		TeamButton.setBounds(X, Y, Gvar.BUT_WIDTH, Gvar.BUT_HEIGHT);
		ListButTeam.add(TeamButton);
		PanElimination.add(TeamButton);
		//PanElimination.setComponentZOrder(TeamButton, 1);
		Y += Gvar.BUT_HEIGHT + Gvar.MARGE;
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
}
