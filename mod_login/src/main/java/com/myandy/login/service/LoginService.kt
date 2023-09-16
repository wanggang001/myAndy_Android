package com.myandy.login.service

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.myandy.common.constant.LOGIN_SERVICE_LOGIN
import com.myandy.common.provider.UserServiceProvider
import com.myandy.common.service.ILoginService
import com.myandy.framework.log.LogUtil
import com.myandy.framework.toast.TipsToast
import com.myandy.login.login.LoginActivity
import com.myandy.network.manager.ApiManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @desc 提供对ILoginService接口的具体实现
 */
@Route(path = LOGIN_SERVICE_LOGIN)
class LoginService : ILoginService {
    /**
     * 是否登录
     * @return Boolean
     */
    override fun isLogin(): Boolean {
        return UserServiceProvider.isLogin()
    }

    /**
     * 跳转登录页
     * @param context
     */
    override fun login(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    /**
     * 跳转隐私协议页
     * @param context
     */
    override fun readPolicy(context: Context) {

    }

    /**
     * 登出
     */
    override fun logout(
        context: Context,
        lifecycleOwner: LifecycleOwner?,
        observer: Observer<Boolean>
    ) {
        val scope = lifecycleOwner?.lifecycleScope ?: GlobalScope
        scope.launch {
            val response = ApiManager.api.logout()
            if (response?.isFailed() == true) {
                TipsToast.showTips(response.errorMsg)
                return@launch
            }
            LogUtil.e("logout${response?.data}", tag = "myandy")
            observer.onChanged(response?.isFailed() == true)
            login(context)
        }
    }

    override fun init(context: Context?) {}

}