package it.nexi.softpos_evo_kotlin_01.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import it.nexi.softpos_evo_kotlin_01.databinding.ActivityResultTransactionBinding
import it.nexi.softpos_evo_kotlin_01.domain.Constant.TYPE_OPERATION_ACCOUNTING_CLOSURE
import it.nexi.softpos_evo_kotlin_01.domain.Constant.TYPE_OPERATION_LAST_TRANSACTION
import it.nexi.softpos_evo_kotlin_01.domain.Constant.TYPE_OPERATION_PAYMENT
import it.nexi.softpos_evo_kotlin_01.domain.Constant.TYPE_OPERATION_REVERSAL
import it.nexi.softpos_evo_kotlin_01.services.PreferencesServvice

/**
 *
 * Nexi Payment
 *
 * Receives via deeplink the outcome of the
 * transaction performed by Nexi POS
 *
 */

class ResultTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityResultTransactionBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnCloseActivity.setOnClickListener {

            finish()

        }

    } // onCreate

    // The handling of the transaction result from the Nexi POS
    // is managed within this method.
    // The Nexi POS sends the transaction result via a deeplink
    // to the response Uri specified in the intent call.
    override fun onResume() {

        super.onResume()

        val uri = intent?.data ?: return
        val operation = uri.host ?: return

        try {

            val resultString = uri.toString()

            Log.d("softpos_kotlin", "Operation: $operation, URI: $resultString")

            binding.editTextResultTransaction.setText(resultString)

            // Select the type of method/operation performed by the Nexi POS
            when (operation) {

                TYPE_OPERATION_PAYMENT -> {

                    parseSoftPosResponsePayment(uri)

                }

                TYPE_OPERATION_REVERSAL -> {

                }

                TYPE_OPERATION_LAST_TRANSACTION -> {

                }

                TYPE_OPERATION_ACCOUNTING_CLOSURE -> {

                }

                else -> {

                    Log.w("softpos_kotlin", "Unknown operation: $operation")

                }

            } // when (operation) {


            intent.data = null

        } catch (e: Exception) {

            binding.editTextResultTransaction.setText(e.message)

        }

    } // onResume(

    /**
     *
     * Example of parsing the deeplink sent by Nexipos
     * relating to a payment transaction
     *
     */
    fun parseSoftPosResponsePayment(uri : Uri) {

        val callerTrxId: String?            = uri.getQueryParameter("callerTrxId")
        val operationType: String?          = uri.getQueryParameter("operationType")
        val result: String?                 = uri.getQueryParameter("result")
        val amount: String?                 = uri.getQueryParameter("amount")
        val actionCode: String?             = uri.getQueryParameter("actionCode")
        val pan: String?                    = uri.getQueryParameter("pan")
        val transactionType: String?        = uri.getQueryParameter("transactionType")
        val authorizationNumber: String?    = uri.getQueryParameter("authorizationNumber")
        val timeStamp: String?              = uri.getQueryParameter("timestamp")
        val resultDescription: String?      = uri.getQueryParameter("resultDescription")
        val cardTypeCVM: String?            = uri.getQueryParameter("cardTypeCVM")
        val acquireId: String?              = uri.getQueryParameter("acquirerId")
        val stan: String?                   = uri.getQueryParameter("stan")
        val operationNumber: String?        = uri.getQueryParameter("operationNumber")
        val acquirerName: String?           = uri.getQueryParameter("acquirerName")
        val terminalID: String?             = uri.getQueryParameter("terminalId")
        val merchantId: String?             = uri.getQueryParameter("merchantId")
        val urlTicket: String?              = uri.getQueryParameter("urlTicket")
        val autoClose: String?              = uri.getQueryParameter("autoClose")

        val amountInt = amount?.toIntOrNull()

        PreferencesServvice.savePaymentData(this,amountInt,terminalID)

    } // fun parseSoftPosResponsePayment

} // end activity