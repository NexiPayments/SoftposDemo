//
//  extensions.swift
//

import SwiftUI
import App2AppSDK

// Estendiamo il tipo URL per conformarlo al protocollo Identifiable.
extension URL: Identifiable {
    
    // Usiamo il percorso assoluto del file (absoluteString) come suo ID univoco.
    // Poiché nessun file può avere lo stesso percorso, questo è un identificatore perfetto.
    public var id: String {
        self.absoluteString
    }
    
} // extension URL: Identifiable {

extension View {

    // Estensione per nascondere la tastiera
    func hideKeyboard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
    
} // extension View {

extension UIApplication {
    
    func minimizeApp() {
        UIControl().sendAction(#selector(URLSessionTask.suspend), to: self, for: nil)
    }
}

extension App2AppSoftposTransaction: Encodable {
   
    enum CodingKeys: String, CodingKey {
       case transactionId
       case pan
       case transmissionDate
       case transmissionTime
       case amount
       case approvalCode
       case authorizationId
       case transactionType
       case batchNumber
       case stan
       case maskedPan
       case txnMti
       case orderId
       case kernelId
       case approvedStatus
       case merchantId
       case merchantAddress
       case terminalId
       case label
       case aid
       case transactionMethod
       case l3Mti
           
    }
    
    public func encode(to encoder: Encoder) throws {
            
        var container = encoder.container(keyedBy: CodingKeys.self)

        // Le proprietà (es. .transactionId) devono essere accessibili (public o internal).
        try container.encode(transactionId, forKey: .transactionId)
        try container.encode(pan, forKey: .pan)
        try container.encodeIfPresent(transmissionDate?.intValue, forKey: .transmissionDate)
        try container.encode(transmissionTime, forKey: .transmissionTime)
        try container.encodeIfPresent(amount?.intValue, forKey: .amount)
        try container.encode(approvalCode, forKey: .approvalCode)
        try container.encode(authorizationId, forKey: .authorizationId)
        try container.encode(transactionType, forKey: .transactionType)
        try container.encodeIfPresent(batchNumber?.intValue, forKey: .batchNumber)
        try container.encode(stan, forKey: .stan)
        try container.encode(maskedPan, forKey: .maskedPan)
        try container.encode(txnMti, forKey: .txnMti)
        try container.encode(orderId, forKey: .orderId)
        try container.encode(kernelId, forKey: .kernelId)
        try container.encodeIfPresent(approvedStatus?.intValue, forKey: .approvedStatus)
        try container.encode(merchantId, forKey: .merchantId)
        try container.encode(merchantAddress, forKey: .merchantAddress)
        try container.encode(terminalId, forKey: .terminalId)
        try container.encode(label, forKey: .label)
        try container.encode(aid, forKey: .aid)
        try container.encode(transactionMethod, forKey: .transactionMethod)
        try container.encode(l3Mti, forKey: .l3Mti)
        
    } // public func encode(to encoder: Encoder) throws
    
} // extension App2AppSoftposTransaction

extension App2AppSoftposTransactionResult : Encodable {
    
    enum CodingKeys: String, CodingKey {
        
        case KEY_TRX_IMPORT_VALUE
        case KEY_TRX_CURRENCY_CODE
        case refusalCode
        case customMessage
        case transactionId
        
    }
    
    public func encode(to encoder: Encoder) throws {
        
        var container = encoder.container(keyedBy: CodingKeys.self)
        
        // Le proprietà (es. .transactionId) devono essere accessibili (public o internal).
        try container.encode(App2AppSoftposTransactionResult.KEY_TRX_IMPORT_VALUE, forKey: .KEY_TRX_IMPORT_VALUE)
        try container.encode(App2AppSoftposTransactionResult.KEY_TRX_CURRENCY_CODE, forKey: .KEY_TRX_CURRENCY_CODE)
        try container.encode(refusalCode, forKey: .refusalCode)
        try container.encode(customMessage, forKey: .customMessage)
        try container.encode(transactionId, forKey: .transactionId)
        
    }
    
} // extension App2AppSoftposTransactionResult
    
