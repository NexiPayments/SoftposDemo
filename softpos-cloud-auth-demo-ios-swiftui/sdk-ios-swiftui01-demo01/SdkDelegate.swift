import App2AppSDK

/**
 *
 * Nexi Payments
 *
 *
 */
protocol SdkDelegate {
    
    // The method is implemented in the AppState class,
    // which, being an Observable, notifies listening
    // objects that a message has been received from the SDK
    // following an initiated operation.
    func resultFromSDK(message : String)
  
    
}
