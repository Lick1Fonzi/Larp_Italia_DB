package larp_italia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

	
public class DB{
	private final static String url = "jdbc:postgresql//localhost:5432/larp_italia";
	private final static String usr = "postgres";
	private final static String psw = "kn0wledgeispow3r";

	public DB() {
	 
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection(url,usr,psw);
			
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			
		}
	}
}
