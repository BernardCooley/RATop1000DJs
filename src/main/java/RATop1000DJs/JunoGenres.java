package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.CopyDBFile;
import FrameworkUtils.DBConnection;

public class JunoGenres {

	public static void getJunoInfo(WebDriver driver, String junoUrl) {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		CopyDBFile.copyDBFile();
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		ArrayList<String> namesList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		String[] genresSplit = null;

		// Get all label names from DB
		String sqlSelect = "SELECT * FROM Top1000";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				if (rs.getString(11).equals("No")) {
					namesList.add(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		driver.get(junoUrl);

		for (String s : namesList) {
			System.out.println("****************************************************************************************");
			CommonFunctions.clearTextField(driver, UiMap.JDPageElements.searchBox);
			CommonFunctions.enterString(driver, UiMap.JDPageElements.searchBox, "\"" + s.toLowerCase() + "\"");
			Select select = new Select(driver.findElement(UiMap.JDPageElements.typeDropdown));
			select.selectByVisibleText("Artists");
			CommonFunctions.clickElement(driver, UiMap.JDPageElements.searchBtn);
			CommonFunctions.customWait(driver, 2);
			try {
				if (driver.findElements(UiMap.JDPageElements.genres).size() > 0) {
					for (WebElement we : CommonFunctions.getArrayOfElements(driver, UiMap.JDPageElements.genres)) {
						if (we.getText().contains("Genre")) {
							genresSplit = we.getText().split("\\r?\\n");
							for (int i = 2; i < genresSplit.length; i+=2) {
								sb.append(genresSplit[i] + ", ");
								if (i == 6) {
									break;
								}
							}
						}
					}
					sb.replace(sb.length()-2, sb.length(), "");
				}
				System.out.println(s);
				System.out.println(sb.toString());
				System.out.println(namesList.size() - namesList.indexOf(s) + " remaining");
				String sqlUpdate = "UPDATE Top1000 SET Genres=?, Juno_Genre_Scanned='Yes', Juno_URL=? WHERE Name=?";

				pst = con.prepareStatement(sqlUpdate);
				pst.setString(1, sb.toString());
				pst.setString(2, driver.getCurrentUrl());
				pst.setString(3, s);

				pst.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			sb.setLength(0);
		}
	}
}
