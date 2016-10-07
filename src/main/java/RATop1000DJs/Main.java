package RATop1000DJs;

import org.openqa.selenium.WebDriver;

import FrameworkUtils.BrowserLauncher;

public class Main {

	public static void main(String[] args) {
		
		String rAUrl = "https://www.residentadvisor.net/dj.aspx";
		String junoUrl = "http://www.junodownload.com/";
		
		String browser = "PhantomJS";
		BrowserLauncher bL = new BrowserLauncher();
    	WebDriver driver = bL.launchBrowser(browser);
		
//		RATop1000DJs.getTop1000DJs(driver, rAUrl);
    	
    	JunoGenres.getJunoInfo(driver, junoUrl);
    	
//    	RADJInfo.getRADJInfo(driver);
    	
//    	FacebookLikes.getFBLikes(driver);
    	
//    	GetDJsEmails.getEmails(driver);
	}

}
