package it.nexi.softpos_evo.service;

import com.nimbusds.jose.jwk.JWKSet;
import it.nexi.softpos_evo.domain.RequestURI;
import it.nexi.softpos_evo.domain.Response;
import it.nexi.softpos_evo.domain.TokenB2Bsoftpos;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Nexi Payments 
 */
public interface IConnectionToServer {
    
    /**
     *
     * @param sslSocketFactory
     * @param APP_ID
     * @param APP_SECRET
     * @param FQDN
     * @return
     */
    public Response<TokenB2Bsoftpos> call_TOKEN_B2B(SSLSocketFactory sslSocketFactory, 
                                                    String APP_ID,
                                                    String APP_SECRET,
                                                    String FQDN);
    
    /**
     *
     * @param sslSocketFactory
     * @param FQDN
     * @return
     */
    public Response<JWKSet> call_ENDPOINT_JWKS(SSLSocketFactory sslSocketFactory, String FQDN);
    
    /**
     *
     * @param sslSocketFactory
     * @param token
     * @param clientAssertionSigned
     * @param APP_ID
     * @param APP_REDIRECT_URI
     * @param FQDN
     * @return
     * @throws java.lang.Exception
     */
    public Response<RequestURI> call_PAR_B2B(SSLSocketFactory sslSocketFactory,
                                   String token,
                                   String clientAssertionSigned,
                                   String APP_ID,
                                   String APP_REDIRECT_URI,
                                   String FQDN) throws Exception;
    
}
