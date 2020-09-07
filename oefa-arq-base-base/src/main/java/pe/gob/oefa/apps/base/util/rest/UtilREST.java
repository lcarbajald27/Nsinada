package pe.gob.oefa.apps.base.util.rest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UtilREST {

	public static String getREST(String uri) throws IOException {

		final int OK = 200;
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.connect();
		
		int responseCode = connection.getResponseCode();
		
		if(responseCode == OK){
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response.toString();
		}
			
		return null;
	}

	
	public static String getREST(String uri,String authString) throws IOException {

		final int OK = 200;
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestProperty("Authorization", "Basic " + authString);
		
		connection.connect();
		
		int responseCode = connection.getResponseCode();
		
		if(responseCode == OK){
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//System.out.println(response.toString());
			return response.toString();
		}
			
		return null;
	}
	public static String postREST(String uri,String rest,String authString) throws IOException {

		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Authorization", "Basic " + authString);
		OutputStream os = connection.getOutputStream();
		os.write(rest.getBytes());
		os.flush();

		if (connection.getResponseCode() != 200) {
			throw new RuntimeException("Error : HTTP error code : "	+ connection.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

		StringBuffer sb=new StringBuffer("");
	    String data="";
		while ((data = br.readLine()) != null) {
			sb.append(data);
		}
		connection.disconnect();
			
		return sb.toString();
	}
	

	public static String postRESTWithParams(String uri, String params) throws IOException {
		String encodedParams = URLEncoder.encode(params, "UTF-8");
		URL url = new URL(uri + "?" + encodedParams);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
//		EL URI YA VIENE CON LOS PARAMETROS CONCATENADOS
		  /*
		  OutputStream os = connection.getOutputStream();
		  params=URLEncoder.encode(params, "UTF-8");
		  os.write(params.getBytes());
		  //os.write(byteOut.toByteArray());
		  os.flush();
		connection.connect();
		*/
		int responseCode = connection.getResponseCode();
//		connection.setRequestProperty("Content-Type", "application/json");
//		OutputStream os = connection.getOutputStream();
//		os.write(rest.getBytes());
//		os.flush();

		if (responseCode != 200) {
			String message = connection.getResponseMessage();
			throw new RuntimeException("Error : HTTP error code : "	+ connection.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

		StringBuffer sb=new StringBuffer("");
	    String data="";
		while ((data = br.readLine()) != null) {
			sb.append(data);
		}
		connection.disconnect();
			
		return sb.toString();
	}
	public static String postRESTWithParams(String uri, String params,String authString) throws IOException {
		//String encodedParams = URLEncoder.encode(params, "UTF-8");
		URL url = new URL(uri + params);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Authorization", "Basic " + authString);
		//connection.setRequestProperty("Content-Length", "0");
		connection.setDoOutput(true);
		connection.setFixedLengthStreamingMode(0);
		connection.setRequestMethod("POST");

		
//		EL URI YA VIENE CON LOS PARAMETROS CONCATENADOS
		  /*
		  OutputStream os = connection.getOutputStream();
		  params=URLEncoder.encode(params, "UTF-8");
		  os.write(params.getBytes());
		  //os.write(byteOut.toByteArray());
		  os.flush();
		connection.connect();
		*/
		int responseCode = connection.getResponseCode();
//		connection.setRequestProperty("Content-Type", "application/json");
//		OutputStream os = connection.getOutputStream();
//		os.write(rest.getBytes());
//		os.flush();

		if (responseCode != 200) {
			String message = connection.getResponseMessage();
			throw new RuntimeException("Error : HTTP error code : "	+ connection.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

		StringBuffer sb=new StringBuffer("");
	    String data="";
		while ((data = br.readLine()) != null) {
			sb.append(data);
		}
		connection.disconnect();
			
		return sb.toString();
	}
	
	
	public static String postREST(String uri,String rest) throws IOException {

		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
//		connection.setFixedLengthStreamingMode(0);
		connection.setRequestMethod("POST");
		
		connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		//OutputStream os = connection.getOutputStream();
		//os.write(rest.getBytes());
		//os.flush();
		 Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
	        writer.write(rest);
	        writer.close();
		System.out.println("connection.getResponseCode() "+connection.getResponseCode() );
		if (connection.getResponseCode() != 200) {
			throw new RuntimeException("Error : HTTP error code : "	+ connection.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

		StringBuffer sb=new StringBuffer("");
	    String data="";
		while ((data = br.readLine()) != null) {
			sb.append(data);
		}
		connection.disconnect();
			
		return sb.toString();
	}
}
