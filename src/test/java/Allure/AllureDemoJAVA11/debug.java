package Allure.AllureDemoJAVA11;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class debug {

	public static void main(String[] args) throws Exception {
		String find = "D";
		// TODO Auto-generated method stub
		JSONParser jsonParser = new JSONParser();
		HashMap<String, ArrayList<String>> errors = new HashMap<String, ArrayList<String>>();
		try
		{
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
				if(value.contains(find))
				{
					System.out.println("Found in:" + key);
				}
			}



		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw(e);
		}

	}

}
