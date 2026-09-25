package it.nexi.softposbase_kotlin_demo_01.services

import android.net.Uri
import android.util.Log
import it.nexi.softposbase_kotlin_demo_01.commons.Constant
import it.nexi.softposbase_kotlin_demo_01.commons.Utils
import it.nexi.softposbase_kotlin_demo_01.domain.SoftPosResponseAccountingClosure
import it.nexi.softposbase_kotlin_demo_01.domain.SoftPosResponseLastTransaction
import it.nexi.softposbase_kotlin_demo_01.domain.SoftPosResponsePayment
import it.nexi.softposbase_kotlin_demo_01.domain.SoftPosResponseReversal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 *
 * Nexi Payment
 *
 * Contains the interface methods
 * with the Nexi pos
 *
 */
object NexiPosService {

    /**
     *
     * Generate the Uri that must be passed to the
     * intent that calls the Nexi pos
     *
     */
    fun getPaymentUri(amount: String?,
        callerName: String?,
        callerTrxId: String?,
        email: String?,
        sendTicket: Boolean,
        sms: String?,
        isUrlTicket: Boolean,
        addInfo1: String?,
        addInfo2: String?,
        addInfo3: String?,
        addInfo4: String?,
        addInfo5: String?,
        autoClose: Boolean?,
        hideRetry: Boolean?,
        tipEnabled: Boolean?,
        tipAmount: String?
    ): Uri? {

        return try {

            val amountFormatted = Utils.convertAmountForPos(amount)
            val tipAmountFormatted = Utils.convertAmountForPos(tipAmount)

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            val uriResponse = Uri.Builder()
                .scheme(Constant.APP_SCHEMA)
                .authority(Constant.APP_AUTHORITY_PAYMENT)
                .build().toString()

            // Create a new Uri with the parameters
            // for the Nexi POS method to be executed.
            Uri.Builder().apply {
                scheme(Constant.NEXIPOS_SCHEMA)
                authority(Constant.NEXIPOS_AUTHORITY_PAYMENT)
                appendQueryParameter("amount", amountFormatted)
                appendQueryParameter("callerName", callerName)
                appendQueryParameter("callerTrxId", callerTrxId)
                appendQueryParameter("email", email)
                appendQueryParameter("sendTicket", sendTicket.toString())
                appendQueryParameter("sms", sms)
                appendQueryParameter("uri", uriResponse)
                appendQueryParameter("urlTicket", isUrlTicket.toString())
                appendQueryParameter("tipEnabled", tipEnabled.toString())
                appendQueryParameter("tipAmount", tipAmountFormatted)
                appendQueryParameter("autoClose", autoClose.toString())
                appendQueryParameter("hideRetry", hideRetry.toString())

                if (!addInfo1.isNullOrEmpty()) {
                    appendQueryParameter("addInfoKey1", "key1")
                    appendQueryParameter("addInfo1", addInfo1)
                }
                if (!addInfo2.isNullOrEmpty()) {
                    appendQueryParameter("addInfoKey2", "key2")
                    appendQueryParameter("addInfo2", addInfo2)
                }
                if (!addInfo3.isNullOrEmpty()) {
                    appendQueryParameter("addInfoKey3", "key3")
                    appendQueryParameter("addInfo3", addInfo3)
                }
                if (!addInfo4.isNullOrEmpty()) {
                    appendQueryParameter("addInfoKey4", "key4")
                    appendQueryParameter("addInfo4", addInfo4)
                }
                if (!addInfo5.isNullOrEmpty()) {
                    appendQueryParameter("addInfoKey5", "key5")
                    appendQueryParameter("addInfo5", addInfo5)
                }
            }.build()

        } catch (ex: Exception) {

            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in getPaymentUri")

            null

        }

    } // fun getPaymentUri(amount: String?

    /**
     *
     * Generate the Uri that must be passed to the
     * intent that calls the Nexi pos
     *
     */
    fun getReversalUri(
        amount: String?,
        callerTrxId: String?,
        sendTicket: Boolean?,
        urlTicket: Boolean?,
        terminalId: String?,
        callerName: String?,
        email: String?,
        sms: String?,
        autoClose: Boolean?,
        hideRetry: Boolean?
    ): Uri? {
        return try {

            val timeStamp = SimpleDateFormat("ddMMyyhhmm", Locale.getDefault()).format(Date())

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            val uriResponse = Uri.Builder()
                .scheme(Constant.APP_SCHEMA)
                .authority(Constant.APP_AUTHORITY_REVERSAL)
                .build().toString()

            // Create a new Uri with the parameters
            // for the Nexi POS method to be executed.
            Uri.Builder().apply {
                scheme(Constant.NEXIPOS_SCHEMA)
                authority(Constant.NEXIPOS_AUTHORITY_REVERSAL)
                appendQueryParameter("amount", amount)
                appendQueryParameter("callerTrxId", callerTrxId)
                appendQueryParameter("sendTicket", sendTicket.toString())
                appendQueryParameter("urlTicket", urlTicket.toString())
                appendQueryParameter("timestamp", timeStamp)
                appendQueryParameter("terminalId", terminalId)
                appendQueryParameter("callerName", callerName)
                appendQueryParameter("uri", uriResponse)
                appendQueryParameter("email", email)
                appendQueryParameter("sms", sms)
                appendQueryParameter("autoClose", autoClose.toString())
                appendQueryParameter("hideRetry", hideRetry.toString())
            }.build()

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in getReversalUri")
            null
        }

    } //  fun getReversalUri(

    /**
     *
     * Generate the Uri that must be passed to the
     * intent that calls the Nexi pos
     *
     */
    fun getLastTransactionUri(
            callerName: String?,
            terminalId: String?,
            callerTrxId: String?
        ): Uri? {
            return try {

                // Generates the Uri that will be called by the Nexi POS
                // to return the data regarding the executed transaction.
                val uriResponse = Uri.Builder()
                    .scheme(Constant.APP_SCHEMA)
                    .authority(Constant.APP_AUTHORITY_LAST_TRANSACTION)
                    .build().toString()

                // Create a new Uri with the parameters
                // for the Nexi POS method to be executed.
                Uri.Builder().apply {
                    scheme(Constant.NEXIPOS_SCHEMA)
                    authority(Constant.NEXIPOS_AUTHORITY_LAST_TRANSACTION)
                    appendQueryParameter("callerName", callerName)
                    appendQueryParameter("terminalId", terminalId)
                    appendQueryParameter("callerTrxId", callerTrxId)
                    appendQueryParameter("uri", uriResponse)
                }.build()

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in getLastTransactionUri")
            null
        }

    } // fun getLastTransactionUri(

    /**
     *
     * Generate the Uri that must be passed to the
     * intent that calls the Nexi pos
     *
     */
    fun getAccountingClosureUri(
        callerName: String?,
        callerTrxId: String?
    ): Uri? {
        return try {

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            val uriResponse = Uri.Builder()
                .scheme(Constant.APP_SCHEMA)
                .authority(Constant.APP_AUTHORITY_ACCOUNTING_CLOSURE)
                .build().toString()

            // Create a new Uri with the parameters
            // for the Nexi POS method to be executed.
            Uri.Builder().apply {
                scheme(Constant.NEXIPOS_SCHEMA)
                authority(Constant.NEXIPOS_AUTHORITY_ACCOUNTING_CLOSURE)
                appendQueryParameter("callerName", callerName)
                appendQueryParameter("callerTrxId", callerTrxId)
                appendQueryParameter("uri", uriResponse)
            }.build()

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in getAccountingClosureUri")
            null
        }

    } // fun getAccountingClosureUri(

    /**
     *
     *  Parses the response sent by Nexi POS and returns
     *  an object containing the transaction data executed
     *
     */
    fun parseSoftPosResponsePayment(data: Uri?): SoftPosResponsePayment? {

        if (data == null) return null

        return try {

            SoftPosResponsePayment(
                callerTrxId = data.getQueryParameter("callerTrxId"),
                operationType = data.getQueryParameter("operationType"),
                result = data.getQueryParameter("result"),
                amount = data.getQueryParameter("amount"),
                tipAmount = data.getQueryParameter("tipAmount"),
                totalAmount = data.getQueryParameter("totalAmount"),
                actionCode = data.getQueryParameter("actionCode"),
                pan = data.getQueryParameter("pan"),
                transactionType = data.getQueryParameter("transactionType"),
                authorizationNumber = data.getQueryParameter("authorizationNumber"),
                timeStamp = data.getQueryParameter("timestamp"),
                resultDescription = data.getQueryParameter("resultDescription"),
                cardTypeCVM = data.getQueryParameter("cardTypeCVM"),
                acquireId = data.getQueryParameter("acquirerId"),
                stan = data.getQueryParameter("stan"),
                operationNumber = data.getQueryParameter("operationNumber"),
                acquirerName = data.getQueryParameter("acquirerName"),
                terminalID = data.getQueryParameter("terminalId"),
                merchantId = data.getQueryParameter("merchantId"),
                urlTicket = data.getQueryParameter("urlTicket"),
                autoClose = data.getQueryParameter("autoClose"),
                campiAggiuntivi = data.getQueryParameter("CT122")?.replace("%7C", "|")
            )

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in parseSoftPosResponsePayment")
            null
        }

    } // fun parseSoftPosResponsePayment(data: Uri?): SoftPosResponsePayment? {

    /**
     *
     * Parses the response sent by Nexi POS and returns
     * an object containing the transaction data executed
     *
     */
    fun parseSoftPosResponseReversal(data: Uri?): SoftPosResponseReversal? {

        if (data == null) return null

        return try {

            SoftPosResponseReversal(
                callerTrxId = data.getQueryParameter("callerTrxId"),
                operationType = data.getQueryParameter("operationType"),
                result = data.getQueryParameter("result"),
                hostTotal = data.getQueryParameter("hostTotal"),
                terminalTotal = data.getQueryParameter("terminalTotal")
            )

        } catch (ex: Exception) {

            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in parseSoftPosResponseReversal")

            null

        }

    } // fun parseSoftPosResponseReversal(data: Uri?): SoftPosResponseReversal? {

    /**
     *
     * Parses the response sent by Nexi POS and returns
     * an object containing the transaction data executed
     *
     */
    fun parseSoftPosResponseGetLastTransaction(data: Uri?): SoftPosResponseLastTransaction? {

        if (data == null) return null

        return try {

            SoftPosResponseLastTransaction().apply {
                result = data.getQueryParameter("result")
                callerTrxId = data.getQueryParameter("callerTrxId")
                amount = data.getQueryParameter("amount")
                tipAmount = data.getQueryParameter("tipAmount")
                totalAmount = data.getQueryParameter("totalAmount")
                operationType = data.getQueryParameter("operationType")
                terminalType = data.getQueryParameter("terminalType")
                terminalID = data.getQueryParameter("f41")
                campiAggiuntiviCT122 = data.getQueryParameter("CT122")?.replace("%7C", "|")
                campiAggiuntiviATAGSRECEIPT = data.getQueryParameter("ATAGSRECEIPT")?.replace("%7C", "|")
            }

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in parseSoftPosResponseGetLastTransaction")
            null
        }

    } // fun parseSoftPosResponseGetLastTransaction(data: Uri?): SoftPosResponseLastTransaction? {

    /**
     *
     * Parses the response sent by Nexi POS and returns
     * an object containing the transaction data executed
     *
     */
    fun parseSoftPosResponseAccountingClosure(data: Uri?): SoftPosResponseAccountingClosure? {

        if (data == null) return null

        return try {

            SoftPosResponseAccountingClosure(
                result = data.getQueryParameter("result"),
                callerTrxId = data.getQueryParameter("callerTrxId"),
                hostTotal = data.getQueryParameter("hostTotal"),
                terminalTotal = data.getQueryParameter("terminalTotal")
            )

        } catch (ex: Exception) {

            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in parseSoftPosResponseAccountingClosure")

            null
        }

    } // fun parseSoftPosResponseAccountingClosure(data: Uri?): SoftPosResponseAccountingClosure?

}