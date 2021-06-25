package com.simpleplus.contraster.activities

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.MimeTypeFilter
import com.google.android.gms.common.util.HttpUtils
import com.simpleplus.contraster.R
import com.simpleplus.contraster.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity(), View.OnClickListener {

    //LayoutComponents
    private val binder by lazy {
        ActivityAboutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)

        binder.aboutActivityImageBack.setOnClickListener (this)
        binder.aboutActivityTxtPrivacyPolicy.setOnClickListener (this)
        binder.aboutActivityTxtRateOurApp.setOnClickListener (this)
        binder.aboutActivityBtnContactUs.setOnClickListener (this)

    }

    private fun showErrorToast() {
        Toast.makeText(this,getString(R.string.toast_could_not_do_it),Toast.LENGTH_SHORT).show()
    }

    private fun openPrivacyPolicy() {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.freeprivacypolicy.com/live/4a644714-7ad8-48db-b06a-d992c3461d22"))
        try {
            startActivity(webIntent)
        }catch (e : ActivityNotFoundException) {
            e.printStackTrace()
            showErrorToast()
        }
    }

    private fun rateApp() {
        val marketIntent = Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=${this.packageName}"))

        try {
            startActivity(marketIntent)
        }catch (e : ActivityNotFoundException) {
            showErrorToast()
        }
    }

    private fun sendEmail() {

        Toast.makeText(this,R.string.label_send_email,Toast.LENGTH_LONG).show()

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("simpleplus.dev@gmail.com"))
        }

        try {
            startActivity(emailIntent)
        }catch (e: ActivityNotFoundException){
            showErrorToast()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.about_activity_imageBack -> finish()

            R.id.about_activity_txtPrivacyPolicy -> openPrivacyPolicy()

            R.id.about_activity_txtRateOurApp -> rateApp()

            R.id.about_activity_btnContactUs -> sendEmail()


        }
    }
}