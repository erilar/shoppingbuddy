package no.andsim.sbuddy.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import no.andsim.sbuddy.model.JSONProductEnvelope;
import no.andsim.sbuddy.model.JSONProductListEnvelope;
import no.andsim.sbuddy.model.Product;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class ProductWSClientRS{

	private static final String className = ProductWSClientRS.class.getSimpleName();
	
	private String baseUrl = "http://vareservice.herokuapp.com/ProductServiceRS/products";
	private String productUrl = "/product";
	
	private static ProductWSClientRS client;
	private final HttpClient httpclient = new DefaultHttpClient();
	private final Gson gson = new Gson();

	private ProductWSClientRS() {
	}

	public static ProductWSClientRS getInstance() {
		if (client == null) {
			client = new ProductWSClientRS();
		}
		return client;
	}

	private String getBaseUrl() {

		// Prepare a request object
		HttpGet httpget = new HttpGet(baseUrl);

		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			return convertResponseToString(entity);

		} catch (ClientProtocolException e) {
			Log.e(className, e.getMessage());
		} catch (IOException e) {
			Log.e(className, e.getMessage());
		}
		return null;
	}
	
	private boolean putProduct(String json){
		HttpPut httpPut = new HttpPut(baseUrl+productUrl);
		try{
			StringEntity entity = new StringEntity(json, "UTF-8");
		    entity.setContentType("application/json");
		    httpPut.setEntity(entity);
			httpclient.execute(httpPut);
			return true;
		} catch (ClientProtocolException e) {
			Log.e(className, e.getMessage());
		} catch (IOException e) {
			Log.e(className, e.getMessage());
		}
		return false;
	}
	

	private String convertResponseToString(HttpEntity entity) throws IOException {
		if (entity != null) {
			InputStream instream = entity.getContent();
			String result = convertStreamToString(instream);
			instream.close();
			return result;
		}
		return null;
	}

	public List<Product> getProductsFromService() {
		
		String jsonString = getBaseUrl();
		JSONProductListEnvelope jsonEnvelope = null;

		try {
			jsonEnvelope = gson.fromJson(jsonString, JSONProductListEnvelope.class);
		} catch (JsonParseException e) {
			Log.w(className, e.getMessage());
		}
		if (jsonEnvelope != null)
			return jsonEnvelope.getProductList().getProduct();
		return null;
	}
	
	public boolean putProductOnServer(Product product){
		try {
			String json =  gson.toJson(wrapProduct(product));
			return putProduct(json);
			
		} catch (JsonParseException e) {
			Log.w(className, e.getMessage());
			return false;
		}
	}

	private JSONProductEnvelope wrapProduct(Product product) {
		return new JSONProductEnvelope(product);
	}
	

	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
