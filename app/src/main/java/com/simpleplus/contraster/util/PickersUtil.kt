package com.simpleplus.contraster.util

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.converter.toPureHueColorInt
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import com.google.android.material.button.MaterialButton
import com.simpleplus.contraster.R
import com.simpleplus.contraster.databinding.ActivityMainBinding
import com.simpleplus.contraster.fragments.SetValueBottomSheet

class PickersUtil(
   private  val activityMainBinding: ActivityMainBinding,
   private val listener: OnPickerChangeListener,
   context: Context
) : ColorSeekBar.OnColorPickListener<ColorSeekBar<IntegerHSLColor>,IntegerHSLColor>{

    companion object {
        private const val TAG = "Porsche"
    }

    //Pickers and picker group
    private var huePicker: HSLColorPickerSeekBar = activityMainBinding.activityMainHuePicker
    private var brightnessPicker: HSLColorPickerSeekBar = activityMainBinding.activityMainBrightnessPicker
    private var saturationPicker: HSLColorPickerSeekBar = activityMainBinding.activityMainSaturationPicker
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

      btnGroup.addOnButtonCheckedListener { _, _, _ ->

            setGroupColorWithoutFuckingEverything()
      }

    }

    private fun setGroupColorWithoutFuckingEverything() {
        pickerGroup.removeListener(this)

        if (btnBackground.isChecked) {

            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedBackgroundColor) })
            handleHexColorDisplay(selectedBackgroundColor)
        }else{
            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedForegroundColor) })
            handleHexColorDisplay(selectedForegroundColor)
        }

        pickerGroup.addListener(this)


    }

    private fun handleHexColorDisplay(selectedColor:Int) {
        activityMainBinding.activityMainTxtHexColor.text = String.format("#%06X", (0xFFFFFF and selectedColor))

    }

    fun updateGroupChosenColor(color:Int){

        if (btnBackground.isChecked) {
            selectedBackgroundColor = color
            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedBackgroundColor) })

        }else {
            selectedForegroundColor = color
            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedForegroundColor) })
        }
    }

    fun updateGroupWithSelectedPalette(backgroundColor: Int,foregroundColor: Int) {
        selectedBackgroundColor = backgroundColor
        selectedForegroundColor = foregroundColor

        if (btnBackground.isChecked) {
            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedBackgroundColor) })
        }else {
            pickerGroup.setColor(IntegerHSLColor().also { it.setFromColorInt(selectedForegroundColor) })
        }

    }

    fun switchColors() {

        val interceptor = selectedBackgroundColor
        selectedBackgroundColor = selectedForegroundColor
        selectedForegroundColor = interceptor

        if (btnBackground.isChecked) {
            handleHexColorDisplay(selectedBackgroundColor)
        }else {
            handleHexColorDisplay(selectedForegroundColor)
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