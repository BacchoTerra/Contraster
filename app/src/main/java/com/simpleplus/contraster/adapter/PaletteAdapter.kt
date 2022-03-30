package com.simpleplus.contraster.adapter

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.simpleplus.contraster.R
import com.simpleplus.contraster.activities.PalettesActivity
import com.simpleplus.contraster.databinding.DialogDeletePaletteBinding
import com.simpleplus.contraster.databinding.RowPalettesBinding
import com.simpleplus.contraster.model.MyPalette
import com.simpleplus.contraster.viewmodel.MyPaletteViewModel

class PaletteAdapter(
    private val activity: PalettesActivity,
    private val viewModel: MyPaletteViewModel, private val layoutInflater: LayoutInflater
) : ListAdapter<MyPalette, PaletteAdapter.MyViewHolder>(PaletteComparator()) {

    class PaletteComparator : DiffUtil.ItemCallback<MyPalette>() {

        override fun areItemsTheSame(oldItem: MyPalette, newItem: MyPalette): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyPalette, newItem: MyPalette): Boolean {
            return oldItem.name == newItem.name && oldItem.backGroundColor == newItem.backGroundColor && oldItem.foregroundColor == newItem.foregroundColor
        }

    }

    class MyViewHolder(val binder: RowPalettesBinding) : RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            RowPalettesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val myPalette = getItem(position)

        bindColors(holder, myPalette)
        bindTexts(holder, myPalette)
        holder.binder.rowPaletteImageDelete.setOnClickListener {
            showDeletionDialog(holder, myPalette)
        }

        holder.binder.rowPaletteImageEdit.setOnClickListener {
            sendSelectedPalette(myPalette)
        }

    }

    private fun showDeletionDialog(holder: MyViewHolder, myPalette: MyPalette) {

        val dialogBinder = DialogDeletePaletteBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(activity).apply {
            this.setView(dialogBinder.root)
            this.create()
        }

        val alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinder.dialogDeletePaletteBtnDelete.setOnClickListener {
            viewModel.delete(myPalette)
            alert.dismiss()
            Snackbar.make(
                holder.binder.root,
                R.string.snackBar_palette_deleted,
                Snackbar.LENGTH_SHORT
            ).show()
        }

        dialogBinder.dialogDeletePaletteTxtCancel.setOnClickListener {
            alert.dismiss()
        }

        alert.show()
    }

    private fun sendSelectedPalette(myPalette: MyPalette) {

        activity.sendPaletteToEdition(myPalette.backGroundColor,myPalette.foregroundColor)

    }

    private fun bindColors(holder: MyViewHolder, myPalette: MyPalette) {

        ImageViewCompat.setImageTintList(
            holder.binder.rowPaletteImageDelete,
            ColorStateList.valueOf(myPalette.foregroundColor)
        )
        ImageViewCompat.setImageTintList(
            holder.binder.rowPaletteImageEdit,
            ColorStateList.valueOf(myPalette.foregroundColor)
        )
        holder.binder.rowPaletteTxtForegroundColor.setTextColor(myPalette.backGroundColor)
        holder.binder.rowPaletteTxtBackgroundColor.setTextColor(myPalette.foregroundColor)
        holder.binder.rowPaletteTxtName.setTextColor(myPalette.foregroundColor)

        holder.binder.root.backgroundTintList = ColorStateList.valueOf(myPalette.backGroundColor)
        holder.binder.rowPaletteViewForegroundColor.backgroundTintList =
            ColorStateList.valueOf(myPalette.foregroundColor)


    }

    private fun bindTexts(holder: MyViewHolder, myPalette: MyPalette) {

        if (myPalette.name.isEmpty()) {
            holder.binder.rowPaletteTxtName.text =
                activity.getString(R.string.label_palette_name, "Palette", myPalette.ratioLabel)
        } else {
            holder.binder.rowPaletteTxtName.text = activity.getString(
                R.string.label_palette_name,
                myPalette.name,
                myPalette.ratioLabel
            )
        }
        holder.binder.rowPaletteTxtBackgroundColor.text = activity.getString(
            R.string.label_background_color,
            String.format("#%06X", 0xFFFFFF and myPalette.backGroundColor)
        )
        holder.binder.rowPaletteTxtForegroundColor.text = activity.getString(
            R.string.label_text_color,
            String.format("#%06X", 0xFFFFFF and myPalette.foregroundColor)
        )

    }


}