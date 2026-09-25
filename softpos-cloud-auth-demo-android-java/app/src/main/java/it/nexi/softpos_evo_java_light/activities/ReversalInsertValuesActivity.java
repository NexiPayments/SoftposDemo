package it.nexi.softpos_evo_java_light.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import it.nexi.softpos_evo_java_light.databinding.ActivityReversalInsertValuesBinding;
import it.nexi.softpos_evo_java_light.domain.Constant;
import it.nexi.softpos_evo_java_light.domain.LoadingDialog;
import it.nexi.softpos_evo_java_light.services.PreferencesService;
import it.nexi.softpos_evo_java_light.services.SDKNexiService;
import it.nexi.softpos_evo_java_light.services.Utils;
import it.nexipos.app2app.data.model.ReversalData;

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS Revert method
 *
 */
public class ReversalInsertValuesActivity extends AppCompatActivity {

    private ActivityReversalInsertValuesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityReversalInsertValuesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        // Retrieve the values of the last payment made, from Preferences.
        String terminalID   = PreferencesService.getLastPaymentTerminalId(this) ;
        String amount       = PreferencesService.getLastPaymentAmount(this) ;
        String tipAmount    = PreferencesService.getLastPaymentTipAmount(this) ;
        String totalAmount  = PreferencesService.getLastPaymentTotalAmount(this) ;

        binding.editTextTerminalIdReversal.setText(terminalID);
        binding.editTextAmountReversal.setText(Utils.formatAmount(amount));
        binding.editTextTipAmount.setText(Utils.formatAmount(tipAmount));
        binding.editTextTotalAmount.setText(Utils.formatAmount(totalAmount));

        String callerTrxIdRandom = Utils.getRandomCallerTrxId();
        binding.editTextCallerTrxId.setText(callerTrxIdRandom);

        binding.btnStartReversal.setOnClickListener(v -> {

            String terminalId       = binding.editTextTerminalIdReversal.getText().toString();
            String callerTrxId      = binding.editTextCallerTrxId.getText().toString();
            String email            = binding.editTextEmail.getText().toString();
            boolean isTipEnabled    = false;

            boolean autoClose       = binding.checkBoxAutoClose.isChecked();
            boolean isUrlTicket     = binding.checkBoxUrlTicket.isChecked();
            boolean hideRetry       = binding.checkBoxHideRetry.isChecked();

            boolean isSendTicket = !autoClose;

            if (terminalId.isEmpty()) {

                Utils.showInfoAlert(this,"Error","'terminalId' cannot be empty");

                return;

            }

            if (callerTrxId.isEmpty()) {

                Utils.showInfoAlert(this,"Error","'callerTrxId' cannot be empty");

                return;

            }

            String timeStamp    = Utils.getCurrentTimestamp();
            String sms          = "";

            // Creating the data object for the sdk
            ReversalData reversalData = new ReversalData(amount,
                                                         timeStamp,
                                                         Constant.CALLER_NAME,
                                                         email,
                                                         sms,
                                                         callerTrxId,
                                                         terminalId,
                                                         isSendTicket,
                                                         isUrlTicket,
                                                         isTipEnabled,
                                                         tipAmount,
                                                         autoClose,
                                                         hideRetry
                                                        );

            // starts the sdk method.
            startRevert(reversalData);

        }); // end binding.btnStartReversal.setOnClickListener(v -> {

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

                Utils.showInfoAlert(this, "Errore", result.getDescription());

            }

            // Resets the LiveData state to prevent the same message
            // from being forwarded multiple times
            // (for example, in the event of a smartphone orientation change).
            SDKNexiService.resetResult();

        }); // end SDKNexiService.getOperationResult().observe(

    } // end onCreate(

    /**
     *
     * Starts the sdk method
     *
     */
    private void startRevert(ReversalData data) {

        LoadingDialog.show(this);

        SDKNexiService.executeRevert(data);

    } // end private void startRevert(

} // end Activity