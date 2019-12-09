package com.allens.model_base.base.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.allens.model_base.base.BaseActivity
import com.allens.model_base.base.BaseFragment


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-13 17:20
 * @Version:        1.0
 */
interface BaseMVVMImpl<M : BaseModel> : LifecycleObserver {
    // 注册Model层
    fun registerModel(model: M)

    //注册 Lifecycle
    fun registerLifecycleOwner(lifecycle: LifecycleOwner)

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(
        owner: LifecycleOwner?,
        event: Lifecycle.Event?
    )

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

}

interface MVVMCallback<M : BaseModel, T : Any> {
    fun createModel(): M

    fun createVMClass(): Class<T>
}


abstract class BaseVM<M : BaseModel> : BaseMVVMImpl<M>, ViewModel() {
    lateinit var model: M

    lateinit var lifecycle: LifecycleOwner
    override fun registerModel(model: M) {
        this.model = model
    }

    override fun registerLifecycleOwner(lifecycle: LifecycleOwner) {
        this.lifecycle = lifecycle
        model.registerLifecycleOwner(lifecycle)
    }


    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }
}


abstract class BaseMVVMAct<V : ViewDataBinding, M : BaseModel, VM : BaseVM<M>> :
    BaseActivity(), MVVMCallback<M, VM> {

    lateinit var bind: V
    lateinit var vm: VM
    override fun initListener() {
        vm = createViewModel(this, createVMClass())
        //绑定生命周期
        lifecycle.addObserver(vm)
        vm.registerModel(createModel())
        //注册生命周期
        vm.registerLifecycleOwner(this)
        bind = DataBindingUtil.setContentView(this, getContentViewId())
        //用LiveData配合DataBinding的话，要手动将生成的Binding布局类和LifecycleOwner关联起来
        bind.lifecycleOwner = this

        initMVVMBind()
        initMVVMListener()
    }


    abstract fun initMVVMBind()
    abstract fun initMVVMListener()
}


abstract class BaseMVVMFragment<V : ViewDataBinding, M : BaseModel, VM : BaseVM<M>> :
    BaseFragment(), MVVMCallback<M, VM> {


    lateinit var bind: V
    lateinit var vm: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = createViewModel(this, createVMClass())
        //绑定生命周期
        lifecycle.addObserver(vm)
        vm.registerModel(createModel())
        //注册生命周期
        vm.registerLifecycleOwner(this)
        bind = DataBindingUtil.inflate(inflater, getContentViewId(), container, false)
        //用LiveData配合DataBinding的话，要手动将生成的Binding布局类和LifecycleOwner关联起来
        bind.lifecycleOwner = this

        return bind.root
    }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
        inflate: View
    ) {
    }


    override fun initListener() {
        initMVVMBind()
        initMVVMListener()
    }


    abstract fun initMVVMBind()
    abstract fun initMVVMListener()
}


private fun <T : ViewModel> createViewModel(
    activity: FragmentActivity, cls: Class<T>
): T {
    return ViewModelProviders.of(activity).get(cls)
}


private fun <T : ViewModel> createViewModel(
    fragment: Fragment, cls: Class<T>
): T {
    return ViewModelProviders.of(fragment).get(cls)
}
