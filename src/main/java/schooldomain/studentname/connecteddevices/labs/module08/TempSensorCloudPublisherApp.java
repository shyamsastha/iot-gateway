package schooldomain.studentname.connecteddevices.labs.module08;
/*
 * Jave script for TempSensorCloudPublisherApp
 * @author: Shyama Sastha Krishnamoorthy Srinivasan 
 */
import schooldomain.studentname.connecteddevices.labs.module06.MqttClientConnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import com.google.gson.Gson;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class TempSensorCloudPublisherApp {
	/* Final static variables */
	public static final String baseUri = "https://things.ubidots.com/api/v1.6/";
	private static ConfigUtil config = ConfigUtil.getInstance();
	private static boolean bool = config.loadConfig();

	/* Instance variables */
	private String baseUrl;
	private String apiKey;
	private String token;
	private Map<String, String> tokenHeader;
	private Map<String, String> apiKeyHeader;

	public static TempSensorCloudPublisherApp TSCPA;

	TempSensorCloudPublisherApp(String apiKey) {
		this(apiKey, baseUri);
	}

	TempSensorCloudPublisherApp(String apiKey, String baseUrl) {
		this.baseUrl = baseUrl;
		this.apiKey = apiKey;
		token = null;

		apiKeyHeader = new HashMap<>();
		apiKeyHeader.put("X-UBIDOTS-APIKEY", this.apiKey);

		initialize();
	}

	TempSensorCloudPublisherApp(String token, boolean isToken) {
		this.token = token;
		baseUrl = baseUri;
		apiKey = null;

		tokenHeader = new HashMap<>();
		tokenHeader.put("X-AUTH-TOKEN", token);
	}

	TempSensorCloudPublisherApp(String token, boolean isToken, String baseUrl) {
		this.token = token;
		this.baseUrl = baseUrl;
		apiKey = null;

		tokenHeader = new HashMap<>();
		tokenHeader.put("X-AUTH-TOKEN", token);
	}

	public void initialize() {
		recieveToken();
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	private void recieveToken() {
		Gson gson = new Gson();
		token = (String) gson.fromJson(postWithApiKey("auth/token"), Map.class).get("token");

		tokenHeader = new HashMap<>();
		tokenHeader.put("X-AUTH-TOKEN", token);
	}

	private String postWithApiKey(String path) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(apiKeyHeader);

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = baseUrl + path;

			HttpPost post = new HttpPost(url);

			for (String name : headers.keySet()) {
				post.setHeader(name, headers.get(name));
			}

			HttpResponse resp = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line;

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			response = result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	private Map<String, String> getCustomHeaders() {
		Map<String, String> customHeaders = new HashMap<>();

		customHeaders.put("content-type", "application/json");

		return customHeaders;
	}

	private Map<String, String> prepareHeaders(Map<String, String> arg) {
		Map<String, String> headers = new HashMap<>();

		headers.putAll(getCustomHeaders());
		headers.putAll(arg);

		return headers;
	}

	/**
	 * Created to mantain compatibility with previous version Perform a GET request
	 * on the API with a given path
	 * 
	 * @param path Path to append to the base URL
	 * @return Response from the API. The API sends back data encoded in JSON. In
	 *         the event of an error, will return null.
	 */
	String get(String path) {
		return get(path, null);
	}

	/**
	 * Perform a GET request on the API with a given path
	 * 
	 * @param path         Path to append to the base URL.
	 * @param customParams Custom parameters added by the user
	 * @return Response from the API. The API sends back data encoded in JSON. In
	 *         the event of an error, will return null.
	 */
	String get(String path, Map<String, String> customParams) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);

		if (customParams != null) {
			List<NameValuePair> params = new LinkedList<>();

			for (Map.Entry<String, String> entry : customParams.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

			}
			path.concat("?");
			path.concat(URLEncodedUtils.format(params, "utf8"));
		}

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = baseUrl + path;

			HttpGet get = new HttpGet(url);

			for (String name : headers.keySet()) {
				get.setHeader(name, headers.get(name));
			}

			HttpResponse resp = client.execute(get);

			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line;

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			response = result.toString();

			// We just need the result field
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(response).getAsJsonObject();
			if (jsonObject.has("results")) {
				response = jsonObject.get("results").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Perform a POST request on the API with a given path
	 * 
	 * @param path Path to append to the base URL.
	 * @param json String of data encoded in JSON to send to the server.
	 * @return Response from the API. The API sends back data encoded in JSON. In
	 *         the event of an error, will return null.
	 */
	String post(String path, String json) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = baseUrl + path;
			HttpPost post = new HttpPost(url);
			for (String name : headers.keySet()) {
				post.setHeader(name, headers.get(name));
			}
			post.setEntity(new StringEntity(json));
			HttpResponse resp = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line;

			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			response = result.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Perform a DELETE request on the API with a given path
	 * 
	 * @param path Path to append to the base URL.
	 * @return Response from the API. The API sends back data encoded in JSON. In
	 *         the event of an error, will return null.
	 */
	String delete(String path) {
		String response = null; // return variable
		Map<String, String> headers = prepareHeaders(tokenHeader);

		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			String url = baseUrl + path;
			HttpDelete delete = new HttpDelete(url);

			for (String name : headers.keySet()) {
				delete.setHeader(name, headers.get(name));
			}
			
			HttpResponse resp = client.execute(delete);
			if (resp.getEntity() == null) {
				response = "";
			} else {
				BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line;

				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
				response = result.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public String genval(float min, float max) {
		Random rand = new Random();
		float random = min + rand.nextFloat() * (max - min);
		System.out.println("\nTemperature : " + Float.toString(random));
		return Float.toString(random);
	}

	public static void main(String[] args) {
		final String topicName = "devices/Temperature";
		String _authToken = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY);
		float min = 0f;
		float max = 45f;

		try {
			TSCPA = new TempSensorCloudPublisherApp(_authToken, true);
			JSONObject Jobj = new JSONObject();
			while (true) {
				Jobj.put("TempSensor", TSCPA.genval(min, max));
				String json = Jobj.toString();
				TSCPA.post(topicName, json);
				Thread.sleep(30000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
