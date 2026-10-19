import SwiftUI

var componentsPayment = URLComponents()

/**
 *
 * Nexi Payments
 *
 * Manages the execution of the Payment method
 *
 */
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
    
    // Computed property for form validation
    // The amount and callerTrxId fields must be populated
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
                    
                        amountValidation(newValue: amount)
                        
                    } //
                    .onChange(of: isTextFieldAmountFocused) {
                        
                        // This block is executed WHEN the focus changes
                        if !isTextFieldAmountFocused {
                            
                            // If the field is no longer active, format the number
                            formatAmountValue()
                            
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
                // Adds a button to close the keyboard
                ToolbarItemGroup(placement: .keyboard) {
                    
                    Spacer()
                    
                    Button("Done") {
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
         
        // If the user clicks on an area where
        // there are no input fields, the keyboard is hidden
        .onTapGesture {
            hideKeyboard()
            isTextFieldFocused = false
        }
        .navigationTitle("Payment")
        // Apply the modifier to ignore the keyboard safe area
        .ignoresSafeArea(.keyboard)
        
    } // body
    
    /**
     *
     * handles the validation of the content of the 'amount' field
     *
     */
    private func amountValidation(newValue: String) {
       
        let decimalSeparator = Locale.current.decimalSeparator ?? "."
           
        var valueFiltered = newValue.filter { "0123456789".contains($0) || String($0) == decimalSeparator }
           
        let components = valueFiltered.components(separatedBy: decimalSeparator)
           
        if components.count > 2 {
            
            let parteIntera = components[0]
            let parteDecimale = components.dropFirst().joined()
            
            valueFiltered = "\(parteIntera)\(decimalSeparator)\(parteDecimale)"
        }
           
        if valueFiltered != self.amount {
           self.amount = valueFiltered
        }
        
    } // func amountValidation(newValue: String)
    
    private func formatAmountValue() {
        
        guard !amount.isEmpty else { return }
        
        let formatter = NumberFormatter()
        formatter.numberStyle = .decimal
    
        formatter.locale = Locale.current
        
        guard let numero = formatter.number(from: amount)
        else { return }
    
        if numero.doubleValue == 0 {
            self.amount = ""
            
            return
        }
        
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        
        if let stringaFormattata = formatter.string(from: numero) {
            self.amount = stringaFormattata
        }
        
    } //  private func formatAmountValue() {
    
    private func payment() {
        
        // Constructs the URL to which Nexipos must return the call result
        componentsPayment.scheme = "softposbasedemo01"
        componentsPayment.host = "payment"

        let urlResponse = componentsPayment.url
        
        // Constructs the call url to Nexi Pos
        componentsPayment.scheme = "neximpos"
        componentsPayment.host = "payment"
        
        // Adds the parameters to start the payment
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
                
        // Start the command
        openURL(url!) { accepted in
            
            if !accepted {
               
                // Could not open Nexi Pos. Is it installed?
            }
            
        } // openURL
        
    } // func payment
    
} // PaymentView


#Preview {
    PaymentView(appState: AppState())
}
