package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.CopyDBFile;
import FrameworkUtils.DBConnection;

public class RADJInfo {

	public static void getRADJInfo(WebDriver driver) {
		CopyDBFile.copyDBFile();
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		String country = "N/A";
		String website = "N/A";
		String twitter = "N/A";
		String soundcloud = "N/A";
		String discogs = "N/A";
		String facebook = "N/A";
		Map<String, String> info = new HashMap<String, String>();

		String sqlSelect = "SELECT * FROM Top1000";
		try {

			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				if (rs.getString(12).equals("No")) {
					driver.get(rs.getString(2));
					System.out.println(rs.getString(2));
					System.out.println(rs.getString(1));
					country = "N/A";
					for (WebElement we : CommonFunctions.getArrayOfElements(driver, UiMap.RADJs.dJInfoSection)) {

						if (we.getText().contains("Country")) {
							if (we.findElements(By.cssSelector("li > span > a > span")).size() > 0) {
								country = we.findElement(By.cssSelector("li > span > a > span")).getText();
							}
							if (we.findElements(By.cssSelector("li:nth-of-type(2) > span > a > span")).size() > 0) {
								country = we.findElement(By.cssSelector("li:nth-of-type(2) > span > a > span")).getText();
							}
							if (we.findElements(By.cssSelector("li:nth-of-type(3) > span > a > span")).size() > 0) {
								country = we.findElement(By.cssSelector("li:nth-of-type(3) > span > a > span")).getText();
							}
							if (we.findElements(By.cssSelector("span > a > span")).size() > 0) {
								country = we.findElement(By.cssSelector("span > a > span")).getText();
							}
						}
						if (we.getText().contains("On the internet")) {
							System.out.println("Name: " + rs.getString(1));

							for (WebElement we1 : we.findElements(By.cssSelector("a"))) {
								info.put(we.getText(), we1.getAttribute("href"));
							}
						}
					}

					website = "N/A";
					twitter = "N/A";
					soundcloud = "N/A";
					discogs = "N/A";
					facebook = "N/A";

					Iterator it = info.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry) it.next();
						if (pair.getKey().toString().contains("Website")) {
							website = pair.getValue().toString();
						}
						if (pair.getKey().toString().contains("Twitter")) {
							twitter = pair.getValue().toString();
						}
						if (pair.getKey().toString().contains("SoundCloud")
								|| pair.getKey().toString().contains("Soundcloud")) {
							soundcloud = pair.getValue().toString();
						}
						if (pair.getKey().toString().contains("Discogs")) {
							discogs = pair.getValue().toString();
						}
						if (pair.getKey().toString().contains("Facebook")) {
							facebook = pair.getValue().toString();
						}
					}

					String sqlUpdate = "UPDATE Top1000 SET Country=?, Website=?, Twitter=?, Soundcloud=?, Discogs=?, Facebook=?, RA_DJ_Info_Scanned='Yes' WHERE Name=?";
					try {
						pst = con.prepareStatement(sqlUpdate);
						System.out.println("Country: " + country);
						pst.setString(1, country);
						System.out.println("Website: " + website);
						pst.setString(2, website);
						System.out.println("Twitter: " + twitter);
						pst.setString(3, twitter);
						System.out.println("Soundcloud: " + soundcloud);
						pst.setString(4, soundcloud);
						System.out.println("Discogs: " + discogs);
						pst.setString(5, discogs);
						System.out.println("Facebook: " + facebook);
						pst.setString(6, facebook);
						pst.setString(7, rs.getString(1));
						pst.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println();
					info.clear();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
