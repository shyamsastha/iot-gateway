package schooldomain.studentname.connecteddevices.labs.module08;
/*
 * Jave script for TempActuatorSubscriberApp
 * @author: Shyama Sastha Krishnamoorthy Srinivasan 
 */

import schooldomain.studentname.connecteddevices.labs.module06.*;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

public class TempActuatorSubscriberApp extends Thread{
	// static
	private static TempActuatorSubscriberApp _App;
	private static ConfigUtil config;
	
	// parameters
	private MqttClientConnector _mqttClient;
	private String _userName = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_NAME_TOKEN_KEY);
	private String _authToken = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY);
	private String _pemFileName = ConfigConst.UBIDOTS_CERT_FILE;
	private String _host = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.BASE_URL_KEY);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		_App = new TempActuatorSubscriberApp();
		try {
			_App.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// constructors
	/**
	 * Default.
	 */

	public TempActuatorSubscriberApp() {
		super();
	}
	
	// public methods
	/**
	 * Connect to the MQTT client, and subscribe to the given topic
	 * unsubscribe the topic after 80s, then disconnect with the MQTT client
	 */
	public void start() {
		_mqttClient = new MqttClientConnector(_host, _userName, _pemFileName, _authToken);
		_mqttClient.connect();
		String topicName = "/devices/Temperature/TempActuator";
		_mqttClient.subscribeToTopic(topicName); // subscribe to the given topic
		//_mqttClient.subscribeToAll(); // subscribe all the topic
		try {
			sleep(10000);
			_mqttClient.disconnect();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		_mqttClient.disconnect();
	}
}