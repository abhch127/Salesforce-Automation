package base;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

public class BaseUtil {
	
	public static LinkedHashMap<Object, LinkedHashMap<Object, Object>> appName;
	public static LinkedHashMap<String, By> objectRepository;
	public static LinkedHashMap<Object, Object> envDetails;
	public static String applicationName, browserName, environment, usernumber;
	public static SoftAssert softAssert;
	public static Reporter report;

}
