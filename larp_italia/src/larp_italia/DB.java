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
    
    private Statement st;

	public DB() {
	 
		try {
                    
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url,usr,psw);
                    
		}catch(SQLException | ClassNotFoundException e) {
                    e.printStackTrace();	
		}
	}
    public boolean login(String cf){
        System.out.println("Login with cf: "+cf);
        String query = (" select * from persona where cf = " +"'"+ cf + "'");
        int empty = 0;

        try {
        	st = this.c.createStatement();
        	ResultSet res = st.executeQuery(query);
        	while(res.next()) {
        		empty = 1;
        		this.user.setNome(res.getString("nome"));
        		this.user.setCognome(res.getString("cognome"));
        		this.user.setCf(res.getString("cf"));
        		//this.user.setEmail(res.getString("email"));
        		this.user.setCellulare(res.getString("cellulare"));
        		
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
