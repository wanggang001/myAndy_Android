package com.myandy.stater.utils

import com.myandy.framework.helper.AndyAppHelper
import com.myandy.framework.log.LogUtil

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