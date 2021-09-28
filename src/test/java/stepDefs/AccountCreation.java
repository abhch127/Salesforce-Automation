package stepDefs;

import java.io.IOException;
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
	public void admin_has_already_logged_into_the_application() throws IOException {
		loginPage.loginToApplication();
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
		enter_values(dataTable);
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
	
	@When("{string} approves the account")
	public void approves_the_account(String approver_name) {
		refGenericUtils.stop_script_for(10000);
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.GearIcon"), "Gear Icon");
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.GearIcon.SetupOption"), "Setup Option");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.switchingTabs(DriverFactory.getDriver().getWindowHandle(), DriverFactory.getDriver().getWindowHandles());
		refGenericUtils.waitForElement(objectRepository.get("SetupPage.GlobalSearch.TextBox"), 5, "Global Search TextBox");
		global_search_textbox(approver_name, "SetupPage.GlobalSearch.TextBox");
		switch_to_profile_frame(approver_name);
		refGenericUtils.clickOnElement(objectRepository.get("SetupPage.Login.Button"), "Login Button");
		refGenericUtils.waitUntilPageLoads();
		global_search_textbox("New Account", "UserHomePage.GlobalSearch.TextBox");
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.Approve.BreadCrumb"), "Approve Bread Crumb");
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.MarkCurrentAccountApproval.Button"), "Mark as Current Account Approval Status Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.take_screenshot();
		refGenericUtils.scrollToViewElement(objectRepository.get("AccountPage.AccountStatus"), "Account Status");
		String actual_text_value = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("AccountPage.AccountStatus"), "Account Status");
		if(actual_text_value.equals("A")) {
			BaseUtil.scenario.log("Account "+account_name_text+" has been approved successfully");
			refGenericUtils.take_screenshot();
		}
		else {
			Assert.fail("Failed to approve the New account");
			refGenericUtils.take_screenshot();
		}
	}
	
	@When("user creates a Pipeline")
	public void user_creates_a_pipeline(DataTable dataTable) {
		global_search_textbox("New Account", "UserHomePage.GlobalSearch.TextBox");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.clickOnElement(objectRepository.get("AccountPage.CreateNewPipeline.Button"), "Create New Pipeline Button");
		refGenericUtils.waitForElement(objectRepository.get("AccountPage.NewPipeline.Popup"), 10, "New Pipeline Popup");
		refGenericUtils.stop_script_for(3000);
		refGenericUtils.ClearTextBox(objectRepository.get("NewPipelinePopup.Year.TextBox"), "Year TextBox");
		enter_values(dataTable);
		refGenericUtils.clickOnElement(objectRepository.get("NewPipelinePopup.Save.Button"), "NewPipelinePopup.Save.Button");
		refGenericUtils.waitUntilPageLoads();
	}
	
	@When("user clones a Print Opportunity")
	public void user_clones_a_print_opportunity(DataTable dataTable) {
		String Pipeline = "Test_Advertiser_Sep24_1726 2021";
		String Opportunity = "OPP-0439178";
		global_search_textbox("Test_Advertiser_Sep24_1726", "UserHomePage.GlobalSearch.TextBox");
		search_page_actions("Pipeline", Pipeline);
		refGenericUtils.take_screenshot();
		By by_opp_number = By.xpath("//p[text()='"+Opportunity+"']/..");
		refGenericUtils.stop_script_for(2000);
		refGenericUtils.waitForElement(by_opp_number, 10, Opportunity);
		refGenericUtils.click_using_javaScript(by_opp_number, Opportunity);
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
		refGenericUtils.waitForElement(objectRepository.get("PipelinePage.Clone.Button"), 10, "Clone Button");
		refGenericUtils.scrollToViewElement(objectRepository.get("PipelinePage.Clone.Button"), "Clone Button");
		refGenericUtils.click_using_javaScript(objectRepository.get("PipelinePage.Clone.Button"), "Clone Button");
		refGenericUtils.waitForElement(objectRepository.get("ClonePopup.Header"), 10, "Clone Popup Header");
		enter_values(dataTable);
		String issue = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("ClonePopup.AvailableIssues.DuellistBox"), "Available Issues Duel listBox");
		refGenericUtils.click_using_javaScript(objectRepository.get("ClonePopup.Save.Button"), "Save Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
		String opp_number = refGenericUtils.get_cloned_opportunity(issue);
		BaseUtil.scenario.log("Opportunity "+Opportunity+" has been cloned successfully");
		refGenericUtils.take_screenshot();
	}
	
	public void enter_values(DataTable dataTable) {
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
			else if(label.endsWith("Select")) {
				refGenericUtils.select_dropdown_value(objectRepository.get(label), value, label);
				refGenericUtils.waitUntilPageLoads();
			}
			else if(label.endsWith("DuellistBox")) {
				refGenericUtils.clickOnElement(objectRepository.get(label), value);
				boolean digit_present = refGenericUtils.containsDigit(value);
				if(digit_present) {
					refGenericUtils.clickOnElement(objectRepository.get("MoveSelectionIssues.Button"), "Move Selection Issues Button");
				}
				else {
					refGenericUtils.clickOnElement(objectRepository.get("MoveSelectionTitles.Button"), "Move Selection Titles Button");
				}
			}
		});
	}
	
	public void global_search_textbox(String text_value, String textBox_element_name) {
		refGenericUtils.waitUntilPageLoads();
		switch(textBox_element_name) {
			case "SetupPage.GlobalSearch.TextBox":
				refGenericUtils.toEnterTextValue(objectRepository.get(textBox_element_name), text_value, textBox_element_name);
				refGenericUtils.waitForElement(objectRepository.get("SetupPage.GlobalSearch.Option"), 10, text_value+" Option");
				refGenericUtils.clickOnElement(objectRepository.get("SetupPage.GlobalSearch.Option"), text_value+" Option");
				refGenericUtils.waitUntilPageLoads();
				refGenericUtils.take_screenshot();
				break;
			case "UserHomePage.GlobalSearch.TextBox":
				refGenericUtils.clickOnElement(objectRepository.get(textBox_element_name), "Global Search TextBox");
				if(text_value.equalsIgnoreCase("New Account")) {
					refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.GlobalSearch.TextBox"), account_name_text, textBox_element_name);
					refGenericUtils.waitForElement(objectRepository.get("HomePage.GlobalSearch.AccountOption"), 10, account_name_text+" account");
					refGenericUtils.clickOnElement(objectRepository.get("HomePage.GlobalSearch.AccountOption"), account_name_text+" account");
					refGenericUtils.waitUntilPageLoads();
					refGenericUtils.take_screenshot();
				}
				else {
					refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.GlobalSearch.TextBox"), text_value, textBox_element_name);
					refGenericUtils.waitUntilPageLoads();
					refGenericUtils.keyboard_action(objectRepository.get("HomePage.GlobalSearch.TextBox"), "Enter");
					refGenericUtils.waitUntilPageLoads();
				}
				break;
		}
	}
	
	public void search_page_actions(String type, String value) {
		By by_Opportunity = By.xpath("//a[text()='Opportunities']");
		By by_Accounts = By.xpath("//a[text()='Accounts']");
		By by_Cases = By.xpath("//a[text()='Cases']");
		switch(type) {
			case "Opportunities":
				refGenericUtils.waitForElement(by_Opportunity, 10, type);
				refGenericUtils.scrollToViewElement(by_Opportunity, type);
				refGenericUtils.waitUntilPageLoads();
				refGenericUtils.take_screenshot();
				By by_Opp_link = By.xpath("//span[@title='"+value+"']/ancestor::tr//th//a");
				refGenericUtils.waitForElement(by_Opp_link, 10, value);
				refGenericUtils.clickOnElement(by_Opp_link, value);
				refGenericUtils.waitUntilPageLoads();
				break;
			case "Pipeline":
				refGenericUtils.waitForElement(by_Accounts, 10, type);
				refGenericUtils.scrollToViewElement(by_Accounts, type);
				refGenericUtils.waitUntilPageLoads();
				refGenericUtils.waitForElement(by_Cases, 10, type);
				refGenericUtils.scrollToViewElement(by_Cases, type);
				refGenericUtils.waitUntilPageLoads();
				refGenericUtils.take_screenshot();
				By by_Pipeline_link = By.xpath("//a[@title='"+value+"']");
				refGenericUtils.waitForElement(by_Pipeline_link, 10, value);
				refGenericUtils.clickOnElement(by_Pipeline_link, value);
				refGenericUtils.waitUntilPageLoads();
				break;
		}
	}
	
	public void switch_to_profile_frame(String profile_name) {
		By by_frame_name = By.xpath("//iframe[contains(@title,'"+profile_name+"')]");
		refGenericUtils.switchingFrame(by_frame_name, profile_name);
	}
}
