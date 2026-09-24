package it.nexi.softpos_evo_kotlin_01.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import it.nexi.softpos_evo_kotlin_01.domain.Constant
import it.nexi.softpos_evo_kotlin_01.commons.LoadingDialog
import it.nexi.softpos_evo_kotlin_01.services.SDKNexiService
import it.nexi.softpos_evo_kotlin_01.services.PreferencesServvice
import it.nexi.softpos_evo_kotlin_01.commons.showInfoAlert
import it.nexi.softpos_evo_kotlin_01.databinding.ActivityLastTransactionInsertValuesBinding
import it.nexipos.app2app.data.model.GetLastTransactionData
import kotlinx.coroutines.launch

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS GetLastTransaction method
 *
 */
class LastTransactionInsertValuesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLastTransactionInsertValuesBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityLastTransactionInsertValuesBinding.inflate(layoutInflater)

        // Retrieving the terminalId from the app preferences
        val terminalId = PreferencesServvice.getTerminalId(this)

        binding.editTextTerminalId.setText(terminalId)

        setContentView(binding.root)

        binding.btnStartLastTransaction.setOnClickListener {

            val terminalId  = binding.editTextTerminalId.text.toString()
            val callerTrxId = binding.editTextCallerTrxId.text.toString()

            if (terminalId.isEmpty() || callerTrxId.isEmpty()) {

                showInfoAlert(
                    context = this@LastTransactionInsertValuesActivity,
                    titolo = "Mandatory fields",
                    messaggio = "terminalId and callerTrxId, cannot be empty"
                )

                return@setOnClickListener

            } //  if (terminalId.isNullOrEmpty()) {

            // Creating the data object for the sdk
            val getLastTransactionData = GetLastTransactionData(Constant.CALLER_NAME,
                                                                terminalId,
                                                                callerTrxId
                                                               )

            // starts the sdk method.
            startGetLastTransaction(getLastTransactionData)

        } //  binding.btnStartLastTransaction {

        // awaits the response from the SDKNexiService
        lifecycleScope.launch {

            SDKNexiService.operationResult.collect { result ->

                LoadingDialog.hide()

                result?.let {

                    if (it.isSuccess) {

                        Toast.makeText(this@LastTransactionInsertValuesActivity, "OK: ${it.description}", Toast.LENGTH_LONG).show()

                        // The activity is closed and the SDK
                        // launches the Nexi POS, passing it the
                        // parameters to execute the requested operation.
                        finish()

                    } else {

                        showInfoAlert(this@LastTransactionInsertValuesActivity, "Errore", it.description)

                    }

                    // Resets the LiveData state to prevent the same message
                    // from being forwarded multiple times
                    // (for example, in the event of a smartphone orientation change).
                    SDKNexiService.resetResult()

                } // result?.let {

            } // PaymentManager.operationResult.collect { result ->

        } //  lifecycleScope.launch {

    } // onCreate

    /**
     *
     * Starts the sdk method
     *
     */
    private fun startGetLastTransaction(data: GetLastTransactionData) {

        lifecycleScope.launch {

            LoadingDialog.show(this@LastTransactionInsertValuesActivity)

            // requests the SDKNexiService to start the method
            SDKNexiService.executeGetLastTransaction(data)

        } // lifecycleScope.launch {

    } // fun startGetLastTransaction

} // end Activity


