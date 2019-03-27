package schooldomain.studentname.connecteddevices.labs.module08;
/*
 * Jave script for TempActuatorSubscriberApp
 * @author: Shyama Sastha Krishnamoorthy Srinivasan 
 */

import schooldomain.studentname.connecteddevices.labs.module06.MqttClientConnector;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

public class TempActuatorSubscriberApp extends Thread{
	// static
	private static TempActuatorSubscriberApp _App;
	private static ConfigUtil config = ConfigUtil.getInstance();
	private static boolean bool = config.loadConfig();
	
	// parameters
	private MqttClientConnector _mqttClient;
	private String _authToken = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY);
	private String _pemFileName = ConfigConst.UBIDOTS_CERT_FILE;
	private String _host = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.BASE_URL_KEY);
	public static final String topicName = "/v1.6/devices/temperature/tempactuator/lv";
	
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
	public void start(String topicName) {
		try {
			_mqttClient = new MqttClientConnector(_host, _pemFileName, _authToken);
			_mqttClient.connect();
			while(true) {
				_mqttClient.subscribeToTopic(topicName); // subscribe to the given topic
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
		_App = new TempActuatorSubscriberApp();
		try {
			_App.start(topicName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}