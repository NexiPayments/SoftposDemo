package it.nexi.softpos_evo_kotlin_01.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import it.nexi.softpos_evo_kotlin_01.commons.LoadingDialog
import it.nexi.softpos_evo_kotlin_01.commons.convertAmountForPos
import it.nexi.softpos_evo_kotlin_01.commons.getRandomAmount
import it.nexi.softpos_evo_kotlin_01.commons.getRandomCallerTrxId
import it.nexi.softpos_evo_kotlin_01.commons.showInfoAlert
import it.nexi.softpos_evo_kotlin_01.databinding.ActivityPaymentInsertValuesBinding
import it.nexi.softpos_evo_kotlin_01.domain.Constant
import it.nexi.softpos_evo_kotlin_01.services.SDKNexiService
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

            val amountRandom = getRandomAmount()
            val callerTrxId = getRandomCallerTrxId()

            editTextAmount.setText(amountRandom)
            editTextCallerTrxId.setText(callerTrxId)
            editTextAddInfo1.setText(callerTrxId)

            checkBoxTipEnabled.setOnCheckedChangeListener { _, isChecked ->

                textInputLayoutTipAmount.isEnabled = !isChecked
                textInputLayoutTipAmount.alpha = if (isChecked) 0.5f else 1.0f

                if (isChecked) editTextTipAmount.setText("")

            }

            btnStartPayment.setOnClickListener {

                val autoClose       = checkBoxAutoClose.isChecked
                val isUrlTicket     = checkBoxUrlTicket.isChecked
                val hideRetry       = checkBoxHideRetry.isChecked
                val amount          = editTextAmount.text.toString()
                val isTipEnabled    = checkBoxTipEnabled.isChecked
                val tipAmount       = editTextTipAmount.text.toString()
                val email           = editTextEmail.text.toString()
                val sms             = editTextSms.text.toString()
                val callerTrxId     = editTextCallerTrxId.text.toString()

                val addInfo1        = editTextAddInfo1.text.toString()
                val addInfo2        = editTextAddInfo2.text.toString()
                val addInfo3        = editTextAddInfo3.text.toString()
                val addInfo4        = editTextAddInfo4.text.toString()
                val addInfo5        = editTextAddInfo5.text.toString()

                val isSendTicket = !autoClose


                if (amount.isEmpty() || callerTrxId.isEmpty()) {

                    showInfoAlert(
                        context = this@PaymentInsertValuesActivity,
                        titolo = "Mandatory fields",
                        messaggio = "amount and callerTrxId, cannot be empty."
                    )

                    return@setOnClickListener

                }

                val amountForPos: String = convertAmountForPos(amount)
                val tipAmountForPos = if (tipAmount.isEmpty()) "" else convertAmountForPos(tipAmount)

                // Creating the data object for the sdk
                val paymentData  = getPaymentData(amountForPos,
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
                                                hideRetry,
                                                 isTipEnabled,
                                                 tipAmountForPos
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
                               hideRetray : Boolean,
                               isTipEnabled : Boolean,
                               tipAmount : String
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
                           isTipEnabled,
                           tipAmount,
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