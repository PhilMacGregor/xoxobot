/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static logika.Entity.CERVENY;
import static logika.Entity.MODRY;
import static logika.Entity.NIKDO;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ai.AiPlayer;
import ai.MapResolver;
import ai.Player;
import grafika.Gui;

/**
 *
 * @author Filip Gregor
 */
public class Hra {
	private boolean hrajeCer;
	private final Gui grafika;
	private final Entity[][] sourad;
	private int volnaPole;
	private final ImageIcon remiza, modry, cerveny;
	private final int naKolik;

	private Entity aiColor;
	private AiPlayer ai;

	public Hra(int x) {

		/*
		 * NASTAVI BARVU AI
		 */
		this.aiColor = Entity.CERVENY;

		int y = x;

		if (x == 3)
			naKolik = 3;
		else {
			if (x == 4)
				naKolik = 4;
			else
				naKolik = 5;
		}

		this.ai = new AiPlayer();
		ai.setMapResolver(new StandardMapResolver());

		this.sourad = new Entity[x][y];
		this.volnaPole = x * y;
		this.hrajeCer = true;

		this.remiza = nastavObr("/obr/remiza.png");
		this.cerveny = nastavObr("/obr/cerveny.png");
		this.modry = nastavObr("/obr/modry.png");

		this.grafika = new Gui(sourad.length, sourad.length, this);
		grafika.setVisible(true);
	}

	public boolean kdoHraje() {
		return hrajeCer;
	}

	public void tah(int souradX, int souradY) {
		Entity hrac;
		if (kdoHraje())
			hrac = CERVENY;
		else
			hrac = MODRY;
		
		this.sourad[souradX][souradY] = hrac;
		volnaPole--;

		jeKonec(souradX, souradY);

		if (kdoHraje())
			hrajeCer = false;
		else
			hrajeCer = true;
		grafika.kdoHraje();
		
		if (hrac == aiColor) {
			ai.turn();
		}
	}

	public boolean jeKonec(int kurzX, int kurzY) {
		Entity konec = null;
		if (volnaPole == 0)
			konec = NIKDO;

		int rada = 0;
		Entity hrac = sourad[kurzX][kurzY];

		for (int i = 0; i < sourad.length; i++) {
			if (sourad[kurzX][i] == hrac)
				rada++;
			else
				rada = 0;
			if (rada >= naKolik)
				konec = hrac;
		}
		rada = 0;
		for (int i = 0; i < sourad.length; i++) {
			if (sourad[i][kurzY] == hrac)
				rada++;
			else
				rada = 0;
			if (rada >= naKolik)
				konec = hrac;
		}
		rada = 0;
		int kurzXX = kurzX;
		int kurzYY = kurzY;
		while ((kurzXX > 0) && (kurzYY > 0)) {
			kurzXX--;
			kurzYY--;
		}
		while ((kurzXX < sourad.length) && (kurzYY < sourad.length)) {
			if (sourad[kurzXX][kurzYY] == hrac)
				rada++;
			else
				rada = 0;
			kurzXX++;
			kurzYY++;
			if (rada >= naKolik)
				konec = hrac;
		}
		rada = 0;
		kurzXX = kurzX;
		kurzYY = kurzY;
		while ((kurzXX > 0) && (kurzYY < sourad.length - 1)) {
			kurzXX--;
			kurzYY++;
		}
		while ((kurzXX < sourad.length) && (kurzYY >= 0)) {
			if (sourad[kurzXX][kurzYY] == hrac)
				rada++;
			else
				rada = 0;
			kurzXX++;
			kurzYY--;
			if (rada >= naKolik)
				konec = hrac;
		}

		if (konec != null) {
			if (konec == NIKDO)
				JOptionPane.showMessageDialog(null, "Skončilo to remízou!", "Remíza!", INFORMATION_MESSAGE, remiza);
			if (konec == CERVENY)
				JOptionPane.showMessageDialog(null, "Vyhrál červený!", "Vitězství!", INFORMATION_MESSAGE, cerveny);
			if (konec == MODRY)
				JOptionPane.showMessageDialog(null, "Vyhrál modrý!", "Vitězství!", INFORMATION_MESSAGE, modry);
			restart();
			return true;
		} else
			return false;
	}

	public void restart() {
		grafika.setVisible(false);
		new Hra(sourad.length);
	}

	private ImageIcon nastavObr(String ikona) {
		ImageIcon ico;
		URL umisteniObrazku = this.getClass().getResource(ikona);
		if (umisteniObrazku == null) {
			JOptionPane.showMessageDialog(null, "Soubor s obrázkem " + ikona + " nebyl nalezen",
					"Chyba při načítání obrázku", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return null;
		} else {
			ico = new ImageIcon(umisteniObrazku);
			return ico;
		}
	}

	private class StandardMapResolver implements MapResolver {

		@Override
		public Player getFieldAt(int x, int y) {
			Entity ent = sourad[x][y];
			if (ent == Entity.NIKDO) {
				return Player.NONE;
			} else {
				return ent == aiColor ? Player.ME : Player.ENEMY;
			}
		}

		@Override
		public Dimension getMapSize() {
			return new Dimension(sourad.length, sourad[0].length);
		}

	}

}
