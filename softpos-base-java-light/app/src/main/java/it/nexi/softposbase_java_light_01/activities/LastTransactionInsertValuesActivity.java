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
import it.nexi.softposbase_java_light_01.databinding.ActivityLastTransactionInsertValuesBinding;
import it.nexi.softposbase_java_light_01.services.NexiPosService;
import it.nexi.softposbase_java_light_01.services.PreferencesService;

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS GetLastTrnsaction method
 *
 */
public class LastTransactionInsertValuesActivity extends AppCompatActivity {

    private ActivityLastTransactionInsertValuesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);

            // Init ViewBinding
            binding = ActivityLastTransactionInsertValuesBinding.inflate(getLayoutInflater());

            setContentView(binding.getRoot());

            ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            // Retrieving the terminalId from the app preferences
            String terminalIdSaved = PreferencesService.getLastPaymentTerminalId(this);
            binding.editTextTerminalId.setText(terminalIdSaved);

            String callerTrxIdRandom = Utils.getRandomCallerTrxId();
            binding.editTextCallerTrxId.setText(callerTrxIdRandom);

            binding.btnAvviaLastTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String terminalId = binding.editTextTerminalId.getText().toString();
                    String callerTrxId = binding.editTextCallerTrxId.getText().toString();

                    // Get the Uri for getLastTransaction method of Nexi pos
                    Uri uri =  NexiPosService.getLastTransactionUri(PARAMETER_SOFTPOS_CALLER_NAME,
                                                                   terminalId,
                                                                   callerTrxId
                                                                  );


                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Start Nexi pos app for Get last transaction
                    startActivity(intent);

                    finish();

                }

            });

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

    }
    @Override
    protected void onResume() {

        try {

            super.onResume();

            // Enters the terminal ID associated with the last payment made into the
            // corresponding input field.
            binding.editTextTerminalId.setText("NOT PRESENT");
            String terminalId = PreferencesService.getLastPaymentTerminalId(this);

            if (terminalId != null && terminalId.length() > 0){
                binding.editTextTerminalId.setText(terminalId);
            }

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }
    }
}