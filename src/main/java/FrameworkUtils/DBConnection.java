package FrameworkUtils;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection {
	
	Connection con = null;
	
	public static Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
//			System.out.println("Attempting Connection");
			String d = null;
			String oS = System.getProperty("os.name");
//			System.out.println(oS);
			Connection con = DriverManager.getConnection("jdbc:sqlite:Top1000DJs.sqlite");
//			JOptionPane.showMessageDialog(null, "Connection successful");
			System.out.println("DB Connection successful");
			return con;
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
