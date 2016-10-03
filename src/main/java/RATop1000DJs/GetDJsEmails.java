package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.DBConnection;

public class GetDJsEmails {

	public static void getEmails(WebDriver driver) {

		// CopyDBFile.copyDBFile();
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;

		String sqlSelect = "SELECT * FROM RATop1000DJs";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			driver.get("https://www.facebook.com");
			CommonFunctions.clickElement(driver, "Log In");
			CommonFunctions.enterString(driver, UiMap.FacebookPageElements.emailField,
					"bernardcooley@gmail.com");
			CommonFunctions.enterString(driver, UiMap.FacebookPageElements.passwordField,
					"yeloocc1");
			CommonFunctions.clickElement(driver, UiMap.FacebookPageElements.loginBtn);
			
			while (rs.next()) {
				if (rs.getString(14).equals("Yes")) {

					driver.get(rs.getString(8));
					CommonFunctions.customWait(driver, 2);
					CommonFunctions.clickElement(driver, "About");
					
					System.out.println(CommonFunctions.getElementByTextContains(driver, "@"));
					
					
					

					CommonFunctions.customWait(driver, 5);

				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
