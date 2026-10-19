//
//  Refund.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 05/11/25.
//

import SwiftUI
import App2AppSDK
import CocoaLumberjackSwift

/**
 *
 * Nexi Payments
 *
 * Manages the insertion of values ​​to be used for the execution
 * of the SDK Refund method
 *
 */
struct RefundView: View {
    
    @ObservedObject var appState : AppState
   
    @State var amount : String = ""
    @State var transactionId : String = ""
    @FocusState private var isTextFieldAmountFocused    : Bool
    
    var appManager = AppManager.shared
    @Environment(\.dismiss) var dismiss
    
    @State private var alertInfo: AlertInfo?
    
    // Computed property for validation
    private var isFormValid: Bool {
       
        let amountValid = !amount.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
        let transactionIdValid = !transactionId.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
        
        return amountValid && transactionIdValid
        
    }
    
    var body: some View {
        
        ZStack {
            
            VStack(spacing: 20) {
                
                Text("Refund entry value - SDK")
                    .font(.title3)
                
                Form {
                    
                    TextField("transactionId", text: $transactionId)
                        .font(.system(size: 24))

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
                                amount = Utils.formatAmount(amount : amount)
                                
                            }
                            
                        } // .onChange(of: isTextFieldAmountFocused)
                    
                }.applyDemoBackground() // Form
                /*
                .toolbar {
                 // Adds a button to close the keyboard
                    ToolbarItemGroup(placement: .keyboard) {
                        
                        Spacer()
                        
                        Button("Done") {
                            // Hides keyboard
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
                    
                    // Adds the parameters to initiate the refund
                    let decimalSeparator = Locale.current.decimalSeparator ?? "."
                    let amountModified = strAmount.replacingOccurrences(of: decimalSeparator, with: "")
                    
                    let importValue = Int(amountModified) ?? 0
                    
                    DDLogInfo("RefundView -> started refund, import : \(importValue)  transactionId : \(transactionId)")
                    
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
            // Apply the modifier to ignore the keyboard safe area
            .ignoresSafeArea(.keyboard)
            .onAppear() {
                
                transactionId = appState.lastPaymentTransactionId
                amount = Utils.formatAmountForDisplay(fromCentes : appState.lastPaymentAmount)
                
            } // onAppear
            
            if appState.isProcessing {
                       
                Color.black.opacity(0.5).ignoresSafeArea()
                
                ProgressView("Refund in progress...")
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
           
        /// Update the @State variable only if the value has actually changed.
        // This prevents an infinite loop of updates.
        if valueFiltered != self.amount {
           self.amount = valueFiltered
        }
        
    } // func amountValidation(newValue: String)
    
} // struct

#Preview {
    
    RefundView(appState: AppState())
}
