package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.DBConnection;

public class Soundcloud {

	public static void sendSoundcloudMessages(WebDriver driver) {
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		
		String sqlSelect = "SELECT * FROM Top1000";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				if (!rs.getString(6).equals("N/A")) {
					driver.get(rs.getString(6));
					
					CommonFunctions.clickElement(driver, UiMap.SoundcloudElements.signInBtn);
					CommonFunctions.customWait(driver, 1);
					
					System.out.println(driver.getWindowHandles().size());
					
					
					CommonFunctions.enterString(driver, UiMap.SoundcloudElements.emailField, "bernardcooley@gmail.com");
					CommonFunctions.customWait(driver, 1);
					CommonFunctions.clickElement(driver, UiMap.SoundcloudElements.continueBtn);
					CommonFunctions.customWait(driver, 1);
					CommonFunctions.enterString(driver, UiMap.SoundcloudElements.passwordField, "yelooc");
					CommonFunctions.customWait(driver, 1);
					CommonFunctions.clickElement(driver, UiMap.SoundcloudElements.signInForm);
					CommonFunctions.customWait(driver, 1);
					CommonFunctions.clickElement(driver, UiMap.SoundcloudElements.logInBtn);
					
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
