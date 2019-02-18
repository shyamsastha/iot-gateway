/**
 * A simple temperature management application
 * @author: (modified by) Shyama Sastha Krishnamoorthy Srinivasan
 */
package schooldomain.studentname.connecteddevices.labs.module05;
import java.io.FileNotFoundException;
import com.google.gson.Gson;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TempManagementApp {

	public static void app()
	{
		DataUtil data = new DataUtil();
		SensorData sensorData = data.JsonToSensorData(null, "/home/shyam/scratch/iot-device/apps/labs/module05/sensedata.txt");
		System.out.println(sensorData);
	}

}