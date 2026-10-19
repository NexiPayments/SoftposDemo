import Foundation
import CocoaLumberjackSwift

/**
 *
 * Nexi Payments
 *
 *  A helper class (Singleton) to easily access file logger information
 *  from anywhere in the application.
 *
 */
class LogManager {

    // Shared instance globally accessible via `LogManager.shared`
    static let shared = LogManager()
    
    // Private property to store a reference to the logger file.
    private var fileLogger: DDFileLogger?

    // Must be called at app startup
    // to pass a reference to the active file logger.
    func setFileLogger(_ logger: DDFileLogger) {
        
        self.fileLogger = logger
        DDLogInfo("LogManager.setFileLogger() -> configured with the file logger.")
        
    }

    /**
     *
     * Returns the URL of the most recent log file, ready to be shared.
     *
     */
    func getLogFileURL() -> URL? {
        
        // Verify that the fileLogger has been set
        guard let fileLogger = fileLogger else {
            
            DDLogError("LogManager.getLogFileURL() -> Attempted to access the log file, but the fileLogger has not been set in the LogManager.")
            return nil
            
        }
        
        // Returns the list of log file paths, sorted from newest to oldest.
        let logFilePaths = fileLogger.logFileManager.sortedLogFilePaths
        
        // Retrieves the first element of the list, i.e., the most recent file.
        if let lastLogFilePath = logFilePaths.first {
            
            // Creates and returns a URL object from the file path.
            return URL(fileURLWithPath: lastLogFilePath)
            
        }
        
        DDLogWarn("LogManager.getLogFileURL() -> No log file found to share.")
        return nil
        
    } // func getLogFileURL()
    
    
    // Returns an array of URLs for all archived log files,
    // sorted from newest to oldest.
    func getLogFileURLs() -> [URL] {
        
        guard let fileLogger = fileLogger else {
            
            DDLogError("LogManager.getLogFileURLs() -> Attempted to access the log file, but the fileLogger has not been set in the LogManager.")
            
            return []
            
        }
        
        let logFilePaths = fileLogger.logFileManager.sortedLogFilePaths
        
        // Converts the array of paths (String) into an array of URLs
        let logFileURLs = logFilePaths.map { path in
            return URL(fileURLWithPath: path)
        }
        
        return logFileURLs
        
    } //  func getLogFileURLs() -> [URL]
    
} // class
