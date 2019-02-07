package com.demineur;

import java.util.Scanner;

public class Demineur {

	// Aire de jeu
	private int[][] aireDeJeu;

	// nombre de Ligne aire de jeu
	private int nbLigne;

	// nombre de colonne aire de jeu
	private int nbColonne;

	// valeur par défaut des cellules
	private static final int valDefaut = -2;

	// valeur des cellules minées
	private static final int valMine = -1;

	public Demineur(int nbLigne, int nbColonne) {
		super();
		this.nbLigne = nbLigne;
		this.nbColonne = nbColonne;
		init();
	}

	/**
	 * Initaialisation de l'aire de jeu avec un tableau à 2 dimensions Chacune des
	 * cases du tableau est initialisé à la valeur valDefaut.
	 */
	private void init() {
		aireDeJeu = new int[nbLigne][nbColonne];
		for (int raw = 0; raw < nbLigne; raw++) {
			for (int col = 0; col < nbColonne; col++) {
				aireDeJeu[raw][col] = valDefaut;
			}
		}
	}

	/**
	 *
	 * @return true si une mine se trouve à la position (posX, posY) donc sa valeur
	 *         vaut valMine
	 */
	private boolean estMine(int posX, int posY) {
		return (aireDeJeu[posX][posY] == valMine);
	}

	/**
	 * @return le nombre de bombe nombre de bombes adjacentes à la position (posX,
	 *         posY)
	 */
	public int nbBombesAdjacentes(int posX, int posY) {
		int nbBombes = 0;
		// recherche des 8 voisins
		for (int x = (posX - 1); x <= (posX + 1); x++) {
			for (int y = (posY - 1); x <= (posY + 1); x++) {
				if ((x >= 0) && (x < nbColonne) && (y >= 0) && (y < nbLigne)) {
					if (!((x == posX) && (y == posY))) {
						if (aireDeJeu[x][y] == valMine) {
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
		System.out.println();
		for (int raw = 0; raw < nbLigne; raw++) {
			System.out.print(" ");
			for (int col = 0; col < nbColonne; col++) {
				System.out.print(" " + aireDeJeu[raw][col] + " ");
			}
			System.out.println(" \n");
		}
	}

	/**
	 * Mine l'aire de jeu en positionnant nbBombes aléatoirement sur l'aire de jeu
	 */
	public void minage(int nbBombes) {
		int nbBombesPlace = 0;
		while (nbBombesPlace != nbBombes) {
			int posX = (int) (Math.random() * (nbColonne - 1));
			int posY = (int) (Math.random() * (nbLigne - 1));
			if (!estMine(posX, posY)) {
				aireDeJeu[posX][posY] = valMine;
				nbBombesPlace++;
			}
		}
	}

	public static void main(String[] args) {
		Demineur demineur = new Demineur(5, 6);
		demineur.minage(5);
		demineur.draw();
		Scanner sc = new Scanner(System.in);
		System.out.print("posX:");
		int posX = Integer.parseInt(sc.next());
		System.out.print("posY:");
		int posY = Integer.parseInt(sc.next());
		System.out.println("nbBombesAdjacentes : " + demineur.nbBombesAdjacentes(posX, posY));
	}
}
