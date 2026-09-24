package it.nexi.softpos_evo_kotlin_01.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import it.nexi.softpos_evo_kotlin_01.domain.Constant
import it.nexi.softpos_evo_kotlin_01.commons.LoadingDialog
import it.nexi.softpos_evo_kotlin_01.services.SDKNexiService
import it.nexi.softpos_evo_kotlin_01.commons.showInfoAlert
import it.nexi.softpos_evo_kotlin_01.databinding.ActivityCloseAccountInsertValuesBinding
import it.nexipos.app2app.data.model.CloseAccountData
import kotlinx.coroutines.launch

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS CloseAccount method
 *
 */
class CloseAccountInsertValuesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCloseAccountInsertValuesBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityCloseAccountInsertValuesBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnStartCloseAccount.setOnClickListener {

            val callerTrxId = binding.editTextCallerTrxId.text.toString()

            if (callerTrxId.isEmpty() || callerTrxId.isEmpty()) {

                showInfoAlert(
                    context = this@CloseAccountInsertValuesActivity,
                    titolo = "Mandatory fields",
                    messaggio = "callerTrxId cannot be empty"
                )

                return@setOnClickListener

            }

            // Creating the data object for the sdk
            val closeAccount = CloseAccountData(Constant.CALLER_NAME,
                                                callerTrxId
                                               )

            // starts the sdk method.
            startCloseAccount(closeAccount)

        } //  binding.btnStartCloseAccount.setOnClickListener {

        // awaits the response from the SDKNexiService
        lifecycleScope.launch {

            SDKNexiService.operationResult.collect { result ->

                LoadingDialog.hide()

                result?.let {

                    if (it.isSuccess) {

                        Toast.makeText(this@CloseAccountInsertValuesActivity, "OK: ${it.description}", Toast.LENGTH_LONG).show()

                        // The activity is closed and the SDK
                        // launches the Nexi POS, passing it the
                        // parameters to execute the requested operation.
                        finish()

                    } else {

                        showInfoAlert(this@CloseAccountInsertValuesActivity, "Error", it.description)

                    }

                    // Resets the LiveData state to prevent the same message
                    // from being forwarded multiple times
                    // (for example, in the event of a smartphone orientation change).
                    SDKNexiService.resetResult()

                } // result?.let {

            } // SDKNexiService.operationResult.collect { result ->

        } // lifecycleScope.launch {

    } // onCreate

    /**
     *
     * Starts the sdk method
     *
     */
    private fun startCloseAccount(data: CloseAccountData) {

        lifecycleScope.launch {

            LoadingDialog.show(this@CloseAccountInsertValuesActivity)

            // requests the SDKNexiService to start the method
            SDKNexiService.executeCloseAccount(data)

        } // lifecycleScope.launch {

    } // fun startCloseAccount

} // end Activity


