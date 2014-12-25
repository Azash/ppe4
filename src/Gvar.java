import javax.swing.DefaultListModel;


public class Gvar {
	public static final int FEN_WIDTH = 800;
	public static final int FEN_HEIGHT = 600;
	public static final int MARGE = 20;
	public static final int BUT_WIDTH = 160;
	public static final int BUT_HEIGHT = 40;
	public static final int FOOTER_HEIGHT = BUT_HEIGHT + (MARGE * 2);
	public static final String FILE_NAME_TEAM = "ListeDesEquipes.txt";
	// Attention deux noms ne peuvent être identiques
	public static final String BUT_STR_LOAD_Menu = "Menu";
	public static final String BUT_STR_LOAD_ManageTeam = "Gestion des équipes";
	public static final String BUT_STR_LOAD_ManageSelection = "Selection des équipes";
	public static final String BUT_STR_LOAD_Close = "Fermer";
	//----------------------------------------------
	public static String CUR_PAN = "Menu";
	public static DefaultListModel<String> listData = new DefaultListModel<String>(); 
	
}
