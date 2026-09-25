//
//  ReversalView.swift
//  SoftposBASETest01
//
//  Created by MAC-01 on 05/09/25.
//

import SwiftUI

var componentsReversal = URLComponents()

struct ReversalView: View {
    
    @Environment(\.openURL) var openURL
    @ObservedObject var appState: AppState
    
    @State var terminalId   : String = ""
    @State var amount       : String = ""
    @State var callerTrxId  : String = ""
    @State var email        : String = ""
    @State var sms          : String = ""
    
    @FocusState private var isTextFieldFocused: Bool
    
    var timeStamp = ""
    var sendTicket = true
    var urlTicket = false
    
    // Proprietà calcolata per la validazione
    private var isFormValid: Bool {
       
        // La form è valida se il campo non è vuoto.
        let callerTrxIDValido = !callerTrxId.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
       
        return callerTrxIDValido
        
    }
    
    var body: some View {
        
        VStack {
            
            Text("Terminal ID : \(terminalId)")        
            Text("amount : \(amount)")
            
            Form {
                
                TextField("callerTrxId", text: $callerTrxId)
                    .textFieldStyle(.roundedBorder)
                    .font(.system(size: 24))
                    .autocorrectionDisabled(true)
                    .textInputAutocapitalization(.never)
                    .focused($isTextFieldFocused)
                
                TextField("email", text: $email)
                    .textFieldStyle(.roundedBorder)
                    .font(.system(size: 24))
                    .focused($isTextFieldFocused)
                
                TextField("sms", text: $sms)
                    .textFieldStyle(.roundedBorder)
                    .font(.system(size: 24))
                    .focused($isTextFieldFocused)
                
            } // Form
            .padding(.top, -10)
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
                
            } // toolbar
            
            Button("Reversal") {
                reversal(terminaId: terminalId, amount: amount)
            }
            .padding()
            .buttonStyle(DemoButtonStyle())
            .disabled(!isFormValid)
                        
        } // VStack
        .applyDemoBackground()
        .onAppear() {
            
            amount = UserDefaults.standard.string(forKey: "amount") ?? ""
            terminalId = UserDefaults.standard.string(forKey: "terminalId") ?? ""
            
        }
        .onTapGesture {
                    hideKeyboard()
                    isTextFieldFocused = false
        }
        .navigationTitle("Reversal")
        // Applica il modificatore per ignorare l'area sicura della tastiera
        .ignoresSafeArea(.keyboard)
        
        
    } // body

    func generaTimestamp() -> String{
    
        let now = Date()
        let formatter = DateFormatter()
        
        formatter.dateFormat = "ddMMyyHHmm"

        return formatter.string(from: now)
        
    }
    
    private func reversal(terminaId : String, amount : String ) {
        
        // Compone la url a cui Nexipos deve restituire l'esito della chiamata
        componentsReversal.scheme = "softposbasedemo01"
        componentsReversal.host = "reversal"
        
        let urlResponse = componentsReversal.url
        
        // Compone la url di chiamata verso Nexi Pos
        componentsReversal.scheme = "neximpos"
        componentsReversal.host = "reversal"
        
        // Aggiunge i parametri per avviare il pagamento
        
        let timeStamp = generaTimestamp()
        
        let itemQueryAmount         = URLQueryItem(name: "amount", value: amount)
        
        let itemQueryCallerTrxId    = URLQueryItem(name: "callerTrxId", value: callerTrxId)
        
        let itemQuerySendTicket     = URLQueryItem(name: "sendTicket", value: "true")
        
        let itemQueryUrlTicket      = URLQueryItem(name: "urlTicket", value: "false")
        
        let itemQueryCallerName     = URLQueryItem(name: "callerName", value: "SOFT POS BASE")
        
        let itemQueryURIResponse    = URLQueryItem(name: "uri", value: urlResponse?.absoluteString)
        
        let itemQueryEmail          = URLQueryItem(name: "email", value: email)
        
        let itemQuerySms            = URLQueryItem(name: "sms", value: sms)
        
        let itemQueryTerminalId     = URLQueryItem(name: "terminalId", value: terminalId)
        
        let itemQueryTimestamp      = URLQueryItem(name: "timestamp", value: timeStamp)
        
        componentsReversal.queryItems = [itemQueryAmount,
                                         itemQueryCallerTrxId,
                                         itemQuerySendTicket,
                                         itemQueryUrlTicket,
                                         itemQueryCallerName,
                                         itemQueryURIResponse,
                                         itemQueryEmail,
                                         itemQuerySms,
                                         itemQueryTerminalId,
                                         itemQueryTimestamp
                                        ]
        
        let url = componentsReversal.url
        
        // Eseguiamo il deeplink
        openURL(url!) { accepted in
            
            if !accepted {
                
                //Non è stato possibile aprire Nexi Pos. È installata?
                
            }
            
        } //  openURL(url!)
        
    }// private func reversal() {
    
} // struct

#Preview {
    ReversalView(appState: AppState())
}
