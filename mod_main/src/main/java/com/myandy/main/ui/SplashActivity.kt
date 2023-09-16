package com.myandy.main.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import com.myandy.common.constant.MAIN_ACTIVITY_HOME
import com.myandy.common.provider.MainServiceProvider
import com.myandy.framework.base.BaseDataBindActivity
import com.myandy.framework.ext.countDownCoroutines
import com.myandy.framework.ext.onClick
import com.myandy.main.R
import com.myandy.main.databinding.ActivitySplashBinding

class SplashActivity : BaseDataBindActivity<ActivitySplashBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.tvSkip.onClick { ARouter.getInstance().build(MAIN_ACTIVITY_HOME).navigation() }

        countDownCoroutines(
            2,
            lifecycleScope,
            onTick = {
                mBinding.tvSkip.text = getString(R.string.splash_time, it.plus(1).toString())
            }) {
//            ARouter.getInstance().build(MAIN_ACTIVITY_HOME).navigation()
            MainServiceProvider.toMain(this)
            finish()
        }
    }
}