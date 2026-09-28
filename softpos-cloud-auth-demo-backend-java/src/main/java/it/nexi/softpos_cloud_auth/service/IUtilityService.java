package it.nexi.softpos_cloud_auth.service;

import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Nexi Payments 
 */
public interface IUtilityService {
        
    /**
     *
     * @param conn
     * @return
     */
    public String getResponseBody(HttpsURLConnection conn);   
    
}
