/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import larp_italia.DB;
import larp_italia.Evento;
import larp_italia.Personaggio_npc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import larp_italia.User;
/**
 *
 * @author giokk
 */
public class DipendentePanel extends JPanel{
    private Frame mainFrame;
    private ArrayList<Evento> eventidisp = new ArrayList<Evento>();
    private ArrayList<Personaggio_npc> npc = new ArrayList<Personaggio_npc>();
    private JTable evtab;
    private JTabbedPane tabbedPane;
    private JTable npctab;
    private GiocatorePanel gp;
    
    public DipendentePanel(Frame mainFrame,DB db){
        this.mainFrame = mainFrame;
        gp= new GiocatorePanel(mainFrame,db);
        
        eventidisp = db.getEventiDisp();
        npc = db.getPersOrNpc();

		User user = db.getUser();
		JPanel userInfo = new JPanel();

		userInfo.setLayout(new GridLayout(3,2));

		userInfo.add(new JLabel("CF Giocatore:",JLabel.CENTER));
		userInfo.add(new JLabel(user.getCf(),2));
		userInfo.add(new JLabel("Nome: ",JLabel.CENTER));
		userInfo.add(new JLabel(user.getNome(),2));
		userInfo.add(new JLabel("Cognome: ",JLabel.CENTER));
		userInfo.add(new JLabel(user.getCognome(),2));
        
        tabbedPane = new JTabbedPane();
        JPanel evdisp = new JPanel();
        JButton addev = new JButton("Aggiungi nuovo evento");
        addev.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				add_evento(db);
				
			}
        	
        });
        JPanel npcdisp = new JPanel();
        
        String[] columnNames_eventi = {"Titolo","Campagna","Data_inizio","Data_fine","Limite Iscrizioni","Costo","Indirizzo"};
        int numcolonne_eventi = columnNames_eventi.length;
        evtab = new JTable(gp.getDataTableEventi(eventidisp, numcolonne_eventi,columnNames_eventi),columnNames_eventi);
        String[] colname_pers = {"Codice Personaggio","Nome","Descrizione"};
        int numcol_pers = colname_pers.length;
        npctab = new JTable(this.getDataTableNpc(npc, numcol_pers, colname_pers),colname_pers);
        
        JButton logout = new JButton("Logout");
        logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goback();
			}
        	
        });
        
        npcdisp.add(npctab);
		evdisp.setLayout(new GridLayout(2,1));
        evdisp.add(evtab);
        evdisp.add(addev);
        tabbedPane.add("Eventi",evdisp);
        tabbedPane.add("NPC interpretati",npcdisp);
        

		this.setLayout(new GridLayout(3,2));
		this.add(tabbedPane);
		this.add(userInfo);
        this.add(logout);
        this.setVisible(true);
    }
    
    public Object[][] getDataTableNpc(ArrayList<Personaggio_npc> arr, int n, String[] colname){
    	Object[][] temp = new Object[arr.size()+1][n];
    	temp[0] = colname;
    	for(int i=0;i<arr.size();i++) {
    		Personaggio_npc ev = arr.get(i);
    		temp[i+1][0] = ev.getCodice();
    		temp[i+1][1] = ev.getNome();
    		temp[i+1][2] = ev.getDescrizione();
    	}
    	return temp;
    }
    
    public void add_evento(DB db) {
    	JFrame frame = new JFrame();
		frame.setTitle("Aggiungi evento");
		
    	JTextField titolo = new JTextField();
    	// per campagna sarebbe meglio scegliere tra campagne esistenti piuttosto che un JTextField, per rispettare vincoli di foreign key
    	JTextField campagna = new JTextField();
    	JTextField data_inizio = new JTextField("AAAA-MMGG");
    	JTextField data_fine = new JTextField("AAAA-MMGG");
    	JTextField limite_iscrizioni = new JTextField();
    	JTextField costo = new JTextField();
    	JTextField indirizzo = new JTextField();
    	JPanel pan = new JPanel();
    	JButton add = new JButton("aggiungi");
    	add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				db.insert_evento(titolo.getText(),campagna.getText(),data_inizio.getText(),data_fine.getText(),limite_iscrizioni.getText(),costo.getText(),indirizzo.getText());
				frame.dispose();
			}
    		
    	});
    	
		pan.setLayout(new GridLayout(8,2));
		pan.add(new JLabel("Titolo: "));
    	pan.add(titolo);
		pan.add(new JLabel("Campagna: "));
    	pan.add(campagna);
		pan.add(new JLabel("Data Inizio: "));
    	pan.add(data_inizio);
		pan.add(new JLabel("Data Fine: "));
    	pan.add(data_fine);
		pan.add(new JLabel("Limite Iscritti: "));
    	pan.add(limite_iscrizioni);
		pan.add(new JLabel("Costo: "));
    	pan.add(costo);
		pan.add(new JLabel("Indirizzo: "));
    	pan.add(indirizzo);
    	pan.add(add);
    	frame.add(pan);
    	frame.pack();
    	frame.setVisible(true);
    }
    
    public void goback() {
    	Frame mainframe = new Frame();
    	this.mainFrame.dispose();
    }
    
}
