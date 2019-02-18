/*
 * Simple Java class that is used for file transfer between json file and SensorData class
 * @author: Shyama Sastha Krishnamoorthy Srinivasan 
 */

package schooldomain.studentname.connecteddevices.labs.module05;

import java.io.*;

public class FileTransfer {
	
	public static String FileReader(String file)
	{
		String json = new String();
		try
		{
			FileReader fileread = new FileReader(file);
			int fp;
			while((fp=fileread.read())!=-1)
			{
				json = json + (char)fp;
			}
			fileread.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json;
	}
	
	public static void fileWrite(String fileWriteEnable, String file, String json)
	{
		File jsonFile = new File(file);
		
		try {
			jsonFile.createNewFile();
			FileWriter writer = new FileWriter(jsonFile);
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}