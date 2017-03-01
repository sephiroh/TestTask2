package config;

import static executionEngine.DriverScript.OR;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import executionEngine.DriverScript;
import utility.Log;

public class ActionKeywords {

	public static String exePath = "D:\\rc\\7z\\Selenium Setup\\chromedriver_win32\\chromedriver.exe";
	public static WebDriver driver;
	public static Actions action;
	
	public static void openBrowser(String object,String data){	
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
		Log.info("Opening Browser");
		try{
			driver.get("https://accounts.amaysim.com.au/identity/login/");
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	
	public static void enter(String object, String data){
		try{
			Log.info("Entering the text in " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		 }catch(Exception e){
			 Log.error("Not able to Enter UserName --- " + e.getMessage());
			 DriverScript.bResult = false;
		 }
	}
	
	public static void click(String object, String data){
		try{
			Log.info("Clicking on Webelement "+ object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			DriverScript.bResult = false;
         }
	}
	
	public static void navigate(String object, String data){
		try{
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//Constant Variable is used in place of URL
			driver.get(Constants.URL);
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void waitFor(String object, String data){
		try{
			Log.info("Wait for 3 seconds");
			Thread.sleep(3000);
		 }catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 DriverScript.bResult = false;
         }
	}
	
	public static void pressESC(String object, String data){
		action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).perform();
	}
	
	public static void closeBrowser(String object, String data){
		try{
			Log.info("Closing the browser");
			driver.quit();
		 }catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 DriverScript.bResult = false;
         }
	}
	
	
}
