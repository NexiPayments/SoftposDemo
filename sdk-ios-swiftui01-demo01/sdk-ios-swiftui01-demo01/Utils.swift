//
//  Utils.swift
//

import Foundation
import SwiftUI
import App2AppSDK

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
    
    public static func formattaImportoPerDisplay(fromCentesimi centesimi: Int) -> String {
        
        // Converte i centesimi in Decimal
        let importoDecimal = Decimal(centesimi) / 100
        
        let formatter = NumberFormatter()
        formatter.numberStyle = .currency // Stile valuta (es. €1,55)
        formatter.locale = Locale.current // Usa le impostazioni locali dell'utente
        //forzare la locale se necessario: Locale(identifier: "it_IT")
        
        // NSNumber è un tipo "ponte" per far funzionare il formatter con Decimal
        return formatter.string(from: importoDecimal as NSNumber) ?? ""
    }
    
    
    public static func formattaValore(amount : String) -> String{
        
        // Se la stringa è vuota, non facciamo nulla
        guard !amount.isEmpty else { return ""}
        
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        
        // Impostiamo la localizzazione del formattatore a quella corrente del dispositivo
        // per interpretare correttamente sia "," che "." come separatore.
        formatter.locale = Locale.current
        
        // Tentiamo di convertire la stringa in un numero
        guard let numero = formatter.number(from: amount) else { return ""}
        
        // Se il valore numerico è 0 (l'utente ha inserito "0", "0,0", ecc.)
        // impostiamo la stringa a vuoto e usciamo dalla funzione.
        // Questo farà apparire il placeholder "valore".
        if numero.doubleValue == 0 {
            
            return ""
            
        }
        
        // Ora riconfiguriamo il formattatore per l'output desiderato
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        
        // Riconvertiamo il numero in una stringa formattata
        if let stringaFormattata = formatter.string(from: numero) {
            
            return stringaFormattata
            
        }
        
        return ""
        
    } //  private func formattaValore()
    
} // class
