package larp_italia;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Login_Frame extends JFrame implements ActionListener{
    private DB db;

    private JPanel userpanel;
    private JTextField userfield;

    private JPanel pswpanel;
    private JTextField pswfield;

    private JPanel loginpanel;
    private JCheckBox admin;
    private JCheckBox cliente;

    private JButton loginbut;
    private JButton signinbut;


    public Login_Frame(String s) {
            super(s);
            //db = new DB();


            userpanel=new JPanel();
            userfield=new JTextField("",20);
            userpanel.add(new JLabel("Username"));
            userpanel.add(userfield);

            pswpanel=new JPanel();
            pswfield=new JTextField("",20);
            pswpanel.add(new JLabel("Password"));
            pswpanel.add(pswfield);

            loginpanel=new JPanel();
            admin=new JCheckBox("Dipendente");
            cliente=new JCheckBox("Gioctore");
            ButtonGroup bgroup = new ButtonGroup();
            bgroup.add(admin);
            bgroup.add(cliente);
            cliente.setSelected(true);
            loginbut=new JButton("Login");
            signinbut=new JButton("Sign in");
            loginbut.addActionListener(this);
            signinbut.addActionListener(this);

            loginpanel.add(admin);
            loginpanel.add(cliente);
            loginpanel.add(loginbut);
            loginpanel.add(signinbut);

            this.add(userpanel,BorderLayout.NORTH);
            this.add(pswpanel,BorderLayout.CENTER);
            this.add(loginpanel,BorderLayout.SOUTH);
    }



    /**
     * Implementazione del metodo fornito dall'interfaccia ActionListener che gestisce gli eventi dei bottoni di login e signin invcando le relative funzioni
     */
    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(loginbut))
                    login();
            else if(e.getSource().equals(signinbut))
                    signin();
    }





    /**
     * rende visibile il frame e pulisce i campi di login
     */
    public void visible() {
            this.setVisible(true);
            userfield.setText("");
            pswfield.setText("");
    }



    /**
     * 
     * @param s crea un messaggio di avviso specificato dal parametro
     */
    public void aggiungiavviso(String s) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,s);
    }


    /**
     * metodo invocato alla pressione del JButton di login, controlla le credenziali di accesso. Se corrispondono ad un utente gia registrato rende invisibile questo frame e crea un istanza della classe Tableframe oppure Adminframe a seconda del login scelto passandogli come parametro il Cliente che si � autenticato. Se credenziali errate restituisce un avviso di errore richiamndo il metodo aggiungiavviso()
     */
    public void login() {

    }



    /**
     * metodo invocato alla pressione del JButton di sign in. Apre un JFrame che permette ad un nuovo cliente di registrarsi. Se l'username scelto � gia esistente restituisce un messaggio di errore invocando aggiungiavviso(), altrimenti restituisce un avviso di riuscita registrazione
     */
    public void signin() {

    }
}