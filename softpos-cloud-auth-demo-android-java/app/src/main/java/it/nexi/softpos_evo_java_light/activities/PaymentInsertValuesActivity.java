package it.nexi.softpos_evo_java_light.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import it.nexi.softpos_evo_java_light.databinding.ActivityPaymentInsertValuesBinding;
import it.nexi.softpos_evo_java_light.domain.Constant;
import it.nexi.softpos_evo_java_light.domain.LoadingDialog;
import it.nexi.softpos_evo_java_light.services.SDKNexiService;
import it.nexi.softpos_evo_java_light.services.Utils;
import it.nexipos.app2app.data.model.PaymentData;


/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS Payment method
 *
 */
public class PaymentInsertValuesActivity extends AppCompatActivity {

    private ActivityPaymentInsertValuesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityPaymentInsertValuesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Gson gson = new Gson();

        // Set random value
        String callerTrxIdRandom = Utils.getRandomCallerTrxId();
        binding.editTextCallerTrxId.setText(callerTrxIdRandom);

        String amountRandom = Utils.getRandomAmount();
        binding.editTextAmount.setText(amountRandom);

        binding.btnStartPayment.setOnClickListener(v -> {

            boolean autoClose   = binding.checkBoxAutoClose.isChecked();
            boolean isUrlTicket = binding.checkBoxUrlTicket.isChecked();
            boolean hideRetry   = binding.checkBoxHideRetry.isChecked();
            boolean isTipAmount = binding.checkBoxTipEnabled.isChecked();

            String amount       = binding.editTextAmount.getText().toString();
            String email        = binding.editTextEmail.getText().toString();
            String sms          = binding.editTextSms.getText().toString();
            String callerTrxId  = binding.editTextCallerTrxId.getText().toString();
            String tipAmount    = binding.editTextTipAmount.getText().toString();

            String addInfo1     = binding.editTextAddInfo1.getText().toString();
            String addInfo2     = binding.editTextAddInfo2.getText().toString();
            String addInfo3     = binding.editTextAddInfo3.getText().toString();
            String addInfo4     = binding.editTextAddInfo4.getText().toString();
            String addInfo5     = binding.editTextAddInfo5.getText().toString();

            if (amount.isEmpty()) {

                Utils.showInfoAlert(this,"Error","'amount' cannot be empty");

                return;

            }

            if (callerTrxId.isEmpty()) {

                Utils.showInfoAlert(this,"Errore","'callerTrxId' cannot be empty");

                return;

            }

            boolean isSendTicket = !autoClose;

            final String amountForPos     = Utils.convertAmountForPos(amount);
            final String tipAmountForPos  = tipAmount.isEmpty() ? "" : Utils.convertAmountForPos(tipAmount);

            // Creating the data object for the sdk
            PaymentData paymentData = getPaymentData(amountForPos,
                                                     callerTrxId,
                                                     isSendTicket,
                                                     isUrlTicket,
                                                     Constant.CALLER_NAME,
                                                     addInfo1,
                                                     addInfo2,
                                                     addInfo3,
                                                     addInfo4,
                                                     addInfo5,
                                                     email,
                                                     sms,
                                                     autoClose,
                                                     hideRetry,
                                                     isTipAmount,
                                                     tipAmountForPos
                                                    );

            String jsonPayment = gson.toJson(paymentData);

            Log.d(Constant.TAG_SOFTPOS_EVO_JAVA, "PaymentData ->  " + jsonPayment);

            // starts the sdk method.
            startPayment(paymentData);

        }); // end binding.btnStartPayment.setOnClickListener(v -> {

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

    } // end onCreate(

    /**
     *
     * Creating the data object for the sdk
     *
     */
    private PaymentData getPaymentData(String amount,
                                       String callerTrxId,
                                       boolean isSendTicket,
                                       boolean isUrlTicket,
                                       String callerName,
                                       String addInfo1,
                                       String addInfo2,
                                       String addInfo3,
                                       String addInfo4,
                                       String addInfo5,
                                       String email,
                                       String sms,
                                       boolean autoClose,
                                       boolean hideRetry,
                                       boolean isTipAmount,
                                       String tipAmount
                                      ) {

        Map<String, String> mapAddInfo = new HashMap<>();

        if (addInfo1 != null && !addInfo1.isEmpty()) mapAddInfo.put("addInfo1", addInfo1);
        if (addInfo2 != null && !addInfo2.isEmpty()) mapAddInfo.put("addInfo2", addInfo2);
        if (addInfo3 != null && !addInfo3.isEmpty()) mapAddInfo.put("addInfo3", addInfo3);
        if (addInfo4 != null && !addInfo4.isEmpty()) mapAddInfo.put("addInfo4", addInfo4);
        if (addInfo5 != null && !addInfo5.isEmpty()) mapAddInfo.put("addInfo5", addInfo5);

        return new PaymentData(amount,
                               callerTrxId,
                               isSendTicket,
                               isUrlTicket,
                               isTipAmount,
                               tipAmount,
                               callerName,
                               mapAddInfo,
                               email,
                               sms,
                               autoClose,
                               hideRetry
                              );

    } // end getPaymentData(

    /**
     *
     * Starts the sdk method
     *
     */
    private void startPayment(PaymentData data) {

        LoadingDialog.show(this);

        // requests the SDKNexiService to start the method
        SDKNexiService.executePay(data);

    } // end private void startPayment(PaymentData data) {

} // end Activity