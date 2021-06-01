package com.simpleplus.contraster.util

import android.content.Context
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import com.simpleplus.contraster.R
import com.simpleplus.contraster.databinding.ActivityMainBinding

class PickersUtil(
   private  val activityMainBinding: ActivityMainBinding,
    val listener: OnPickerChangeListener,
   val context: Context
) {

    //Pickers and picker group
    var huePicker: HSLColorPickerSeekBar = activityMainBinding.activityMainHuePicker
    var brightnessPicker: HSLColorPickerSeekBar = activityMainBinding.activityMainBrightnessPicker
    var saturationPicker: HSLColorPickerSeekBar = activityMainBinding.activityMainSaturationPicker
    val pickerGroup = PickerGroup<IntegerHSLColor>().apply {
        this.registerPickers(huePicker, brightnessPicker, saturationPicker)
    }

    //Dirty hack to set seek bar progress programmatically
    var selectedBackgroundColor : Int = ResourcesCompat.getColor(context.resources,R.color.defaultBackgroundColor,null)
    var selectedForegroundColor : Int = ResourcesCompat.getColor(context.resources,R.color.defaultTextColor,null)


    init {
        attachPickersListener()
    }

    private fun attachPickersListener() {

        pickerGroup.addListener(object :
            ColorSeekBar.OnColorPickListener<ColorSeekBar<IntegerHSLColor>, IntegerHSLColor> {
            override fun onColorChanged(
                picker: ColorSeekBar<IntegerHSLColor>,
                color: IntegerHSLColor,
                value: Int
            ) {
                listener.onColorChanged(color.toColorInt())
            }

            override fun onColorPicked(
                picker: ColorSeekBar<IntegerHSLColor>,
                color: IntegerHSLColor,
                value: Int,
                fromUser: Boolean
            ) {
                if (fromUser)
                bindColorToVariable(color.toColorInt())
            }

            override fun onColorPicking(
                picker: ColorSeekBar<IntegerHSLColor>,
                color: IntegerHSLColor,
                value: Int,
                fromUser: Boolean
            ) {

            }

        })

    }

    private fun bindColorToVariable(color:Int) {

        if (activityMainBinding.activityMainBtnBackground.isChecked) {
            selectedBackgroundColor = color
            return
        }

        selectedForegroundColor = color

    }


    interface OnPickerChangeListener {

        fun onColorChanged(colorInt: Int)

    }


}