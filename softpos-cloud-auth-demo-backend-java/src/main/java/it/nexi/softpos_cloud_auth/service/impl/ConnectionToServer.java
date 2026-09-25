/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.nexi.softpos_cloud_auth.service.impl;

import static it.nexi.softpos_cloud_auth.constant.Constant.*;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import it.nexi.softpos_cloud_auth.domain.RequestURI;
import it.nexi.softpos_cloud_auth.domain.Response;
import it.nexi.softpos_cloud_auth.domain.TokenB2Bsoftpos;
import it.nexi.softpos_cloud_auth.service.IConnectionToServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nexi Payments 
 */
@Service
public class ConnectionToServer implements IConnectionToServer{
    
    @Autowired
    UtilityService utility;
    
    /**
     *
     */
    public String domain;
    
    /**
     * 
     * @param sslSocketFactory
     * @param APP_ID
     * @param APP_SECRET
     * @param FQDN
     * @return 
     */
    @Override
    public Response<TokenB2Bsoftpos> call_TOKEN_B2B(SSLSocketFactory sslSocketFactory, 
                                                    String APP_ID,
                                                    String APP_SECRET,
                                                    String FQDN) {
        URL url = null;
        Response response = new Response();
        HttpsURLConnection connection = null;
        TokenB2Bsoftpos tokenResponse = new TokenB2Bsoftpos();
        String responseJson = null;
        OutputStream wr = null;
        BufferedReader reader = null;
        try {
            
            url = new URL(FQDN + API_POST_TOKEN);
            System.out.println("Call URL: " + url);
            // apre la connessione
            connection = (HttpsURLConnection) url.openConnection();
            if (connection != null) {
                connection.setRequestMethod("POST");
                //connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setSSLSocketFactory(sslSocketFactory);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // spedisce la richiesta
                wr = connection.getOutputStream();
                String requestParams = "grant_type=" + "client_credentials" + "&" +
                                       "client_id=" + APP_ID + "&" +
                                       "client_secret=" + APP_SECRET;
                wr.write(requestParams.getBytes());            
                wr.flush();
                wr.close();                
                // Get response code and handle response
                if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    // Read response content
                    responseJson = utility.getResponseBody(connection);
                    ObjectMapper objectMapper = new ObjectMapper(); 
                    tokenResponse = objectMapper.readValue(responseJson, TokenB2Bsoftpos.class);  
                    response.setResponseBody(tokenResponse);
                }
                else {
                    String error = "ERROR API: " + url + " - HTTP Response code - " + connection.getResponseCode();
                    System.out.println(error);
                    response.setError(error);
                }
                connection.disconnect();                
            } else {
                response.setError("SERVER ERROR: Connection attempt has failed!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
                if (wr != null) {
                    wr.close();
                }
                if (reader != null) {
                    reader.close();
                }                
                              
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }
    
    /**
     * 
     * @param sslSocketFactory
     * @param FQDN
     * @return 
     */
    @Override
    public Response<JWKSet> call_ENDPOINT_JWKS(SSLSocketFactory sslSocketFactory,
                                     String FQDN) {
        URL url = null;
        Response response = new Response();
        HttpsURLConnection connection = null;
        String responseJson = null;
        OutputStream wr = null;
        BufferedReader reader = null;
        JWKSet jwkSet = null;
        try {
            // trasforma in json l'oggetto
            // requestJson =  gson.toJson(requestObjectDTO);
            // crea l'url            
            url = new URL(FQDN + API_GET_PUBLIC_KEY);
            System.out.println("Call URL: " + url);
            // apre la connessione
            connection = (HttpsURLConnection) url.openConnection();
            if (connection != null) {
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setSSLSocketFactory(sslSocketFactory);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // spedisce la richiesta
                wr = connection.getOutputStream();
                wr.flush();
                wr.close();
                // ottiene la risposta
                // Get response code and handle response
                if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    // Read response content
                    responseJson = utility.getResponseBody(connection);
                    jwkSet = JWKSet.parse(responseJson);
                    System.out.println(jwkSet.toString());
                    response.setResponseBody(jwkSet);
                }
                else {
                    String error = "ERROR API: " + url + " - HTTP Response code - " + connection.getResponseCode();
                    System.out.println(error);
                    response.setError(error);
                }
            } else {
                response.setError("SERVER ERROR: Connection attempt has failed!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response.setError(ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
                if (wr != null) {
                    wr.close();
                }
                if (reader != null) {
                    reader.close();
                }                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }        
        return response;
    }

    /**
     * 
     * @param sslSocketFactory
     * @param token
     * @param clientAssertionSigned
     * @param APP_ID
     * @param APP_REDIRECT_URI
     * @param FQDN
     * @return 
     */
    @Override
    public Response<RequestURI> call_PAR_B2B(SSLSocketFactory sslSocketFactory,
                                   String token,
                                   String clientAssertionSigned,
                                   String APP_ID,
                                   String APP_REDIRECT_URI,
                                   String FQDN) throws Exception {
        URL url = null;
        HttpsURLConnection connection = null;
        Response response = new Response();
        RequestURI requestURI = new RequestURI();
        String responseJson = null;
        OutputStream wr = null;
        BufferedReader reader = null;
        try {
            // crea l'url
            url = new URL(FQDN + API_POST_PAR);
            System.out.println("Call URL: " + url);
            // apre la connessione
            connection = (HttpsURLConnection) url.openConnection();
            if (connection != null) {
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setSSLSocketFactory(sslSocketFactory);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // spedisce la richiesta
                wr = connection.getOutputStream();
                String requestParams = "client_assertion=" + clientAssertionSigned + "&" +
                                       "client_id=" + APP_ID  + "&" +
                                       "scope=" + APP_SCOPES  + "&" +
                                       "redirect_uri=" + APP_REDIRECT_URI;
                wr.write(requestParams.getBytes());            
                wr.flush();
                wr.close();                
                // Get response code and handle response
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_CREATED) {
                    // Read response content
                    responseJson = utility.getResponseBody(connection);
                    ObjectMapper objectMapper = new ObjectMapper(); 
                    requestURI = objectMapper.readValue(responseJson, RequestURI.class);  
                    response.setResponseBody(requestURI);
                }
                else { // ERROR
                    String error = "ERROR API: " + url + " - HTTP Response code - " + connection.getResponseCode();
                    System.out.println(error);
                    response.setError(error);    
                }
                connection.disconnect();                
            } else {
                response.setError("SERVER ERROR: Connection attempt has failed!");
            }
        } catch (Exception ex) {                     
            System.out.println(ex.getMessage());  
            response.setError(ex.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.disconnect();
                }
                if (wr != null) {
                    wr.close();
                }
                if (reader != null) {
                    reader.close();
                }                             
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }    
    
}
