package dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.allens.lib_ios_dialog.R

abstract class BaseDialog(private val context: Context) {
    private var windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var display = windowManager.defaultDisplay

    lateinit var dialog: Dialog


    open fun create(): BaseDialog {
        val view = LayoutInflater.from(context).inflate(getLayoutId(), null)
        dialog = Dialog(context, getDialogStyle())
        onBaseDialog(dialog)
        dialog.setContentView(view)

        val height: Int = if (getInflateHeightFull()) {
            display.height
        } else {
            LinearLayout.LayoutParams.WRAP_CONTENT
        }
        val params = FrameLayout.LayoutParams((display.width * getDialogWidth()).toInt(), height)
        view?.layoutParams = params

        if (getDialogFromBottom()) {
            val window = dialog.window
            window?.setGravity(Gravity.BOTTOM)
            val layoutParams = window?.attributes
            layoutParams?.x = 0
            layoutParams?.y = 0
            window?.attributes = layoutParams
        }

        onLayoutView(view)
        return this
    }

    //加载布局
    abstract fun getLayoutId(): Int

    //传递view
    abstract fun onLayoutView(view: View)

    //dialog 样式
    open fun getDialogStyle(): Int {
        return R.style.AlertDialogStyle
    }

    //返回dialog
    open fun onBaseDialog(dialog: Dialog) {

    }

    //是否撑满高度
    open fun getInflateHeightFull(): Boolean {
        return false

    }

    // dialog 宽度 * 屏幕宽度 max 1.0
    open fun getDialogWidth(): Double {
        return 0.8
    }

    //dialog 是否从底部弹出
    open fun getDialogFromBottom(): Boolean {
        return false
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
}