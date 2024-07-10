package Allure.AllureDemoJAVA11;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class HNB_Consumer {
	IOSDriver driver = null;
	String host = "hnb.perfectomobile.com"; 
	ReportiumClient reportiumClient ;

	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception
	{
		System.out.println("Before Test iOS");
		//driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), iosLocal());
		driver = new IOSDriver(new URL("https://hnb.perfectomobile.com/nexperience/perfectomobile/wd/hub"), capabilitiesIOS());
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withContextTags("PS POC")
				.withWebDriver(driver)
				.build();
		reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}


	@AfterMethod(alwaysRun=true)
	public void teardown() throws Exception
	{
		try
		{
			driver.quit();
		}
		catch(Exception e)
		{
			System.out.println("Exception:" + e);
		}
		finally
		{
			driver.quit();
		}
	}

	@Test
	public void Login() throws Exception 
	{	
		try
		{
			System.out.println("Inside execution of first testcase");
			reportiumClient.testStart("IOS Login", new TestContext.Builder()
					.withTestExecutionTags("PS POC", "IOS Login")
					.build());
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			//Thread.sleep(5000);
			/*Map<String, Object> params = new HashMap<>();

			params.put("identifier", "com.huntington.test");
			driver.executeScript("mobile:application:open", params);*/
			driver.activateApp("com.huntington.test");
			Thread.sleep(5000);



			reportiumClient.stepStart("Accept permissions and user agreement");

			scrollDowntoXPath("//XCUIElementTypeButton[@name='Accept']",driver);
			driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name='Accept']")).click();

			/*WebElement permissions = driver.findElement(AppiumBy.xpath("//*[@label = 'Allow While Using App']"));

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
			}*/

			reportiumClient.stepStart("Enter login details");

			//Thread.sleep(5000);
			WebElement username = driver.findElement(AppiumBy.xpath("//*[@label='Username']"));
			tapWithCoordinates1(getCenter(username).getX(), getCenter(username).getY());
			driver.findElement(AppiumBy.xpath("//*[@label='Username']")).sendKeys("dodge413");
			//Thread.sleep(5000);

			WebElement password = driver.findElement(AppiumBy.xpath("//*[@label='Password']"));
			tapWithCoordinates1(getCenter(password).getX(), getCenter(password).getY());
			driver.findElement(AppiumBy.xpath("//*[@label='Password']")).sendKeys("12341234a");
			driver.findElement(AppiumBy.xpath("//*[@label='Log In']")).click();
			reportiumClient.stepStart("Enter OTP");
			WebElement OTP = driver.findElement(AppiumBy.xpath("//*[@label='Request a Verification Code']"));

			if(OTP.isDisplayed())
			{
				OTP.click();
				driver.findElement(AppiumBy.xpath("//XCUIElementTypeOther[@label='Select Contact Method']//following-sibling::XCUIElementTypeButton[1]")).click();
				driver.findElement(AppiumBy.xpath("//*[@label='Send Me a New Code']")).click();
				WebElement enterVerificationCode = driver.findElement(AppiumBy.xpath("//*[@label='Enter Verification Code']//following-sibling::*"));
				tapWithCoordinates1(getCenter(enterVerificationCode).getX(), getCenter(enterVerificationCode).getY());
				enterVerificationCode.sendKeys("123456");
				driver.findElement(AppiumBy.xpath("//*[@label='Next']")).click();
			}

			try
			{
				WebElement continueButton1 = driver.findElement(AppiumBy.xpath("//*[@label = 'Remember This Device']"));
				continueButton1.click();
			}
			catch(Exception e)
			{

			}

			reportiumClient.stepStart("Dismiss Biometrics prompt");


			WebElement dismiss = driver.findElement(AppiumBy.xpath("//*[@label=\"SKIP THESE TIPS\"]"));
			dismiss.click();
			Thread.sleep(10000);
			scrollDowntoXPath("//*[contains(@label,\"Investments\")]//following-sibling::*[1]",driver);
			driver.findElement(AppiumBy.xpath("//*[contains(@label,\"Investments\")]//following-sibling::*[1]")).click();

			driver.findElement(AppiumBy.xpath("//*[@*[contains(., 'something went wrong') or contains(., 'Something Went Wrong') or contains(., 'something happened')]]"));
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
			capabilities.setPlatformVersion("17.0");
			capabilities.setBundleId("com.huntington.test");
			capabilities.setApp("Public:4-5-2024_ios_simulator.zip");
			HashMap<String, Object> perfectoOptions = new HashMap<String, Object>();
			perfectoOptions.put("model", "iPhone 14 Pro Max");
			perfectoOptions.put("manufacturer", "Apple");
			//perfectoOptions.put("appiumVersion", "2.4.1");
			perfectoOptions.put("useVirtualDevice", true);
			perfectoOptions.put("securityToken", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2OTY1N2UxZi1iM2M3LTQ0M2EtODg1Ny03MDUyMDZjZGRiYzkifQ.eyJpYXQiOjE3MTM3OTI5MDIsImp0aSI6IjU4ODY4MzQzLTkzNjctNDhjZS05NzBiLTY3NjEwNDkwMmVhYiIsImlzcyI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvaG5iLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6IjhkODcyZTk0LTYzMTQtNDY2My1hM2M1LTc5ZDRkNWZmYjQzNiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiMDlhNzExYTAtMDU3My00NDE0LWJiNWItNDU5NGFmYWUyZjIxIiwic2Vzc2lvbl9zdGF0ZSI6ImNhZThlZDVlLTFjYTAtNGFmMi1iZWIxLWM2ZDVmZjZmMTdiYyIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIn0.PQN_fE2xc2meh7vWEuuIp1PBk_zUPn4jNRS5ULBkNkk");
			capabilities.setCapability("perfecto:options", perfectoOptions);
			return capabilities;		
		}

		DesiredCapabilities iosLocal()
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();

			capabilities.setCapability("appium:deviceName", "Iphon14-17");
			capabilities.setCapability("appium:platformName", "iOS");
			//capabilities.setCapability("appium:platformVersion", "16");
			capabilities.setCapability("appium:platformVersion", "17.0");
			capabilities.setCapability("appium:automationName", "XCUITest");
			//capabilities.setCapability("appium:browserName", "Safari");
			capabilities.setCapability("bundleId","com.huntington.test");
			//capabilities.setCapability("app","/Users/agrewal/Downloads/PUBLIC31/app.zip");
			//capabilities.setCapability("appium:app", "GROUP:ipa/development/RBCMobile.v6_31.j1731.b28049751.eIST0.31d59f2292.ipa");
			//capabilities.setCapability("bundleId", "com.apple.mobileslideshow");
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
			perfectoOptions.put("securityToken", "");
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

		public void verticalSwipe2()
		{
			Dimension size = driver.manage().window().getSize();
			Point midPoint = new Point((int)(size.width * 0.5),(int)(size.height * 0.5));
			int bottom = midPoint.y + (int)(midPoint.y * 0.75);
			int top = midPoint.y - (int)(midPoint.y * 0.75);
			Point start  = new Point(midPoint.x, bottom);
			Point end  = new Point(midPoint.x, top);
			swipe(start,end,Duration.ofSeconds(2));
		}

		void verticalSwipe1()
		{
			Dimension size = driver.manage().window().getSize();
			//Point midPoint = new Point((int)(size.width * 0.5),(int)(size.height * 0.5));
			//int bottom = midPoint.y + (int)(midPoint.y * 0.75);
			//int top = midPoint.y - (int)(midPoint.y * 0.75);

			//Point midPoint = new Point((int)(size.width * 0.5),(int)(size.height * 0.5));
			int bottom = size.height;
			int top = 0;
			Point start  = new Point(size.width/2, bottom);
			Point end  = new Point(size.width/2, top);
			swipe(start,end,Duration.ofSeconds(2));

			//Point start  = new Point(midPoint.x, bottom);
			//Point end  = new Point(midPoint.x, top);
			swipe(start,end,Duration.ofSeconds(2));
		}

		public  void swipe(Point start, Point end, Duration duration) {
			//boolean isAndroid = driver1 instanceof AndroidDriver;

			PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
			Sequence swipe = new Sequence(input, 0);
			swipe.addAction(input.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), start.x, start.y));
			swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

			swipe.addAction(input.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), end.x, end.y));
			swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			driver.perform(ImmutableList.of(swipe));
		}

		public void scrollDowntoXPath(String xPath, AppiumDriver driver) {
			boolean flag=true;
			int count=1;
			while(flag){
				try {
					driver.findElement(By.xpath(xPath));
					flag=false;
					System.out.println("Found");
					break;
				}
				catch(Exception NoSuchElementException) {
					System.out.println("Here");
					count=count+1;
					verticalSwipe1();
					if(count==15)
					{
						break;
					}
				}
			}
		}
	}


