package com.rcudev.myqrscan.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyQRScanSplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(IO) {
            Thread.sleep(250)
            withContext(Main) {
                startActivity(Intent(applicationContext, MyQRScanMainActivity::class.java))
                this@MyQRScanSplashActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                this@MyQRScanSplashActivity.finish()
            }
        }
    }

}