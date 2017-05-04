package com.apkcore.animationhelperdemo;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Apkcore on 2017/5/4.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        if (resume != null) {
            resume.setResume();
        }
    }

    public void setResume(IonResume resume) {
        this.resume = resume;
    }

    private IonResume resume;

    public interface IonResume {
        void setResume();
    }

}
