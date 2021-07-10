package larp_italia;

import javax.swing.JFrame;

public class main {
	
	public static void main(String[] args) {
		Login_Frame log = new Login_Frame("Login Larp Italia");
		log.setVisible(true);
		log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		log.setLocationRelativeTo(null);
		log.pack();
	}
	
	Login_Frame log = new Login_Frame("Login");
	
}
