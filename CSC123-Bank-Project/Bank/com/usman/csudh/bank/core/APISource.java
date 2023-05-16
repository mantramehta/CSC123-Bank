package com.usman.csudh.bank.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class APISource extends CSVReader{
	String restURL;

	public APISource(String restURL) {
		super();
		this.restURL = restURL;
	}
	
	@Override
	protected InputStream getInputStream() throws Exception {
		  TreeMap<String, Double> exchangeRates1 = new TreeMap<>();

	        try {
	            URL url = new URL(this.restURL);
	            InputStreamReader reader = new InputStreamReader(url.openStream());

	            JSONParser parser = new JSONParser();
	            JSONObject jsonObject = (JSONObject) parser.parse(reader);

	            JSONObject rates = (JSONObject) jsonObject.get("rates");
	            for (Object key : rates.keySet()) {
	                String countryCode = (String) key;
	                Object rateObject = rates.get(key);

	                // Handle conversion from Long to Double if necessary
	                Double rate;
	                if (rateObject instanceof Long) {
	                    rate = ((Long) rateObject).doubleValue();
	                } else {
	                    rate = (Double) rateObject;
	                }

	                exchangeRates1.put(countryCode, rate);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
	        objectStream.writeObject(exchangeRates1);
	        objectStream.flush();
	        return new ByteArrayInputStream(byteStream.toByteArray());
	}
}
