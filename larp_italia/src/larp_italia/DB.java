package larp_italia;
import java.sql.Statement;
import java.util.ArrayList;
import java.security.DrbgParameters.Reseed;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

	
public class DB{
	private final static String url = "jdbc:postgresql://localhost:5432/larp_italia";
	private final static String usr = "postgres";
	private final static String psw = "kn0wledgeispow3r";// DEN
    //private final static String psw = "password";// NICO
	
	//private final static String usr = "jdbc";
	//private final static String psw = "jdbc";
        
    private User user = new User();
    private Connection c;
    private String tabella;
    private String tabella2;
    private String tabella3;
    private String attributo;
    private String query;
    private Statement st;

	public DB() {
	 
		try {
                    
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url,usr,psw);
                    
		}catch(SQLException | ClassNotFoundException e) {
                    e.printStackTrace();	
		}
	}
    public boolean login(String cf,boolean dipendente){
        System.out.println("Login with cf: "+cf);
        if(dipendente) {
        	tabella = "dipendente";
        	tabella2 = "npc";
        	tabella3 = "interpreta";
        	attributo = "cod_npc";
        	this.user.setType(User.types.DIPENDENTE);
        }
        else {
        	tabella = "giocatore";
        	tabella2 = "personaggio";
        	tabella3 = "partecipa";
        	attributo = "cod_pg";
        	this.user.setType(User.types.GIOCATORE);
        }
        query = (" select * from "+tabella+" where cf = " +"'"+ cf + "'");
        int empty = 0;

        try {
        	st = this.c.createStatement();
        	ResultSet res = st.executeQuery(query);
        	while(res.next()) {
        		empty = 1;
        		this.user.setNome(res.getString("nome"));
        		this.user.setCognome(res.getString("cognome"));
        		this.user.setCf(res.getString("cf"));
        		this.user.setCellulare(res.getString("cellulare"));
        		if(dipendente)
        			this.user.setStipendio(res.getInt("stipendio"));
        		else
        			this.user.setEmail(res.getString("email"));
        	}
        	res.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        if(empty==0)
        	return false;
        else
        	return true;
    }
    
    public ArrayList<Evento> getEventiDisp(){
    	ArrayList<Evento> lista = new ArrayList<Evento>();
    	try {
			st = this.c.createStatement();
			if(this.getUser().getType().equals(User.types.GIOCATORE))
				query = "select * from evento where data_inizio > current_date order by data_inizio;";
			else
				query = "select * from evento";
			ResultSet res = st.executeQuery(query);
			while(res.next()) {
				Evento temp = new Evento();
				temp.setTitolo(res.getString("titolo"));
				temp.setNome_campagna(res.getString("nome_campagna"));
				temp.setInizio(res.getDate("data_inizio"));
				temp.setFine(res.getDate("data_fine"));
				temp.setCosto(res.getInt("costo_iscrizione"));
				temp.setIndirizzo(res.getString("indirizzo_location"));
				lista.add(temp);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return lista;
    }
    
    public ArrayList<Personaggio_npc> getPersOrNpc(){
    	ArrayList<Personaggio_npc> lista = new ArrayList<Personaggio_npc>();
    	try {
    	st = this.c.createStatement();
    	query = "select * from "+tabella2+" pe, "+tabella3+" pa where pe."+attributo+" = pa."+attributo+" and pa.cf = '"+this.user.getCf()+"' ;";
		ResultSet res = st.executeQuery(query);
		while(res.next()) {
			Personaggio_npc temp = new Personaggio_npc();
			temp.setCodice(res.getInt(attributo));
			temp.setNome(res.getString("nome"));
			temp.setDescrizione(res.getString("descrizione"));
			if(user.getType().equals(User.types.GIOCATORE)) {
				temp.setExp(res.getInt("exp"));
				temp.setTipo(res.getString("tipo"));
			}
			lista.add(temp);
		}
		res.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return lista;
    }
    
    public void insert_evento(String tit,String nom,String in,String fin,String costo, String limite,String ind) {
    	try {
			st = this.c.createStatement();
			query = "insert into evento values ('"+ tit +"','"+nom+"',"+"to_date('"+in+"' , 'YYYY-MMDD'),"+"to_date('"+fin+"' , 'YYYY-MMDD'),"+limite+","+costo+",'"+ind+"');";
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public User getUser(){
        return this.user;
    }
}
