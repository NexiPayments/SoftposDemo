package it.nexi.softpos_evo_java_light.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.nexi.softpos_evo_java_light.databinding.ActivityAccountingClosureInsertValuesBinding;
import it.nexi.softpos_evo_java_light.domain.Constant;
import it.nexi.softpos_evo_java_light.domain.LoadingDialog;
import it.nexi.softpos_evo_java_light.services.SDKNexiService;
import it.nexi.softpos_evo_java_light.services.Utils;
import it.nexipos.app2app.data.model.CloseAccountData;

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS CloseAccount method
 *
 */
public class AccountingClosureInsertValuesActivity extends AppCompatActivity {

    private ActivityAccountingClosureInsertValuesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityAccountingClosureInsertValuesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnStartCloseAccountingClosure.setOnClickListener(v -> {

            String callerTrxId      = binding.editTextCallerTrxId.getText().toString();
            boolean isTipEnabled    = false;
            String tipAmount        = "";

            if (callerTrxId.isEmpty()) {

                Utils.showInfoAlert(
                        this,
                        "Mandatory fields",
                        "Enter  callerTrxId."
                );

                return;

            }

            // Creating the data object for the sdk
            CloseAccountData closeAccount = new CloseAccountData(Constant.CALLER_NAME,
                                                                 callerTrxId,
                                                                 isTipEnabled,
                                                                 tipAmount
                                                                );

            // starts the sdk method.
            startCloseAccount(closeAccount);

        }); // binding.btnStartCloseAccountingClosure.setOnClickListener(

        // An Observer is instantiated to receive communication from the Nexi sdk,
        // after the sdk's method is launched.
        SDKNexiService.getOperationResult().observe(this, result -> {

            // SDKNexiService communicated the outcome of the
            // operation to the activity
            if (result == null) return;

            LoadingDialog.hide();

            if (result.isSuccess()) {

                // SDKNexiService returned a successful result.
                Toast.makeText(this, "OK: " + result.getDescription(), Toast.LENGTH_LONG).show();

                // The activity is closed and the SDK
                // launches the Nexi POS, passing it the
                // parameters to execute the requested operation.
                finish();

            } else {

                // SDKNexiService returned an error.
                Utils.showInfoAlert(this, "Error", result.getDescription());

            }

            // Resets the LiveData state to prevent the same message
            // from being forwarded multiple times
            // (for example, in the event of a smartphone orientation change).
            SDKNexiService.resetResult();

        }); // end SDKNexiService.getOperationResult().observe(

    } // end protected void onCreate(

    /**
     *
     * Starts the sdk method
     *
     */
    private void startCloseAccount(CloseAccountData data) {

        LoadingDialog.show(this);

        // requests the SDKNexiService to start the method
        SDKNexiService.executeCloseAccount(data);

    } // private void startCloseAccount(CloseAccountData data) {

} // end Activity