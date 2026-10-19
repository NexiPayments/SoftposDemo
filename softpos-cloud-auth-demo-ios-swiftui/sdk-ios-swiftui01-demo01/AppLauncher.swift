import SwiftUI
import CocoaLumberjackSwift

/**
 *
 * Nexi Payment
 *
 * App Launcher
 *
 */
@main
struct AppLauncher: App {
    
    @StateObject private var appState = AppState()
    
    private var appManager = AppManager.shared
    
    let fileLogger: DDFileLogger
   
    init() {
        
        // Create an instance of the custom file manager
        let customLogFileManager = CustomLogFilenameManager()
        fileLogger = DDFileLogger(logFileManager: customLogFileManager)
        
        setupLogging()
        
        // The file logger is passed to the LogManager for sharing
        LogManager.shared.setFileLogger(fileLogger)
        
        DDLogInfo("AppLauncher -> create a single instance of AppState")
        let state = AppState()
        
        // The underscore is used to access the property wrapper,
        // and the newly created appState is assigned to it.
        _appState = StateObject(wrappedValue: state)
        
        DDLogInfo("AppLauncher -> assign appState to appManager")
        appManager.appState = state
        
        DDLogInfo("AppLauncher -> assign appState to the appManager's sdkDelegate")
        appManager.sdkDelegate = state
        
        
    } // init()
    
    var body: some Scene {
        
        WindowGroup {
            
            // The instance that manages the app's state
            // is injected into the environment.
            MainView()
                .environmentObject(appState)
                .onAppear {
                    
                    // Performs the assignment only when the ContentView appears
                    // and only if it hasn't already been done.
                    if AppManager.shared.appState == nil {
                        AppManager.shared.appState = appState
                       
                    }
                } // onAppear
            
        } // WindowGroup
        
    } // body
    
    private func setupLogging() {
           
        // Adds the Xcode console logger (for debugging)
        DDLog.add(DDOSLogger.sharedInstance)

        // Configure the logger to write to a file
        fileLogger.rollingFrequency = 60 * 60 * 24 // 24 hours
        fileLogger.logFileManager.maximumNumberOfLogFiles = 7 // Keep logs from the last 7 days
        
        DDLog.add(fileLogger)

        DDLogInfo("AppLauncher -> Application startup and logging configuration completed.")
        
        
   } // private func setupLogging() {
    
} // struct
