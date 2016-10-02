package RATop1000DJs;

import org.openqa.selenium.WebDriver;

import FrameworkUtils.BrowserLauncher;

public class Main {

	public static void main(String[] args) {
		
		String url = "https://www.residentadvisor.net/dj.aspx?genre=list";
		
		String browser = "Firefox";
		BrowserLauncher bL = new BrowserLauncher();
    	WebDriver driver = bL.launchBrowser(browser);
		
		RATop1000DJs.getTop1000DJs(driver, url);
	}

}
