//
//  SharingLogView.swift
//  SoftposBASETest01
//
//  Created by MAC-01 on 07/09/25.
//

import SwiftUI

struct SharingLogView: View {
    
    let fileURL: URL

    var body: some View {
        
        VStack(spacing: 20) {
            Text("Condividi File")
                .font(.headline)
            
            Text(fileURL.lastPathComponent)
                .font(.subheadline)
                .foregroundColor(.secondary)
            
            // ShareLink è usato per condividere contenuti.
            ShareLink(
                item: fileURL,
                subject: Text("Log App test SDK SwitUI"),
                message: Text("In allegato il file di log: \(fileURL.lastPathComponent)")
            ) {
                Label("Condividi", systemImage: "square.and.arrow.up")
            }
            .buttonStyle(.borderedProminent)
            
            Spacer()
            
        } // VStack
        .padding()
        // Aggiunge la capacità di chiudere lo sheet trascinando verso il basso
        .presentationDetents([.medium, .large])
        
    } // body
    
} // struct

#Preview {
   // SharingLogView()
}
