package schooldomain.studentname.connecteddevices.labs.module07;
/*
 * Java script for CoAP Server Test Application
 * @modified by: Shyama Sastha Krishnamoorthy Srinivasan
 */

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoapServerTestApp
{
	// static
	private static final Logger _Logger = Logger.getLogger(CoapServerTestApp.class.getName());
	private static CoapServerTestApp _App;
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		_App = new CoapServerTestApp();
		try {
			_App.start();
			} catch (Exception e) {
				_Logger.log(Level.SEVERE, "ERROR 101 ", e);
				e.printStackTrace();
				System.exit(1);
				}
		}
	
	// private var's
	private CoapServerConnector _coapServer;
	
	// constructors
	/**
	 *
	 **/
	public CoapServerTestApp()
	{
		super();
		}
	
	//public methods
	/**
	*
	*/
	public void start()
	{
		_coapServer = new CoapServerConnector();
		_coapServer.start();
		}
	}