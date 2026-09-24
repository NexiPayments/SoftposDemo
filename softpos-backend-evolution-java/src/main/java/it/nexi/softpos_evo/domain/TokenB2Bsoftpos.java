package it.nexi.softpos_evo.domain;

/**
 *
 * @author Nexi Payments 
 */
public class TokenB2Bsoftpos {
    
    private String access_token;
    private String token_type;
    private String expires_in; 

    /**
     *
     */
    public TokenB2Bsoftpos() {
    }

    /**
     *
     * @param access_token
     * @param token_type
     * @param expires_in
     */
    public TokenB2Bsoftpos(String access_token, String token_type, String expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    /**
     *
     * @return
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     *
     * @param access_token
     */
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     *
     * @return
     */
    public String getToken_type() {
        return token_type;
    }

    /**
     *
     * @param token_type
     */
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    /**
     *
     * @return
     */
    public String getExpires_in() {
        return expires_in;
    }

    /**
     *
     * @param expires_in
     */
    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
 
    /**
     *
     */
    public void printKeyValues() {
        System.out.println("--- Token ---\n" + 
            "access_token: " + this.getAccess_token() + "\n" +
            "token_type: " + this.getToken_type() + "\n" +
            "expires_in: " + this.getExpires_in() + "\n" 
        );        
    }
    
}
