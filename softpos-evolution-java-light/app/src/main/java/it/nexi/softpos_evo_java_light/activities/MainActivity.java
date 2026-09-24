package it.nexi.softpos_evo_java_light.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.nexi.softpos_evo_java_light.databinding.ActivityMainBinding;
import it.nexi.softpos_evo_java_light.domain.Constant;
import it.nexi.softpos_evo_java_light.domain.LoadingDialog;
import it.nexi.softpos_evo_java_light.services.SDKNexiService;
import it.nexi.softpos_evo_java_light.services.Utils;
import it.nexipos.app2app.utility.App2AppDomain;


/**
 *
 *  Nexi Payment
 *
 * The app's main activity.
 * It contains the menu that
 * allows you to launch the Nexi POS methods.
 *
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {

            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;

        });

        binding.textViewAppVersion.setText("Version : " + Utils.getAppVersion(this));

        binding.btnPayment.setOnClickListener(v -> {

            Intent intent = new Intent(this, PaymentInsertValuesActivity.class);
            startActivity(intent);

        });

        binding.btnReversal.setOnClickListener(v -> {

            startActivity(new Intent(this, ReversalInsertValuesActivity.class));

        });

        binding.btnLastTransaction.setOnClickListener(v -> {

            startActivity(new Intent(this, LastTransactionInsertValuesActivity.class));

        });

        binding.btnCloseAccount.setOnClickListener(v -> {

            startActivity(new Intent(this, AccountingClosureInsertValuesActivity.class));

        });

        // An Observer is instantiated to receive communication from the Nexi sdk,
        // after the sdk's `init` method is launched.
        SDKNexiService.getOperationResult().observe(this, result -> {

            // SDKNexiService communicated the outcome of the
            // init operation to the activity
            if (result == null) return;

            LoadingDialog.hide();

            if (result.isSuccess()) {

                // SDKNexiService returned a successful result.
                // If the operation result is READY, the menu buttons are enabled.
                if (result.getDescription().equals("READY") ) {

                    binding.textViewStatoConnessioneSDK.setText(result.getDescription());

                    statoButton(true);

                }

            } else {

                // errors occurred during the init phase
                Utils.showInfoAlert(this, "SDK ERROR ", result.getDescription());

                // the menu buttons are disabled.
                statoButton(false);

            }

            // Resets the LiveData state to prevent the same message
            // from being forwarded multiple times
            // (for example, in the event of a smartphone orientation change).
            SDKNexiService.resetResult();

        }); // end SDKNexiService.getOperationResult().observe(

        LoadingDialog.show(this);

        // Launches the SDK's init method,
        // retrieving configuration parameters
        // from the Constant file.
        SDKNexiService.initSDK(this,
                                Constant.CLIENT_ID,
                                Constant.POINT_OF_SALE_ID,
                                Constant.MERCHANT_USER_NAME,
                                Constant.REDIRECT_URI,
                                App2AppDomain.STAGING,
                                Constant.DEEP_LINK_SCHEMA
                            );

    } // fine onCreate()

    /**
     *
     * Enable/disable the buttons based on the SDK connection status.
     *
     */
    private void statoButton(boolean state) {

        binding.btnPayment.setEnabled(state);
        binding.btnReversal.setEnabled(state);
        binding.btnLastTransaction.setEnabled(state);
        binding.btnCloseAccount.setEnabled(state);

    } // private void statoButton(boolean stato) {

} // end Activity