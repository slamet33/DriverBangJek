package com.iu33.driverbangjek;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.iu33.driverbangjek.helper.SessionManager;

/**
 * Created by hp on 12/25/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=this;
        sessionManager = new SessionManager(context);
    }
}
