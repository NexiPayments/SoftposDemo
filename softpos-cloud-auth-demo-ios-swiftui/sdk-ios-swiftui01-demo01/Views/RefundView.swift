//
//  Refund.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 05/11/25.
//

import SwiftUI
import App2AppSDK
import CocoaLumberjackSwift

struct RefundView: View {
    
    @ObservedObject var appState : AppState
   
    @State var amount : String = ""
    @State var transactionId : String = ""
    @FocusState private var isTextFieldAmountFocused    : Bool
    
    var appManager = AppManager.shared
    @Environment(\.dismiss) var dismiss
    
    @State private var alertInfo: AlertInfo?
    
    // Proprietà calcolata per la validazione
    private var isFormValid: Bool {
       
        // La form è valida se il campo non è vuoto.
        let amountValida = !amount.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
        let transactionIdValida = !transactionId.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
        
        return amountValida && transactionIdValida
        
    }
    
    var body: some View {
        
        ZStack {
            
            VStack(spacing: 20) {
                
                Text("Refund inserimento dati - SDK")
                    .font(.title3)
                
                Form {
                    
                    TextField("transactionId", text: $transactionId)
                        .font(.system(size: 24))

                    TextField("amount", text: $amount)
                        .keyboardType(.decimalPad)
                        .font(.system(size: 24))
                        .focused($isTextFieldAmountFocused)
                        .onChange(of: amount) { nuovoValore in
                            
                            validaAmount(newValue: amount)
                            
                        }
                        .onChange(of: isTextFieldAmountFocused) { nuovoValore in
                            
                            // Questo blocco viene eseguito QUANDO il focus cambia
                            if !isTextFieldAmountFocused {
                                
                                // Se il campo non è più attivo, formattiamo il numero
                                amount = Utils.formattaValore(amount : amount)
                                
                            }
                            
                        } // .onChange(of: isTextFieldAmountFocused)
                    
                }.applyDemoBackground() // Form
                /*
                .toolbar {
                    // Aggiunge un pulsante per chiudere la tastiera
                    ToolbarItemGroup(placement: .keyboard) {
                        
                        Spacer()
                        
                        Button("Fatto") {
                            // Nasconde la tastiera
                            UIApplication
                                .shared.sendAction(#selector(UIResponder
                                    .resignFirstResponder
                                ),
                                                   to: nil,
                                                   from: nil,
                                                   for: nil
                                )
                        } // Button
                        
                    } // ToolbarItemGroup
                    
                } // From-> toolbar
                */
                
                Button("Refund") {
                    // Avvia refund
                    
                    let strAmount = amount
                    
                    // Aggiunge i parametri per avviare il pagamento
                    let decimalSeparator = Locale.current.decimalSeparator ?? "."
                    let amountModificata = strAmount.replacingOccurrences(of: decimalSeparator, with: "")
                    
                    let importValue = Int(amountModificata) ?? 0
                    
                    DDLogInfo("RefundView -> avviato refund, import : \(importValue)  transactionId : \(transactionId)")
                    
                    Task {
                        
                        appManager.refund(transactionId: transactionId,
                                          importValue: importValue
                                       
                                         )
                        
                    } // Task
                    
                }
                .font(.title)
                .padding()
                .buttonStyle(DemoButtonStyle())
                .disabled(!isFormValid)
                
                Spacer()
                
            } // VStack
            .applyDemoBackground()
            .onTapGesture {
                hideKeyboard()
            }
            // Applica il modificatore per ignorare l'area sicura della tastiera
            .ignoresSafeArea(.keyboard)
            .onAppear() {
                
                transactionId = appState.transactionIdUltimoPagamento
                amount = Utils.formattaImportoPerDisplay(fromCentesimi : appState.amountUltimoPagamento)
                
            } // onAppear
            
            if appState.isProcessing {
                       
                Color.black.opacity(0.5).ignoresSafeArea()
                
                ProgressView("Refund in corso...")
                    .padding(20)
                    .background(Color.white)
                    .cornerRadius(10)
                    .shadow(radius: 5)
            }
            
        } // ZStack
        .alert(item: $alertInfo) { info in
                    Alert(
                        title: Text(info.title),
                        message: Text(info.message),
                        dismissButton: .default(Text("OK"))
                    )
        }
        
        
    } // body
    
    private func validaAmount(newValue: String) {
           
        // Ottiene il separatore decimale corretto per le impostazioni locali
        let decimalSeparator = Locale.current.decimalSeparator ?? "."
           
        // 1. Filtra l'input per mantenere solo i numeri e il separatore
        var valueFiltered = newValue.filter { "0123456789".contains($0) || String($0) == decimalSeparator }
           
        // 2. Gestisce la presenza di più separatori decimali
        let components = valueFiltered.components(separatedBy: decimalSeparator)
           
        // Se ci sono più di 2 componenti, significa che è stato inserito più di un separatore
        // Esempio: "12,34,56" -> componenti = ["12", "34", "56"] (conteggio = 3)
        if components.count > 2 {
            
            // Ricostruisce la stringa mantenendo solo il primo separatore
            let parteIntera = components[0]
            let parteDecimale = components.dropFirst().joined()
            
            valueFiltered = "\(parteIntera)\(decimalSeparator)\(parteDecimale)"
        }
           
        // 3. Aggiorna la variabile @State solo se il valore è effettivamente cambiato.
        // Questo evita un ciclo infinito di aggiornamenti.
        if valueFiltered != self.amount {
           self.amount = valueFiltered
        }
        
    } // func validaAmount(newValue: String)
    
} // struct

#Preview {
    
    RefundView(appState: AppState())
}
