package RATop1000DJs;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.DBConnection;

public class GetDJsEmails {

	public static void getEmailAddressesFromGoogle(WebDriver driver) {
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;

		String sqlSelect = "SELECT * FROM Top1000";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				driver.get("https://www.google.co.uk");

				StringSelection selection = new StringSelection(
						rs.getString(1) + "  promo email + demo -management -booking -info -mix -stee");
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, selection);

				try {
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
				} catch (AWTException e) {
					e.printStackTrace();
				}

				CommonFunctions.customWait(driver, 10);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void getEmails(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		// CopyDBFile.copyDBFile();
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;

		String sqlSelect = "SELECT * FROM Top1000";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			// driver.get("https://www.facebook.com");
			// CommonFunctions.clickElement(driver, "Log In");
			// CommonFunctions.enterString(driver,
			// UiMap.FacebookPageElements.emailField,
			// "bernardcooley@gmail.com");
			// CommonFunctions.enterString(driver,
			// UiMap.FacebookPageElements.passwordField, "yeloocc1");
			// CommonFunctions.clickElement(driver,
			// UiMap.FacebookPageElements.loginBtn);

			while (rs.next()) {
				if (rs.getString(8).contains("http")) {
					driver.get(rs.getString(8));
					CommonFunctions.customWait(driver, 2);
					CommonFunctions.clickElement(driver, "About");
					// if (driver.findElements(By.xpath("//"))) {

					// }
					System.out.println(CommonFunctions.getElementByTextContains(driver, "@"));
					CommonFunctions.customWait(driver, 5);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}