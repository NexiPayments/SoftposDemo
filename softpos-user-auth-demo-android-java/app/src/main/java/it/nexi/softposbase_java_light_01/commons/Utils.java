package it.nexi.softposbase_java_light_01.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/**
 *
 * Nexi Payment
 *
 * Contains methods of various utility
 *
 */
public class Utils {

    /**
     *
     * Format the amount to display it in the local currency format.
     *
     * @param amount    amount to be formatted
     *
     * @return String   formatted amount
     *
     */
    public static String formatAmount(String amount) {

        String amountFormatted = "";

        Double doubleAmount = 0.0;

        if (amount != null && !amount.isEmpty()) {

            try {
                if (Double.parseDouble(amount) >= 0.0) {

                    doubleAmount = Double.parseDouble(amount) / 100;
                    NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
                    amountFormatted = nf.format(doubleAmount);

                }
            } catch (Exception exception) {
                amountFormatted = "";
            }
        }
        return amountFormatted;

    } // public static String formatAmount(String amount) {

    /**
     *
     * Generate a random callerTrxId.
     *
     * @return es "trx0001"
     *
     */
    public static String getRandomCallerTrxId() {

        int randomNumber = new Random().nextInt(9999) + 1;
        String result = String.format("trx%03d", randomNumber);

        return result;

    } // public static String generaCallerTrxId() {

    /**
     *
     *
     * Generate a random amount
     *
     * @return random amount
     *
     */
    public static String getRandomAmount() {

        Random r = new Random();
        double amount = (r.nextInt(999) + 1) / 100.0;

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());

        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumIntegerDigits(1);

        return nf.format(amount);

    } // public static String generaAmountRandom()

    /**
     *
     * Converts an amount into the format required by Nexi POS
     *
     * @param enteredAmount
     *
     * @return amount converted
     *
     */
    public static String convertAmountForPos(String enteredAmount) {

        if (enteredAmount == null || enteredAmount.trim().isEmpty()) {
            return "";
        }

        try {

            String cleanString = enteredAmount.trim().replaceAll("[^0-9,.]", "");

            if (cleanString.isEmpty()) return "0";

            int lastDot = cleanString.lastIndexOf('.');
            int lastComma = cleanString.lastIndexOf(',');
            int lastSeparatorIndex = Math.max(lastDot, lastComma);

            String integerPart;
            String decimalPart = "00";

            if (lastSeparatorIndex != -1) {

                // Separate the integer part from the decimal part.
                integerPart = cleanString.substring(0, lastSeparatorIndex);
                decimalPart = cleanString.substring(lastSeparatorIndex + 1);

                // Removes any other separators (thousands) from the integer part
                integerPart = integerPart.replaceAll("[,.]", "");

                // If the decimal part is empty (e.g., the user typed "10,")
                if (decimalPart.isEmpty()) decimalPart = "00";

                // If the user has entered only one decimal digit (e.g., "10.5"), it becomes "50"
                if (decimalPart.length() == 1) decimalPart += "0";

                // If the user has entered more than two decimal places, we consider only the first two.
                if (decimalPart.length() > 2) decimalPart = decimalPart.substring(0, 2);

            } else {

                // There are no separators; the amount is an integer (e.g., "10")
                integerPart = cleanString;

            }

            // Generates a standard numeric string (e.g., "10.23")
            String standardNumericString = (integerPart.isEmpty() ? "0" : integerPart) + "." + decimalPart;

            // Use BigDecimal to achieve absolute precision and convert to cents.
            BigDecimal valore = new BigDecimal(standardNumericString);
            BigDecimal centesimi = valore.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP);

            return centesimi.toPlainString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    } // public static String convertAmountForPos(String enteredAmount) {

}
