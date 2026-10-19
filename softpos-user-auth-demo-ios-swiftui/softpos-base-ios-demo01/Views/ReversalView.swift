import SwiftUI

var componentsReversal = URLComponents()

/**
 *
 * Nexi Payments
 *
 * Manages the execution of the Reversal method
 *
 */
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
    
    // Computed property for form validation.
    // callerTrxId fields must be populated
    private var isFormValid: Bool {
    
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
                // Adds a button to close the keyboard.
                ToolbarItemGroup(placement: .keyboard) {
                    
                    Spacer()
                    
                    Button("Done") {
                        
                        // close the keyboard.
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
                
                // Start the command
                startReversal(terminaId: terminalId, amount: amount)
                
            }
            .padding()
            .buttonStyle(DemoButtonStyle())
            .disabled(!isFormValid)
                        
        } // VStack
        .applyDemoBackground()
        .onAppear() {
            
            // When opening the View, retrieve
            // the data from UserDefaults
            amount = UserDefaults.standard.string(forKey: "amount") ?? ""
            terminalId = UserDefaults.standard.string(forKey: "terminalId") ?? ""
            
        }
        .onTapGesture {
                    hideKeyboard()
                    isTextFieldFocused = false
        }
        .navigationTitle("Reversal")
        // Apply the modifier to ignore the keyboard safe area.
        .ignoresSafeArea(.keyboard)
        
        
    } // body

    /**
     *
     * Generates a timestamp based on the Reversal specifications
     *
     */
    func getTimestamp() -> String{
    
        let now = Date()
        let formatter = DateFormatter()
        
        formatter.dateFormat = "ddMMyyHHmm"

        return formatter.string(from: now)
        
    }
    
    private func startReversal(terminaId : String, amount : String ) {
        
        // // Constructs the URL to which Nexipos must return the call result
        componentsReversal.scheme = "softposbasedemo01"
        componentsReversal.host = "reversal"
        
        let urlResponse = componentsReversal.url
        
        // Constructs the call url to Nexi Pos
        componentsReversal.scheme = "neximpos"
        componentsReversal.host = "reversal"
        
        // Adds the parameters to start the Reversal
        
        let timeStamp = getTimestamp()
        
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
        
        // Start the command
        openURL(url!) { accepted in
            
            if !accepted {
                
                // Could not open Nexi Pos. Is it installed?
                
            }
            
        } //  openURL(url!)
        
    }// private func reversal() {
    
} // struct

#Preview {
    ReversalView(appState: AppState())
}
