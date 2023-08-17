package com.myandy.network.interceptor

import com.myandy.framework.log.LogUtil
import com.myandy.network.cotstant.ARTICLE_WEBSITE
import com.myandy.network.cotstant.COIN_WEBSITE
import com.myandy.network.cotstant.COLLECTION_WEBSITE
import com.myandy.network.cotstant.KEY_COOKIE
import com.myandy.network.cotstant.NOT_COLLECTION_WEBSITE
import com.myandy.network.manager.CookiesManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @desc   头信息拦截器
 * 添加头信息
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newBuilder = request.newBuilder()
        newBuilder.addHeader("Content-type", "application/json; charset=utf-8")

        val host = request.url().host()
        val url = request.url().toString()

        //给有需要的接口添加Cookies
        if (!host.isNullOrEmpty()  && (url.contains(COLLECTION_WEBSITE)
                        || url.contains(NOT_COLLECTION_WEBSITE)
                        || url.contains(ARTICLE_WEBSITE)
                        || url.contains(COIN_WEBSITE))) {
            val cookies = CookiesManager.getCookies()
            LogUtil.e("HeaderInterceptor:cookies:$cookies", tag = "smy")
            if (!cookies.isNullOrEmpty()) {
                newBuilder.addHeader(KEY_COOKIE, cookies)
            }
        }
        return chain.proceed(newBuilder.build())
    }
}