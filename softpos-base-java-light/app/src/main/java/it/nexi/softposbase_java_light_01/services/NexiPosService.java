package it.nexi.softposbase_java_light_01.services;

import static it.nexi.softposbase_java_light_01.commons.Constant.APP_AUTHORITY_ACCOUNTING_CLOSURE;
import static it.nexi.softposbase_java_light_01.commons.Constant.APP_AUTHORITY_LAST_TRANSACTION;
import static it.nexi.softposbase_java_light_01.commons.Constant.APP_AUTHORITY_PAYMENT;
import static it.nexi.softposbase_java_light_01.commons.Constant.APP_AUTHORITY_REVERSAL;
import static it.nexi.softposbase_java_light_01.commons.Constant.APP_SCHEMA;
import static it.nexi.softposbase_java_light_01.commons.Constant.NEXIPOS_AUTHORITY_ACCOUNTING_CLOSURE;
import static it.nexi.softposbase_java_light_01.commons.Constant.NEXIPOS_AUTHORITY_LAST_TRANSACTION;
import static it.nexi.softposbase_java_light_01.commons.Constant.NEXIPOS_AUTHORITY_PAYMENT;
import static it.nexi.softposbase_java_light_01.commons.Constant.NEXIPOS_AUTHORITY_REVERSAL;
import static it.nexi.softposbase_java_light_01.commons.Constant.NEXIPOS_SCHEMA;

import android.net.Uri;
import android.util.ArrayMap;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import it.nexi.softposbase_java_light_01.commons.Constant;
import it.nexi.softposbase_java_light_01.commons.Utils;
import it.nexi.softposbase_java_light_01.domain.SoftPosResponseAccountingClosure;
import it.nexi.softposbase_java_light_01.domain.SoftPosResponseLastTransaction;
import it.nexi.softposbase_java_light_01.domain.SoftPosResponsePayment;
import it.nexi.softposbase_java_light_01.domain.SoftPosResponseReversal;

/**
 *
 * Nexi Payment
 *
 * Contains the interface methods
 * with the Nexi pos
 *
 */
public class NexiPosService {

    private NexiPosService() {
    }

    /**
     *
     * Generate the Uri that must be passed to the
     * intent that calls the Nexi pos
     *
     * @param amount
     * @param callerName
     * @param callerTrxId
     * @param email
     * @param sendTicket
     * @param sms
     * @param isUrlTicket
     * @param addInfo1
     * @param addInfo2
     * @param addInfo3
     * @param addInfo4
     * @param addInfo5
     * @param autoClose
     *
     * @return Uri
     */
    public static Uri getPaymentUri(String amount,
                                    String callerName,
                                    String callerTrxId,
                                    String email,
                                    boolean sendTicket,
                                    String sms,
                                    boolean isUrlTicket,
                                    String addInfo1,
                                    String addInfo2,
                                    String addInfo3,
                                    String addInfo4,
                                    String addInfo5,
                                    Boolean autoClose,
                                    Boolean hideRetry,
                                    Boolean tipEnabled,
                                    String tipAmount
                                    ) {
        Uri uri = null;

        try {

            final String amountFormattataPerPos = Utils.convertAmountForPos(amount);
            final String tipAmountFormattataPerPos = Utils.convertAmountForPos(tipAmount);

            Uri.Builder builder = new Uri.Builder();

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            builder.scheme(APP_SCHEMA).
                    authority(APP_AUTHORITY_PAYMENT);

            String uriResponse = builder.build().toString();

            // Create a new Uri with the parameters
            // for the Nexi POS method to be executed.
            builder = new Uri.Builder();

            builder.
                    scheme(NEXIPOS_SCHEMA) .
                    authority(NEXIPOS_AUTHORITY_PAYMENT) .
                    appendQueryParameter("amount", amountFormattataPerPos).
                    appendQueryParameter("callerName", callerName).
                    appendQueryParameter("callerTrxId", callerTrxId).
                    appendQueryParameter("email", email).
                    appendQueryParameter("sendTicket", Boolean.toString(sendTicket)).
                    appendQueryParameter("sms", sms).
                    appendQueryParameter("uri", uriResponse).
                    appendQueryParameter("urlTicket", Boolean.toString(isUrlTicket)).
                    appendQueryParameter("tipEnabled", Boolean.toString(tipEnabled)).
                    appendQueryParameter("tipAmount", tipAmountFormattataPerPos).
                    appendQueryParameter("autoClose", Boolean.toString(autoClose));

            Map<String,String> mapAddInfo = new ArrayMap<String,String>();

            if (addInfo1 != null && !addInfo1.isEmpty()) {
                mapAddInfo.put("addInfoKey1","key1");
                mapAddInfo.put("addInfo1",addInfo1);
            }

            if (addInfo2 != null && !addInfo2.isEmpty()) {
                mapAddInfo.put("addInfoKey2","key2");
                mapAddInfo.put("addInfo2",addInfo2);
            }

            if (addInfo3 != null && !addInfo3.isEmpty()) {
                mapAddInfo.put("addInfoKey3","key3");
                mapAddInfo.put("addInfo3",addInfo3);
            }

            if (addInfo4 != null && !addInfo4.isEmpty()) {
                mapAddInfo.put("addInfoKey4","key4");
                mapAddInfo.put("addInfo4",addInfo4);
            }

            if (addInfo5 != null && !addInfo5.isEmpty()) {
                mapAddInfo.put("addInfoKey5","key5");
                mapAddInfo.put("addInfo5",addInfo5);
            }

            for (Map.Entry<String, String> entry : mapAddInfo.entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }

            builder.appendQueryParameter("hideRetry", Boolean.toString(hideRetry));

            uri = builder.build();

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        return uri;

    } // public static void getPaymentUri(...)

    /**
     *
     * Generate the Uri that must be passed to the
     * intent that calls the Nexi pos
     *
     * @param amount
     * @param callerTrxId
     * @param sendTicket
     * @param urlTicket
     * @param terminalId
     * @param callerName
     * @param email
     * @param sms
     * @param autoClose
     *
     * @return Uri
     *
     */
    public static Uri getReversalUri(String amount,
                                     String callerTrxId,
                                     Boolean sendTicket,
                                     Boolean urlTicket,
                                     String terminalId,
                                     String callerName,
                                     String email,
                                     String sms,
                                     Boolean autoClose,
                                     Boolean hideRetry
                                    ) {

        Uri uri = null;

        try {

            SimpleDateFormat  sdf = new SimpleDateFormat("ddMMyyhhmm");

            String timeStamp = sdf.format(new Date());

            Uri.Builder builder = new Uri.Builder();
            builder.scheme(APP_SCHEMA) .authority(APP_AUTHORITY_REVERSAL);

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            String uriResponse = builder.build().toString();

            // Create a new Uri with the parameters
            // for the Nexi POS method to be executed.
            builder = new Uri.Builder();

            builder.scheme(NEXIPOS_SCHEMA) .
                    authority(NEXIPOS_AUTHORITY_REVERSAL).
                    appendQueryParameter("amount", amount) .
                    appendQueryParameter("callerTrxId", callerTrxId).
                    appendQueryParameter("sendTicket", Boolean.toString(sendTicket)).
                    appendQueryParameter("urlTicket", Boolean.toString(urlTicket)).
                    appendQueryParameter("timestamp", timeStamp) .
                    appendQueryParameter("terminalId", terminalId).
                    appendQueryParameter("callerName", callerName).
                    appendQueryParameter("uri", uriResponse).
                    appendQueryParameter( "email", email).
                    appendQueryParameter("sms", sms).
                    appendQueryParameter("autoClose", Boolean.toString(autoClose)).
                    appendQueryParameter("hideRetry", Boolean.toString(hideRetry));

            uri = builder.build();


        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        return uri;

    } // public static void getReversalUri(..)

    /**
     *
     * Generate the Uri that must be passed to the
     * intent that calls the Nexi pos
     *
     * @param callerName
     * @param terminalId
     * @param callerTrxId
     *
     * @return Uri
     *
     */
    public static Uri getLastTransactionUri(String callerName,
                                            String terminalId,
                                            String callerTrxId
                                           ) {

        Uri uri = null;

        try {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme(APP_SCHEMA) .authority(APP_AUTHORITY_LAST_TRANSACTION);

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            String uriResponse = builder.build().toString();

            // Create a new Uri with the parameters
            // for the Nexi POS method to be executed.
            builder = new Uri.Builder();

            builder.scheme(NEXIPOS_SCHEMA).
                    authority(NEXIPOS_AUTHORITY_LAST_TRANSACTION).
                    appendQueryParameter("callerName", callerName) .
                    appendQueryParameter("terminalId", terminalId).
                    appendQueryParameter("callerTrxId", callerTrxId).
                    appendQueryParameter("uri", uriResponse);

            uri = builder.build();

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        return uri;

    } // public static Uri getLastTransactionUri(...)

    /**
     *
     * Generate the Uri that must be passed to the
     * intent that calls the Nexi pos
     *
     * @param callerName
     * @param callerTrxId
     *
     * @return Uri
     *
     */
    public static Uri getAccountingClosureUri(String callerName,
                                              String callerTrxId
                                             ) {
        Uri uri = null;

        try {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme(APP_SCHEMA) .authority(APP_AUTHORITY_ACCOUNTING_CLOSURE);

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            String uriResponse = builder.build().toString();

            // Create a new Uri with the parameters
            // for the Nexi POS method to be executed.
            builder = new Uri.Builder();

            builder.scheme(NEXIPOS_SCHEMA) .
                    authority(NEXIPOS_AUTHORITY_ACCOUNTING_CLOSURE).
                    appendQueryParameter("callerName", callerName) .
                    appendQueryParameter("callerTrxId", callerTrxId).
                    appendQueryParameter("uri", uriResponse);

            uri = builder.build();


        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        return uri;

    } // public static Uri getAccountingClosureUri(...)

    /**
     *
     * Parses the response sent by Nexi POS and returns
     * an object containing the transaction data executed
     *
     * @param data
     *
     * @return
     *
     */
    public static SoftPosResponsePayment parseSoftPosResponsePayment(Uri data){

        SoftPosResponsePayment softPosResponsePayment = null;

        try {

            if (data != null){

                softPosResponsePayment = new SoftPosResponsePayment();

                softPosResponsePayment.setCallerTrxId(data.getQueryParameter("callerTrxId"));
                softPosResponsePayment.setOperationType(data.getQueryParameter("operationType"));
                softPosResponsePayment.setResult(data.getQueryParameter("result"));
                softPosResponsePayment.setAmount(data.getQueryParameter("amount"));
                softPosResponsePayment.setTipAmount(data.getQueryParameter("tipAmount"));
                softPosResponsePayment.setTotalAmount(data.getQueryParameter("totalAmount"));
                softPosResponsePayment.setActionCode(data.getQueryParameter("actionCode"));
                softPosResponsePayment.setPan(data.getQueryParameter("pan"));
                softPosResponsePayment.setTransactionType(data.getQueryParameter("transactionType"));
                softPosResponsePayment.setAuthorizationNumber(data.getQueryParameter("authorizationNumber"));
                softPosResponsePayment.setTimeStamp(data.getQueryParameter("timestamp"));
                softPosResponsePayment.setResultDescription(data.getQueryParameter("resultDescription"));
                softPosResponsePayment.setCardTypeCVM(data.getQueryParameter("cardTypeCVM"));
                softPosResponsePayment.setAcquireId(data.getQueryParameter("acquirerId"));
                softPosResponsePayment.setStan(data.getQueryParameter("stan"));
                softPosResponsePayment.setOperationNumber(data.getQueryParameter("operationNumber"));
                softPosResponsePayment.setAcquirerName(data.getQueryParameter("acquirerName"));
                softPosResponsePayment.setTerminalID(data.getQueryParameter("terminalId"));
                softPosResponsePayment.setMerchantId(data.getQueryParameter("merchantId"));
                softPosResponsePayment.setUrlTicket(data.getQueryParameter("urlTicket"));
                softPosResponsePayment.setAutoClose(data.getQueryParameter("autoClose"));
                String campiAggiuntivi = data.getQueryParameter("CT122");

                if (campiAggiuntivi != null && !campiAggiuntivi.isEmpty()) {
                    campiAggiuntivi.replace("%7C","|");
                }

                softPosResponsePayment.setCampiAggiuntivi(data.getQueryParameter("CT122"));

            }

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        return softPosResponsePayment;

    } // public static SoftPosResponsePayment parseSoftPosResponsePayment(Uri data)

    /**
     *
     * Parses the response sent by Nexi POS and returns
     * an object containing the transaction data executed
     *
     * @param data
     *
     * @return
     *
     */
    public static SoftPosResponseReversal parseSoftPosResponseReversal(Uri data){

        SoftPosResponseReversal softPosResponseReversal = null;

        try {

            if (data != null){

                softPosResponseReversal = new SoftPosResponseReversal();

                softPosResponseReversal.setCallerTrxId(data.getQueryParameter("callerTrxId"));
                softPosResponseReversal.setOperationType(data.getQueryParameter("operationType"));
                softPosResponseReversal.setResult(data.getQueryParameter("result"));
                softPosResponseReversal.setHostTotal(data.getQueryParameter("hostTotal"));
                softPosResponseReversal.setTerminalTotal(data.getQueryParameter("terminalTotal"));

            }

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        return softPosResponseReversal;

    } // public static SoftPosResponseReversal parseSoftPosResponseReversal(Uri data)

    /**
     *
     * Parses the response sent by Nexi POS and returns
     * an object containing the transaction data executed
     *
     * @param data
     *
     * @return
     */
    public static SoftPosResponseLastTransaction parseSoftPosResponseGetLastTransaction(Uri data){

        SoftPosResponseLastTransaction softPosResponseLastTransaction = null;

        try {

            if (data != null){

                softPosResponseLastTransaction = new SoftPosResponseLastTransaction();

                softPosResponseLastTransaction.setResult(data.getQueryParameter("result"));
                softPosResponseLastTransaction.setCallerTrxId(data.getQueryParameter("callerTrxId"));
                softPosResponseLastTransaction.setAmount(data.getQueryParameter("amount"));
                softPosResponseLastTransaction.setTipAmount(data.getQueryParameter("tipAmount"));
                softPosResponseLastTransaction.setTotalAmount(data.getQueryParameter("totalAmount"));
                softPosResponseLastTransaction.setOperationType(data.getQueryParameter("operationType"));
                softPosResponseLastTransaction.setTerminalType(data.getQueryParameter("terminalType"));
                softPosResponseLastTransaction.setTerminalID(data.getQueryParameter("f41"));

                String campiAggiuntiviCT122         = data.getQueryParameter("CT122");
                String campiAggiuntiviATAGSRECEIPT  = data.getQueryParameter("ATAGSRECEIPT");

                    if (campiAggiuntiviCT122 != null && !campiAggiuntiviCT122.isEmpty()) {
                        campiAggiuntiviCT122.replace("%7C","|");

                        softPosResponseLastTransaction.setCampiAggiuntiviCT122(campiAggiuntiviCT122);
                    }

                    if (campiAggiuntiviATAGSRECEIPT != null && !campiAggiuntiviATAGSRECEIPT.isEmpty()) {
                        campiAggiuntiviCT122.replace("%7C","|");

                        softPosResponseLastTransaction.setCampiAggiuntiviATAGSRECEIPT(campiAggiuntiviATAGSRECEIPT);

                    }

            } //  if (data != null){

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        return softPosResponseLastTransaction;

    } //  public static SoftPosResponseLastTransaction parseSoftPosResponseGetLastTransaction(Uri data)

    /**
     *
     * Parses the response sent by Nexi POS and returns
     * an object containing the transaction data executed
     *
     * @param data
     *
     * @return
     *
     */
    public static SoftPosResponseAccountingClosure parseSoftPosResponseAccountingClosure(Uri data){

        SoftPosResponseAccountingClosure softPosResponseAccountingClosure = null;

        try {

            if (data != null){

                softPosResponseAccountingClosure = new SoftPosResponseAccountingClosure();

                softPosResponseAccountingClosure.setResult(data.getQueryParameter("result"));
                softPosResponseAccountingClosure.setCallerTrxId(data.getQueryParameter("callerTrxId"));
                softPosResponseAccountingClosure.setHostTotal(data.getQueryParameter("hostTotal"));
                softPosResponseAccountingClosure.setTerminalTotal(data.getQueryParameter("terminalTotal"));

            } // if (data != null)

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        return softPosResponseAccountingClosure;

    } //  public static SoftPosResponseAccountingClosure parseSoftPosResponseAccountingClosure(Uri data)


} // fine NexiPosService
