package stepDefs;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

	//Abhishek declared
	public static WebDriver driver = DriverFactory.getDriver();
	
	public GenericUtils refGenericUtils = new GenericUtils(DriverFactory.getDriver());
	public LoginPage loginPage = new LoginPage(DriverFactory.getDriver(), envDetails, objectRepository, usernumber);

	
	@Given("Admin has already logged into the application")
	public void admin_has_already_logged_into_the_application() {
		loginPage.loginToApplication();
	}
	
	@When("User creates new account for {string} Record type")
	public void user_creates_new_account_for_record_type(String record_type, DataTable dataTable) throws InterruptedException {
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
//		refGenericUtils.take_screenshot();
		refGenericUtils.clickOnElement(objectRepository.get("NewAccount.Create.Button"), "Save Button");
		Thread.sleep(2000);

		/*Toast Notification has a bug in Account Creation functionality in Salesforce, Need to build it after its fixed
		refGenericUtils.waitForElement(objectRepository.get("AccountCreated.Notification"), 10, "Account Created Notification");*/


		String actual_account = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("AccountPage.AccountNumber"), "Account Page Account Number");
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
	
	@Then("user deletes the {string} record type")
	public void user_deletes_the_record_type(String record_type) {
		String account_name = AccountName;
		globalSearch("Accounts", account_name);
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.Cases.Link"), "Cases Link");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.First.Case.Checkbox"), "First Case Checkbox");
		refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.First.Case.DropdownButton"), "First Case Dropdown Button");
		refGenericUtils.waitForElement(objectRepository.get("AccountPage.First.Case.Dropdown.Delete"), 5, "First Case Dropdown Delete");
		refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.First.Case.Dropdown.Delete"), "First Case Dropdown Delete");
		refGenericUtils.waitForElement(objectRepository.get("AccountPage.Popup.Delete.Button"), 5, "Popup Delete");
		refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.Popup.Delete.Button"), "Popup Delete");
		refGenericUtils.waitUntilPageLoads();
		int count1 = refGenericUtils.findElementsCount(objectRepository.get("AccountPage.First.Case.Checkbox"), "First Case Checkbox");
		if(count1==0) {
			BaseUtil.scenario.log("Case has been deleted successfully");
			refGenericUtils.take_screenshot();
			refGenericUtils.click_using_javaScript(objectRepository.get("HomePage.AccountsTab"), "Accounts Tab");
			refGenericUtils.waitUntilPageLoads();
			globalSearch("Accounts", account_name);
			refGenericUtils.waitUntilPageLoads();
			if(record_type.equals("Advertiser")) {
				refGenericUtils.waitForElement(objectRepository.get("AccountPage.ShowMoreActions.Button"), 5, "Show More Actions Button");
				refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.ShowMoreActions.Button"), "Show More Actions Button");
				refGenericUtils.waitForElement(objectRepository.get("AccountPage.ShowMoreActions.Dropdown.Delete"), 5, "Show More Actions Dropdown Delete");
				refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.ShowMoreActions.Dropdown.Delete"), "Show More Actions Dropdown Delete");
				
			}
			else {
				refGenericUtils.waitForElement(objectRepository.get("AccountPage.Delete.Button"), 5, "Delete Button");
				refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.Delete.Button"), "Delete Button");
			}
			refGenericUtils.waitForElement(objectRepository.get("AccountPage.Popup.Delete.Button"), 5, "Popup Delete");
			refGenericUtils.click_using_javaScript(objectRepository.get("AccountPage.Popup.Delete.Button"), "Popup Delete");
			refGenericUtils.waitUntilPageLoads();
			refGenericUtils.take_screenshot();
//			-----Global Search------
			refGenericUtils.refreshBrowser();
			refGenericUtils.waitUntilPageLoads();
			refGenericUtils.stop_script_for(5000);
			refGenericUtils.clickOnElement(objectRepository.get("UserHomePage.GlobalSearch.TextBox"), "UserHomePage.GlobalSearch.TextBox");
			refGenericUtils.stop_script_for(2000);
			refGenericUtils.clickOnElement(objectRepository.get("HomePage.GlobalSearch.SearchType"), "HomePage.GlobalSearch.SearchType");
			//refGenericUtils.ClearTextBox(objectRepository.get("HomePage.GlobalSearch.SearchType"), "HomePage.GlobalSearch.SearchType");
			//refGenericUtils.click_using_javaScript(objectRepository.get("HomePage.GlobalSearch.SearchType"), "GlobalSearch.SearchType");
			By searchType=By.xpath("(//div[@class='slds-grid slds-p-top--x-small slds-p-horizontal--x-small slds-size--1-of-1 slds-combobox-group header']//*[text()='Accounts'])[1]");
			refGenericUtils.click_using_javaScript(searchType, "GlobalSearch.SearchType");
			refGenericUtils.stop_script_for(2000);
			refGenericUtils.sendKeysJS(objectRepository.get("HomePage.GlobalSearch.SearchType"), "Accounts");
			//refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.GlobalSearch.SearchType"), objectName, "HomePage.GlobalSearch.SearchType");
			//refGenericUtils.keyboard_action(objectRepository.get("HomePage.GlobalSearch.SearchType"), "Enter");
			refGenericUtils.click_using_javaScript(searchType, "searchType");
			refGenericUtils.sendKeysJS(objectRepository.get("HomePage.GlobalSearch.TextBox"), account_name);
			//refGenericUtils.click_using_javaScript(objectRepository.get("HomePage.GlobalSearch.TextBox"), "HomePage.GlobalSearch.TextBox");
			//refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.GlobalSearch.TextBox"), searchText, "GlobalSearch");
			refGenericUtils.stop_script_for(2000);
			By by_search_account = By.xpath("//span[@title='"+account_name+"']");
			int count2 = refGenericUtils.findElementsCount(by_search_account, "Search Account");
			if(count2==0) {
				BaseUtil.scenario.log("Account "+"\'"+account_name+"\'"+" has been deleted successfully");
				refGenericUtils.take_screenshot();
			}
			else {
				Assert.fail("Failed to delete the account");
				refGenericUtils.take_screenshot();
			}
		}
		else {
			Assert.fail("Failed to delete the Case");
			refGenericUtils.take_screenshot();
		}
	}
	
	@When("{string} approves the account")
	public void approves_the_account(String approver_name) throws InterruptedException {
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
			BaseUtil.scenario.log("Account "+"\'"+account_name_text+"\'"+" has been approved successfully");
			refGenericUtils.take_screenshot();
		}
		else {
			Assert.fail("Failed to approve the New account");
			refGenericUtils.take_screenshot();
		}
		refGenericUtils.waitForElement(objectRepository.get("AccountPage.Logout"), 5, "Profile Logout");
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.Logout"), "Profile Logout");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.closeCurrentTab();////a[contains(text(),'Log out as')]
		loginPage.loginToApplication();
	}
	
	@When("{string} tries to {string} an account")
	public void tries_to_an_account(String approver_name, String decision) throws InterruptedException {
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
		refGenericUtils.stop_script_for(1000);
		refGenericUtils.clickUsingActions(objectRepository.get("AccountPage.RejectedReasonDropdown"), "AccountPage.RejectedReasonDropdown");
		refGenericUtils.waitUntilPageLoads();
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
	public void user_creates_a_pipeline(DataTable dataTable) throws InterruptedException {
		String account_name = AccountName;
		refGenericUtils.waitUntilPageLoads();
		globalSearch("Accounts", account_name);
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.clickOnElement(objectRepository.get("AccountPage.CreateNewPipeline.Button"), "Create New Pipeline Button");
		refGenericUtils.waitForElement(objectRepository.get("AccountPage.NewPipeline.Popup"), 10, "New Pipeline Popup");
		refGenericUtils.stop_script_for(3000);
		refGenericUtils.waitForElement(objectRepository.get("NewPipelinePopup.Year.TextBox"), 10, "New Pipeline Popup");
		refGenericUtils.ClearTextBox(objectRepository.get("NewPipelinePopup.Year.TextBox"), "Year TextBox");
		enter_values_updated(dataTable);
		refGenericUtils.take_screenshot();
		refGenericUtils.click_using_javaScript(objectRepository.get("NewPipelinePopup.Save.Button"), "NewPipelinePopup.Save.Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
	}
	
	@When("user clones a {string} Opportunity")
	public void user_clones_a_opportunity(String opportunity_type, DataTable dataTable) throws InterruptedException {
		String Pipeline = AccountName;
		String Opportunity = OppId.replaceAll("\\(.*?\\)","").trim();
		loginPage.loginToApplication();
		globalSearch("Pipeline", Pipeline);
		refGenericUtils.take_screenshot();
		By tabName = null;
		if(opportunity_type.equalsIgnoreCase("Print")) {
			 tabName=By.xpath("//a[@data-tab-value='Print']");
		} else if(opportunity_type.equalsIgnoreCase("Digital")) {
			 tabName=By.xpath("//a[@data-tab-value='Digital']");
		}else if(opportunity_type.equalsIgnoreCase("F360")) {
			 tabName=By.xpath("//a[@data-tab-value='F360']");
		}
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.waitForElement(tabName, 50, "tabName selection");
		refGenericUtils.click_using_javaScript(tabName, "Opportunity.Tab name");
		By by_opp_number=null;
		if(opportunity_type.equalsIgnoreCase("Print")||opportunity_type.equalsIgnoreCase("F360")) {
			by_opp_number = By.xpath("//table//p[contains(text(), '"+Opportunity+"')]/..");
		}else if(opportunity_type.equalsIgnoreCase("Digital")) {
			by_opp_number = By.xpath("//table//a[contains(text(), '"+Opportunity+"')]");
		}
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
		String opp_number="";
		if(opportunity_type.equalsIgnoreCase("Print")) {
			String issue = refGenericUtils.fetch_attribute_value(objectRepository.get("ClonePopup.SelectedIssue.DuellistBox"), "title", "Selected Issue Duel listBox");
			refGenericUtils.take_screenshot();
			refGenericUtils.click_using_javaScript(objectRepository.get("ClonePopup.Save.Button"), "Save Button");
			refGenericUtils.waitUntilPageLoads();
			refGenericUtils.take_screenshot();
			refGenericUtils.stop_script_for(5000);
			opp_number = refGenericUtils.get_cloned_opportunity(issue);
		}
		else if(opportunity_type.equalsIgnoreCase("Digital")) {
			Map<String, String> map_of_data = enter_values_2(dataTable);
			String clone_opp_name = map_of_data.get("Opportunity Name.TextBox");
//			refGenericUtils.click_using_javaScript(objectRepository.get("ClonePopup.Save.Button"), "Save Button");
			refGenericUtils.waitUntilPageLoads();
			refGenericUtils.take_screenshot();
			refGenericUtils.stop_script_for(5000);
			By by_opp_name = By.xpath("//div[contains(text(),'"+clone_opp_name+"')]/../..//a");
			opp_number = refGenericUtils.fetchingTextvalueofElement(by_opp_name, "Opportunity "+clone_opp_name);
		}
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
	public void user_creates_a_new_Account_Assignment_Request(DataTable dataTable) throws InterruptedException {
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
	public void user_adds_a_New_Request_Members(DataTable dataTable) throws InterruptedException {
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
	public void user_should_be_able_to_approve_the_Case(DataTable dataTable) throws InterruptedException {
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
	
	@And("navigate to profile {string}")
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
	
	@And("{string} tries to create a Pipeline")
	public void tries_to_create_a_Pipeline(String profile_name) throws InterruptedException {
		String account_name = AccountName;
		refGenericUtils.waitUntilPageLoads();
		globalSearch("Accounts", account_name);
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.clickOnElement(objectRepository.get("AccountPage.CreateNewPipeline.Button"), "Create New Pipeline Button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
	}
	
	public void setup_page_search_textbox(String text_value, String textBox_element_name) {
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.toEnterTextValue(objectRepository.get(textBox_element_name), text_value, textBox_element_name);
		refGenericUtils.waitForElement(objectRepository.get("SetupPage.GlobalSearch.Option"), 10, text_value+" Option");
		refGenericUtils.clickOnElement(objectRepository.get("SetupPage.GlobalSearch.Option"), text_value+" Option");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();		
	}


	@When("user searches object name as {string} and record name as {string} and lands on record")
	public void globalSearch(String objectName, String searchText){
//		refGenericUtils.refreshBrowser();
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.clickOnElement(objectRepository.get("UserHomePage.GlobalSearch.TextBox"), "UserHomePage.GlobalSearch.TextBox");
		refGenericUtils.stop_script_for(2000);
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.GlobalSearch.SearchType"), "HomePage.GlobalSearch.SearchType");
		//refGenericUtils.ClearTextBox(objectRepository.get("HomePage.GlobalSearch.SearchType"), "HomePage.GlobalSearch.SearchType");
		//refGenericUtils.click_using_javaScript(objectRepository.get("HomePage.GlobalSearch.SearchType"), "GlobalSearch.SearchType");
		By searchType=By.xpath("(//div[@class='slds-grid slds-p-top--x-small slds-p-horizontal--x-small slds-size--1-of-1 slds-combobox-group header']//*[text()='"+objectName+"'])[1]");
		refGenericUtils.click_using_javaScript(searchType, "GlobalSearch.SearchType");
		refGenericUtils.stop_script_for(2000);
		refGenericUtils.sendKeysJS(objectRepository.get("HomePage.GlobalSearch.SearchType"), objectName);
		//refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.GlobalSearch.SearchType"), objectName, "HomePage.GlobalSearch.SearchType");
		//refGenericUtils.keyboard_action(objectRepository.get("HomePage.GlobalSearch.SearchType"), "Enter");
		refGenericUtils.click_using_javaScript(searchType, "searchType");
		refGenericUtils.sendKeysJS(objectRepository.get("HomePage.GlobalSearch.TextBox"), searchText);
		//refGenericUtils.click_using_javaScript(objectRepository.get("HomePage.GlobalSearch.TextBox"), "HomePage.GlobalSearch.TextBox");
		//refGenericUtils.toEnterTextValue(objectRepository.get("HomePage.GlobalSearch.TextBox"), searchText, "GlobalSearch");
		refGenericUtils.stop_script_for(2000);
		refGenericUtils.waitForElement(objectRepository.get("HomePage.firstSearchResult"), 10, "First Search Result");
		refGenericUtils.clickOnElement(objectRepository.get("HomePage.firstSearchResult"), "HomePage.firstSearchResult");
		refGenericUtils.stop_script_for(2000);
	}
	
	public void switch_to_profile_frame(String profile_name) {
		By by_frame_name = By.xpath("//iframe[contains(@title,'"+profile_name+"')]");
		refGenericUtils.switchingFrame(by_frame_name, profile_name);
	}
	
	public Map<String,String> enter_values_2(DataTable dataTable) {
		List<Map<String, String>> map_of_feature_file_info = dataTable.asMaps();
		Map<String,String> map_of_account_info = new LinkedHashMap<String, String>();
		Map<String,String> account_info = new LinkedHashMap<String, String>();
		for(int i=0;i<map_of_feature_file_info.size();i++) {
			map_of_account_info = map_of_feature_file_info.get(i);
			String label = map_of_account_info.get("Element Name");
			String value = map_of_account_info.get("Values");
			account_info.put(label, value);
		}
		return account_info;
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
	
	public void enter_values_updated(DataTable dataTable) {
		refGenericUtils.stop_script_for(5000);
		Map<String,String> account_info = feature_file_data(dataTable);
		account_info.forEach((label, value) -> {
			if(value.equalsIgnoreCase("{AccountName}")) {
				value=AccountName;
			}
			if(label.endsWith("AccountName")) {
				account_name_text = value.replace("{TimeStamp}", refGenericUtils.get_Date("MMMdd'_'HHmm"));
				System.out.println("label: "+  label);
				refGenericUtils.waitForElement(objectRepository.get(label), 5, label);
				refGenericUtils.scrollToViewElement(objectRepository.get(label), label);
				refGenericUtils.toEnterTextValue(objectRepository.get(label), account_name_text, label);
			}
			else if(label.endsWith("TextBox")) {
				label=label.replace(".TextBox", "");
				By textBox=null;
				By textBox1=By.xpath("//label[text()='"+label+"']/ancestor::lightning-input//input");
				By textBox2=By.xpath("//*[text()='"+label+"']/ancestor::div[@class='slds-m-bottom_x-small']//input");
				By textBox3=By.xpath("//legend[text()='"+label+"']/../..//input[@type='text']");
				By textBox4=By.xpath("//span[text()='"+label+"']/../..//textarea[@role='textbox']");
				By textBox5 = By.xpath("//label[text()='"+label+"']/ancestor::lightning-textarea//textarea");


//				System.out.print("textBox5"+textBox5);

				if(refGenericUtils.findElementsCount(textBox1,label)==1)
					textBox=textBox1;
				else if(refGenericUtils.findElementsCount(textBox2,label)==1)
					textBox=textBox2;
				else if(refGenericUtils.findElementsCount(textBox3,label)==1)
					textBox=textBox3;
				else if(refGenericUtils.findElementsCount(textBox4,label)==1)
					textBox=textBox4;
				else if(refGenericUtils.findElementsCount(textBox5, label)==1){
					textBox=textBox5;

				}



				refGenericUtils.waitForElement(textBox, 20, label);
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
//				By valueXpath1=By.xpath("//label[text()='"+label+"']/..//ul/li");
				By valueXpath1 = By.xpath("//label[text()="+label+"]/..//lightning-base-combobox-item/span[2]");
				By dropDownXpath2=By.xpath("//span[text()='"+label+"']/../..//a[@class='select']");
				By valueXpath2=By.xpath("//div[@class='select-options']//li//a");

				By dropDwnXpath3 = By.xpath("//label[text()="+label+"]");
				By valueXpath3 = By.xpath("//label[text()="+label+"]/ancestor::lightning-picklist//lightning-base-combobox-item/span[2]");

				if((refGenericUtils.findElementsCount(dropDownXpath1,"input Single Dropdown" )==1)&&(refGenericUtils.findElementsCount(valueXpath1,"input values")>0)){
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
					valueXpath=By.xpath("//*[@title='"+value+"']");
					refGenericUtils.waitForElement(dropDownXpath, 5, label);
					refGenericUtils.scrollToViewElement(dropDownXpath, label);
					refGenericUtils.click_using_javaScript(dropDownXpath, label);
					refGenericUtils.waitForElement(valueXpath, 10, label);
					refGenericUtils.click_using_javaScript(valueXpath, value);
				} else if((refGenericUtils.findElementsCount(dropDownXpath2,"input Single Dropdown" )==1)&&(refGenericUtils.findElementsCount(valueXpath1,"input values")>0)){
					dropDownXpath=dropDwnXpath3;
					valueXpath=valueXpath3;
					refGenericUtils.waitForElement(dropDownXpath, 5, label);
					refGenericUtils.scrollToViewElement(dropDownXpath, label);
					refGenericUtils.click_using_javaScript(dropDownXpath, label);
					refGenericUtils.click_Fromlist_of_Textvalues(valueXpath,value, label+" : "+ value);
				}
				refGenericUtils.waitUntilPageLoads();
			}
			else if(label.endsWith("DuellistBox")) {
				label = label.replace(".DuellistBox","");
				By by_listBox_value = By.xpath("//*[text()='"+label+"']/..//span[@title='"+value+"']/ancestor::li");
				refGenericUtils.clickOnElement(by_listBox_value, value);
				refGenericUtils.waitUntilPageLoads();
				label = label.replace("Available ","");
				By valueXpath=null;
				if(label.equals("Contextual")) {
					valueXpath = By.xpath("//button[@title='Move selection to Chosen']");
				}else {
					valueXpath = By.xpath("//button[@title='Move selection to Selected "+label+"']");
				}
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
				refGenericUtils.keyboard_action(dateXpath, "Enter");
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




//	//Updates Opportunity Entries Dynamically
//	public void createOpportunityDynamically(DataTable datatable){
//		List<WebElement> Elements = null;
//		List<String> labels = null;
//
////		On Opp Creation first Page
////		* Collect all fields present on the Page
////		* Match the collected field labels with Datatable fields
////		* Shrink the Datatable with only matching field with keeping original one intact
////		* call "update_Opp_Entries(shrinkedDatatable)"
////		*
//		// Repeat the Same Process for Next Page, by checking the Account Assignment Validation Page
//
//		List<Map<String, String>> datatable_Map = datatable.asMaps(String.class, String.class);
//		List<Map<String, String>> finalDataMap = null;
//
//
//		By xpath1 = By.xpath("//flowruntime-screen-field//lightning-formatted-text/span");
//		By xpath2 = By.xpath("//flowruntime-screen-field//label/span");
//		By xpath3 = By.xpath("//flowruntime-screen-field//label");
//
//		List<WebElement> ele1 = refGenericUtils.findElementsCount(xpath1, "xpath1") >0? refGenericUtils.findElements(xpath1,"xpath1"): null;
//		List<WebElement> ele2 = refGenericUtils.findElementsCount(xpath2, "xpath2") >0? refGenericUtils.findElements(xpath2,"xpath2"): null;
//		List<WebElement> ele3 = refGenericUtils.findElementsCount(xpath3, "xpath3") >0? refGenericUtils.findElements(xpath3,"xpath1"): null;
//
//		ele1.addAll(ele2);
//		ele1.addAll(ele3);
//
//		Elements = ele1;
//
//		for(WebElement ele : Elements){
//			String extractedLabel = null;
//			if(ele!=null){
//				extractedLabel = refGenericUtils.fetchingTextvalueofElement(ele, "Element");
//			} else continue;
//
//			labels.add(extractedLabel);
//		}
//
//		//datatable_Map, [('Key':'Value'),(),()]
//		for(int i=0; i < datatable_Map.size(); i++){
//			String key = datatable_Map.get(i).keySet().toArray()[1].toString();
//			if(key.contains(labels.get(i))){
//				finalDataMap.add( datatable_Map.get(i));
//			}
//
//
//		}
//
////		DataTable finalDataMapTable = DataTable.create(finalDataMap);
//
//
//		refAccountCreation.update_Opp_entries(dataTable);
//		refGenericUtils.take_screenshot();
//		refGenericUtils.clickOnElement(objectRepository.get("CreateNewOpportunity.Next.Button"), "Opportunity Creation Next Button");
//		refGenericUtils.waitUntilPageLoads();
//		By accountAssignmentValidation = objectRepository.get("CreateNewOpportunity.AccountAssignmentValidation");
//
//		//if the "This account is not assigned to you? Do you wish to Continue" window appears then IF condition handles it
//		if(refGenericUtils.findElementsCount(accountAssignmentValidation, "Account Assignment validation")>0){
//
//			refGenericUtils.take_screenshot();
//			refGenericUtils.clickOnElement(objectRepository.get("CreateNewOpportunity.Next.Button"), "Next Button");
//		}
//
//		refGenericUtils.waitUntilPageLoads();
//		refAccountCreation.update_Opp_entries(dataTable);
//		refGenericUtils.take_screenshot();
//
//		//New Oportunity Create Button
//		refGenericUtils.waitForElement(objectRepository.get("CreateNewOpportunity.Create.Button"),10, "Opportunity Create Button");
//		refGenericUtils.click_using_javaScript(objectRepository.get("CreateNewOpportunity.Create.Button"), "Opportunity Create Button");
//
//	}
//


	@When("User clicks Next Button")
	public void user_clicks_Next_button(){
		refGenericUtils.take_screenshot();
		refGenericUtils.waitForElement(objectRepository.get("CreateNewOpportunity.Next.Button"), 10, "CreateNewOpportunity.Next.Button");
		refGenericUtils.click_using_javaScript(objectRepository.get("CreateNewOpportunity.Next.Button"), "CreateNewOpportunity.Next.Button");

	}


	//Updates entries of Opportunities only

	@When("User updates the Opportunity entries")
	public void update_Opp_entries(DataTable dataTable) {
		refGenericUtils.stop_script_for(5000);
		Map<String,String> account_info = feature_file_data(dataTable);
		System.out.println("account_info: " + account_info);
		//ex:- (OpportunityName.TextBox, "Test_Opp_TimeStamp")
		account_info.forEach((label, value) -> {

//			System.out.println(label + value);

			if(label.endsWith("TextBox")) {
				label=label.replace(".TextBox", "");
				By textBox=null;

				//Different combinations of possible textboxes
				By textBox1 = By.xpath("//lightning-formatted-rich-text//*[text()='"+label+"']/../../..//input");


//				System.out.print("textBox5"+textBox5);

				if(refGenericUtils.findElementsCount(textBox1,label)==1)
					textBox=textBox1;


				refGenericUtils.waitForElement(textBox, 20, label);
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

//
			
				label=label.replace(".SingleInputDropdown","");
				By dropDownXpath=null; By valueXpath=null;



				By dropDownXpath1 = By.xpath("//flowruntime-screen-field//label[text()='"+label+"']/..//button");
				By valueXpath1 = By.xpath("//flowruntime-screen-field//label[text()='"+label+"']/..//lightning-base-combobox-item/span");

				if((refGenericUtils.findElementsCount(dropDownXpath1,"Single Input Dropdown" )==1)&&(refGenericUtils.findElementsCount(valueXpath1,"input values")>0)){
//
					dropDownXpath=dropDownXpath1;
					valueXpath=valueXpath1;
					refGenericUtils.waitForElement(dropDownXpath, 5, label);
					refGenericUtils.scrollToViewElement(dropDownXpath, label);
					refGenericUtils.click_using_javaScript(dropDownXpath, label);
					refGenericUtils.click_Fromlist_of_Textvalues(valueXpath,value, label+" : "+ value);
				}else if((refGenericUtils.findElementsCount(dropDownXpath1,"Single Input Dropdown" )==1)){
//
					dropDownXpath=dropDownXpath1;
					valueXpath=valueXpath1;
					refGenericUtils.waitForElement(dropDownXpath, 5, label);
					refGenericUtils.scrollToViewElement(dropDownXpath, label);
					refGenericUtils.clickOnElement(dropDownXpath, "dropDownXpath");
					refGenericUtils.click_Fromlist_of_Textvalues(valueXpath,value, label+" : "+ value);
				}



				refGenericUtils.waitUntilPageLoads();
			}
			else if(label.endsWith("DuellistBox")) {
				label = label.replace(".DuellistBox","");
				By by_listBox_value = By.xpath("//*[text()='"+label+"']/..//span[@title='"+value+"']/ancestor::li");
				refGenericUtils.clickOnElement(by_listBox_value, value);
				refGenericUtils.waitUntilPageLoads();
				label = label.replace("Available ","");
				By valueXpath=null;
				if(label.equals("Contextual")) {
					valueXpath = By.xpath("//button[@title='Move selection to Chosen']");
				}else {
					valueXpath = By.xpath("//button[@title='Move selection to Selected "+label+"']");
				}
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
				refGenericUtils.keyboard_action(dateXpath, "Enter");
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
			else if (label.endsWith(".Lookup")){
				label = label.replace(".Lookup", "");

				By lookupFieldInputBox = null; By lookupFieldOptions = null;

				By lFIB1 = By.xpath("//label[text()='"+label+"']//ancestor::lightning-lookup//input");
				By lFO1 = By.xpath("//label[text()='"+label+"']//ancestor::lightning-lookup//ul/li/lightning-base-combobox-item");

				refGenericUtils.clickOnElement(lFIB1,"lFIB1");
				refGenericUtils.ClearTextBox(lFIB1,"lFIB1");
				driver.findElement(lFIB1).sendKeys(value);
				refGenericUtils.waitForVisibilty(lFO1, 5, "lookup field Option");
				refGenericUtils.click_using_javaScript(lFO1, "lookup field option");


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
		By by_error4 = By.xpath("//*[contains(text(), '"+error_message+"')]");
		By by_error=null;
		if(refGenericUtils.findElementsCount(by_error1, "Error Message")==1) {
			by_error=by_error1;
		}else if(refGenericUtils.findElementsCount(by_error2, "Error Message")==1) {
			by_error=by_error2;
		}else if(refGenericUtils.findElementsCount(by_error3, "Error Message")==1) {
			by_error=by_error3;
		}else if(refGenericUtils.findElementsCount(by_error4, "Error Message")==1) {
			by_error=by_error4;
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
