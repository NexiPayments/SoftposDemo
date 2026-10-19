import SwiftUI

/**
 *
 * Nexi Payments
 *
 * Show the transaction result
 *
 */
struct ResultView: View {
    
    @ObservedObject                     var appState            : AppState
    @Environment(\.presentationMode)    var presentationMode
    @State                              var result              : String = ""
    
    var body: some View {
        
        VStack(spacing: 20) {
            
            TextEditor(text: $result)
                .foregroundColor(.black)
                .padding()
            
            Button("Close"){
                
                presentationMode.wrappedValue.dismiss()
                
            }
            .padding()
            .buttonStyle(DemoButtonStyle())
            
        } // VStack
        .applyDemoBackground()
        .navigationTitle("Result")
        .navigationBarBackButtonHidden(true)
        .onAppear() {
            
            // When opening the View, retrieve
            // the result from the app state
            result = appState.resultTransaction
            
        }
        
    } // body
    
} // struct 

#Preview {
    ResultView(appState: AppState())
}
