package it.nexi.softpos_evo_java_light.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import it.nexipos.app2app.data.model.A2ASDKResponse;


/**
 *
 * Nexi Payment
 *
 * Contains methods of various utility
 *
 */
public class Utils {

    private Utils() {

    }

    /**
     *
     * Displays an alert
     *
     */
    public static void showInfoAlert(Context context,
                                     String title,
                                     String message
                                    ) {

        // Check if the context is a valid activity.

        if (context instanceof Activity) {

            Activity activity = (Activity) context;

            if (activity.isFinishing() || activity.isDestroyed()) {

                return;

            }

        } // if (context instanceof Activity) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .show();

    } // end public static void showInfoAlert(

    /**
     *
     * Returns an amount in euro format
     *
     */
    public static String toEuroFormat(Integer value) {

        if (value == null || value < 0) {

            return "0,00";

        }

        double euro = value.doubleValue() / 100.0;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ITALY);

        return new DecimalFormat("#,##0.00", symbols).format(euro);

    } // end public static String toEuroFormat(Integer value) {

    /**
     *
     * generates a timestamp in the format required by the sdk
     *
     */
    public static String getCurrentTimestamp() {

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyhhmm", Locale.getDefault());

        return sdf.format(new Date());

    } // end public static String getCurrentTimestamp() {

    /**
     *
     * returns the app version
     *
     */
    public static String getAppVersion(Context context) {

        try {

            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo packageInfo;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                packageInfo = packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0));

            } else {

                packageInfo = packageManager.getPackageInfo(packageName, 0);

            }

            String versionName = (packageInfo.versionName != null) ? packageInfo.versionName : "N/D";
            long versionCode;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                versionCode = packageInfo.getLongVersionCode();

            } else {

                versionCode = (long) packageInfo.versionCode;

            }

            return versionName + " (" + versionCode + ")";

        } catch (Exception e) {

            return "Version not available";

        }

    } // end public static String getAppVersion(Context context) {

    /**
     *
     * Formats the A2ASDKResponse object returned
     * by the SDK in the event of errors.
     *
     * @param error
     *
     * @return
     *
     */
    public static String formatA2AError(A2ASDKResponse error) {

        String isServiceErrorStr = (error != null && error.getException() != null)
                ? String.valueOf(error.getException().isServiceError())
                : "N/D";

        String errorCodeStr = (error != null && error.getException() != null && error.getException().getErrorCode() != null)
                ? error.getException().getErrorCode()
                : "N/D";

        String errorMessageStr = (error != null && error.getException() != null && error.getException().getErrorMessage() != null)
                ? error.getException().getErrorMessage()
                : "N/D";

        return "isServiceError = " + isServiceErrorStr + ",  " +
                "errorCode = " + errorCodeStr + ", " +
                "ErrorMessage = '" + errorMessageStr + "'";

    } // end public static String formatA2AError(A2ASDKResponse error)

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

            return "0";

        }

        try {

            String cleanString = enteredAmount.trim().replaceAll("[^0-9,.]", "");

            if (cleanString.isEmpty()) return "0";

            int lastDot             = cleanString.lastIndexOf('.');
            int lastComma           = cleanString.lastIndexOf(',');
            int lastSeparatorIndex  = Math.max(lastDot, lastComma);

            String integerPart  = "";
            String decimalPart  = "00";

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
            return "0";
        }

    } // public static String convertiImportoPerPOS(String importoDigitato) {

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

    } // public static String formatAmount(String amount)

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

} // fine Class
