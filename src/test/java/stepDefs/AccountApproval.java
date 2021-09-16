package stepDefs;

import org.testng.Assert;

import base.BaseUtil;
import factory.DriverFactory;
import io.cucumber.java.en.Then;
import pages.AccountsPage;
import pages.HomePage;
import pages.SetUpHomePage;

public class AccountApproval extends BaseUtil{
	
	public SetUpHomePage setUpHomePage = new SetUpHomePage(DriverFactory.getDriver(), objectRepository);
	public AccountsPage accountsPage = new AccountsPage(DriverFactory.getDriver(), objectRepository);
	public HomePage homePage = new HomePage(DriverFactory.getDriver(), objectRepository);
	
	@Then("user switches to {string}")
	public void user_switches_to(String element_name) {
	    setUpHomePage.switch_to_new_tab();
	}
	
	@Then("user searches for {string} in {string}")
	public void user_searches_for_in(String text_value, String textBox_element_name) {
		setUpHomePage.global_search_textbox(text_value, textBox_element_name);
		
	}
	
	@Then("user searches for an existing {string} {string} in {string}")
	public void user_searches_for_an_existing(String acc_or_opp_or_ppl, String text_value, String textBox_element_name) {
		homePage.global_search_textbox_existing(acc_or_opp_or_ppl, text_value, textBox_element_name);
	}
	
	@Then("user switches to {string} frame")
	public void user_switches_to_frame(String frame_name) {
		setUpHomePage.switch_to_frame(frame_name);
	}
	
	@Then("user scrolls to {string}")
	public void user_scrolls_to(String element_name) {
		accountsPage.scroll_to_view_element(element_name);
	}
	
	@Then("{string} must be set to {string}")
	public void  must_be_set_to(String textBox_element_name, String expected_text_value) {
		String actual_text_value = accountsPage.get_element_text_value(textBox_element_name);
		Assert.assertEquals(actual_text_value, expected_text_value);
	}

}
