package Allure.AllureDemoJAVA11;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.testng.ITestResult;
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
import io.qameta.allure.testng.AllureTestNg;

public class CustomErrorDemo {
	IOSDriver driver = null;
	String host = "rbc-ca.perfectomobile.com"; 
	static ReportiumClient reportiumClient ;

	@BeforeTest(alwaysRun=true)
	public void setup() throws Exception
	{
		System.out.println("Before Test iOS");
		driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilitiesIOSReal());
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withContextTags("PS POC")
				.withWebDriver(driver)
				.build();
		reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}

	@AfterTest(alwaysRun=true)
	public void teardown() throws Exception
	{
				
		driver.quit();
	}

	@Test
	public void Login() throws Exception 
	{	
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			reportiumClient.testStart("Custom Error Demo", new TestContext.Builder()
					.withTestExecutionTags("PS POC", "IOS Login")
					.build());
			// Submit for login
			// Home page screen to load.
			throw(new Exception("Login service down at the backend"));
			
			//reportiumClient.testStop(TestResultFactory.createSuccess());
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
		capabilities.setBundleId("com.rbc.mobile");
		capabilities.setApp("GROUP:ipa/release/6.41/RBCMobile.v6_41.j2129.b0.eIST0.83556b0835.ipa");
		HashMap<String, Object> perfectoOptions = new HashMap<String, Object>();
		perfectoOptions.put("deviceName", "00008110-001664EE3E98801E");
		perfectoOptions.put("iOSResign",true);
		perfectoOptions.put("securityToken", "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxZTNlM2Y1Ny04YjI4LTQyNjgtOTdhMi1mYTc0OGM5YTgwNjcifQ.eyJpYXQiOjE2NjI5OTEyNDEsImp0aSI6IjgzYTFkM2Y4LTM4ZGEtNGE2Yy05MTAwLWRhMjExYzZhYzNjOSIsImlzcyI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvcmJjLWNhLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvcmJjLWNhLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6Ijk2MDZkMmMwLTdjOWItNGE4Zi04ZWRiLWI2NGRlMWY2ODZkYiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiMjc0MGFkOTItMzU1NC00MzMwLTk2OWItMmM1MWJkOWIyZWMzIiwic2Vzc2lvbl9zdGF0ZSI6ImJmYjRiZjc2LTMwYTItNDk0ZC1iNjk2LTdmM2IyMDExMGUwZCIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIn0.Pai9xZ3MXTmrio1T9uL-H8OjI32KKs16Ih3wPj0Ag8A");
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
	
	public static String getCustomError(String StackTrace) throws IOException, ParseException
	{
		// TODO Auto-generated method stub
		JSONParser jsonParser = new JSONParser();
		HashMap<String, ArrayList<String>> errors = new HashMap<String, ArrayList<String>>();
			FileReader reader = new FileReader("/Users/agrewal/eclipse-workspace/AllureDemoJAVA11/src/test/java/Allure/AllureDemoJAVA11/CustomError.json");
			Object obj = jsonParser.parse(reader);
			JSONArray readList = (JSONArray) obj;
			System.out.println(readList.size());
			for(int i=0; i < readList.size();i++)
			{
				JSONObject o = (JSONObject) readList.get(i);
				errors.put((String)o.get("CustomError"),(ArrayList<String>)o.get("StackTraceErrors"));
				System.out.println("Here");
			}


			for (Map.Entry<String, ArrayList<String>>  entry : errors.entrySet()) {
				String key = entry.getKey();
				ArrayList<String> value = entry.getValue();
				if(value.contains(StackTrace))
				{
					System.out.println("Found in:" + key);
					return key;
				}
			}
			return null;

	}


}
