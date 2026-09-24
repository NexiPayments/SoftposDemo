package it.nexi.softpos_evo_java_light.services;

import android.content.Context;
import android.content.SharedPreferences;

import it.nexi.softpos_evo_java_light.domain.Constant;


/**
 *
 * Nexi Payment
 *
 * Manage app preferences
 *
 */
public class PreferencesService {

    private PreferencesService() {
    }

    final private static String SHARED_PREFERENCES_NAME                                             = "SOFTPOS_EVO_JAVA_LIGHT";

    // To perform a transaction reversal, the Reversal method must
    // send specific parameters regarding the last executed payment
    // transaction to the Nexi POS.
    // Therefore, the necessary parameter values are saved in the
    // app's preferences.
    final private static String KEY_LAST_PAYMENT_TERMINALID                                         = "KEY_LAST_PAYMENT_TERMINALID";
    final private static String KEY_LAST_PAYMENT_AMOUNT                                             = "KEY_LAST_PAYMENT_AMOUNT";
    final private static String KEY_LAST_PAYMENT_TIP_AMOUNT                                         = "KEY_LAST_PAYMENT_TIP_AMOUNT";
    final private static String KEY_LAST_PAYMENT_TOTAL_AMOUNT                                       = "KEY_LAST_PAYMENT_TOTAL_AMOUNT";


    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static String getLastPaymentAmount(Context context) {

        return getPrefs(context).getString(KEY_LAST_PAYMENT_AMOUNT, "");

    }

    public static String getLastPaymentTotalAmount(Context context) {

        return getPrefs(context).getString(KEY_LAST_PAYMENT_TOTAL_AMOUNT, "");

    }

    public static String getLastPaymentTipAmount(Context context) {

        return getPrefs(context).getString(KEY_LAST_PAYMENT_TIP_AMOUNT, "");

    }

    public static String getLastPaymentTerminalId(Context context) {

        return getPrefs(context).getString(KEY_LAST_PAYMENT_TERMINALID, "");

    }

    public static void setLastPaymentTerminalid(Context context, String terminalId) {

        SharedPreferences.Editor editor = getPrefs(context).edit();

        String valueToSave = (terminalId != null) ? terminalId : "";

        editor.putString(KEY_LAST_PAYMENT_TERMINALID, valueToSave);

        editor.apply();
    }

    public static void setLastPaymentTotalAmount(Context context, String totalAmount) {

        SharedPreferences.Editor editor = getPrefs(context).edit();

        String valueToSave = (totalAmount != null) ? totalAmount : "";

        editor.putString(KEY_LAST_PAYMENT_TOTAL_AMOUNT, valueToSave);

        editor.apply();

    }

    public static void setLastPaymentAmount(Context context, String amount) {

        SharedPreferences.Editor editor = getPrefs(context).edit();

        String valueToSave = (amount != null) ? amount : "";

        editor.putString(KEY_LAST_PAYMENT_AMOUNT, valueToSave);

        editor.apply();

    }

    public static void setLastPaymentTipAmount(Context context, String tipAmount) {

        SharedPreferences.Editor editor = getPrefs(context).edit();

        String valueToSave = (tipAmount != null) ? tipAmount : "";

        editor.putString(KEY_LAST_PAYMENT_TIP_AMOUNT, valueToSave);

        editor.apply();

    }

} // end class
