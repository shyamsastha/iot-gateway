/**
 * Copyright (c) 2018-2019. Andrew D. King. All Rights Reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.labbenchstudios.edu.connecteddevices.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * A utility class that permits the loading, and storage, of
 * a given certificate that adheres to X.509 format.
 * 
 * @author aking
 */
public class CertManagementUtil
{
	// static
	
	private static final Logger             _Logger   =
		Logger.getLogger(CertManagementUtil.class.getSimpleName());
	
	private static final CertManagementUtil _Instance = new CertManagementUtil();
	
	public static final String DEFAULT_SOCKET_TYPE      = "SSL";
	public static final String DEFAULT_CERTIFICATE_TYPE = "X.509";
	
	/**
	 * @return CertManagementUtil
	 */
	public static final CertManagementUtil getInstance()
	{
		return _Instance;
	}
	
	
	// private var's
	
	
	// constructors
	
	/**
	 * Default (private).
	 * 
	 */
	private CertManagementUtil()
	{
		super();
	}
	
	
	// public methods
	
	/**
	 * Attempts to load the certificate contained in 'fileName'
	 * using the Java {@link KeyStore} and {@link TrustManagerFactory}
	 * functionality.
	 * <p>
	 * On success, the certificate will be loaded by the system, stored
	 * under a unique ID, and mapped to an {@link SSLSocketFactory}, which
	 * is returned to the caller.
	 * <p>
	 * On failure, an exception will be logged, and null will be returned.
	 * 
	 * @param fileName The certificate file name to load.
	 * @return SSLSocketFactory The socket factory initialized with
	 * the loaded certificate.
	 */
	public SSLSocketFactory loadCertificate(String fileName)
	{
		try {
			_Logger.info("Configuring " + DEFAULT_SOCKET_TYPE + " using certificate: " + fileName);
			
			SSLContext sslContext = SSLContext.getInstance(DEFAULT_SOCKET_TYPE);
			KeyStore   keyStore   = processCertificate(fileName);
			
			TrustManagerFactory trustManagerFactory =
				TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			
			trustManagerFactory.init(keyStore);
			sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
			
			_Logger.info("Certificate load / init successful.");
				
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to initialize and load certificate from file: " + fileName, e);
		}
		
		return null;
	}
	
	
	// private methods
	
	/**
	 * Loads the given certificate file and stores as a uniquely named
	 * keystore reference (based on the filename and available bytes.
	 * 
	 * @param fileName The file name of the certificate to process.
	 * @return KeyStore A reference to the {@link KeyStore} containing
	 * the certificate.
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	private KeyStore processCertificate(String fileName)
		throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
	    KeyStore            ks  = KeyStore.getInstance(KeyStore.getDefaultType());
	    FileInputStream     fis = new FileInputStream(fileName);
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    CertificateFactory  cf  = CertificateFactory.getInstance(DEFAULT_CERTIFICATE_TYPE);

	    ks.load(null);
	    
	    while (bis.available() > 0) {
	        Certificate cert = cf.generateCertificate(bis);
	        ks.setCertificateEntry(fileName + bis.available(), cert);
	    }
	    
		return ks;		
	}

}
