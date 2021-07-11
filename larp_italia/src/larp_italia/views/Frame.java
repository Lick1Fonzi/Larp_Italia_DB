/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.views;

import javax.swing.JFrame;
import larp_italia.DB;
import larp_italia.User.types;

/**
 *
 * @author giokk
 */
public class Frame extends JFrame{
    public DB db;

    LoginPanel lp = new LoginPanel(this);
    DipendentePanel dp;
    GiocatorePanel gp;
    
    public Frame(DB db) {
        super("Login with CF");
        this.db = db;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(lp);
        this.pack();
        this.setVisible(true);
	}

    public void switchPanel(types type){
        this.remove(lp);
        if(type == types.GIOCATORE){
            gp = new GiocatorePanel(this);
            this.add(gp);
        }else{
            dp = new DipendentePanel(this);
            this.add(dp);
        }
        this.invalidate();
        this.validate();
    }
}