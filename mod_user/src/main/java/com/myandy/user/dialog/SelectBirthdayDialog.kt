package com.myandy.user.dialog

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.myandy.framework.base.BaseDialog
import com.myandy.framework.base.BaseDialogFragment
import com.myandy.framework.ext.onClick
import com.myandy.framework.log.LogUtil
import com.myandy.framework.utils.ViewUtils
import com.myandy.framework.utils.dpToPx
import com.myandy.user.databinding.DialogPickerCalendarBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * @desc   生日日期弹框
 */
class SelectBirthdayDialog {
    class Builder(activity: FragmentActivity) : BaseDialogFragment.Builder<Builder>(activity) {

        private val mCurrentData = Calendar.getInstance()

        private var mOnDateCall: ((String?) -> Unit)? = null

        private val mBinding: DialogPickerCalendarBinding =
            DialogPickerCalendarBinding.inflate(LayoutInflater.from(activity))

        init {
            initView()
        }

        private fun initView() {
            setContentView(mBinding.root)
            setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM)
            gravity = Gravity.BOTTOM

            ViewUtils.setClipViewCornerTopRadius(mBinding.clRoot, dpToPx(12))
            mBinding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                LogUtil.i("日期选择回调：$year-$month-$dayOfMonth")
                mCurrentData.set(year, month, dayOfMonth)
            }

            mBinding.tvComplete.onClick {
                val timeStamp = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(mCurrentData.time)
                LogUtil.i("当前选择时间：${timeStamp}")
                mOnDateCall?.invoke(timeStamp)
                dismiss()
            }
            mBinding.tvCancel.onClick {
                dismiss()
            }
        }

        fun setBirthDayDateCall(onDateCall: ((String?) -> Unit)): Builder {
            mOnDateCall = onDateCall
            return this
        }
    }
}