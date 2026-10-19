import SwiftUI
import App2AppSDK
import CocoaLumberjackSwift

/**
 *
 * Nexi Payments
 *
 * Start execution of the getListTransaction method
 *
 */
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
                    
                    DDLogInfo("TransactionView -> start getListTransactions")
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
                
                ProgressView("getListTransactions in progress...")
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
