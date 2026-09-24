package it.nexi.softpos_evo_kotlin_01.domain

/**
 *
 * Nexi Payment
 *
 * This file must contain the parameters
 * to test the Nexi SDK's functionality.
 *
 */
object Constant {

    // ## Start - Data to be used during the SDK initialization phase

    // To be retrieved from the Nexi developer portal.
    // Identifier of the application registered on the Nexi developer portal
    const val CLIENT_ID                                                                             = ""

    // Point-of-sale identifier associated with the app registered on the Nexi developer portal
    const val POINT_OF_SALE_ID                                                                      = ""

    // Web address specified in the app registered on the Nexi developer portal.
    // It does not necessarily have to exist, but the format must be correct
    // (e.g., https://mysite/mypage)
    const val REDIRECT_URI                                                                          = ""

    // The merchant_user_name is typically an email address associated with the user
    const val MERCHANT_USER_NAME                                                                    = ""

    // ## End - Data to be used during the SDK initialization phase

    // Example of the schema to include in the Android Manifest to identify
    // the activity that will handle the return deep link provided by Nexi POS
    const val DEEP_LINK_SCHEMA                                                                      = "demonexi_test"

    // Text to display on the return button after a Nexi POS operation
    const val CALLER_NAME                                                                           = "SOFTPOS_TEST"

    // TAG for log
    const val TAG_SOFTPOS_EVO_KOTLIN                                                                = "TAG_SOFTPOS_EVO_KOTLIN"

    // Keys App preferences
    const val PREFS_NAME                                                                            = "AppKotlinTestPreferences"
    const val KEY_AMUOUNT                                                                           = "amount"
    const val KEY_TERMINAL_ID                                                                       = "terminalId"

    // SDK methods
    const val TYPE_OPERATION_PAYMENT                                                                = "payment"
    const val TYPE_OPERATION_REVERSAL                                                               = "reversal"
    const val TYPE_OPERATION_LAST_TRANSACTION                                                       = "last_transaction"
    const val TYPE_OPERATION_ACCOUNTING_CLOSURE                                                     = "accounting_closure"

} // end object