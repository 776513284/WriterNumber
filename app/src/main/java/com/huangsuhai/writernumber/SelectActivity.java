package com.huangsuhai.writernumber;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends Activity {  //SelectActivity类头部
    MediaPlayer mediaPlayer;  //定义音乐播放器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //onCreate()方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        if (MainActivity.isPlay == true) {       //如果游戏主界面设置背景音乐为播放音乐状态
            PlayMusic();    //调用播放音乐的方法
        }
    }  //onCreate()方法尾部

    private void PlayMusic() {  //播放背景音乐方法
        //创建音乐播放器并加载播放音乐文件
        mediaPlayer = MediaPlayer.create(this, R.raw.number_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    //该方法实现选择数字界面停止时，背景音乐停止
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    //选择数字界面清空所占内存资源时，背景音乐停止并清空音乐资源所占的内存
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //从其他界面返回选择数字界面时，根据音乐播放状态播放音乐
    @Override
    protected void onRestart() {
        super.onRestart();
        if (MainActivity.isPlay == true) {
            PlayMusic();
        }
    }

    public void OnOne(View v) {   //单击事件  进入数字1书写界面
        //当前界面跳转至数字1书写界面
        startActivity(new Intent(SelectActivity.this, OneActivity.class));
    }

    public void OnSeven(View v) {  //单击事件 进入数字7书写界面
        startActivity(new Intent(SelectActivity.this, SevenActivity.class));
    }

    public void OnTwo(View v) {  //单击事件 进入数字2书写界面
        startActivity(new Intent(SelectActivity.this, TwoActivity.class));
    }

    public void OnThree(View v) {
        startActivity(new Intent(SelectActivity.this, ThreeActivity.class));
    }


}  //SelectActivity类尾部
