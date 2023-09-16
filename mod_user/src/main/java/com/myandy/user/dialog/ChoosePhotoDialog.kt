package com.myandy.user.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.myandy.framework.base.BaseDialog
import com.myandy.framework.base.BaseDialogFragment
import com.myandy.framework.ext.onClick
import com.myandy.user.databinding.DialogPhotoChooseBinding

/**
 * @desc   拍照、相册选择弹框
 */
class ChoosePhotoDialog {
    class Builder(activity: FragmentActivity) : BaseDialogFragment.Builder<Builder>(activity) {
        private var mOnTakePicturesCall: (() -> Unit)? = null
        private var mOnPhotoAlbumCall: (() -> Unit)? = null

        private val mBinding: DialogPhotoChooseBinding = DialogPhotoChooseBinding.inflate(LayoutInflater.from(activity))

        init {

            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
            gravity = Gravity.BOTTOM

            mBinding.clPhotoAlbum.onClick {
                mOnPhotoAlbumCall?.invoke()
                dismiss()
            }
            mBinding.clTakePictures.onClick {
                mOnTakePicturesCall?.invoke()
                dismiss()
            }
            mBinding.tvCancel.onClick {
                dismiss()
            }
        }

        fun setTakePicturesCall(onTakePicturesCall: (() -> Unit)): Builder {
            mOnTakePicturesCall = onTakePicturesCall
            return this
        }

        fun setPhotoAlbumCall(onPhotoAlbumCall: (() -> Unit)): Builder {
            mOnPhotoAlbumCall = onPhotoAlbumCall
            return this
        }
    }
}