package it.nexi.softposbase_kotlin_demo_01.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it.nexi.softposbase_kotlin_demo_01.commons.Constant
import it.nexi.softposbase_kotlin_demo_01.commons.Utils
import it.nexi.softposbase_kotlin_demo_01.databinding.ActivityReversalInsertValuesBinding
import it.nexi.softposbase_kotlin_demo_01.services.NexiPosService
import it.nexi.softposbase_kotlin_demo_01.services.PreferencesService


/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS Reversal method
 *
 */
class ReversalInsertValuesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReversalInsertValuesBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        try {

            enableEdgeToEdge()

            binding = ActivityReversalInsertValuesBinding.inflate(layoutInflater)

            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // data relating to the last payment transaction made
            val terminalID  = PreferencesService.getLastPaymentTerminalId(this)
            val amount      = PreferencesService.getLastPaymentAmount(this)
            val tipAmount   = PreferencesService.getLastPaymentTipAmount(this)
            val totalAmount = PreferencesService.getLastPaymentTotalAmount(this)

            binding.apply {

                editTextTerminalId.setText(terminalID)

                editTextAmount.setText(Utils.formatAmount(amount))
                editTextTipAmount.setText(Utils.formatAmount(tipAmount))
                editTextTotalAmount.setText(Utils.formatAmount(totalAmount))

                // // Generate a random callerTrxId
                editTextCallerTrxId.setText(Utils.generaCallerTrxId())
            }

            setupReversalListener(amount, terminalID)

        } catch (ex: Exception) {

            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore Reversal Activity")

        }

    } // override fun onCreate(savedInstanceState: Bundle?

    private fun setupReversalListener(amount: String, terminalId: String) {

        binding.btnStartReversal.setOnClickListener {

            val autoClose = binding.checkBoxAutoClose.isChecked
            val email = binding.editTextEmail.text.toString()
            val callerTrxId = binding.editTextCallerTrxId.text.toString()
            val isUrlTicket = binding.checkBoxUrlTicket.isChecked
            val hideRetry = binding.checkBoxHideRetry.isChecked
            val sendTicket = !autoClose
            val sms = ""

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            val uri = NexiPosService.getReversalUri(amount = amount,
                                                    callerTrxId = callerTrxId,
                                                    sendTicket = sendTicket,
                                                    urlTicket = isUrlTicket,
                                                    terminalId = terminalId,
                                                    callerName = Constant.PARAMETER_SOFTPOS_CALLER_NAME,
                                                    email = email,
                                                    sms = sms,
                                                    autoClose = autoClose,
                                                    hideRetry = hideRetry
                                                   )

            // Start Nexi pos app for Reversal
            uri?.let {

                val intent = Intent(Intent.ACTION_VIEW, it).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

                startActivity(intent)

                finish()

            } // uri?.let {

        } // binding.btnStartReversal.setOnClickListener

    } // private fun setupReversalListener(amount: String, terminalId: String)


}