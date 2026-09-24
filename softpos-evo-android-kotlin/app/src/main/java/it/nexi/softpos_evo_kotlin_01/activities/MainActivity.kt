package it.nexi.softpos_evo_kotlin_01.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it.nexi.softpos_evo_kotlin_01.databinding.ActivityMainBinding
import it.nexi.softpos_evo_kotlin_01.domain.Constant
import it.nexi.softpos_evo_kotlin_01.services.SDKNexiService
import it.nexi.softpos_evo_kotlin_01.commons.getAppVersion
import it.nexipos.app2app.utility.App2AppDomain


/**
 *
 *  Nexi Payment
 *
 * The app's main activity.
 * It contains the menu that
 * allows you to launch the Nexi POS methods.
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.textViewVersionApp.setText("Version: ${getAppVersion(this)}")

        binding.btnPayment.setOnClickListener {

            val intent = Intent(this, PaymentInsertValuesActivity::class.java)

            startActivity(intent)

        }

        binding.btnReversal.setOnClickListener {

            startActivity(Intent(this, ReversalInsertValuesActivity::class.java))

        }

        binding.btnLastTransaction.setOnClickListener {

            startActivity(Intent(this, LastTransactionInsertValuesActivity::class.java))

        }

        binding.btnCloseAccount.setOnClickListener {

            startActivity(Intent(this, CloseAccountInsertValuesActivity::class.java))

        }

        // Starts the SDK's init method and waits for the result.
        SDKNexiService.initSDK(this,
            Constant.CLIENT_ID,
            Constant.POINT_OF_SALE_ID,
            Constant.MERCHANT_USER_NAME,
            Constant.REDIRECT_URI,
            App2AppDomain.STAGING,
            Constant.DEEP_LINK_SCHEMA

        ) {result, message ->

            // result of the init command
            Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()

            binding.textViewStatoConnessioneSDK.setText(message)

            if (result) {

                // returned a successful result.

                buttonState(true)

            } else {

                // errors occurred during the init phase

                buttonState(false)

            }

        } // SDKNexiService.initSDK(this,

    } // onCreate(

    /**
     *
     * Enable/disable the buttons based on the SDK connection status.
     *
     */
    fun buttonState(stato : Boolean) {

        binding.btnPayment.isEnabled = stato
        binding.btnReversal.isEnabled = stato
        binding.btnLastTransaction.isEnabled = stato
        binding.btnCloseAccount.isEnabled = stato

    } // fun buttonState(stato : Boolean)

} // end Activity