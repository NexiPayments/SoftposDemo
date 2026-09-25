//
//  CustomLogFileManager.swift
//

import Foundation
import CocoaLumberjackSwift

class CustomLogFilenameManager: DDLogFileManagerDefault {

    // Fornisce un nome di file personalizzato.
    // Viene chiamato ogni volta che CocoaLumberjack ha bisogno di creare un nuovo file di log.
    override var newLogFileName: String {
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd_HH-mm-ss"
        let timestamp = dateFormatter.string(from: Date())
        
        let appName = Bundle.main.object(forInfoDictionaryKey: "CFBundleName") as? String ?? "AppName"
        
        return "\(appName)_\(timestamp).log"
        
    } // override var newLogFileName

    override func isLogFile(withName fileName: String) -> Bool {
        
        return fileName.hasSuffix(".log")
        
    } // override func isLogFile(withName
    
} // class CustomLogFileManager
