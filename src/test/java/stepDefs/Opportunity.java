package stepDefs;

import base.BaseUtil;
import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.GenericUtils;

public class Opportunity extends BaseUtil {
	
	public static String account_name_text;
	public GenericUtils refGenericUtils = new GenericUtils(DriverFactory.getDriver());
	public AccountCreation refAccountCreation = new AccountCreation();
	public LoginPage loginPage = new LoginPage(DriverFactory.getDriver(), envDetails, objectRepository, usernumber);
	
	
	
	@When("User creates new Opportunity for {string} type")
	public void user_creates_new_Opportunity(String opportunity_type, DataTable dataTable) throws InterruptedException {
		refAccountCreation.globalSearch("Pipeline", "Test_Advertiser_Sep28_0838");
		refGenericUtils.click_using_javaScript(objectRepository.get("Opportunity.NewPrintOpportunity.button"), "Opportunity.NewPrintOpportunity.button");
		refAccountCreation.enter_values_updated(dataTable);
		refGenericUtils.click_using_javaScript(objectRepository.get("Save.Button"), "Save.Button");
		refGenericUtils.waitForElement(objectRepository.get("PrintOpportunity.IdLink"), 20, "PrintOpportunity.IdLink");
		refGenericUtils.click_using_javaScript(objectRepository.get("PrintOpportunity.IdLink"), "PrintOpportunity.IdLink");
		refGenericUtils.waitForElement(objectRepository.get("PrintOpportunity.IdLink"), 20, "PrintOpportunity.IdLink");
		Thread.sleep(2000); 
	}
	
	
	//========Delete
	
	
	
}
