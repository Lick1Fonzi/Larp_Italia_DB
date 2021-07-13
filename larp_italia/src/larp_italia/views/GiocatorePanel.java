/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.views;

import javax.swing.JPanel;
import javax.swing.JTable;

import larp_italia.DB;
import larp_italia.Evento;
import larp_italia.Personaggio_npc;

import java.util.ArrayList;

import javax.swing.JLabel;
/**
 *
 * @author giokk
 */
public class GiocatorePanel extends JPanel{
    Frame mainFrame;
    ArrayList<Evento> eventidisp = new ArrayList<Evento>();
    ArrayList<Personaggio_npc> npc = new ArrayList<Personaggio_npc>();
    JTable evtab;

    
    public GiocatorePanel(Frame mainFrame, DB db){
        this.mainFrame = mainFrame;
        eventidisp = db.getEventiDisp();
        npc = db.getPersOrNpc();
        JLabel giocatore = new JLabel("Giocatore");
        
        /*String[] columnNames_eventi = {"Titolo","Campagna","Data_inizio","Data_fine","Limite Iscrizioni","Costo","Indirizzo"};
        int numcolonne_eventi = columnNames_eventi.length;
        evtab = new JTable(this.getDataTableEventi(eventidisp, 7),columnNames_eventi); */
        
        this.add(giocatore);
        //this.add(evtab);
        this.setVisible(true);
    }
    
    Object[][] getDataTableEventi(ArrayList<Evento> arr,int n){
    	Object[][] temp = new Object[arr.size()][n];
    	for(int i=0;i<n;i++) {
    		Evento ev = arr.get(i);
    		temp[i][0] = ev.getTitolo();
    		temp[i][1] = ev.getNome_campagna();
    		temp[i][2] = ev.getInizio();
    		temp[i][3] = ev.getFine();
    		temp[i][4] = ev.getLimite_iscrizione();
    		temp[i][5] = ev.getCosto();
    		temp[i][6] = ev.getIndirizzo();
    				
    	}
    	return temp;
    }
    
}
