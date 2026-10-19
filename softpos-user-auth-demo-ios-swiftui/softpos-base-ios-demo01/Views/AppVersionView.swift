import SwiftUI

/**
 *
 * Nexi Payments
 *
 * View which is used to show the app version
 *
 */
struct AppVersionView: View {
    let version: String
    let buildNumber: String
        
    // Retrieves the version data from the Bundle
    init() {
        
        self.version = Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as? String ?? "N/A"
        
        self.buildNumber = Bundle.main.object(forInfoDictionaryKey: "CFBundleVersion") as? String ?? "N/A"
    }
    
    var body: some View {
        
        Text("Version \(version) (\(buildNumber))")
            .font(.title)
            .foregroundColor(.secondary)
            
        }
}

#Preview {
    AppVersionView()
}
