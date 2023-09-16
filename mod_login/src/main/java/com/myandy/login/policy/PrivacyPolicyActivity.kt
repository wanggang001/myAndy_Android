package com.myandy.login.policy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.myandy.common.constant.Login_ACTIVITY_POLICY
import com.myandy.framework.base.BaseDataBindActivity
import com.myandy.login.databinding.ActivityPrivacyPolicyBinding

/**
 * @desc   隐私协议
 */
@Route(path = Login_ACTIVITY_POLICY)
class PrivacyPolicyActivity : BaseDataBindActivity<ActivityPrivacyPolicyBinding>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PrivacyPolicyActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}