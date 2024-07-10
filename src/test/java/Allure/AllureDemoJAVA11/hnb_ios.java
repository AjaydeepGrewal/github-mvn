package Allure.AllureDemoJAVA11;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class hnb_ios {
	IOSDriver driver = null;
	String host = "hnb.perfectomobile.com"; 
	ReportiumClient reportiumClient ;

	@BeforeTest
	public void setup() throws Exception
	{
		System.out.println("Before Test iOS");
		driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilitiesIOS());
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withContextTags("PS POC")
				.withWebDriver(driver)
				.build();
		reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}


	@AfterTest
	public void teardown() throws Exception
	{
		driver.quit();
	}

	@Test
	public void Login() throws Exception 
	{	
		try
		{
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			reportiumClient.testStart("IOS Login", new TestContext.Builder()
					.withTestExecutionTags("PS POC", "IOS Login")
					.build());
			//Thread.sleep(5000);
			Map<String, Object> params = new HashMap<>();

			params.put("identifier", "com.huntington.mbusinessonline.test");
			driver.executeScript("mobile:application:open", params);

			//Thread.sleep(10000);			

			reportiumClient.stepStart("Accept permissions and user agreement");
			WebElement permissions = driver.findElement(AppiumBy.xpath("//*[@label = 'Allow While Using App']"));

			if(permissions.isDisplayed())
			{
				permissions.click();
			}

			WebElement continueButton = driver.findElement(AppiumBy.xpath("//*[@label = 'Continue button']"));

			if(continueButton.isDisplayed())
			{
				continueButton.click();
			}

			WebElement acceptButton = driver.findElement(AppiumBy.xpath("//*[@label = 'Accept button']"));

			if(acceptButton.isDisplayed())
			{
				acceptButton.click();
			}

			reportiumClient.stepStart("Enter login details");
			WebElement loginPageCompanyID = driver.findElement(AppiumBy.xpath("//*[@label=\"Company ID\"]"));
			if(loginPageCompanyID.isDisplayed())
			{

				tapWithCoordinates1(getCenter(loginPageCompanyID).getX(), getCenter(loginPageCompanyID).getY());
				driver.findElement(AppiumBy.xpath("//*[@label=\"Company ID\"]")).sendKeys("571002");

				//Thread.sleep(5000);
				WebElement username = driver.findElement(AppiumBy.xpath("//*[@label='Username']"));
				tapWithCoordinates1(getCenter(username).getX(), getCenter(username).getY());
				driver.findElement(AppiumBy.xpath("//*[@label='Username']")).sendKeys("hnbjenkinsauto1");
				//Thread.sleep(5000);
				
				WebElement password = driver.findElement(AppiumBy.xpath("//*[@label='Password']"));
				tapWithCoordinates1(getCenter(password).getX(), getCenter(password).getY());
				driver.findElement(AppiumBy.xpath("//*[@label='Password']")).sendKeys("password@1");
				driver.findElement(AppiumBy.xpath("//*[@label='Log In button']")).click();
			}

			reportiumClient.stepStart("Enter OTP");
			WebElement OTP = driver.findElement(AppiumBy.xpath("//*[@label='One-Time Code']"));

			if(OTP.isDisplayed())
			{
				tapWithCoordinates1(getCenter(OTP).getX(), getCenter(OTP).getY());
				driver.findElement(AppiumBy.xpath("//*[@label='One-Time Code']")).sendKeys("123456");
				driver.findElement(AppiumBy.xpath("//*[@label='Submit button']")).click();
			}

			WebElement continueButton1 = driver.findElement(AppiumBy.xpath("//*[@label = 'Continue button']"));
			if(continueButton1.isDisplayed())
			{
				continueButton1.click();
			}

			reportiumClient.stepStart("Dismiss Biometrics prompt");
			WebElement dismiss = driver.findElement(AppiumBy.xpath("//*[@label=\"Dismiss\"]"));

			if(dismiss.isDisplayed())
			{
				dismiss.click();
			}
			reportiumClient.testStop(TestResultFactory.createSuccess());
		}
		catch(Exception e)
		{
			reportiumClient.testStop(TestResultFactory.createFailure(e.getMessage(), e));
			throw(e);
		}
	}
	

	XCUITestOptions capabilitiesIOS()
	{	
		XCUITestOptions capabilities = new XCUITestOptions();
		capabilities.setPlatformName("iOS");
		capabilities.setPlatformVersion("16.4");
		capabilities.setBundleId("com.huntington.mbusinessonline.test");
		capabilities.setApp("Public:Runner.zip");
		HashMap<String, Object> perfectoOptions = new HashMap<String, Object>();
		perfectoOptions.put("model", "iPhone 14");
		perfectoOptions.put("manufacturer", "Apple");
		perfectoOptions.put("useVirtualDevice", true);
		perfectoOptions.put("securityToken", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2OTY1N2UxZi1iM2M3LTQ0M2EtODg1Ny03MDUyMDZjZGRiYzkifQ.eyJpYXQiOjE3MTM3OTI5MDIsImp0aSI6IjU4ODY4MzQzLTkzNjctNDhjZS05NzBiLTY3NjEwNDkwMmVhYiIsImlzcyI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjhkODcyZTk0LTYzMTQtNDY2My1hM2M1LTc5ZDRkNWZmYjQzNiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiMDlhNzExYTAtMDU3My00NDE0LWJiNWItNDU5NGFmYWUyZjIxIiwic2Vzc2lvbl9zdGF0ZSI6ImNhZThlZDVlLTFjYTAtNGFmMi1iZWIxLWM2ZDVmZjZmMTdiYyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIn0.PQN_fE2xc2meh7vWEuuIp1PBk_zUPn4jNRS5ULBkNkk");
		capabilities.setCapability("perfecto:options", perfectoOptions);
		return capabilities;		
	}
	
	XCUITestOptions capabilitiesIOSReal()
	{	
		XCUITestOptions capabilities = new XCUITestOptions();
		capabilities.setPlatformName("iOS");
		capabilities.setBundleId("com.huntington.mbusinessonline.test");
		//capabilities.setApp("Public:Runner.ipa");
		HashMap<String, Object> perfectoOptions = new HashMap<String, Object>();
		perfectoOptions.put("deviceName", "00008130-001854502140001C");
		//perfectoOptions.put("iOSResign",true);
		perfectoOptions.put("securityToken", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2OTY1N2UxZi1iM2M3LTQ0M2EtODg1Ny03MDUyMDZjZGRiYzkifQ.eyJpYXQiOjE3MTM3OTI5MDIsImp0aSI6IjU4ODY4MzQzLTkzNjctNDhjZS05NzBiLTY3NjEwNDkwMmVhYiIsImlzcyI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjhkODcyZTk0LTYzMTQtNDY2My1hM2M1LTc5ZDRkNWZmYjQzNiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiMDlhNzExYTAtMDU3My00NDE0LWJiNWItNDU5NGFmYWUyZjIxIiwic2Vzc2lvbl9zdGF0ZSI6ImNhZThlZDVlLTFjYTAtNGFmMi1iZWIxLWM2ZDVmZjZmMTdiYyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIn0.PQN_fE2xc2meh7vWEuuIp1PBk_zUPn4jNRS5ULBkNkk");
		capabilities.setCapability("perfecto:options", perfectoOptions);
		return capabilities;		
	}

	public void tapWithCoordinates1(int xcoordinates, int ycoordinate) {
		System.out.println("Tapping on point with coordinates X:" + xcoordinates + " Y:" + ycoordinate);
		PointerInput finger = new PointerInput(org.openqa.selenium.interactions.PointerInput.Kind.TOUCH, "finger");
		org.openqa.selenium.interactions.Sequence clickPosition = new org.openqa.selenium.interactions.Sequence(finger, 1);
		clickPosition.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xcoordinates, ycoordinate))
		.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg())).addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		driver.perform(Arrays.asList(clickPosition));
	}

	public Point getCenter(WebElement ele) {
		Point upperLeft = ele.getLocation();
		Dimension dimensions = ele.getSize();
		return new Point(upperLeft.getX() + dimensions.getWidth() / 2,
				upperLeft.getY() + dimensions.getHeight() / 2);
	}


}
