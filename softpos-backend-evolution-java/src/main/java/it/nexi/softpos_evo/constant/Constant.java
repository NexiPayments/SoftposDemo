package it.nexi.softpos_evo.constant;

/**
 *
 * @author Nexi Payments 
 */
public class Constant {    

    // SCOPES can take the values: "openid", "oidc", "email", "profile", "phone", "address". 
    // For the app to function, “oidc” is sufficient.
    public static final String APP_SCOPES                                       = "oidc";
    
    // URL SERVER
    public static final String URL_SERVER_SVILUPPATORI_STAGE                    = "https://intapi.nexi.local:8443/nexiauth/openid/";
    public static final String URL_SERVER_SVILUPPATORI_PROD                     = "https://b2bsoftpos.nexigroup.com";
    public static final String URL_SERVER_SVILUPPATORI_STAGE_CHEKTR             = "https://stgb2b-issuing.nexi.it";
    
    // API
    public static final String API_POST_TOKEN                                   = "/softpos/appenrollment/token";
    public static final String API_GET_PUBLIC_KEY                               = "/softpos/par/jwks.json";
    public static final String API_POST_PAR                                     = "/softpos/as/par";
    public static final String API_Check_Transaction                            = "/mpos-core/app/checkTransaction"; 
    
    // DEFAULT VALUE
    public static final String DEFAULT_DOMAIN                                   = "STAGING"; // STAGING / PRODUCTION
    // X.509 certificate in PEM format containing Base64-encoded data uploaded in the portal.
    public static final String DEFAULT_CERTIFICATE                              = "-----BEGIN CERTIFICATE-----\n... Base64 code ...==\n-----END CERTIFICATE-----";
    // Unencrypted cryptographic private key containing Base64-encoded data.
    public static final String DEFAULT_PRIVATE_KEY                              = "-----BEGIN PRIVATE KEY-----\n... Base64 code ...==\n-----END PRIVATE KEY-----"; 
    // Client ID identifier for the application registered on the Nexi developer portal.
    public static final String DEFAULT_ID_CLIENT                                = ""; 
    // Secret key identifying the application registered on the Nexi developer portal.
    public static final String DEFAULT_SECRET                                   = ""; 
    // Web address specified in the app registered on the Nexi developer portal and it must be correctly formatted (e.s. https://mywebsite/myredirectpage).
    public static final String DEFAULT_APP_REDIRECT_URI                         = "https://mywebsite/myredirectpage"; 
    // Point of Sale ID assigned to your application in the portal.
    public static final String DEFAULT_ID_POINT_OF_SALE                         = ""; 
    // Terminal ID associated with the Point of Sale.
    public static final String DEFAULT_TERMINAL_ID                              = ""; 
    // Alphanumeric ID identifying the device making the request. During the testing phase, it can also be a randomly generated value.
    public static final String DEFAULT_DEVICE_ID                                = "";
    // The Username Merchant is the email address associated with the user.
    public static final String DEFAULT_USERNAME_MERCHANT                        = "";
        
}
