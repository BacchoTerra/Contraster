package com.simpleplus.contraster.activities

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils

import com.simpleplus.contraster.databinding.ActivityMainBinding
import com.simpleplus.contraster.fragments.SetValueBottomSheet
import com.simpleplus.contraster.util.PickersUtil

class MainActivity : AppCompatActivity(), PickersUtil.OnPickerChangeListener,SetValueBottomSheet.OnColorValueSetListener {
    companion object {
        private const val TAG = "Porsche"
        const val BTM_SHEET_BUNDLE_KEY = "com.simpleplus.contraster.BTMSHEET_KEY"
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
        calculateContrastRatio(
            pickersUtil.selectedBackgroundColor,
            pickersUtil.selectedForegroundColor
        )
        showAndHandleBottomSheet()

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

            in 7.0..21.0 -> SpannableString("Aa ${String.format("%.2f", ratio)} AAA")
            in 4.5..6.9999 -> SpannableString("Aa ${String.format("%.2f", ratio)} AA")
            in 3.0..4.49999 -> SpannableString("Aa ${String.format("%.2f", ratio)} AA+")
            else -> SpannableString("Aa ${String.format("%.2f", ratio)} FAIL")

        }

        spannableString.setSpan(RelativeSizeSpan(3f), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        binder.activityMainTxtContrastRatio.text = spannableString

    }

    private fun showAndHandleBottomSheet() {

        binder.activityMainTxtHexColor.setOnClickListener {

            val setValueBtmSheet = SetValueBottomSheet(this)

            if (binder.activityMainBtnBackground.isChecked) {
                setValueBtmSheet.arguments = Bundle().also {
                    it.putString(
                        BTM_SHEET_BUNDLE_KEY,
                        binder.activityMainBtnBackground.text.toString()
                    )
                }
            } else {
                setValueBtmSheet.arguments = Bundle().also {
                    it.putString(
                        BTM_SHEET_BUNDLE_KEY,
                        binder.activityMainBtnText.text.toString()
                    )
                }
            }

            setValueBtmSheet.show(supportFragmentManager, null)
        }

    }

    override fun onColorChanged(currentColorInt: Int, backgroundColor: Int, foregroundColor: Int) {
        paintSelectedLayout(currentColorInt)
        calculateContrastRatio(backgroundColor, foregroundColor)

    }

    override fun onColorValueSet(colorInt: Int) {
        pickersUtil.updateGroupWithInputColor(colorInt)
    }


}