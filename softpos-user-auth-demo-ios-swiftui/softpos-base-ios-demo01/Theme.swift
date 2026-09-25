//
//  Theme..swift
//  softpos-base-ios-demo01
//
//  Created by MAC-01 on 19/08/2026.
//

import SwiftUI

struct DemoButtonStyle: ButtonStyle {
    
    @Environment(\.isEnabled) private var isEnabled
    
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
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
            // Se la View è un Form o una List, questo toglie lo sfondo default
            .scrollContentBackground(.hidden)
            // Questo applica il colore degli Assets che abbiamo creato
            .background(Color("DemoBackgroundColor").ignoresSafeArea())
    }
}
