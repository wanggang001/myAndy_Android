package com.myandy.banner.annotation

import androidx.annotation.IntDef
import com.myandy.banner.mode.IndicatorOrientation

/**
 * 指示器方向
 */
@IntDef(
    IndicatorOrientation.INDICATOR_HORIZONTAL, IndicatorOrientation.INDICATOR_VERTICAL,
    IndicatorOrientation.INDICATOR_RTL
)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
annotation class AIndicatorOrientation
