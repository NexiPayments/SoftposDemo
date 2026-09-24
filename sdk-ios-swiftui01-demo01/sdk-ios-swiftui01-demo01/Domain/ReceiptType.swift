//
//  ReceiptType.swift

import Foundation

enum ReceiptType: String, CaseIterable, Identifiable {
    
    case payment = "Payment"
    case refund = "Refund"
    
    var id: String { self.rawValue }
}
