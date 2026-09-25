package it.nexi.softpos_evo_java_light.domain;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import it.nexi.softpos_evo_java_light.R;

/**
 *
 * Nexy Payment
 *
 * manages waiting dialogs
 *
 */
public class LoadingDialog {

    private static Dialog dialog = null;

    private LoadingDialog() {

    }

    /**
     *
     * Show waiting dialog
     *
     * @param activity
     *
     */
    public static void show(Activity activity) {

        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {

            return;

        }

        if (dialog != null && dialog.isShowing()) {

            dialog.dismiss();

        }

        try {
            dialog = new Dialog(activity);

            View view = LayoutInflater.from(activity).inflate(R.layout.layout_loading_dialog, null);

            dialog.setContentView(view);

            Window window = dialog.getWindow();

            if (window != null) {

                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            }

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            dialog.show();

        } catch (Exception e) {

            e.printStackTrace();

            dialog = null;

        }

    } // end public static void show(

    /**
     *
     * Hide the waiting dialog
     */
    public static void hide() {

        try {

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            dialog = null;

        }

    } // end public static void hide() {

} // end class
