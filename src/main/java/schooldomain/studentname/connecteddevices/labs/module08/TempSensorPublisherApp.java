package schooldomain.studentname.connecteddevices.labs.module08;
/*
 * Jave script for TempSensorPublisherApp
 * @author: Shyama Sastha Krishnamoorthy Srinivasan 
 */

import schooldomain.studentname.connecteddevices.labs.module06.MqttClientConnector;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

import java.util.Random;
import java.util.logging.Logger;
import org.json.JSONObject;

public class TempSensorPublisherApp {
	// static
	private static final Logger logger = Logger.getLogger(MqttClientConnector.class.getName());
	private static TempSensorPublisherApp _App;
	private static ConfigUtil config = ConfigUtil.getInstance();
	private static boolean bool = config.loadConfig();
	
	// parameters
	private MqttClientConnector _mqttClient;
	private String _authToken = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY);
	private String _pemFileName = ConfigConst.UBIDOTS_CERT_FILE;
	private String _host = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.HOST_KEY);
	public static final String topicName = "/v1.6/devices/temperature/TempSensor";
	
	/**
	 * Default constructor
	 */
	public TempSensorPublisherApp() {
		super();
	}
	
	/*
	 * To generate random values
	 */
	public String genval(float min, float max) {
		Random rand = new Random();
		float random = min + rand.nextFloat() * (max - min);
		return Float.toString(random);
	}
	
	// public methods
	/**
	 * Connect to the MQTT client, and publish a test message to the given topic
	 */
	public void start(String topicName) {
		try {
			_mqttClient = new MqttClientConnector(_host, _pemFileName, _authToken);
			_mqttClient.connect();
			while (true) {
				_mqttClient.publishMessage(topicName, ConfigConst.DEFAULT_QOS_LEVEL, genval(15f, 25f).getBytes());
				Thread.sleep(30000);
			}
		} catch (InterruptedException e) {
			_mqttClient.disconnect();
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_App = new TempSensorPublisherApp();
		try {
			_App.start(topicName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
