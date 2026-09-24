package it.nexi.softpos_evo_kotlin_01.domain

import com.google.gson.annotations.SerializedName

/**
 *
 * Nexi Payment
 *
 * It represents the request URI returned by the Nexi server.
 *
 */
data class RequestURI(

    @SerializedName("request_uri")
    val requestUri: String,

    @SerializedName("expires_in")
    val expiresIn: String
)