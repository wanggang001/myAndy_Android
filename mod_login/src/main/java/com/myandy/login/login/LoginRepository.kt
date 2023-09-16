package com.myandy.login.login

import com.myandy.common.model.User
import com.myandy.network.manager.ApiManager
import com.myandy.network.repository.BaseRepository

/**
 * 登录仓库
 */
class LoginRepository : BaseRepository() {
    /**
     * 登录
     * @param username  用户名
     * @param password  密码
     */
    suspend fun login(username: String, password: String): User? {
        return requestResponse {
            ApiManager.api.login(username, password)
        }

    }

    /**
     * 注册
     * @param username  用户名
     * @param password  密码
     * @param repassword  确认密码
     */
    suspend fun register(username: String, password: String, repassword: String): User? {
        return requestResponse {
            ApiManager.api.register(username, password, repassword)
        }
    }
}