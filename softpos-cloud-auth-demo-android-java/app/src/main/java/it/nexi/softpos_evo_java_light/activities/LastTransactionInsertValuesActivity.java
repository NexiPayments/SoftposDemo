package it.nexi.softpos_evo_java_light.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import it.nexi.softpos_evo_java_light.databinding.ActivityLastTransactionInsertValuesBinding;
import it.nexi.softpos_evo_java_light.domain.Constant;
import it.nexi.softpos_evo_java_light.domain.LoadingDialog;
import it.nexi.softpos_evo_java_light.services.PreferencesService;
import it.nexi.softpos_evo_java_light.services.SDKNexiService;
import it.nexi.softpos_evo_java_light.services.Utils;
import it.nexipos.app2app.data.model.GetLastTransactionData;

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS GetLastTransaction method
 *
 */
public class LastTransactionInsertValuesActivity extends AppCompatActivity {

    private ActivityLastTransactionInsertValuesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityLastTransactionInsertValuesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Retrieving the terminalId from the app preferences
        String terminalIdSaved = PreferencesService.getLastPaymentTerminalId(this);
        binding.editTextTerminalId.setText(terminalIdSaved);

        String callerTrxIdRandom = Utils.getRandomCallerTrxId();
        binding.editTextCallerTrxId.setText(callerTrxIdRandom);

        binding.btnStartLastTransaction.setOnClickListener(v -> {

            String terminalId = binding.editTextTerminalId.getText().toString();
            String callerTrxId = binding.editTextCallerTrxId.getText().toString();

            boolean isTipEnabled    = false;
            String tipAmount        = "";

            if (terminalId.isEmpty()) {

                Utils.showInfoAlert(this,"Error","'terminalId' cannot be empty");
                return;

            }

            if (callerTrxId.isEmpty()) {

                Utils.showInfoAlert(this,"Error","'callerTrxId' cannot be empty");
                return;

            }

            // Creating the data object for the sdk
            GetLastTransactionData getLastTransactionData = new GetLastTransactionData(Constant.CALLER_NAME,
                                                                                       terminalId,
                                                                                       callerTrxId,
                                                                                       isTipEnabled,
                                                                                       tipAmount
                                                                                      );

            // starts the sdk method.
            startGetLastTransaction(getLastTransactionData);

        }); // end binding.btnStartLastTransaction.setOnClickListener(v -> {

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

        }); // end SDKNexiService.getOperationResult().observe(this, result -> {

    } // end protected void onCreate(

    /**
     *
     * Starts the sdk method
     *
     */
    private void startGetLastTransaction(GetLastTransactionData data) {

        LoadingDialog.show(this);

        // requests the SDKNexiService to start the method
        SDKNexiService.executeGetLastTransaction(data);

    } // private void startGetLastTransaction(GetLastTransactionData data) {

} // end Activity