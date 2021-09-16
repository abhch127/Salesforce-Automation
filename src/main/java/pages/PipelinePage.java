package pages;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utilities.GenericUtils;

public class PipelinePage {
	
	public WebDriver driver;
	public LinkedHashMap<String, By> objectRepository;
	public GenericUtils refGenericUtils;
	
	public PipelinePage(WebDriver driver, LinkedHashMap<String, By> objectRepository) {
		this.driver = driver;
		this.objectRepository = objectRepository;
		refGenericUtils = new GenericUtils(driver);
	}
	
	public void clear_textbox_values(String textBox_element_name) {
		refGenericUtils.ClearTextBox(objectRepository.get(textBox_element_name), textBox_element_name);
	}

}
