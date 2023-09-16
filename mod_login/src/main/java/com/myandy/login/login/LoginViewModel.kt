package com.myandy.login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myandy.common.model.User
import com.myandy.framework.toast.TipsToast
import com.myandy.network.viewmodel.BaseViewModel

/**
 * 登录viewModel
 */
class LoginViewModel : BaseViewModel() {
    val loginLiveData = MutableLiveData<User?>()
    val registerLiveData = MutableLiveData<User?>()
    val registerLiveDat1: MutableLiveData<User?> = MutableLiveData()
    val loginRepository by lazy { LoginRepository() }

    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     * @return
     */
    fun login(username: String, password: String): LiveData<User?> {
        launchUI(errorBlock = { code, error ->
            TipsToast.showTips(error)
            loginLiveData.value = null
        }) {
            val data = loginRepository.login(username, password)
            loginLiveData.value = data
        }
        return loginLiveData
    }

    /**
     * 注册
     * @param username  用户名
     * @param password  密码
     * @param repassword  确认密码
     * @return
     */
    fun register(username: String, password: String, repassword: String): LiveData<User?> {
        launchUI(errorBlock = { code, error ->
            TipsToast.showTips(error)
            registerLiveDat1.value = null
        }) {
            val data = loginRepository.register(username, password, repassword)
            registerLiveDat1.value = data
        }
        return registerLiveDat1
    }

}