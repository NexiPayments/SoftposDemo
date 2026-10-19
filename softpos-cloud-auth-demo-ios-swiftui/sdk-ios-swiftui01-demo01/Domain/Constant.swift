/**
 *
 * Nexi Payments
 *
 * This file must contain the parameters
 * to test the Nexi SDK's functionality.
 *
 */

import UIKit

class Constant {

    public static let APP_MANAGER                                                   = "AppManager"
    
    // To perform a transaction reversal, the Reversal method must
    // send specific parameters regarding the last executed payment
    // transaction to the Nexi POS.
    // Therefore, the necessary parameter values are saved in the
    // app's UserDefaults
    public static let USER_DEFAULTS_LAST_PAYMENT_AMOUNT                             = "USER_DEFAULTS_LAST_PAYMENT_AMOUNT"
    public static let USER_DEFAULTS_LAST_PAYMENT_TERMINAL_ID                        = "USER_DEFAULTS_LAST_PAYMENT_TERMINAL_ID"
    public static let USER_DEFAULTS_LAST_PAYMENT_TRANSACTION_ID                     = "USER_DEFAULTS_LAST_PAYMENT_TRANSACTION_ID"
    
    // ## Start - Data to be used during the SDK initialization phase

    // To be retrieved from the Nexi developer portal.
    // Identifier of the application registered on the Nexi developer portal
    public static let CLIENT_ID                                                     = ""

    // Point-of-sale identifier associated with the app registered
    // on the Nexi developer portal
    public static let POINT_OF_SALE_ID                                              = ""

    // Web address specified in the app registered on the Nexi developer portal.
    // It does not necessarily have to exist, but the format must be correct
    // (e.g., https://mysite/mypage)
    public static let REDIRECT_URI                                                  = ""

    // The merchant_user_name is typically
    // an email address associated with the user.
    public static let MERCHANT_USER_NAME                                            = ""

    // ## End - Data to be used during the SDK initialization phase

    // Example of the schema
    public static let DEEP_LINK_SCHEMA                                              = "demonexi_test"

    
}
