//
//  EsitoView.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 06/11/25.
//

import SwiftUI

struct EsitoView: View {
    
    @EnvironmentObject  var appState    : AppState
    @State              var risultato   : String = ""

    var body: some View {
        
        VStack(spacing: 20) {
            
            TextEditor(text: $risultato)
                .foregroundColor(.black)
                .padding()
            
            ShareLink(item: risultato,
                      subject: Text("Esito operazione ")
                     ) {
                
                Label("Condividi", systemImage: "square.and.arrow.up")
                
            } // ShareLink
            .buttonStyle(.borderedProminent)
            // Disabilita il pulsante se non c'è testo da condividere
            .disabled(risultato.isEmpty)
            
            Button{
                
                appState.mostraResultView = false
                
            } label: {
                Text("Chiudi")
            }
            .font(.title)
            .padding()
            .buttonStyle(DemoButtonStyle())
            
        }.applyDemoBackground() // VStack
       .navigationTitle("Esito operazione")
       .navigationBarBackButtonHidden(true)
         
        .onAppear() {
            
           risultato = appState.messaggioTransazione
            
        }
    
        .onDisappear {
           
          appState.mostraResultView = false
           
        }
        
    } // body
    
} // struct

#Preview {
    
   // EsitoView(appState: AppState())
}
