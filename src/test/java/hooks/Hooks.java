package hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import base.BaseUtil;
import factory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.Constants;
import utilities.ExcelUtility;
import utilities.JsonUtility;

public class Hooks extends BaseUtil{
	
	private DriverFactory driverFactory;
	private WebDriver driver;
	private JsonUtility jsonUtility = new JsonUtility();
	private ExcelUtility refExcelUtility = new ExcelUtility();
	
	@Before
	public void get_config_details() {
		try {
			appName = refExcelUtility.readExcel(Constants.TEST_DATA_FILE_PATH, Constants.APP_CONFIG_SHEET_NAME);
			driverFactory = new DriverFactory();
			softAssert = new SoftAssert();
			report = new Reporter();
			Object[] appNames = appName.keySet().toArray();
			applicationName = appNames[0].toString();
			browserName = appNames[1].toString();
			usernumber = appNames[2].toString();
			environment = appNames[3].toString();
			
			if(applicationName.equalsIgnoreCase("Salesforce")){
				envDetails = jsonUtility.environmentSetUp(applicationName.toLowerCase(), environment, Constants.ENVIRONMENT_PATH);
				objectRepository = jsonUtility.readObjectRepository(applicationName.toLowerCase(), Constants.OBJECT_PATH);
				driver = driverFactory.init_driver(browserName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After(order = 0)
	public void quitBrowser() {
		driver.quit();
	}
	
	@AfterStep
	public void tearDown(Scenario scenario) {
//		if(scenario.isFailed()) {
//			String screenShotName = scenario.getName().replaceAll(" ", "_");
			byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", "");
//		}
	}
	
	@After(order=1)
	public void assert_all() {
		softAssert.assertAll();
	}
}
