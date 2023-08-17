package com.myandy.user.setting

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.myandy.common.constant.USER_ACTIVITY_SETTING
import com.myandy.framework.base.BaseDataBindActivity
import com.myandy.user.databinding.ActivitySettingBinding

@Route(path = USER_ACTIVITY_SETTING)
class SettingActivity : BaseDataBindActivity<ActivitySettingBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }
}