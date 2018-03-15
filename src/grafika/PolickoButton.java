/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika;

import static logika.Entity.CERVENY;
import static logika.Entity.MODRY;
import static logika.Entity.NIKDO;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import logika.Entity;
import logika.Hra;

/**
 *
 * @author Filip Gregor
 */
public class PolickoButton extends JButton {
	/** uid. */
	private static final long	serialVersionUID	= 1L;

	private final Hra hra;

	private class Vybrano implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Entity hrac;
			if (hra.kdoHraje())
				hrac = CERVENY;
			else
				hrac = MODRY;
			if (obsad(hrac))
				hra.tah(souradX, souradY);
		}
	}

	private final int	souradX;
	private final int	souradY;
	private Entity		hrac;

	PolickoButton(int souradX, int souradY, Hra hra) {
		super(Entity.NIKDO.getIkona());
		this.hra = hra;
		hrac = NIKDO;
		this.setPreferredSize(new Dimension(hrac.getIkona().getIconWidth(), hrac.getIkona().getIconHeight()));
		this.souradX = souradX;
		this.souradY = souradY;
		this.addActionListener(new Vybrano());
		this.doLayout();
		this.repaint();
	}

	public boolean obsad(Entity kdo) {
		if (hrac == NIKDO) {
			this.hrac = kdo;
			this.setIcon(hrac.getIkona());
			repaint();
			return true;
		} else
			return false;
	}

	public Dimension getSouradnice() {
		return new Dimension(souradX, souradY);
	}

	public void reset() {
		this.hrac = NIKDO;
	}
}