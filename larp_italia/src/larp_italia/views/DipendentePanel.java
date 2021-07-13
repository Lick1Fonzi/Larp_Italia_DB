/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.views;

import javax.swing.JPanel;

import larp_italia.DB;

import javax.swing.JLabel;
/**
 *
 * @author giokk
 */
public class DipendentePanel extends JPanel{
    Frame mainFrame;
    
    public DipendentePanel(Frame mainFrame,DB db){
        this.mainFrame = mainFrame;

        JLabel dipendente = new JLabel("Dipendente");
        
        this.add(dipendente);
        this.setVisible(true);
    }
}
