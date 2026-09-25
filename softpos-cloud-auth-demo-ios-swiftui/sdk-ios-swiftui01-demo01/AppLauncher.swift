//
//  AppLauncher.swift

import SwiftUI
import CocoaLumberjackSwift

@main
struct AppLauncher: App {
    
    @StateObject private var appState = AppState()
    
    private var appManager = AppManager.shared
    
    let fileLogger: DDFileLogger
   
    init() {
        
        // Crea un'istanza del tuo file manager personalizzato
        let customLogFileManager = CustomLogFilenameManager()
        fileLogger = DDFileLogger(logFileManager: customLogFileManager)
        
        setupLogging()
        
        // Il file logger viene passato al LogManager per la condivisione
        LogManager.shared.setFileLogger(fileLogger)
        
        DDLogInfo("AppLauncher -> crea istanza unica di AppState")
        let state = AppState()
        
        // con l'underscore si accede al property wrapper e gli si assegna l'appState appena creata.
        _appState = StateObject(wrappedValue: state)
        
        DDLogInfo("AppLauncher -> assegna appState ad appManager")
        appManager.appState = state
        
        DDLogInfo("AppLauncher -> assegna appState ad all'sdkDelegate di appManager")
        appManager.sdkDelegate = state
        
        
    } // init()
    
    var body: some Scene {
        
        WindowGroup {
            
            // Viene iniettato nell'environment l'istanza che gestisce
            // lo stato dell'app
            MainView()
                .environmentObject(appState)
                .onAppear {
                    
                    // Esegue l'assegnazione solo quando la ContentView appare
                    // e solo se non è già stata fatta.
                    if AppManager.shared.appState == nil {
                        AppManager.shared.appState = appState
                       
                    }
                } // onAppear
            
        } // WindowGroup
        
    } // body
    
    private func setupLogging() {
           
        // Aggiunge il logger della console di Xcode (per il debug)
        DDLog.add(DDOSLogger.sharedInstance)

        // Configura il logger per scrivere su file
        fileLogger.rollingFrequency = 60 * 60 * 24 // 24 ore
        fileLogger.logFileManager.maximumNumberOfLogFiles = 7 // Conserva i log degli ultimi 7 giorni
        
        DDLog.add(fileLogger)

        // Messaggio di avvio per verificare che il logging funzioni
        DDLogInfo("AppLauncher -> Avvio dell'applicazione e configurazione del logging completata.")
        
        
   } // private func setupLogging() {
    
} // struct
