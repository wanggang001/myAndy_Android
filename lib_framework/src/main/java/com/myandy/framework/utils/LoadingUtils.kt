package com.myandy.framework.utils

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.myandy.framework.R

/**
 * 等待提示框
 */
class LoadingUtils(private val mContext: Context) {
    private var loadView: com.myandy.framework.loading.CenterLoadingView? = null

    /**
     * 统一耗时操作Dialog
     */
    fun showLoading(txt: String?) {
        if (loadView == null) {
            loadView = com.myandy.framework.loading.CenterLoadingView(mContext, R.style.dialog)
            // loadView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (loadView?.isShowing == true) {
            loadView?.dismiss()
        }
        if (!TextUtils.isEmpty(txt)) {
            loadView?.setTitle(txt as CharSequence)
        }
        if (mContext is Activity && mContext.isFinishing) {
            return
        }
        loadView?.show()
    }

    /**
     * 关闭Dialog
     */
    fun dismissLoading() {
        if (mContext is Activity && mContext.isFinishing) {
            return
        }

        loadView?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}
