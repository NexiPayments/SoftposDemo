package it.nexi.softpos_cloud_auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Nexi Payments 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUriParamitersObj {
    
    String domain;
    String certificate;
    String privateKey;
    String idClient;
    String secret;
    String appRedirectUri;
    String idPointOfSale;
    String terminaleId;
    String deviceId;
    String usernameMerchant;       
    
}
