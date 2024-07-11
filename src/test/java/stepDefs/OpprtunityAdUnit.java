package stepDefs;

import base.BaseUtil;

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

import java.util.LinkedHashMap;
import java.util.Map;


public class OpprtunityAdUnit extends BaseUtil {

    public GenericUtils refGenericUtils = new GenericUtils(DriverFactory.getDriver());
    public AppGenericUtils refAppGenericUtils = new AppGenericUtils();
    public LoginPage loginPage = new LoginPage(DriverFactory.getDriver(), envDetails, objectRepository, usernumber);


    @When("user creates the Product for the {string} Opportunity")
    public void user_creates_product_for_first_opp_of_type(String Opp_type){
        Opp_type = Opp_type.toUpperCase();
        By tabName = null; By firstOppFromList = null;
        By checkbox = null;

        if(Opp_type.equalsIgnoreCase("Print") || Opp_type.equalsIgnoreCase("Direct Media") ){
            tabName = By.xpath("//a[text()='PRINT']");
            checkbox = objectRepository.get("PipelinePage.PRINTTAB.MyTeamOpps.CheckBox");
        } else if (Opp_type.equalsIgnoreCase("Digital") || Opp_type.equalsIgnoreCase("Programmatic")){
            tabName = By.xpath("//a[text()='DIGITAL']");
            checkbox = objectRepository.get("PipelinePage.DIGITALTAB.MyTeamOpps.CheckBox");
        }

        firstOppFromList = By.xpath("//*[contains(text(),'"+Opp_type+"') and contains(text(),'OPPORTUNITIES')]//ancestor::li//table/tbody/tr[1]//a");

        refGenericUtils.waitUntilPageLoads();
        refGenericUtils.click_using_javaScript(tabName,"Opp type tab");
        refGenericUtils.stop_script_for(3000);

        refGenericUtils.click_using_javaScript(checkbox,checkbox.toString());
        refGenericUtils.stop_script_for(3000);
        refGenericUtils.waitForElement(firstOppFromList,10,"first Opp from List");
        refGenericUtils.take_screenshot();
        refGenericUtils.click_using_javaScript(firstOppFromList,"first Opp from List");
        refGenericUtils.waitForVisibilty(objectRepository.get("Pipeline.OppDetailPage"), 10, "Pipeline.OppDetailPage");
        refGenericUtils.take_screenshot();
        refGenericUtils.stop_script_for(10000); //waiting few seconds because the Opp open img button internally taking time to have the proper url
        refGenericUtils.click_using_javaScript(objectRepository.get("Pipeline.OppDetailPage.OppOpenImageButton"), "Pipeline.OppDetailPage.OppOpenImageButton");
        refGenericUtils.stop_script_for(10000);
        refGenericUtils.switchingTabs(DriverFactory.getDriver().getWindowHandle(), DriverFactory.getDriver().getWindowHandles());

        refGenericUtils.take_screenshot();
        System.out.print("Windows Title: "+DriverFactory.getDriver().getTitle());
        refGenericUtils.click_using_javaScript(objectRepository.get("OpportunityAdUnit.CreateNew.Button"), "OpportunityAdUnit.CreateNew.Button");
        refGenericUtils.stop_script_for(5000);
        String product_field_value = (Opp_type.equalsIgnoreCase("Digital"))? "Audio": "Onsert";
        Map<String,String> fields = new LinkedHashMap<String,String>();
        fields.put("Products:.SingleInputDropdown", product_field_value);
        fields.put("Sales Price.TextBox","1000");
        //Define all fields and (their value items xpath, if they are singleinputdropdown)  as Map
        if (Opp_type.equalsIgnoreCase("Digital")){
            fields.put("Sales Price Change Reason.SingleInputDropdown","Incremental Increase");
            refAppGenericUtils.update_field_entries(fields);
        }
        else if (Opp_type.equalsIgnoreCase("Print")){
            fields.put("Height.TextBox","3");
            fields.put("Width.TextBox","2");
            fields.put("Sides.TextBox","3");
            fields.put("Insert Type.SingleInputDropdown","Poly");
            fields.put("Price Override Reason.SingleInputDropdown","Barter");
            fields.put("Ad Type.SingleInputDropdown","Display - 1");
            fields.put("Run Type:.SingleInputDropdown", "National");
            refAppGenericUtils.update_field_entries(fields);

        } else {
            fields.put("Height.TextBox","3");
            fields.put("Width.TextBox","2");
            fields.put("Sides.TextBox","3");
            refAppGenericUtils.update_field_entries(fields);
        }

        //After filling the Data
        //refGenericUtils.scrollToViewElement(objectRepository.get("OpportunityAdUnit.CreateNew.Save.Button"),"Save Button");
        refGenericUtils.moveToElement(objectRepository.get("OpportunityAdUnit.CreateNew.Button"), "OpportunityAdUnit.CreateNew.Button");
        refGenericUtils.take_screenshot();
        refGenericUtils.click_using_javaScript(objectRepository.get("OpportunityAdUnit.CreateNew.Save.Button"), "OpportunityAdUnit.CreateNew.Save.Button");
        refGenericUtils.stop_script_for(1000);
        //Assertions, Deletion of created Ad unit
        By checkbox_element = objectRepository.get("OpportunityAdUnit.AdUnitTile.CheckBox");
        refGenericUtils.waitForVisibilty(checkbox_element, 10, "OpportunityAdUnit.AdUnitTile.CheckBox");
        refGenericUtils.moveToElement(checkbox_element, "checkbox");
        refGenericUtils.take_screenshot();
        if (refGenericUtils.findElementsCount(checkbox_element,"checboxelement")>0){
            scenario.log("The Opportunity Ad Unit is created Succesfully");
            refGenericUtils.moveToElement(objectRepository.get("OpportunityAdUnit.AdUnitTitle"),"OpportunityAdUnit.AdUnitTitle");
            refGenericUtils.take_screenshot();
        } else
            Assert.fail("Failed to Create Opportunity Ad Unit");

        //Now delete the Opportunity Ad Unit
        refGenericUtils.click_using_javaScript(checkbox_element,"checkbox");
        refGenericUtils.click_using_javaScript(objectRepository.get("OpportunityAdUnit.DeleteSelected.Button"), "OpportunityAdUnit.DeleteSelected.Button");
        refGenericUtils.stop_script_for(5000);
//		if (! refGenericUtils.findElement(checkbox_element,"checkbox").isDisplayed()){
//			scenario.log("Opportunity Ad Unit is Deleted Successfully");
//			refGenericUtils.take_screenshot();
//		}
//		else softAssert.fail("Could not Delete Ad Unit");

		/*
		* Now Delete Opportunity
		refGenericUtils.click_using_javaScript(objectRepository.get("Opportunity.Delete.Button"),"Opportunity.Delete.Button");
		 */

    }

}
