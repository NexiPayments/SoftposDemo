package it.nexi.softpos_evo_kotlin_01.commons

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import it.nexipos.app2app.data.model.A2ASDKResponse
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

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

} // fun formatA2AError(error: A2ASDKResponse?): String

/**
 *
 * Converts an amount into the format required by Nexi POS
 *
 * @param enteredAmount
 *
 * @return amount converted
 *
 */
fun convertAmountForPos(enteredAmount: String?): String {

    if (enteredAmount.isNullOrBlank()) return "0"

    return try {

        val cleanString = enteredAmount.trim().replace(Regex("[^0-9,.]"), "")
        if (cleanString.isEmpty()) return "0"

        val lastSeparatorIndex = maxOf(cleanString.lastIndexOf('.'), cleanString.lastIndexOf(','))

        val integerPart: String
        var decimalPart = "00"

        if (lastSeparatorIndex != -1) {

            // Separate the integer part from the decimal part.
            // Also removes any other separators (thousands) from the integer part
            integerPart = cleanString.substring(0, lastSeparatorIndex).replace(Regex("[,.]"), "")
            decimalPart = cleanString.substring(lastSeparatorIndex + 1)

            decimalPart = when {
                // If the decimal part is empty (e.g., the user typed "10,")
                decimalPart.isEmpty() -> "00"
                // Only one decimal digit (e.g., "10.5") becomes "50"
                decimalPart.length == 1 -> decimalPart + "0"
                // More than two decimal places: keep only the first two
                decimalPart.length > 2 -> decimalPart.substring(0, 2)
                else -> decimalPart
            }

        } else {

            // There are no separators; the amount is an integer (e.g., "10")
            integerPart = cleanString
        }

        // Generates a standard numeric string (e.g., "10.23")
        val standardNumericString = "${integerPart.ifEmpty { "0" }}.$decimalPart"

        // Use BigDecimal for absolute precision and convert to cents
        BigDecimal(standardNumericString)
            .multiply(BigDecimal(100))
            .setScale(0, RoundingMode.HALF_UP)
            .toPlainString()

    } catch (e: Exception) {
        e.printStackTrace()
        "0"
    }

} // fun convertAmountForPos(enteredAmount: String?): String

/**
 *
 *
 * Generate a random amount
 *
 * @return random amount
 *
 */
fun getRandomAmount(): String {

    val amount = Random.nextInt(1, 1000) / 100.0

    val nf = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
        minimumIntegerDigits = 1
    }

    return nf.format(amount)

} // fun getRandomAmount(): String

/**
 *
 * Generate a random callerTrxId.
 *
 * @return es "trx0001"
 *
 */
fun getRandomCallerTrxId(): String {

    val randomNumber = Random.nextInt(1, 10000)

    return "trx%03d".format(randomNumber)

} // fun getRandomCallerTrxId(): String

