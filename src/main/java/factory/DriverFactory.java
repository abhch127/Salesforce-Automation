package factory;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
	
	public WebDriver init_driver(String browser, String profile, String os) throws IOException {

		if(browser.equalsIgnoreCase("Chrome") && os.contains("Windows")) {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("taskkill /im chrome.exe /f /t");
			WebDriverManager.chromedriver().setup();
//			System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver-win64/chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--user-data-dir="+profile);
			//options.addArguments("--profile-directory=Person 1");
			options.addArguments("--start-maximized");

			ChromeDriver driver = new ChromeDriver(options);
			threadDriver.set(driver);
		}

		else if(browser.equalsIgnoreCase("Chrome") && os.contains("Mac")){
			
			System.out.println("in the Mac if case");

//			Runtime rt = Runtime.getRuntime();
//			Process proc = rt.exec("taskkill /im chrome.exe /f /t");
			
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("--user-data-dir="+profile);
			//options.addArguments("--profile-directory=Person 1");
			options.addArguments("--start-maximized");

			ChromeDriver driver = new ChromeDriver(options);
			threadDriver.set(driver);

		}


		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
	}


	
	public static synchronized WebDriver getDriver() {
		return threadDriver.get();
	}
}
