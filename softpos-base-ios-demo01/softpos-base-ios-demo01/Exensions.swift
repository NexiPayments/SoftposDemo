//
//  Exensions.swift
//  SoftposBASETest01
//
//  Created by MAC-01 on 07/09/25.
//

import Foundation
import SwiftUI

// Estendiamo il tipo URL per conformarlo al protocollo Identifiable.
extension URL: Identifiable {
    
    // Usiamo il percorso assoluto del file (absoluteString) come suo ID univoco.
    // Poiché nessun file può avere lo stesso percorso, questo è un identificatore perfetto.
    public var id: String {
        self.absoluteString
    }
    
} // extension URL: Identifiable {

// Estensione per nascondere la tastiera
extension View {
    
    func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
    
} // extension View {
