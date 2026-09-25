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
import it.nexi.softposbase_java_light_01.databinding.ActivityAccountingClosureInsertValuesBinding;
import it.nexi.softposbase_java_light_01.services.NexiPosService;

/**
 *
 * Nexi Payment
 *
 * Manages the insertion of values to be used for the execution
 * of the Nexi POS AccountingClosure method
 *
 */
public class AccountingClosureInsertValuesActivity extends AppCompatActivity {

    private ActivityAccountingClosureInsertValuesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);

            // Init ViewBinding
            binding = ActivityAccountingClosureInsertValuesBinding.inflate(getLayoutInflater());

            setContentView(binding.getRoot());

            String callerTrxIdRandom = Utils.getRandomCallerTrxId();
            binding.editTextCallerTrxIdCloseAccount.setText(callerTrxIdRandom);

            ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            binding.btnAvviaCloseAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String callerTrxId = binding.editTextCallerTrxIdCloseAccount.getText().toString();

                    // Get the Uri for AccountingClosureUri method of Nexi pos
                    Uri uri = NexiPosService.getAccountingClosureUri(PARAMETER_SOFTPOS_CALLER_NAME,
                                                                     callerTrxId
                                                                    );

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Start Nexi pos app for AccountingClosure
                    startActivity(intent);

                    finish();


                } // onClick

            });

        } catch (Exception ex){

            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());

        }

    } // onCreate()


}