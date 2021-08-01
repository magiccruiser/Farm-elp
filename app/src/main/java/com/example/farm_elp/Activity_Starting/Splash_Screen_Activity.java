package com.example.farm_elp.Activity_Starting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farm_elp.R;

public class Splash_Screen_Activity extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView splashImg;
    TextView textView;

    private static int SPLASH_SCREEN=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen_);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ////////////Animations//////////////
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        splashImg=findViewById(R.id.splash_img);
        textView=findViewById(R.id.splash_textView);

        ///////////////Assigning Animation////////////

        splashImg.setAnimation(topAnim);
        textView.setAnimation(bottomAnim);

        //////////////CALLING NEXT ACTIVITY AFTER THIS SCREEN//////////////////
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash_Screen_Activity.this,StartingActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}