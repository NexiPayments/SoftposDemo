package it.nexi.softpos_evo_kotlin_01.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import it.nexi.softpos_evo_kotlin_01.domain.Constant
import it.nexi.softpos_evo_kotlin_01.commons.LoadingDialog
import it.nexi.softpos_evo_kotlin_01.services.SDKNexiService
import it.nexi.softpos_evo_kotlin_01.services.PreferencesServvice
import it.nexi.softpos_evo_kotlin_01.commons.getCurrentTimestamp
import it.nexi.softpos_evo_kotlin_01.commons.showInfoAlert
import it.nexi.softpos_evo_kotlin_01.commons.toEuroFormat
import it.nexi.softpos_evo_kotlin_01.databinding.ActivityReversalInsertValuesBinding
import it.nexipos.app2app.data.model.ReversalData
import kotlinx.coroutines.launch

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS Revert method
 *
 */
class ReversalInsertValuesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReversalInsertValuesBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityReversalInsertValuesBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val amount          = PreferencesServvice.getAmount(this)
        val terminalId      = PreferencesServvice.getTerminalId(this)
        val stringaAmount   = amount?.toString() ?:""

        binding.editTextAmountReversal.setText(amount.toEuroFormat())
        binding.editTextTerminalIdReversal.setText(terminalId)

        with(binding) {

            btnStartReversal.setOnClickListener {

                val terminalId      = editTextTerminalIdReversal.text.toString()
                val callerTrxId     = editTextCallerTrxId.text.toString()
                val email           = editTextEmail.text.toString()

                val autoClose       = checkBoxAutoClose.isChecked
                val isUrlTicket     = checkBoxUrlTicket.isChecked
                val hideRetray      = checkBoxHideRetry.isChecked

                val isSendTicket    = !autoClose


                if (terminalId.isEmpty() || stringaAmount.isEmpty()) {

                    showInfoAlert(context = this@ReversalInsertValuesActivity,
                                  titolo = "Mandatory fields",
                                  messaggio = "Terminal ID, amount and callerTrxId, cannot be empty"
                                 )

                    return@setOnClickListener

                }

                val timeStamp = getCurrentTimestamp()
                val sms = ""

                // Creating the data object for the sdk
                val reversalData = ReversalData(stringaAmount,
                                                timeStamp,
                                                Constant.CALLER_NAME,
                                                email,
                                                sms,
                                                callerTrxId,
                                                terminalId,
                                                isSendTicket,
                                                isUrlTicket,
                                                autoClose,
                                                hideRetray
                                               )

                // starts the sdk method.
                startRevert(reversalData)

            } //  btnStartReversal.setOnClickListener {

        } // with(binding) {

        // awaits the response from the SDKNexiService
        lifecycleScope.launch {

            SDKNexiService.operationResult.collect { result ->

                LoadingDialog.hide()

                result?.let {

                    if (it.isSuccess) {

                        Toast.makeText(this@ReversalInsertValuesActivity, "OK: ${it.description}", Toast.LENGTH_LONG).show()

                        // The activity is closed and the SDK
                        // launches the Nexi POS, passing it the
                        // parameters to execute the requested operation.
                        finish()

                    } else {

                        showInfoAlert(this@ReversalInsertValuesActivity, "Error", it.description)

                    }

                    // Resets the LiveData state to prevent the same message
                    // from being forwarded multiple times
                    // (for example, in the event of a smartphone orientation change).
                    SDKNexiService.resetResult()

                } // result?.let {

            } // PaymentManager.operationResult.collect { result ->

        } // lifecycleScope.launch {

    } // onCreate

    /**
     *
     * Starts the sdk method
     *
     */
    private fun startRevert(data: ReversalData) {

        lifecycleScope.launch {

            LoadingDialog.show(this@ReversalInsertValuesActivity)

            // requests the SDKNexiService to start the method
            SDKNexiService.executeRevert(data)

        } // lifecycleScope.launch {

    } // fun startRevert

} // end class