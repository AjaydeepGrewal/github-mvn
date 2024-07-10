package Allure.AllureDemoJAVA11;

import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;

import io.appium.java_client.android.AndroidDriver;

public class Anthem {
	RemoteWebDriver driver = null;
	String host = "anthem.perfectomobile.com"; 
	ReportiumClient reportiumClient ;

	@BeforeTest
	public void setup() throws Exception
	{
		System.out.println("Before Test Android");
		driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilitiesMacAnthem());
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withContextTags("PS POC")
				.withWebDriver(driver)
				.build();
		reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}
	
	@Test
	public void anthem() throws InterruptedException
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.xpath("//h1[contains(.,'Dental Plan Privacy Notice')]")).click();
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("motatk");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("Smile$1404");
		driver.findElement(By.xpath("//*[@id=\"submit_button\"]")).click();
		Thread.sleep(10000);
	}
	
	public DesiredCapabilities capabilitiesMacAnthem()
	{
		DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
		capabilities.setCapability("platformName", "Mac");
		capabilities.setCapability("platformVersion", "macOS Big Sur");
		capabilities.setCapability("browserName", "Safari");
		capabilities.setCapability("browserVersion", "15");
		capabilities.setCapability("location", "NA-US-BOS");
		capabilities.setCapability("resolution", "800x600");
		capabilities.setCapability("securityToken", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI3Nzg3YTRlOS0wNjI4LTRmNGUtODA5Ny1kZjNhOWJkYTJjMTEifQ.eyJpYXQiOjE3MTQzNTI1MDgsImp0aSI6ImEyNzRlZmJjLTk1N2QtNGM4Ny04Y2JiLTgwOWVkODE5N2VkNCIsImlzcyI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvYW50aGVtLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvYW50aGVtLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjUyMmMxODFmLTgzOWUtNGUxNi05MTQ3LTg3YTQzM2E1OTczNSIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiYTEwMWM0NzQtZjE4Mi00MGViLThlZTktZjUzYjllMjJlYTU2Iiwic2Vzc2lvbl9zdGF0ZSI6IjMyMGQwMjcyLWU3MTktNDQzMC05ZTcwLTg5Y2M1MzVmNmFmZSIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIn0.IWjPvmhfZ20FhsOeOuZVgprbk70A5-1x0hXdtWhhYCw");
		return capabilities;
	}

}
