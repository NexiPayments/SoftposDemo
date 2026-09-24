package it.nexi.softpos_evo_java_light.activities;


import static it.nexi.softpos_evo_java_light.domain.Constant.TYPE_OPERATION_ACCOUNTING_CLOSURE;
import static it.nexi.softpos_evo_java_light.domain.Constant.TYPE_OPERATION_LAST_TRANSACTION;
import static it.nexi.softpos_evo_java_light.domain.Constant.TYPE_OPERATION_PAYMENT;
import static it.nexi.softpos_evo_java_light.domain.Constant.TYPE_OPERATION_REVERSAL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import it.nexi.softpos_evo_java_light.databinding.ActivityResultTransactionBinding;
import it.nexi.softpos_evo_java_light.domain.Constant;
import it.nexi.softpos_evo_java_light.domain.SoftPosResponseAccountingClosure;
import it.nexi.softpos_evo_java_light.domain.SoftPosResponseLastTransaction;
import it.nexi.softpos_evo_java_light.domain.SoftPosResponsePayment;
import it.nexi.softpos_evo_java_light.domain.SoftPosResponseReversal;
import it.nexi.softpos_evo_java_light.services.PreferencesService;
import it.nexi.softpos_evo_java_light.services.SDKNexiService;

/**
 *
 * Nexi Payment
 *
 * Receives via deeplink the outcome of the
 * transaction performed by Nexi POS
 *
 */
public class ResultTransactionActivity extends AppCompatActivity {

    private ActivityResultTransactionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityResultTransactionBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.btnCloseActivity.setOnClickListener(v -> finish());

    } // end protected void onCreate(

    // The handling of the transaction result from the Nexi POS
    // is managed within this method.
    // The Nexi POS sends the transaction result via a deeplink
    // to the response Uri specified in the intent call.
    @Override
    protected void onResume() {

        try {

            super.onResume();

            // Handle the transaction result sent by the Nexi POS
            if (getIntent() != null && getIntent().getData() != null
                    && getIntent().getData().getHost() != null){
                //  && (getIntent().getData().getHost().equals("payment"))){

                // Extracts the type of operation/method performed by the Nexi POS
                String method = getIntent().getData().getHost();

                // Extracts the data sent by Nexi POS
                Uri data = getIntent().getData();

                // Convert data to string
                String result = data.toString();

                // Format data string
                if (result != null && result.length() > 0){
                    result = result.replace("&","&\n");
                }

                // Show the result
                binding.editTextResultTransaction.setText(result);

                // Select the type of method/operation performed by the Nexi POS
                switch (method) {

                    case TYPE_OPERATION_PAYMENT:

                        // To perform a transaction reversal, the Reversal Activity must
                        // send specific parameters regarding the last executed payment
                        // transaction to the Nexi POS.
                        // Therefore, the necessary parameter values are saved in the
                        // app's preferences.

                        SoftPosResponsePayment softPosResponsePayment = SDKNexiService.parseSoftPosResponsePayment(data);

                        if (softPosResponsePayment != null) {

                            String terminalId = softPosResponsePayment.getTerminalID();

                            if (terminalId != null && !terminalId.isEmpty()) {

                                PreferencesService.setLastPaymentTerminalid(this, terminalId);

                            }

                            String totalAmount  = softPosResponsePayment.getTotalAmount();
                            String amount       = softPosResponsePayment.getAmount();
                            String tipAmount    = softPosResponsePayment.getTipAmount();

                            if (totalAmount != null && !totalAmount.isEmpty()) {

                                PreferencesService.setLastPaymentTotalAmount(this, totalAmount);
                                PreferencesService.setLastPaymentAmount(this, amount);
                                PreferencesService.setLastPaymentTipAmount(this, tipAmount);

                            }

                        } // end if (softPosResponsePayment != null)

                        break;

                    case TYPE_OPERATION_REVERSAL:

                        SoftPosResponseReversal softPosResponseReversal = SDKNexiService.parseSoftPosResponseReversal(data);

                        if (softPosResponseReversal != null) {

                            // ------

                        } // end if (softPosResponseLastTransaction != null)

                        break;

                    case TYPE_OPERATION_LAST_TRANSACTION:

                        // To perform a transaction reversal, the Reversal Activity must
                        // send specific parameters regarding the last executed payment
                        // transaction to the Nexi POS.
                        // Therefore, the necessary parameter values are saved in the
                        // app's preferences.

                        SoftPosResponseLastTransaction softPosResponseLastTransaction = SDKNexiService.parseSoftPosResponseGetLastTransaction(data);

                        if (softPosResponseLastTransaction != null) {

                            String terminalId = softPosResponseLastTransaction.getTerminalID();

                            if (terminalId != null && !terminalId.isEmpty()) {

                                PreferencesService.setLastPaymentTerminalid(this, terminalId);
                            }

                            String totalAmount = softPosResponseLastTransaction.getTotalAmount();
                            String amount = softPosResponseLastTransaction.getAmount();
                            String tipAmount = softPosResponseLastTransaction.getTipAmount();

                            if (totalAmount != null && !totalAmount.isEmpty()) {

                                PreferencesService.setLastPaymentTotalAmount(this, totalAmount);
                                PreferencesService.setLastPaymentAmount(this, amount);
                                PreferencesService.setLastPaymentTipAmount(this, tipAmount);
                            }

                        } // end if (softPosResponseLastTransaction != null)

                        break;

                    case TYPE_OPERATION_ACCOUNTING_CLOSURE:

                        SoftPosResponseAccountingClosure softPosResponseAccountingClosure  = SDKNexiService.parseSoftPosResponseAccountingClosure(data);

                        if (softPosResponseAccountingClosure != null) {

                            // ------

                        } // end if (softPosResponseLastTransaction != null)

                        break;

                    default:

                        Log.w(Constant.TAG_SOFTPOS_EVO_JAVA, "unknown method : " + method);

                        break;

                } // end switch (host)

            } else {

                Log.e(Constant.TAG_SOFTPOS_EVO_JAVA, "Server error");

            } // end if (getIntent() != null && getIntent().getData() != null

        } catch (Exception e) {

            binding.editTextResultTransaction.setText(e.getMessage());

            Log.e(Constant.TAG_SOFTPOS_EVO_JAVA, "Deeplink parsing error: " + e.getMessage());
        }

    } // end  protected void onResume() {

} // end Activity