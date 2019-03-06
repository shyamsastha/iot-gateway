package schooldomain.studentname.connecteddevices.labs.module06;
/**
 * Jave script to perform MQTT Client connector operations
 * @author: (modified by) Shyama Sastha Krishnamoorthy Srinivasan
 */

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import schooldomain.studentname.connecteddevices.common.SensorData;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;

public class MqttClientConnector implements MqttCallback{
	
	private static final Logger logger = Logger.getLogger(MqttClientConnector.class.getName());
	private String protocol = ConfigConst.DEFAULT_MQTT_PROTOCOL;
	private String host = ConfigConst.DEFAULT_MQTT_SERVER;
	private int port = ConfigConst.DEFAULT_MQTT_PORT;
	private String clientID;
	private String brokerAddr;
	private MqttClient mqttClient;
	private SensorData sensorData;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date date = new Date();
	
	public MqttClientConnector()
	{
		if(host!=null && host.trim().length()>0)
		{
			this.sensorData = new SensorData(30.0, 0.0, dateFormat.format(date),"name");
			this.clientID = mqttClient.generateClientId();
			logger.info("ClientID for connection: " + clientID);
			this.brokerAddr = protocol + "://" + host + ":" + port;
			logger.info("URL for connection: " + brokerAddr);
		}
	}
	
	public void connect()
	{
		if (mqttClient == null) {
			MemoryPersistence persistence = new MemoryPersistence();
		try
		{
			mqttClient = new MqttClient(brokerAddr, clientID, persistence);
			MqttConnectOptions ConnOp = new MqttConnectOptions();
			ConnOp.setCleanSession(true);
			logger.info("Connecting to broker: " + brokerAddr);
			mqttClient.setCallback(this);
			mqttClient.connect(ConnOp);
			logger.info("connected to broker: " + brokerAddr);
		}catch(MqttException ex)
		{
			logger.log(Level.SEVERE, "Failed to connect to broker" + brokerAddr, ex);
		}
		
		}
	}
	
	public void disconnect()
	{
		try {
			mqttClient.disconnect();
			logger.info("Disconnected from broker: " + brokerAddr);
		}catch(Exception ex)
		{
			logger.log(Level.SEVERE, "Failed to disconnect from broker: " + brokerAddr , ex);
		}
	}
	
	public boolean publishMessage(String topic, int qosLevel, byte[] payload) {
		boolean msgSent = false;
		try
		{
			logger.info("Publishing message to " + topic + ".\n payload: " + Arrays.toString(payload));
			MqttMessage msg = new MqttMessage(payload);
			msg.setQos(qosLevel);
			mqttClient.publish(topic, msg);
			logger.info("Message Published " + msg.getId());
			msgSent = true;
		}catch(Exception ex)
		{
			logger.log(Level.SEVERE, "Failed to publish Mqtt message " + ex.getMessage());
		}
		return msgSent;
	}


	public boolean subscribeToTopic(String topic)
	{
		boolean success = false;
		try {
			mqttClient.subscribe(topic);
			success = true;
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	
	public void connectionLost(Throwable cause) {
		
		logger.log(Level.WARNING, "Connection to broker lost. Retrying...", cause);
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		logger.info("Message arrived: " + topic + "," + message.getId());
		System.out.print("Message recieved: " + topic + "," + message.getId() + message);
	
	}
	
	public void deliveryComplete(IMqttDeliveryToken token) {
		
		logger.info("Message delivered: " + token.getMessageId() + "-" + token.getResponse());
		
	}
}