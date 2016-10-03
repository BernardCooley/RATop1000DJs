package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.DBConnection;

public class RADJInfo {

	public static void getRADJInfo(WebDriver driver) {

		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		String country = "N/A";
		String website = "N/A";
		String twitter = "N/A";
		String soundcloud = "N/A";
		String discogs = "N/A";
		String facebook = "N/A";
		
		String sqlSelect = "SELECT * FROM RATop1000DJs";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				if (rs.getString(12).equals("No")) {
					driver.get(rs.getString(2));
					System.out.println(rs.getString(2));
					System.out.println(rs.getString(1));
					for (WebElement we : CommonFunctions.getArrayOfElements(driver, UiMap.RADJs.dJInfoSection)) {
						country = "N/A";
						website = "N/A";
						twitter = "N/A";
						soundcloud = "N/A";
						discogs = "N/A";
						facebook = "N/A";
						
						if (we.getText().contains("Country")) {
							country = we.findElement(By.cssSelector("span > a > span")).getText();
						}
						if (we.getText().contains("On the internet")) {
							System.out.println("Name: " + rs.getString(1));
							for (WebElement we1 : we.findElements(By.cssSelector("a"))) {
								if (we1.getText().contains("Website")) {
									website = we1.getAttribute("href");
									System.out.println("Website: " + website);
								}
								if (we1.getText().contains("Twitter")) {
									twitter = we1.getAttribute("href");
									System.out.println("Twitter: " + twitter);
								}
								if (we1.getText().contains("SoundCloud")) {
									soundcloud = we1.getAttribute("href");
									System.out.println("Soundcloud: " + soundcloud);
								}
								if (we1.getText().contains("Discogs")) {
									discogs = we1.getAttribute("href");
									System.out.println("Discogs: " + discogs);
								}
								if (we1.getText().contains("Facebook")) {
									facebook = we1.getAttribute("href");
									System.out.println("Facebook: " + facebook);
								}
							}
						}
					}
					String sqlUpdate = "UPDATE RATop1000DJs SET Country=?, Website=?, Twitter=?, Soundcloud=?, Discogs=?, Facebook=?, RA_DJ_Info_Scanned='Yes' WHERE Name=?";
					try {
						pst = con.prepareStatement(sqlUpdate);
						pst.setString(1, country);
						pst.setString(2, website);
						pst.setString(3, twitter);
						pst.setString(4, soundcloud);
						pst.setString(5, discogs);
						pst.setString(6, facebook);
						pst.setString(7, rs.getString(1));
						pst.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
