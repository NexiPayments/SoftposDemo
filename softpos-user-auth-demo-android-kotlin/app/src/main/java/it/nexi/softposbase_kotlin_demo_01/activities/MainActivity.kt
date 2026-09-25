package it.nexi.softposbase_kotlin_demo_01.activities

import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it.nexi.softposbase_kotlin_demo_01.commons.Constant
import it.nexi.softposbase_kotlin_demo_01.databinding.ActivityMainBinding

/**
 *
 *  Nexi Payment
 *
 * The app's main activity.
 * It contains the menu that allows you
 * to launch the Nexi POS methods.
 *
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        try {

            super.onCreate(savedInstanceState)

            enableEdgeToEdge()

            binding = ActivityMainBinding.inflate(layoutInflater)

            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            // Set the app version
            setAppVersion()

            // Configure click listener
            setupClickListeners()

        } catch (ex: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, ex.message ?: "Unknown Error")
        }
    }

    private fun setAppVersion() {
        try {

            val pInfo: PackageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getPackageInfo(packageName, android.content.pm.PackageManager.PackageInfoFlags.of(0))
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(packageName, 0)
            }

            binding.textViewAppVersion.text = "Version: ${pInfo.versionName}"

        } catch (e: Exception) {
            Log.e(Constant.LOG_PREFISSO_MSG_ERR, "Error retrieving the version : ${e.message}")
        }
    }

    private fun setupClickListeners() {

        binding.apply {

            btnPayment.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        PaymentInsertValuesActivity::class.java
                    )
                )
            }

            btnReversal.setOnClickListener {
                startActivity(Intent(this@MainActivity, ReversalInsertValuesActivity::class.java))
            }

            btnLastTransaction.setOnClickListener {
                startActivity(Intent(this@MainActivity, LastTransactionInsertValuesActivity::class.java))
            }

            btnCloseAccount.setOnClickListener {
                startActivity(Intent(this@MainActivity, AccountingClosureInsertValuesActivity::class.java))
            }

        } // binding.apply {

    } //  private fun setupClickListeners() {

}
