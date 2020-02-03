/**
 * 
 */
package com.rahukalaya.common;

/**
 * @author tharindu
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class JSONParser {

	// constructor
	public JSONParser() {

	}

	// function get json from url
	// by making HTTP POST or GET mehtod

	public String makePostRequest(String urlString, HashMap<String, String> params) throws IOException {
		InputStream stream = null;
		StringBuffer output = new StringBuffer("");
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(15000);
		connection.setConnectTimeout(15000);
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);

		OutputStream os = connection.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		writer.write(getPostDataString(params));
		writer.flush();
		writer.close();
		os.close();

		connection.connect();

		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			stream = connection.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
			String s = "";
			while ((s = buffer.readLine()) != null)
				output.append(s);
		}
		return output.toString();
	}

	private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}

		return result.toString();
	}

	public static String getResponseFromUrl(String urlString) throws Exception{
		StringBuffer output = new StringBuffer("");
		InputStream stream = null;
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				stream = httpConnection.getInputStream();

				BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
				String s = "";
				while ((s = buffer.readLine()) != null)
					output.append(s);
			}
			

		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return output.toString();

	}
}