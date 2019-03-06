/**
 * Simple DataUtil class that is equivalent to the python DataUtil class
 * @author: Shyama Sastha Krishnamoorthy Srinivasan
 */

package schooldomain.studentname.connecteddevices.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import schooldomain.studentname.connecteddevices.labs.module05.FileTransfer;

/*
 * Class that helps in parsing data from Json to object and vice-versa
 */
public class DataUtil {
	
	/*
	 * To get data from object to Json
	 */
	public String SensorDataToJson(SensorData sensordata)
	{
		String jsonsD;
		Gson gson = new Gson();
		jsonsD = gson.toJson(sensordata);
		return jsonsD;
	}
	
	/*
	 * To get object from Json to object
	 */
	public SensorData JsonToSensorData(String jsondata,String filename)
	{
		SensorData sensorData=null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		if(filename==null)
		{	
			sensorData = gson.fromJson(jsondata, SensorData.class);
			return sensorData;
		}
		else
		{
			String data = FileTransfer.fileRead(filename);
			sensorData = gson.fromJson(data, SensorData.class);
			return sensorData;	
		}
	}

}