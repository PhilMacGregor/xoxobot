/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logika.Hra;

/**
 *
 * @author Filip Gregor
 */
public class Gui {
	private final JFrame			oknoFrame;
	private final JPanel			plochaPanel;
	private final JPanel			spodniPanel;
	private final JTextField	infoTextField;
	private final JMenuBar		menicko;
	private final JMenuItem		newGameMenuItem;
	private final JMenuItem		konecMenuItem;
	private final Hra					hra;

	public Gui(int vyska, int sirka, Hra hra) {
		this.hra = hra;
		oknoFrame = new JFrame("Xoxo");
		oknoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oknoFrame.setSize(new Dimension(27 * sirka + 10, 27 * vyska + 100));

		Point stred = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		stred.move(stred.x - (oknoFrame.getSize().width / 2), stred.y - (oknoFrame.getSize().height / 2));
		oknoFrame.setLocation(stred);

		oknoFrame.setResizable(false);

		menicko = new JMenuBar();
		oknoFrame.setJMenuBar(menicko);
		plochaPanel = new JPanel(new GridLayout(vyska, sirka));

		spodniPanel = new JPanel();
		infoTextField = new JTextField();
		infoTextField.setEditable(false);
		spodniPanel.add(infoTextField);

		newGameMenuItem = new JMenuItem("Nová hra", KeyEvent.VK_N);
		newGameMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hra.restart();
			}
		});

		konecMenuItem = new JMenuItem("Konec", KeyEvent.VK_K);
		konecMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		menicko.add(newGameMenuItem);
		menicko.add(konecMenuItem);

		kdoHraje();

		for (int i = 0; i < vyska; i++) {
			for (int j = 0; j < sirka; j++) {
				plochaPanel.add(new PolickoButton(i, j, hra));
			}
		}

		oknoFrame.add(plochaPanel);
		oknoFrame.add(spodniPanel, BorderLayout.SOUTH);
	}

	public void kdoHraje() {
		if (hra.kdoHraje() == true) {
			infoTextField.setText("Hraje červený (křížky)  ");
			spodniPanel.setBackground(Color.red);
		}

		else {
			infoTextField.setText("Hraje modrý (kolečka)");
			spodniPanel.setBackground(Color.blue);
		}
	}

	public void setVisible(boolean viditelnost) {
		oknoFrame.setVisible(viditelnost);
	}
}
