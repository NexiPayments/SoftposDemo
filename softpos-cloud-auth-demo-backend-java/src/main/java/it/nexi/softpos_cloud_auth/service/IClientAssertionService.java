package it.nexi.softpos_cloud_auth.service;

import com.nimbusds.jose.jwk.JWKSet;
import it.nexi.softpos_cloud_auth.domain.RequestUriParamitersObj;

/**
 *
 * @author Nexi Payments 
 */
public interface IClientAssertionService {
       
    /**
     * 
     * @param remoteJWKSet
     * @param clientAssertionReqObj
     * @param FQDN
     * @return 
     */
    public String makeClientAssertion(final JWKSet remoteJWKSet, 
                                      RequestUriParamitersObj clientAssertionReqObj,
                                      String FQDN);
    
}
