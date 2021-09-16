package stepDefs;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BaseUtil;
import factory.DriverFactory;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.PipelinePage;
import utilities.GenericUtils;

public class PipelineCreation extends BaseUtil{
	
	public WebDriver driver;
	public LinkedHashMap<String, By> objectRepository;
	public GenericUtils refGenericUtils;
	
	public PipelineCreation(WebDriver driver, LinkedHashMap<String, By> objectRepository) {
		this.driver = driver;
		this.objectRepository = objectRepository;
		refGenericUtils = new GenericUtils(driver);
	}
	
	PipelinePage pipelinePage = new PipelinePage(DriverFactory.getDriver(), objectRepository);
	
	@Then("user clears existing value from {string}")
	public void user_clears_existing_value_from(String textBox_element_name) {
		pipelinePage.clear_textbox_values(textBox_element_name);
	}

}
