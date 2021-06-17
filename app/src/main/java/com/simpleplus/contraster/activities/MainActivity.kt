package com.simpleplus.contraster.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.simpleplus.contraster.application.ContrasterApplication
import com.simpleplus.contraster.R

import com.simpleplus.contraster.databinding.ActivityMainBinding
import com.simpleplus.contraster.databinding.DialogSavePaletteBinding
import com.simpleplus.contraster.fragments.SetValueBottomSheet
import com.simpleplus.contraster.model.MyPalette
import com.simpleplus.contraster.util.PickersUtil
import com.simpleplus.contraster.viewmodel.MyPaletteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), PickersUtil.OnPickerChangeListener,
    SetValueBottomSheet.OnColorValueSetListener, PopupMenu.OnMenuItemClickListener {
    companion object {
        private const val TAG = "Porsche"
        const val BTM_SHEET_BUNDLE_KEY = "com.simpleplus.contraster.BTMSHEET_KEY"
    }

    //layout components
    private val binder by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //ViewModel
    private val viewModel: MyPaletteViewModel by viewModels {
        MyPaletteViewModel.MyPaletteViewModelFactory((application as ContrasterApplication).repository)
    }

    //Utils
    private lateinit var pickersUtil: PickersUtil
    private var ratioLabel = "AA"

    //String components
    private lateinit var spannableString: SpannableString

    //Activity launch components
    private val palettesActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == RESULT_OK) {
                val background = it.data?.extras?.getInt(PalettesActivity.KEY_FOR_BACKGROUND)!!
                val foreground = it.data?.extras?.getInt(PalettesActivity.KEY_FOR_FOREGROUND)!!
                pickersUtil.updateGroupWithSelectedPalette(background, foreground)
                paintLayoutWithSelectedPalette(background,foreground)

            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        initAdd()
        pickersUtil = PickersUtil(binder, this, this)
        calculateContrastRatio(
            pickersUtil.selectedBackgroundColor,
            pickersUtil.selectedForegroundColor
        )
        showAndHandleBottomSheet()

        binder.activityMainImageMenu.setOnClickListener {
            showPopUpMenu()
        }

        binder.activityMainImageAddPalette.setOnClickListener {
            showPaletteSavingDialog()
        }


    }

    private fun initAdd() {

        lifecycleScope.launch(Dispatchers.Main) {
            val adRequest = AdRequest.Builder().build()
            binder.adView.loadAd(adRequest)
        }
    }

    private fun showPaletteSavingDialog() {

        val dialogBinder = DialogSavePaletteBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(this).also {
            it.setView(dialogBinder.root)
        }

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinder.dialogSavePaletteBtnSave.setOnClickListener {
            viewModel.insert(
                MyPalette(
                    dialogBinder.dialogSavePaletteEditName.text.toString(),
                    ratioLabel,
                    pickersUtil.selectedBackgroundColor,
                    pickersUtil.selectedForegroundColor
                )
            )
            alertDialog.dismiss()
            Snackbar.make(binder.root, R.string.snackBar_palette_saved, Snackbar.LENGTH_SHORT)
                .show()
        }

        dialogBinder.dialogSavePaletteTxtCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()

    }

    private fun showPopUpMenu() {

        val popupMenu = PopupMenu(this, binder.activityMainImageMenu)

        popupMenu.menuInflater.inflate(R.menu.menu_main_activity_popup, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(this)

        popupMenu.show()

    }

    private fun paintSelectedLayout(color: Int) {

        when (binder.activityMainMaterialBtnGroup.checkedButtonId) {
            binder.activityMainBtnBackground.id -> binder.root.setBackgroundColor(color)
            else -> {
                binder.activityMainTxtAppName.setTextColor(color)
                binder.activityMainTxtContrastRatio.setTextColor(color)
                binder.activityMainTxtMessage.setTextColor(color)
                binder.activityMainTxtInformation.setTextColor(color)
                ImageViewCompat.setImageTintList(
                    binder.activityMainImageMenu,
                    ColorStateList.valueOf(color)
                )
                ImageViewCompat.setImageTintList(
                    binder.activityMainImageAddPalette,
                    ColorStateList.valueOf(color)
                )
            }
        }

    }

    private fun paintLayoutWithSelectedPalette(backgroundColor: Int, foregroundColor: Int) {

        binder.root.setBackgroundColor(backgroundColor)
        binder.activityMainTxtAppName.setTextColor(foregroundColor)
        binder.activityMainTxtContrastRatio.setTextColor(foregroundColor)
        binder.activityMainTxtMessage.setTextColor(foregroundColor)
        binder.activityMainTxtInformation.setTextColor(foregroundColor)
        ImageViewCompat.setImageTintList(
            binder.activityMainImageMenu,
            ColorStateList.valueOf(foregroundColor)
        )
        ImageViewCompat.setImageTintList(
            binder.activityMainImageAddPalette,
            ColorStateList.valueOf(foregroundColor)
        )


    }

    private fun calculateContrastRatio(backgroundColor: Int, foregroundColor: Int) {

        val ratio = ColorUtils.calculateContrast(foregroundColor, backgroundColor)

        spannableString = when (ratio) {

            in 7.0..21.0 -> {
                SpannableString("Aa ${String.format("%.2f", ratio)} AAA").also {
                    ratioLabel = "AAA"
                }
            }
            in 4.5..6.9999 -> {
                SpannableString("Aa ${String.format("%.2f", ratio)} AA").also { ratioLabel = "AA" }
            }
            in 3.0..4.49999 -> {
                SpannableString("Aa ${String.format("%.2f", ratio)} AA+").also {
                    ratioLabel = "AA+"
                }
            }
            else -> {
                SpannableString("Aa ${String.format("%.2f", ratio)} FAIL").also {
                    ratioLabel = "FAIL"
                }
            }

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
        pickersUtil.updateGroupChosenColor(colorInt)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.menu_main_activity_popup_see_palettes -> {
                palettesActivityLauncher.launch(Intent(this, PalettesActivity::class.java))
            }

            R.id.menu_main_activity_popup_about -> startActivity(Intent(this,AboutActivity::class.java))

        }

        return true

    }


}