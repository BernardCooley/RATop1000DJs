package RATop1000DJs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import FrameworkUtils.DBConnection;

public class EmailAddressesFromTextFile {

	public static void getEmailAddressesFromTextFile() {
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("DJ Promo Emails.txt"));

			String[] linesSplit = null;
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			
			linesSplit = everything.split("\\r?\\n");
			
			for (String s : linesSplit) {
				String[] split = s.split("\t", -1);
				String sqlUpdate = "INSERT INTO Top1000 (Name, Promo_Email) VALUES (?, ?)";
				try {
					pst = con.prepareStatement(sqlUpdate);
					System.out.println(split[0].trim());
					System.out.println(split[1].trim());
					pst.setString(1, split[0].trim());
					pst.setString(2, split[1].trim());
					pst.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
