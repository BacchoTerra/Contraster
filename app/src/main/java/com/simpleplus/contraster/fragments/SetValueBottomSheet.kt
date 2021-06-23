package com.simpleplus.contraster.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.simpleplus.contraster.R
import com.simpleplus.contraster.activities.MainActivity
import com.simpleplus.contraster.databinding.BottomSheetSetValueBinding
import java.lang.Exception
import java.util.regex.Pattern

class SetValueBottomSheet (private var listener : OnColorValueSetListener ): BottomSheetDialogFragment() {

    companion object {
        private const val TAG = "Porsche"
    }

    //layout components
    private val binder by lazy {
        BottomSheetSetValueBinding.inflate(layoutInflater)
    }

    //Identifier of what is being changed
    private var targetText = ""

    //Pattern for hex code
    private val hexPattern = Pattern.compile("^#\\w{6}")

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as OnColorValueSetListener
        }catch (e : Exception) {
            e.printStackTrace()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        retrieveArguments()
        checkInputHex()
        return binder.root
    }

    private fun retrieveArguments() {

        targetText = arguments?.getString(MainActivity.BTM_SHEET_BUNDLE_KEY) !!
        binder.bottomSheetSetValueInputLayout.helperText = targetText

    }

    private fun checkInputHex() {

        binder.bottomSheetSetValueFabConfirm.setOnClickListener {

            val hexValues = binder.bottomSheetSetValueEditValue.text.toString()

            if (hexPattern.matcher(hexValues).matches()) {

                try {
                    sendColorBack(hexValues)
                }catch (e : NumberFormatException) {
                    e.printStackTrace()
                    Toast.makeText(context,R.string.error_invalid_color,Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(context,R.string.error_hex_value,Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun sendColorBack(hexValues: String) {

        val colorInt = Color.parseColor(hexValues)
        listener.onColorValueSetFromBottomSheet(colorInt)
        dismiss()
    }

    interface OnColorValueSetListener {
        fun onColorValueSetFromBottomSheet (colorInt: Int)
    }

}