package UiMap;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

public class FacebookPageElements {

	public static final By likes = By
			.cssSelector("div[role='main'] > div:nth-of-type(2) > div > div:nth-of-type(2) > div > div > div > div");
	public static final By emailField = By.cssSelector("input#email");
	public static final By passwordField = By.cssSelector("input#pass");
	public static final By loginBtn = By.cssSelector("button#loginbutton");
	public static final By likesBtn = By.cssSelector("a[href*='/likes']");
}