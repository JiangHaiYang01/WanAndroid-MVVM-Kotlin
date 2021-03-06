package com.allens

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.allens.config.Config
import com.allens.model_base.base.BaseApplication
import com.allens.model_base.tools.getBasePath
import com.allens.tools.R
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator
import com.tencent.mmkv.MMKV

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-26 11:54
 * @Version:        1.0
 */
class MyApp : BaseApplication() {

    companion object {
        lateinit var context: android.app.Application /* compiled code */
    }


    override fun onAppCreate() {
        context = this
        //日志
        registerLog()
        //key value 配置
        registerMMKV()
        //refresh
        registerRefresh()
        //路由
        registerAuthor()


    }

    private fun registerAuthor() {
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    private fun registerRefresh() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(object : DefaultRefreshHeaderCreator {
            override fun createRefreshHeader(
                context: Context,
                layout: RefreshLayout
            ): RefreshHeader {
                ClassicsHeader.REFRESH_HEADER_PULLING = "下拉可以刷新"
                ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新…"
                ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载…"
                ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新"
                ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成"
                val header = ClassicsHeader(context)
                //设置标题文字大小（sp单位）
                header.setTextSizeTitle(10F)
                //设置时间文字大小（sp单位）
                header.setTextSizeTime(10F)
                //设置时间文字的上边距（dp单位）
                header.setTextTimeMarginTop(1F)
                //设置刷新完成显示的停留时间（设为0可以关闭停留功能）
                header.setFinishDuration(0)
                //设置箭头图片
                header.setArrowResource(R.drawable.act_main_refresh_point)
                //设置箭头的大小（dp单位）
                header.setDrawableArrowSize(18F)
                return header
            }
        })

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(object : DefaultRefreshFooterCreator {
            override fun createRefreshFooter(
                context: Context,
                layout: RefreshLayout
            ): RefreshFooter {
                //指定为经典Footer，默认是 BallPulseFooter
                return ClassicsFooter(context)
                    .setDrawableSize(10F)
                    .setTextSizeTitle(10f)
                    .setDrawableArrowSize(18f)
            }
        })
    }

    private fun registerMMKV() {
        MMKV.initialize(this)
    }

    private fun registerLog() {
        LogHelper.init(
            this,
            getBasePath() + Config.logPath,
            Config.maxLogRom,
            Config.maxLogFile,
            Config.isDebug
        )
    }

}