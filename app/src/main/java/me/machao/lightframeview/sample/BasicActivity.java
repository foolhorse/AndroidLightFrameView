package me.machao.lightframeview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.machao.lightframeview.LightFrameView;

public class BasicActivity extends AppCompatActivity implements View.OnClickListener {

    LightFrameView lfvWrapView;
    LightFrameView lfvWrapViewGroup;
    LightFrameView lfvWrapScrollView;

    Button btnWrapView;
    Button btnWrapViewGroup;
    Button btnWrapScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        lfvWrapView = findViewById(R.id.lfvWrapView);
        lfvWrapViewGroup = findViewById(R.id.lfvWrapViewGroup);
        lfvWrapScrollView = findViewById(R.id.lfvWrapScrollView);

        btnWrapView = findViewById(R.id.btnWrapView);
        btnWrapViewGroup = findViewById(R.id.btnWrapViewGroup);
        btnWrapScrollView = findViewById(R.id.btnWrapScrollView);

        btnWrapView.setOnClickListener(this);
        btnWrapViewGroup.setOnClickListener(this);
        btnWrapScrollView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWrapView:
                lfvWrapView.setEffectsEnable(!lfvWrapView.isEffectsEnabled());
                break;
            case R.id.btnWrapViewGroup:
                lfvWrapViewGroup.setEffectsEnable(!lfvWrapViewGroup.isEffectsEnabled());
                break;
            case R.id.btnWrapScrollView:
                lfvWrapScrollView.setEffectsEnable(!lfvWrapScrollView.isEffectsEnabled());
                break;
        }
    }
}
