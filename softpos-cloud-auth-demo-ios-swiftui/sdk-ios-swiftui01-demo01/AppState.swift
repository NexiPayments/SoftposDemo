//
//  AppState.swift
//
//

import Foundation
import App2AppSDK
import CocoaLumberjackSwift
import SwiftUI

// Classe utilizzata per la gestione dello stato dell'app
@MainActor // Assicura che le proprietà @Published vengano aggiornate sul thread principale
class AppState: ObservableObject, SdkDelegate {
    
    // Implementa il metodo dell'SdkDelegate che
    // riceve il messaggio di ritorno dall'SDK
    nonisolated func resultFromSDK(message: String) {
        
        Task { @MainActor in
           
            // Recupera il messaggio
            self.messaggioTransazione = message
            
            // Informa l'applicazione che è arrivato
            // un messaggio dall'SDK.
            // Nella MainView è presente un NavigationLink
            // associato alla EsitoView che viene
            // avviata solo se il valore di "mostraResultView" è true
            self.mostraResultView = true
           
        }
        
    } // nonisolated func resultFromSDK
    
    @Published var mostraResultView                         = false
    @Published var messaggioTransazione         : String    = ""
    @Published var terminalIdUltimoPagamento    : String    = ""
    @Published var amountUltimoPagamento        : Int       = 0
    @Published var transactionIdUltimoPagamento : String    = ""
    @Published var isLoadingUtenti              : Bool      = false
    @Published var isProcessing                 : Bool      = false
    
    init() {
        
        self.getDatiUltimoPagamentoFromUserDefaults()
        
    }
    
    func setDatiUltimoPagamentoInUserDefaults(amount: Int,
                                              transactionId: String
                                             ) {
        
        self.amountUltimoPagamento        = amount
        self.transactionIdUltimoPagamento = transactionId
        
        UserDefaults.standard.set(transactionId, forKey: Constant.USER_DEFAULTS_TRANSACTION_ID_ULTIMO_PAGAMENTO)
        UserDefaults.standard.set(amount, forKey: Constant.USER_DEFAULTS_AMOUNT_ULTIMO_PAGAMENTO)
        
    }
    
    // Imposta nele variabili @published i valori relativi all'ultima transazione
    // effettuata, recuparandoli dalle UserDefaults
    private func getDatiUltimoPagamentoFromUserDefaults() {
        
        self.amountUltimoPagamento           = UserDefaults.standard.integer(forKey: Constant.USER_DEFAULTS_AMOUNT_ULTIMO_PAGAMENTO)
        self.terminalIdUltimoPagamento       = UserDefaults.standard.string(forKey: Constant.USER_DEFAULTS_TERMINAL_ID_ULTIMO_PAGAMENTO) ?? ""
        self.transactionIdUltimoPagamento    = UserDefaults.standard.string(forKey: Constant.USER_DEFAULTS_TRANSACTION_ID_ULTIMO_PAGAMENTO) ?? ""
        
    }

} // class
