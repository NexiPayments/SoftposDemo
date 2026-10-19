
import Foundation

/**
 *
 * Nexi Payments
 *
 * An Observable class that the app uses
 * for application state management.
 *
 */
class AppState: ObservableObject {
    
    // Contains the result of the last transaction performed.
    @Published var resultTransaction        : String  = ""
    
    // If set to true, the ResultView is displayed.
    @Published var showResultView                     = false
    
    // Data regarding the last successful payment transaction.
    // These are required to execute the Reversal method.
    @Published var LastPaymentTerminalId    : String  = ""
    @Published var LastPaymentAmount        : String  = ""
    
}
