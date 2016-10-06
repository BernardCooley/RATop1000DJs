package RATop1000DJs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import FrameworkUtils.CommonFunctions;
import FrameworkUtils.CopyDBFile;
import FrameworkUtils.DBConnection;
import UiMap.RADJs;

public class RATop1000DJs {
	public static void getTop1000DJs(WebDriver driver, String rAUrl) {
//		CopyDBFile.copyDBFile();
		Connection con = null;
		con = DBConnection.dbConnector();
		PreparedStatement pst = null;

		driver.get(rAUrl);

		int count = 1;

//		WebElement we = CommonFunctions.getArrayOfElements(driver, RADJs.dJs).get(count);
		
		
		for (WebElement we : CommonFunctions.getArrayOfElements(driver, RADJs.dJs)) {
			if (we.findElements(By.cssSelector("span:nth-of-type(3)")).size() > 0) {
				System.out.println("*********************");
				for (WebElement we1 : we.findElements(By.cssSelector("span:nth-of-type(3) > a"))) {
					String href = we1.getAttribute("href");
					if (href.contains("/dj/")) {
						System.out.println(href);
						String sqlInsert = "INSERT INTO Top1000 (Name, RA_URL) VALUES (?, ?)";
						try {
							pst = con.prepareStatement(sqlInsert);
							pst.setString(1, we1.getText());
							pst.setString(2, href);
							pst.executeUpdate();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

				}
			}
		}
		
		driver.close();
	}
}
