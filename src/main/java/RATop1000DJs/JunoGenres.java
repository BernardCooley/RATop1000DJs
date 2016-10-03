package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.CopyDBFile;
import FrameworkUtils.DBConnection;

public class JunoGenres {

	public static void getJunoInfo(WebDriver driver, String junoUrl) {

		CopyDBFile.copyDBFile();
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;
		ArrayList<String> namesList = new ArrayList<String>();
		ArrayList<String> genresList = new ArrayList<String>();
		ArrayList<WebElement> namesElementList = new ArrayList<WebElement>();
		ArrayList<WebElement> releaseAmtElementList = new ArrayList<WebElement>();
		List<By> namesByList = new ArrayList<By>();
		List<By> releaseAmtByList = new ArrayList<By>();
		String name = null;
		String releaseAmount = null;
		String currentURL = "N/A";
		int listSize = 0;

		// Get all label names from DB
		String sqlSelect = "SELECT * FROM RATop1000DJs";
		try {
			pst = con.prepareStatement(sqlSelect);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				namesList.add(rs.getString(1));
				// junoURLList.add(rs.getString(13));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		driver.get(junoUrl);
		
		for (String s : namesList) {
			CommonFunctions.enterString(driver, UiMap.JDPageElements.searchBox, "\""+s.toLowerCase()+"\"");
			Select select = new Select(driver.findElement(UiMap.JDPageElements.typeDropdown));
			select.selectByVisibleText("Artists");
			CommonFunctions.clickElement(driver, UiMap.JDPageElements.searchBtn);
			CommonFunctions.customWait(driver, 2);
			
			for (WebElement we : CommonFunctions.getArrayOfElements(driver, UiMap.JDPageElements.genres)) {
				if (we.findElements(By.cssSelector("a")).size() > 0) {
					genresList.add(we.findElement(By.cssSelector("a > b > strong > div > abbr")).getText());
				}
			}
			
			StringBuilder sb = new StringBuilder();
			
			for (String str : genresList) {
				sb.append(str);
				sb.append(", ");
			}
			sb.replace(sb.length()-2, sb.length(), "");
			
			System.out.println(sb.toString());
			
			
//			String sqlUpdate = "UPDATE RATop1000DJs SET Genres=";
//			
//			try {
//				pst = con.prepareStatement(sqlUpdate);
//				pst.setString(1, );
//				pst.setString(2, );
//				pst.executeUpdate();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			
			
			
			
			
			
			
			
			
			
			
			CommonFunctions.customWait(driver, 5);
			driver.get(junoUrl);
			
			
			
		}
		
		
		
		
		
		

	}

}
