package larp_italia;
import java.security.DrbgParameters.Reseed;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

	
public class DB{
	private final static String url = "jdbc:postgresql//localhost:5432/larp_italia";
	private final static String usr = "postgres";
	//private final static String psw = "kn0wledgeispow3r";// DEN
    private final static String psw = "password";// NICO
        
    private User user = new User();

	public DB() {
	 
		try {
                    
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection(url,usr,psw);
                    
		}catch(SQLException | ClassNotFoundException e) {
                    e.printStackTrace();	
		}
	}
    public boolean login(String cf){
        System.out.println("Login with cf: "+cf);
        //res = query()

        //this.user.setId(res.id);
        //this.user.setNome(res.nome);

        // this.user.set....
        // Sfilza di set
        return true;
    }
    
    public User getUser(){
        return this.user;
    }
}
