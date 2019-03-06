/*
/ * Simple Acutator data class that is equivalent to the python ActuatorData class
 * @author: Shyama Sastha Krishnamoorthy Srinivasan
 */

package schooldomain.studentname.connecteddevices.common;

public class ActuatorData {

	// Command, Status and Error case type definition
	public static final int COMMAND_OFF = 0;
	public static final int COMMAND_ON = 1;
	public static final int COMMAND_SET = 2;
	public static final int COMMAND_RESET = 3;
	public static final int STATUS_IDLE = 0;
	public static final int STATUS_ACTIVE = 1;
	public static final int ERROR_OK = 0;
	public static final int ERROR_COMMAND_FAILED = 1;
	public static final int ERROR_NON_RESPONSIBLE = -1;

	private String name = "Actuator Data";
	private String timeStamp = null;
	private boolean hasError = false;
	private int command = 0;
	private int errCode = 0;
	private int statusCode = 0;
	private String stateData = null;
	private float val = 0.0f;

	
	public ActuatorData(String timeStamp, String name) {
		this.setTime(timeStamp);
		this.name = name;
	}
	
	/**
	 * To get name
	 */
	public String getName() {
		return name;
	}

	/**
	 * To set name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * To get the date and time
	 */
	public String getTime() {
		return timeStamp;
	}

	/**
	 * To set the date and time
	 */
	public void setTime(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * To check error
	 */
	public boolean isHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}

	/**
	 * To get the command type
	 */
	public int getCommand() {
		return command;
	}

	/**
	 * To set the command type
	 */
	public void setCommand(int command) {
		this.command = command;
	}

	/**
	 *To return error code type
	 */
	public int getErrCode() {
		return errCode;
	}

	/**
	 * To set error code type
	 */
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	/**
	 *To get the status type
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * To set the status type
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * To get the state of the Actuator
	 */
	public String getStateData() {
		return stateData;
	}

	/**
	 * To set the state of the Actuator
	 */
	public void setStateData(String stateData) {
		this.stateData = stateData;
	}

	/**
	 * To get the ActuatorData value
	 */
	public float getVal() {
		return val;
	}

	/**
	 * To set the ActuatorData value
	 */
	public void setVal(float val) {
		this.val = val;
	}

	/**
	 * To update the ActuatorData
	 */
	public void updateData(ActuatorData data) {
		this.command = data.getCommand();
		this.statusCode = data.getStatusCode();
		this.errCode = data.getErrCode();
		this.stateData = data.getStateData();
		this.val = data.getVal();
	}

	/**
	 * To print out the ActuatorData
	 */
	public String toString() {
		return "Name: " + name + "\n"
				+ "time: " + timeStamp + "\n"
				+ "Command: " + command + "\n"
				+ "Status Code: " + statusCode + "\n"
				+ "Error Code: " + errCode + "\n"
				+ "State Data: " + stateData + "\n"
				+ "Value: " + val + "\n";
	}

}