//
//  AppState.swift
//  SwiftUI_25-08-30_02_ AppIntercom
//
//  Created by MAC-01 on 30/08/25.
//

import Foundation

// Un oggetto osservabile che l'intera app può usare per cambiare stato
class AppState: ObservableObject {
    
    @Published var risultatoOperazione:         String  = ""
    @Published var mostraResultView                     = false
    @Published var terminalIdUltimoPagamento:   String  = ""
    @Published var amountUltimoPagamento:       String  = ""
    
}
