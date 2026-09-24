//
//  AppVersionView.swift
//  SwiftUI_25-08-30_02_ AppIntercom
//
//  Created by MAC-01 on 31/08/25.
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
