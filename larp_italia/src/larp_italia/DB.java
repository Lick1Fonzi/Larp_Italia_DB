package larp_italia;
import java.sql.Statement;
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
        	this.user.setType(User.types.DIPENDENTE);
        }
        else {
        	tabella = "giocatore";
        	this.user.setType(User.types.GIOCATORE);
        }
        String query = (" select * from "+tabella+" where cf = " +"'"+ cf + "'");
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
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
        if(empty==0)
        	return false;
        else
        	return true;
    }
    
    public User getUser(){
        return this.user;
    }
}
