package UiMap;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

public class SoundcloudElements {

	public static final By signInBtn = By.cssSelector("button.loginButton");
	public static final By emailField = By.cssSelector("input[name='username']");
	public static final By continueBtn = By.cssSelector("button[title='Continue']");
	public static final By passwordField = By.cssSelector("input[name='password']");
	public static final By logInBtn = By.cssSelector("button[title='Sign in']");
	public static final By sendMessageBtn = By.cssSelector("a[title='Send a message']");
	public static final By messageTextBox = By.cssSelector("textarea");
	public static final By addTrackBtn = By.cssSelector("button.composeMessage__addSoundButton");
	public static final By sendBtn = By.cssSelector("button.composeMessage__sendButton");
	public static final By signInForm = By.cssSelector("div.signinForm__steps");
}