/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.views;

import javax.swing.JPanel;
import javax.swing.JLabel;
/**
 *
 * @author giokk
 */
public class GiocatorePanel extends JPanel{
    Frame mainFrame;
    
    public GiocatorePanel(Frame mainFrame){
        this.mainFrame = mainFrame;

        JLabel giocatore = new JLabel("Giocatore");
        
        this.add(giocatore);
        this.setVisible(true);
    }
}
