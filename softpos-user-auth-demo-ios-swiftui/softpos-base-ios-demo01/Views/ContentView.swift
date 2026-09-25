//
//  ContentView.swift
//  SwiftUI_25-08-30_02_ AppIntercom
//
//  Created by MAC-01 on 30/08/25.
//

import SwiftUI

private var amount      : String = ""
private var terminalId  : String = ""
private var result      : String = ""

// Il ContentView principale che funge da router
struct ContentView: View {
    
    // Recupero lo stato dall'ambiente
    @EnvironmentObject var appState: AppState
    @State var isLinkReversalValid = false

    // Recupera la versione dell'app
    var appVersion: String {
            return Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as? String ?? "N/A"
    }
    
    var body: some View {
                
        NavigationView {
                   
            VStack(spacing: 20){
                
                Text("Softpos BASE demo")
                    .font(.title)
                    
                AppVersionView()
                
                Spacer()
                
                NavigationLink(destination: PaymentView(appState: appState)) {
                    
                    HStack {
                       Text("Payment")
                            .font(.title2)
                        
                       Spacer() // Spinge il testo a sinistra
                        
                    }
                   .padding() // Aggiunge spazio interno
                   .frame(maxWidth: .infinity) // Si espande per tutta la larghezza
                   .cornerRadius(10) // Angoli arrotondati
                    
                } // NavigationLink
                .buttonStyle(DemoButtonStyle())
                
                NavigationLink(destination: ReversalView(appState: appState)) {
                    
                    HStack {
                       Text("Reversal")
                            .font(.title2)
                       
                        Spacer() // Spinge il testo a sinistra
                    }
                   .padding() // Aggiunge spazio interno
                   .frame(maxWidth: .infinity) // Si espande per tutta la larghezza
                
                    
                } // NavigationLink
                .buttonStyle(DemoButtonStyle())
                .disabled(!isLinkReversalValid)
                
                // NavigationLink invisibile per Resultview, attivato programmaticamente
                NavigationLink(destination: ResultView(appState: appState),
                               isActive: $appState.mostraResultView) {
                    
                    EmptyView()
                    
                }
                               
                Spacer()
                
            } // VStack
            .padding()
            .applyDemoBackground()
           
            .onAppear() {
                
                // Recupera i valori di amount e terminalId dalle UsersDefault
                amount = UserDefaults.standard.string(forKey: "amount") ?? ""
                terminalId = UserDefaults.standard.string(forKey: "terminalId") ?? ""
                
                checkLinkReversal()
                
            }
         
        } // NavigationView
        
          // Il gestore di deeplink è sulla vista radice, così è sempre attivo
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
            
            appState.risultatoOperazione = incomingURL.absoluteString
            appState.mostraResultView = true
                
        } // .onOpenURL
        
    } // body
    
    // Funzione per verificare i dati in UserDefaults e aggiornare lo stato
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
