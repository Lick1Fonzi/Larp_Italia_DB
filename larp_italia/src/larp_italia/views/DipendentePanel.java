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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        JLabel dipendente = new JLabel("Dipendente: "+db.getUser().getCf());
        gp= new GiocatorePanel(mainFrame,db);
        
        eventidisp = db.getEventiDisp();
        npc = db.getPersOrNpc();
        
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
        
        npcdisp.add(npctab);
        evdisp.add(evtab);
        evdisp.add(addev);
        tabbedPane.add("Eventi",evdisp);
        tabbedPane.add("NPC disponibili",npcdisp);
        
        this.add(tabbedPane);
        this.add(dipendente);
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
    	JFrame frame = new JFrame("Aggiungi evento");
    	JTextField titolo = new JTextField("titolo");
    	// per campagna sarebbe meglio scegliere tra campagne esistenti piuttosto che un JTextField, per rispettare vincoli di foreign key
    	JTextField campagna = new JTextField("Nome_Campagna");
    	JTextField data_inizio = new JTextField("Data_inizio: AAAA-MMGG");
    	JTextField data_fine = new JTextField("Data_fine: AAAA-MMGG");
    	JTextField limite_iscrizioni = new JTextField("Limite iscrizioni");
    	JTextField costo = new JTextField("Costo iscrizione");
    	JTextField indirizzo = new JTextField("Indirizzo");
    	JPanel pan = new JPanel();
    	JButton add = new JButton("aggiungi");
    	add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				db.insert_evento(titolo.getText(),campagna.getText(),data_inizio.getText(),data_fine.getText(),limite_iscrizioni.getText(),costo.getText(),indirizzo.getText());
				frame.dispose();
			}
    		
    	});
    	
    	pan.add(titolo);
    	pan.add(campagna);
    	pan.add(data_inizio);
    	pan.add(data_fine);
    	pan.add(limite_iscrizioni);
    	pan.add(costo);
    	pan.add(indirizzo);
    	pan.add(add);
    	frame.add(pan);
    	frame.pack();
    	frame.setVisible(true);
    }
    
}
