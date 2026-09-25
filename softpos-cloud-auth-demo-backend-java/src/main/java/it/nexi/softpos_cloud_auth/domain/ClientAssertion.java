package it.nexi.softpos_cloud_auth.domain;

/**
 *
 * @author Nexi Payments 
 */
public class ClientAssertion {
    
    private String appId;
    private String pointOfSale;
    private String[] terminal_ids;
    private String app_username_merchant;
    private String app_deviceid;
    private String iss;
    private String sub;
    private String aud;
    private Long iat;
    private Long exp;
    private String jti;

    /**
     *
     */
    public ClientAssertion() {
    }

    /**
     *
     * @param appId
     * @param pointOfSale
     * @param terminal_ids
     * @param app_username_merchant
     * @param app_deviceid
     * @param iss
     * @param sub
     * @param aud
     * @param iat
     * @param exp
     * @param jti
     */
    public ClientAssertion(String appId, String pointOfSale, String[] terminal_ids, String app_username_merchant, String app_deviceid, String iss, String sub, String aud, Long iat, Long exp, String jti) {
        this.appId = appId;
        this.pointOfSale = pointOfSale;
        this.terminal_ids = terminal_ids;
        this.app_username_merchant = app_username_merchant;
        this.app_deviceid = app_deviceid;
        this.iss = iss;
        this.sub = sub;
        this.aud = aud;
        this.iat = iat;
        this.exp = exp;
        this.jti = jti;
    }    

    /**
     *
     * @return
     */
    public String getAppId() {
        return appId;
    }

    /**
     *
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     *
     * @return
     */
    public String getPointOfSale() {
        return pointOfSale;
    }

    /**
     *
     * @param pointOfSale
     */
    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    /**
     *
     * @return
     */
    public String[] getTerminal_ids() {
        return terminal_ids;
    }

    /**
     *
     * @param terminal_ids
     */
    public void setTerminal_ids(String[] terminal_ids) {
        this.terminal_ids = terminal_ids;
    }

    /**
     *
     * @return
     */
    public String getApp_username_merchant() {
        return app_username_merchant;
    }

    /**
     *
     * @param app_username_merchant
     */
    public void setApp_username_merchant(String app_username_merchant) {
        this.app_username_merchant = app_username_merchant;
    }

    /**
     *
     * @return
     */
    public String getApp_deviceid() {
        return app_deviceid;
    }

    /**
     *
     * @param app_deviceid
     */
    public void setApp_deviceid(String app_deviceid) {
        this.app_deviceid = app_deviceid;
    }

    /**
     *
     * @return
     */
    public String getIss() {
        return iss;
    }

    /**
     *
     * @param iss
     */
    public void setIss(String iss) {
        this.iss = iss;
    }

    /**
     *
     * @return
     */
    public String getSub() {
        return sub;
    }

    /**
     *
     * @param sub
     */
    public void setSub(String sub) {
        this.sub = sub;
    }

    /**
     *
     * @return
     */
    public String getAud() {
        return aud;
    }

    /**
     *
     * @param aud
     */
    public void setAud(String aud) {
        this.aud = aud;
    }

    /**
     *
     * @return
     */
    public Long getIat() {
        return iat;
    }

    /**
     *
     * @param iat
     */
    public void setIat(Long iat) {
        this.iat = iat;
    }

    /**
     *
     * @return
     */
    public Long getExp() {
        return exp;
    }

    /**
     *
     * @param exp
     */
    public void setExp(Long exp) {
        this.exp = exp;
    }    

    /**
     *
     * @return
     */
    public String getJti() {
        return jti;
    }

    /**
     *
     * @param jti
     */
    public void setJti(String jti) {
        this.jti = jti;
    }

}
