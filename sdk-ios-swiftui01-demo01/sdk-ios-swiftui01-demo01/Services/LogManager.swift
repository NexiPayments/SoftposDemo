//
//  LogManager.swift
//

import Foundation
import CocoaLumberjackSwift

// Una classe helper (Singleton) per accedere facilmente alle informazioni
// del logger di file da qualsiasi punto dell'applicazione.
class LogManager {

    // Istanza condivisa accessibile globalmente tramite `LogManager.shared`
    static let shared = LogManager()
    
    // Una proprietà privata per conservare un riferimento al file logger.
    private var fileLogger: DDFileLogger?

    // Deve essere chiamato all'avvio dell'app
    // per passare un riferimento al file logger attivo.
    func setFileLogger(_ logger: DDFileLogger) {
        
        self.fileLogger = logger
        DDLogInfo("LogManager.setFileLogger() -> configurato con il file logger.")
        
    }

    // Restituisce l'URL del file di log più recente, pronto per essere condiviso.
    func getLogFileURL() -> URL? {
        
        // Verifica che il fileLogger sia stato impostato
        guard let fileLogger = fileLogger else {
            
            DDLogError("LogManager.getLogFileURL() -> Tentativo di accedere al file di log, ma il fileLogger non è stato impostato in LogManager.")
            return nil
            
        }
        
        // Restituisce la lista dei percorsi dei file di log, ordinati dal più recente al più vecchio.
        let logFilePaths = fileLogger.logFileManager.sortedLogFilePaths
        
        // Recupero il primo elemento della lista, che è il file più recente.
        if let lastLogFilePath = logFilePaths.first {
            // Crea e restituisce un oggetto URL dal percorso del file.
            return URL(fileURLWithPath: lastLogFilePath)
        }
        
        DDLogWarn("LogManager.getLogFileURL() -> Nessun file di log trovato da condividere.")
        return nil
        
    } // func getLogFileURL()
    
    
    // Restituisce un array di URL di tutti i file di log archiviati,
    // ordinati dal più recente al più vecchio.
    func getLogFileURLs() -> [URL] {
        
        guard let fileLogger = fileLogger else {
            
            DDLogError("LogManager.getLogFileURLs() -> Tentativo di accedere ai file di log, ma il fileLogger non è stato impostato in LogManager.")
            return []
            
        }
        
        let logFilePaths = fileLogger.logFileManager.sortedLogFilePaths
        
        // Converte l'array di percorsi (String) in un array di URL
        let logFileURLs = logFilePaths.map { path in
            return URL(fileURLWithPath: path)
        }
        
        return logFileURLs
        
    } //  func getLogFileURLs() -> [URL]
    
} // class
