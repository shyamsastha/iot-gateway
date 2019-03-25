package schooldomain.studentname.connecteddevices.labs.module06;
/**
 * Jave script to perform MQTT Client connector operations
 * @author: (modified by) Shyama Sastha Krishnamoorthy Srinivasan
 */

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

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
	private String authToken = ConfigConst.API_KEY;
	private MqttClient mqttClient;
	private SensorData sensorData;
	private static String mssg;
	private String pemFileName = ConfigConst.UBIDOTS_CERT_FILE;
	private boolean isSecureConn = true;
	
	public MqttClientConnector()
	{
		if(host!=null && host.trim().length()>0)
		{
			this.sensorData = new SensorData(30.0, 0.0, "Time","Temperature Sensor");
			this.clientID = mqttClient.generateClientId();
			logger.info("ClientID for connection: " + clientID);
			this.brokerAddr = protocol + "://" + host + ":" + port;
			logger.info("URL for connection: " + brokerAddr);
		}
	}
	
	/**
	* Constructor.
	*
	* @param host The name of the broker to connect.
	* @param userName The username for authorizing access to the broker.
	* @param pemFileName The name of the certificate file to use. If null / invalid, ignored.
	*/
	public MqttClientConnector(String _host, String _authToken, String _pemFileName)
	{
		super();
		if (_host != null && _host.trim().length() > 0) {
			host = _host;
		}
		if (_authToken != null && _authToken.trim().length() > 0) {
			authToken = _authToken;
		}
		if (_pemFileName != null) {
			File file = new File(_pemFileName);
			
			if (file.exists()) {
				protocol = ConfigConst.SECURE_MQTT_PROTOCOL;
				port = 8883;
				pemFileName = _pemFileName;
				isSecureConn = true;
				
				logger.info("PEM file valid. Using secure connection: " + _pemFileName);
				} else {
					logger.warning("PEM file invalid. Using insecure connection: " + pemFileName);
					}
			}
		clientID = MqttClient.generateClientId();
		brokerAddr = protocol + "://" + host + ":" + port;
		logger.info("Using URL for broker conn: " + brokerAddr);
		}
	
	/*
	 * To connect
	 */
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
			// Check to use secure connection
			if (isSecureConn) {
			initSecureConnection(ConnOp);
			}
			mqttClient.setCallback(this);
			mqttClient.connect(ConnOp);
			logger.info("Connected to broker: " + brokerAddr);
		}catch(MqttException ex)
		{
			logger.log(Level.SEVERE, "Failed to connect to broker" + brokerAddr, ex);
		}
		
		}
	}
	
	private void initSecureConnection(MqttConnectOptions connOpts)
	{
		try {
			logger.info("Configuring TLS...");
			
			SSLContext sslContext = SSLContext.getInstance("SSL");
			KeyStore keyStore = readCertificate();
			
			TrustManagerFactory trustManagerFactory =
					TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			trustManagerFactory.init(keyStore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			
			connOpts.setSocketFactory(sslContext.getSocketFactory());
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Failed to initialize secure MQTT connection.", e);
				}
		}
	
	private KeyStore readCertificate()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream fis = new FileInputStream(pemFileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		
		ks.load(null);
		
		while (bis.available() > 0) {
			Certificate cert = cf.generateCertificate(bis);
			ks.setCertificateEntry("adk_store" + bis.available(), cert);
			}
		return ks;
		}
	
	/*
	 * To disconnect
	 */
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
	
	/*
	 * To publish a message
	 */
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

	/*
	 * To subscribe to topics
	 */
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
	
	/*
	 * (non-Javadoc) To check for loss of connection
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.Throwable)
	 */
	public void connectionLost(Throwable cause) {
		
		logger.log(Level.WARNING, "Connection to broker lost. Retrying...", cause);
	}

	/*
	 * (non-Javadoc) To check if message has arrived
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String, org.eclipse.paho.client.mqttv3.MqttMessage)
	 */
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		MqttClientConnector.setMsg(message);
		logger.info("Message arrived: " + topic + "," + message.getId());
		System.out.print("Message recieved: " + topic + "," + message.getId() + message + "\n");
	
	}
	
	/*
	 * (non-Javadoc) To check for completetion of delivery
	 * @see org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho.client.mqttv3.IMqttDeliveryToken)
	 */
	public void deliveryComplete(IMqttDeliveryToken token) {
		
		logger.info("Message delivered: " + token.getMessageId() + "-" + token.getResponse());
		
	}
	
	/*
	 * To parse message as string
	 */
	public static String getMsg() {
		return mssg;
	}
	
	/*
	 * To parse the message from MQTT Broker 
	 */
	public static void setMsg(MqttMessage message) {
		MqttClientConnector.mssg = message.toString();
	}
}