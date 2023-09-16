package com.myandy.user.setting

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.myandy.common.constant.USER_ACTIVITY_SETTING
import com.myandy.common.dialog.MessageDialog
import com.myandy.common.manager.FileManager
import com.myandy.common.provider.LoginServiceProvider
import com.myandy.common.provider.SearchServiceProvider
import com.myandy.common.provider.UserServiceProvider
import com.myandy.framework.base.BaseDataBindActivity
import com.myandy.framework.ext.gone
import com.myandy.framework.ext.onClick
import com.myandy.framework.ext.visible
import com.myandy.framework.manager.AppManager
import com.myandy.framework.toast.TipsToast
import com.myandy.framework.utils.ViewUtils
import com.myandy.framework.utils.dpToPx
import com.myandy.framework.utils.getColorFromResource
import com.myandy.framework.utils.getStringFromResource
import com.myandy.network.manager.CookiesManager
import com.myandy.user.R
import com.myandy.user.databinding.ActivitySettingBinding
import com.myandy.user.dialog.LogoutTipsDialog
import com.myandy.user.info.UserInfoActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = USER_ACTIVITY_SETTING)
class SettingActivity : BaseDataBindActivity<ActivitySettingBinding>() {

    var allCacheDir = arrayOfNulls<String>(2)

    override fun initView(savedInstanceState: Bundle?) {
        ViewUtils.setClipViewCornerRadius(mBinding.tvLogout, dpToPx(8))
        mBinding.tvCurrentVersion.text = String.format(
            getString(
                R.string.setting_current_version
            ), AppManager.getAppVersionName(this)
        )
        if (UserServiceProvider.isLogin()) {
            mBinding.tvLogout.visible()
        } else {
            mBinding.tvLogout.gone()
        }
        val rootDir = FileManager.getAppRootDir()
        val imageDir = FileManager.getImageDirectory(this)
        allCacheDir = arrayOf(rootDir, imageDir.absolutePath)
        lifecycleScope.launch(Dispatchers.IO) {
            updateCacheSize()
        }
        initListener()
    }

    /**
     * 更新缓存大小
     */
    private fun updateCacheSize() {
        val size = FileManager.getTotalCacheSize(this, *allCacheDir)
        mBinding.tvCache.text = size
    }

    private fun initListener() {
        mBinding.clUserInfo.onClick {
            UserInfoActivity.start(this)
        }
        mBinding.clAccountSafe.onClick {
            TipsToast.showWarningTips(com.myandy.common.R.string.default_developing)
        }
        mBinding.clCurrentVersion.onClick {
            TipsToast.showWarningTips(R.string.setting_newest_version)
        }
        mBinding.clPrivacyPolicy.onClick {
            LoginServiceProvider.readPolicy(this)
        }
        mBinding.clClearCache.onClick {
            showClearCacheDialog()
        }
        mBinding.clAboutUs.onClick {
//            AboutUsActivity.start(this)
        }
        mBinding.tvLogout.onClick {
            LogoutTipsDialog.Builder(this, mButtonClickListener = {
                showLoading()
                LoginServiceProvider.logout(context = this, lifecycleOwner = this) {
                    CookiesManager.clearCookies()
                    UserServiceProvider.clearUserInfo()
                    SearchServiceProvider.clearSearchHistoryCache()
                    dismissLoading()
                }
            }).show()
        }
    }

    /**
     * 清理缓存弹框
     */
    private fun showClearCacheDialog() {
        MessageDialog.Builder(this).setTitle(getStringFromResource(com.myandy.common.R.string.dialog_tips_title))
            .setMessage(getStringFromResource(R.string.setting_clear_cache_tips))
            .setConfirm(getStringFromResource(com.myandy.common.R.string.default_confirm))
            .setConfirmTxtColor(getColorFromResource(com.myandy.common.R.color.color_0165b8))
            .setCancel(getString(com.myandy.common.R.string.default_cancel))
            .setonCancelListener {
                it?.dismiss()
            }
            .setonConfirmListener {
                clearCache()
                it?.dismiss()
            }.create().show()
    }

    /**
     * 清理缓存
     */
    private fun clearCache() {
        showLoading("正在清理...")
        lifecycleScope.launch {
            allCacheDir.forEach { filesDir ->
                filesDir?.let { FileManager.delAllFile(it) }
            }
            delay(500)
            dismissLoading()
            updateCacheSize()
        }
    }
}