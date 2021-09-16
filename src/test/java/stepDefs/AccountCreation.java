package stepDefs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.testng.Assert;

import base.BaseUtil;
import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.GenericUtils;

public class AccountCreation extends BaseUtil {
	
	public static String account_name_text;
	public GenericUtils refGenericUtils = new GenericUtils(DriverFactory.getDriver());
	public LoginPage loginPage = new LoginPage(DriverFactory.getDriver(), envDetails, objectRepository, usernumber);
	
	@Given("Admin has already logged into the application")
	public void admin_has_already_logged_into_the_application() {
		loginPage.loginToApplication();
	}
	
	@When("user clicks on {string}")
	public void user_clicks_on(String element_name) {
		switch(element_name) {
			case "HomePage Accounts Tab":
				refGenericUtils.waitUntilPageLoads();
				refGenericUtils.click_using_javaScript(objectRepository.get(element_name), element_name);
				break;
			case "HomePage GearIcon":
				refGenericUtils.stop_script_for(10000);
				refGenericUtils.clickOnElement(objectRepository.get(element_name), element_name);
				break;
			case "Approve Bread Crumb":
				refGenericUtils.stop_script_for(5000);
				refGenericUtils.clickUsingActions(objectRepository.get(element_name), element_name);
				break;
			case "Mark as Current Account Approval Status Button":
				refGenericUtils.stop_script_for(5000);
				refGenericUtils.clickUsingActions(objectRepository.get(element_name), element_name);
				break;
			default:
				refGenericUtils.clickOnElement(objectRepository.get(element_name), element_name);
				break;
		}
	}
	
	@When("User creates new account for {string} Record type")
	public void user_creates_new_account_for_record_type(String record_type, DataTable dataTable) {
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.click_using_javaScript(objectRepository.get("HomePage.AccountsTab"), "Accounts Tab");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.NewButton"), "New Button");
		refGenericUtils.waitForElement(objectRepository.get("NewAccount.Header"), 10, "New Account Header");
		refGenericUtils.click_using_javaScript(objectRepository.get("NewAccount."+record_type+"RadioButton"), record_type+" Radio Button");
		refGenericUtils.clickOnElement(objectRepository.get("NewAccount.NextButton"), "Next Button");
		refGenericUtils.waitForElement(objectRepository.get("ExistingAccountMatch.Header"), 10, "Search For An Active Existing Account Match Header");
		refGenericUtils.clickOnElement(objectRepository.get("ExistingAccountMatch.CreateNew"+record_type+"Button"), "Create New "+record_type+" Button");
		refGenericUtils.waitForElement(objectRepository.get(record_type+".Header"), 10, "New Account "+record_type+" Header");
		enter_account_information(dataTable);
		refGenericUtils.clickOnElement(objectRepository.get("NewAccount.CopyAddress.Checkbox"), "Copy Address Checkbox");
		refGenericUtils.clickOnElement(objectRepository.get("NewAccount.Save.Button"), "Save Button");
		refGenericUtils.waitForElement(objectRepository.get("AccountCreated.Notification"), 10, "Account Created Notification");
		String actual_account = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("AccountCreated.Notification"), "Account Created Notification");
		if(actual_account.equals(account_name_text)) {
			BaseUtil.scenario.log("Hurray!!! "+actual_account+" created successfully");
			refGenericUtils.take_screenshot();
		}
		else {
			Assert.fail("Failed to create the New account");
			refGenericUtils.take_screenshot();
		}
	}
	
	public void enter_account_information(DataTable dataTable) {
		List<Map<String, String>> map_of_feature_file_info = dataTable.asMaps();
		Map<String,String> map_of_account_info = new LinkedHashMap<String, String>();
		Map<String,String> account_info = new LinkedHashMap<String, String>();
		for(int i=0;i<map_of_feature_file_info.size();i++) {
			map_of_account_info = map_of_feature_file_info.get(i);
			String label = map_of_account_info.get("Element Name");
			String value = map_of_account_info.get("Values");
			account_info.put(label, value);
		}
		
		account_info.forEach((label, value) -> {
			if(label.endsWith("AccountName")) {
				account_name_text = value.replace("{TimeStamp}", refGenericUtils.get_Date("MMMdd'_'HHmm"));
				refGenericUtils.waitForElement(objectRepository.get(label), 5, label);
				refGenericUtils.scrollToViewElement(objectRepository.get(label), label);
				refGenericUtils.toEnterTextValue(objectRepository.get(label), account_name_text, label);
			}
			else if((label.endsWith("TextBox"))||(label.endsWith("TextArea"))) {
				refGenericUtils.waitForElement(objectRepository.get(label), 5, label);
				refGenericUtils.scrollToViewElement(objectRepository.get(label), label);
				refGenericUtils.toEnterTextValue(objectRepository.get(label), value, label);
			}
			else if(label.endsWith("Dropdown")) {
				refGenericUtils.waitForElement(objectRepository.get(label), 5, label);
				refGenericUtils.scrollToViewElement(objectRepository.get(label), label);
				refGenericUtils.clickOnElement(objectRepository.get(label), label);
				By dropdown_list = By.xpath("//div[contains(@class, 'select-options') and contains(@class, 'uiMenuList')]//li/a[@title='"+value+"']");
				refGenericUtils.waitForElement(dropdown_list, 5, value);
				refGenericUtils.clickOnElement(dropdown_list, value);
			}
		});
	}
}
