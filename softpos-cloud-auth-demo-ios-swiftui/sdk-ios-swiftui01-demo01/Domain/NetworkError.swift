/**
 *
 * Nexi Payments
 *
 * Defines a network error
 *
 */

import Foundation

enum NetworkError: Error {
    case invalidURL
    case invalidResponse
    case decodingError
    case requestFailed(Error)
}
