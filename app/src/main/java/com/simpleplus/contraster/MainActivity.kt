package com.simpleplus.contraster

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
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
    private val btnGroup by lazy {
        binder.activityMainMaterialBtnGroup
    }

    //Utils
    private lateinit var pickersUtil: PickersUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        pickersUtil = PickersUtil(binder, this,this)
        btnGroup.check(binder.activityMainBtnBackground.id)
        handleButtonGroup()

    }

    private fun paintLayout(color: Int) {

        when (binder.activityMainMaterialBtnGroup.checkedButtonId) {
            binder.activityMainBtnBackground.id -> binder.root.setBackgroundColor(color)
            else -> {
                binder.activityMainTargetTxt1.setTextColor(color)
                binder.activityMainTargetTxt2.setTextColor(color)
                binder.activityMainTargetTxt3.setTextColor(color)
            }
        }

    }

    private fun handleButtonGroup() {

        btnGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {

                binder.activityMainBtnBackground.id -> {

                    pickersUtil.pickerGroup.setColor(IntegerHSLColor().also {
                        it.setFromColorInt(
                            pickersUtil.selectedBackgroundColor
                        )
                    })

                }

                else -> pickersUtil.pickerGroup.setColor(IntegerHSLColor().also {
                    it.setFromColorInt(
                        pickersUtil.selectedForegroundColor
                    )
                })

            }

        }
    }

    private fun calculateContrastRatio() : String{

        return when(ColorUtils.calculateContrast(pickersUtil.selectedForegroundColor,pickersUtil.selectedBackgroundColor)) {

            7.0 -> "AAA"
            in 4.5..6.9 -> "AA"
            in 3.0..4.4 -> "AA+"
            else -> "FAIL"

        }



    }

    override fun onColorChanged(colorInt: Int) {
        paintLayout(colorInt)
    }

}