package it.nexi.softpos_evo.service;

import java.io.InputStream;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Nexi Payments 
 */
public interface IUtilityService {
    
    /**
     *
     * @param fileName
     * @return
     */
    public InputStream getFileAsIOStream(final String fileName);
        
    /**
     *
     * @param conn
     * @return
     */
    public String getResponseBody(HttpsURLConnection conn);   
    
}
