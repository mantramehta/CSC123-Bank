package com.usman.csudh.bank.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeMap;

public abstract class CSVReader {
	public static final String FILE_SOURCE = "file";
	public static final String HTTP_SOURCE = "webservice";
	public static final String API_SOURCE = "rest";
	private static TreeMap<String, ExchangeRate> exchangeRates = new TreeMap<>();

	public static CSVReader newCSVReader(String type, String fileLoc, String url, String restURL) throws Exception {
		if (type.equalsIgnoreCase(FILE_SOURCE)) {
			return new InputSourceFile(fileLoc);
		} else if (type.equalsIgnoreCase(HTTP_SOURCE)) {
			return new HTTPSource(url);
		} else if (type.equalsIgnoreCase(API_SOURCE)) {
			return new APISource(restURL);
		} else {
			throw new Exception("Type " + type + " unknown!");
		}
	}

	public TreeMap<String, ExchangeRate> readData() throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream()));
			String line = br.readLine();
			while (line != null) {
				// split each line by comma
				String[] parts = line.split(",");
				String currencyCode = parts[0].trim();
				String currencyName = parts[1].trim();
				// parse the exchange rate value as a Double
				Double rate = Double.parseDouble(parts[2]);
				// create a new Currency object and put it into the TreeMap
				ExchangeRate currency = new ExchangeRate(currencyName, rate);
				exchangeRates.put(currencyCode, currency);
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return exchangeRates;
	}

	protected abstract InputStream getInputStream() throws Exception;
}
