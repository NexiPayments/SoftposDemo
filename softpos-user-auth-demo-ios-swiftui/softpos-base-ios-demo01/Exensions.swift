import Foundation
import SwiftUI

/**
 *
 * Nexi Payments
 *
 * Manages Extensions
 *
 */
// Extends the URL type to conform to the Identifiable protocol.
extension URL: Identifiable {
    
    // Uses the absolute file path (absoluteString) as its unique ID.
    public var id: String {
        self.absoluteString
    }
    
} // extension URL: Identifiable {

// Extension to hide the keyboard
extension View {
    
    func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
    
} // extension View {
