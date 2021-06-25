package com.simpleplus.contraster.activities

import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.simpleplus.contraster.R
import com.simpleplus.contraster.adapter.InstagramAdapter
import com.simpleplus.contraster.adapter.MusicAdapter
import com.simpleplus.contraster.databinding.*

class RealUseActivity : AppCompatActivity() {

    //LayoutComponents
    private val binder by lazy {
        ActivityRealUseBinding.inflate(layoutInflater)
    }

    private val articleBinder by lazy {
        ContentRealUseArticleBinding.inflate(layoutInflater)
    }

    private val recyclerLayoutBinder by lazy {
        ContentRealUseInstagramPostsBinding.inflate(layoutInflater)
    }

    private val musicBinder by lazy {
        ContentRealUseMusicPlayerBinding.inflate(layoutInflater)
    }

    private val article2Binder by lazy {
        ContentRealUseArticle2Binding.inflate(layoutInflater)
    }

    //Colors
    private var bk = 0
    private var fg = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        initColors()
        initToolbar()
        displayRightLayout()

        initAds()

    }

    private fun initAds() {
        val adRequest = AdRequest.Builder().build()
        binder.activityRealUseAdBanner.loadAd(adRequest)
    }

    private fun initToolbar() {

        setSupportActionBar(binder.activityRealUseToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = null
        }

        binder.activityRealUseToolbar.setBackgroundColor(bk)
        val toolbarIcon = binder.activityRealUseToolbar.navigationIcon

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            toolbarIcon?.colorFilter = BlendModeColorFilter(fg, BlendMode.SRC_ATOP)
        }else{
            toolbarIcon?.setColorFilter(fg, PorterDuff.Mode.SRC_ATOP)
        }

    }

    private fun initColors() {
        bk = intent.extras?.get(MainActivity.DIALOG_REAL_USE_BACKGROUND_KEY) as Int
        fg = intent.extras?.get(MainActivity.DIALOG_REAL_USE_FOREGROUND_KEY) as Int

        binder.root.setBackgroundColor(bk)

    }

    private fun displayRightLayout() {
        when (intent.extras?.getString(MainActivity.DIALOG_REAL_USE_LAYOUT_KEY)) {

            MainActivity.IDENTIFIER_ARTICLE_LAYOUT -> displayArticleLayout()
            MainActivity.IDENTIFIER_ARTICLE_2_LAYOUT -> displayArticleLayout2()
            MainActivity.IDENTIFIER_MUSIC_LAYOUT -> displayMusicLayout()
        }
    }

    private fun displayInstagramPosts() {
        binder.activityRealUseViewParentContainer.addView(recyclerLayoutBinder.root)

        recyclerLayoutBinder.contentRealUseInstagramPostsRecyclerView.apply {

            adapter = InstagramAdapter(layoutInflater, bk, fg)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RealUseActivity)

        }

    }

    private fun displayArticleLayout() {
        binder.activityRealUseViewParentContainer.addView(articleBinder.root)

        Glide.with(this).load(R.drawable.instagram_post_pic_2).into(articleBinder.contentRealUseArticleImageArticle)

        articleBinder.root.setBackgroundColor(bk)
        articleBinder.contentRealUseArticleTxtArticleTitle.setTextColor(fg)
        articleBinder.contentRealUseArticleTxtArticleAuthor.setTextColor(fg)
        articleBinder.contentRealUseArticleTxtArticleReadTime.setTextColor(fg)
        articleBinder.contentRealUseArticleTxtArticleContent.setTextColor(fg)


    }

    private fun displayMusicLayout() {
        binder.activityRealUseViewParentContainer.addView(musicBinder.root)

        musicBinder.contentRealUseMusicPlayerRecyclerView.apply {

            adapter = MusicAdapter(bk, fg)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RealUseActivity)

        }

        musicBinder.contentRealUseMusicPlayerTxtMyMusic.setTextColor(fg)

        musicBinder.contentRealUseMusicPlayerImagePlay.backgroundTintList =
            ColorStateList.valueOf(fg)
        musicBinder.contentRealUseMusicPlayerImageRandom.backgroundTintList =
            ColorStateList.valueOf(fg)

        ImageViewCompat.setImageTintList(
            musicBinder.contentRealUseMusicPlayerImagePlay,
            ColorStateList.valueOf(bk)
        )
        ImageViewCompat.setImageTintList(
            musicBinder.contentRealUseMusicPlayerImageRandom,
            ColorStateList.valueOf(bk)
        )


    }

    private fun displayArticleLayout2() {
        binder.activityRealUseViewParentContainer.addView(article2Binder.root)
        article2Binder.contentRealUse2TxtHeader.setHtml(R.raw.real_use_article_2_part_1)
        article2Binder.contentRealUse2TxtArticle.setHtml(R.raw.real_use_article_2_part_2)
        Glide.with(this).load(R.drawable.instagram_post_pic_2).into(article2Binder.contentRealUse2ImageImage)

        article2Binder.contentRealUse2TxtArticle.setTextColor(fg)
        article2Binder.contentRealUse2TxtHeader.setTextColor(fg)
        article2Binder.contentRealUse2TxtHeader.typeface = ResourcesCompat.getFont(this,R.font.playfair_display)
        article2Binder.contentRealUse2TxtArticle.typeface = ResourcesCompat.getFont(this,R.font.playfair_display)

    }

}