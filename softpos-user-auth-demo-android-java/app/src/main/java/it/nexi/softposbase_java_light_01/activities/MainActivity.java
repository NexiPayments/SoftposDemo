package it.nexi.softposbase_java_light_01.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.nexi.softposbase_java_light_01.commons.Constant;
import it.nexi.softposbase_java_light_01.databinding.ActivityMainBinding;

/**
 *
 *  Nexi Payment
 *
 * The app's main activity.
 * It contains the menu that allows you
 * to launch the Nexi POS methods.
 *
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);

            EdgeToEdge.enable(this);

            // Init ViewBinding
            binding = ActivityMainBinding.inflate(getLayoutInflater());

            setContentView(binding.getRoot());

            ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            // Set app version
            String versionName ="Version : ";
            PackageInfo pInfo = null;
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                versionName += pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);

            }

            binding.textViewVersioneApp.setText(versionName);

            binding.btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intentNew = new Intent(MainActivity.this, PaymentInsertValuesActivity.class);
                    startActivity(intentNew);
                }

            });

            binding.btnReversal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intentNew = new Intent(MainActivity.this, ReversalInsertValuesActivity.class);
                    startActivity(intentNew);

                }

            });

            binding.btnLastTransaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentNew = new Intent(MainActivity.this, LastTransactionInsertValuesActivity.class);
                    startActivity(intentNew);
                }

            });

            binding.btnCloseAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentNew = new Intent(MainActivity.this, AccountingClosureInsertValuesActivity.class);
                    startActivity(intentNew);
                }

            });

        } catch (Exception ex){
            Log.e(Constant.LOG_PREF_MSG_ERR, ex.getMessage());
        }
    }


}