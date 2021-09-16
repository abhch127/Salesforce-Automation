package pages;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import factory.DriverFactory;
import utilities.GenericUtils;

public class LoginPage {
	
	public WebDriver driver;
	public LinkedHashMap<String, By> objectRepository;
	LinkedHashMap<Object, Object> envDetails;
	String usernumber;
	public GenericUtils refGenericUtils;
	
	public LoginPage(WebDriver driver, LinkedHashMap<Object, Object> envDetails, LinkedHashMap<String, By> objectRepository, String usernumber) {
		this.driver = driver;
		this.objectRepository = objectRepository;
		this.envDetails = envDetails;
		this.usernumber = usernumber;
		refGenericUtils = new GenericUtils(driver);
	}
	
	public void loginToApplication() {
		
		String	url = envDetails.get("url").toString();
		String	username = envDetails.get("username"+Integer.parseInt(usernumber.split("\\.")[0])).toString();
		String  password = envDetails.get("password"+Integer.parseInt(usernumber.split("\\.")[0])).toString();
		
		DriverFactory.getDriver().get(url);
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.toEnterTextValue(objectRepository.get("LoginPage.UserName"), username, "Username");
		refGenericUtils.toEnterTextValue(objectRepository.get("LoginPage.Password"), password, "Password");
		refGenericUtils.clickOnElement(objectRepository.get("LoginPage.SubmitButton"), "Submit Button");
		refGenericUtils.waitUntilPageLoads();
	}
	
	public String getPageTitle() {
		refGenericUtils.waitUntilPageLoads();
		return refGenericUtils.pageTitle();
	}
}
