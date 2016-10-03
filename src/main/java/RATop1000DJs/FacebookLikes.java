package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.DBConnection;

public class FacebookLikes {

	public static void getFBLikes(WebDriver driver) {

		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;

		String sqlSelect = "SELECT * FROM RATop1000DJs";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				if (rs.getString(13).equals("No")) {
					if (!rs.getString(8).equals("N/A") || rs.getString(8).length() > 0) {
						driver.get(rs.getString(8));
						driver.get("https://www.facebook.com/HenrySaizOfficial/");
						String likes = CommonFunctions.getElementText(driver, UiMap.FacebookPageElements.likes).replace(" likes", "").replace(",", "");
						System.out.println(likes);
						
						String sqlUpdate = "UPDATE RATop1000DJs SET Facebook_Likes=? WHERE Name=?";
						try {
							pst = con.prepareStatement(sqlUpdate);
							pst.setString(1, likes);
							pst.setString(2, rs.getString(1));
							pst.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
