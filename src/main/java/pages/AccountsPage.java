package pages;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utilities.GenericUtils;

public class AccountsPage {
	
	public WebDriver driver;
	public LinkedHashMap<String, By> objectRepository;
	public GenericUtils refGenericUtils;
	
	public AccountsPage(WebDriver driver, LinkedHashMap<String, By> objectRepository) {
		this.driver = driver;
		this.objectRepository = objectRepository;
		refGenericUtils = new GenericUtils(driver);
	}
	
	public String get_element_text_value(String text_value) {
		String element_text = refGenericUtils.fetchingTextvalueofElement(objectRepository.get(text_value), text_value);
		return element_text;
	}
	
	public void scroll_to_view_element(String element_name) {
		refGenericUtils.scrollToViewElement(objectRepository.get(element_name), element_name);
	}

}
