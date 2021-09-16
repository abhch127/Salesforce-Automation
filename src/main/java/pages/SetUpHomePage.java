package pages;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utilities.GenericUtils;

public class SetUpHomePage {
	
	public WebDriver driver;
	public LinkedHashMap<String, By> objectRepository;
	public GenericUtils refGenericUtils;
	
	public SetUpHomePage(WebDriver driver, LinkedHashMap<String, By> objectRepository) {
		this.driver = driver;
		this.objectRepository = objectRepository;
		refGenericUtils = new GenericUtils(driver);
	}

	public void switch_to_new_tab() {
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.switchingTabs(driver.getWindowHandle(), driver.getWindowHandles());
	}
	
	public void global_search_textbox(String text_value, String textBox_element_name) {
		refGenericUtils.waitUntilPageLoads();
		switch(textBox_element_name) {
			case "Setup Page Global Search TextBox":
				refGenericUtils.toEnterTextValue(objectRepository.get(textBox_element_name), text_value, textBox_element_name);
				refGenericUtils.waitForElement(objectRepository.get("Global Search Option"), 10, text_value+" Option");
				refGenericUtils.clickOnElement(objectRepository.get("Global Search Option"), text_value+" Option");
				break;
			case "User HomePage Global Search TextBox":
				refGenericUtils.clickOnElement(objectRepository.get(textBox_element_name), text_value+" Option");
				if(text_value.equalsIgnoreCase("New Account")) {
					refGenericUtils.toEnterTextValue(objectRepository.get("HomePage Global Search TextBox"), HomePage.account_name_text, textBox_element_name);
					refGenericUtils.waitForElement(objectRepository.get("Global Search Account Option"), 10, text_value+" Option");
					refGenericUtils.clickOnElement(objectRepository.get("Global Search Account Option"), text_value+" Option");
				}
				break;
		}
	}
	
	public void switch_to_frame(String frame_name) {
		By by_frame_name = By.xpath("//iframe[contains(@title,'"+frame_name+"')]");
		refGenericUtils.switchingFrame(by_frame_name, frame_name);
	}

}
