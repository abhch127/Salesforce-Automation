package utilities;

import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;

public class JsonUtility {
	
	public LinkedHashMap<Object, Object> environmentSetUp(String appName, String environment, String ENVIRONMENT_FILE_PATH) {
		LinkedHashMap<Object, Object> envDetails=new LinkedHashMap<Object, Object>();
		try {
			JSONParser parser = new JSONParser();
			JSONObject config = (JSONObject) parser.parse(new FileReader(ENVIRONMENT_FILE_PATH));
			JSONObject appNames = (JSONObject) config.get("app_name");
			JSONObject environments = (JSONObject) appNames.get(appName.toLowerCase());
			Map<String, String> env_Details = (Map<String, String>) environments.get(environment.toLowerCase());
			Iterator it = env_Details.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				envDetails.put(pair.getKey().toString(), pair.getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return envDetails;
	}
	
	public LinkedHashMap<String, By> readObjectRepository(String appName, String OR_FILE_PATH) {
		LinkedHashMap<String, By> envObjects = new LinkedHashMap<String, By>();
		try {
			//Reading Json Config File    
				JSONParser parser = new JSONParser();
				JSONObject config = (JSONObject) parser.parse(new FileReader(OR_FILE_PATH));
				JSONObject appXpath = (JSONObject) config.get(appName.toLowerCase());
				Iterator it = appXpath.entrySet().iterator();
				while (it.hasNext()) {
				    Map.Entry pair = (Map.Entry)it.next();
				    if(appName.equalsIgnoreCase("TOS")||appName.equalsIgnoreCase("ContentStation") ||appName.equalsIgnoreCase("MyTime")){
				    	envObjects.put(pair.getKey().toString(), By.cssSelector(pair.getValue().toString()));
				    }else{
				    	envObjects.put(pair.getKey().toString(), By.xpath(pair.getValue().toString()));
				    }
				}   
		} catch (Exception e) {
			e.printStackTrace();
		}
		return envObjects;
	}
}
