package com.usman.csudh.bank.core;

import java.io.InputStream;
import java.io.InputStreamReader;
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
		// This method is not used when readData() is overridden
		return null;
	}
	
	@Override
	public TreeMap<String, ExchangeRate> readData() throws Exception {
		TreeMap<String, ExchangeRate> exchangeRates = new TreeMap<>();

		try {
			URL url = new URL(this.restURL);
			InputStreamReader reader = new InputStreamReader(url.openStream());

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(reader);

			// Add USD with rate 1.0 since it's the base currency
			ExchangeRate usdRate = new ExchangeRate("USD", 1.0);
			exchangeRates.put("USD", usdRate);
			
			JSONObject rates = (JSONObject) jsonObject.get("rates");
			if (rates != null) {
				for (Object key : rates.keySet()) {
					String currencyCode = (String) key;
					Object rateObject = rates.get(key);

					// Handle conversion from Long to Double if necessary
					Double rate;
					if (rateObject instanceof Long) {
						rate = ((Long) rateObject).doubleValue();
					} else if (rateObject instanceof Double) {
						rate = (Double) rateObject;
					} else if (rateObject instanceof Number) {
						rate = ((Number) rateObject).doubleValue();
					} else {
						continue; // Skip invalid rate values
					}

					// Use currency code as name since API doesn't provide names
					String currencyName = currencyCode;
					ExchangeRate exchangeRate = new ExchangeRate(currencyName, rate);
					exchangeRates.put(currencyCode, exchangeRate);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return exchangeRates;
	}
}
