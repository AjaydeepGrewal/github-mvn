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
import org.openqa.selenium.interactions.Actions;
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
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class HNBLoginAndroid {
	AndroidDriver driver = null;
	String host = "hnb.perfectomobile.com"; 
	ReportiumClient reportiumClient ;

	@BeforeTest
	public void setup() throws Exception
	{
		System.out.println("Before Test Android");
		driver = new AndroidDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilitiesAndroidReal());
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
		Thread.sleep(10000);
		try
		{
			driver.findElement(AppiumBy.xpath("//*[@*[contains(., 'something went wrong') or contains(., 'Something Went Wrong') or contains(., 'something happened') or contains(., 'Incorrect Entry') or contains(., 'No Connection Detected') or contains(., 'be back soon') or contains(., 'system issues')]]"));
		}
		catch(Exception e)
		{
			
		}
		try
		{
			reportiumClient.testStart("Android Login", new TestContext.Builder()
					.withTestExecutionTags("PS POC", "Android Login")
					.build());
			Map<String, Object> params = new HashMap<>();

			params.put("identifier", "com.huntington.mbusinessonline.test");
			driver.executeScript("mobile:application:open", params);

			//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			reportiumClient.stepStart("Accept permissions and user agreement");
			try
			{
				WebElement continueButton = driver.findElement(AppiumBy.xpath("//*[@content-desc=\"Continue button\"]"));
				if(continueButton.isDisplayed())
				{
					continueButton.click();
				}
			}
			catch(Exception e)
			{

			}
			Thread.sleep(1000);
			try
			{
				WebElement acceptButton = driver.findElement(AppiumBy.xpath("//*[@content-desc = 'Accept button']"));

				if(acceptButton.isDisplayed())
				{
					acceptButton.click();
				}
			}
			
			catch(Exception e)
			{

			}
			Thread.sleep(1000);
			try
			{
				driver.findElement(AppiumBy.xpath("//*[@*[contains(., 'something went wrong') or contains(., 'Something Went Wrong') or contains(., 'something happened') or contains(., 'Incorrect Entry') or contains(., 'No Connection Detected') or contains(., 'be back soon') or contains(., 'system issues')]]"));
			}
			catch(Exception e)
			{
				
			}
			reportiumClient.stepStart("Enter login details");
			WebElement loginPageCompanyID = driver.findElement(AppiumBy.xpath("//*[@text='Company ID Field']"));
			if(loginPageCompanyID.isDisplayed())
			{

				Actions action = new Actions(driver);
				tapWithCoordinates1(getCenter(loginPageCompanyID).getX(), getCenter(loginPageCompanyID).getY());
				Thread.sleep(1000);
				action.sendKeys("571002").perform();
				Thread.sleep(5000);

				WebElement username = driver.findElement(AppiumBy.xpath("//*[@text=\"Username Field\"]"));
				tapWithCoordinates1(getCenter(username).getX(), getCenter(username).getY());
				Thread.sleep(1000);
				action.sendKeys("hnbjenkinsauto1").perform();
				Thread.sleep(1000);
				WebElement password = driver.findElement(AppiumBy.xpath("//*[@text=\"Password Field\"]"));
				tapWithCoordinates1(getCenter(password).getX(), getCenter(password).getY());
				Thread.sleep(1000);
				action.sendKeys("password@1").perform();
				driver.findElement(AppiumBy.xpath("//*[@content-desc=\"Log In button\"]")).click();
			}
			reportiumClient.stepStart("Enter OTP");
			try
			{
				driver.findElement(AppiumBy.xpath("//*[@*[contains(., 'something went wrong') or contains(., 'Something Went Wrong') or contains(., 'something happened') or contains(., 'Incorrect Entry') or contains(., 'No Connection Detected') or contains(., 'be back soon') or contains(., 'system issues')]]"));
			}
			catch(Exception e)
			{
				
			}
			WebElement OTP = driver.findElement(AppiumBy.xpath("//*[contains(@text,'One-Time Code')]"));

			if(OTP.isDisplayed())
			{
				Actions action = new Actions(driver);
				tapWithCoordinates1(getCenter(OTP).getX(), getCenter(OTP).getY());
				action.sendKeys("123456").perform();
				driver.findElement(AppiumBy.xpath("//*[@content-desc='Submit button']")).click();
			}

			WebElement continueButton1 = driver.findElement(AppiumBy.xpath("//*[@content-desc='Continue button']"));
			if(continueButton1.isDisplayed())
			{
				continueButton1.click();
			}
			Thread.sleep(5000);
			try
			{
				driver.findElement(AppiumBy.xpath("//*[@*[contains(., 'something went wrong') or contains(., 'Something Went Wrong') or contains(., 'something happened') or contains(., 'Incorrect Entry') or contains(., 'No Connection Detected') or contains(., 'be back soon') or contains(., 'system issues')]]"));
			}
			catch(Exception e)
			{
				
			}
			reportiumClient.testStop(TestResultFactory.createSuccess());
		}
		catch(Exception e)
		{
			reportiumClient.testStop(TestResultFactory.createFailure(e.getMessage(), e));
			throw(e);
		}
	}

	static UiAutomator2Options capabilitiesAndroid()
	{	
		UiAutomator2Options capabilities = new UiAutomator2Options();
		capabilities.setPlatformName("Android");
		capabilities.setPlatformVersion("13");
		capabilities.setAppPackage("com.huntington.mbusinessonline.test");
		capabilities.setApp("PUBLIC:luavmandroid.apk");
		HashMap<String, Object> perfectoOptions = new HashMap<String, Object>();
		perfectoOptions.put("model", "galaxy s23");
		perfectoOptions.put("manufacturer", "Samsung");
		perfectoOptions.put("useVirtualDevice", true);
		perfectoOptions.put("securityToken", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2OTY1N2UxZi1iM2M3LTQ0M2EtODg1Ny03MDUyMDZjZGRiYzkifQ.eyJpYXQiOjE3MTM3OTI5MDIsImp0aSI6IjU4ODY4MzQzLTkzNjctNDhjZS05NzBiLTY3NjEwNDkwMmVhYiIsImlzcyI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjhkODcyZTk0LTYzMTQtNDY2My1hM2M1LTc5ZDRkNWZmYjQzNiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiMDlhNzExYTAtMDU3My00NDE0LWJiNWItNDU5NGFmYWUyZjIxIiwic2Vzc2lvbl9zdGF0ZSI6ImNhZThlZDVlLTFjYTAtNGFmMi1iZWIxLWM2ZDVmZjZmMTdiYyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIn0.PQN_fE2xc2meh7vWEuuIp1PBk_zUPn4jNRS5ULBkNkk");
		capabilities.setCapability("perfecto:options", perfectoOptions);
		return capabilities;		
	}

	static UiAutomator2Options capabilitiesAndroidReal()
	{	
		UiAutomator2Options capabilities = new UiAutomator2Options();
		capabilities.setPlatformName("Android");
		capabilities.setAppPackage("com.huntington.mbusinessonline.test");
		//capabilities.setApp("PUBLIC:luavmandroid.apk");
		HashMap<String, Object> perfectoOptions = new HashMap<String, Object>();
		perfectoOptions.put("deviceName", "R5CX20X2DRX");
		perfectoOptions.put("securityToken", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2OTY1N2UxZi1iM2M3LTQ0M2EtODg1Ny03MDUyMDZjZGRiYzkifQ.eyJpYXQiOjE3MTM3OTI5MDIsImp0aSI6IjU4ODY4MzQzLTkzNjctNDhjZS05NzBiLTY3NjEwNDkwMmVhYiIsImlzcyI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjhkODcyZTk0LTYzMTQtNDY2My1hM2M1LTc5ZDRkNWZmYjQzNiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiMDlhNzExYTAtMDU3My00NDE0LWJiNWItNDU5NGFmYWUyZjIxIiwic2Vzc2lvbl9zdGF0ZSI6ImNhZThlZDVlLTFjYTAtNGFmMi1iZWIxLWM2ZDVmZjZmMTdiYyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIn0.PQN_fE2xc2meh7vWEuuIp1PBk_zUPn4jNRS5ULBkNkk");
		capabilities.setCapability("perfecto:options", perfectoOptions);
		return capabilities;		
	}
	
	static UiAutomator2Options capabilitiesAndroidSessionSharing()
	{	
		UiAutomator2Options capabilities = new UiAutomator2Options();
		HashMap<String, Object> perfectoOptions = new HashMap<String, Object>();
		perfectoOptions.put("deviceSessionId", "8056ad94-0dc6-426a-b760-b91f30eca0f4");
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
