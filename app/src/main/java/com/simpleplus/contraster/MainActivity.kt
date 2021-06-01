package com.simpleplus.contraster

import android.content.Context
import android.os.Bundle
import android.text.style.ForegroundColorSpan
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        pickersUtil = PickersUtil(binder, this,this)
        binder.activityMainTargetTxt3.text = calculateContrastRatio(pickersUtil.selectedBackgroundColor,pickersUtil.selectedForegroundColor)

    }

    private fun paintSelectedLayout(color: Int) {

        when (binder.activityMainMaterialBtnGroup.checkedButtonId) {
            binder.activityMainBtnBackground.id -> binder.root.setBackgroundColor(color)
            else -> {
                binder.activityMainTargetTxt1.setTextColor(color)
                binder.activityMainTargetTxt2.setTextColor(color)
                binder.activityMainTargetTxt3.setTextColor(color)
            }
        }

    }

    private fun calculateContrastRatio(backgroundColor: Int, foregroundColor: Int) : String{

        val ratio = ColorUtils.calculateContrast(foregroundColor,backgroundColor)


        return when(ratio) {

            in 7.0..21.0 -> "$ratio AAA"
            in 4.5..6.9 -> "$ratio AA"
            in 3.0..4.49 -> "$ratio AA+"
            else -> "$ratio FAIL"

        }

    }

    override fun onColorChanged(currentColorInt: Int, backgroundColor: Int, foregroundColor: Int) {
        paintSelectedLayout(currentColorInt)
        binder.activityMainTargetTxt3.text = calculateContrastRatio(backgroundColor,foregroundColor)

    }


}