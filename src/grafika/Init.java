/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafika;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logika.Hra;

/**
 *
 * @author Filip Gregor
 */
public class Init {
    private JButton knoflik;
    private JFrame setFrame;
    private JTextField dolniPole;
    private class Zadano implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String text=dolniPole.getText();
            int cislo;
            
            try {
            cislo = new Integer(text);
            new Hra(cislo);
            setFrame.setVisible(false);
            } catch (NumberFormatException ex) {}
        }
    }
    
    public Init(){
        setFrame = new JFrame("Počet polí");
        setFrame.setSize(320, 160);
        setFrame.setLocation(500, 300);
        setFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField horniText = new JTextField("Počet řádků a sloupců:");
        horniText.setEditable(false);
        horniText.setBorder(null);
        setFrame.add(horniText);
        JPanel dolniPanel = new JPanel();
        dolniPole = new JTextField(3);
        dolniPanel.add(dolniPole);
        knoflik = new JButton("OK");
        dolniPanel.add(knoflik);
        Zadano zadano = new Zadano();
        knoflik.addActionListener(zadano);
        dolniPole.addActionListener(zadano);
        setFrame.add(dolniPanel,BorderLayout.SOUTH);
        setFrame.setVisible(true);
        dolniPole.requestFocus();
    }
    
    
}
