

import java.util.Scanner;

public class Demineur {

	// Aire de jeu
	private int[][] aireDeJeu;

	// Tableau des cases découvertes
	private boolean[][] decouvertes;

	// nombre de Ligne aire de jeu
	private int nbLigne;

	// nombre de colonne aire de jeu
	private int nbColonne;

	// valeur par défaut des cellules
	private static final int valDefaut = -2;

	// valeur des cellules minées
	private static final int valMine = -1;

	// nombre de ligne max
	private static final int nbLigneMax = 10;

	// nombre de colonne max
	private static final int nbcolonneMax = 10;

	// fin de partie
	private boolean isLost;

	// Scanner
	private static Scanner scanner = new Scanner(System.in);;

	public Demineur() {
		super();
	}

	public Demineur(int nbLigne, int nbColonne) {
		super();
		this.nbLigne = nbLigne;
		this.nbColonne = nbColonne;
		isLost = false;
		init();
	}

	public int getNbLigne() {
		return nbLigne;
	}

	public void setNbLigne(int nbLigne) {
		this.nbLigne = nbLigne;
	}

	public int getNbColonne() {
		return nbColonne;
	}

	public void setNbColonne(int nbColonne) {
		this.nbColonne = nbColonne;
	}

	/**
	 * Initaialisation de l'aire de jeu avec un tableau à 2 dimensions Chacune des
	 * cases du tableau est initialisé à la valeur valDefaut.
	 */
	private void init() {
		aireDeJeu = new int[nbLigne][nbColonne];
		decouvertes = new boolean[nbLigne][nbColonne];
		for (int ligne = 0; ligne < nbLigne; ligne++) {
			for (int colonne = 0; colonne < nbColonne; colonne++) {
				decouvertes[ligne][colonne] = false;
				aireDeJeu[ligne][colonne] = valDefaut;
			}
		}
	}

	/**
	 *
	 * @return true si une mine se trouve à la position (posLigne, posYcolonne) donc
	 *         sa valeur vaut valMine
	 */
	private boolean estMine(int posLigne, int posColonne) {
		return (aireDeJeu[posLigne][posColonne] == valMine);
	}

	/**
	 * @return le nombre de bombe nombre de bombes adjacentes à la position
	 *         (posLigne, posColonne)
	 */
	public int nbBombesAdjacentes(int posLigne, int posColonne) {
		int nbBombes = 0;
		// recherche des 8 voisins
		for (int ligne = (posLigne - 1); ligne <= (posLigne + 1); ligne++) {
			for (int colonne = (posColonne - 1); colonne <= (posColonne + 1); colonne++) {
				if ((ligne >= 0) && (ligne < nbLigne) && (colonne >= 0) && (colonne < nbColonne)) {
					if (!((ligne == posLigne) && (colonne == posColonne))) {
						if (estMine(ligne, colonne)) {
							nbBombes++;
						}
					}
				}
			}
		}
		return nbBombes;
	}

	/**
	 * Affiche l'aire de jeu
	 */
	public void draw() {
		String separator = "";
		for (int k = 0; k < nbColonne; k++) {
			separator += "----";
		}

		System.out.print("   ");
		for (int i = 0; i < nbColonne; i++) {
			System.out.print("[" + i + "] ");
		}
		System.out.println("\n   " + separator);

		for (int i = 0; i < nbLigne; i++) {
			System.out.print("[" + i + "] ");
			for (int j = 0; j < nbColonne; j++) {
				if (decouvertes[i][j]) {
					if (!estMine(i, j)) {
						System.out.print(nbBombesAdjacentes(i, j) + " | ");
					} else {
						System.out.print("*" + " | ");
					}

				} else {
					System.out.print("-" + " | ");
				}
			}
			System.out.println("\n   " + separator);
		}
	}

	/**
	 * Mine l'aire de jeu en positionnant nbBombes aléatoirement sur l'aire de jeu
	 */
	public void minage(int nbBombes) {
		int nbBombesPlace = 0;
		while (nbBombesPlace != nbBombes) {
			int posLigne = (int) (Math.random() * (nbLigne - 1));
			int posColonne = (int) (Math.random() * (nbColonne - 1));
			if (!estMine(posLigne, posColonne)) {
				aireDeJeu[posLigne][posColonne] = valMine;
				nbBombesPlace++;
			}
		}
	}

	/**
	 * Lance le jeu
	 */
	public void run() {
		System.out.println("------ Initialisation du jeu ------");
		// Demande du nombre de ligne et du nombre de colonne
		System.out.print("Nombre de ligne (max " + nbLigneMax + ") : ");
		int nbLigne = Integer.parseInt(scanner.next());
		System.out.print("Nombre de colone (max " + nbcolonneMax + ") : ");
		int nbColonne = Integer.parseInt(scanner.next());
		// Initialisation aire de jeu
		if ((nbLigne > nbLigneMax) || (nbColonne > nbcolonneMax)) {
			System.out.println("Mauvais nombre de ligne ou de colonne!");
			return;
		}
		this.setNbLigne(nbLigne);
		this.setNbColonne(nbColonne);
		init();
		// Placage des bombes
		System.out.print("Nombre de bombes (max " + (nbLigne * nbColonne) / 2 + ") : ");
		int nbBombes = Integer.parseInt(scanner.next());
		if (nbBombes > (nbLigne * nbColonne) / 2) {
			System.out.println("Mauvais nombre de bombe!");
			return;
		}
		this.minage(nbBombes);
		System.out.println("------- fin initialisation -------");
		draw();
		boolean stop = false;
		while (!stop) {
			run1Tour();
			draw();
			if (isWin()) {
				stop = true;
				System.out.println("Bravo Vous avez gagné :( felicitations!");
			} else if (!isLost) {
				System.out.print("continuer o/n ? : ");
				String rep = scanner.next();
				if (!rep.toLowerCase().startsWith("o")) {
					stop = true;
				}
			} else {
				stop = true;
			}
		}
		if (isLost) {
			System.out.println("Vous avez perdu :( désolé!");
		} else {
			System.out.println("Aurevoir !");
		}
	}

	/**
	 * Fait jouer un tour
	 */
	private void run1Tour() {
		System.out.println("------ Découvrir une case ------");
		System.out.print("Coordonnées colonne : ");
		int colonne = Integer.parseInt(scanner.next());
		System.out.print("Coordonnées ligne : ");
		int ligne = Integer.parseInt(scanner.next());
		decouvertes[ligne][colonne] = true;
		if (estMine(ligne, colonne)) {
			isLost = true;
		}
		System.out.println("--------------------------------");
	}

	/**
	 * Verifie si on a gagné
	 */
	private boolean isWin() {
		boolean win = true;
		for (int i = 0; i < nbLigne; i++) {
			for (int j = 0; j < nbColonne; j++) {
				if (!decouvertes[i][j]) {
					return false;
				}
			}
		}
		return win;
	}

	public static void main(String[] args) {
		Demineur demineur = new Demineur();
		demineur.run();
	}
}
