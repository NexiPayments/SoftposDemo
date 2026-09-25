//
//  ResultView.swift
//  SwiftUI_25-08-30_02_ AppIntercom
//
//  Created by MAC-01 on 30/08/25.
//

import SwiftUI

// Mostra i dati ricevuti
struct ResultView: View {
    
    @ObservedObject                     var appState            : AppState
    @Environment(\.presentationMode)    var presentationMode
    @State                              var risultato           : String = ""
    
    var body: some View {
        
        VStack(spacing: 20) {
            
            TextEditor(text: $risultato)
                .foregroundColor(.black)
                .padding()
            
            Button("Chiudi"){
                
                // Chiude la View
                presentationMode.wrappedValue.dismiss()
                
            }
            .padding()
            .buttonStyle(DemoButtonStyle())
            
        } // VStack
        .applyDemoBackground()
        .navigationTitle("Risultati")
        .navigationBarBackButtonHidden(true)
        .onAppear() {
            
            risultato = appState.risultatoOperazione
            
        }
        
    } // body
    
} // struct 

#Preview {
    ResultView(appState: AppState())
}
