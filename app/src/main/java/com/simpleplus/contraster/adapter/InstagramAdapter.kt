package com.simpleplus.contraster.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.simpleplus.contraster.databinding.RowInstagramPost1Binding
import com.simpleplus.contraster.databinding.RowInstagramPost2Binding
import com.simpleplus.contraster.databinding.RowInstagramPost3Binding
import com.simpleplus.contraster.databinding.RowInstagramPostBinding

class InstagramAdapter(private val layoutInflater: LayoutInflater, val bk: Int, val fg: Int) :
    RecyclerView.Adapter<InstagramAdapter.MyViewHolder>() {

    //Row binders
    private val post1Binder by lazy {
        RowInstagramPost1Binding.inflate(layoutInflater)
    }

    private val post2Binder by lazy {
        RowInstagramPost2Binding.inflate(layoutInflater)
    }

    private val post3Binder by lazy {
        RowInstagramPost3Binding.inflate(layoutInflater)
    }


    class MyViewHolder(val binder: RowInstagramPostBinding) : RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(RowInstagramPostBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        when (position) {

            0 -> bindPost1(holder)
            1 -> bindPost2(holder)
            2 -> bindPost3(holder)


        }
    }

    private fun bindPost1(holder: MyViewHolder) {


        holder.binder.rowInstagramPostContainerLayout.addView(post1Binder.root)

        post1Binder.apply {
            root.setBackgroundColor(bk)
            rowInstagramPost1TxtMain.setTextColor(fg)
            rowInstagramPost1TxtMock1.setTextColor(fg)
            rowInstagramPost1TxtMock2.setTextColor(fg)



            rowInstagramPost1Image1.backgroundTintList = ColorStateList.valueOf(fg)
            rowInstagramPost1Image2.backgroundTintList = ColorStateList.valueOf(fg)
        }



    }

    private fun bindPost2(holder: MyViewHolder) {

        holder.binder.rowInstagramPostContainerLayout.addView(post2Binder.root)

        post2Binder.apply {
            root.setBackgroundColor(bk)

            rowInstagramPost2TxtMock1.setTextColor(fg)
            rowInstagramPost2TxtMock2.setTextColor(fg)
            rowInstagramPost2TxtMain.setTextColor(fg)

        }

    }

    private fun bindPost3(holder: MyViewHolder) {

        holder.binder.rowInstagramPostContainerLayout.addView(post3Binder.root)

        post3Binder.apply {

            root.setBackgroundColor(bk)
            rowInstagramPost3TxtMain.setTextColor(fg)

            rowInstagramPost3TxtMock1.setTextColor(fg)
            rowInstagramPost3TxtMock2.setTextColor(fg)
            rowInstagramPost3TxtMock3.setTextColor(fg)

            rowInstagramPost3ImageBrand.backgroundTintList = ColorStateList.valueOf(fg)

        }

    }

    override fun getItemCount(): Int {
        return 3
    }


}