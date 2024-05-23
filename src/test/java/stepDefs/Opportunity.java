package stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.testng.Assert;

import base.BaseUtil;
import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.GenericUtils;

public class Opportunity extends BaseUtil {

	public static String account_name_text;
	public GenericUtils refGenericUtils = new GenericUtils(DriverFactory.getDriver());
	public AppGenericUtils refAccountCreation = new AppGenericUtils();
	public LoginPage loginPage = new LoginPage(DriverFactory.getDriver(), envDetails, objectRepository, usernumber);


	@When("User navigates to New Opportunity  for {string} type")
	public void user_navigates_to_new_Opportunity(String opportunity_type) {

		/*Since we are already present on Pipeline Page
		refAccountCreation.globalSearch("Pipeline", AccountName);//AccountName */

		refGenericUtils.waitUntilPageLoads();

		By tabName = null;
		By buttonName = null;
		if (opportunity_type.equalsIgnoreCase("Print")) {
			tabName = By.xpath("//a[@data-tab-value='Print']");
			buttonName = By.xpath("//button[text()='Create New Opportunity']");
		} else if (opportunity_type.equalsIgnoreCase("Digital")) {
			tabName = By.xpath("//a[@data-tab-value='Digital']");
			buttonName = By.xpath("//button[text()='Create New DigitalOpportunity']");
		} else if (opportunity_type.equalsIgnoreCase("F360")) {
			tabName = By.xpath("//a[@data-tab-value='F360']");
			buttonName = By.xpath("//button[text()='Create New Opportunity']");
		}
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.waitForElement(tabName, 50, "tabName selection");
		refGenericUtils.click_using_javaScript(tabName, "Opportunity.Tab name");
		refGenericUtils.click_using_javaScript(buttonName, "Opportunity.New.button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();
		if (opportunity_type.equalsIgnoreCase("Print")) {
			refGenericUtils.waitForElement(objectRepository.get("Opportunity.RecordType.Print.RadioButton"), 10, "Print Radio Button");
			refGenericUtils.click_using_javaScript(objectRepository.get("Opportunity.RecordType.Print.RadioButton"), "Print Radio Button");
			refGenericUtils.take_screenshot();
			refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "Next Button");
		}
		refGenericUtils.waitForElement(By.xpath("//*[text()='Brand']"), 20, "Brand Field");
	}
	@Then("user confirms the creation of Opportunity Type {string}")
	public void user_confirms_the_creation_of_opportunity(String opportunity_type){
		refGenericUtils.take_screenshot();
		refGenericUtils.click_using_javaScript(objectRepository.get("CreateNewOpportunity.Create.Button"), "CreateNewOpportunity.Create.Button");
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.waitForElement(objectRepository.get("PipelinePage.MyTeamOpps.CheckBox"), 10, "My Team Opps Checkbox");
		refGenericUtils.take_screenshot();

		//uncheck "My Team Opps" button
		refGenericUtils.click_using_javaScript(objectRepository.get("PipelinePage.MyTeamOpps.CheckBox"), "My Team Opps Checkbox");
		System.out.println("Unchecked");

		refGenericUtils.stop_script_for(2000);

//		After Unchecking the checkbox all Opps would be displayed
//		Click the First Opportunity Number to display its details on Right side
//		refGenericUtils.click_using_javaScript(objectRepository.get("PrintOpportunity.IdLink"),
//				"PrintOpportunity.IdLink");

		System.out.println("Clicked");
//		OppId = refGenericUtils.fetchingTextvalueofElement(objectRepository.get("PrintOpportunity.IdLink"), "Opp-Id");
//		refGenericUtils.waitForElement(objectRepository.get("PrintOpportunity.IdLink"), 20, "PrintOpportunity.IdLink");
		refGenericUtils.stop_script_for(2000);

		refGenericUtils.take_screenshot();
		int count = refGenericUtils.findElementsCount(objectRepository.get("Pipeline.CreateNewOpportunity.Button"), "Create New Opportunity Button");

		if (count == 1) {
			BaseUtil.scenario.log("New "+opportunity_type+" Opportunity  has been created Successfully");
			refGenericUtils.take_screenshot();
		} else {
			BaseUtil.scenario.log("Failed to create the "+opportunity_type+" Opportunity");
			Assert.fail("Failed to create the Opportunity");
			refGenericUtils.take_screenshot();
		}
	}

}
