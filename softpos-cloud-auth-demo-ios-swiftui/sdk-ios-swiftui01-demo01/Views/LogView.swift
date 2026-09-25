//
//  Log.swift
//  sdk-ios-swiftui01
//
//  Created by MAC-01 on 05/11/25.
//

import SwiftUI

struct LogView: View {
    
    
    // Questo stato conterrà l'elenco degli URL dei file di log.
    @State private var logFiles: [URL] = []
    
    // Questo stato opzionale terrà traccia del file che l'utente ha selezionato per la condivisione.
    // Quando non è `nil`, verrà mostrato il foglio di condivisione.
    @State private var selectedFileForSharing: URL?
    
    var body: some View {
        
        VStack(spacing: 20) {
            
            Text("Log applicativi")
                .bold()
            
            // Controlliamo se ci sono file di log
            if logFiles.isEmpty {
                
                VStack {
                    
                    Image(systemName: "doc.text.magnifyingglass")
                        .font(.largeTitle)
                        .foregroundColor(.gray)
                    
                    Text("Nessun file di log trovato.")
                        .padding(.top)
                    
                } // VStack
                
            } else {
                
                // Creo una lista per visualizzare i file di log
                List(logFiles, id: \.self) { fileURL in
                    
                    Button(action: {
                        // Quando l'utente tocca una riga, impostiamo il file selezionato
                        self.selectedFileForSharing = fileURL
                    }) {
                        
                        HStack {
                            
                            Image(systemName: "doc.text.fill")
                            // Mostriamo solo il nome del file, non il percorso completo
                            Text(fileURL.lastPathComponent)
                            
                            Spacer()
                            
                            Image(systemName: "chevron.right")
                                .foregroundColor(.gray.opacity(0.5))
                            
                        } // HStack
                        .foregroundColor(.primary) // Fa in modo che il testo non sia blu come un bottone standard
                        
                    } // Button
                    
                } // List(logFiles, id:
                
            } // if logFiles.isEmpty {
            
            Spacer()
            
        } // VStack
        .applyDemoBackground()
        .onAppear(perform: loadLogFiles) // Carica i file quando la vista appare
        .sheet(item: $selectedFileForSharing) { fileURL in
                    
            // Questo sheet viene presentato quando `selectedFileForSharing` non è nil.
            // Il `fileURL` passato qui è quello selezionato.
            SharingLogView(fileURL: fileURL)
            
        } // sheet
        
    } // body
    
    // Funzione per caricare gli URL dei file di log dal nostro LogManager.
    private func loadLogFiles() {
        
        self.logFiles = LogManager.shared.getLogFileURLs()
        
    }
    
    
} // body

#Preview {
    LogView()
}
