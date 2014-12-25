import javax.swing.DefaultListModel;


public class Gvar {
	public static final int FEN_WIDTH = 800; //largeur fenetre
	public static final int FEN_HEIGHT = 600; //hauteur fenetre
	public static final int MARGE = 20; //marge avec le bord et/ou les differents objet (ex : boutons, listes...)
	public static final int BUT_WIDTH = 160; //largeur standard d'un bouton
	public static final int BUT_HEIGHT = 40; //hauteur standard d'un bouton
	public static final int FOOTER_HEIGHT = BUT_HEIGHT + (MARGE * 2); //Position en hauteur des boutons Menu et Fermer
	public static final String FILE_NAME_TEAM = "ListeDesEquipes.txt"; //Nom du fichier dans lequel on stock / on importe les equipes
	
	// Attention deux noms ne peuvent être identiques
	// Correspond au NOM du Panel et ce qu'on AFFICHE SUR LE BOUTON
	public static final String BUT_STR_LOAD_Menu = "Menu"; 
	public static final String BUT_STR_LOAD_ManageTeam = "Gestion des équipes";
	public static final String BUT_STR_LOAD_ManageSelection = "Selection des équipes";
	public static final String BUT_STR_LOAD_ManagePoules = "Gestion des poules";
	public static final String BUT_STR_LOAD_Close = "Fermer";
	//----------------------------------------------
	
	//Contient le nom du panel sur lequel on se trouve, initaliement BUT_STR_LOAD_Menu : "Menu"
	public static String CUR_PAN = "Menu";
	
	//liste qui va contenir les équipes. Elle est affiché dans le JList (teamList)
	//Et c'est sur cette liste (listData) qu'on fait des ajouts ou des suppressions, PAS SUR la JList (teamList)
	public static DefaultListModel<String> listData = new DefaultListModel<String>(); 
	
}
