//
//  PaymentView.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 05/11/25.
//

import SwiftUI
import App2AppSDK
import CocoaLumberjackSwift

import ProximityReader

struct PaymentView: View {
    
    @Environment(\.dismiss) var dismiss

    var appManager = AppManager.shared
    
    @EnvironmentObject var appState : AppState
    @State var amount: String = ""
    @State var tag01: String = ""
    @State var tag02: String = ""
    @State var tags = [String: String]()
    
    @State private var alertInfo: AlertInfo?
    
    @FocusState private var isTextFieldAmountFocused    : Bool
    @FocusState private var isTextFieldFocused          : Bool
    
    // Proprietà calcolata per la validazione
    private var isFormValid: Bool {
       
        // La form è valida se il campo non è vuoto.
        let amountValida = !amount.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
       
        return amountValida
        
    }
    
    var body: some View {
        
        ZStack {
        
            VStack(spacing: 20) {
                
                Text("Payment inserimento dati - SDK")
                    .font(.title3)
                
                Form {
                    
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
                                amount = Utils.formattaValore(amount: amount)
                                
                            }
                            
                        } // .onChange(of: isTextFieldAmountFocused) 
                    
                    TextField("tag 01", text: $tag01)
                        .font(.system(size: 24))
                    
                    TextField("tag 02", text: $tag02)
                        .font(.system(size: 24))
                    
                }.applyDemoBackground() // Form
                
                Button("Payment") {
                    // Avvia pagamento
                    
                    if  !tag01.isEmpty {
                        
                        tags["tag1"] = tag01
                            
                    }
                    
                    if !tag02.isEmpty {
                        
                        tags["tag2"] = tag02
                        
                    }
                    
                    let strAmount = amount
                    
                    // Aggiunge i parametri per avviare il pagamento
                    let decimalSeparator = Locale.current.decimalSeparator ?? "."
                    let amountModificata = strAmount.replacingOccurrences(of: decimalSeparator, with: "")
                    
                    let importValue = Int(amountModificata) ?? 0
                    
                    DDLogInfo("PaymentView -> avviato payment, import : \(importValue)  tags : \(tags)")
                    
                    
                    Task {
                    
                        appManager.payment(importValue: importValue,
                                           currency: CurrencyEnum.euro,
                                           tags: tags)
                        
                
                    } // Task {
                
                } // button
                .padding()
                .font(.title)
                .buttonStyle(DemoButtonStyle())
                .disabled(!isFormValid)
               
                Spacer()
                
            } // VStack
            .disabled(appState.isProcessing)
            .onTapGesture {
                hideKeyboard()
            }
            // Applica il modificatore per ignorare l'area sicura della tastiera
            .ignoresSafeArea(.keyboard)
        
            // La progress view viene mostrata
            // se è in corso una elaborazione
            if appState.isProcessing {
                       
                Color.black.opacity(0.5).ignoresSafeArea()
                
                ProgressView("Payment in corso...")
                    .padding(20)
                    .background(Color.white)
                    .cornerRadius(10)
                    .shadow(radius: 5)
                
            }
            
            
        } // ZStack
        .applyDemoBackground()
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
    
    PaymentView().environmentObject(AppState())
    
}
