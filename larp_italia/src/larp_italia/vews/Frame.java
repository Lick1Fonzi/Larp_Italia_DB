/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.vews;

import javax.swing.JFrame;
import larp_italia.DB;

/**
 *
 * @author giokk
 */
public class Frame extends JFrame{
    public DB db;
    LoginPanel lp = new LoginPanel(this);
    
    public Frame(DB db) {
            super("Login with CF");
            this.db = db;
            
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
            this.add(lp);
            this.pack();
            this.setVisible(true);
	}
}
