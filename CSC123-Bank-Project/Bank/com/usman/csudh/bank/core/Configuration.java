package com.usman.csudh.bank.core;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Configuration{
	
	private static Configuration self=new Configuration();
	
	private Map<String, String> configMap = new HashMap<>();
	
	public Configuration() {
		 try (BufferedReader br = new BufferedReader(new FileReader("config.txt"))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            	//System.out.println(line);
	                String[] parts = line.split("=", 2);
	                if (parts.length == 2) {
	                    String key = parts[0].trim();
	                    String value = parts[1].trim();
	                    configMap.put(key, value);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		//Read file
		//Parse file
		//Store in key value pairs 
		//System.out.println(configMap);
	}

	public static Configuration getInstance() {
		return self;
	}
	
	public String getValue(String key) {
		return this.configMap.get(key);
	}

	public boolean useCurrencies() {
		String x = configMap.get("support.currencies");
		if(x.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}
}
