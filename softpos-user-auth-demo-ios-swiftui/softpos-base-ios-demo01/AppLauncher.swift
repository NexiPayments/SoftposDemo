import SwiftUI

/**
 *
 * Nexi Payments
 *
 * App entry point
 *
 */
@main
struct AppLauncher: App {
    
    // Create an instance of the state manager
    @StateObject private var appState = AppState()

    var body: some Scene {
        
        WindowGroup {
            
            // ContentView will act as a router for the views
            ContentView()
               .environmentObject(appState) // Inject the state into the SwiftUI environment.
            
        }
        
    } // body
    
} // struct
