package it.nexi.softposbase_kotlin_demo_01.commons

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

/**
 *
 * Nexi Payment
 *
 * Contains methods of various utility
 *
 */
object Utils {

    /**
     *
     *  Format the amount to display it in the local currency format.
     *
     */
    fun formatAmount(amount: String?): String {

        if (amount.isNullOrEmpty()) return ""

        return try {

            val amountValue = amount.toDoubleOrNull() ?: 0.0

            if (amountValue >= 0.0) {

                val importo = amountValue / 100.0

                val nf = NumberFormat.getCurrencyInstance(Locale.getDefault())

                nf.format(importo)

            } else ""
        } catch (e: Exception) {
            ""
        }

    } // fun formatAmount(amount: String?): String {

    /**
     *
     * Generate a random callerTrxId
     *
     */
    fun generaCallerTrxId(): String {

        val randomNumber = (1..9999).random()

        return String.format("trx%03d", randomNumber)

    } // fun generaCallerTrxId(): String

    /**
     *
     * Generate a random amount
     *
     */
    fun generaAmountRandom(): String {

        val randomAmount = (1..999).random() / 100.0

        val nf = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
            minimumIntegerDigits = 1
        }

        return nf.format(randomAmount)

    } // fun generaAmountRandom(): String {

    /**
     *
     * Converts an amount into the format required by Nexi POS
     *
     */
    fun convertAmountForPos(enteredAmount: String?): String {

        if (enteredAmount.isNullOrBlank()) return ""

        return try {

            val cleanString = enteredAmount.trim().replace(Regex("[^0-9,.]"), "")

            if (cleanString.isEmpty()) return "0"

            val lastDot             = cleanString.lastIndexOf('.')
            val lastComma           = cleanString.lastIndexOf(',')
            val lastSeparatorIndex  = maxOf(lastDot, lastComma)

            var integerPart: String
            var decimalPart = "00"

            if (lastSeparatorIndex != -1) {

                integerPart = cleanString.substring(0, lastSeparatorIndex).replace(Regex("[,.]"), "")

                val rawDecimale = cleanString.substring(lastSeparatorIndex + 1)

                decimalPart = when {

                    rawDecimale.isEmpty() -> "00"
                    rawDecimale.length == 1 -> "${rawDecimale}0"
                    rawDecimale.length > 2 -> rawDecimale.substring(0, 2)

                    else -> rawDecimale

                }

            } else {

                integerPart = cleanString

            }

            val standardNumericString = "${if (integerPart.isEmpty()) "0" else integerPart}.$decimalPart"

            // Generates a standard numeric string (e.g., "10.23")
            BigDecimal(standardNumericString)
                .multiply(BigDecimal(100))
                .setScale(0, RoundingMode.HALF_UP)
                .toPlainString()

        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }

    } // fun convertAmountForPos(enteredAmount: String?): String

}