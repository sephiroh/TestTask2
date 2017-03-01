package executionEngine;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;


public class DriverScript {
	
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
	
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	
	
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
	}
	
	public static void main(String[] args) throws Exception {
		ExcelUtils.setExcelFile(Constants.Path_TestData);
		DOMConfigurator.configure("log4j.xml");
		
		String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
	}
	
    private void execute_TestCase() throws Exception {
    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);
		for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
			bResult = true;
			sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
			sRunMode = ExcelUtils.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
			if (sRunMode.equals("Yes")){
				iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				Log.startTestCase(sTestCaseID);
				bResult=true;
				for (;iTestStep<iTestLastStep;iTestStep++){
		    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
		    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
		    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
		    		execute_Actions();
					if(bResult==false){
						ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
						break;
						}

					}
				if(bResult==true){
				ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
				Log.endTestCase(sTestCaseID);	
					}
				}
			}
		}
	
	
    private static void execute_Actions() throws Exception {
    	 
		for(int i=0;i<method.length;i++){
 
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sPageObject);
				if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					break;
				}else{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
					ActionKeywords.closeBrowser("", "");
					break;
					}
				}
			}
     }
}		

/*
		String exePath = "D:\\rc\\7z\\Selenium Setup\\chromedriver_win32\\chromedriver.exe";
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		System.setProperty("webdriver.chrome.driver", exePath);
		WebDriver driver = new ChromeDriver(options);
		Actions action = new Actions(driver);
		driver.get("https://accounts.amaysim.com.au/identity/login/");
		driver.findElement(By.xpath("//input[@name='mobile_number']")).sendKeys("0468827174");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("theHoff34");
		driver.findElement(By.xpath("//input[@name='commit']")).click();
		driver.navigate().to("https://www.amaysim.com.au/my-account/my-amaysim/dashboards");
		action.sendKeys(Keys.ESCAPE).perform();
		driver.findElement(By.linkText("My Settings")).click();
		driver.findElement(By.linkText("Logout")).click();
		//driver.quit();
*/
