package it.nexi.softpos_evo_kotlin_01.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import it.nexi.softpos_evo_kotlin_01.domain.Constant
import it.nexi.softpos_evo_kotlin_01.commons.LoadingDialog
import it.nexi.softpos_evo_kotlin_01.services.SDKNexiService
import it.nexi.softpos_evo_kotlin_01.commons.showInfoAlert
import it.nexi.softpos_evo_kotlin_01.databinding.ActivityPaymentInsertValuesBinding
import it.nexipos.app2app.data.model.PaymentData
import kotlinx.coroutines.launch

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

        binding = ActivityPaymentInsertValuesBinding.inflate(layoutInflater)

        setContentView(binding.root)

        with(binding) {

            btnStartPayment.setOnClickListener {

                val autoClose   = checkBoxAutoClose.isChecked
                val isUrlTicket = checkBoxUrlTicket.isChecked
                val hideRetry   = checkBoxHideRetry.isChecked
                val rawAmount   = editTextAmount.text.toString()
                val email       = editTextEmail.text.toString()
                val sms         = editTextSms.text.toString()
                val callerTrxId = editTextCallerTrxId.text.toString()

                val addInfo1    = editTextAddInfo1.text.toString()
                val addInfo2    = editTextAddInfo2.text.toString()
                val addInfo3    = editTextAddInfo3.text.toString()
                val addInfo4    = editTextAddInfo4.text.toString()
                val addInfo5    = editTextAddInfo5.text.toString()

                val isSendTicket = !autoClose

                var finalAmount = ""

                val amountDouble = rawAmount.toDoubleOrNull() ?: 0.0

                if (amountDouble > 0.0) {

                    val formatted = String.format(java.util.Locale.ITALY, "%.2f", amountDouble)

                    finalAmount = formatted.replace(".", "").replace(",", "")
                }

                if (finalAmount.isEmpty() || callerTrxId.isEmpty()) {

                    showInfoAlert(
                        context = this@PaymentInsertValuesActivity,
                        titolo = "Mandatory fields",
                        messaggio = "amount and callerTrxId, cannot be empty."
                    )

                    return@setOnClickListener

                }

                // Creating the data object for the sdk
                val paymentData  = getPaymentData(finalAmount,
                                                  callerTrxId,
                                                  isSendTicket,
                                                  isUrlTicket,
                                                Constant.CALLER_NAME,
                                                  addInfo1,
                                                  addInfo2,
                                                  addInfo3,
                                                  addInfo4,
                                                  addInfo5,
                                                  email,
                                                  sms,
                                                  autoClose,
                                                hideRetry
                                                 )

                // starts the sdk method.
                startPayment(paymentData)

            } // btnStartPayment.setOnClickListener {

        } // with

        // awaits the response from the SDKNexiService
        lifecycleScope.launch {

            SDKNexiService.operationResult.collect { result ->

                LoadingDialog.hide()

                result?.let {

                    if (it.isSuccess) {

                        Toast.makeText(this@PaymentInsertValuesActivity,
                                    "OK: ${it.description}",
                                        Toast.LENGTH_LONG).show()

                        // The activity is closed and the SDK
                        // launches the Nexi POS, passing it the
                        // parameters to execute the requested operation.
                        finish()

                    } else {

                        showInfoAlert(this@PaymentInsertValuesActivity,
                                "Error",
                                        it.description
                                     )

                    }

                    // Resets the LiveData state to prevent the same message
                    // from being forwarded multiple times
                    // (for example, in the event of a smartphone orientation change).
                    SDKNexiService.resetResult()

                } // result?.let {

            } //  PaymentManager.operationResult.collect { result ->

        } //  lifecycleScope.launch {

    } // onCreate(

    /**
     *
     * Creating the data object for the sdk
     *
     */
    private fun getPaymentData(amount: String,
                               callerTrxId: String,
                               isSendTicket: Boolean,
                               isUrlTicket: Boolean,
                               callerName: String,
                               addInfo1: String,
                               addInfo2: String,
                               addInfo3: String,
                               addInfo4: String,
                               addInfo5: String,
                               email: String,
                               sms: String,
                               autoClose: Boolean,
                               hideRetray : Boolean
                              ) : PaymentData {

        val mapAddInfo = mutableMapOf<String, String>()

        if (!addInfo1.isNullOrEmpty()) mapAddInfo["addInfo1"] = addInfo1
        if (!addInfo2.isNullOrEmpty()) mapAddInfo["addInfo2"] = addInfo2
        if (!addInfo3.isNullOrEmpty()) mapAddInfo["addInfo3"] = addInfo3
        if (!addInfo4.isNullOrEmpty()) mapAddInfo["addInfo4"] = addInfo4
        if (!addInfo5.isNullOrEmpty()) mapAddInfo["addInfo5"] = addInfo5

        return PaymentData(amount,
                           callerTrxId,
                           isSendTicket,
                           isUrlTicket,
                           callerName,
                        mapAddInfo,
                           email,
                           sms,
                           autoClose,
                           hideRetray
                          )

    } // fun getPaymentData

    /**
     *
     * Starts the sdk method
     *
     */
    private fun startPayment(data: PaymentData) {

        lifecycleScope.launch {

            LoadingDialog.show(this@PaymentInsertValuesActivity)

            // requests the SDKNexiService to start the method
            SDKNexiService.executePay(data)

        } // lifecycleScope.launch {

    } // fun startPayment

} // end Activity