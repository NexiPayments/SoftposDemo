package it.nexi.softpos_cloud_auth.service;

import java.io.InputStream;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Nexi Payments 
 */
public interface ISSLService {
    
    /**
     * 
     * @param isClientCertificate
     * @param isPrivateKey
     * @return 
     */
    public SSLSocketFactory createSSLSocketFactory(
        InputStream isClientCertificate, InputStream isPrivateKey
    );
    
}
