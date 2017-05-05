package com.apkcore.animationhelperdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.apkcore.animationhelperlibrary.helper.AnimationHelper;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageView;
    private Button bt2;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.img);
        findViewById(R.id.bt1).setOnClickListener(this);
        bt2 = (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                if (count++ % 2 == 0) {
                    AnimationHelper.hide(mImageView);
                } else {
                    AnimationHelper.show(mImageView);
                }
                break;
            case R.id.bt2:
                AnimationHelper.startActivity(this, MyActivity.class, bt2, R.color.colorPrimaryDark);
                break;
            default:
                break;
        }
    }
}
