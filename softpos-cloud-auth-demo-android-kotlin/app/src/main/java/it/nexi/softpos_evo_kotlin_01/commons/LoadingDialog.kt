package it.nexi.softpos_evo_kotlin_01.commons

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import it.nexi.softpos_evo_kotlin_01.R

// Oggetto di supporto
// utilizzato per gestire l'attesa di operazioni pesanti in termini di tempo
object LoadingDialog {

    private var dialog: Dialog? = null

    fun show(activity: Activity) {

        if (activity == null || activity.isFinishing || activity.isDestroyed) {

            return

        }

        if (dialog?.isShowing == true) {

            dialog?.dismiss()

        }

        try {

            dialog = Dialog(activity).apply {

                val view = LayoutInflater.from(activity).inflate(R.layout.layout_loading_dialog, null)

                setContentView(view)

                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                setCancelable(false)
                setCanceledOnTouchOutside(false)

                show()

            } // dialog

        } catch (e: Exception) {

            e.printStackTrace()
            dialog = null

        }

    } // show

    fun hide() {

        try {

            dialog?.let {

                if (it.isShowing) {
                    it.dismiss()
                }

            }

            dialog = null

        } catch (e: Exception) {

            e.printStackTrace()

        }   finally {

            dialog = null

        }

    } // hide

} // object LoadingDialog