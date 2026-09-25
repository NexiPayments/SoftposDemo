package it.nexi.softposbase_java_light_01.activities;

import static it.nexi.softposbase_java_light_01.commons.Constant.PARAMETER_SOFTPOS_CALLER_NAME;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.nexi.softposbase_java_light_01.commons.Constant;
import it.nexi.softposbase_java_light_01.commons.Utils;
import it.nexi.softposbase_java_light_01.databinding.ActivityPaymentInsertValuesBinding;
import it.nexi.softposbase_java_light_01.services.NexiPosService;

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

        try {

            super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);

            // Init ViewBinding
            binding = ActivityPaymentInsertValuesBinding.inflate(getLayoutInflater());

            setContentView(binding.getRoot());

            ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            String callerTrxIdRandom = Utils.getRandomCallerTrxId();
            binding.editTextCallerTrxId.setText(callerTrxIdRandom);

            binding.editTextAmount.setText(Utils.getRandomAmount());
            binding.checkBoxTipEnabled.setChecked(false);

            binding.btnAvviaPayment.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    boolean autoClose   = binding.checkBoxAutoClose.isChecked();
                    boolean hideRetry   = binding.checkBoxHideRetry.isChecked();
                    boolean isUrlTicket = binding.checkBoxUrlTicket.isChecked();
                    String  amount      = binding.editTextAmount.getText().toString();
                    boolean tipEnabled  = binding.checkBoxTipEnabled.isChecked();
                    String  tipAmount   = binding.editTextTipAmount.getText().toString();
                    String  email       = binding.editTextEmail.getText().toString();
                    String  sms         =  "";
                    String  callerTrxId = binding.editTextCallerTrxId.getText().toString();
                    String  addInfo1    = binding.editTextAddInfo1.getText().toString().trim();
                    String  addInfo2    = binding.editTextAddInfo2.getText().toString().trim();
                    String  addInfo3    = binding.editTextAddInfo3.getText().toString().trim();
                    String  addInfo4    = binding.editTextAddInfo4.getText().toString().trim();
                    String  addInfo5    = binding.editTextAddInfo5.getText().toString().trim();

                    boolean sendTicket = !autoClose;

                    // Get the Uri for Payment method of Nexi pos
                    Uri uri = NexiPosService.getPaymentUri(amount,
                                                          PARAMETER_SOFTPOS_CALLER_NAME,
                                                          callerTrxId,
                                                          email,
                                                          sendTicket,
                                                          sms,
                                                          isUrlTicket,
                                                          addInfo1,
                                                          addInfo2,
                                                          addInfo3,
                                                          addInfo4,
                                                          addInfo5,
                                                          autoClose,
                                                          hideRetry,
                                                          tipEnabled,
                                                          tipAmount
                                                         );

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Start Nexi pos app for Payment
                    startActivity(intent);

                    finish();

                } // onClick


            }); // binding.btnAvviaPayment.setOnClickListener(new View.OnClickListener() {

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

        binding.checkBoxTipEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    binding.textInputLayoutTipAmount.setEnabled(false);
                    binding.textInputLayoutTipAmount.setAlpha(0.5f);
                    binding.editTextTipAmount.setText("");

                } else {

                    binding.textInputLayoutTipAmount.setEnabled(true);
                    binding.textInputLayoutTipAmount.setAlpha(1.0f);
                }

            }
        });

    }


}