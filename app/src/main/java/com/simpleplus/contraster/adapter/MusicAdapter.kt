package com.simpleplus.contraster.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.simpleplus.contraster.databinding.RowMusicsBinding
import com.simpleplus.contraster.model.MusicModel

class MusicAdapter(private val bk: Int,private val fg: Int) : RecyclerView.Adapter<MusicAdapter.MyRecyclerView>() {

    private val listOfItems = MusicModel.createMusicList()

    class MyRecyclerView(val binder: RowMusicsBinding) : RecyclerView.ViewHolder(binder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerView {
        return MyRecyclerView(RowMusicsBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: MyRecyclerView, position: Int) {

        val music = listOfItems[position]
        bindText(holder,music)
        handleAllColors(holder)


    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    private fun bindText(holder: MyRecyclerView,musicModel: MusicModel) {

        holder.binder.rowMusicsTxtMusicName.text = musicModel.musicName
        holder.binder.rowMusicsTxtMusicArtist.text = musicModel.musicAuthor

    }

    private fun handleAllColors(holder: MyRecyclerView) {

        holder.binder.rowMusicsTxtMusicName.setTextColor(fg)
        holder.binder.rowMusicsTxtMusicArtist.setTextColor(fg)

        ImageViewCompat.setImageTintList(holder.binder.rowMusicsImageMore, ColorStateList.valueOf(fg))
        ImageViewCompat.setImageTintList(holder.binder.rowMusicsImageMusicNote, ColorStateList.valueOf(bk))

        holder.binder.rowMusicsImageMusicNote.backgroundTintList = ColorStateList.valueOf(fg)
        holder.binder.rowMusicViewDivider.setBackgroundColor(fg)

    }


}
