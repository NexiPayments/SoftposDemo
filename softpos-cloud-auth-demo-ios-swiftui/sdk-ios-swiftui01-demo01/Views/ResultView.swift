/**
 *
 * Nexi Payments
 *
 * Receives  the outcome of the
 * transaction performed by SDK
 *
 */
import SwiftUI

struct ResultView: View {
    
    @EnvironmentObject  var appState    : AppState
    @State              var result   : String = ""

    var body: some View {
        
        VStack(spacing: 20) {
            
            TextEditor(text: $result)
                .foregroundColor(.black)
                .padding()
            
            ShareLink(item: result,
                      subject: Text("Result transaction")
                     ) {
                
                Label("Share with", systemImage: "square.and.arrow.up")
                
            } // ShareLink
            .buttonStyle(.borderedProminent)
            // Disable the button if there is no text to share
            .disabled(result.isEmpty)
            
            Button{
                
                appState.showResult = false
                
            } label: {
                Text("Close")
            }
            .font(.title)
            .padding()
            .buttonStyle(DemoButtonStyle())
            
        }.applyDemoBackground() // VStack
       .navigationTitle("Result transaction")
       .navigationBarBackButtonHidden(true)
         
        .onAppear() {
            
           result = appState.transactionMessage
            
        }
    
        .onDisappear {
           
          appState.showResult = false
           
        }
        
    } // body
    
} // struct

#Preview {
    
   // EsitoView(appState: AppState())
}
