package dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.allens.lib_ios_dialog.R


abstract class ButtonDialog(private val context: Context) {
    var windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var display = windowManager.defaultDisplay

    lateinit var dialog: Dialog


    open fun builder(): ButtonDialog {
        val view = LayoutInflater.from(context).inflate(getLayoutId(), null)
        onLayoutView(view)
        view?.minimumWidth = display.width


        dialog = Dialog(context, R.style.ActionSheetDialogStyle)
        dialog.setContentView(view)
        val window = dialog.window
        window?.setGravity(Gravity.BOTTOM)
        val layoutParams = window?.attributes
        layoutParams?.x = 0
        layoutParams?.y = 0
        window?.attributes = layoutParams
        return this
    }


    open fun show() {
        dialog.show()
    }

    open fun dismiss() {
        if (dialog.isShowing)
            dialog.dismiss()
    }

    open fun isShowing(): Boolean {
        return dialog.isShowing
    }

    abstract fun getLayoutId(): Int

    abstract fun onLayoutView(view: View)

}