/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.views;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import larp_italia.DB;
import larp_italia.Evento;
import larp_italia.Personaggio_npc;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
/**
 *
 * @author giokk
 */
public class GiocatorePanel extends JPanel{
    private Frame mainFrame;
    private ArrayList<Evento> eventidisp = new ArrayList<Evento>();
    private ArrayList<Personaggio_npc> npc = new ArrayList<Personaggio_npc>();
    private JTable evtab;
    private JTabbedPane tabbedPane;
    
    private JTable perstab;

    
    public GiocatorePanel(Frame mainFrame, DB db){
        this.mainFrame = mainFrame;
        eventidisp = db.getEventiDisp();
        npc = db.getPersOrNpc();
        JLabel giocatore = new JLabel("Giocatore: " + db.getUser().getCf());
        
        tabbedPane = new JTabbedPane();
        JPanel evdisp = new JPanel();
        JPanel persdisp = new JPanel();
        
        String[] columnNames_eventi = {"Titolo","Campagna","Data_inizio","Data_fine","Limite Iscrizioni","Costo","Indirizzo"};
        int numcolonne_eventi = columnNames_eventi.length;
        evtab = new JTable(this.getDataTableEventi(eventidisp, numcolonne_eventi,columnNames_eventi),columnNames_eventi);
        String[] colname_pers = {"Codice Personaggio","Nome","Descrizione","Exp","Tipo"};
        int numcol_pers = colname_pers.length;
        perstab = new JTable(this.getDataTablePersonaggi(npc, numcol_pers, colname_pers),colname_pers);
        
        persdisp.add(perstab);
        evdisp.add(evtab);
        tabbedPane.add("Eventi disponibili",evdisp);
        tabbedPane.add("Personaggi disponibili",persdisp);

        
        this.add(tabbedPane);
        this.add(giocatore,BorderLayout.NORTH);
        this.setVisible(true);
    }
    
    public Object[][] getDataTableEventi(ArrayList<Evento> arr,int n, String[] colname){
    	Object[][] temp = new Object[arr.size()+1][n];
    	temp[0] = colname;
    	for(int i=0;i<arr.size();i++) {
    		Evento ev = arr.get(i);
    		temp[i+1][0] = ev.getTitolo();
    		temp[i+1][1] = ev.getNome_campagna();
    		temp[i+1][2] = ev.getInizio();
    		temp[i+1][3] = ev.getFine();
    		temp[i+1][4] = ev.getLimite_iscrizione();
    		temp[i+1][5] = ev.getCosto();
    		temp[i+1][6] = ev.getIndirizzo();
    				
    	}
    	return temp;
    }
    
    public Object[][] getDataTablePersonaggi(ArrayList<Personaggio_npc> arr, int n, String[] colname){
    	Object[][] temp = new Object[arr.size()+1][n];
    	temp[0] = colname;
    	for(int i=0;i<arr.size();i++) {
    		Personaggio_npc ev = arr.get(i);
    		temp[i+1][0] = ev.getCodice();
    		temp[i+1][1] = ev.getNome();
    		temp[i+1][2] = ev.getDescrizione();
    		temp[i+1][3] = ev.getExp();
    		temp[i+1][4] = ev.getTipo();
    	}
    	return temp;
    }
    
}
