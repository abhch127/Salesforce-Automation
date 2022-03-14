package factory;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {
	
	public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
	
	public WebDriver init_driver(String browser, String profile) throws IOException {
		if(browser.equalsIgnoreCase("Chrome")) {
			Runtime rt = Runtime.getRuntime();
//			Process proc = rt.exec("taskkill /im chrome.exe /f /t");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("user-data-dir="+profile);
			options.addArguments("--start-maximized");
			threadDriver.set(new ChromeDriver(options));
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
	}
	
	public static synchronized WebDriver getDriver() {
		return threadDriver.get();
	}
}
