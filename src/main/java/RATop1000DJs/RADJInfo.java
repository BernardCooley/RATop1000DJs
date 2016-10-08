package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.CopyDBFile;
import FrameworkUtils.DBConnection;

public class RADJInfo {

	public static void getRADJInfo(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
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

		String sqlSelect = "SELECT * FROM Top1000";
		try {

			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				if (rs.getString(12).equals("No")) {
					driver.get(rs.getString(2));
					
					if (driver.findElements(UiMap.RADJs.country).size() > 0) {
						country = CommonFunctions.getElementText(driver, UiMap.RADJs.country);
					} else {
						country = "N/A";
					}
					if (driver.findElements(By.xpath("//a[contains(text(),'SoundCloud')]")).size() > 0) {
						soundcloud = driver.findElement(By.xpath("//a[contains(text(),'SoundCloud')]")).getAttribute("href");
					} else {
						soundcloud = "N/A";
					}
					if (driver.findElements(By.xpath("//a[contains(text(),'Twitter')]")).size() > 0) {
						twitter = driver.findElement(By.xpath("//a[contains(text(),'Twitter')]")).getAttribute("href");
					} else {
						twitter = "N/A";
					}
					if (driver.findElements(By.xpath("//a[contains(text(),'Website')]")).size() > 0) {
						website = driver.findElement(By.xpath("//a[contains(text(),'Website')]")).getAttribute("href");
					} else {
						website = "N/A";
					}
					if (driver.findElements(By.xpath("//a[contains(text(),'Facebook')]")).size() > 0) {
						facebook = driver.findElement(By.xpath("//a[contains(text(),'Facebook')]")).getAttribute("href");
					} else {
						facebook = "N/A";
					}
					if (driver.findElements(By.xpath("//a[contains(text(),'Discogs')]")).size() > 0) {
						discogs = driver.findElement(By.xpath("//a[contains(text(),'Discogs')]")).getAttribute("href");
					} else {
						discogs = "N/A";
					}

					String sqlUpdate = "UPDATE Top1000 SET Country=?, Website=?, Twitter=?, Soundcloud=?, Discogs=?, Facebook=?, RA_DJ_Info_Scanned='Yes' WHERE Name=?";
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
