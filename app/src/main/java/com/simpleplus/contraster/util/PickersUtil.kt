package com.simpleplus.contraster.util

import android.content.Context
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import com.google.android.material.button.MaterialButton
import com.simpleplus.contraster.R
import com.simpleplus.contraster.databinding.ActivityMainBinding

class PickersUtil(
   private  val activityMainBinding: ActivityMainBinding,
   private val listener: OnPickerChangeListener,
   context: Context
) : ColorSeekBar.OnColorPickListener<ColorSeekBar<IntegerHSLColor>,IntegerHSLColor>{

    private val TAG = "Porsche"

    //Pickers and picker group
    var huePicker: HSLColorPickerSeekBar = activityMainBinding.activityMainHuePicker
    var brightnessPicker: HSLColorPickerSeekBar = activityMainBinding.activityMainBrightnessPicker
    var saturationPicker: HSLColorPickerSeekBar = activityMainBinding.activityMainSaturationPicker
    private val pickerGroup = PickerGroup<IntegerHSLColor>().apply {
        this.registerPickers(huePicker, brightnessPicker, saturationPicker)
    }

    //Variables to hold selected colors
    var selectedBackgroundColor = ResourcesCompat.getColor(context.resources,R.color.defaultBackgroundColor,null)
    var selectedForegroundColor = ResourcesCompat.getColor(context.resources,R.color.defaultTextColor,null)

    //Layout components
    private val btnGroup = activityMainBinding.activityMainMaterialBtnGroup
    private val btnBackground = btnGroup[0] as MaterialButton

    init {
        pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedBackgroundColor) })
        pickerGroup.addListener(this)
        activityMainBinding.activityMainMaterialBtnGroup.check(activityMainBinding.activityMainBtnBackground.id)
        handleHexColorDisplay(selectedBackgroundColor)
        handleButtonGroupSelection()
    }

    private fun handleButtonGroupSelection() {

      btnGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->

            setGroupColorWithoutFuckingEverything()
      }

    }

    private fun setGroupColorWithoutFuckingEverything() {
        pickerGroup.removeListener(this)

        if (btnBackground.isChecked) {
            Log.i(TAG, "handleButtonGroupSelection: Background")

            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedBackgroundColor) })
            handleHexColorDisplay(selectedBackgroundColor)
        }else{
            Log.i(TAG, "handleButtonGroupSelection: text")
            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedForegroundColor) })
            handleHexColorDisplay(selectedForegroundColor)
        }

        pickerGroup.addListener(this)


    }

    private fun handleHexColorDisplay(selectedColor:Int) {

        activityMainBinding.activityMainTxtHexColor.text = String.format("#%06X", (0xFFFFFF and selectedColor))

    }

    fun updateGroupWithInputColor(color:Int){

        if (btnBackground.isChecked) {
            selectedBackgroundColor = color
            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedBackgroundColor) })
        }else {
            selectedForegroundColor = color
            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedForegroundColor) })
        }

    }

    override fun onColorChanged(
        picker: ColorSeekBar<IntegerHSLColor>,
        color: IntegerHSLColor,
        value: Int
    ) {

        if (btnBackground.isChecked) {
            selectedBackgroundColor = color.toColorInt()
            handleHexColorDisplay(selectedBackgroundColor)
        }else{
            selectedForegroundColor = color.toColorInt()
            handleHexColorDisplay(selectedForegroundColor)
        }

        listener.onColorChanged(color.toColorInt(),selectedBackgroundColor,selectedForegroundColor)

    }

    override fun onColorPicked(
        picker: ColorSeekBar<IntegerHSLColor>,
        color: IntegerHSLColor,
        value: Int,
        fromUser: Boolean
    ) {

    }

    override fun onColorPicking(
        picker: ColorSeekBar<IntegerHSLColor>,
        color: IntegerHSLColor,
        value: Int,
        fromUser: Boolean
    ) {

    }

    interface OnPickerChangeListener {

        fun onColorChanged(currentColorInt: Int, backgroundColor: Int, foregroundColor:Int)

    }

}