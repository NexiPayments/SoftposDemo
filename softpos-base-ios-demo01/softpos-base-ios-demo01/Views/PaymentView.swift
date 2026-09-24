//
//  InitialView.swift
//  SwiftUI_25-08-30_02_ AppIntercom
//
//  Created by MAC-01 on 30/08/25.
//

import SwiftUI

var componentsPayment = URLComponents()

struct PaymentView: View {
    
    @Environment(\.openURL) var openURL
    @ObservedObject         var appState                    : AppState
    @FocusState private     var isTextFieldAmountFocused    : Bool
    @FocusState private     var isTextFieldFocused          : Bool
    
    @State var amount       : String = ""
    @State var callerTrxId  : String = ""
    @State var email        : String = ""
    @State var sms          : String = ""
    @State var addoInfo1    : String = ""
    @State var addoInfo2    : String = ""
    @State var addoInfo3    : String = ""
    @State var addoInfo4    : String = ""
    @State var addoInfo5    : String = ""
    
    // Proprietà calcolata per la validazion del form
    // i campi amount e callerTrxId devono essere valorizzati
    private var isFormValid: Bool {
        
        let amountValido = !amount.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
        
        let callerTrxIDValido = !callerTrxId.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
       
       return callerTrxIDValido && amountValido
        
    } // private var isFormValid: Bool {
    
    var body: some View {
        
        VStack{
            
            Form {
               
                TextField("amount", text: $amount)
                    .textFieldStyle(.roundedBorder)
                    .keyboardType(.decimalPad)
                    .font(.system(size: 24))
                    .focused($isTextFieldAmountFocused)
                    .onChange(of: amount) { oldValue, newValue in
                    
                        validaAmount(newValue: amount)
                        
                    } //
                    .onChange(of: isTextFieldAmountFocused) {
                        
                        // Questo blocco viene eseguito QUANDO il focus cambia
                        if !isTextFieldAmountFocused {
                            
                            // Se il campo non è più attivo, formattiamo il numero
                            formattaValore()
                            
                        }
                    }
                                
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
                
                TextField("addInfo1", text: $addoInfo1)
                    .textFieldStyle(.roundedBorder)
                    .font(.system(size: 24))
                    .focused($isTextFieldFocused)
                
                TextField("addInfo2", text: $addoInfo2)
                    .textFieldStyle(.roundedBorder)
                    .font(.system(size: 24))
                    .focused($isTextFieldFocused)
                
                TextField("addInfo3", text: $addoInfo3)
                    .textFieldStyle(.roundedBorder)
                    .font(.system(size: 24))
                    .focused($isTextFieldFocused)
                
                TextField("addInfo4", text: $addoInfo4)
                    .textFieldStyle(.roundedBorder)
                    .font(.system(size: 24))
                    .focused($isTextFieldFocused)
                
                TextField("addInfo5", text: $addoInfo5)
                    .textFieldStyle(.roundedBorder)
                    .font(.system(size: 24))
                    .focused($isTextFieldFocused)
                
            } // Form
            .padding(.top, -30)
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
    
            Button("Payment") {
                payment()
            }
            .font(.title)
            .padding()
            .buttonStyle(DemoButtonStyle())
            .disabled(!isFormValid)
                        
        } // VStack
        .applyDemoBackground()
         
          // Se l'utente clicca su una zona nella quale
          // non ci sono campi di inserimento, la tastiera viene nascosta
        .onTapGesture {
            hideKeyboard()
            isTextFieldFocused = false
        }
        .navigationTitle("Payment")
        // Applica il modificatore per ignorare l'area sicura della tastiera
        .ignoresSafeArea(.keyboard)
        
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
    
    private func formattaValore() {
        
        // Se la stringa è vuota, non facciamo nulla
        guard !amount.isEmpty else { return }
        
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
        
        // Impostiamo la localizzazione del formattatore a quella corrente del dispositivo
        // per interpretare correttamente sia "," che "." come separatore.
        formatter.locale = Locale.current
        
        // Tentiamo di convertire la stringa in un numero
        guard let numero = formatter.number(from: amount)
        else { return }
        
        // Se il valore numerico è 0 (l'utente ha inserito "0", "0,0", ecc.)
        // impostiamo la stringa a vuoto e usciamo dalla funzione.
        // Questo farà apparire il placeholder "valore".
        if numero.doubleValue == 0 {
            self.amount = ""
            
            return
        }
        
        // Ora riconfiguriamo il formattatore per l'output desiderato
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        
        // Riconvertiamo il numero in una stringa formattata
        if let stringaFormattata = formatter.string(from: numero) {
            self.amount = stringaFormattata
        }
        
    } //  private func formattarValore() {
    
    private func payment() {
        
        // Compone la url a cui Nexipos deve restituire l'esito della chiamata
        componentsPayment.scheme = "softposbasedemo01"
        componentsPayment.host = "payment"

        let urlResponse = componentsPayment.url
        
        // Compone la url di chiamata verso Nexi Pos
        componentsPayment.scheme = "neximpos"
        componentsPayment.host = "payment"
        
        // Aggiunge i parametri per avviare il pagamento
        let decimalSeparator = Locale.current.decimalSeparator ?? "."
        let amountModificata = amount.replacingOccurrences(of: decimalSeparator, with: "")
        
        let itemQueryAmount         = URLQueryItem(name: "amount", value: amountModificata)
        
        let itemQueryCallerTrxId    = URLQueryItem(name: "callerTrxId", value: callerTrxId)
        
        let itemQuerySendTicket     = URLQueryItem(name: "sendTicket", value: "true")
        
        let itemQueryUrlTicket      = URLQueryItem(name: "urlTicket", value: "false")
        
        let itemQueryCallerName     = URLQueryItem(name: "callerName", value: "SOFT POS BASE")
      
        let itemQueryURIResponse    = URLQueryItem(name: "uri", value: urlResponse?.absoluteString)
        
        let itemQueryEmail          = URLQueryItem(name: "email", value: email)
        
        let itemQuerySms            = URLQueryItem(name: "sms", value: sms)
        
        let itemQueryAddInfo1       = URLQueryItem(name: "addInfo1", value: addoInfo1)
        
        let itemQueryAddInfo2       = URLQueryItem(name: "addInfo2", value: addoInfo2)
        
        let itemQueryAddInfo3       = URLQueryItem(name: "addInfo3", value: addoInfo3)
        
        let itemQueryAddInfo4       = URLQueryItem(name: "addInfo4", value: addoInfo4)
        
        let itemQueryAddInfo5       = URLQueryItem(name: "addInfo5", value: addoInfo5)
        

        componentsPayment.queryItems = [
                                 itemQueryAmount,
                                 itemQueryCallerTrxId,
                                 itemQuerySendTicket,
                                 itemQueryUrlTicket,
                                 itemQueryCallerName,
                                 itemQueryURIResponse,
                                 itemQueryEmail,
                                 itemQuerySms,
                                 itemQueryAddInfo1,
                                 itemQueryAddInfo2,
                                 itemQueryAddInfo3,
                                 itemQueryAddInfo4,
                                 itemQueryAddInfo5
                                ]
        
        let url = componentsPayment.url
                
        // Eseguiamo il deeplink
        openURL(url!) { accepted in
            
            if !accepted {
               
                // Non è stato possibile aprire Nexi Pos. È installata?
            }
            
        } // openURL
        
    } // func payment
    
} // PaymentView


#Preview {
    PaymentView(appState: AppState())
}
