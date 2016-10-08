package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.DBConnection;

public class ValidEmailCheck {

	public static void checkEmailValidity(WebDriver driver) {
		
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		
		String sqlSelect = "SELECT * FROM Top1000 WHERE Promo_Email IS NOT NULL";
		
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();
			
			driver.get("http://www.verifyemailaddress.org/");
			
			while(rs.next()) {
				CommonFunctions.enterString(driver, UiMap.EmailCheckElements.emailField, rs.getString(16));
				CommonFunctions.clickElement(driver, UiMap.EmailCheckElements.checkBtn);
				
				if (CommonFunctions.getElementText(driver, UiMap.EmailCheckElements.resultLabel).contains("worked")) {
					System.out.println("Valid");
				}else {
					String sqlDelete = "DELETE FROM Top1000 WHERE Promo_Email=?";
					pst = con.prepareStatement(sqlDelete);
					pst.setString(1, rs.getString(16));
					pst.executeUpdate();
					System.out.println("Invalid: " + rs.getString(16));
				}
				CommonFunctions.clearTextField(driver, UiMap.EmailCheckElements.emailField);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
