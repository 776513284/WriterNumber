package com.huangsuhai.writernumber;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {  //AboutActivity类头部

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void onBack(View v) {  //实现关于界面返回上一界面
        AboutActivity.this.finish();  //关闭关于界面
    }

}  //AboutActivity类头部
