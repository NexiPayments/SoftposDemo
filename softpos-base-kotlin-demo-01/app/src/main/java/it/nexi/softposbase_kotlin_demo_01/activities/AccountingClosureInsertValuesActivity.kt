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
import it.nexi.softposbase_kotlin_demo_01.databinding.ActivityAccountingClosureInsertValuesBinding
import it.nexi.softposbase_kotlin_demo_01.services.NexiPosService

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS AccountingClosure method
 *
 */
class AccountingClosureInsertValuesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountingClosureInsertValuesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {

            enableEdgeToEdge()

            binding = ActivityAccountingClosureInsertValuesBinding.inflate(layoutInflater)

            setContentView(binding.root)

            // Generate a random callerTrxId
            binding.editTextCallerTrxIdCloseAccount.setText(Utils.generaCallerTrxId())

            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            binding.btnStartCloseAccountingClosure.setOnClickListener {

                val callerTrxId = binding.editTextCallerTrxIdCloseAccount.text.toString()

                // Generate the Uri that must be passed to the
                // intent that calls the Nexi pos
                val uri: Uri? = NexiPosService.getAccountingClosureUri(
                    callerName = Constant.PARAMETER_SOFTPOS_CALLER_NAME,
                    callerTrxId = callerTrxId
                )

                // Start Nexi pos app for AccountingClosure
                uri?.let {

                    val intent = Intent(Intent.ACTION_VIEW, it).apply {

                        flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    }

                    startActivity(intent)

                    finish()

                } //  uri?.let

            } // binding.btnStartCloseAccountingClosure.setOnClickListener

        } catch (ex: Exception) {

            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Errore in AccountingClosure Activity")

        }

    } //  override fun onCreate(savedInstanceState: Bundle?)

}