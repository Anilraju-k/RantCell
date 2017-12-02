package implimentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class GlueCode {
	WebDriver driver;
	public Properties prop ;
	
	@Before
	public void intialisation(){
		try {
			FileInputStream fi=new FileInputStream("src\\test\\resources\\locators.properties");
			prop=new Properties();
			prop.load(fi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@After
	public void error(Scenario s) throws IOException{
		if (s.isFailed()){
			Reporter.addScreenCaptureFromPath(takeScreenShot());
			 //System.out.println(takeScreenShot());
			 Reporter.addScreenCast(takeScreenShot());
		}
	}	
	public void addScreenShot() throws IOException{
		Reporter.addScreenCaptureFromPath(takeScreenShot());		
		 Reporter.addScreenCast(takeScreenShot());
	}
	
	public String takeScreenShot() throws IOException{
		TakesScreenshot f=(TakesScreenshot)driver;
		File source=f.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String destination=System.getProperty("user.dir")+"\\errorScreenShots\\"+timeStamp+".png";
		File dest=new File(destination);
		FileUtils.copyFile(source, dest);
		return destination;
	}
	
	public By attribute(String prop)
	{
		String[] locator=prop.split(":");
		if (locator[0].contains("id"))
		return By.id(locator[1]);
	else if (locator[0].contains("name")) {
		return By.name(locator[1]);
	}
	else if (locator[0].contains("xpath")) {
		return By.xpath(locator[1]);
	}
	else if (locator[0].contains("css")) {
		return By.cssSelector(locator[1]);
	}
	else if (locator[0].contains("linkText")) {
		return By.linkText(locator[1]);
	}
	else if (locator[0].contains("class")) {
		return By.className(locator[1]);
	}
	else
		System.out.println(locator[0] +" is Invalid locator, Please write valid locator");
		return null;
		
	}
	private void enterText(String string, String arg1) {
		driver.findElement(attribute(prop.getProperty(string))).sendKeys(arg1);		
	}

	private void click(String string) {
		driver.findElement(attribute(prop.getProperty(string))).click();
		
	}

	private void getText(String string) {
		System.out.println(driver.findElement(attribute(prop.getProperty(string))).getText());
	}

	@Given("^I am on RantCell pre-login page$")
	public void i_am_on_RantCell_pre_login_page() throws Throwable {		
			System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
			driver=new ChromeDriver();	
			driver.manage().window().maximize();		
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("https://demo.rantcell.com/");
	}

	@When("^I enter username \"([^\"]*)\" and I enter password \"([^\"]*)\"$")
	public void i_enter_username_and_I_enter_password(String arg1, String arg2) throws Throwable {
	   click("loginButton");
		enterText("login",arg1);
	   enterText("userName",arg1);
	   enterText("password",arg2);
	   click("login");
	   addScreenShot();
	}

	


	@When("^I click on login button$")
	public void i_click_on_login_button() throws Throwable {
	    
	}

	@Then("^Ishould be logged onto the RantCell Home page$")
	public void ishould_be_logged_onto_the_RantCell_Home_page() throws Throwable {
	    
	}
	
	@When("^I get the basic details$")
	public void I_get_the_basic_details() throws Throwable {
	   //waitUntil("TotalTestConducted");
	   Thread.sleep(10000);	   
	   getText("RemainingTestMinutes");
	   getText("RegisterdDevices");
	   getText("TotalTestConducted");
	   getText("DetectedNetworks");	
	   addScreenShot();
		}
	private void waitUntil(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);		 
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(attribute(string))));
	}


	@When("^I logoff from the RantCell$")
	public void I_logoff() throws Throwable {
	   click("customerName");
	   click("logOff");
	   
		}
	
	
}
