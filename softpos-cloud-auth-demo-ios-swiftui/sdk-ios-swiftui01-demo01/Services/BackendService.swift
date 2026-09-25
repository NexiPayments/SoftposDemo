//
//
// Backend cliente
//
//
import Foundation
import CocoaLumberjackSwift

class BackendService {
        
    static func getRequestUri(deviceId : String) async throws -> String {
        
        DDLogInfo("\(Constant.APP_MANAGER) -> avviata getRequestUri(\(deviceId)")
        
        // Esempio di url di backend
        
        let strURLBackend = ""
        
        guard let url = URL(string: strURLBackend)
            else {
                throw NetworkError.invalidURL
        }

        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.setValue("application/json", forHTTPHeaderField: "Accept")
        
        do {
           
            let (data, response) =  try await URLSession.shared.data(for: request)
           
            guard let httpResponse = response as? HTTPURLResponse, (200...299).contains(httpResponse.statusCode) else {
                
                // Verifica lo statusCode per il debug
                if let httpResponse = response as? HTTPURLResponse {
                    
                    DDLogError("\(Constant.APP_MANAGER) -> errore \(httpResponse.statusCode)")
                    
                }
                
                throw NetworkError.invalidResponse
            }

            do {
                
                let decoder = JSONDecoder()
                
                let requestURIObject = try decoder.decode(RequestURI.self, from: data)
                
                DDLogInfo("\(Constant.APP_MANAGER) -> recuperata : \(requestURIObject.request_uri)")
                
                return requestURIObject.request_uri
                
            } catch {
                
                DDLogError("\(Constant.APP_MANAGER) -> errore \(NetworkError.decodingError)")
                
                throw NetworkError.decodingError
            }
            
        } catch {
            
            // Cattura altri possibili errori durante la chiamata di rete (es. no internet)
            DDLogError("\(Constant.APP_MANAGER) -> errore \(NetworkError.decodingError)")
            
            throw NetworkError.requestFailed(error)
        }
        
    } // func getRequestUri(deviceId : String, idUtente : Int)
}
