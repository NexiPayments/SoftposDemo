package it.nexi.softposbase_kotlin_demo_01.activities

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it.nexi.softposbase_kotlin_demo_01.commons.Constant
import it.nexi.softposbase_kotlin_demo_01.databinding.ActivityResultTransactionBinding
import it.nexi.softposbase_kotlin_demo_01.services.NexiPosService
import it.nexi.softposbase_kotlin_demo_01.services.PreferencesService

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

        try {

            enableEdgeToEdge()

            binding = ActivityResultTransactionBinding.inflate(layoutInflater)

            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            binding.btnCloseActivity.setOnClickListener { finish() }

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore onCreate Esito")
        }
    }

    // The handling of the transaction result from the Nexi POS
    // is managed within this method.
    // The Nexi POS sends the transaction result via a deep link
    // to the response Uri specified in the intent call.
    override fun onResume() {

        super.onResume()

        try {

            // Handle the transaction result sent by the Nexi POS
            val data: Uri = intent.data ?: return
            val host: String = data.host ?: return

            // Format data string
            val resultText = data.toString().replace("&", "&\n")

            binding.editTextResultTransaction.setText(resultText)

            Log.d("SOFTPOS_KOTLIN", "Host received: $host")

            // Extracts the type of operation performed by the Nexi POS
            // Select the type of operation performed by the Nexi POS
            when (host) {

                Constant.TIPO_OPERAZIONE_PAYMENT -> handlePaymentResponse(data)

                Constant.TIPO_OPERAZIONE_REVERSAL -> handleReversalResponse(data)

                Constant.TIPO_OPERAZIONE_LAST_TRANSACTION -> handleLastTransactionResponse(data)

                Constant.TIPO_OPERAZIONE_ACCOUNTING_CLOSURE -> handleAccountingClosureResponse(data)

                else -> Log.w("SOFTPOS_KOTLIN", "Unhandled operation: $host")

            } // when (host)

        } catch (ex: Exception) {

            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Error onResume")

        }

    } // override fun onResume()

    private fun handlePaymentResponse(data: Uri) {

        NexiPosService.parseSoftPosResponsePayment(data)?.let { response ->

            // To perform a transaction reversal, the Reversal method must
            // send specific parameters regarding the last executed payment
            // transaction to the Nexi POS.
            // Therefore, the necessary parameter values are saved in the
            // app's preferences.

            if (!response.terminalID.isNullOrEmpty()) {
                PreferencesService.setLastPaymentTerminalid(this, response.terminalID!!)
            }

            if (!response.totalAmount.isNullOrEmpty()) {
                PreferencesService.setLastPaymentTotalAmount(this, response.totalAmount)
                PreferencesService.setLastPaymentAmount(this, response.amount)
                PreferencesService.setLastPaymentTipAmount(this, response.tipAmount)
            }

        } // NexiPosService.parseSoftPosResponsePayment(data)?.let { response ->

    } // private fun handlePaymentResponse(data: Uri) {

    private fun handleReversalResponse(data: Uri) {

        NexiPosService.parseSoftPosResponseReversal(data)?.let {

            Log.d("SOFTPOS_KOTLIN", "Storno ricevuto correttamente")

        } // NexiPosService.parseSoftPosResponseReversal(data)?.let

    } // private fun handleReversalResponse(data: Uri) {

    private fun handleLastTransactionResponse(data: Uri) {

        NexiPosService.parseSoftPosResponseGetLastTransaction(data)?.let { response ->

            // To perform a transaction reversal, the Reversal method must
            // send specific parameters regarding the last executed payment
            // transaction to the Nexi POS.
            // Therefore, the necessary parameter values are saved in the
            // app's preferences.

            if (!response.terminalID.isNullOrEmpty()) {

                PreferencesService.setLastPaymentTerminalid(this, response.terminalID!!)

            }

            if (!response.totalAmount.isNullOrEmpty()) {

                PreferencesService.setLastPaymentTotalAmount(this, response.totalAmount)
                PreferencesService.setLastPaymentAmount(this, response.amount)
                PreferencesService.setLastPaymentTipAmount(this, response.tipAmount)

            }

        } // NexiPosService.parseSoftPosResponseGetLastTransaction(data)?.let { response ->

    } // private fun handleLastTransactionResponse(data: Uri) {

    private fun handleAccountingClosureResponse(data: Uri) {

        NexiPosService.parseSoftPosResponseAccountingClosure(data)?.let {

            Log.d("SOFTPOS_KOTLIN", "Chiusura contabile ricevuta")

        }

    } // private fun handleAccountingClosureResponse(data: Uri) {

}