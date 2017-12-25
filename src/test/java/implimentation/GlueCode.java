package implimentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
	public String timeStamp ;
	
	@Before
	public void intialisation(){
		try {
			FileInputStream fi=new FileInputStream("src\\test\\resources\\locators.properties");
			prop=new Properties();
			prop.load(fi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();	
		driver.manage().window().maximize();		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
		timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String destination=System.getProperty("user.dir")+"\\ScreenShots\\"+timeStamp+".png";
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
	
	@SuppressWarnings("deprecation")
	private void verifyText(String string, String string2) {
		Assert.assertEquals(string2, driver.findElement(attribute(prop.getProperty(string))).getText());
		
	}

	private void getText(String string) {
		System.out.println(driver.findElement(attribute(prop.getProperty(string))).getText());
	}

	@Given("^I am on RantCell pre-login page$")
	public void i_am_on_RantCell_pre_login_page() throws Throwable {		
			if(driver!=null)
			driver.get("https://demo.rantcell.com/");
	}

	@When("^I enter username \"([^\"]*)\" and I enter password \"([^\"]*)\"$")
	public void i_enter_username_and_I_enter_password(String arg1, String arg2) throws Throwable {
	   click("loginButton");
		enterText("login",arg1);
	   enterText("userName",arg1);
	   enterText("password",arg2);
	   
	}


	@When("^I click on login button$")
	public void i_click_on_login_button() throws Throwable {
		
		click("login");		
		addScreenShot();
		Thread.sleep(10000);	
	}

	@Then("^I should be logged onto the RantCell Home page$")
	public void ishould_be_logged_onto_the_RantCell_Home_page() throws Throwable {
	    verifyText("loginVerify", "Dashboard");
		
	}
	
	@When("^I get the basic details$")
	public void I_get_the_basic_details() throws Throwable {
	   //waitUntil("TotalTestConducted");	      
	   getText("RemainingTestMinutes");
	   getText("RegisterdDevices");
	   getText("TotalTestConducted");
	   getText("DetectedNetworks");	
	   addScreenShot();	   
		}
	


	@When("^I click on Registerd devices for more information$")
	public void I_click_on_Registerd_devices_for_more_information() throws InterruptedException, IOException{
		 Thread.sleep(10000);
		 click("RegisterdDevicesMoreinfo");
		   Thread.sleep(3000);
		   addScreenShot();
		  
		   JavascriptExecutor jse = (JavascriptExecutor)driver;
		   jse.executeScript("window.scrollBy(0,500)", "");
		  }
	
	@When("^I click on detected networks for more information$")
	public void I_click_on_detected() throws InterruptedException, IOException{
		 Thread.sleep(15000);
		 click("DetectedNetworksMoreinfo");
		   Thread.sleep(10000);
		   addScreenShot();
		  
		   JavascriptExecutor jse = (JavascriptExecutor)driver;
		   jse.executeScript("window.scrollBy(0,500)", "");
		  }
	@When("^I get the list of detected networks$")
	public void I_get_th() throws Throwable {
		int i=driver.findElements(By.xpath("//*[@id='tableView']/div[2]/div/div/table/tbody/tr")).size();
		   List<WebElement> ele=driver.findElements(By.xpath("//*[@id='tableView']/div[2]/div/div/table/tbody/tr"));
		   System.out.println("No of networks detected are "+i);
		   File file = new File("D:\\Selenium\\RantCell_Cucumber\\Log\\DetectedNetworks.txt");
		   
		   WriteArrayToFile(ele,file);
		   
		   for (WebElement webElement : ele) {
			System.out.println(webElement.getText());
		}	  	   
		}
	
	@When("^I get the list of devices registerd$")
	public void I_get_the() throws Throwable {
		int i=driver.findElements(By.xpath("//*[@id='tableView']/div[2]/div/div/table/tbody/tr")).size();
		   List<WebElement> ele=driver.findElements(By.xpath("//*[@id='tableView']/div[2]/div/div/table/tbody/tr"));
		   System.out.println("No of devices registerd are "+i);
		   File file = new File("D:\\Selenium\\RantCell_Cucumber\\Log\\RegisterdDevices.txt");
		   WriteArrayToFile(ele,file);
		   
		   for (WebElement webElement : ele) {
			System.out.println(webElement.getText());
		}	  	   
		}
	
	@When("^I logoff from the RantCell$")
	public void I_logoff() throws Throwable {
	   click("customerName");
	   click("logOff");   
		}
	
	public void WriteArrayToFile(List<WebElement> ele, File file) throws IOException{
		
		 if (!file.exists()) {
			 file.createNewFile();
         }
	        FileWriter writer = new FileWriter(file);
	        int size = ele.size();
	        for (int i=0;i<size;i++) {
	            String str = ele.get(i).getText();
	            writer.write(str);
	            if(i < size-1)//This prevent creating a blank like at the end of the file**
	                writer.write("\n");
	        }
	        writer.close();
	    }
	
	private void waitUntil(String string) {
		WebDriverWait wait = new WebDriverWait(driver, 10);		 
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(attribute(string))));
	}
}	



		
		
		
		
		/*try {
		    FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir")+"\\ScreenShots\\"+timeStamp+"RegisterdDevices"));
		    ObjectOutputStream oos = new ObjectOutputStream(fos);   
		    oos.writeObject(ele); 
		    oos.close(); 
		} catch(Exception ex) {
		    ex.printStackTrace();
		}*/
