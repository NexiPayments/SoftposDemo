import SwiftUI

/**
 *
 * Nexi Payments
 *
 * It acts as a router to display the other views.
 *
 */
private var amount      : String = ""
private var terminalId  : String = ""
private var result      : String = ""

struct ContentView: View {
    
    // Retrieve the state from the environment
    @EnvironmentObject var appState: AppState
    @State var isLinkReversalValid = false

    // Retrieve the App version
    var appVersion: String {
            return Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as? String ?? "N/A"
    }
    
    var body: some View {
                
        NavigationView {
                   
            VStack(spacing: 20){
                
                Text("Softpos User Authentication demo")
                    .font(.title)
                    
                AppVersionView()
                
                Spacer()
                
                NavigationLink(destination: PaymentView(appState: appState)) {
                    
                    HStack {
                       Text("Payment")
                            .font(.title2)
                        
                       Spacer() // Pushes the text to the left
                        
                    }
                   .padding()
                   .frame(maxWidth: .infinity) // It expands across the entire width.
                   .cornerRadius(10)
                    
                } // NavigationLink
                .buttonStyle(DemoButtonStyle())
                
                NavigationLink(destination: ReversalView(appState: appState)) {
                    
                    HStack {
                       Text("Reversal")
                            .font(.title2)
                       
                        Spacer() // Pushes the text to the left
                    }
                   .padding()
                   .frame(maxWidth: .infinity) // It expands across the entire width.
                
                    
                } // NavigationLink
                .buttonStyle(DemoButtonStyle())
                // The Reversal button is enabled only if data
                // regarding the amount and terminalId is present in UserDefaults.
                .disabled(!isLinkReversalValid)
                
                // NavigationLink invisibile.
                // The ResultView is displayed only
                // when the Nexi POS has returned the result of an operation.
                NavigationLink(destination: ResultView(appState: appState),
                               isActive: $appState.showResultView) {
                    
                    EmptyView()
                    
                }
                               
                Spacer()
                
            } // VStack
            .padding()
            .applyDemoBackground()
           
            .onAppear() {
                
                // Retrieves the amount and terminalId values
                // from the UsersDefaults
                amount = UserDefaults.standard.string(forKey: "amount") ?? ""
                terminalId = UserDefaults.standard.string(forKey: "terminalId") ?? ""
                
                // Checks for the presence in UserDefaults,
                // of the items required by the Reversal.
                // Updates the View's state
                checkLinkReversal()
                
            }
         
        } // NavigationView
        
        // The deeplink handler is on the root view, so
        // it will always be active.
        //
        // The handling of the transaction result from the Nexi POS
        // is managed within this method.
        // The Nexi POS sends the transaction result via a deep link
        // to the response Ur specified in the method call.
        .onOpenURL { incomingURL in
        
            if let host = incomingURL.host {
                
                if let resultURL = URLComponents(string: incomingURL.absoluteString)?.queryItems?.first(where: { $0.name == "result" })?.value {
                        
                    result = resultURL
                    
                } else {
                    
                    result = ""
                    
                }
                
                switch host{
                    
                    case "payment":
                        
                        if (result == "0") {
                        
                            // To perform a transaction reversal, the Reversal method must
                            // send specific parameters regarding the last executed payment
                            // transaction to the Nexi POS.
                            // Therefore, the necessary parameter values are saved in the
                            // app's UserDefaults.
                            
                            if let terminalId = URLComponents(string: incomingURL.absoluteString)?.queryItems?.first(where: { $0.name == "terminalId" })?.value {
                                    
                                UserDefaults.standard.set(terminalId, forKey: "terminalId")
                                
                            }
                            
                            if let amount = URLComponents(string: incomingURL.absoluteString)?.queryItems?.first(where: { $0.name == "amount" })?.value {
                                    
                                UserDefaults.standard.set(amount, forKey: "amount")
                                
                            }
                            
                        } // if (result == "0") {
                        
                    case "reversal":
                    
                        if result == "0" {
                         
                            UserDefaults.standard.set("", forKey: "terminalId")
                            UserDefaults.standard.set("", forKey: "amount")
                        }
                        
                    default:
                        print("Default")
                    
                } // switch
                
            } // if let host = incomingURL.host {
            
            // Set the operation result in the app state.
            appState.resultTransaction = incomingURL.absoluteString
            
            // When the operation result is set,
            // the ResultView is displayed.
            appState.showResultView = true
                
        } // .onOpenURL
        
    } // body
    
    // Checks for the presence in UserDefaults,
    // of the items required by the Reversal.
    // Updates the View's state
    private func checkLinkReversal() {
           
        if !amount.isEmpty && !terminalId.isEmpty {
               isLinkReversalValid = true
           } else {
               isLinkReversalValid = false
           }
    }
    
} // struct

#Preview {
    
    ContentView().environmentObject(AppState())
    
}
