import javax.swing.DefaultListModel;


public class Gvar {
	public static final int FEN_WIDTH = 800; //largeur fenetre
	public static final int FEN_HEIGHT = 600; //hauteur fenetre
	public static final int MARGE = 20; //marge avec le bord et/ou les differents objet (ex : boutons, listes...)
	public static final int BUT_WIDTH = 160; //largeur standard d'un bouton
	public static final int BUT_HEIGHT = 40; //hauteur standard d'un bouton
	public static final int LAB_HEIGHT = 20; //hauteur standard d'un label
	public static final int FOOTER_HEIGHT = BUT_HEIGHT + (MARGE * 2); //Position en hauteur des boutons Menu et Fermer
	public static final String FILE_NAME_TEAM = "ListeDesEquipes.txt"; //Nom du fichier dans lequel on stock / on importe les equipes
	public static final String FILE_NAME_TEAM_SELECTED = "ListeDesEquipesSelectionnees.txt";
	public static final int MIN_TEAM_POULES = 3; //Nombre minimum d'equipe par poule
	public static final int MAX_TEAM_POULES = 6; //Nombre maximum d'equipe par poule
	public static final int POULE_HEIGHT = 150; // Hauteur de la liste d'une poule
	public static final int POULE_WIDTH = 170; // Hauteur de la liste d'une poule
	
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
	
	public static DefaultListModel<String> listSelected = new DefaultListModel<String>(); 
	
}
