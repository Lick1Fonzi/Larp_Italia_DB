/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.vews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import larp_italia.User;
import larp_italia.User.types;

/**
 *
 * @author giokk
 */
public class LoginPanel extends JPanel{
    Frame mainFrame;
    JTextField cf; // To get CF text
    
    public LoginPanel(Frame mainFrame){
        this.mainFrame = mainFrame;
        
        cf = new JTextField();
        JLabel cfLabel = new JLabel("Codice Fiscale");
        cfLabel.setHorizontalAlignment(JLabel.CENTER);
        cfLabel.setVerticalAlignment(JLabel.CENTER);
        
        JLabel errorLabel = new JLabel("CF non presente nel database");
        errorLabel.setForeground(Color.red);
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.CENTER);
        errorLabel.setVisible(false);
        
        JButton send = new JButton("login");
        send.addActionListener((ActionEvent e) -> {
            if(mainFrame.db.login(cf.getText())){
                System.out.println("Login ok");
                errorLabel.setVisible(false);
                
                // Change panel
                User user = mainFrame.db.getUser();
                if(user.getType() == types.DIPENDENTE){
                    System.out.println("Dipendente");
                    //changePanelDipendente
                }
                else { 
                    System.out.println("Giocatore");
                    //changePanelGiocatore
                }
            } else {
                System.out.println("Login errato");
                errorLabel.setVisible(true);
            }
        });
        
        this.add(cfLabel);
        this.add(errorLabel);
        this.add(cf);
        this.add(send);
        
        this.setLayout(new GridLayout(4,0));
        this.setPreferredSize(new Dimension(200,100));
        this.setVisible(true);
    }
}
