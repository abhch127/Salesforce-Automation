package stepDefs;

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
	
	
	
	@When("User creates new Opportunity for {string} type")
	public void user_creates_new_Opportunity(String opportunity_type, DataTable dataTable) throws InterruptedException {
		refAccountCreation.globalSearch("Pipeline", AccountName);//AccountName
		refGenericUtils.waitUntilPageLoads();
		By tabName=null;
		By buttonName=null;
		if(opportunity_type.equalsIgnoreCase("Print")) {
			 tabName=By.xpath("//a[@data-tab-value='Print']");
			 buttonName=By.xpath("//button[text()='Create New Print Opportunity']");
		} else if(opportunity_type.equalsIgnoreCase("Digital")) {
			 tabName=By.xpath("//a[@data-tab-value='Digital']");
			 buttonName=By.xpath("//button[text()='Create New DigitalOpportunity']");
		}else if(opportunity_type.equalsIgnoreCase("F360")) {
			 tabName=By.xpath("//a[@data-tab-value='F360']");
			 buttonName=By.xpath("//button[text()='Create New F360 Opportunity']");
		}
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.waitForElement(tabName, 50, "tabName selection");
		refGenericUtils.click_using_javaScript(tabName, "Opportunity.Tab name");
		refGenericUtils.click_using_javaScript(buttonName, "Opportunity.New.button");
		refGenericUtils.waitUntilPageLoads();
		//refGenericUtils.waitForElement(byXpath, 20, ElementName);
		refAccountCreation.enter_values_updated(dataTable);
		refGenericUtils.click_using_javaScript(objectRepository.get("Save.Button"), "Save.Button");
		refGenericUtils.waitForElement(objectRepository.get("NewContractIO.Button"), 20, "NewContractIO.Button");
		refGenericUtils.click_using_javaScript(objectRepository.get("PrintOpportunity.IdLink"), "PrintOpportunity.IdLink");
		OppId=refGenericUtils.fetchingTextvalueofElement(objectRepository.get("PrintOpportunity.IdLink"), "Opp-Id");
		refGenericUtils.waitForElement(objectRepository.get("PrintOpportunity.IdLink"), 20, "PrintOpportunity.IdLink");
		Thread.sleep(2000); 
		int count = refGenericUtils.findElementsCount(objectRepository.get("Opprtunity.CloneButton"), "Opprtunity.CloneButton");
		if(count==1) {
			BaseUtil.scenario.log("New Opportunity created Successfully");
			refGenericUtils.take_screenshot();
		}
		else {
			BaseUtil.scenario.log("Failed to create the Opportunity");
			Assert.fail("Failed to create the Opportunity");
			refGenericUtils.take_screenshot();
		}
	}
	
	
	//========Delete
	
	
	
}
