import SwiftUI
import App2AppSDK


/**
 *
 * Nexi Payments
 *
 * Main view, displays the list of features
 * that can be enabled
 *
 */
struct MainView: View {
    
    var appManager = AppManager.shared
    
    @State private var showingAlert = false
    @State private var messageAlert = ""
        
    @EnvironmentObject var appState: AppState
    
    let columns: [GridItem] = [
        
        GridItem(.flexible(), spacing: 16),
        GridItem(.flexible(), spacing: 16)
       
    ]
    
    var body: some View {
        
        NavigationView {
            
            VStack(spacing: 10){
                    
                Text("Cloud Authentication iOS - demo")
                    .font(.title)
                    .padding(.top, 10)
                    
                AppVersionView()
                    
                Spacer()
                
                LazyVGrid(columns: columns, spacing: 10) {
                    
                    NavigationLink(destination: PaymentView()) {
                        
                        HStack {
                            Text("Payment")
                                .font(.title3)
                            
                            Spacer() // Pushes the text to the left
                            
                        }
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    NavigationLink(destination: RefundView(appState: appState)) {
                        
                        HStack {
                            Text("Refund")
                                .font(.title3)
                            
                            Spacer()
                            
                        }
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    NavigationLink(destination: ReceiptView(appState: appState)) {
                        
                        HStack {
                            Text("Receipt")
                                .font(.title3)
                            
                            Spacer()
                            
                        }
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    NavigationLink(destination: TransactionsView(appState: appState)) {
                        
                        HStack {
                            Text("Transactions")
                                .font(.title3)
                            
                            Spacer()
                            
                        }
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    
                    NavigationLink(destination: LogView()) {
                        
                        HStack {
                            Text("Log")
                                .font(.title3)
                            
                            Spacer()
                            
                        }
    
                        
                    }.buttonStyle(DemoButtonStyle()) // NavigationLink
                    
                    // Logs out from the SDK
                    Button(action: {
                        
                        // Command to send the app to the background
                        
                        Task {
                            
                            appManager.logout()
                            
                        }
                        
                        self.messageAlert = "Logout. The app will be closed."
                        self.showingAlert = true
                        
                        
                        }) {
                        HStack {
                            Image(systemName: "house.fill")
                            Text("Logout")
                            
                            Spacer()
                        }
                            
                    }.buttonStyle(DemoButtonStyle()) // Button - logout sdk
                    
                    
                } // Lazygrid
                
                // Hidden NavigationLink that is triggered only if
                // the SDK has an operation result to display
                NavigationLink(destination: ResultView(),
                               isActive: $appState.showResult) { EmptyView() }
            
                    
                Spacer()
                    
            } // VStack menu
            .padding(.horizontal).applyDemoBackground()
            
        } // NavigationView
        .onAppear {
            
            // The init operation is performed when the view is created during
            // the app startup phase
           
            Task{
                
                appManager.initSDK(appId: Constant.CLIENT_ID,
                                   redirectUri: Constant.REDIRECT_URI,
                                   merchantId: Constant.POINT_OF_SALE_ID,
                                   merchantUsername: Constant.MERCHANT_USER_NAME,
                                   deeplinkScheme: Constant.DEEP_LINK_SCHEMA,
                                   domain: App2AppDomain.staging
                                  )
            } // Task
            
        } // fine onAppear
        .alert("Attention", isPresented: $showingAlert) {
                        
            Button("Ok", role: .destructive) {
               
                // Send the app to the background
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
