package me.machao.lightframeview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.machao.lightframeview.LightFrameView;

public class BasicActivity extends AppCompatActivity {

    LightFrameView lfv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        lfv = findViewById(R.id.lfv);
//        lfv.setEffectsEnable(false);
    }


}
