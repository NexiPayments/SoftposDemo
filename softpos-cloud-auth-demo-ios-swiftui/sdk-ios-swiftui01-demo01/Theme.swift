import SwiftUI

/**
 *
 * Nexi Payments
 *
 *
 */
struct DemoButtonStyle: ButtonStyle {
    
    @Environment(\.isEnabled) private var isEnabled
    
    func makeBody(configuration: Configuration) -> some View {
        
        configuration
            .label
            .padding()
            .frame(maxWidth: .infinity)
            .background(isEnabled ? Color("DemoBrandColor") : Color.gray.opacity(0.5))
            .foregroundColor(Color("DemoTextColor"))
            .cornerRadius(10)
            .scaleEffect(configuration.isPressed && isEnabled ? 0.95 : 1.0)
    }
    
}

extension View {
    
    func applyDemoBackground() -> some View {
        
        self.frame(maxWidth: .infinity, maxHeight: .infinity)
        // If the View is a Form or a List, remove the default background
        .scrollContentBackground(.hidden)
        .background(Color("DemoBackgroundColor").ignoresSafeArea())
    }
}

