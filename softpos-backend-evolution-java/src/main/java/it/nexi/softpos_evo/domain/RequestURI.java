package it.nexi.softpos_evo.domain;

/**
 *
 * @author Nexi Payments 
 */
public class RequestURI {
    
    private String request_uri;
    private String expires_in;

    /**
     *
     */
    public RequestURI() {
    }

    /**
     *
     * @param request_uri
     * @param expires_in
     */
    public RequestURI(String request_uri, String expires_in) {
        this.request_uri = request_uri;
        this.expires_in = expires_in;
    }

    /**
     *
     * @return
     */
    public String getRequest_uri() {
        return request_uri;
    }

    /**
     *
     * @param request_uri
     */
    public void setRequest_uri(String request_uri) {
        this.request_uri = request_uri;
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

    @Override
    public String toString() {
        return "RequestURI{" + "request_uri=" + request_uri + ", expires_in=" + expires_in + '}';
    }
    
}
