package com.simpleplus.contraster

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.view.get
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.model.IntegerHSLColor

import com.simpleplus.contraster.databinding.ActivityMainBinding
import com.simpleplus.contraster.util.PickersUtil

class MainActivity : AppCompatActivity(), PickersUtil.OnPickerChangeListener {
    companion object {
        private const val TAG = "Porsche"
    }

    //layout components
    private val binder by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Utils
    private lateinit var pickersUtil: PickersUtil

    //String components
    private lateinit var spannableString: SpannableString

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        pickersUtil = PickersUtil(binder, this, this)
        calculateContrastRatio(pickersUtil.selectedBackgroundColor,pickersUtil.selectedForegroundColor)

    }

    private fun paintSelectedLayout(color: Int) {

        when (binder.activityMainMaterialBtnGroup.checkedButtonId) {
            binder.activityMainBtnBackground.id -> binder.root.setBackgroundColor(color)
            else -> {
                binder.activityMainTxtAppName.setTextColor(color)
                binder.activityMainTxtContrastRatio.setTextColor(color)
                binder.activityMainTxtMessage.setTextColor(color)
                binder.activityMainTxtInformation.setTextColor(color)
            }
        }

    }

    private fun calculateContrastRatio(backgroundColor: Int, foregroundColor: Int) {

        val ratio = ColorUtils.calculateContrast(foregroundColor, backgroundColor)

        spannableString = when (ratio) {

            in 7.0..21.0 -> SpannableString("Aa ${String.format("%.2f",ratio)} AAA")
            in 4.5..6.9 -> SpannableString("Aa ${String.format("%.2f",ratio)} AA")
            in 3.0..4.49 -> SpannableString("Aa ${String.format("%.2f",ratio)} AA+")
            else -> SpannableString("Aa ${String.format("%.2f",ratio)} FAIL")

        }

        spannableString.setSpan(RelativeSizeSpan(3f),0,3,Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        binder.activityMainTxtContrastRatio.text = spannableString

    }

    override fun onColorChanged(currentColorInt: Int, backgroundColor: Int, foregroundColor: Int) {
        paintSelectedLayout(currentColorInt)
        calculateContrastRatio(backgroundColor,foregroundColor)

    }


}