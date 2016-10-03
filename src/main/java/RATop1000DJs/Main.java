package RATop1000DJs;

import org.openqa.selenium.WebDriver;

import FrameworkUtils.BrowserLauncher;

public class Main {

	public static void main(String[] args) {
		
		String rAUrl = "https://www.residentadvisor.net/dj.aspx?genre=list";
		String junoUrl = "http://www.junodownload.com/";
		
		String browser = "Firefox";
		BrowserLauncher bL = new BrowserLauncher();
    	WebDriver driver = bL.launchBrowser(browser);
		
//		RATop1000DJs.getTop1000DJs(driver, url);
    	
    	JunoGenres.getJunoInfo(driver, junoUrl);
	}

}
