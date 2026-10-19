/**
 *
 * Nexi Payments
 *
 *  Defines the requesturi model returned by the
 *  Nexi authentication service
 *
 */
struct RequestURI: Codable {
    let request_uri : String
    let expires_in : String
}
