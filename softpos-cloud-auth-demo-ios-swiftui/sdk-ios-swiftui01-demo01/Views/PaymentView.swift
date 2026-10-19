import SwiftUI
import App2AppSDK
import CocoaLumberjackSwift
import ProximityReader

/**
 *
 * Nexi Payments
 *
 * Manages the insertion of values ​​to be used for the execution
 * of the SDK Payment method
 *
 */
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
    
    // Computed property for validation
    private var isFormValid: Bool {
       
        let amountValidation = !amount.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
       
        return amountValidation
        
    }
    
    var body: some View {
        
        ZStack {
        
            VStack(spacing: 20) {
                
                Text("Payment value entry - SDK")
                    .font(.title3)
                
                Form {
                    
                    TextField("amount", text: $amount)
                        .keyboardType(.decimalPad)
                        .font(.system(size: 24))
                        .focused($isTextFieldAmountFocused)
                        .onChange(of: amount) { nuovoValore in
                            
                            amountValidation(newValue: amount)
                            
                        }
                        .onChange(of: isTextFieldAmountFocused) { nuovoValore in
                            
                            // This block is executed WHEN the focus changes
                            if !isTextFieldAmountFocused {
                                
                                // If the field is no longer active, we format the number
                                amount = Utils.formatAmount(amount: amount)
                                
                            }
                            
                        } // .onChange(of: isTextFieldAmountFocused) 
                    
                    TextField("tag 01", text: $tag01)
                        .font(.system(size: 24))
                    
                    TextField("tag 02", text: $tag02)
                        .font(.system(size: 24))
                    
                }.applyDemoBackground() // Form
                
                Button("Payment") {
                    // Start payment
                    
                    if  !tag01.isEmpty {
                        
                        tags["tag1"] = tag01
                            
                    }
                    
                    if !tag02.isEmpty {
                        
                        tags["tag2"] = tag02
                        
                    }
                    
                    let strAmount = amount
                    
                    // Adds the parameters to initiate the payment
                    let decimalSeparator = Locale.current.decimalSeparator ?? "."
                    let amountModified = strAmount.replacingOccurrences(of: decimalSeparator, with: "")
                    
                    let importValue = Int(amountModified) ?? 0
                    
                    DDLogInfo("PaymentView -> payment started, importValue = : \(importValue)  tags : \(tags)")
                    
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
            // Apply the modifier to ignore the keyboard safe area
            .ignoresSafeArea(.keyboard)
        
            // The progress view is displayed
            // if processing is underway
            if appState.isProcessing {
                       
                Color.black.opacity(0.5).ignoresSafeArea()
                
                ProgressView("Payment in progress...")
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
    
    private func amountValidation(newValue: String) {
           
        // Gets the correct decimal separator for the locale settings
        let decimalSeparator = Locale.current.decimalSeparator ?? "."
           
        // Filter the input to keep only the numbers and the separator
        var valueFiltered = newValue.filter { "0123456789".contains($0) || String($0) == decimalSeparator }
           
        // Handles the presence of multiple decimal separators
        let components = valueFiltered.components(separatedBy: decimalSeparator)
           
        // If there are more than 2 components, it means that more than one separator has been entered
        // Example: "12,34,56" -> components = ["12", "34", "56"] (count = 3)
        if components.count > 2 {
            
            // Reconstructs the string, keeping only the first separator
            let parteIntera = components[0]
            let parteDecimale = components.dropFirst().joined()
            
            valueFiltered = "\(parteIntera)\(decimalSeparator)\(parteDecimale)"
        }
           
        // Update the @State variable only if the value has actually changed.
        // This prevents an infinite loop of updates.
        if valueFiltered != self.amount {
           self.amount = valueFiltered
        }
        
    } // func amountValidation(newValue: String)
            
} // struct

#Preview {
    
    PaymentView().environmentObject(AppState())
    
}
