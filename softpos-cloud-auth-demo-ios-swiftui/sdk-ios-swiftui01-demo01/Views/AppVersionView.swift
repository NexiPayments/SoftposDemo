//
//  AppVersionView.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 05/11/25.
//

import SwiftUI

struct AppVersionView: View {
    let version: String
    let buildNumber: String
        
    // Inizializzatore che recupera automaticamente i dati dal Bundle
    init() {
        
        self.version = Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as? String ?? "N/A"
        
        self.buildNumber = Bundle.main.object(forInfoDictionaryKey: "CFBundleVersion") as? String ?? "N/A"
    }
    
    var body: some View {
        
        Text("Versione \(version) (\(buildNumber))")
            .font(.title)
            .foregroundColor(.secondary)
            
        }
}

#Preview {
    AppVersionView()
}
