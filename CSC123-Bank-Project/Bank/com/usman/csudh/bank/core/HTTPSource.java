package com.usman.csudh.bank.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class HTTPSource extends CSVReader {
	String url;

	public HTTPSource(String url) {
		super();
		this.url = url;
	}

	@Override
	protected InputStream getInputStream() throws Exception {
		// create an instance of HttpClient
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url)).build();
		// send the request and process the response
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String body = response.body();
		InputStream is = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
		return is;
	}

}
