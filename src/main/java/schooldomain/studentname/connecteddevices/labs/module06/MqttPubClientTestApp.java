package schooldomain.studentname.connecteddevices.labs.module06;
/*
 * Jave script for MQTT Subscriber Client
 * @author: (modified by) Shyama Sastha Krishnamoorthy Srinivasan 
 */

import java.util.logging.Logger;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

public class MqttPubClientTestApp {
	
	private static final Logger logger = Logger.getLogger(MqttPubClientTestApp.class.getName());
	private static MqttPubClientTestApp SubApp;
	private MqttClientConnector mqttClient;
	public SensorData sensorData;
	public DataUtil data;

	public MqttPubClientTestApp() {
		super();
	}
	
	/*Function to create Json data
	 * @param sensorData: SensorData instance
	 * @return: Json object
	 */
	public String createJSON(SensorData sensorData) {
		data = new DataUtil();
		SensorData sd = setSensorData(sensorData);
		logger.info("SensorData before converting into Json\n");
		System.out.println(data+"\n");
		String Jobj = data.SensorDataToJson(sd);
		return Jobj;
	}
	
	/*
	 * Function to intialize or update the SensorData instance
	 * @param sensorData: variable of SensorData
	 * @return: Update to SensorData
	 */
	public SensorData setSensorData(SensorData sensorData) {
		sensorData.setName("Temperature Sensor");
		sensorData.getTime();
		sensorData.updateValue(15);
		return sensorData;
	}
	
	/*
	 * Function to initialize publish method
	 * @param topic: Topic of the message
	 */
	public void start(String topicName)
	{
		mqttClient = new MqttClientConnector();
		sensorData = new SensorData(30.0,0.0,"name","Temperature");
		mqttClient.connect();
		String sensor = createJSON(sensorData);
		logger.info("SensorData after converting into Json\n");
		System.out.println(sensor+"\n");
		mqttClient.publishMessage(topicName, 2 , sensor.getBytes());
	}
	
	public static void main(String[] args) {

		SubApp = new MqttPubClientTestApp();
		String topic = "Temperature Sensor";
		try {
			SubApp.start(topic);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}