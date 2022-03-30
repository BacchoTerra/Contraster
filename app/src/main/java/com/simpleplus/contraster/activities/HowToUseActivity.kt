package com.simpleplus.contraster.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.simpleplus.contraster.R
import com.simpleplus.contraster.databinding.ActivityHowToUseBinding

class HowToUseActivity : AppCompatActivity() {

    //Layout components
    private val binder by lazy {
        ActivityHowToUseBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()
        setContentView(binder.root)
        displayArticle()
    }

    private fun initToolbar() {
        setSupportActionBar(binder.activityHowToUseToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null
    }

    private fun displayArticle() {
        binder.activityHowToUseTxtArticle.setHtml(R.raw.article_how_to_use)
    }
}