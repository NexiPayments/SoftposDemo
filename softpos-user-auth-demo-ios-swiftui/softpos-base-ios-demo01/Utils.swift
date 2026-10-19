import Foundation
import SwiftUI

/**
 *
 * Nexi Payments
 *
 *
 */
class Utils {
    
    public static func colorDomain(domain: String) -> Color {
        
        switch domain {
            
            case "staging":
                return .green
            
            case "production":
                return .red
            
            default:
                return .primary
            
        } // switch
        
    } // public static func colorDomain
    
} // class
