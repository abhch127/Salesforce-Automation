package stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.BaseUtil;
import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.GenericUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Opportunity extends BaseUtil {

	public static String account_name_text;
	public GenericUtils refGenericUtils = new GenericUtils(DriverFactory.getDriver());
	public AppGenericUtils refAppGenericUtils = new AppGenericUtils();
	public LoginPage loginPage = new LoginPage(DriverFactory.getDriver(), envDetails, objectRepository, usernumber);

	By tabName = null;
	By buttonName = null;


	@When("User navigates to New Opportunity  for {string} type")
	public void  user_navigates_to_new_Opportunity(String opportunity_type) {

		/*Since we are already present on Pipeline Page
		refAccountCreation.globalSearch("Pipeline", AccountName);//AccountName */

		refGenericUtils.waitUntilPageLoads();

		if (opportunity_type.equalsIgnoreCase("Print") || opportunity_type.equalsIgnoreCase("Direct Media")) {
			tabName = By.xpath("//a[@data-tab-value='Print']");
			buttonName = By.xpath("//button[text()='Create New Opportunity']");
		} else if (opportunity_type.equalsIgnoreCase("Digital") || opportunity_type.equalsIgnoreCase("Programmatic")) {
			tabName = By.xpath("//a[@data-tab-value='Digital']");
			buttonName = By.xpath("//div[contains(@class,'slds-grid cMDP_DigitalOpportunityList')]//button[contains(@type,'button')]" +
					"[normalize-space()='Create New Opportunity']");
		} else if (opportunity_type.equalsIgnoreCase("F360")) {
			buttonName = By.xpath("//div[contains(@class,'slds-grid cMDP_F360OpportunityList')]//button[contains(@type,'button')]" +
					"[normalize-space()='Create New Opportunity']");
			tabName = By.xpath("//a[@data-tab-value='F360']");
		}


		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.waitForElement(tabName, 50, "tabName selection");
		refGenericUtils.click_using_javaScript(tabName, "Opportunity.Tab name");

		refGenericUtils.click_using_javaScript(buttonName, "Opportunity.New.button");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.take_screenshot();

		By radioButtonXpath = By.xpath("//legend[text()='Select a record type']/..//*[contains(text(), '"+opportunity_type+"')]/ancestor::label/span[1]");

		refGenericUtils.waitForElement(radioButtonXpath, 10, ""+opportunity_type+" Radio Button");
		refGenericUtils.click_using_javaScript(radioButtonXpath, ""+opportunity_type+" Radio Button");
		refGenericUtils.take_screenshot();
		refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "Next Button");

//		refGenericUtils.waitForElement(By.xpath("//*[text()='Brand']"), 20, "Brand Field");
		refGenericUtils.stop_script_for(3000);
	}
	@Then("user confirms the creation of Opportunity Type {string}")
	public void user_confirms_the_creation_of_opportunity(String opportunity_type){
		refGenericUtils.stop_script_for(5000);
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.waitForElement(objectRepository.get("PipelinePage.PRINTTAB.MyTeamOpps.CheckBox"), 10, "My Team Opps Checkbox");
		refGenericUtils.waitForVisibilty(objectRepository.get("PipelinePage.PRINTTAB.MyTeamOpps.CheckBox"), 10, "My Team Opps Checkbox");
		refGenericUtils.stop_script_for(3000);
		refGenericUtils.take_screenshot();

		refGenericUtils.stop_script_for(3000);

		refGenericUtils.take_screenshot();
		int count = !opportunity_type.equalsIgnoreCase("F360")?
				                                          refGenericUtils.findElementsCount(objectRepository.get("Pipeline.CreateNewOpportunity.Button"), "Create New Opportunity Button for Print"):
				                                          refGenericUtils.findElementsCount(buttonName,"Create New Opportunity Button for F360");

		if (count == 1) {
			BaseUtil.scenario.log("New "+opportunity_type+" Opportunity  has been created Successfully");
			refGenericUtils.take_screenshot();
		}

		else {
			BaseUtil.scenario.log("Failed to create the "+opportunity_type+" Opportunity");
			softAssert.fail("Failed to create the Opportunity");
			refGenericUtils.take_screenshot();
		}


	}


	public List<Map<String,String>> create_field_Maps(String opp_type, Map<String,String> Opp_info){
		List<Map<String,String>> ListFields = new ArrayList<Map<String, String>>();
		Map<String,String> firstPagefieldsMap = new LinkedHashMap<>();
		Map<String,String> secondPagefieldsMap = new LinkedHashMap<>();


		//Construct a two (single HasMap structure) of field with values

		if (opp_type.equalsIgnoreCase("Print") || opp_type.equalsIgnoreCase("Direct Media")){
			firstPagefieldsMap.put("Brand.SingleInputDropdown", Opp_info.get("Brand"));
			firstPagefieldsMap.put("Advertiser.SingleInputDropdown", Opp_info.get("Advertiser"));
			firstPagefieldsMap.put("Title.SingleInputDropdown", Opp_info.get("Title"));
			firstPagefieldsMap.put("Issue.SingleInputDropdown", Opp_info.get("Issue"));

			secondPagefieldsMap.put("Planning Agency.Lookup",Opp_info.get("Planning Agency"));
			secondPagefieldsMap.put("Opp Estimate.TextBox",Opp_info.get("Opp Estimate"));
			secondPagefieldsMap.put("Foundry/Content Strategy involved?.SelectDropdown",Opp_info.get("Foundry/Content Strategy involved?"));
		}
		else
		if (opp_type.equalsIgnoreCase("Digital") ||opp_type.equalsIgnoreCase("Programmatic")  ){
			firstPagefieldsMap.put("Brand.SingleInputDropdown", Opp_info.get("Brand"));
			firstPagefieldsMap.put("Advertiser.SingleInputDropdown", Opp_info.get("Advertiser"));
			//Digital Second Page fields
			if (opp_type.equalsIgnoreCase("Digital")){
				secondPagefieldsMap.put("Opportunity Name",Opp_info.get("Opportunity Name"));
				secondPagefieldsMap.put("Planning Agency.Lookup",Opp_info.get("Planning Agency"));
				secondPagefieldsMap.put("Sales Category.SingleInputDropdown",Opp_info.get("Sales Category"));
				secondPagefieldsMap.put("Order Type.SelectDropdown",Opp_info.get("Order Type"));
				secondPagefieldsMap.put("Campaign Start Date.Date",Opp_info.get("Campaign Start Date"));
				secondPagefieldsMap.put("Campaign End Date.Date",Opp_info.get("Campaign End Date"));
				secondPagefieldsMap.put("RFP Due Date.Date",Opp_info.get("RFP Due Date"));
				secondPagefieldsMap.put("Foundry/Content Strategy involved?.SelectDropdown",Opp_info.get("Foundry/Content Strategy involved?"));
				secondPagefieldsMap.put("Opp Estimate.TextBox",Opp_info.get("Opp Estimate"));
			} else {
				secondPagefieldsMap.put("Opportunity Name",Opp_info.get("Opportunity Name"));
				secondPagefieldsMap.put("Planning Agency.Lookup",Opp_info.get("Planning Agency"));
				secondPagefieldsMap.put("Campaign Start Date.Date",Opp_info.get("Campaign Start Date"));
				secondPagefieldsMap.put("Campaign End Date.Date",Opp_info.get("Campaign End Date"));
				secondPagefieldsMap.put("Order Type.SelectDropdown",Opp_info.get("Order Type"));
			}

		}
		else {
			//F360 Opp
			firstPagefieldsMap.put("Brand.SingleInputDropdown", Opp_info.get("Brand"));
			firstPagefieldsMap.put("Advertiser.SingleInputDropdown", Opp_info.get("Advertiser"));

			secondPagefieldsMap.put("Opp Estimate.TextBox",Opp_info.get("Opp Estimate"));
			secondPagefieldsMap.put("Close Date.Date",Opp_info.get("Close Date"));
			secondPagefieldsMap.put("Execute Month.Date",Opp_info.get("Execute Month"));

		}
		System.out.println(firstPagefieldsMap);

		ListFields.add(firstPagefieldsMap);
		ListFields.add(secondPagefieldsMap);

		return ListFields;

	}



	@When("User creates {string} Opportunity and Validate")
	public void user_creates_opportunity_and_validate(String opp_type, DataTable datatable) {

		//refAppGenericUtils.update_field_entries();

		user_navigates_to_new_Opportunity(opp_type);



		Map<String,String> Opp_info = refAppGenericUtils.feature_file_data(datatable);
		List<Map<String,String>> listOfMaps = new ArrayList<Map<String,String>>();
		listOfMaps = this.create_field_Maps(opp_type, Opp_info);
		System.out.println("listOfMaps"+listOfMaps);
		//For All Opportunities Type expect F360 below process is same
		if (! opp_type.equalsIgnoreCase("MBL")){
			refAppGenericUtils.update_field_entries(listOfMaps.get(0));
			refAppGenericUtils.user_clicks_that_action_button("Next");

		/*	if (refGenericUtils.findElementsCount(By.xpath("//*[contains(text(),'Do you wish to continue?')]//ancestor" +
					"::lightning-formatted-rich-text"),"Element")==1){
				refAppGenericUtils.user_clicks_that_action_button("Next");
			}

		 */
			if(! opp_type.equalsIgnoreCase("Programmatic")){
				refAppGenericUtils.user_clicks_that_action_button("Next");
			}
			refGenericUtils.waitUntilPageLoads();
			refAppGenericUtils.update_field_entries(listOfMaps.get(1));
			refAppGenericUtils.user_clicks_that_action_button("Create");

		}
		else {
			softAssert.fail("Not Valid Opportunity Type");
		}

		//User validates the Creation of Opportunity
		user_confirms_the_creation_of_opportunity(opp_type);
	}

}
