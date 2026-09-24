package it.nexi.softpos_evo.service.impl;

import com.nimbusds.jose.jwk.JWK;
import it.nexi.softpos_evo.service.ISSLService;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nexi Payments 
 */
@Service
public class SSLService implements ISSLService{
    
    /**
     * 
     * @param isClientCertificate
     * @param isPrivateKey
     * @return 
     */
    @Override
    public SSLSocketFactory createSSLSocketFactory(InputStream isClientCertificate, InputStream isPrivateKey) {
        CertificateFactory  cf                          = null;
        KeyManagerFactory   kmf                         = null;
        Certificate         clientCert                  = null;
        KeyStore            clientKeyStore              = null;
        SSLContext          contextSSL                  = null;
        try {
            // Retrieve the client CA certificate stream. (.crt)
            cf = CertificateFactory.getInstance("X.509");
            
            // Retrieve the private key data        
            String encodedPrivateKey = IOUtils.toString(isPrivateKey, Charset.defaultCharset());
            
            // Create JWK
            JWK jwk = JWK.parseFromPEMEncodedObjects(encodedPrivateKey);
            RSAPrivateKey privateKey = jwk.toRSAKey().toRSAPrivateKey(); 
            
            // create Keystore
            clientKeyStore = KeyStore.getInstance("PKCS12"); 
            
            // generate Certificates by InputStream
            if (isClientCertificate.available() > 0) {
                clientCert = cf.generateCertificate(isClientCertificate);
            }
            
            // Load the certificate into the client keystore.
            clientKeyStore.load(null, null);
            clientKeyStore.setKeyEntry("client",privateKey,null, new Certificate[] { clientCert });
            String kmfAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
            kmf = KeyManagerFactory.getInstance(kmfAlgorithm);
            kmf.init(clientKeyStore, null);

            // Create an SSLContext that uses the TrustManager and the KeyManager.
            contextSSL = SSLContext.getInstance("TLSv1.3");
            if (kmf != null) {
                contextSSL.init(kmf.getKeyManagers(), null, null);
            } else {
                contextSSL.init(null, null, null);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return contextSSL.getSocketFactory();
    }
    
}
