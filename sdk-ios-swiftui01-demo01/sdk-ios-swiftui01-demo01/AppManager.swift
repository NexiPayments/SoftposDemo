//
//  AppManager.swift
//
//
// 
//

import Foundation
import App2AppSDK
import UIKit
import CocoaLumberjackSwift
import SwiftUI

// Classe singleton che gestisce l'interfacciamento con l'SDK
class AppManager {
    
    var sdkDelegate : SdkDelegate?
    
    var appState : AppState?
    
    static var shared = AppManager()
    
    var app2AppManager: App2AppManager!
       
    private init() {
       
    }
    
    // Inizializza l'SDK
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
    
   
    // Quando si avvia un metodo dell'SDK, questi richiede sempre il metodo onGetRequestUri.
    // all'interno di questo metodo lo sviluppatore deve implementare il metodo del suo
    // backoffice che consente il recupero effettivo della request_uri
    public func onGetRequestUri(deviceld: String) -> String {
        
        var requestUri = ""
        let semaphore = DispatchSemaphore(value: 0)
        
        // Create a semaphore for synchronization
        Task {
            
            do {
                
                // Metodo implementato dallo sviluppatore per recuperare la request_uri
                requestUri = try await BackendService.getRequestUri(deviceId: deviceld)
                
            } catch {
                
                DDLogError("\(Constant.APP_MANAGER) -> onGetRequestUri -> errore \(NetworkError.decodingError)")
                
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
   
    func getReceipe(transactionId : String,
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
                        
                       DDLogInfo("AppManager -> operation listener onSucces() :  Impossibile convertire i dati JSON in una stringa. \n")
                        
                    }
                    
                } catch {
                   
                    DDLogError("AppManager -> operation listener onSucces() :  Errore durante la codifica JSON: \(error.localizedDescription) \n")
                    
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
                                               
                        errore = "Impossibile convertire i dati JSON in una stringa."
                    }
                    
                } catch {
                                
                    errore = "Errore durante la codifica JSON: \(error.localizedDescription)"
                    
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
    
    
    // Pay eseguito
    func app2AppManagerSoftPosEventsOnDidPaymentSuccess(_ result: App2AppSDK.App2AppSoftposTransactionResult) {
       
        let customMessage = result.customMessage
        let importValue = Int(String(customMessage["amount"] ?? "0"))
        
        DispatchQueue.main.async {
            // Questo blocco di codice verrà eseguito sul thread principale.
            
            if let appState = self.appState {
                
                appState.setDatiUltimoPagamentoInUserDefaults(amount: importValue ?? 0,
                                                              transactionId: result.transactionId
                                                             )
                
                DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentSuccess -> salvati sul UserDefaults i dati della : transazione con ID : \(result.transactionId) e importo : \(importValue ?? 0)")
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
                        
            message = "Errore nella conversione in JSON"
        }
        
        do {
            
            let jsonData = try encoder.encode(customMessage)

            if let jsonString = String(data: jsonData, encoding: .utf8) {
                
                resultMessage = jsonString
                
            }
            
        } catch {
                        
            resultMessage = "Errore nella conversione in JSON"
            
        }
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentSuccess result : \(resultMessage)")
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentSuccess -> message: \(message)")
        
        sdkDelegate?.resultFromSDK(message: message)
        
    } // func app2AppManagerSoftPosEventsOnDidPaymentSuccess(_ result: App2AppSDK.App2AppSoftposTransactionResult)
    
    // Refund eseguito
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
                        
            message = "Errore nella conversione in JSON"
            
        }
        
        do {
            
            let jsonData = try encoder.encode(customMessage)

            if let jsonString = String(data: jsonData, encoding: .utf8) {
                
                resultMessage = jsonString
                
            }
            
        } catch {
                        
            resultMessage = "Errore nella conversione in JSON"
            
        }
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundSuccess result : \(resultMessage)")
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundSuccess message : \(message)")
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundSuccess : avviata sdkDelegate?.resultRefund(resultTransaction: \(resultMessage))")
        
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
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidPaymentGetReceiptSuccess : avviata sdkDelegate?.receivedReceipt(receipt: \(receipt))")
        
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
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidRefundGetReceiptSuccess : avviata sdkDelegate?.receivedReceipt(receipt : \(receipt))")
        
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
        
        DDLogInfo("\(Constant.APP_MANAGER) -> app2AppManagerSoftPosEventsOnDidGetListTransactions : avviata sdkDelegate?.receivedListTransaction(listTransaction : \(list))")
        
        sdkDelegate?.resultFromSDK(message: message)
       
    } // func app2AppManagerSoftPosEventsOnDidGetListTransactions

} // extension

