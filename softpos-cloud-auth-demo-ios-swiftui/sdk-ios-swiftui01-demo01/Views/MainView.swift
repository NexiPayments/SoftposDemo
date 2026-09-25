//
//  ContentView.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 23/10/25.
//

import SwiftUI
import App2AppSDK

// View principale, mostra l'elenco delle funzionalità
// attivabili
struct MainView: View {
    
    var appManager = AppManager.shared
    
    @State private var showingAlert = false
    @State private var messageAlert = ""
        
    @EnvironmentObject var appState: AppState
    
    // Colonne per la LazyGrid
    let columns: [GridItem] = [
        
        GridItem(.flexible(), spacing: 16),
        GridItem(.flexible(), spacing: 16)
       
    ]
    
    var body: some View {
        
        NavigationView {
            
            VStack(spacing: 10){
                    
                Text("Test SDK iOS - demo")
                    .font(.title)
                    .padding(.top, 10)
                    
                AppVersionView()
                    
                Spacer()
                
                LazyVGrid(columns: columns, spacing: 10) {
                    
                    NavigationLink(destination: PaymentView()) {
                        
                        HStack {
                            Text("Payment")
                                .font(.title3)
                            
                            Spacer() // Spinge il testo a sinistra
                            
                        }
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    NavigationLink(destination: RefundView(appState: appState)) {
                        
                        HStack {
                            Text("Refund")
                                .font(.title3)
                            
                            Spacer() // Spinge il testo a sinistra
                            
                        }
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    NavigationLink(destination: ReceiptView(appState: appState)) {
                        
                        HStack {
                            Text("Receipt")
                                .font(.title3)
                            
                            Spacer() // Spinge il testo a sinistra
                            
                        }
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    NavigationLink(destination: TransactionsView(appState: appState)) {
                        
                        HStack {
                            Text("Transactions")
                                .font(.title3)
                            
                            Spacer() // Spinge il testo a sinistra
                            
                        }
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    
                    NavigationLink(destination: LogView()) {
                        
                        HStack {
                            Text("Log")
                                .font(.title3)
                            
                            Spacer() // Spinge il testo a sinistra
                            
                        }
    
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    // Esegue il logout dall'sdk
                    Button(action: {
                        
                        // Comando per mandare l'app in background
                        
                        Task {
                            
                            appManager.logout()
                            
                        }
                        
                        self.messageAlert = "Logout. L'app verrà chiusa."
                        self.showingAlert = true
                        
                        
                        }) {
                        HStack {
                            Image(systemName: "house.fill")
                            Text("Logout")
                            
                            Spacer() // Spinge il testo a sinistra
                        }
                            
                    }.buttonStyle(DemoButtonStyle()) // Button - logout sdk
                    
                    
                } // Lazygrid
                
                // NavigationLink nascosto che viene attivato solo se
                // l'SDK ha un risultato di una operazione da mostrare
                NavigationLink(destination: EsitoView(),
                               isActive: $appState.mostraResultView) { EmptyView() }
            
                    
                Spacer()
                    
            } // VStack menu
            .padding(.horizontal).applyDemoBackground()
            
        } // NavigationView
        .onAppear {
            
            // L'operazione viene eseguita solo quando la view viene creata in
            // fase di avvio dell'app
           
            Task{
                
                appManager.initSDK(appId: Constant.CLIENT_ID,
                                   redirectUri: Constant.REDIRECT_URI,
                                   merchantId: Constant.ID_PUNTO_VENDITA,
                                   merchantUsername: Constant.MERCHANT_USER_NAME,
                                   deeplinkScheme: Constant.DEEP_LINK_SCHEMA,
                                   domain: App2AppDomain.staging
                                  )
            } // Task
            
        } // fine onAppear
        .alert("Attenzione", isPresented: $showingAlert) {
                        
            Button("Ok", role: .destructive) {
               
                // Manda l'app in background
                UIApplication.shared.minimizeApp()
            }
            
        } message: {
            
            Text(messageAlert)
        }
        
    } // body
    
} // MainView

#Preview {
    MainView().environmentObject(AppState())
}
