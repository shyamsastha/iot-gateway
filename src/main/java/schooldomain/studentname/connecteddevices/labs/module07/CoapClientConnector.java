package schooldomain.studentname.connecteddevices.labs.module07;
/*
 * Java script for CoAP Client Connecter
 * @modified by: Shyama Sastha Krishnamoorthy Srinivasan
 */

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapClient;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;

public class CoapClientConnector
{
	// static
	private static final Logger _Logger = Logger.getLogger(CoapClientConnector.class.getName());
	
	// params
	private String _protocol;
	private String _host;
	private int _port;
	private String _serverAddr;
	
	private CoapClient _clientConn;
	private boolean _isInitialized;
	
	// constructors
	/**
	 *Default.
	 */
	public CoapClientConnector()
	{
		this(ConfigConst.DEFAULT_COAP_SERVER, false);
		}
	
	/**
	 * Constructor.
	 *
	 * @param host
	 */
	public CoapClientConnector(String host, boolean isSecure)
	{
		super();
		if (isSecure) {
			_protocol = ConfigConst.SECURE_COAP_PROTOCOL;
			_port = ConfigConst.SECURE_COAP_PORT;
			}
		else {
			_protocol = ConfigConst.DEFAULT_COAP_PROTOCOL;
			_port = ConfigConst.DEFAULT_COAP_PORT;
			}
		if (host != null && host.trim().length() > 0) {
			_host = host;
			}
		else {
			_host = ConfigConst.DEFAULT_COAP_SERVER;
			}
		
		// NOTE: URL does not have a protocol handler for "coap",
		// so we need to construct the URL manually
		_serverAddr = _protocol + "://" + _host + ":" + _port;
		_Logger.info("Using URL for server conn: " + _serverAddr);
		}
	
	// public methods
	public void runTests(String resourceName)
	{
		try {
			_isInitialized = false;
			initClient(resourceName);
			_Logger.info("Current URI: " + getCurrentUri());
			String payload = "Sample payload.";
			pingServer();
			discoverResources();
			sendGetRequest();
			sendGetRequest(true);
			sendPostRequest(payload, false);
			sendPostRequest(payload, true);
			sendPutRequest(payload, false);
			sendPutRequest(payload, true);
			sendDeleteRequest();
			}
		catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to issue request to CoAP server.", e);
			}
		}
	/**
	* Returns the CoAP client URI (if set, otherwise returns the _serverAddr, or
	* null).
	*
	* @return String
	*/
	public String getCurrentUri()
	{
		return (_clientConn != null ? _clientConn.getURI() : _serverAddr);
		}
	
	public void discoverResources()
	{
		_Logger.info("Issuing discover...");
		initClient();
		Set<WebLink> wlSet = _clientConn.discover();
		if (wlSet != null) {
			for (WebLink wl : wlSet) {
				_Logger.info(" --> WebLink: " + wl.getURI());
				}
			}
		}
	
	public void pingServer()
	{
		_Logger.info("Sending ping...");
		initClient();
		// TODO: you must implement this yourself (hint: it’s only one line of code)
		}
	
	public void sendDeleteRequest()
	{
		initClient();
		handleDeleteRequest();
		}
	
	public void sendDeleteRequest(String resourceName)
	{
		_isInitialized = false;
		initClient(resourceName);
		handleDeleteRequest();
		}
	
	public void sendGetRequest()
	{
		initClient();
		handleGetRequest(false);
		}
	
	public void sendGetRequest(String resourceName)
	{
		_isInitialized = false;
		initClient(resourceName);
		handleGetRequest(false);
		}
	
	public void sendGetRequest(boolean useNON)
	{
		initClient();
		handleGetRequest(useNON);
		}
	
	public void sendGetRequest(String resourceName, boolean useNON)
	{
		_isInitialized = false;
		initClient(resourceName);
		sendGetRequest(useNON);
		}
	
	public void sendPostRequest(String payload, boolean useCON)
	{
		initClient();
		handlePostRequest(payload, useCON);
		}
	
	public void sendPostRequest(String resourceName, String payload, boolean useCON)
	{
		_isInitialized = false;
		initClient(resourceName);
		handlePostRequest(payload, useCON);
		}
	
	public void sendPutRequest(String payload, boolean useCON)
	{
		initClient();
		handlePutRequest(payload, useCON);
		}
	
	public void sendPutRequest(String resourceName, String payload, boolean useCON)
	{
		_isInitialized = false;
		initClient(resourceName);
		handlePutRequest(payload, useCON);
		}
	
	public void registerObserver(boolean enableWait)
	{
		_Logger.info("Registering observer...");
		CoapClientObserverHandler handler = null;
		if (enableWait) {
			_clientConn.observeAndWait(handler);
			}
		else {
			_clientConn.observe(handler);
			}
		}
	
	// private methods
	private void handleDeleteRequest()
	{
		// NOTE: you must implement this yourself
		}
	
	private void handleGetRequest(boolean useNON)
	{
		if (useNON) {
			_clientConn.useNONs();
			}
		// NOTE: you must implement the rest of this yourself
		}
	
	private void handlePutRequest(String payload, boolean useCON)
	{
		_Logger.info("Sending PUT...");
		CoapResponse response = null;
		if (useCON) {
			_clientConn.useCONs().useEarlyNegotiation(32).get();
			}
		response = _clientConn.put(payload, MediaTypeRegistry.TEXT_PLAIN);
		if (response != null) {
			_Logger.info("Response: " + response.isSuccess() + " - " + response.getOptions() + " - " + response.getCode());
			}
		else {
			_Logger.warning("No response received.");
			}
		}
	
	private void handlePostRequest(String payload, boolean useCON)
	{
		_Logger.info("Sending POST...");
		CoapResponse response = null;
		if (useCON) {
			_clientConn.useCONs().useEarlyNegotiation(32).get();
			}
		response = _clientConn.post(payload, MediaTypeRegistry.TEXT_PLAIN);
		if (response != null) {
			_Logger.info("Response: " + response.isSuccess() + " - " + response.getOptions() + " - " + response.getCode());
			}
		else {
			_Logger.warning("No response received.");
			}
		}
		
	private void initClient()
	{
		initClient(null);
		}
	
	private void initClient(String resourceName)
	{
		if (_isInitialized) {
			return;
			}
		if (_clientConn != null) {
			_clientConn.shutdown();
			_clientConn = null;
			}
		try {
			if (resourceName != null) {
				_serverAddr += "/" + resourceName;
				}
			_clientConn = new CoapClient(_serverAddr);
			_Logger.info("Created client connection to server / resource: " + _serverAddr);
			} catch (Exception e) {
				_Logger.log(Level.SEVERE, "Failed to connect to broker: " + getCurrentUri(), e);
				}
		}
	}