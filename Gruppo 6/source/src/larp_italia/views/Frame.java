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
    
    public Frame() {
        super("Login with CF");
        db = new DB();
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(lp);
        this.pack();
        this.setVisible(true);
	}

    public void switchPanel(types type){
        this.remove(lp);
        if(type == types.GIOCATORE){
            gp = new GiocatorePanel(this,db);
            this.add(gp);
        }else{
            dp = new DipendentePanel(this,db);
            this.add(dp);
        }
        this.pack();
        this.invalidate();
        this.validate();
    }
    public void logout(){
        this.getContentPane().removeAll();
        this.repaint();

        this.add(lp);
        this.pack();
        this.invalidate();
        this.validate();
    }
    
    public DB getdb() {
    	return this.db;
    }
}
