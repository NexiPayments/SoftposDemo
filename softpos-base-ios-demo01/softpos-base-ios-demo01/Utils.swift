//
//  Utils.swift
//  SoftposBASETest01
//
//  Created by MAC-01 on 07/11/25.
//

import Foundation
import SwiftUI

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
