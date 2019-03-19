package schooldomain.studentname.connecteddevices.labs.module07;
/*
 * Java script for handling temperature for CoAPServer
 * @author: Shyama Sastha Krishnamoorthy Srinivasan
 */

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

import schooldomain.studentname.connecteddevices.common.DataUtil;
import schooldomain.studentname.connecteddevices.common.SensorData;

public class TempResourceHandler extends CoapResource {
	//static
	private static final Logger _Logger = 
			Logger.getLogger(TempResourceHandler.class.getName());
	private SensorData sensorData = new SensorData(30.0,0.0,"time","Temperature");
	private DataUtil dataUtil = new DataUtil();
	private String payload = dataUtil.SensorDataToJson(sensorData);
	private SensorData payloadr = dataUtil.JsonToSensorData(payload, null);
	
	//constructors
	public TempResourceHandler() {
		super("Temperature");
	}

	/**
	 * @param name
	 */
	public TempResourceHandler(String name) {
		super(name);
	}

	/**
	 * @param name: Name of the resource
	 * @param visible: Visibility of the resource
	 */
	public TempResourceHandler(String name, boolean visible) {
		super(name, visible);
	}
	
	//public methods
	
	@Override
	public void handleGET(CoapExchange exchange) {
		String responseMsg =  "Response for " + super.getName() + ": \n jsonData: " + payload;
		exchange.respond(ResponseCode.CONTENT, responseMsg);
		_Logger.info("Handling GET:" + responseMsg);
		_Logger.info(exchange.getRequestCode().toString() + ": " + exchange.getRequestText());
	}
	
	@Override
	public void handlePOST(CoapExchange exchange) {
		String responseMsg =  "Response for " + super.getName() + ": \n SensorData: \n" + payloadr;
		exchange.respond(ResponseCode.CREATED, responseMsg);
		_Logger.info("Handling POST:" + responseMsg);
		_Logger.info(exchange.getRequestCode().toString() + ": " + exchange.getRequestText());
	}
	
	@Override
	public void handlePUT(CoapExchange exchange) {
		String responseMsg =  "Response for " + super.getName() + ": \n SensorData: \n" + payloadr;
		exchange.respond(ResponseCode.CHANGED, responseMsg);
		_Logger.info("Handling PUT:" + responseMsg);
		_Logger.info(exchange.getRequestCode().toString() + ": " + exchange.getRequestText());
	}
	
	@Override
	public void handleDELETE(CoapExchange exchange) {
		sensorData = null;
		String responseMsg =  "Response for" + super.getName() + "." + sensorData;
		exchange.respond(ResponseCode.DELETED, responseMsg);
		_Logger.info("Handling DELETE:" + responseMsg);
		_Logger.info(exchange.getRequestCode().toString() + ": " + exchange.getRequestText());
	}
}