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
		String country = null;
		String website = null;
		String twitter = null;
		String soundcloud = null;
		String discogs = null;
		String facebook = null;

		String sqlSelect = "SELECT * FROM RATop1000DJs";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				if (rs.getString(12).equals("No")) {
					driver.get(rs.getString(2));
					for (WebElement we : CommonFunctions.getArrayOfElements(driver, UiMap.RADJs.dJInfoSection)) {
						if (we.getText().contains("Country")) {
							country = we.findElement(By.cssSelector("span > a > span")).getText();
						} else {
							country = "N/A";
						}
						if (we.getText().contains("On the internet")) {
							for (WebElement we1 : we.findElements(By.cssSelector("a"))) {
								if (we1.getText().contains("Website")) {
									website = we1.getAttribute("href");
								} else {
									website = "N/A";
								}
								if (we1.getText().contains("Twitter")) {
									twitter = we1.getAttribute("href");
								} else {
									twitter = "N/A";
								}
								if (we1.getText().contains("SoundCloud")) {
									soundcloud = we1.getAttribute("href");
								} else {
									soundcloud = "N/A";
								}
								if (we1.getText().contains("Discogs")) {
									discogs = we1.getAttribute("href");
								} else {
									discogs = "N/A";
								}
								if (we1.getText().contains("Facebook")) {
									facebook = we1.getAttribute("href");
								} else {
									facebook = "N/A";
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
					System.out.println(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
