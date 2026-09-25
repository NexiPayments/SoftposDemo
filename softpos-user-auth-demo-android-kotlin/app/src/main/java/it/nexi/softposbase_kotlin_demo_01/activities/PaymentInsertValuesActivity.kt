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
import it.nexi.softposbase_kotlin_demo_01.databinding.ActivityPaymentInsertValuesBinding
import it.nexi.softposbase_kotlin_demo_01.services.NexiPosService

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS Payment method
 *
 */
class PaymentInsertValuesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentInsertValuesBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        try {

            enableEdgeToEdge()

            binding = ActivityPaymentInsertValuesBinding.inflate(layoutInflater)

            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Generate a random callerTrxId
            binding.editTextCallerTrxId.setText(Utils.generaCallerTrxId())

            // Generate a random amount
            binding.editTextAmount.setText(Utils.generaAmountRandom())

            binding.checkBoxTipEnabled.isChecked = false

            setupListeners()

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Unknown Error")
        }

    } //  override fun onCreate(savedInstanceState: Bundle?) {

    private fun setupListeners() {

        binding.btnStartPayment.setOnClickListener {

            val autoClose   = binding.checkBoxAutoClose.isChecked
            val hideRetry   = binding.checkBoxHideRetry.isChecked
            val isUrlTicket = binding.checkBoxUrlTicket.isChecked
            val amount      = binding.editTextAmount.text.toString()
            val tipEnabled  = binding.checkBoxTipEnabled.isChecked
            val tipAmount   = binding.editTextTipAmount.text.toString()
            val email       = binding.editTextEmail.text.toString()
            val sms         = ""
            val callerTrxId = binding.editTextCallerTrxId.text.toString()

            // Set Additional Tags
            val addInfo1 = binding.editTextAddInfo1.text.toString().trim()
            val addInfo2 = binding.editTextAddInfo2.text.toString().trim()
            val addInfo3 = binding.editTextAddInfo3.text.toString().trim()
            val addInfo4 = binding.editTextAddInfo4.text.toString().trim()
            val addInfo5 = binding.editTextAddInfo5.text.toString().trim()

            val sendTicket = !autoClose

            // Generates the Uri that will be called by the Nexi POS
            // to return the data regarding the executed transaction.
            val uri = NexiPosService.getPaymentUri(amount,
                                                Constant.PARAMETER_SOFTPOS_CALLER_NAME,
                                                   callerTrxId,
                                                   email,
                                                   sendTicket,
                                                   sms,
                                                   isUrlTicket,
                                                   addInfo1,
                                                   addInfo2,
                                                   addInfo3,
                                                   addInfo4,
                                                   addInfo5,
                                                   autoClose,
                                                   hideRetry,
                                                   tipEnabled,
                                                   tipAmount
                                                  )

            // Start Nexi pos app for Payment
            uri?.let {

                val intent = Intent(Intent.ACTION_VIEW, it).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

                startActivity(intent)

                finish()

            } // uri?.let {

        } // binding.btnStartPayment.setOnClickListener

        // Listener per l'abilitazione/disabilitazione del Tip (Mancia)
        // Usiamo "_" perché buttonView non ci serve
        binding.checkBoxTipEnabled.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {

                // The TipAmount edit field is enabled only if
                // the TipEnabled flag is set to false.
                binding.textInputLayoutTipAmount.apply {
                    isEnabled = false
                    alpha = 0.5f
                }

                binding.editTextTipAmount.setText("")

            } else {

                binding.textInputLayoutTipAmount.apply {
                    isEnabled = true
                    alpha = 1.0f
                }

            }

        } //  binding.checkBoxTipEnabled.setOnCheckedChangeListener { _, isChecked ->

    } // private fun setupListeners() {

}