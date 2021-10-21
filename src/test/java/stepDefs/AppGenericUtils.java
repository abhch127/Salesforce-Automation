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
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.GenericUtils;

public class AppGenericUtils extends BaseUtil {
	
	public static String account_name_text;
	public static String case_number;
	public static String actual_assignment_type;
	
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
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.NewButton"), "New Button");
		refGenericUtils.waitForElement(objectRepository.get("NewAccount.Header"), 10, "New Account Header");
		refGenericUtils.click_using_javaScript(objectRepository.get("NewAccount."+record_type+"RadioButton"), record_type+" Radio Button");
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("NewAccount.NextButton"), "Next Button");
		refGenericUtils.waitForElement(objectRepository.get("ExistingAccountMatch.Header"), 10, "Search For An Active Existing Account Match Header");
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("ExistingAccountMatch.CreateNew"+record_type+"Button"), "Create New "+record_type+" Button");
		refGenericUtils.waitForElement(objectRepository.get("NewAccountCreation.Header"), 10, "New Account "+record_type+" Header");
		enter_values_updated(dataTable);
//		refGenericUtils.clickOnElement(objectRepository.get("NewAccount.CopyAddress.Checkbox"), "Copy Address Checkbox");
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("NewAccount.Save.Button"), "Save Button");
		refGenericUtils.waitForElement(objectRepository.get("AccountCreated.Notification"), 10, "Account Created Notification");
		String actual_account = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("AccountCreated.Notification"), "Account Created Notification");
		System.out.println("Account created : "+ actual_account);
		if(actual_account.equals(account_name_text)) {
			BaseUtil.scenario.log("\'"+actual_account+"\'"+" has been created successfully");
			refGenericUtils.take_screenshot();
			AccountName=actual_account;
		}
		else {
			Assert.fail("Failed to create the New account");
			refGenericUtils.take_screenshot();
			AccountName="Not Created";
		}
	}
	
	@When("{string} approves the account")
	public void approves_the_account(String approver_name) {
		String account_name = AccountName;
		navigate_to_profile(approver_name);
		globalSearch("Accounts", account_name);
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
		refGenericUtils.closeCurrentTab();////a[contains(text(),'Log out as')]
		loginPage.loginToApplication();
	}
	
	@When("{string} tries to {string} an account")
	public void tries_to_an_account(String approver_name, String decision) {
		String account_name = AccountName;
		navigate_to_profile(approver_name);
		globalSearch("Accounts", account_name);
		refGenericUtils.stop_script_for(5000);
		if(decision.equalsIgnoreCase("approve")) 
			refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.Approve.BreadCrumb"), "Approve Bread Crumb");
		else if(decision.equalsIgnoreCase("reject")) 
			refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.Rejected.BreadCrumb"), "Rejected Bread Crumb");
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.MarkCurrentAccountApproval.Button"), "Mark as Current Account Approval Status Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.take_screenshot();
		refGenericUtils.scrollToViewElement(objectRepository.get("AccountPage.AccountStatus"), "Account Status");
		String actual_text_value = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("AccountPage.AccountStatus"), "Account Status");
		if(actual_text_value.equals("")) {
			BaseUtil.scenario.log("\'"+approver_name+"\'"+" is not authorized to "+decision+" an account");
			refGenericUtils.take_screenshot();
		}
		else {
			Assert.fail(decision+" was successful, which is not expected");
			refGenericUtils.take_screenshot();
		}
		refGenericUtils.closeCurrentTab();
	}
	
	@When("{string} rejects the account")
	public void rejects_the_account(String approver_name) throws InterruptedException, IOException {
		String account_name = AccountName;
		navigate_to_profile(approver_name);
		globalSearch("Accounts", account_name);
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.Rejected.BreadCrumb"), "Rejected Bread Crumb");
		refGenericUtils.stop_script_for(3000);
		refGenericUtils.waitForElement(objectRepository.get("AccountPage.RejectedReason"), 10, "AccountPage.RejectedReason");
		refGenericUtils.scrollToViewElement(objectRepository.get("AccountPage.RejectedReason"), "AccountPage.RejectedReason");
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.RejectedReason"), "AccountPage.RejectedReason");
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.RejectedReasonDropdown"), "AccountPage.RejectedReasonDropdown");
		refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.RejectedReasonDropdown"), "RejectedReasonDropdown");
		//refGenericUtils.keyboard_action(objectRepository.get("AccountPage.RejectedReasonDropdown"), "Double-Down-Enter");
		refGenericUtils.click_using_javaScript(objectRepository.get("Accountpage.Reject"), "Accountpage.Reject");
		refGenericUtils.toEnterTextValue(objectRepository.get("AccountPage.DisableReason"), "testing", "AccountPage.DisableReason");
		refGenericUtils.click_using_javaScript(objectRepository.get("NewPipelinePopup.Save.Button"), "NewPipelinePopup.Save.Button");
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.MarkCurrentAccountApproval.Button"), "Mark as Current Account Approval Status Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.take_screenshot();
		refGenericUtils.scrollToViewElement(objectRepository.get("AccountPage.AccountStatus"), "Account Status");
		String actual_text_value = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("AccountPage.AccountStatus"), "Account Status");
		if(actual_text_value.equals("I")) {
			BaseUtil.scenario.log("Account "+account_name_text+" has been Rejected successfully");
			refGenericUtils.take_screenshot();
		}else if(actual_text_value.equals("")) {
			BaseUtil.scenario.log("\'"+approver_name+"\'"+" is not authorized to approve/reject an account");
			refGenericUtils.take_screenshot();
		}
		else {
			Assert.fail("Failed to approve the New account");
			refGenericUtils.take_screenshot();
		}
		refGenericUtils.closeCurrentTab();////a[contains(text(),'Log out as')]
		loginPage.loginToApplication();
	}
	
	@When("user creates a Pipeline")
	public void user_creates_a_pipeline(DataTable dataTable) {
		String account_name = AccountName;
		globalSearch("Accounts", account_name);
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.clickOnElement(objectRepository.get("AccountPage.CreateNewPipeline.Button"), "Create New Pipeline Button");
		refGenericUtils.waitForElement(objectRepository.get("AccountPage.NewPipeline.Popup"), 10, "New Pipeline Popup");
		refGenericUtils.stop_script_for(3000);
		refGenericUtils.waitForElement(objectRepository.get("NewPipelinePopup.Year.TextBox"), 10, "New Pipeline Popup");
		refGenericUtils.ClearTextBox(objectRepository.get("NewPipelinePopup.Year.TextBox"), "Year TextBox");
		enter_values_updated(dataTable);
		refGenericUtils.click_using_javaScript(objectRepository.get("NewPipelinePopup.Save.Button"), "NewPipelinePopup.Save.Button");
		refGenericUtils.waitUntilPageLoads();
	}
	
	@When("user clones a {string} Opportunity")
	public void user_clones_a_opportunity(String Opp_Type, DataTable dataTable) {
		String Pipeline = "TEST_ADVERTISER_OCT05_1131 2021";
		String Opportunity = "OPP-0439214";
		globalSearch("Pipeline", Pipeline);
		refGenericUtils.take_screenshot();
		By by_opp_number = By.xpath("//p[text()='"+Opportunity+"']/..");
		refGenericUtils.stop_script_for(4000);
		refGenericUtils.waitForElement(by_opp_number, 10, Opportunity);
		refGenericUtils.click_using_javaScript(by_opp_number, Opportunity);
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
		refGenericUtils.waitForElement(objectRepository.get("PipelinePage.Clone.Button"), 10, "Clone Button");
		refGenericUtils.scrollToViewElement(objectRepository.get("PipelinePage.Clone.Button"), "Clone Button");
		refGenericUtils.click_using_javaScript(objectRepository.get("PipelinePage.Clone.Button"), "Clone Button");
		refGenericUtils.stop_script_for(2000);
		refGenericUtils.waitForElement(objectRepository.get("ClonePopup.Header"), 10, "Clone Popup Header");
		enter_values_updated(dataTable);
		String issue = refGenericUtils.fetch_attribute_value(objectRepository.get("ClonePopup.SelectedIssue.DuellistBox"), "title", "Selected Issue Duel listBox");
		refGenericUtils.take_screenshot();
		refGenericUtils.click_using_javaScript(objectRepository.get("ClonePopup.Save.Button"), "Save Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
		refGenericUtils.stop_script_for(5000);
		String opp_number = refGenericUtils.get_cloned_opportunity(issue);
		if(!opp_number.equals("")) {
			BaseUtil.scenario.log("Opportunity "+"\'"+opp_number+"\'"+" has been created successfully which is a clone of "+"\'"+Opportunity+"\'");
			refGenericUtils.take_screenshot();
		}
		else {
			BaseUtil.scenario.log("Failed to clone the opportunity "+Opportunity);
			Assert.fail("Failed to clone the opportunity "+Opportunity);
			refGenericUtils.take_screenshot();
		}
	}
	
	@When("user creates Account assignment for {string} as Record type")
	public void user_selects_as_record_type(String record_type, DataTable dataTable) throws InterruptedException {
		String advertiser_name = AccountName;
		String title = "EATING WELL";
		search_using_waffle("Account Assignments");
		switch_to_frame(1);
		refGenericUtils.waitForElement(objectRepository.get("AccountAssignments.NewAccountAssignments.Button"), 10, "New Account Assignments Button");
		refGenericUtils.take_screenshot();
		refGenericUtils.click_using_javaScript(objectRepository.get("AccountAssignments.NewAccountAssignments.Button"), "New Account Assignments Button");
		refGenericUtils.switch_to_default_frame();
		refGenericUtils.waitUntilPageLoads();
		switch_to_frame(2);
		refGenericUtils.select_dropdown_value(objectRepository.get("AccountAssignments.RecordType.Select"), record_type, "Record Type");
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("AccountAssignments.Continue.Button"), "Continue Button");
		refGenericUtils.switch_to_default_frame();
		refGenericUtils.waitUntilPageLoads();
		switch_to_frame(2);
		refGenericUtils.waitForElement(objectRepository.get("AccountAssignments.Advertiser.TextBox"), 10, "Advertiser TextBox");
		refGenericUtils.toEnterTextValue(objectRepository.get("AccountAssignments.Advertiser.TextBox"), advertiser_name, "Advertiser TextBox");
		By by_titles = By.xpath("//label[text()='Available Titles']/../..//select[@class='multilist']");
		refGenericUtils.select_dropdown_value(by_titles, title, "Available Titles");
		refGenericUtils.click_using_javaScript(objectRepository.get("AccountAssignments.RightArrow.Button"), "Right Arrow Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("AccountAssignments.Next.Button"), "Next Button");
		refGenericUtils.switch_to_default_frame();
		refGenericUtils.waitUntilPageLoads();
		Thread.sleep(5000);
		switch_to_frame(2);
		Map<String, Integer> map_of_cols = refGenericUtils.get_col_location(objectRepository.get("AccountAssignments.ColoumnNames"), "Coloumn Names");
		account_assignment_data(dataTable, map_of_cols);
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("AccountAssignments.Save.Button"), "Save Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.waitForElement(objectRepository.get("AccountAssignments.AlertText"), 10, "Alert Text Message");
		String alert_message = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("AccountAssignments.AlertText"), "Alert Text Message");
		if(alert_message.contains("Saved successfully")) {
			BaseUtil.scenario.log("Record has been Saved Successfully");
			refGenericUtils.take_screenshot();
		}
		else {
			BaseUtil.scenario.log("Failed to create the record");
			Assert.fail("Failed to create the record");
			refGenericUtils.take_screenshot();
		}
	}
	
	@When("user creates a new Account Assignment Request")
	public void user_creates_a_new_Account_Assignment_Request(DataTable dataTable) {
		search_using_waffle("Cases");
		refGenericUtils.take_screenshot();
		refGenericUtils.waitForElement(objectRepository.get("HomePage.NewButton"), 10, "New Button");
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.NewButton"), "New Button");
		refGenericUtils.waitForElement(objectRepository.get("NewCasePopup.NewCase.Header"), 10, "New Case Popup Header");
		refGenericUtils.clickOnElement(objectRepository.get("NewCasePopup.AccountAssignment.RadioButton"), "Account Assignment RadioButton");
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("NewCasePopup.Next.Button"), "Next Button");
		refGenericUtils.waitForElement(objectRepository.get("NewcasePopup.NewCase.AccountAssignment.Header"), 10, "Account Assignment Header");
		refGenericUtils.stop_script_for(2000);
		enter_values_updated(dataTable);
		refGenericUtils.scrollToViewElement(objectRepository.get("NewCasePopup.Status.Label"), "Status Label");
		actual_assignment_type = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("NewCasePopup.AssignmentType"), "Assignment Type");
	}
	
	@Then("verify the non-mandatory field {string}")
	public void verify_the_non_mandatory_field(String filed_name) {
		By by_mandotory = By.xpath("//span[text()='"+filed_name+"']/..//span[@title='required']");
		int count = refGenericUtils.findElementsCount(by_mandotory, filed_name);
		if(actual_assignment_type.equals("F360")) {
			BaseUtil.scenario.log("User is able to select the Assignment Type as "+"\'"+actual_assignment_type+"\'");
			refGenericUtils.take_screenshot();
			//------------Validate for Title & Start Issue----------------//
			if((count==0)) {
				BaseUtil.scenario.log("\'"+filed_name+"\'"+" is not mandatory when user selects "+"\'"+actual_assignment_type+"\'");
				refGenericUtils.take_screenshot();
			}
			else {
				BaseUtil.scenario.log("\'"+filed_name+"\'"+" is still mandatory for "+"\'"+actual_assignment_type+"\'"+" which is not expected");
				softAssert.fail("Title and Start Issue Name is still mandatory for "+"\'"+actual_assignment_type+"\'"+" which is not expected");
				refGenericUtils.take_screenshot();
			}
		}
	}
	
	@When("Clicked on {string} button")
	public void clicked_on_button(String button_name) {
		refGenericUtils.waitUntilPageLoads();
		By by_button1=By.xpath("//button[@title='"+button_name+"']");
		By by_button2=By.xpath("//button[contains(@class, '"+button_name+"')]");
		By by_button=null;
		if(refGenericUtils.findElementsCount(by_button1, button_name)==1) {
			by_button=by_button1;
		}else if(refGenericUtils.findElementsCount(by_button2, button_name)==1) {
			by_button=by_button2;
		}
		refGenericUtils.take_screenshot();
		refGenericUtils.click_using_javaScript(by_button, button_name+" Button");
 		refGenericUtils.waitUntilPageLoads();
	}
	
	@Then("Case should be created successfully")
	public void case_should_be_created_successfully() {
		refGenericUtils.waitForElement(objectRepository.get("CaseDetailPage.CaseNumber"), 10, "Case Number");
		case_number = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("CaseDetailPage.CaseNumber"), "Case Number");
 		int case_number_count = refGenericUtils.findElementsCount(objectRepository.get("CaseDetailPage.CaseNumber"), "Case Number");
 		if(case_number_count==1) {
 			BaseUtil.scenario.log("Case "+"\'"+case_number+"\'"+" created successfully");
			refGenericUtils.take_screenshot();
 		}
 		else {
 			BaseUtil.scenario.log("Failed to create the new Case");
 			Assert.fail("Failed to create the new Case");
			refGenericUtils.take_screenshot();
 		}
	}
	
	@And("User navigates to {string} tab")
	public void user_navigates_to_tab(String tab_name) {
		refGenericUtils.click_Fromlist_of_Textvalues(objectRepository.get("CaseDetailPage.Tabs"), tab_name, tab_name+" Tab");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
	}
	
	@Then("User adds a New Request Members")
	public void user_adds_a_New_Request_Members(DataTable dataTable) {
		enter_values_updated(dataTable);
		refGenericUtils.stop_script_for(2000);
		refGenericUtils.click_using_javaScript(objectRepository.get("CaseDetailPage.Next.Button"), "Next Button");
		refGenericUtils.waitUntilPageLoads();
	}

	@Then("an error message {string} should be displayed")
	public void an_error_message_should_be_displayed(String error_message) {
		validate_error_message(error_message);
	}
	
	@Then("{string} checkbox must be {string}")
	public void checkbox_must_be(String checkbox_label, String enabled_or_disabled) {
		refGenericUtils.stop_script_for(4000);
		By by_checkbox = By.xpath("//span[text()='"+AccountName+"']/ancestor::div[@class='record-page-decorator']//span[text()='"+checkbox_label+"']/ancestor::lightning-input//input[@type='checkbox']");
		By by_details_tab = By.xpath("//span[text()='"+AccountName+"']/ancestor::div[@class='record-page-decorator']//a[@data-tab-value='detailTab']");
		refGenericUtils.scrollToViewElement(by_details_tab, "Details Tab");
		boolean if_enabled = refGenericUtils.checkbox_if_selected(by_checkbox, checkbox_label+" checkbox");
		if(enabled_or_disabled.equalsIgnoreCase("enabled") && if_enabled==true) {
			BaseUtil.scenario.log(checkbox_label+" checkbox is enabled as expected");
			refGenericUtils.take_screenshot();
		}else if(enabled_or_disabled.equalsIgnoreCase("disabled") && if_enabled==false) {
			BaseUtil.scenario.log(checkbox_label+" checkbox is disabled as expected");
			refGenericUtils.take_screenshot();
		}else {
			Assert.fail("Failed to approve the New account");
			refGenericUtils.take_screenshot();
		}
	}
	
	@Then("User should be able to approve the Case")
	public void user_should_be_able_to_approve_the_Case(DataTable dataTable) {
		refGenericUtils.waitForElement(objectRepository.get("CaseDetailPage.MarkStatus.Button"), 10, "Mark Status Button");
		refGenericUtils.click_using_javaScript(objectRepository.get("CaseDetailPage.MarkStatus.Button"), "Mark Status Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.click_using_javaScript(objectRepository.get("CaseDetailPage.MarkStatus.Button"), "Mark Status Button");
		refGenericUtils.waitForElement(objectRepository.get("CloseCasePopup.Header"), 10, "Close Case Popup Header");
		enter_values_updated(dataTable);
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("NewcasePopup.Save.Button"), "Save Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.waitForElement(objectRepository.get("CaseDetailPage.Status"), 10, "Status");
		String actual_status = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("CaseDetailPage.Status"), "Status");
		if(actual_status.contains("Approved")) {
			BaseUtil.scenario.log("Case "+"\'"+case_number+"\'"+" has been approved successfully");
			refGenericUtils.take_screenshot();
		}else {
			BaseUtil.scenario.log("Failed to approve the Case");
 			Assert.fail("Failed to approve the Case "+"\'"+case_number+"\'");
			refGenericUtils.take_screenshot();
		}
	}
	
	public void setup_page_search_textbox(String text_value, String textBox_element_name) {
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.toEnterTextValue(objectRepository.get(textBox_element_name), text_value, textBox_element_name);
		refGenericUtils.waitForElement(objectRepository.get("SetupPage.GlobalSearch.Option"), 10, text_value+" Option");
		refGenericUtils.clickOnElement(objectRepository.get("SetupPage.GlobalSearch.Option"), text_value+" Option");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();		
	}
	
	public void globalSearch(String objectName,String searchText) {
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.clickOnElement(objectRepository.get("UserHomePage.GlobalSearch.TextBox"), "UserHomePage.GlobalSearch.TextBox");
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.GlobalSearch.SearchType"), "HomePage.GlobalSearch.SearchType");
		refGenericUtils.ClearTextBox(objectRepository.get("HomePage.GlobalSearch.SearchType"), "HomePage.GlobalSearch.SearchType");
		refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.GlobalSearch.SearchType"), objectName, "HomePage.GlobalSearch.SearchType");
		refGenericUtils.stop_script_for(2000);
		refGenericUtils.keyboard_action(objectRepository.get("HomePage.GlobalSearch.SearchType"), "Enter");
		refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.GlobalSearch.TextBox"), searchText, "GlobalSearch");
		refGenericUtils.stop_script_for(2000);
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.firstSearchResult"), "HomePage.firstSearchResult");
	}
	
	public void switch_to_profile_frame(String profile_name) {
		By by_frame_name = By.xpath("//iframe[contains(@title,'"+profile_name+"')]");
		refGenericUtils.switchingFrame(by_frame_name, profile_name);
	}
	
	public void enter_values_2(DataTable dataTable) {
		List<Map<String, String>> map_of_feature_file_info = dataTable.asMaps();
		Map<String,String> map_of_account_info = new LinkedHashMap<String, String>();
		Map<String,String> account_info = new LinkedHashMap<String, String>();
		for(int i=0;i<map_of_feature_file_info.size();i++) {
			map_of_account_info = map_of_feature_file_info.get(i);
			String label = map_of_account_info.get("Element Name");
			String value = map_of_account_info.get("Values");
			account_info.put(label, value);
		}
	}
	
	public void search_using_waffle(String item_name) {
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.waitForElement(objectRepository.get("HomePage.Waffle"), 10, "App Launcher");
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.Waffle"), "App Launcher");
		refGenericUtils.waitForElement(objectRepository.get("HomePage.SearchAppsItems.TextBox"), 10, "Search Apps & Items textBox");
		refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.SearchAppsItems.TextBox"), item_name, "Search Apps Items TextBox");
		refGenericUtils.stop_script_for(2000);
		By by_item_name = By.xpath("//a[@data-label='"+item_name+"']");
		refGenericUtils.clickUsingActions(by_item_name, item_name);
		refGenericUtils.waitUntilPageLoads();
	}
	
	public void navigate_to_profile(String approver_name) {
		refGenericUtils.stop_script_for(10000);
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.GearIcon"), "Gear Icon");
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.GearIcon.SetupOption"), "Setup Option");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.switchingTabs(DriverFactory.getDriver().getWindowHandle(), DriverFactory.getDriver().getWindowHandles());
		refGenericUtils.waitForElement(objectRepository.get("SetupPage.GlobalSearch.TextBox"), 5, "Global Search TextBox");
		setup_page_search_textbox(approver_name, "SetupPage.GlobalSearch.TextBox");
		switch_to_profile_frame(approver_name);
		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("SetupPage.Login.Button"), "Login Button");
		refGenericUtils.waitUntilPageLoads();
	}
	
	public void enter_values_updated(DataTable dataTable) {
		Map<String,String> account_info = feature_file_data(dataTable);
		account_info.forEach((label, value) -> {
			if(value.equalsIgnoreCase("{AccountName}")) { 
				value=AccountName;
			}
			if(label.endsWith("AccountName")) {
				account_name_text = value.replace("{TimeStamp}", refGenericUtils.get_Date("MMMdd'_'HHmm"));
				refGenericUtils.waitForElement(objectRepository.get(label), 5, label);
				refGenericUtils.scrollToViewElement(objectRepository.get(label), label);
				refGenericUtils.toEnterTextValue(objectRepository.get(label), account_name_text, label);
			}
			else if(label.endsWith("TextBox")) {
				label=label.replace(".TextBox", "");
				By textBox=null;
				By textBox1=By.xpath("//label[text()='"+label+"']/ancestor::lightning-input//input");
				By textBox2=By.xpath("//*[text()='"+label+"']/ancestor::div[@class='slds-m-bottom_x-small']//input");
				By textBox3=By.xpath("//span[text()='"+label+"']/../..//input[@type='text']");
				By textBox4=By.xpath("//span[text()='"+label+"']/../..//textarea[@role='textbox']");
				if(refGenericUtils.findElementsCount(textBox1,label)==1)
					textBox=textBox1;
				else if(refGenericUtils.findElementsCount(textBox2,label)==1)
					textBox=textBox2;
				else if(refGenericUtils.findElementsCount(textBox3,label)==1)
					textBox=textBox3;
				else if(refGenericUtils.findElementsCount(textBox4,label)==1)
					textBox=textBox4;
				refGenericUtils.waitForElement(textBox, 10, label);
				refGenericUtils.scrollToViewElement(textBox, label);
				refGenericUtils.ClearTextBox(textBox, label);
				refGenericUtils.toEnterTextValue(textBox, value, label);
			}
			else if(label.endsWith("SelectDropdown")) {
				label=label.replace(".SelectDropdown","");
				 By dropDownXpath = null;
				 By dropDownXpath1 = By.xpath("//*[text()='"+label+"']/ancestor::div[contains(@class, 'slds-form-element')]//Select");
				 By dropDownXpath2 = By.xpath("//*[text()='"+label+"']/ancestor::div[@class='slds-m-bottom_x-small']//select");
				 By dropDownXpath3 = By.xpath("//*[text()='"+label+"']/ancestor::div[contains(@class, 'select')]//Select");
				 if(refGenericUtils.findElementsCount(dropDownXpath1,label)==1) {
					 dropDownXpath=dropDownXpath1;
					 refGenericUtils.waitForElement(dropDownXpath, 5, label);
					 refGenericUtils.scrollToViewElement(dropDownXpath, label);
					 refGenericUtils.select_dropdown_value(dropDownXpath, value, label);
					 refGenericUtils.waitUntilPageLoads();
				 }
				 else if(refGenericUtils.findElementsCount(dropDownXpath3,label)==1) {
					 dropDownXpath=dropDownXpath3;
					 refGenericUtils.waitForElement(dropDownXpath, 5, label);
					 refGenericUtils.scrollToViewElement(dropDownXpath, label);
					 refGenericUtils.select_dropdown_value(dropDownXpath, value, label);
					 refGenericUtils.waitUntilPageLoads();
				 }
				 else if(refGenericUtils.findElementsCount(dropDownXpath2,label)==1) {
					 dropDownXpath=dropDownXpath2;
					 refGenericUtils.waitForElement(dropDownXpath, 5, label);
					 refGenericUtils.scrollToViewElement(dropDownXpath, label);
					 refGenericUtils.click_using_javaScript(dropDownXpath, label);
					 refGenericUtils.toEnterTextValue(dropDownXpath, value, label);
					 refGenericUtils.waitUntilPageLoads();
				 }
			}
			else if(label.endsWith("SingleInputDropdown")) {
				label=label.replace(".SingleInputDropdown","");
				By dropDownXpath=null; By valueXpath=null;
				By dropDownXpath1=By.xpath("//label[text()='"+label+"']/..//input");
				By valueXpath1=By.xpath("//label[text()='"+label+"']/..//ul/li");
				By dropDownXpath2=By.xpath("//span[text()='"+label+"']/../..//a[@class='select']");
				By valueXpath2=By.xpath("//div[@class='select-options']//li//a");
				if((refGenericUtils.findElementsCount(dropDownXpath1,label)==1) && (refGenericUtils.findElementsCount(valueXpath1,value)>0)) {
					dropDownXpath=dropDownXpath1;
					valueXpath=valueXpath1;
					refGenericUtils.waitForElement(dropDownXpath, 5, label);
					refGenericUtils.scrollToViewElement(dropDownXpath, label);
					refGenericUtils.click_using_javaScript(dropDownXpath, label);
					refGenericUtils.click_Fromlist_of_Textvalues(valueXpath,value, label+" : "+ value);
				}
				else if(refGenericUtils.findElementsCount(dropDownXpath2,label)==1) {
					dropDownXpath=dropDownXpath2;
					valueXpath=valueXpath2;
					refGenericUtils.waitForElement(dropDownXpath, 5, label);
					refGenericUtils.scrollToViewElement(dropDownXpath, label);
					refGenericUtils.click_using_javaScript(dropDownXpath, label);
					refGenericUtils.click_Fromlist_of_Textvalues(valueXpath,value, label+" : "+ value);
				}else if(refGenericUtils.findElementsCount(dropDownXpath1,label)==1) {
					dropDownXpath=dropDownXpath1;
					valueXpath=By.xpath("//*[text()='"+value+"']");
					refGenericUtils.waitForElement(dropDownXpath, 5, label);
					refGenericUtils.scrollToViewElement(dropDownXpath, label);
					refGenericUtils.click_using_javaScript(dropDownXpath, label);
					refGenericUtils.click_using_javaScript(valueXpath, value);
				}
				refGenericUtils.waitUntilPageLoads();
			}
			else if(label.endsWith("DuellistBox")) {
				label = label.replace(".DuellistBox","");
				By by_listBox_value = By.xpath("//*[text()='"+label+"']/..//span[@title='"+value+"']/ancestor::li");
				refGenericUtils.clickOnElement(by_listBox_value, value);
				refGenericUtils.waitUntilPageLoads();
				label = label.replace("Available ","");
				By valueXpath = By.xpath("//button[@title='Move selection to Selected "+label+"']");
				refGenericUtils.clickOnElement(valueXpath, "Move Selection Right Button");
				refGenericUtils.waitUntilPageLoads();
			}else if(label.endsWith("Date")) {
				label = label.replace(".Date","");
				By dateXpath=null;
				By dateXpath1 = By.xpath("//*[text()='"+label+"']/ancestor::div[@class='slds-m-bottom_x-small']//input");
				By dateXpath2 = By.xpath("//*[text()='"+label+"']/../..//div[@class='form-element']//input");
				By dateXpath3= By.xpath("//*[text()='"+label+"']/..//input");
				if(refGenericUtils.findElementsCount(dateXpath1,label)==1)
					dateXpath=dateXpath1;
				else if(refGenericUtils.findElementsCount(dateXpath2,label)==1)
					dateXpath=dateXpath2;
				else if(refGenericUtils.findElementsCount(dateXpath3,label)==1)
					dateXpath=dateXpath3;
				refGenericUtils.click_using_javaScript(dateXpath, label);
				refGenericUtils.toEnterTextValue(dateXpath, value, label);
			}else if(label.endsWith("SearchBox")) {
				label=label.replace(".SearchBox", "");
				By textBox=null; By valueXpath=null;
				By textBox1=By.xpath("//span[text()='"+label+"']/../..//input[@type='text']");
				By textBox2=By.xpath("//label[text()='"+label+"']/../..//input[@type='text']");
				By valueXpath1=By.xpath("//span[text()='"+label+"']/../..//div[contains(@title, '"+value+"')]//mark");
				By valueXpath2=By.xpath("//div[@class='slds-lookup__menu']//div[text()='"+value+"']");
				if((refGenericUtils.findElementsCount(textBox1,label)==1)||(refGenericUtils.findElementsCount(valueXpath1,value)==1)) {
					textBox=textBox1;
					valueXpath=valueXpath1;
				}else if((refGenericUtils.findElementsCount(textBox2,label)==1)||(refGenericUtils.findElementsCount(valueXpath2,value)==1)) {
					textBox=textBox2;
					valueXpath=valueXpath2;
				}
				refGenericUtils.waitForElement(textBox, 5, label);
				refGenericUtils.toEnterTextValue(textBox, value, label);
				refGenericUtils.click_Fromlist_of_Textvalues(valueXpath,value, label+" : "+ value);
			}else if(label.endsWith("Checkbox")) {
				label=label.replace(".Checkbox", "");
				By checkbox=null;
				By checkbox1=By.xpath("//span[text()='"+label+"']/../..//input[@type='checkbox']");
				if((refGenericUtils.findElementsCount(checkbox1,label)==1) && value.equalsIgnoreCase("Y")){
					checkbox=checkbox1;
					refGenericUtils.clickOnElement(checkbox, label+" Checkbox");
				}
			}
		});
	}

	public void account_assignment_data(DataTable dataTable, Map<String, Integer> map_of_cols) {
		Map<String,String> data = feature_file_data(dataTable);
		data.forEach((label, value) -> {
			String[] arr_label = label.split("\\.");
			String row = arr_label[0].trim(); String coloumn_name_feat = arr_label[1].trim();
			int position=0;
			if((label.endsWith("TextBox"))) {
				position = map_of_cols.get(coloumn_name_feat);
				By by_textBox = By.xpath("//table[@id='thePage:pageForm:initSalesTeamSplitTeamTable:pageBlockTable']/tbody/tr["+row+"]/td["+position+"]//input[@type='text']");
				refGenericUtils.waitForElement(by_textBox, 5, coloumn_name_feat);
				refGenericUtils.ClearTextBox(by_textBox, coloumn_name_feat);
				refGenericUtils.toEnterTextValue(by_textBox, value, coloumn_name_feat);
			}
			else if((label.endsWith("SearchBox"))) {
				position = map_of_cols.get(coloumn_name_feat);
				By by_SearchBox = By.xpath("//table[@id='thePage:pageForm:initSalesTeamSplitTeamTable:pageBlockTable']/tbody/tr["+row+"]/td["+position+"]//span[@class='lookupInput']//input[@type='text']");
				refGenericUtils.waitForElement(by_SearchBox, 5, coloumn_name_feat);
				refGenericUtils.toEnterTextValue(by_SearchBox, value, coloumn_name_feat);
			}
			else if((label.endsWith("CheckBox"))) {
				position = map_of_cols.get(coloumn_name_feat);
				By by_checkBox= By.xpath("//table[@id='thePage:pageForm:initSalesTeamSplitTeamTable:pageBlockTable']/tbody/tr["+row+"]/td["+position+"]//input[@type='checkbox']");
				refGenericUtils.waitForElement(by_checkBox, 5, coloumn_name_feat);
				if(value.equals("Y"))
					refGenericUtils.click_using_javaScript(by_checkBox, coloumn_name_feat);
			}
			else if(label.endsWith("Select")) {
				position = map_of_cols.get(coloumn_name_feat);
				By by_select= By.xpath("//table[@id='thePage:pageForm:initSalesTeamSplitTeamTable:pageBlockTable']/tbody/tr["+row+"]/td["+position+"]//select");
				refGenericUtils.select_dropdown_value(by_select, value, coloumn_name_feat);
				refGenericUtils.waitUntilPageLoads();
			}
		});
	}
	
	public Map<String,String> feature_file_data(DataTable dataTable){
		Map<String,String> map_of_feature_values = new LinkedHashMap<String, String>();
		List<Map<String, String>> map_of_feature_file_info = dataTable.asMaps();
		Map<String,String> map_of_account_info = new LinkedHashMap<String, String>();
		for(int i=0;i<map_of_feature_file_info.size();i++) {
			map_of_account_info = map_of_feature_file_info.get(i);
			String label = map_of_account_info.get("Element Name");
			String value = map_of_account_info.get("Values");
			map_of_feature_values.put(label, value);
		}
		return map_of_feature_values;
	}
	
	public void switch_to_frame(int frame_num) {
		By by_xpath = By.xpath("(//iframe[@title='accessibility title'])["+frame_num+"]");
		refGenericUtils.waitForElement(by_xpath, 10, "Account Assignments Frame");
		refGenericUtils.switchingFrame(by_xpath, "Account Assignments Frame");
	}
	
	public void validate_error_message(String error_message) {
		By by_error1 = By.xpath("//div[@class='pageLevelErrors']//li[contains(text(), '"+error_message+"')]");
		By by_error2 = By.xpath("//div[@class='slds-m-bottom_x-small']//span[contains(text(), '"+error_message+"')]");
		By by_error3 = By.xpath("//div[@data-key='error']//span[contains(@class, 'toastMessage')]");
		By by_error=null;
		if(refGenericUtils.findElementsCount(by_error1, "Error Message")==1) {
			by_error=by_error1;
		}else if(refGenericUtils.findElementsCount(by_error2, "Error Message")==1) {
			by_error=by_error2;
		}else if(refGenericUtils.findElementsCount(by_error3, "Error Message")==1) {
			by_error=by_error3;
		}
		if(refGenericUtils.fetchingTextvalueofElement(by_error, error_message).contains(error_message)) {
			BaseUtil.scenario.log("\'"+error_message+"\'"+" error is displayed as expected");
			refGenericUtils.take_screenshot();
		}else {
			BaseUtil.scenario.log("Failed to display the error "+"\'"+error_message+"\'");
 			Assert.fail("Failed to display the error "+"\'"+error_message+"\'");
			refGenericUtils.take_screenshot();
		}
	}
}
