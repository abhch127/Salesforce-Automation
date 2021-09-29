package utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseUtil;

public class GenericUtils extends BaseUtil {

	public static WebDriver driver;

	public GenericUtils(WebDriver driver) {
		GenericUtils.driver = driver;
	}

	public void waitForElement(By byXpath, int timeoutinSec, String ElementName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutinSec);
			wait.until(ExpectedConditions.elementToBeClickable(byXpath));
		} catch (Exception e) {
			softAssert.fail("Failed to find " + ElementName + " in " + timeoutinSec + " sec");
			take_screenshot();
			e.printStackTrace();
		}
	}

	public void waitForVisibilty(By byXpath, int timeoutinSec, String ElementName) {

		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutinSec);
			wait.until(ExpectedConditions.presenceOfElementLocated(byXpath));
		} catch (Exception e) {
			softAssert.fail("Unable to locate " + ElementName);
			take_screenshot();
			e.printStackTrace();
		}
	}

	public void waitUntilPageLoads() {
		try {
			ExpectedCondition<Boolean> pageloadcondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver input) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				}
			};
			Thread.sleep(5000);
			new WebDriverWait(driver, 100).until(pageloadcondition);
		} catch (Exception e) {
			softAssert.fail("Failed to load the page within the given time");
			take_screenshot();
			e.printStackTrace();
		}
	}

	public void toEnterTextValue(By by_xpath, String valueToPass, String elementName) {
		try {
			waitForElement(by_xpath, 20, elementName);
			driver.findElement(by_xpath).sendKeys(valueToPass);
		} catch (Exception e) {
			softAssert.fail("Failed to enter the text " + "\'" + valueToPass + "\'" + " in " + elementName);
			take_screenshot();
			e.printStackTrace();
		}
	}

	public boolean clickOnElement(By byXpath, String elementName) {
		try {
			waitForElement(byXpath, 20, elementName);
			driver.findElement(byXpath).click();
			return true;
		} catch (Exception e) {
			softAssert.fail("Failed to click on " + elementName);
			take_screenshot();
			e.printStackTrace();
			return false;
		}
	}

	public void click_using_javaScript(By byXpath, String elementName) {
		try {
			waitForElement(byXpath, 20, elementName);
			WebElement element = driver.findElement(byXpath);
			JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
			myExecutor.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			softAssert.fail("Failed to click on " + elementName);
			take_screenshot();
			e.printStackTrace();
		}
	}

	public <Element> void clickUsingActions(Element element, String ElementName) {
		String element_class = element.getClass().getName();
		try {
			if (element_class.endsWith("ByXPath")) {
				Actions action = new Actions(driver);
				By byxpath = (By) element;
				WebElement element_to_be_clicked = driver.findElement(byxpath);
				action.moveToElement(element_to_be_clicked).click(element_to_be_clicked).build().perform();
			}
			if (element_class.endsWith("RemoteWebElement")) {
				WebElement element_to_be_clicked = (WebElement) element;
				Actions action = new Actions(driver);
				action.moveToElement(element_to_be_clicked).click(element_to_be_clicked).build().perform();
			}
		} catch (Exception e) {
			softAssert.fail("Failed to click on " + ElementName);
			take_screenshot();
			e.printStackTrace();
		}

	}

	public String pageTitle() {
		waitUntilPageLoads();
		String pageTitle = "";
		try {
			pageTitle = driver.getTitle();
		} catch (Exception e) {
			softAssert.fail("Either the Page load is failed or this page does not have a valid Title");
			take_screenshot();
			e.printStackTrace();
		}
		return pageTitle;
	}

	public <Element> String fetchingTextvalueofElement(Element element, String ElementName) {
		String element_class = element.getClass().getName();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		String elementTextValue = "";
		try {
			if (element_class.endsWith("ByXPath")) {
				By by_xpath = (By) element;
				waitForElement(by_xpath, 10, ElementName);
				elementTextValue = driver.findElement(by_xpath).getText();
			} else if (element_class.endsWith("RemoteWebElement")) {
				WebElement by_webelement = (WebElement) element;
				wait.until(ExpectedConditions.visibilityOf(by_webelement));
				elementTextValue = by_webelement.getText();
			}
		} catch (Exception e) {
			softAssert.fail("Failed to get the text value from " + ElementName);
			take_screenshot();
			e.printStackTrace();
		}
		return elementTextValue;
	}

	public List<String> click_Fromlist_of_Textvalues(By by_xpath, String value, String ElementName) {
		waitUntilPageLoads();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		List<String> list_of_element_texts = new ArrayList<String>();
		try {
			List<WebElement> list_of_WebElements = driver.findElements(by_xpath);
			wait.until(ExpectedConditions.visibilityOfAllElements(list_of_WebElements));
			for (WebElement element : list_of_WebElements) {
				if (element.getText().trim().equalsIgnoreCase(value)) {
					JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
					myExecutor.executeScript("arguments[0].click();", element);
				}
			}
		} catch (Exception e) {
			softAssert.fail("Failed to get the list of text values from " + ElementName);
			take_screenshot();
			e.printStackTrace();
		}
		return list_of_element_texts;
	}

	public String get_Date(String date_and_time_format) {
		String req_format = "";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(date_and_time_format);
			Date date = new Date(System.currentTimeMillis());
			req_format = formatter.format(date);
			return req_format;
		} catch (Exception e) {
			softAssert.fail("Failed to extract the current Date and Time");
			take_screenshot();
			e.printStackTrace();
		}
		return req_format;
	}

	public <T> void scrollToViewElement(T element, String ElementName) {
		String element_class = element.getClass().getName();
		try {
			if (element_class.endsWith("ByXPath")) {
				By by_xpath = (By) element;
				JavascriptExecutor je = (JavascriptExecutor) driver;
				WebElement elementToBeVisible = driver.findElement(by_xpath);
				je.executeScript("arguments[0].scrollIntoView(true);", elementToBeVisible);
			} else if (element_class.endsWith("RemoteWebElement")) {
				WebElement element_name = (WebElement) element;
				JavascriptExecutor je = (JavascriptExecutor) driver;
				je.executeScript("arguments[0].scrollIntoView(true);", element_name);
			}
		} catch (Exception e) {
			softAssert.fail("Failed to double click on " + ElementName);
			take_screenshot();
			e.printStackTrace();
		}
	}

	public void switchingTabs(String homewindow, Set<String> noofWindows) {
		try {
			Thread.sleep(2000);
			Iterator<String> itr = noofWindows.iterator();
			while (itr.hasNext()) {
				String windowhandle = itr.next().toString();
				if (!windowhandle.contains(homewindow)) {
					driver.switchTo().window(windowhandle);
					break;
				}
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Unable to switch to window");
			take_screenshot();
		}
	}

	public void switchingFrame(By byFrameXpath, String Framename) {
		try {
			waitForVisibilty(byFrameXpath, 40, Framename);
			WebElement elementFrame = driver.findElement(byFrameXpath);
			driver.switchTo().frame(elementFrame);
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Unable to Switch to Frame");
			take_screenshot();
		}
	}

	public void ClearTextBox(By byxpath, String element_name) {
		try {
			driver.findElement(byxpath).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
		} catch (Exception E) {
			E.printStackTrace();
			softAssert.fail("Unable to clear the text in " + element_name);
			take_screenshot();
		}
	}

	public void select_dropdown_value(By by_xpath, String value, String element_name) {
		try {
			WebElement element = driver.findElement(by_xpath);
			waitForElement(by_xpath, 10, value);
			Select select = new Select(element);
			select.selectByValue(value);
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Failed to select " + value + " from the " + element_name);
			take_screenshot();
		}
	}

	public String get_cloned_opportunity(String selected_issue) {
		String[] issue_arr = selected_issue.split("[^\\w]");
		String month = issue_arr[0].toLowerCase();
		String opp_number = "";
		String titleCase_month = month.substring(0, 1).toUpperCase() + month.substring(1);
		try {
			By by_issues = By.xpath("//td[contains(text(),'" + titleCase_month + "')]/..//td[@data-label='Opp #']//a");
			List<WebElement> elements = driver.findElements(by_issues);
			for (WebElement ele : elements) {
				String issue_name = ele.getAttribute("title");
				if (selected_issue.equals(issue_name)) {
					By by_req_issue = By.xpath("//a[@title='" + selected_issue + "']//p");
					String[] opp_number_with_zero = fetchingTextvalueofElement(by_req_issue, selected_issue).split(" ");
					opp_number = opp_number_with_zero[0];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Failed to generate the opportunity");
			take_screenshot();
		}
		return opp_number;
	}

	public void stop_script_for(int sleep_time) {
		try {
			Thread.sleep(sleep_time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void take_screenshot() {
		byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		scenario.attach(sourcePath, "image/png", "");
	}

	public void keyboard_action(By byxpath, String key) {
		try {
			Actions action = new Actions(driver);
			WebElement element = driver.findElement(byxpath);
			switch (key) {
			case "Enter":
				action.sendKeys(element, Keys.ENTER).build().perform();
				break;
			default:
				action.sendKeys(element, Keys.RETURN).build().perform();
			}
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Failed to click on " + key);
			take_screenshot();
		}
	}

	public <Element> String fetch_attribute_value(Element xpath_or_webelement, String attribute_name,
			String element_name) {
		String element_class = xpath_or_webelement.getClass().getName();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		String req_value = "";
		try {
			if (element_class.endsWith("ByXPath")) {
				By by_xpath = (By) xpath_or_webelement;
				wait.until(ExpectedConditions.presenceOfElementLocated(by_xpath));
				req_value = driver.findElement(by_xpath).getAttribute(attribute_name);
			} else if (element_class.endsWith("RemoteWebElement")) {
				WebElement element = (WebElement) xpath_or_webelement;
				wait.until(ExpectedConditions.visibilityOf(element));
				req_value = element.getAttribute(attribute_name);
			} else {
				softAssert.fail("Incorrect Webelement passed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			softAssert.fail("Failed to find " + element_name);
			take_screenshot();
		}
		return req_value;
	}

	public final boolean containsDigit(String s) {
		boolean containsDigit = false;
		if (s != null && !s.isEmpty()) {
			for (char c : s.toCharArray()) {
				if (containsDigit = Character.isDigit(c)) {
					break;
				}
			}
		}
		return containsDigit;
	}

}
