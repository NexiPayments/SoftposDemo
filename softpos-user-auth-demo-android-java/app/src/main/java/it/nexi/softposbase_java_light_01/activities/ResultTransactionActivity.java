package it.nexi.softposbase_java_light_01.activities;

import static it.nexi.softposbase_java_light_01.commons.Constant.TYPE_OPERATION_ACCOUNTING_CLOSURE;
import static it.nexi.softposbase_java_light_01.commons.Constant.TYPE_OPERATION_LAST_TRANSACTION;
import static it.nexi.softposbase_java_light_01.commons.Constant.TYPE_OPERATION_REVERSAL;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.nexi.softposbase_java_light_01.commons.Constant;
import it.nexi.softposbase_java_light_01.databinding.ActivityResultTransactionBinding;
import it.nexi.softposbase_java_light_01.domain.SoftPosResponseAccountingClosure;
import it.nexi.softposbase_java_light_01.domain.SoftPosResponseLastTransaction;
import it.nexi.softposbase_java_light_01.domain.SoftPosResponsePayment;
import it.nexi.softposbase_java_light_01.domain.SoftPosResponseReversal;
import it.nexi.softposbase_java_light_01.services.NexiPosService;
import it.nexi.softposbase_java_light_01.services.PreferencesService;


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

        try {

            super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);

            // Init ViewBinding
            binding = ActivityResultTransactionBinding.inflate(getLayoutInflater());

            setContentView(binding.getRoot());

            ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            binding.btnCloseActivity.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    finish();

                }
            });

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

    } // onCreate

    // The handling of the transaction result from the Nexi POS
    // is managed within this method.
    // The Nexi POS sends the transaction result via a deep link
    // to the response Uri specified in the intent call.
    @Override
    protected void onResume() {

        try {

            super.onResume();

            // Handle the transaction result sent by the Nexi POS
            if (getIntent() != null && getIntent().getData() != null
                    && getIntent().getData().getHost() != null){
                //  && (getIntent().getData().getHost().equals("payment"))){

                // Extracts the type of operation performed by the Nexi POS
                String operation = getIntent().getData().getHost();

                // Extracts the data sent by Nexi POS
                Uri data = getIntent().getData();

                String result = data.toString();

                // Format data string
                if (result != null && result.length() > 0){
                    result = result.replace("&","&\n");
                }

                binding.editTextResult.setText(result);

                // Select the type of operation performed by the Nexi POS
                switch (operation){

                    case Constant.TYPE_OPERATION_PAYMENT:

                        // To perform a transaction reversal, the Reversal method must
                        // send specific parameters regarding the last executed payment
                        // transaction to the Nexi POS.
                        // Therefore, the necessary parameter values are saved in the
                        // app's preferences.

                        SoftPosResponsePayment softPosResponsePayment = NexiPosService.parseSoftPosResponsePayment(data);

                        if (softPosResponsePayment != null) {

                            String terminalId = softPosResponsePayment.getTerminalID();

                            if (!terminalId.isEmpty()) {

                                PreferencesService.setLastPaymentTerminalid(this, terminalId);
                            }

                            String totalAmount  = softPosResponsePayment.getTotalAmount();
                            String amount       = softPosResponsePayment.getAmount();
                            String tipAmount    = softPosResponsePayment.getTipAmount();

                            if (!totalAmount.isEmpty()) {

                                PreferencesService.setLastPaymentTotalAmount(this, totalAmount);
                                PreferencesService.setLastPaymentAmount(this, amount);
                                PreferencesService.setLastPaymentTipAmount(this, tipAmount);
                            }

                        } // if (softPosResponsePayment != null)

                        break;

                    case TYPE_OPERATION_REVERSAL:

                        SoftPosResponseReversal softPosResponseReversal = NexiPosService.parseSoftPosResponseReversal(data);

                        if (softPosResponseReversal != null) {

                           // ------

                        } // if (softPosResponseLastTransaction != null)

                        break;

                    case TYPE_OPERATION_LAST_TRANSACTION:

                        // To perform a transaction reversal, the Reversal method must
                        // send specific parameters regarding the last executed payment
                        // transaction to the Nexi POS.
                        // Therefore, the necessary parameter values are saved in the
                        // app's preferences.

                        SoftPosResponseLastTransaction softPosResponseLastTransaction = NexiPosService.parseSoftPosResponseGetLastTransaction(data);

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

                        } // if (softPosResponseLastTransaction != null)

                        break;

                    case TYPE_OPERATION_ACCOUNTING_CLOSURE:

                        SoftPosResponseAccountingClosure softPosResponseAccountingClosure  = NexiPosService.parseSoftPosResponseAccountingClosure(data);

                        if (softPosResponseAccountingClosure != null) {

                         // ------

                        } // if (softPosResponseLastTransaction != null)

                        break;

                    default:

                        Log.w(Constant.LOG_PREF_MSG_ERR, "unknown operation : " + operation);

                        break;

                } // Switch

            } else {

            } // if (getIntent() != null && getIntent().getData() != null

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

    } // onResume

}