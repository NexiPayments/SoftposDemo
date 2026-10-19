
import SwiftUI

/**
 *
 * Nexi Payments
 *
 * allows the user to share the log file via email,
 * messaging, etc.
 *
 */
struct SharingLogView: View {
    
    let fileURL: URL

    var body: some View {
        
        VStack(spacing: 20) {
            Text("Share File")
                .font(.headline)
            
            Text(fileURL.lastPathComponent)
                .font(.subheadline)
                .foregroundColor(.secondary)
            
            // ShareLink is used to share content.
            ShareLink(
                item: fileURL,
                subject: Text("Log App test iOS SDK SwitUI"),
                message: Text("The log file is attached: \(fileURL.lastPathComponent)")
            ) {
                Label("Share", systemImage: "square.and.arrow.up")
            }
            .buttonStyle(.borderedProminent)
            
            Spacer()
            
        } // VStack
        .padding()
        // Adds the ability to close the sheet by dragging it downwards
        .presentationDetents([.medium, .large])
        
    } // body
    
} // struct

#Preview {
   // SharingLogView()
}
