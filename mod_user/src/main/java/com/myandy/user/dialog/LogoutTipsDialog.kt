package com.myandy.user.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.myandy.framework.base.BaseDialog
import com.myandy.framework.base.BaseDialogFragment
import com.myandy.framework.ext.onClick
import com.myandy.framework.manager.AppManager
import com.myandy.user.databinding.DialogLogoutBinding

/**
 * @Desc   申请售后过期提示dialog
 */
class LogoutTipsDialog {
    class Builder(
        activity: FragmentActivity,
        private var mButtonClickListener: (() -> Unit)? = null
    ) : BaseDialogFragment.Builder<Builder>(activity) {

        private val mBinding: DialogLogoutBinding =
            DialogLogoutBinding.inflate(LayoutInflater.from(context))

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setWidth((AppManager.getScreenWidthPx() * 0.8).toInt())
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.TOAST)
            setCanceledOnTouchOutside(true)

            mBinding.tvApplyReturn.onClick {
                mButtonClickListener?.invoke()
                dismiss()
            }
            mBinding.tvCancel.onClick {
                dismiss()
            }
        }
    }
}