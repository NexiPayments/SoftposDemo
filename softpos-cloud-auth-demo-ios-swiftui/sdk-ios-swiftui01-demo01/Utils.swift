import Foundation
import SwiftUI
import App2AppSDK

/**
 *
 * Nexi Payments
 *
 * defines a set of helper methods for the application
 *
 */
class Utils {
    
    public static func colorDomain(domain: String) -> Color {
        
        switch domain {
            
            case "STAGING":
                return .green
            
            case "PRODUCTION":
                return .red
            
            default:
                return .primary
            
        } // switch
        
    } // public static func colorDomain
    
    public static func getPaymentData(importValue : Int,
                                      currency : CurrencyEnum,
                                      tags : [String : String]
                                     ) -> PaymentData {
        
        return PaymentData(importValue: importValue,
                           currency: currency,
                           tags: tags
                          )
        
    }
    
    public static func getRefundData(transactionId : String,
                                     importValue : Int
                                    ) -> RefundData {
        
        return RefundData(transactionId: transactionId,
                          importValue: importValue,
                          currency: CurrencyEnum.euro
                         )
        
    }
    
    public static func  getReceiptData(transactionId : String,
                                       isTransactionSuccess : Bool
                                      ) -> ReceiptData {
        
        return ReceiptData(transactionId: transactionId,
                           isTransactionSuccess: isTransactionSuccess
                          )
        
    }
    
    public static func formatAmountForDisplay(fromCentes cents: Int) -> String {
        
        let importoDecimal = Decimal(cents) / 100
        
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency
        formatter.locale = Locale.current
        
        return formatter.string(from: importoDecimal as NSNumber) ?? ""
        
    }
    
    
    public static func formatAmount(amount : String) -> String{
        
        guard !amount.isEmpty else { return ""}
        
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        
        formatter.locale = Locale.current
        
        guard let numero = formatter.number(from: amount) else { return ""}
        
        if numero.doubleValue == 0 {
            
            return ""
            
        }
        
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        
        if let stringaFormattata = formatter.string(from: numero) {
            
            return stringaFormattata
            
        }
        
        return ""
        
    } //  private func formatAmount()
    
} // class
