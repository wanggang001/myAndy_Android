package com.myandy.banner.drawer

import com.myandy.banner.mode.IndicatorStyle
import com.myandy.banner.options.IndicatorOptions

/**
 * Indicator Drawer Factory.
 */
internal object DrawerFactory {
    fun createDrawer(indicatorOptions: IndicatorOptions): IDrawer {
        return when (indicatorOptions.indicatorStyle) {
            IndicatorStyle.DASH -> DashDrawer(indicatorOptions)
            IndicatorStyle.ROUND_RECT -> RoundRectDrawer(indicatorOptions)
            else -> CircleDrawer(indicatorOptions)
        }
    }
}
