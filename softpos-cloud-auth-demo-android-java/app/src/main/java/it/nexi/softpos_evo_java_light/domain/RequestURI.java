package it.nexi.softpos_evo_java_light.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 *
 * Nexi Payment
 *
 * It represents the request URI returned by the Nexi server.
 *
 */

public class RequestURI {

    @SerializedName("request_uri")
    private final String requestUri;

    @SerializedName("expires_in")
    private final String expiresIn;

    public RequestURI(String requestUri, String expiresIn) {

        this.requestUri = requestUri;
        this.expiresIn = expiresIn;

    }
    public String getRequestUri() {
        return requestUri;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestURI that = (RequestURI) o;
        return Objects.equals(requestUri, that.requestUri) &&
                Objects.equals(expiresIn, that.expiresIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestUri, expiresIn);
    }

    @Override
    public String toString() {
        return "RequestURI{" +
                "requestUri='" + requestUri + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                '}';
    }

} // end class
