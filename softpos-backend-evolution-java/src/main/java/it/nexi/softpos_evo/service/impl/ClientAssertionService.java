package it.nexi.softpos_evo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import it.nexi.softpos_evo.domain.RequestUriParamitersObj;
import it.nexi.softpos_evo.domain.ClientAssertion;
import it.nexi.softpos_evo.service.IClientAssertionService;
import java.security.interfaces.RSAPublicKey;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nexi Payments 
 */
@Service
public class ClientAssertionService implements IClientAssertionService{
    
    private static final Logger logger = LogManager.getLogger(ClientAssertionService.class); 
    
    /**
     * I create the client assertion, sign it with the private key, and encrypt it with the public key.
     * @param remoteJWKSet
     * @param clientAssertionReqObj
     * @param FQDN
     * @return 
     */   
    @Override
    public String makeClientAssertion(final JWKSet remoteJWKSet, 
                                      RequestUriParamitersObj clientAssertionReqObj,
                                      String FQDN) {
        String clientAssertionEncrypted = "";
        
        ClientAssertion parClientAssertion = new ClientAssertion();
        parClientAssertion.setAppId(clientAssertionReqObj.getIdClient());
        parClientAssertion.setPointOfSale(clientAssertionReqObj.getIdPointOfSale());
        parClientAssertion.setTerminal_ids(clientAssertionReqObj.getTerminaleId().split(","));
        parClientAssertion.setApp_username_merchant(clientAssertionReqObj.getUsernameMerchant());
        parClientAssertion.setApp_deviceid(clientAssertionReqObj.getDeviceId());
        parClientAssertion.setIss(clientAssertionReqObj.getIdClient());
        parClientAssertion.setSub(clientAssertionReqObj.getIdClient());
        parClientAssertion.setAud(FQDN);

        // iat and exp must be a valid timestamp
        // use NTP to avoid time synchronization issues
        // The claim will be rejected if the time is wrong
        final long unixTime = ZonedDateTime.now().toInstant().getEpochSecond();
        final long iat = unixTime;
        // this must be short expiring, 500s is planty to perform the action
        final long exp = unixTime + 800;
        final UUID jti = UUID.randomUUID();
        
        parClientAssertion.setIat(iat);
        parClientAssertion.setExp(exp);
        parClientAssertion.setJti(jti.toString());
        
        String parClientAssertionBodyJson = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper(); 
            parClientAssertionBodyJson = objectMapper.writeValueAsString(parClientAssertion);
        } catch (final JsonProcessingException e) {
            System.out.println("can't json encode the value: " + e.getMessage());
        }
        System.out.println("par client assertion: {}" + parClientAssertionBodyJson);
        logger.info("par client assertion: {}: " + parClientAssertionBodyJson);

        try {
            // First, the payload is signed with the PrivateKey.    
            final JWK jwkSignature = JWK.parseFromPEMEncodedObjects(clientAssertionReqObj.getPrivateKey());
            final JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.RS256);
            final JWSObject jws = new JWSObject(jwsHeader, new Payload(parClientAssertionBodyJson));
            jws.sign(new RSASSASigner(jwkSignature.toRSAKey()));
            final String jwsPayload = jws.serialize();
            // Signed data is encrypted using the JWKS nexi public key set
            final JWK jwkEncryption = remoteJWKSet.getKeys().get(0);
            final EncryptionMethod encryptionMethod = EncryptionMethod.A128CBC_HS256;
            final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(encryptionMethod.cekBitLength());
            final SecretKey contentEncryptionKey = keyGenerator.generateKey();
            final RSAPublicKey rsaPublicKey = (RSAPublicKey) jwkEncryption.toPublicJWK().toRSAKey().toPublicKey();
            final JWEHeader jweHeader = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, encryptionMethod);
            final JWEObject jwe = new JWEObject(jweHeader, new Payload(jwsPayload));
            jwe.encrypt(new RSAEncrypter(rsaPublicKey, contentEncryptionKey));
            clientAssertionEncrypted = jwe.serialize();
        } catch (final Exception e) {
            System.out.println("error in cryptography: " + e.getMessage());
        }        
        return clientAssertionEncrypted;
    }   
    
}
