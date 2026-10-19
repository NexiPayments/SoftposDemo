import Foundation
import App2AppSDK
import CocoaLumberjackSwift
import SwiftUI

/**
 *
 * Nexi Payment
 *
 * App state management
 *
 */
@MainActor // Ensures that @Published properties are updated on the main thread.
class AppState: ObservableObject, SdkDelegate {
    
    // Implement the SdkDelegate method that
    // receives the return message from the SDK
    nonisolated func resultFromSDK(message: String) {
        
        Task { @MainActor in
           
            // Retrieve the message
            self.transactionMessage = message
            
            // Notifies the application that a message
            // has been received from the SDK.
            // The MainView contains a NavigationLink
            // associated with the EsitoView, which
            // is launched only if the value of "mostraResultView" is true.
            self.showResult = true
           
        }
        
    } // nonisolated func resultFromSDK
    
    @Published var showResult                               = false
    @Published var transactionMessage           : String    = ""
    @Published var lastPaymentTerminalID        : String    = ""
    @Published var lastPaymentAmount            : Int       = 0
    @Published var lastPaymentTransactionId     : String    = ""
    @Published var isLoadingUsers               : Bool      = false
    @Published var isProcessing                 : Bool      = false
    
    init() {
        
        self.getLastPaymentDataFromUserDefaults()
        
    }
    
    func setDataLastPaymentInUserDefaults(amount: Int,
                                              transactionId: String
                                             ) {
        
        self.lastPaymentAmount        = amount
        self.lastPaymentTransactionId = transactionId
        
        UserDefaults.standard.set(transactionId, forKey: Constant.USER_DEFAULTS_LAST_PAYMENT_TRANSACTION_ID)
        UserDefaults.standard.set(amount, forKey: Constant.USER_DEFAULTS_LAST_PAYMENT_AMOUNT)
        
    }
    
    // Set the variables @published to the values ​​from the last transaction
    // performed, retrieving them from UserDefaults
    private func getLastPaymentDataFromUserDefaults() {
        
        self.lastPaymentAmount           = UserDefaults.standard.integer(forKey: Constant.USER_DEFAULTS_LAST_PAYMENT_AMOUNT)
        self.lastPaymentTerminalID       = UserDefaults.standard.string(forKey: Constant.USER_DEFAULTS_LAST_PAYMENT_TERMINAL_ID) ?? ""
        self.lastPaymentTransactionId    = UserDefaults.standard.string(forKey: Constant.USER_DEFAULTS_LAST_PAYMENT_TRANSACTION_ID) ?? ""
        
    }

} // class
