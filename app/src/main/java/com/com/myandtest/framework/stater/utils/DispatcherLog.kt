package com.com.myandtest.framework.stater.utils

import com.com.myandtest.framework.helper.AndyAppHelper
import com.com.myandtest.framework.log.LogUtil

object DispatcherLog {
    var isDebug = AndyAppHelper.isDebug()

    @JvmStatic
    fun i(msg: String?) {
        if (msg == null) {
            return
        }
        LogUtil.i(msg, tag = "StartTask")
    }
}