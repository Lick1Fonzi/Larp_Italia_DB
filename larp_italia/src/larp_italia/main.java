package larp_italia;

import javax.swing.JFrame;

import larp_italia.views.Frame;

public class Main {
	
	public static void main(String[] args) {
		//Login_Frame log = new Login_Frame("Login Larp Italia");
		DB db = new DB();
		
		Frame log = new Frame(db);
	}
	
	Login_Frame log = new Login_Frame("Login");
	
}
