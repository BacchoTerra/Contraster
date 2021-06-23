package com.simpleplus.contraster.model

data class MusicModel (val musicName:String,val musicAuthor:String) {


    companion object{
        fun createMusicList() :List<MusicModel>{

            val list = mutableListOf<MusicModel>()

            for (i in 0..100) {
                list.add(MusicModel("Lorem ipsun","Dolor sit amet"))
            }

            return list

        }
    }

}