import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/*@RunWith(Cucumber.class)
@CucumberOptions(plugin={"pretty","html:target/cucumber","json:target/cucumber/cucumber.json"},
				features="src//test//resources",tags={"@login"})

public class RunnerClass {*/



@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"src//test//resources"},
   // glue = {"src//test//java//implimentation"},
    plugin = {"com.cucumber.listener.ExtentCucumberFormatter:output/report.html"},
    format = {"pretty", "html:target/Destination"}  ,
    tags={" @tag2"}
)
public class RunnerClass {
    @AfterClass
    public static void setup() {
        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Windows 8");
        Reporter.setTestRunnerOutput("Sample test runner output message");
    }

}
