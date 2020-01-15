package com.example.firebaseauthwithmvvm.ui.splash

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.VideoView
import com.example.firebaseauthwithmvvm.R
import com.example.firebaseauthwithmvvm.ui.auth.LoginActivity

class Splash_Screen_Activity : AppCompatActivity() {

    lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        setContentView(R.layout.activity_splash__screen_)

        supportActionBar?.hide()

        videoView = findViewById(R.id.videoView)

        val video: Uri =
            Uri.parse("android.resource://" + packageName + "/" + R.raw.hazaz1_video_327)

        videoView.setVideoURI(video)

        videoView.start()

        Handler().postDelayed({
            val test = Intent(this, LoginActivity::class.java)
            startActivity(test)
            finish()
        }, 4000)
    }
}
