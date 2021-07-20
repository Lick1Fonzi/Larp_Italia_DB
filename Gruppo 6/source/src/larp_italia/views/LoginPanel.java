/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    private Frame mainFrame;
    private JTextField cf; // To get CF text
    private JCheckBox dipbox;
    private JCheckBox giocbox;
    
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
        
        dipbox = new JCheckBox("Dipendente");
        giocbox = new JCheckBox("Giocatore");
        ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(dipbox);
		bgroup.add(giocbox);
        
        JButton send = new JButton("login");
        send.addActionListener((ActionEvent e) -> {
            if(mainFrame.db.login(cf.getText(),dipbox.isSelected())){
                System.out.println("Login ok");
                errorLabel.setVisible(false);
                
                // Change panel
                User user = mainFrame.db.getUser();
                
                mainFrame.switchPanel(user.getType());
            } else {
                System.out.println("Login errato");
                errorLabel.setVisible(true);
            }
        });
        
        this.add(cfLabel);
        this.add(errorLabel);
        this.add(cf);
        this.add(send);
        this.add(dipbox);
        this.add(giocbox);
        
        this.setLayout(new GridLayout(4,0));
        this.setPreferredSize(new Dimension(200,100));
        this.setVisible(true);
    }
}
