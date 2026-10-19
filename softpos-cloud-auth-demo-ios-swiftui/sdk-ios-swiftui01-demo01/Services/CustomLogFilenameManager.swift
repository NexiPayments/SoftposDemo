import Foundation
import CocoaLumberjackSwift

/**
 *
 * Nexi Payments
 *
 * Customized management of the app's logging system
 *
 */
class CustomLogFilenameManager: DDLogFileManagerDefault {

    // Provides a custom file name.
    // Called whenever CocoaLumberjack needs to create a new log file.
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
