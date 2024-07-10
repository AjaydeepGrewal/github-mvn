package Allure.AllureDemoJAVA11;

import java.io.File;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("This is Epic")
@Feature("This is feature")
@Story("This is Story")
public class NewTest{
	
 @BeforeTest
 public void setup() throws Exception
 {
	 System.out.println("Before Test");
 }
 
 
 @AfterTest
 public void teardown() throws Exception
 {
	 System.out.println("After Test");
	 //throw new Exception("Error");
 }
 
 @Step("Step 1")
 public void step1() {
     System.out.println("Inside step");
 }
 
 @Epic("This is Epic")
 @Feature("This is feature1234")
 @Story("This is Story")
 @Test(groups = "allure")
  public void f() throws Exception 
  {
	  System.out.println("Test");
	  //throw new Exception("Error");
	  /*DefaultHttpClient httpclient = new DefaultHttpClient();
	  HttpPost httpPost = new HttpPost("https://rbc-ca.app.perfectomobile.com/repository/api/v1/artifacts");
	  httpPost.addHeader("Perfecto-Authorization:","eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxZTNlM2Y1Ny04YjI4LTQyNjgtOTdhMi1mYTc0OGM5YTgwNjcifQ.eyJpYXQiOjE2NjI5OTEyNDEsImp0aSI6IjgzYTFkM2Y4LTM4ZGEtNGE2Yy05MTAwLWRhMjExYzZhYzNjOSIsImlzcyI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvcmJjLWNhLXBlcmZlY3RvbW9iaWxlLWNvbSIsImF1ZCI6Imh0dHBzOi8vYXV0aC5wZXJmZWN0b21vYmlsZS5jb20vYXV0aC9yZWFsbXMvcmJjLWNhLXBlcmZlY3RvbW9iaWxlLWNvbSIsInN1YiI6Ijk2MDZkMmMwLTdjOWItNGE4Zi04ZWRiLWI2NGRlMWY2ODZkYiIsInR5cCI6Ik9mZmxpbmUiLCJhenAiOiJvZmZsaW5lLXRva2VuLWdlbmVyYXRvciIsIm5vbmNlIjoiMjc0MGFkOTItMzU1NC00MzMwLTk2OWItMmM1MWJkOWIyZWMzIiwic2Vzc2lvbl9zdGF0ZSI6ImJmYjRiZjc2LTMwYTItNDk0ZC1iNjk2LTdmM2IyMDExMGUwZCIsInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIn0.Pai9xZ3MXTmrio1T9uL-H8OjI32KKs16Ih3wPj0Ag8A");
	  httpPost.addHeader("Content-Type", "multipart/form-data");
	  FileBody uploadFilePart = new FileBody(new File("/Users/agrewal/Desktop/CustomInstrumentation/Chat.png"));
	  MultipartEntity reqEntity = new MultipartEntity();
	  StringBody artifact = new StringBody("PRIVATE:Test_curl.png");
	  StringBody mimeType = new StringBody("multipart/form-data");

	  reqEntity.addPart("upload-file", uploadFilePart);
	  reqEntity.addPart("artifactLocator", artifact);
	  
	  reqEntity.addPart("mimeType", mimeType);
	  httpPost.setEntity(reqEntity);

	  org.apache.http.HttpResponse response = httpclient.execute(httpPost);
	  System.out.println(response);*/
  }
}
