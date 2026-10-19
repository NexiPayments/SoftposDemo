import Foundation

/**
 *
 * Nexi Payments
 *
 * defines the type of receipt that can be issued or received
 *
 */
enum ReceiptType: String, CaseIterable, Identifiable {
    
    case payment = "Payment"
    case refund = "Refund"
    
    var id: String { self.rawValue }
}
