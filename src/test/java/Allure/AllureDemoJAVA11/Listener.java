package Allure.AllureDemoJAVA11;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.ITestResult;

import com.perfecto.reportium.test.result.TestResultFactory;

import io.qameta.allure.testng.AllureTestNg;

public class Listener extends AllureTestNg{
	
	@Override
	public void onTestFailure(ITestResult result)
	{
		if (result.getStatus() == ITestResult.FAILURE) {
            // Retrieve the error message from the throwable
            Throwable throwable = result.getThrowable();
            if (throwable != null) {
            	String customError = null ;
                String errorMessage = throwable.getMessage();
                System.out.println("Test failed with error: " + errorMessage);
                try {
					 customError = CustomErrorDemo.getCustomError(errorMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                CustomErrorDemo.reportiumClient.testStop(TestResultFactory.createFailure(errorMessage, throwable, customError));
            } else {
                System.out.println("Test failed with no error message.");
            }
            
        }
	}

}
