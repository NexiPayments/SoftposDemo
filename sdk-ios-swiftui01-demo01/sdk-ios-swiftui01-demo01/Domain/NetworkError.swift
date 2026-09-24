//
//  NetworkError.swift
//

import Foundation

enum NetworkError: Error {
    case invalidURL
    case invalidResponse
    case decodingError
    case requestFailed(Error)
}
