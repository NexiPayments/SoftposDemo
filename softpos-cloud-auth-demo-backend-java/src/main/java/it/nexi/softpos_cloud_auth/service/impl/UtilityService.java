/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.nexi.softpos_cloud_auth.service.impl;

import it.nexi.softpos_cloud_auth.service.IUtilityService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nexi Payments 
 */
@Service
public class UtilityService implements IUtilityService {              

    /**
     *
     */
    public UtilityService() {}   
        
    /**
     *
     * @param conn
     * @return
     */
    @Override
    public String getResponseBody(HttpsURLConnection conn) {
        BufferedReader br = null;
        StringBuilder body = null;
        String line = "";
        try {//  w  w w .j  a  v a 2s . c  o m
            br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            body = new StringBuilder();
            while ((line = br.readLine()) != null)
                body.append(line);
            return body.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }        
    
}
