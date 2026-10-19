import Foundation
import App2AppSDK
import UIKit
import CocoaLumberjackSwift
import SwiftUI

/**
 *
 * Nexi Payments
 *
 * Singleton class that handles interfacing with the SDK
 *
 */
class AppManager {
    
    var sdkDelegate : SdkDelegate?
    
    var appState : AppState?
    
    static var shared = AppManager()
    
    var app2AppManager: App2AppManager!
       
    private init() {
       
    }
    
    // InitSDK
    public func initSDK(appId : String,
                        redirectUri : String,
                        merchantId : String,
                        merchantUsername : String,
                        deeplinkScheme : String,
                        domain : App2AppDomain
                       ){
        
        let appData =  AppData(appId: appId,
                               merchantId: merchantId,
                               redirectUri: redirectUri,
                               merchantUsername: merchantUsername,
                               deeplinkScheme: deeplinkScheme,
                               domain: domain
                             )
        
        var jsonString: String = ""
        let jsonEncoder = JSONEncoder()
        
        jsonEncoder.outputFormatting = [.prettyPrinted]
        
        do {
            
            let jsonData = try jsonEncoder.encode(appData)
            
            jsonString = String(data: jsonData, encoding: .utf8) ?? ""
            
        } catch {
            
            jsonString = ""
            
        }
        
        DDLogInfo("\(Constant.APP_MANAGER) -> initSDK() -> AppData : \(jsonString)")
        
        app2AppManager = App2AppManager(appData: appData,
                                        delegate: self,
                                        onGetRequestUri: onGetRequestUri(deviceld:)
                                        )
        
    } // public func initSDK
    
   
    // Whenever an SDK method is invoked, it always calls the onGetRequestUri method.
    // Within this method, the developer must implement a call to their own
    // backend that enables the actual retrieval of the request_uri.
    public func onGetRequestUri(deviceld: String) -> String {
        
        var requestUri = ""
        let semaphore = DispatchSemaphore(value: 0)
        
        // Create a semaphore for synchronization
        Task {
            
            do {
                
                // Method implemented by the developer to retrieve the request_uri
                requestUri = try await BackendService.getRequestUri(deviceId: deviceld)
                
            } catch {
                
                DDLogError("\(Constant.APP_MANAGER) -> onGetRequestUri -> error \(NetworkError.decodingError)")
                
                requestUri = ""
            }
            
           semaphore.signal() // Signal that the async task has completed
            
        }
        
        semaphore.wait() // Wait for the async task to finish return requestUri
        
        return requestUri
        
    } // public func onGetRequestUri(deviceld: String) -> String {
    
    func payment(importValue : Int,
                 currency : CurrencyEnum,
                 tags : [String : String]
                ) {
        
        let paymentData = Utils.getPaymentData(importValue: importValue,
                                               currency: CurrencyEnum.euro,
                                               tags: tags
                                              )
        
        DispatchQueue.main.async {
            self.appState?.isProcessing = true
        }
        
        app2AppManager.pay(paymentData: paymentData,
                           operationListener: getOperationListener()
                          )
            
        
    } // func payment
    
    func refund(transactionId : String,
                importValue : Int
               ) {
        
        let refundData = Utils.getRefundData(transactionId: transactionId,
                                             importValue: importValue
                                            )
        
        DispatchQueue.main.async {
            self.appState?.isProcessing = true
        }
        
        app2AppManager.refund(refundData: refundData,
                              operationListener: getOperationListener()
                             )
        
    } // func refund
   
    func getReceipt(transactionId : String,
                    receiptType : ReceiptType
                   ) {
        
        let receiptData = Utils.getReceiptData(transactionId: transactionId,
                                              isTransactionSuccess: true
                                            )
        
        DispatchQueue.main.async {
            self.appState?.isProcessing = true
        }
        
        switch receiptType {
            
            case .payment:
                
                app2AppManager.getPayReceipt(receiptData: receiptData,
                                operationListener: getOperationListener()
                               )
            
            case .refund:
            
                app2AppManager.getRefundReceipt(receiptData: receiptData,
                                                operationListener: getOperationListener()
                                               )
            
        } // switch
            
    } // func getReceipe
    
    func getListTransactions() {
        
        DispatchQueue.main.async {
            self.appState?.isProcessing = true
        }
        
        app2AppManager.getListTransactions(operationListener: getOperationListener())
                
    } // func refund
    
    func logout(){
        
        app2AppManager.logout(operationListener: getOperationListener())
        
    }
    
    func getOperationListener() -> OperationListener{
        
        let operationListener = OperationListener(
            
            onSuccess: { (response: A2ASDKResponse?)  in
                
                let encoder = JSONEncoder()
                
                do {
                 
                    let jsonData = try encoder.encode(response)
                
                    if let jsonString = String(data: jsonData, encoding: .utf8) {
                        
                        DDLogInfo("AppManager -> operation listener onSucces() : \(jsonString) \n")
                    
                    } else {
                        
                       DDLogInfo("AppManager -> operation listener onSucces() :  Unable to convert JSON data to a string. \n")
                        
                    }
                    
                } catch {
                   
                    DDLogError("AppManager -> operation listener onSucces() :  Error during JSON encoding: \(error.localizedDescription) \n")
                    
                }
               
                DispatchQueue.main.async {
                    
                    self.appState?.isProcessing = false
                    
                }
                
            }, // onSuccess
            onFailure: { (response: A2ASDKResponse?) in
                
                var errore: String = ""
                
                let encoder = JSONEncoder()
                
                do {
                 
                    let jsonData = try encoder.encode(response)
                
                    if let jsonString = String(data: jsonData, encoding: .utf8) {
                    
                        errore = jsonString
                    
                    } else {
                                               
                        errore = "Unable to convert JSON data to a string."
                    }
                    
                } catch {
                                
                    errore = "Error during JSON encoding: \(error.localizedDescription)"
                    
                }
                
                DDLogInfo("AppManager -> operation listener onFailure() : \(errore) \n")
                
                DispatchQueue.main.async {
                    
                    self.appState?.isProcessing = false
                    
                }
                
                self.sdkDelegate?.resultFromSDK(message: errore)
                
            } // onFailure()
            
        ) // let operationListener
        
        return operationListener
        
    } // func setupOperationListener() {
    
    
} // class

// Extension that handles SDK response events
extension AppManager: App2AppManagerSoftPosEventsDelegate {
    
    func app2AppManagerSoftPosEventsOnReaderInitProgress(_ progress: Int) {
        
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnReaderInitProgress : \(progress)")
        
    }
    
    func app2AppManagerSoftPosEventsOnDidCardReaderEvent(_ event: App2AppSDK.App2AppSoftposReaderEvent) {
      
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidCardReaderEvent : \(event)")
        
        var message : String = ""
        
        switch (event) {
            
            case .readCancelled :
                
                message = "Read cancelled"
            
                break
            
            case .readNotCompleted :
                
               message = "Read not completed"
            
                break
            
            default :
            
                break
        }
        
        //sdkDelegate?.resultFromSDK(message: message)
        
    } // func app2AppManagerSoftPosEventsOnDidCardReaderEvent(_ event: App2AppSDK.App2AppSoftposReaderEvent) {
    
    // payment completed
    func app2AppManagerSoftPosEventsOnDidPaymentSuccess(_ result: App2AppSDK.App2AppSoftposTransactionResult) {
       
        let customMessage = result.customMessage
        let importValue = Int(String(customMessage["amount"] ?? "0"))
        
        DispatchQueue.main.async {
            
            if let appState = self.appState {
                
                // To process a refund for a transaction, it is necessary to store
                // the amount and the transaction ID of the payment transaction.
                
                appState.setDataLastPaymentInUserDefaults(amount: importValue ?? 0,
                                                          transactionId: result.transactionId
                                                          )
                
                DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentSuccess -> saved the transaction data (with ID): \(result.transactionId) and amount : \(importValue ?? 0) to UserDefaults")
            }
            
        }
        
        var message = ""
        var resultMessage = ""
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        
        do {
            
            let jsonData = try encoder.encode(customMessage)

            if let jsonString = String(data: jsonData, encoding: .utf8) {
                
                message = jsonString
                
            }
            
        } catch {
                        
            message = "Error during JSON conversion"
        }
        
        do {
            
            let jsonData = try encoder.encode(customMessage)

            if let jsonString = String(data: jsonData, encoding: .utf8) {
                
                resultMessage = jsonString
                
            }
            
        } catch {
                        
            resultMessage = "Error during JSON conversion"
            
        }
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentSuccess result : \(resultMessage)")
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentSuccess -> message: \(message)")
        
        sdkDelegate?.resultFromSDK(message: message)
        
    } // func app2AppManagerSoftPosEventsOnDidPaymentSuccess(_ result: App2AppSDK.App2AppSoftposTransactionResult)
    
    // Refund completed
    func app2AppManagerSoftPosEventsOnDidRefundSuccess(_ result: App2AppSDK.App2AppSoftposTransactionResult) {
        
        var message = ""
        
        var resultMessage = ""
        
        let customMessage = result.customMessage
        
        let encoder = JSONEncoder()
        
        encoder.outputFormatting = .prettyPrinted
        
        do {
            
            let jsonData = try encoder.encode(customMessage)

            if let jsonString = String(data: jsonData, encoding: .utf8) {
                
                message = jsonString
                
            }
            
        } catch {
                        
            message = "Error during JSON conversion"
            
        }
        
        do {
            
            let jsonData = try encoder.encode(customMessage)

            if let jsonString = String(data: jsonData, encoding: .utf8) {
                
                resultMessage = jsonString
                
            }
            
        } catch {
                        
            resultMessage = "Error during JSON conversion"
            
        }
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundSuccess result : \(resultMessage)")
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundSuccess message : \(message)")
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundSuccess : start sdkDelegate?.resultRefund(resultTransaction: \(resultMessage))")
        
        sdkDelegate?.resultFromSDK(message: message)
        
    } // func app2AppManagerSoftPosEventsOnDidRefundSuccess(_ result: App2AppSDK.App2AppSoftposTransactionResult) {
    
    func app2AppManagerSoftPosEventsOnDidPaymentGetReceiptSuccess(_ receipt: App2AppSDK.App2AppSoftPosReceipt) {
       
        var message = ""
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        
        let data = try! encoder.encode(receipt)
        
        let str = String(data: data, encoding: .utf8)!
        
        message = str
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentGetReceiptSuccess receipt : \(receipt)")
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentGetReceiptSuccess message : \(message)")
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentGetReceiptSuccess : start sdkDelegate?.receivedReceipt(receipt: \(receipt))")
        
        sdkDelegate?.resultFromSDK(message: message)
        
    } // func app2AppManagerSoftPosEventsOnDidPaymentGetReceiptSuccess
    
    func app2AppManagerSoftPosEventsOnDidRefundGetReceiptSuccess(_ receipt: App2AppSDK.App2AppSoftPosReceipt) {
        
        var message = ""
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        
        let data = try! encoder.encode(receipt)
        
        let str = String(data: data, encoding: .utf8)!
        
        message = str
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundGetReceiptSuccess receipt : \(receipt)")
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundGetReceiptSuccess message : \(message)")
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundGetReceiptSuccess : start sdkDelegate?.receivedReceipt(receipt : \(receipt))")
        
        sdkDelegate?.resultFromSDK(message: message)
        
    } // func app2AppManagerSoftPosEventsOnDidRefundGetReceiptSuccess
    
    func app2AppManagerSoftPosEventsOnDidGetListTransactions(_ list: [App2AppSDK.App2AppSoftposTransaction]) {
        
        var message = ""
        
        let encoder = JSONEncoder()
        encoder.outputFormatting = .prettyPrinted
        
        let data = try! encoder.encode(list)
        
        let str = String(data: data, encoding: .utf8)!
        
        message = str
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidGetListTransactions list : \(list)")
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidGetListTransactions message : \(message)")
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidGetListTransactions : start sdkDelegate?.receivedListTransaction(listTransaction : \(list))")
        
        sdkDelegate?.resultFromSDK(message: message)
       
    } // func app2AppManagerSoftPosEventsOnDidGetListTransactions

} // extension

