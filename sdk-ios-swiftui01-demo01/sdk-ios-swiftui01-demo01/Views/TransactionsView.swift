//
//  Transactions.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 05/11/25.
//

import SwiftUI
import App2AppSDK
import CocoaLumberjackSwift

struct TransactionsView: View {
    
    @ObservedObject var appState : AppState
    @Environment(\.dismiss) var dismiss
    var appManager = AppManager.shared
    
    @State private var alertInfo: AlertInfo?
    
    var body: some View {
        
        ZStack {
            
            VStack(spacing: 20) {
                
                Text("List transaction - SDK")
                    .font(.title3)
                
                Spacer()
            
                Button("Transactions") {
                    // Avvia metodo
                    
                    DDLogInfo("TransactionView -> avviato getListTransactions")
                    Task {
                        
                        appManager.getListTransactions()
                        
                    }
                }
                .font(.title)
                .buttonStyle(DemoButtonStyle())
               
            }
            .padding()
            .applyDemoBackground() // VStack
        
            if appState.isProcessing {
                       
                Color.black.opacity(0.5).ignoresSafeArea()
                
                ProgressView("List transactions in corso...")
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
    
    
} // struct

#Preview {
    TransactionsView(appState: AppState())
}
