package it.nexi.softpos_evo_java_light.domain;

/**
 *
 * Nexi Payment
 *
 * This file must contain the parameters
 * to test the Nexi SDK's functionality.
 *
 */
public class Constant {

    // ## Start - Data to be used during the SDK initialization phase

    // To be retrieved from the Nexi developer portal.
    // Identifier of the application registered on the Nexi developer portal
    public static final String CLIENT_ID                                                            = "";

    // Point-of-sale identifier associated with the app registered on the Nexi developer portal
    public static final String POINT_OF_SALE_ID                                                     = "";

    // Web address specified in the app registered on the Nexi developer portal.
    // It does not necessarily have to exist, but the format must be correct
    // (e.g., https://mysite/mypage)
    public static final String REDIRECT_URI                                                         = "";

    // The merchant_user_name is typically an email address associated with the user.
    public static final String MERCHANT_USER_NAME                                                   = "";

    // ## End - Data to be used during the SDK initialization phase

    // Example of the schema to include in the Android Manifest to identify
    // the activity that will handle the return deep link provided by Nexi POS
    public static final String DEEP_LINK_SCHEMA                                                     = "demonexi_test_java_light";

    // Text to display on the return button after a Nexi POS operation
    public static final String CALLER_NAME                                                          = "SOFTPOS_TEST";

    // TAG for log
    public static final String TAG_SOFTPOS_EVO_JAVA                                                 = "TAG_SOFTPOS_EVO_JAVA";

    // SDK methods
    final public static String  TYPE_OPERATION_PAYMENT                                              = "payment";
    final public static String  TYPE_OPERATION_REVERSAL                                             = "reversal";
    final public static String  TYPE_OPERATION_LAST_TRANSACTION                                     = "last_transaction";
    final public static String  TYPE_OPERATION_ACCOUNTING_CLOSURE                                   = "accounting_closure";


}
