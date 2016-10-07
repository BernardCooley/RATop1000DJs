package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.CopyDBFile;
import FrameworkUtils.DBConnection;

public class FacebookLikes {

	public static void getFBLikes(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		CopyDBFile.copyDBFile();
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;

		String sqlSelect = "SELECT * FROM Top1000";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				if (rs.getString(13) == null) {
					if (rs.getString(8).contains("http")) {
						System.out.println(rs.getString(8));
						driver.get(rs.getString(8));
						
						if (driver.findElements(UiMap.FacebookPageElements.likesBtn).size() > 0) {
							CommonFunctions.clickElement(driver, UiMap.FacebookPageElements.likesBtn);
						}
						
//						CommonFunctions.clickElementByContainingText(driver, "Log In");
//						CommonFunctions.enterString(driver, UiMap.FacebookPageElements.emailField, "bernardcooley@gmail.com");
//						CommonFunctions.enterString(driver, UiMap.FacebookPageElements.passwordField, "yeloocc1");
//						CommonFunctions.clickElement(driver, UiMap.FacebookPageElements.loginBtn);
						
						if (CommonFunctions.isElementVisible(driver, UiMap.FacebookPageElements.likes)) {
							String likes = CommonFunctions.getElement(driver, UiMap.FacebookPageElements.likes).getText();
							likes = likes.replace(" likes", "");
							likes = likes.replace(",", "");
							String sqlUpdate = "UPDATE Top1000 SET Facebook_Likes=? WHERE Name=?";
							try {
								System.out.println("Likes: " + likes);
								pst = con.prepareStatement(sqlUpdate);
								pst.setString(1, likes);
								pst.setString(2, rs.getString(1));
								pst.executeUpdate();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}else {
							String sqlUpdate = "UPDATE Top1000 SET Facebook_Likes='N/A' WHERE Name=?";
							try {
								pst = con.prepareStatement(sqlUpdate);
								pst.setString(1, rs.getString(1));
								pst.executeUpdate();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}else if(rs.getString(8).contains("N/A")) {
						String sqlUpdate = "UPDATE Top1000 SET Facebook_Likes='N/A' WHERE Name=?";
						try {
							pst = con.prepareStatement(sqlUpdate);
							pst.setString(1, rs.getString(1));
							pst.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					System.out.println("******************************************");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
