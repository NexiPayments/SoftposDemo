import SwiftUI
import CocoaLumberjackSwift
import App2AppSDK

/**
 *
 * Nexi Payments
 *
 * allows for the retrieval of a transaction receipt
 *
 */
struct ReceiptView: View {
    
    @State private var selectedReceiptType: ReceiptType = .payment
    @ObservedObject var appState : AppState
    @State var transactionId : String = ""
    
    @Environment(\.dismiss) var dismiss
    
    @State private var alertInfo: AlertInfo?
    
    var appManager = AppManager.shared
    
    // Computed property for validation
    private var isFormValid: Bool {
       
        let transactionIdValid = !transactionId.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
       
        return transactionIdValid
        
    }
    
    var body: some View {
        
        ZStack {
            
            VStack(spacing: 20) {
                
                Text("Receipt value entry - SDK")
                    .font(.title3)
                
                Form {
                    
                    TextField("transactionId", text: $transactionId)
                    
                    Picker("Receipt Type", selection: $selectedReceiptType) {
                                            
                        ForEach(ReceiptType.allCases) { type in
                            
                            Text(type.rawValue).tag(type)
                            
                        }
                        
                    } // Picker
                    .pickerStyle(.segmented)
                    
                }.applyDemoBackground() // Form
                /*
                .toolbar {
                 // Adds a button to close the keyboard
                    ToolbarItemGroup(placement: .keyboard) {
                        
                        Spacer()
                        
                        Button("Fatto") {
                            // Hides the keyboard
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
                
                Button("Receipt") {
                    // Avvia refund
                    
                    DDLogInfo("ReceiptView -> started getPayReceipt transactionId : \(transactionId) ")
                    
                    Task {
                        
                        switch selectedReceiptType {
                            
                            case .payment:
                                
                                appManager.getReceipt(transactionId: transactionId,
                                                receiptType : ReceiptType.payment
                                               )
                            
                            case .refund:
                            
                                appManager.getReceipt(transactionId: transactionId,
                                                      receiptType: ReceiptType.refund
                                                     )
                            
                        } // switch
                        
                    } // Task
                    
                } // Button("Receipt")
                .font(.title)
                .padding()
                .buttonStyle(DemoButtonStyle())
               
                Spacer()
                
            } // VStack
            .onTapGesture {
                hideKeyboard()
            }
            // Apply the modifier to ignore the keyboard safe area
            .ignoresSafeArea(.keyboard)
            .onAppear() {
                
                transactionId = appState.lastPaymentTransactionId
                
            }
            
            if appState.isProcessing {
                       
                Color.black.opacity(0.5).ignoresSafeArea()
                
                ProgressView("Receipt in progress...")
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
    
} // struct

#Preview {
    ReceiptView(appState: AppState())
}
