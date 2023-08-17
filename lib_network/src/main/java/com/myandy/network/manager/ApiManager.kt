package com.myandy.network.manager

import com.myandy.network.api.ApiInterface


/**
 * @desc   API管理器
 */
object ApiManager {
    val api by lazy { HttpManager.create(ApiInterface::class.java) }
}