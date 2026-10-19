//
//  Untitled.swift
//

import Foundation
import Network

/**
 *
 * Nexi Payments
 *
 *
 */
class NetworkMonitor {
    
    static let shared = NetworkMonitor()

    let monitor = NWPathMonitor()
    
    private var status: NWPath.Status = .requiresConnection
    
    var isConnected: Bool {
        
        return status == .satisfied
            
    } // var isConnected: Bool {

    private init() {}

    func startMonitoring() {
        
        monitor.pathUpdateHandler = { [weak self] path in
            
            self?.status = path.status
            
            // Notify the rest of the app about the state change
            NotificationCenter.default.post(name: .connectivityStatusChanged, object: nil)
            
        }

        let queue = DispatchQueue(label: "NetworkMonitor")
        
        monitor.start(queue: queue)
        
    } // func startMonitoring() {

    func stopMonitoring() {
        monitor.cancel()
    }
    
} // class

extension Notification.Name {
    
    static let connectivityStatusChanged = Notification.Name("connectivityStatusChanged")
    
}
