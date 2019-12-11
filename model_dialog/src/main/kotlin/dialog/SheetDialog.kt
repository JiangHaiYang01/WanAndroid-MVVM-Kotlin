package dialog

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.allens.lib_ios_dialog.R

class SheetDialog(private val context: Context) : BottomDialog(context = context) {


    //cancel 的样式
    private val bg_cancel = R.drawable.bg_cancel
    //当item 多个的时候  中间item的样式
    private val bg_multiple_center = R.drawable.bg_item_center
    //当 item 只有一个的时候  item  样式
    private val bg_single_center = R.drawable.bg_item_single
    //当item 多个的时候 最下面的一个item 样式
    private val bg_multiple_bottom = R.drawable.bg_single
    //当item 多个的时候 最上面的一个item 样式
    private val bg_multiple_top = R.drawable.bg_top
    //title 的样式
    private val bg_title = R.drawable.bg_top

    //view
    private lateinit var scrollView: ScrollView
    private lateinit var linearLayout: LinearLayout
    private lateinit var tvTitle: TextView
    private lateinit var tvCancel: TextView


    //单条数据的高度
    private var itemHeight = 50

    //单条数据的字体大小
    private var itemTvSize = 12f
    //取消的字体大小
    //是否显示设置标记
    private var showTitle = false

    //item 数据集合
    private var itemList = mutableListOf<SheetItem>()


    //是否显示线
    private var isShowLine = true
    //中间线的颜色
    private var lineColor = Color.parseColor("#DDDDDD")
    //线的高度
    private var lineHeight = 1

    //item 的颜色
    private var itemColor = SheetItemColor.Blue.getColor()


    //最大单项数目  如果超过次数字  会有滚动效果
    private var maxItem = 7


    override fun getLayoutId(): Int {
        return R.layout.view_actionsheet
    }

    override fun onLayoutView(view: View) {
        scrollView = view.findViewById(R.id.sLayout_content)
        linearLayout = view.findViewById(R.id.lLayout_content)
        tvTitle = view.findViewById(R.id.txt_title)
        tvCancel = view.findViewById(R.id.txt_cancel)

    }

    override fun create(): SheetDialog {
        super.create()
        return this
    }


    //==============================================================================================
    // line
    //==============================================================================================

    fun showLine(isShowLine: Boolean): SheetDialog {
        this.isShowLine = isShowLine
        return this
    }

    fun setLineColor(color: Int): SheetDialog {
        this.lineColor = color
        return this
    }

    fun setLineHeight(height: Int): SheetDialog {
        this.lineHeight = height
        return this
    }


    //==============================================================================================
    // title
    //==============================================================================================
    fun setTitle(title: String): SheetDialog {
        showTitle = true
        tvTitle.text = title
        tvTitle.visibility = View.VISIBLE
        return this
    }


    fun getTitleTextView(): TextView {
        return tvTitle
    }


    //==============================================================================================
    // cancel
    //==============================================================================================
    fun setCancelTvColor(color: Int): SheetDialog {
        tvCancel.setTextColor(color)
        return this
    }

    fun setCancelTvMsg(info: String): SheetDialog {
        tvCancel.text = info
        tvCancel.visibility = View.VISIBLE
        return this
    }

    fun setCancelTvSize(size: Float): SheetDialog {
        tvCancel.textSize = size
        return this
    }


    fun getCancelTextView(): TextView {
        return tvCancel
    }


    //==============================================================================================
    // item
    //==============================================================================================
    fun setItemTvSize(itemTvSize: Float): SheetDialog {
        this.itemTvSize = itemTvSize
        return this
    }

    fun setItemHeight(itemHeight: Int): SheetDialog {
        this.itemHeight = itemHeight
        return this
    }

    //最大单项数目  如果超过次数字  会有滚动效果
    fun setMaxItemSize(size: Int): SheetDialog {
        this.maxItem = size
        return this
    }

    fun setItemColor(color: Int): SheetDialog {
        this.itemColor = color
        return this
    }

    //==============================================================================================
    // add
    //==============================================================================================


    fun addSheetItem(itemName: String): SheetDialog {
        addSheetItem(itemName, SheetItemColor.Blue.getColor(), null)
        return this
    }


    fun addSheetItem(itemName: String, color: Int): SheetDialog {
        addSheetItem(itemName, color, null)
        return this
    }


    fun addSheetItem(
        itemName: String,
        color: Int,
        listener: OnSheetItemClickListener?
    ): SheetDialog {
        itemList.add(SheetItem(itemName, color, listener))
        return this
    }

    //==============================================================================================
    // dialog
    //==============================================================================================
    //点击其他位置是否能够取消
    fun setCanceledOnTouchOutside(cancel: Boolean): SheetDialog {
        dialog.setCanceledOnTouchOutside(cancel)
        return this
    }


    // 是否点击返回能够取消
    fun setCancelable(cancel: Boolean): SheetDialog {
        dialog.setCancelable(cancel)
        return this
    }

    override fun getDialogWidth(): Double {
        return 0.9
    }


    override fun show() {
        prepareShow()
        super.show()
    }

    private fun prepareShow() {
        val size: Int = itemList.size
        //设置 滚动布局的高度为屏幕的一半
        if (size >= maxItem) {
            val params = scrollView.layoutParams as LinearLayout.LayoutParams
            params.height = display.height / 2
            linearLayout.layoutParams = params
        }


        //title cancel 样式
        tvCancel.setBackgroundResource(bg_cancel)
        tvTitle.setBackgroundResource(bg_title)
        tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        itemList.indices.forEach {

            val index = it

            //是否添加title 下面的line
            if (isShowLine && it == 0 && showTitle) {
                addLine()
            }

            val textView = TextView(context)
            textView.text = itemList[index].name
            textView.textSize = itemTvSize
            textView.gravity = Gravity.CENTER
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextAppearance(R.style.style_water)
            }

            textView.setTextColor(itemList[index].color)

            if (showTitle) {
                when (index) {
                    itemList.size - 1 -> {
                        textView.setBackgroundResource(bg_multiple_bottom)
                    }
                    else -> {
                        textView.setBackgroundResource(bg_multiple_center)
                    }
                }
            } else {
                when (index) {
                    0 -> {
                        textView.setBackgroundResource(bg_multiple_top)
                    }
                    itemList.size - 1 -> {
                        textView.setBackgroundResource(bg_multiple_bottom)
                    }
                    else -> {
                        textView.setBackgroundResource(bg_multiple_center)
                    }
                }
            }


            textView.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dip2px(context, itemHeight)
                )

            textView.setTextColor(itemColor)
            textView.setOnClickListener {
                dialog.dismiss()
                itemList[index].listener?.onSheetItemClick(index)
            }
            itemList[it].listener?.onSheetItem(it, textView, itemList[it].name)
            linearLayout.addView(textView)
            if (isShowLine && index < itemList.size - 1) {
                addLine()
            }
        }
    }

    private fun addLine() {
        if (!isShowLine) {
            return
        }
        val line = View(context)
        line.setBackgroundColor(lineColor)
        line.layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dip2px(context, lineHeight)
            )
        linearLayout.addView(line)
    }

}


fun dip2px(context: Context, dpValue: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}


data class SheetItem(
    val name: String,
    val color: Int,
    val listener: OnSheetItemClickListener?
)


abstract class OnSheetItemClickListener {
    abstract fun onSheetItemClick(which: Int)

    fun onSheetItem(index: Int, textView: TextView, title: String) {

    }


}

/***
 * 默认颜色
 */
enum class SheetItemColor(name: String) {
    Blue("#0088FF"),
    Red("#FD4A2E");

    fun getColor(): Int {
        return Color.parseColor(name)
    }

}