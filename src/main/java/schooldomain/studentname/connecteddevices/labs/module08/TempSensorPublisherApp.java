package schooldomain.studentname.connecteddevices.labs.module08;
/*
 * Jave script for TempSensorPublisherApp
 * @author: Shyama Sastha Krishnamoorthy Srinivasan 
 */

import schooldomain.studentname.connecteddevices.labs.module06.*;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

public class TempSensorPublisherApp {
	// static
	private static TempSensorPublisherApp _App;
	private static ConfigUtil config;
	
	// parameters
	private MqttClientConnector _mqttClient;
	private String _userName = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_NAME_TOKEN_KEY);
	private String _authToken = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY);
	private String _pemFileName = ConfigConst.UBIDOTS_CERT_FILE;
	private String _host = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.BASE_URL_KEY);
	
	/**
	 * Default constructor
	 */
	public TempSensorPublisherApp() {
		super();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_App = new TempSensorPublisherApp();
		try {
			_App.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// public methods
	/**
	 * Connect to the MQTT client, and publish a test message to the given topic
	 */
	public void start() {
		_mqttClient = new MqttClientConnector(_host, _userName, _pemFileName, _authToken);
		_mqttClient.connect();
		String topicName = "/devices/Temperature/TempSensor";
		String payload = "25";
		_mqttClient.publishMessage(topicName, 0, payload.getBytes());
		_mqttClient.disconnect();
	}
}
