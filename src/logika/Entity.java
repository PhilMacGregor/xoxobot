/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Filip Gregor
 */
public enum Entity {
    
MODRY("/obr/o.png"),
CERVENY("/obr/x.png"),
NIKDO("/obr/n.png");

    private ImageIcon ikona;

    private Entity(String ikona){
        this.ikona=nastavObr(ikona);
    }

    private ImageIcon nastavObr(String ikona){
        ImageIcon ico;
        URL umisteniObrazku = this.getClass().getResource(ikona);
        if (umisteniObrazku == null) {
            JOptionPane.showMessageDialog(null, "Soubor s obrázkem " + ikona + " nebyl nalezen", "Chyba při načítání obrázku", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        } else {
            ico = new ImageIcon(umisteniObrazku);
            return ico;
        }
    }
    
    public ImageIcon getIkona(){
        return ikona;
    }
}