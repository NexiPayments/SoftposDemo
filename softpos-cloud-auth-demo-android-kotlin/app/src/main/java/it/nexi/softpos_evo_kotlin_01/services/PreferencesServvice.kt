package it.nexi.softpos_evo_kotlin_01.services

import android.content.Context
import android.content.SharedPreferences
import it.nexi.softpos_evo_kotlin_01.domain.Constant

/**
 *
 * Nexi Payment
 *
 * Manage app preferences
 *
 */
object PreferencesServvice {

    // To perform a transaction reversal, the Reversal method must
    // send specific parameters regarding the last executed payment
    // transaction to the Nexi POS.
    // Therefore, the necessary parameter values are saved in the
    // app's preferences.

    private fun getPrefs(context: Context): SharedPreferences {

        return context.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_PRIVATE)

    } // getPrefs

    fun savePaymentData(context: Context, amount: Int?, terminalId : String?) {
        val editor = getPrefs(context).edit()

        editor.putInt(Constant.KEY_AMUOUNT, amount ?: 0)
        editor.putString(Constant.KEY_TERMINAL_ID, terminalId)

        editor.apply()
    }

    fun getAmount(context: Context): Int? = getPrefs(context).getInt(Constant.KEY_AMUOUNT, 0)
    fun getTerminalId(context: Context): String? = getPrefs(context).getString(Constant.KEY_TERMINAL_ID, "")

} // end object