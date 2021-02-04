package com.nice.ppjoke.ui.publish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nice.libnavannotation.ActivityDestination;
import com.nice.ppjoke.R;

@ActivityDestination(pageUrl = "main/tabs/publish",needLogin = true)
public class PublishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
    }
}