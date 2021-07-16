/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package larp_italia.views;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import larp_italia.DB;
import larp_italia.Evento;
import larp_italia.Partecipazioni;
import larp_italia.Personaggio_npc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
/**
 *
 * @author giokk
 */
public class GiocatorePanel extends JPanel{
    private Frame mainFrame;
    private ArrayList<Evento> eventidisp;
    private ArrayList<Personaggio_npc> npc;
    private ArrayList partecip;
    private JTable evtab;
    private JTabbedPane tabbedPane;
    
    private JTable perstab;
    private JTable parttab;

    
    public GiocatorePanel(Frame mainFrame, DB db){
        this.mainFrame = mainFrame;
        eventidisp = db.getEventiDisp();
        npc = db.getPersOrNpc();
        partecip = db.getpartecipaz();
        JLabel giocatore = new JLabel("Giocatore: " + db.getUser().getCf());
        tabbedPane = new JTabbedPane();
        JPanel evdisp = new JPanel();
        JPanel persdisp = new JPanel();
        JPanel part = new JPanel();
        
        String[] columnNames_eventi = {"Titolo","Campagna","Data_inizio","Data_fine","Limite Iscrizioni","Costo","Indirizzo"};
        int numcolonne_eventi = columnNames_eventi.length;
        evtab = new JTable(this.getDataTableEventi(eventidisp, numcolonne_eventi,columnNames_eventi),columnNames_eventi);
        evtab.addMouseListener((MouseListener) new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int rowheight = 15;
				int nev = e.getY()/rowheight;
				if(e.getY()>15 && db.checkiscritto(eventidisp.get(nev-1).getTitolo())) {
					JPopupMenu jp = new JPopupMenu("Iscrizione");
					JMenuItem it = new JMenuItem("iscriviti ad evento: " + eventidisp.get(nev-1).getTitolo());
					it.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							ArrayList<Personaggio_npc> lista;
							JFrame frame = new JFrame("scegli personaggio con cui iscriverti");
							JPanel pan = new JPanel();
							lista = db.getAllPersonaggi();
							for(int i=0;i<lista.size();i++) {
								int cod = lista.get(i).getCodice();
								JButton temp = new JButton(lista.get(i).getNome()+": "+Integer.toString(cod));
								temp.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										db.addNewPartecipaz(db.getUser().getCf(),eventidisp.get(nev-1).getTitolo(),cod);
										System.out.println("iscritto con successo");
										frame.dispose();
										
									}
									
								});
								pan.add(temp);
							}
							frame.add(pan);
							frame.setVisible(true);
							frame.pack();
							frame.setLocationRelativeTo(null);
						}
						
					});
					jp.add(it);
					jp.show(e.getComponent(),e.getX(), e.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			
        	
        });
        String[] colname_pers = {"Codice Personaggio","Nome","Descrizione","Exp","Tipo"};
        int numcol_pers = colname_pers.length;
        perstab = new JTable(this.getDataTablePersonaggi(npc, numcol_pers, colname_pers),colname_pers);
        String[] colname_part = {"Codice personaggio","Titolo evento"};
        int numcol_part = colname_part.length;
        parttab = new JTable(this.getDataTablePartecipazioni(partecip, numcol_part, colname_part),colname_part);
        
        
        persdisp.add(perstab);
        evdisp.add(evtab);
        part.add(parttab);
        tabbedPane.add("Eventi disponibili",evdisp);
        tabbedPane.add("Personaggi giocati",persdisp);
        tabbedPane.add("Iscrizioni",part);

        JButton logout = new JButton("Logout");
        logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goback();
			}
        	
        });
        
        
        this.add(tabbedPane);
        this.add(giocatore,BorderLayout.NORTH);
        this.add(logout);
        this.setVisible(true);
        this.mainFrame.setSize(700, 200);
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
    
    public Object[][] getDataTablePartecipazioni(ArrayList<Partecipazioni> arr, int n, String[] colname){
    	Object[][] temp = new Object[arr.size()+1][n];
    	temp[0] = colname;
    	for(int i=0;i<arr.size();i++) {
    		Partecipazioni p = arr.get(i);
    		temp[i+1][0] = p.getCod_pg();
    		temp[i+1][1] = p.getTitolo();
    	}
    	return temp;
    }
    
    public void goback() {
    	Frame mainframe = new Frame();
    	this.mainFrame.dispose();
    }
    
}
