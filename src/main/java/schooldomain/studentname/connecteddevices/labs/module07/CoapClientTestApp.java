package schooldomain.studentname.connecteddevices.labs.module07;
/*
 * Java script for CoAP Client Test application
 * @modified by: Shyama Sastha Krishnamoorthy Srinivasan
 */

import java.util.logging.Logger;

public class CoapClientTestApp 
{
	// static
	private static final Logger _Logger =
			Logger.getLogger(CoapClientTestApp.class.getName());
	private static CoapClientTestApp _App;
	
	/**
	 * * @param args
	 * */
	public static void main(String[] args)
	{
		_App = new CoapClientTestApp();
		try {
			_App.start();
			} catch (Exception e) {
				e.printStackTrace();
				}
		}
	
	// private var's
	private CoapClientConnector _coapClient;
	
	// constructors
	/**
	 *
	 */
	public CoapClientTestApp()
	{
		super();
		}
	//public methods
	/**
	 * Connect to the CoAP server
	 *
	 */
	public void start()
	{
		_coapClient = new CoapClientConnector();
		_coapClient.runTests("temp");
		}
	}