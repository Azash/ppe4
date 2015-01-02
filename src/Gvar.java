import javax.swing.DefaultListModel;

//Cette classe est en gros notre super global qu'on appel de n'importe ou.
//On peu la considerer un peu comme une feuille CSS en Web elle sert a la mise en page
//MAIS PAS SEULEMENT : dedans on y mettra egalement les fonction dont on a besoin un peu partout exemple : isDoublon, isColon, parseTxtFile, etc...

public class Gvar {
	public static final int DEFAULT_FEN_WIDTH = 800; //largeur standard de la fenetre
	public static final int DEFAULT_FEN_HEIGHT = 600; //hauteur standard de la fenetre
	public static int CURRENT_FEN_WIDTH = DEFAULT_FEN_WIDTH; //largeur courante de la fenetre
	public static int CURRENT_FEN_HEIGHT = DEFAULT_FEN_HEIGHT; //hauteur courante de la fenetre
	public static final int MARGE = 10; //marge avec le bord et/ou les differents objet (ex : boutons, listes...)
	public static final int BUT_WIDTH = 160; //largeur standard d'un bouton
	public static final int BUT_SQUARE_WIDTH = 50; //largeur d'un bouton de forme a peu pres carre
	public static final int BUT_SLIM_HEIGHT = 20; //hauteur standard d'un bouton
	public static final int BUT_HEIGHT = 40; //hauteur standard d'un bouton
	public static final int LAB_HEIGHT = 20; //hauteur standard d'un label
	public static final int FOOTER_HEIGHT = BUT_HEIGHT + (MARGE * 2); //Position en hauteur des boutons Menu et Fermer
	public static final String FILE_NAME_TEAM = "ListeDesEquipes.txt"; //Nom du fichier dans lequel on stock / on importe les equipes
	public static final String FILE_NAME_TEAM_SELECTED = "ListeDesEquipesSelectionnees.txt"; //Nom du fichier dans lequel on stock / on importe les equipes selectionnees
	public static final String FILE_NAME_POULES = "ListeDesPoules.txt"; //Nom du fichier dans lequel on stock / on importe les poules
	public static final int MIN_TEAM_POULES = 3; //Nombre minimum d'equipe par poule
	public static final int MAX_TEAM_POULES = 6; //Nombre maximum d'equipe par poule
	public static final int POULE_HEIGHT = 140; // Hauteur de la liste d'une poule
	public static final int POULE_WIDTH = 170; // Hauteur de la liste d'une poule
	
	public static final int ONG_HEADER_HEIGHT = 28 + MARGE;
	public static int ONG_WIDTH = CURRENT_FEN_WIDTH - 7; //largeur du block onglet
	public static int ONG_HEIGHT = CURRENT_FEN_HEIGHT - (ONG_HEADER_HEIGHT - MARGE); //hauteur du block onglet (28 : prend en compte l'intitule des onglets)
	// Attention deux noms ne peuvent être identiques
	// Correspond au NOM du Panel et ce qu'on AFFICHE SUR LE BOUTON
	public static final String BUT_STR_LOAD_ManageTeam = "Gestion des équipes";
	public static final String BUT_STR_LOAD_ManageSelection = "Selection des équipes";
	public static final String BUT_STR_LOAD_ManagePoules = "Gestion des poules";
	public static final String BUT_STR_LOAD_ManageEliminations = "Gestion des éliminations";
	//public static final String BUT_STR_LOAD_Close = "Fermer"; on vera si on remet un bouton fermer
	//----------------------------------------------
	
	//Contient le nom du panel sur lequel on se trouve. Au lancement du programme : BUT_STR_LOAD_Menu : "Menu"
	public static String FEN_TITLE = "Gestion de tournoi";
	
	//liste qui contient les équipes enregistrees
	public static DefaultListModel<String> listData = new DefaultListModel<String>(); 
	//liste qui contient les équipes selectionnees
	public static DefaultListModel<String> listSelected = new DefaultListModel<String>(); 
	
}
