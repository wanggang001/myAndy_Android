package com.myandy.banner.annotation

import androidx.annotation.IntDef
import com.myandy.banner.mode.PageStyle.MULTI_PAGE
import com.myandy.banner.mode.PageStyle.MULTI_PAGE_OVERLAP
import com.myandy.banner.mode.PageStyle.MULTI_PAGE_SCALE
import com.myandy.banner.mode.PageStyle.NORMAL
import java.lang.annotation.ElementType

/**
 * 指示器页面样式
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@IntDef(NORMAL, MULTI_PAGE, MULTI_PAGE_OVERLAP, MULTI_PAGE_SCALE)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@java.lang.annotation.Target(ElementType.PARAMETER)
annotation class APageStyle()
