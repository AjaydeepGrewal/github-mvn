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
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class HNB_ConsumerRealDevice {
	public IOSDriver driver = null;
	public String host = "hnb.perfectomobile.com"; 
	public ReportiumClient reportiumClient ;
	
	public PerfectoExecutionContext perfectoExecutionContext;
	String userName = "";
	String password = "";
	
	@BeforeClass(alwaysRun=true)
	public void beforeClass() throws MalformedURLException
	{
		driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilitiesIOSReal());
		perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withJob(new Job("IOS Tests Perfecto", 4).withBranch("branch-name"))
				.withContextTags("PS POC")
				.withWebDriver(driver)
				.build();
		reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
		
	}

	@BeforeMethod(alwaysRun=true)
	public void setup(ITestContext context) throws Exception
	{
		reportiumClient.testStart(context.getName(), new TestContext.Builder()
				.withTestExecutionTags("PS POC", "IOS Login")
				.build());
		System.out.println("Before Test iOS");
		userName = context.getCurrentXmlTest().getParameter("Username");
		password = context.getCurrentXmlTest().getParameter("Password");
			
	}
	
	@AfterMethod(alwaysRun=true)
	public void destroy(ITestContext context, ITestResult result)
	{
		if ( reportiumClient!= null ) {
			reportiumClient.testStop( result.getStatus() == ITestResult.SUCCESS ? TestResultFactory.createSuccess() : TestResultFactory.createFailure( result.getThrowable().getMessage(), result.getThrowable() ) );
		}
	}


	@AfterClass(alwaysRun=true)
	public void teardown() throws Exception
	{	
		try
		{		
			driver.close();
			System.out.println("driver closed");
		}
		catch(Exception e)
		{
			//System.out.println("Exception:" + e);
		}
		finally
		{			
			driver.quit();
		}
		System.out.println("\n\nPerfectoReportUrl = \n" + reportiumClient.getReportUrl()+ "\n\n");
	}

	
	@Test
	public void SendTransfer() throws Exception 
	{	
		int initialTransferAmount = getRandomNumber(10,99);
		int editTransferAmount = getRandomNumber(10,99);
		try
		{
			//Thread.sleep(5000);
			Map<String, Object> params = new HashMap<>();

			params.put("identifier", "com.huntington.test");
			driver.executeScript("mobile:application:open", params);

			reportiumClient.stepStart("Accept permissions and user agreement");

			scrollDowntoXPath("//XCUIElementTypeButton[@name='Accept']",driver);
			driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name='Accept']")).click();
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			WebElement remind = driver.findElement(AppiumBy.xpath("//*[@label = 'Remind Me Later']"));
			if(remind.isDisplayed())
			{
				remind.click();
			}

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
			driver.findElement(AppiumBy.xpath("//*[@label='Username']")).sendKeys(userName);
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
				WebElement continueButton1 = driver.findElement(AppiumBy.xpath("//*[@label = 'Remember This Device']"));
				if(continueButton1.isDisplayed())
				{
					continueButton1.click();
				}
			}


			reportiumClient.stepStart("Skip These tips");
			WebElement dismiss = driver.findElement(AppiumBy.xpath("//*[@label=\"SKIP THESE TIPS\"]"));

			if(dismiss.isDisplayed())
			{
				dismiss.click();
			}
			
			
			Thread.sleep(30000);
			scrollDowntoXPath1("//*[contains(@label,\"Investments\")]//following-sibling::*[1]",driver);
			driver.findElement(AppiumBy.xpath("//*[contains(@label,\"Investments\")]//following-sibling::*[1]")).click();

			driver.findElement(AppiumBy.xpath("//*[@*[contains(., 'something went wrong') or contains(., 'Something Went Wrong') or contains(., 'something happened')]]"));
			
			
			
			
			reportiumClient.stepStart("Navigate to Tranfers page");
			driver.findElement(AppiumBy.xpath("(//*[contains(@label, 'Transfers') or contains(@content-desc, 'Transfers')])[last()]")).click();
			
			reportiumClient.stepStart("Navigate to Internal Transfers page");
			driver.findElement(AppiumBy.xpath("//*[contains(@content-desc, 'Between Huntington Accounts')]/../*[@content-desc='Transfer Funds'] | //*[contains(@label, 'Between Huntington Accounts')]/../*[@label='Transfer Funds']")).click();
			
			reportiumClient.stepStart("Create Internal Tranfer");
			driver.findElement(AppiumBy.xpath("//*[contains(@content-desc, 'Select') and (contains(@content-desc, 'From Account') or contains(@content-desc, 'from account'))] | //*[contains(@label, 'Select') and (contains(@label, 'From Account') or contains(@label, 'from account'))]")).click();
			
			scrollDowntoXPath("(//*[contains(@label, 'Checking')])[1] | (//*[contains(@content-desc, 'Checking')])[1]",driver);
			driver.findElement(AppiumBy.xpath("(//*[contains(@label, 'Checking')])[1] | (//*[contains(@content-desc, 'Checking')])[1]")).click();
			
			driver.findElement(AppiumBy.xpath("//*[contains(@content-desc, 'Select') and (contains(@content-desc, 'To Account') or contains(@content-desc, 'to account'))] | //*[contains(@label, 'Select') and (contains(@label, 'To Account') or contains(@label, 'to account'))]")).click();
			
			driver.findElement(AppiumBy.xpath("(//*[contains(@label, 'Checking')])[1] | (//*[contains(@content-desc, 'Checking')])[1]")).click();
			
			driver.findElement(AppiumBy.xpath("//*[contains(@label, 'Transfer Amount') and contains(@label, 'clear')] | //*[contains(@content-desc, 'Transfer Amount') and contains(@content-desc, 'clear')]")).click();
			
			WebElement amount = driver.findElement(AppiumBy.xpath("//*[@label=\"$\"]//following-sibling::*[1]"));
			
			tapWithCoordinates1(getCenter(amount).getX(), getCenter(amount).getY());
			
			amount.sendKeys(String.valueOf(initialTransferAmount));
			
			driver.findElement(AppiumBy.xpath("//*[contains(@content-desc, 'Transfer Frequency')]/android.widget.Button | //*[contains(@content-desc, 'Transfer Frequency') and not(*)] | //*[contains(@label, 'Select A Frequency')] | //*[contains(@label, 'Transfer Frequency')]"));
			
			driver.findElement(AppiumBy.xpath("//*[@content-desc[contains(., 'One') and contains(., 'Time')]] | //*[@label[contains(., 'One') and contains(., 'Time')]]")).click();
			Thread.sleep(2000);
			driver.findElement(AppiumBy.xpath("//*[@content-desc[contains(., 'One') and contains(., 'Time')]] | //*[@label[contains(., 'One') and contains(., 'Time')]]")).click();
			
			
			driver.findElement(AppiumBy.xpath("//*[@content-desc='Submit'] | //*[@label='Submit']")).click();
			
			reportiumClient.stepStart("Dismiss transfer receipt page");
			driver.findElement(AppiumBy.xpath("//*[@label='Done'] | //*[contains(@content-desc, 'Done')]")).click();
			
			reportiumClient.stepStart("Navigate to Internal Scheduled Transfers");
			driver.findElement(AppiumBy.xpath("//*[contains(@content-desc, 'Between Huntington Accounts')]/../*[@content-desc='Scheduled Transfers'] | //*[contains(@label, 'Between Huntington Accounts')]/../*[@label='Scheduled Transfers']")).click();
			
			reportiumClient.stepStart("Find scheduled transfer in list");
			WebElement transfer = driver.findElement(AppiumBy.xpath("(//*[contains(@content-desc, 'from:') or contains(@content-desc, 'Transfer From')])[1] | (//*[contains(@label, 'from:') or contains(@label, 'Transfer From')])[1]"));
			
			if(transfer.isDisplayed())
			{
				String initialAmount = "//*[@*[contains(., '$0.%d')]]";
				String initialAmountXpath = String.format(initialAmount, initialTransferAmount);
				
				WebElement amt = driver.findElement(AppiumBy.xpath(initialAmountXpath));
				if(amt.isDisplayed())
				{
					amt.click();
				}
			}
			
			reportiumClient.stepStart("Get transfer receipt info");
			
			WebElement receipt = driver.findElement(AppiumBy.xpath("//*[@label[contains(., 'Transfer From')]]"));
			if(receipt.isDisplayed())
			{
				System.out.println(driver.findElement(AppiumBy.xpath("//*[contains(@label, 'Confirmation') or contains(@label, 'Reference') or contains(@value, 'Confirmation') or contains(@value, 'Reference')]")).getText());
				System.out.println(driver.findElement(AppiumBy.xpath("//*[@label[contains(., 'Date') and not(contains(., 'End'))]]")).getText());
				
			}
			
			WebElement frequency = driver.findElement(AppiumBy.xpath("//*[@*[contains(., 'One-Time') or contains(., 'Weekly') or contains(., 'Bi-Weekly') or contains(., 'Every Four Weeks') or contains(., 'Semi-Monthly') or contains(., 'Monthly') or contains(., 'Quarterly') or contains(., 'Semi-Annually')]]"));
			frequency.isDisplayed();
			
			reportiumClient.stepStart("Edit the transfer");
			driver.findElement(AppiumBy.xpath("//*[@label='Edit this Transfer'] | //*[@content-desc='Edit this Transfer']")).click();
			
			driver.findElement(AppiumBy.xpath("//*[contains(@label, 'Transfer Amount') and contains(@label, 'clear')] | //*[contains(@content-desc, 'Transfer Amount') and contains(@content-desc, 'clear')]")).click();
			WebElement amount1 = driver.findElement(AppiumBy.xpath("//*[@label=\"$0.00\"]"));
			tapWithCoordinates1(getCenter(amount1).getX(), getCenter(amount1).getY());
			
			amount1.sendKeys(String.valueOf(editTransferAmount));
			driver.findElement(AppiumBy.xpath("//*[@label[contains(., 'Transfer From')]]")).click();
			
			driver.findElement(AppiumBy.xpath("//*[@label='Confirm']")).click();
			
			reportiumClient.stepStart("Validate fields after editing");
			
			WebElement confirmation = driver.findElement(AppiumBy.xpath("//*[contains(@label, 'Confirmation') or contains(@label, 'Reference') or contains(@value, 'Confirmation') or contains(@value, 'Reference')]"));
			confirmation.isDisplayed();
			
			reportiumClient.stepStart("Dismiss transfer receipt page");
			WebElement done = driver.findElement(AppiumBy.xpath("//*[@label='Done'] | //*[contains(@content-desc, 'Done')]"));
			if(done.isDisplayed())
			{
				done.click();
			}
			reportiumClient.stepStart("Find scheduled transfer in list");
			WebElement transfers = driver.findElement(AppiumBy.xpath("(//*[contains(@content-desc, 'from:') or contains(@content-desc, 'Transfer From')])[1] | (//*[contains(@label, 'from:') or contains(@label, 'Transfer From')])[1]"));
			if(transfers.isDisplayed())
			{
				String editedAmount = "//*[@*[contains(., '$0.%d')]]";
				String editedAmountXpath = String.format(editedAmount, editTransferAmount);
				
				WebElement amountConfirm = driver.findElement(AppiumBy.xpath(editedAmountXpath));
				if(amountConfirm.isDisplayed())
				{
					System.out.println("Found");
				}
				amountConfirm.click();
			}
			reportiumClient.stepStart("Cancel the transfer");
			driver.findElement(AppiumBy.xpath("//*[@label='Cancel this Transfer'] | //*[@content-desc='Cancel this Transfer']")).click();
			driver.findElement(AppiumBy.xpath("//*[@label=\"Yes\"]")).click();
			
			if(driver.findElement(AppiumBy.xpath("//*[@label=\"This transfer was successfully canceled!\"]")).isDisplayed())
			{
				driver.findElement(AppiumBy.xpath("//*[@label=\"OK\"]")).click();
			}
			Thread.sleep(10000);
			//scrollDowntoXPath("//*[contains(@label,\"Investments\")]//following-sibling::*[1]",driver);
			//driver.findElement(AppiumBy.xpath("//*[contains(@label,\"Investments\")]//following-sibling::*[1]")).click();
			
			//driver.findElement(AppiumBy.xpath("//*[@*[contains(., 'something went wrong') or contains(., 'Something Went Wrong') or contains(., 'something happened')]]"));
			//reportiumClient.testStop(TestResultFactory.createSuccess());
		}
		catch(Exception e)
		{
			//reportiumClient.testStop(TestResultFactory.createFailure(e.getMessage(), e));
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
		capabilities.setBundleId("com.huntington.test");
		capabilities.setApp("PUBLIC:HuntingtonApp/qa_6_51_140.ipa");
		HashMap<String, Object> perfectoOptions = new HashMap<String, Object>();
		//perfectoOptions.put("model", "iPhone.*");
		//perfectoOptions.put("model", "iPhone(-14 Pro|-15 Pro)");
		perfectoOptions.put("deviceName", "00008130-001854502140001C");
		//perfectoOptions.put("iOSResign",true);
		perfectoOptions.put("iOSResign", true);
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
	 
	 void verticalSwipe2()
		{
			Dimension size = driver.manage().window().getSize();
			 Point midPoint = new Point((int)(size.width * 0.5),(int)(size.height * 0.5));
			  int bottom = midPoint.y + (int)(midPoint.y * 0.75);
			  int top = midPoint.y - (int)(midPoint.y * 0.75);
			
			
			Point start  = new Point(midPoint.x, bottom);
			Point end  = new Point(midPoint.x, top);
			swipe(start,end,Duration.ofSeconds(2));
		}

	protected void swipe(Point start, Point end, Duration duration) {
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
	
	public void scrollDowntoXPath1(String xPath, AppiumDriver driver) {
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
				verticalSwipe2();
				if(count==15)
				{
					break;
				}
			}
		}
	}
	
	public ReportiumClient getReportiumClient() {
		return reportiumClient;
	}

	public void setReportiumClient(ReportiumClient reportiumClient) {
		this.reportiumClient = reportiumClient;
	}
	
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}

}


