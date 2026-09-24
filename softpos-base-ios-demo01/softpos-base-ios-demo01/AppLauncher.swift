//
//  SwiftUI_25_08_30_02__AppIntercomApp.swift
//  SwiftUI_25-08-30_02_ AppIntercom
//
//  Created by MAC-01 on 30/08/25.
//

import SwiftUI

@main
struct AppLauncher: App {
    
    // Creiamo un'istanza del nostro gestore di stato
    @StateObject private var appState = AppState()

    var body: some Scene {
        
        WindowGroup {
            
            // ContentView farà da router per le viste
            ContentView()
               .environmentObject(appState) // Iniettiamo lo stato nell'ambiente SwiftUI
            
        }
        
    } // body
    
    
   
    
} // struct
