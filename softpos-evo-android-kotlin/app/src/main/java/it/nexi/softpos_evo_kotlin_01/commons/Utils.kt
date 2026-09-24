package it.nexi.softpos_evo_kotlin_01.commons

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import it.nexipos.app2app.data.model.A2ASDKResponse
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 *
 * Nel file sono incluse una serie di metodi
 * di varia utilità
 *
 */

fun showInfoAlert(context: Context,
                  titolo: String,
                  messaggio: String
                 ) {

    if (context is Activity && (context.isFinishing || context.isDestroyed)) {
        return
    }

    AlertDialog.Builder(context)
        .setTitle(titolo)
        .setMessage(messaggio)
        .setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        .setCancelable(true)
        .show()

} // fun showInfoAlert(

/**
 *
 * Estensione per restituire un valore
 * intero relativo ad un pagamento pos,
 * in formato stringa
 *
 */
fun Int?.toEuroFormat(): String {

    if (this == null || this < 0) return "0,00"

    val euro = this.toDouble() / 100.0
    val symbols = DecimalFormatSymbols(Locale.ITALY)
    return DecimalFormat("#,##0.00", symbols).format(euro)

} // fun Int?.toEuroFormat()

/**
 *
 * Genera il timestamp richiesto dall'SDK (ddMMyyhhmm)
 *
 */
fun getCurrentTimestamp(): String {

    val sdf = SimpleDateFormat("ddMMyyhhmm", Locale.getDefault())

    return sdf.format(Date())

} // fun getCurrentTimestamp(): String {

/**
 *
 * Restituisce versione dell'app
 *
 */
fun getAppVersion(context: Context): String {

    return try {

        val packageManager = context.packageManager
        val packageName = context.packageName

        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))

        } else {

            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(packageName, 0)

        }

        val versionName = packageInfo.versionName ?: "N/D"

        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            packageInfo.longVersionCode

        } else {

            @Suppress("DEPRECATION")
            packageInfo.versionCode.toLong()

        }

        "$versionName ($versionCode)"

    } catch (e: Exception) {
        "Versione non disponibile"
    }

} // fun getAppVersion(context: Context): String

/**
 *
 * Converte un oggetto A2ASDKResponse (errore) in una stringa formattata
 *
 */
fun formatA2AError(error: A2ASDKResponse?): String {

    val ex = error?.exception

    return "isServiceError = ${ex?.isServiceError ?: "N/D"},  " +
            "errorCode = ${ex?.errorCode ?: "N/D"}, " +
            "ErrorMessage = '${ex?.errorMessage ?: "N/D"}'"

} // fun formatA2AError(error: A2ASDKResponse?): String {