package it.nexi.softposbase_kotlin_demo_01.services

import android.content.Context
import android.content.SharedPreferences

/**
 *
 * Nexi Payment
 *
 * Manage app preferences
 *
 */
object PreferencesService {

    private const val SHARED_PREFERENCESE_NAME = "SOFTPOS_BASE_JAVA_LIGHT_01"

    // To perform a transaction reversal, the Reversal method must
    // send specific parameters regarding the last executed payment
    // transaction to the Nexi POS.
    // Therefore, the necessary parameter values are saved in the
    // app's preferences.
    private const val KEY_LAST_PAYMENT_TERMINALID = "KEY_LAST_PAYMENT_TERMINALID"
    private const val KEY_LAST_PAYMENT_AMOUNT = "KEY_LAST_PAYMENT_AMOUNT"
    private const val KEY_LAST_PAYMENT_TIP_AMOUNT = "KEY_LAST_PAYMENT_TIP_AMOUNT"
    private const val KEY_LAST_PAYMENT_TOTAL_AMOUNT = "KEY_LAST_PAYMENT_TOTAL_AMOUNT"

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCESE_NAME, Context.MODE_PRIVATE)

    fun getLastPaymentAmount(context: Context): String =
        getPrefs(context).getString(KEY_LAST_PAYMENT_AMOUNT, "") ?: ""

    fun getLastPaymentTotalAmount(context: Context): String =
        getPrefs(context).getString(KEY_LAST_PAYMENT_TOTAL_AMOUNT, "") ?: ""

    fun getLastPaymentTipAmount(context: Context): String =
        getPrefs(context).getString(KEY_LAST_PAYMENT_TIP_AMOUNT, "") ?: ""

    fun getLastPaymentTerminalId(context: Context): String =
        getPrefs(context).getString(KEY_LAST_PAYMENT_TERMINALID, "") ?: ""

    // --- SETTERS ---

    fun setLastPaymentTerminalid(context: Context, terminalId: String?) {

        getPrefs(context).edit().apply {
            putString(KEY_LAST_PAYMENT_TERMINALID, terminalId ?: "")
            apply()
        }

    }

    fun setLastPaymentTotalAmount(context: Context, totalAmount: String?) {

        getPrefs(context).edit().apply {
            putString(KEY_LAST_PAYMENT_TOTAL_AMOUNT, totalAmount ?: "")
            apply()
        }

    }

    fun setLastPaymentAmount(context: Context, amount: String?) {
        getPrefs(context).edit().apply {
            putString(KEY_LAST_PAYMENT_AMOUNT, amount ?: "")
            apply()
        }
    }

    fun setLastPaymentTipAmount(context: Context, tipAmount: String?) {

        getPrefs(context).edit().apply {
            putString(KEY_LAST_PAYMENT_TIP_AMOUNT, tipAmount ?: "")
            apply()
        }

    }

}