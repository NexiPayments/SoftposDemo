package it.nexi.softposbase_kotlin_demo_01.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it.nexi.softposbase_kotlin_demo_01.commons.Constant
import it.nexi.softposbase_kotlin_demo_01.commons.Utils
import it.nexi.softposbase_kotlin_demo_01.databinding.ActivityLastTransactionInsertValuesBinding
import it.nexi.softposbase_kotlin_demo_01.services.NexiPosService
import it.nexi.softposbase_kotlin_demo_01.services.PreferencesService

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

        try {

            enableEdgeToEdge()

            binding = ActivityLastTransactionInsertValuesBinding.inflate(layoutInflater)
            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Generate a random callerTrxId
            binding.editTextCallerTrxId.setText(Utils.generaCallerTrxId())

            binding.btnStartLastTransaction.setOnClickListener {

                val terminalId = binding.editTextTerminalId.text.toString()
                val callerTrxId = binding.editTextCallerTrxId.text.toString()

                // Generate the Uri that must be passed to the
                // intent that calls the Nexi pos
                val uri: Uri? = NexiPosService.getLastTransactionUri(
                    callerName = Constant.PARAMETER_SOFTPOS_CALLER_NAME,
                    terminalId = terminalId,
                    callerTrxId = callerTrxId
                )

                // Start Nexi pos app for GetLastTransaction
                uri?.let {
                    val intent = Intent(Intent.ACTION_VIEW, it).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }

                    startActivity(intent)

                    finish()

                } // uri?.let {

            } // binding.btnStartLastTransaction.setOnClickListener

        } catch (ex: Exception) {

            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in onCreate LastTransaction")

        }

    } // override fun onCreate(savedInstanceState: Bundle?)

    override fun onResume() {
        super.onResume()

        try {
            // Recupero dell'ultimo Terminal ID utilizzato salvato nelle preferenze
            val terminalId = PreferencesService.getLastPaymentTerminalId(this)

            // Impostazione del valore nella UI con logica di fallback Kotlin-style
            binding.editTextTerminalId.setText(
                if (!terminalId.isNullOrEmpty()) terminalId else "NON PRESENTE"
            )

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in onResume LastTransaction")
        }
    }

}