package com.myandy.network.interceptor

import com.myandy.framework.helper.AndyAppHelper
import com.myandy.framework.manager.AppManager
import com.myandy.framework.utils.DeviceInfoUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URLEncoder

/**
 * @desc   公共参数拦截器
 */
class PublicParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newBuilder = request.newBuilder().apply {
            addHeader("device-type", "android")
            addHeader("app-version", AppManager.getAppVersionName(AndyAppHelper.getApplication()))
            addHeader("device-id", DeviceInfoUtils.androidId)
            addHeader("device-os-version", AppManager.getDeviceBuildRelease())//获取手机系统版本号
            val deviceNameStr = AppManager.getDeviceBuildBrand().plus("_")
                    .plus(AppManager.getDeviceBuildModel())
            addHeader("device-name", URLEncoder.encode(deviceNameStr, "UTF-8"))//获取设备类型
        }

        return chain.proceed(newBuilder.build())
    }
}