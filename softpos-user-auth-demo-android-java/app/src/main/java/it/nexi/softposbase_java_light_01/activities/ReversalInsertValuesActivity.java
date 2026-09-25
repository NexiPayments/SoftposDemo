package it.nexi.softposbase_java_light_01.activities;

import static it.nexi.softposbase_java_light_01.commons.Constant.PARAMETER_SOFTPOS_CALLER_NAME;

import android.content.Intent;
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
import it.nexi.softposbase_java_light_01.commons.Utils;
import it.nexi.softposbase_java_light_01.databinding.ActivityReversalInsertValuesBinding;
import it.nexi.softposbase_java_light_01.services.NexiPosService;
import it.nexi.softposbase_java_light_01.services.PreferencesService;


/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS Reversal method
 *
 */
public class ReversalInsertValuesActivity extends AppCompatActivity {

    private ActivityReversalInsertValuesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);

            binding = ActivityReversalInsertValuesBinding.inflate(getLayoutInflater());

            setContentView(binding.getRoot());

            ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

                return insets;
            });

            String terminalID = PreferencesService.getLastPaymentTerminalId(this) ;
            String amount =  PreferencesService.getLastPaymentAmount(this) ;
            String tipAmount = PreferencesService.getLastPaymentTipAmount(this) ;
            String totalAmount = PreferencesService.getLastPaymentTotalAmount(this) ;

            binding.editTextTerminalId.setText(terminalID);
            binding.editTextAmount.setText(Utils.formatAmount(amount));
            binding.editTextTipAmount.setText(Utils.formatAmount(tipAmount));
            binding.editTextTotalAmount.setText(Utils.formatAmount(totalAmount));

            String callerTrxIdRandom = Utils.getRandomCallerTrxId();
            binding.editTextCallerTrxId.setText(callerTrxIdRandom);

            binding.btnAvviaReversal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean autoClose   = binding.checkBoxAutoClose.isChecked();
                    String  email       = binding.editTextEmail.getText().toString();
                    String  callerTrxId = binding.editTextCallerTrxId.getText().toString();
                    boolean isUrlTicket = binding.checkBoxUrlTicket.isChecked();
                    boolean hideRetry   = binding.checkBoxHideRetry.isChecked();
                    boolean sendTicket  = !autoClose;
                    String  sms         = "";

                    Uri uri = NexiPosService.getReversalUri(amount,
                                                            callerTrxId,
                                                            sendTicket,
                                                            isUrlTicket,
                                                            terminalID,
                                                            PARAMETER_SOFTPOS_CALLER_NAME,
                                                            email,
                                                            sms,
                                                            autoClose,
                                                            hideRetry
                                                           );

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Start Nexi pos app for Reversal
                    startActivity(intent);

                    finish();

                }
            });

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }
    }


}