/**
 * Simple DataUtil class that is equivalent to the python DataUtil class
 * @author: Shyama Sastha Krishnamoorthy Srinivasan
 */

package schooldomain.studentname.connecteddevices.common;

import com.google.gson.Gson;

import schooldomain.studentname.connecteddevices.labs.module05.FileTransfer;

public class DataUtil {
	
	public String SensorDataToJson(SensorData sensordata)
	{
		String jsonSd;
		Gson gson = new Gson();
		jsonSd = gson.toJson(sensordata);
		return jsonSd;
	}
	
	public SensorData JsonToSensorData(String jsondata,String filename)
	{
		SensorData sensorData=null;
		if(filename==null)
		{
			
			Gson gson = new Gson();
			sensorData = gson.fromJson(jsondata, SensorData.class);
			return sensorData;
		}
		else
		{
			Gson gson = new Gson();
			String data = FileTransfer.FileReader(filename);
			sensorData = gson.fromJson(data, SensorData.class);
			return sensorData;
			
		}
			
	}

}