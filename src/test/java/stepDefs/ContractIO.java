package stepDefs;

import java.util.Map;

import base.BaseUtil;
import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.GenericUtils;

public class ContractIO extends BaseUtil {
	
	public static String account_name_text;
	public GenericUtils refGenericUtils = new GenericUtils(DriverFactory.getDriver());
	public AccountCreation refAccountCreation = new AccountCreation();
	public LoginPage loginPage = new LoginPage(DriverFactory.getDriver(), envDetails, objectRepository, usernumber);
	
	
	
	@When("User creates new ContractIO for {string} account")
	public void user_creates_Contract_IO(String contractIO_type, DataTable dataTable) throws InterruptedException {
		Map<String, String> map_info = refAccountCreation.feature_file_data(dataTable);
		refAccountCreation.globalSearch("Pipeline", "Test_Advertiser_Sep28_0838");
		refGenericUtils.click_using_javaScript(objectRepository.get("NewContractIO.Button"), "NewContractIO.Button");
		refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "NextButton");
		refGenericUtils.click_using_javaScript(objectRepository.get("ContractIO.Print.RadioButton"), "ContractIO.Print.RadioButton");
		refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "NextButton");
		refGenericUtils.waitUntilPageLoads();
		refAccountCreation.enter_values_updated(dataTable);
		refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "NextButton");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.select_dropdown_index(objectRepository.get("ContractIO.Opportunity"), Integer.parseInt(map_info.get("ContractIO.Opportunity")), "ContractIO.Opportunity");
		refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "NextButton");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.select_dropdown_value(objectRepository.get("ContractIO.BillTo.Dropdown"), map_info.get("ContractIO.BillTo.Dropdown"), "ContractIO.BillTo.Dropdown");
		//refGenericUtils.click_using_javaScript(objectRepository.get("ContractIO.Agency"), "ContractIO.Agency");
		//refGenericUtils.toEnterTextValue(objectRepository.get("ContractIO.Agency"), map_info.get("ContractIO.Agency"), "ContractIO.Agency");
		Thread.sleep(3000);
		refGenericUtils.scrollToViewElement(objectRepository.get("ContractIO.Agency"), "ContractIO.Agency");
		//refGenericUtils.sendKeysJS(objectRepository.get("ContractIO.Agency"), map_info.get("ContractIO.Agency"));
		refGenericUtils.toEnterTextValue(objectRepository.get("ContractIO.Agency"), map_info.get("ContractIO.Agency"), "ContractIO.Agency");;
		Thread.sleep(2000);
		refGenericUtils.keyboard_action(objectRepository.get("HomePage.GlobalSearch.SearchType"), "Enter");
		refGenericUtils.click_using_javaScript(objectRepository.get("ContractIO.TestAgencyAccount"), "ContractIO.TestAgencyAccount");
		refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "NextButton");
		refGenericUtils.waitUntilPageLoads();
		refGenericUtils.select_dropdown_index(objectRepository.get("ContractIO.BillToAddress"), Integer.parseInt(map_info.get("ContractIO.BillToAddress")), "ContractIO.BillToAddress");
		refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "NextButton");
		Thread.sleep(2000); 
		refGenericUtils.click_using_javaScript(objectRepository.get("NextButton"), "NextButton");
		Thread.sleep(2000);
	}
	
	
	//========Delete
	
	
	
}
