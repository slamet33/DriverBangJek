package com.iu33.driverbangjek;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context,MainActivity.class));
                finish();
            }
        }, 4000);
    }
}
