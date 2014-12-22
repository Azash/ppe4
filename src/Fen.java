import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class Fen extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panManageTeam;
	private ManageTeam ManageTeamObj;
	private int fenWidth = 800;
	private int fenHeight = 600;
	final JFrame frame = this;
	
	public Fen() {
		ManageTeamObj = new ManageTeam(); // Creation d'un objet ManageTeamObj de type ManageType
		panManageTeam = ManageTeamObj.getManagePan(fenWidth, fenHeight); // Je l'initialise en recuperant un JPanel
		
		//Exemple pour créer un panel Menu
		/*MenuObj = new ManageTeam();
		panMenu = MenuObj.getMenuPan(fenWidth, fenHeight);*/
		
		// Ici je choisi le Panel que j'affiche en premier a la création d'une fenetre
		// Genre j'aurai voulu commencer par afficher le menu si il existait j'aurai mis panMenu
		this.add(panManageTeam);
		
		// Je parametre deux trois trucs
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setBounds(700, 200, fenWidth, fenHeight);
		// Ecoute de la fenetre
		WindowListener exitListener = new WindowAdapter() {
			// Sur l'action de fermeture de la fenetre
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	DefaultListModel<String> TmpList = ManageTeamObj.getListData(); // Je recupere la list des equipes du JPanel ManageTeamObj
		    	if (panManageTeam.isVisible() && TmpList.getSize() > 0) {
			        int confirm = JOptionPane.showOptionDialog(frame,
			                "Souhaitez vous sauvegarder avant de quitter ?",
			                "Save Confirmation", JOptionPane.YES_NO_OPTION,
			                JOptionPane.QUESTION_MESSAGE, null, null, null);
			        if (confirm == JOptionPane.YES_OPTION) {
							PrintWriter writer = null; // Ce bout de code est expliqué dans ManageTeam.java car copié collé
							try {
								writer = new PrintWriter("ListeDesEquipes.txt", "UTF-8");
							} catch (FileNotFoundException | UnsupportedEncodingException e1) {
								e1.printStackTrace();
							}
							if (!writer.equals(null)) {
								for(int i = 0; i < TmpList.getSize(); i++)
									writer.println(TmpList.get(i).toString());
								writer.close();
							}
			        }
			        else {
			        	
			        }
		    	}
		    }
		};		
        frame.addWindowListener(exitListener);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}