//
//  ReceiptView.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 05/11/25.
//

import SwiftUI
import CocoaLumberjackSwift
import App2AppSDK

struct ReceiptView: View {
    
    @State private var selectedReceiptType: ReceiptType = .payment
    @ObservedObject var appState : AppState
    @State var transactionId : String = ""
    
    @Environment(\.dismiss) var dismiss
    
    @State private var alertInfo: AlertInfo?
    
    var appManager = AppManager.shared
    
    // Proprietà calcolata per la validazione
    private var isFormValid: Bool {
       
        // La form è valida se il campo non è vuoto.
        let transactionIdValida = !transactionId.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty
       
        return transactionIdValida
        
    }
    
    var body: some View {
        
        ZStack {
            
            VStack(spacing: 20) {
                
                Text("Receipt inserimento dati - SDK")
                    .font(.title3)
                
                Form {
                    
                    TextField("transactionId", text: $transactionId)
                    
                    Picker("Tipo Ricevuta", selection: $selectedReceiptType) {
                                            
                        ForEach(ReceiptType.allCases) { type in
                            
                            Text(type.rawValue).tag(type)
                            
                        }
                        
                    } // Picker
                    .pickerStyle(.segmented)
                    
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
                
                Button("Receipt") {
                    // Avvia refund
                    
                    DDLogInfo("ReceiptView -> avviato getPayReceipt transactionId : \(transactionId) ")
                    
                    Task {
                        
                        switch selectedReceiptType {
                            
                            case .payment:
                                
                                appManager.getReceipe(transactionId: transactionId,
                                                receiptType : ReceiptType.payment
                                               )
                            
                            case .refund:
                            
                                appManager.getReceipe(transactionId: transactionId,
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
            // Applica il modificatore per ignorare l'area sicura della tastiera
            .ignoresSafeArea(.keyboard)
            .onAppear() {
                
                transactionId = appState.transactionIdUltimoPagamento
                
            }
            
            if appState.isProcessing {
                       
                Color.black.opacity(0.5).ignoresSafeArea()
                
                ProgressView("Receipt in corso...")
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
