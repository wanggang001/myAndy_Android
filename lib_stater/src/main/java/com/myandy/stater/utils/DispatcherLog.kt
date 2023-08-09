package com.myandy.framework.stater.utils

import com.myandy.framework.helper.AndyAppHelper
import com.myandy.framework.log.LogUtil

object DispatcherLog {
    var isDebug = com.myandy.framework.helper.AndyAppHelper.isDebug()

    @JvmStatic
    fun i(msg: String?) {
        if (msg == null) {
            return
        }
        com.myandy.framework.log.LogUtil.i(msg, tag = "StartTask")
    }
}