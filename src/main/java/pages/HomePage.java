package pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import io.cucumber.datatable.DataTable;
import utilities.GenericUtils;

public class HomePage {
	
	public WebDriver driver;
	public LinkedHashMap<String, By> objectRepository;
	public GenericUtils refGenericUtils;
	
	public static String account_name_text;
	
	public HomePage(WebDriver driver, LinkedHashMap<String, By> objectRepository) {
		this.driver = driver;
		this.objectRepository = objectRepository;
		refGenericUtils = new GenericUtils(driver);
	}
	
	public void click_using_java_script(String element_name) {
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.click_using_javaScript(objectRepository.get(element_name), element_name);
	}
	
	public boolean click_on_the_Element(String element_name) {
		boolean isClicked = refGenericUtils.clickOnElement(objectRepository.get(element_name), element_name);
		return isClicked;
	}
	
	public void click_using_actions(String element_name) {
		refGenericUtils.clickUsingActions(objectRepository.get(element_name), element_name);
	}
	
	public void wait_for_any_element_or_page_load(String element_name) {
		if(element_name.contains("Page"))
			refGenericUtils.waitUntilPageLoads();
		else
			refGenericUtils.waitForElement(objectRepository.get(element_name), 10, element_name);
	}
	
	public void click_on_record_type_Radio_button(String record_type) {
		refGenericUtils.click_using_javaScript(objectRepository.get(record_type), record_type);
	}
	
	public void enter_text_values(String textBox_element_name, String text_value) {
		refGenericUtils.toEnterTextValue(objectRepository.get(textBox_element_name), text_value, textBox_element_name);
	}
	
	public String enter_new_account_name(String textBox_element_name, String text_value) {
		account_name_text = text_value+"_"+refGenericUtils.get_Date("MMMdd'_'HHmm");
		refGenericUtils.toEnterTextValue(objectRepository.get(textBox_element_name), account_name_text, text_value+" Account Name");
		return account_name_text;
	}
	
	public void global_search_textbox_existing(String acc_or_opp_or_ppl, String text_value, String textBox_element_name) {
		refGenericUtils.clickOnElement(objectRepository.get(textBox_element_name), text_value+" Option");
		By by_xpath = By.xpath("//span[@title='"+text_value+"']/ancestor::div[@role='option']");
//		By by_xpath = By.xpath("//span[contains(text(), '"+acc_or_opp_or_ppl+"')]/ancestor::span[@title='"+text_value+"']/ancestor::div[@role='option']");
		refGenericUtils.toEnterTextValue(objectRepository.get("HomePage Global Search TextBox"), text_value, textBox_element_name);
		refGenericUtils.waitForElement(by_xpath, 10, text_value+" Option");
		refGenericUtils.clickOnElement(by_xpath, text_value+" Option");
	}
	
	public void enter_account_information(DataTable dataTable) {
		List<Map<String, String>> map_of_feature_file_info = dataTable.asMaps();
		Map<String,String> map_of_account_info = new LinkedHashMap<String, String>();
		Map<String,String> account_info = new LinkedHashMap<String, String>();
		for(int i=0;i<map_of_feature_file_info.size();i++) {
			map_of_account_info = map_of_feature_file_info.get(i);
			String label = map_of_account_info.get("ElementName");
			String value = map_of_account_info.get("Values");
			account_info.put(label, value);
		}
		
		account_info.forEach((label, value) -> {
			By form_elements_xpath;
			if((label.equals("Account Name"))||(label.equals("Billing City")||(label.equals("Billing Zip/Postal Code")))) {
				form_elements_xpath = By.xpath("//span[text()='"+label+"']/../..//input[@type='text']");
				refGenericUtils.waitForElement(form_elements_xpath, 5, label+" Combobox");
				refGenericUtils.scrollToViewElement(form_elements_xpath, label+" Combobox");
				refGenericUtils.toEnterTextValue(form_elements_xpath, value, label);
			}
			else if(label.equals("Type")||label.equals("Billing State/Province")||label.equals("Credit Status")) {
				form_elements_xpath = By.xpath("//span[text()='"+label+"']/../..//a[@class='select']");
				refGenericUtils.waitForElement(form_elements_xpath, 5, label+" dropdown");
				refGenericUtils.scrollToViewElement(form_elements_xpath, label+" dropdown");
				refGenericUtils.clickOnElement(form_elements_xpath, label+" dropdown");
				By dropdown_list = By.xpath("//div[contains(@class, 'select-options') and contains(@class, 'uiMenuList')]//li/a[@title='"+value+"']");
				refGenericUtils.waitForElement(dropdown_list, 5, value);
				refGenericUtils.clickOnElement(dropdown_list, value);
			}
			else if(label.equals("Billing Street")) {
				form_elements_xpath = By.xpath("//span[text()='"+label+"']/../..//textarea[@role='textbox']");
				refGenericUtils.waitForElement(form_elements_xpath, 5, label+" textbox");
				refGenericUtils.scrollToViewElement(form_elements_xpath, label+" textbox");
				refGenericUtils.toEnterTextValue(form_elements_xpath, value, label);
			}
		});
	}
}
