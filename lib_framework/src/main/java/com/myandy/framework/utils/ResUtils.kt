package com.myandy.framework.utils

import android.graphics.drawable.ColorDrawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.myandy.framework.helper.AndyAppHelper

/**
 * @desc   资源获取工具类
 */
fun getStringArrayFromResource(@ArrayRes arrayId: Int): Array<String> {
    return AndyAppHelper.getApplication().resources.getStringArray(arrayId)
}

fun getStringFromResource(@StringRes stringId: Int): String {
    return AndyAppHelper.getApplication().getString(stringId)
}

fun getStringFromResource(@StringRes stringId: Int, vararg formatArgs: Any?): String? {
    return AndyAppHelper.getApplication().getString(stringId, *formatArgs)
}

fun getColorFromResource(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(AndyAppHelper.getApplication(), colorRes)
}

fun getDimensionFromResource(@DimenRes dimenRes: Int): Int {
    return AndyAppHelper.getApplication().resources
        .getDimensionPixelSize(dimenRes)
}

fun getColorDrawable(@ColorRes colorRes: Int): ColorDrawable? {
    return ColorDrawable(
        ContextCompat.getColor(
            AndyAppHelper.getApplication(),
            colorRes
        )
    )
}