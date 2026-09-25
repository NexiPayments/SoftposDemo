package it.nexi.softpos_cloud_auth.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import static it.nexi.softpos_cloud_auth.constant.Constant.*;
import it.nexi.softpos_cloud_auth.domain.RequestUriParamitersObj;
import it.nexi.softpos_cloud_auth.domain.RequestURI;
import it.nexi.softpos_cloud_auth.domain.Response;
import it.nexi.softpos_cloud_auth.domain.TokenB2Bsoftpos;
import it.nexi.softpos_cloud_auth.enums.DomainEnum;
import it.nexi.softpos_cloud_auth.service.impl.ClientAssertionService;
import it.nexi.softpos_cloud_auth.service.impl.ConnectionToServer;
import it.nexi.softpos_cloud_auth.service.impl.SSLService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.net.ssl.SSLSocketFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Nexi Payments 
 */
@Controller
public class SoftPosWebController {
    
    private static final Logger logger = LogManager.getLogger(SoftPosWebController.class);    

    @Autowired
    SSLService sslService;
    
    @Autowired
    ConnectionToServer connectionToServer;
    
    @Autowired
    ClientAssertionService clientAssertionService;
    
    @GetMapping({"/","/getRequestUriForm"})
    public String getRequestUriForm(HttpServletRequest request, Model model) throws IOException, JOSEException, NoSuchAlgorithmException, InvalidKeySpecException {  
        RequestUriParamitersObj requestUriObj = new RequestUriParamitersObj();
        requestUriObj.setDomain(DEFAULT_DOMAIN);
        requestUriObj.setCertificate(DEFAULT_CERTIFICATE);
        requestUriObj.setPrivateKey(DEFAULT_PRIVATE_KEY);
        requestUriObj.setAppRedirectUri(DEFAULT_APP_REDIRECT_URI);
        requestUriObj.setDeviceId(DEFAULT_DEVICE_ID);
        requestUriObj.setIdClient(DEFAULT_ID_CLIENT);
        requestUriObj.setIdPointOfSale(DEFAULT_ID_POINT_OF_SALE);
        requestUriObj.setTerminaleId(DEFAULT_TERMINAL_ID);
        requestUriObj.setUsernameMerchant(DEFAULT_USERNAME_MERCHANT);
        model.addAttribute("requestUriParamitersObj", requestUriObj);
        return "request_uri_form";
    }
    
    /**
     * 
     * @param request
     * @param model
     * @param requestUriParamitersObj
     * @return
     * @throws IOException
     * @throws JOSEException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    @RequestMapping(method = RequestMethod.POST, value = "/getRequestURI", produces = "text/plain")
    public String getRequestURI(HttpServletRequest request, 
                                Model model, 
                                @ModelAttribute("requestUriParamitersObj") RequestUriParamitersObj requestUriParamitersObj) throws IOException, JOSEException, NoSuchAlgorithmException, InvalidKeySpecException, Exception {        
        String FQDN = null;
        try {
            // crea l'url
            if (requestUriParamitersObj.getDomain().equals(DomainEnum.STAGE.getEnvironment())) {
                FQDN  = URL_SERVER_SVILUPPATORI_STAGE;
            } else if (requestUriParamitersObj.getDomain().equals(DomainEnum.PROD.getEnvironment()))  {
                FQDN  = URL_SERVER_SVILUPPATORI_PROD;
            }             
            // SSL CONNECTION            
            SSLSocketFactory sslSocketFactory = sslService.createSSLSocketFactory(
                new ByteArrayInputStream(requestUriParamitersObj.getCertificate().getBytes(StandardCharsets.UTF_8)),
                new ByteArrayInputStream(requestUriParamitersObj.getPrivateKey().getBytes(StandardCharsets.UTF_8))                           
            );                        
            // **** 1  - CALL TOKEN B2B DA BE MERCHANT (B2B)
            System.out.println("1 CHIAMATA TOKEN B2B DA BE MERCHANT (B2B)...");
            Response<TokenB2Bsoftpos> responseToken = connectionToServer.call_TOKEN_B2B(sslSocketFactory, 
                                                        requestUriParamitersObj.getIdClient(), 
                                                        requestUriParamitersObj.getSecret(), 
                                                        FQDN);
            if (responseToken.getError() != null) { // CHECK Response ERROR
                throw new Exception(responseToken.getError());
            }            
            // **** 2 - ENDPOINT JWKS (B2B)
            System.out.println("2 CALL ENDPOINT JWKS (B2B)...");
            Response<JWKSet> responseJwkSet = connectionToServer.call_ENDPOINT_JWKS(sslSocketFactory, FQDN);  
            if (responseJwkSet.getError() != null) { // CHECK Response ERROR
                throw new Exception(responseJwkSet.getError());
            }            
            // MAKE CLient Assertion
            String clientAssertionSigned = clientAssertionService.makeClientAssertion(responseJwkSet.getResponseBody(), 
                                                                                requestUriParamitersObj,
                                                                                FQDN);              
            System.out.println("ClientAssertionSigned: " + clientAssertionSigned);
            // 3 - CALL PAR B2B GET Request URI
            System.out.println("3 CALL PAR B2B GET Request URI...");
            Response<RequestURI> responseRequestURI = connectionToServer.call_PAR_B2B(sslSocketFactory, 
                                                        responseToken.getResponseBody().getAccess_token(), 
                                                        clientAssertionSigned, 
                                                        requestUriParamitersObj.getIdClient(), 
                                                        requestUriParamitersObj.getAppRedirectUri(),
                                                        FQDN);
            if (responseRequestURI.getError() != null) { // CHECK Response ERROR
                throw new Exception(responseRequestURI.getError());
            }  
            System.out.println("RequestURI: " + responseRequestURI.getResponseBody().toString()); 
            model.addAttribute("response", responseRequestURI.getResponseBody().toString());
            model.addAttribute("responseStatus", "OK");                       
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            model.addAttribute("response", ex.getMessage());
            model.addAttribute("responseStatus", "KO"); 
        }        
        return "request_uri_response_form";
    }   

}