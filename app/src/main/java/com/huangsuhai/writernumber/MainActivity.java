package com.huangsuhai.writernumber;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {  //MainActivity类头部

    static boolean isPlay = true;  //设置音乐播放状态变量
    MediaPlayer mediaPlayer;  //定义音乐播放对象
    Button music_btn;  //定义控制音乐播放按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //onCreate()方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取布局文件中控制背景音乐按钮
        music_btn = findViewById(R.id.btn_music);
        PlayMusic();  //调用播放音乐的方法
    }  //onCreate()方法尾部

    private void PlayMusic() {  //播放背景音乐方法
        //创建音乐播放器对象并加载播放音乐文件
        mediaPlayer = MediaPlayer.create(this, R.raw.main_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void OnPlay(View v) {  //单击事件 进入选择数字界面
        startActivity(new Intent(MainActivity.this, SelectActivity.class));
    }

    public void OnAbout(View v) {  //单击事件 进入关于数字界面
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }

    //单击事件：音乐播放时单击按钮停止音乐播放，音乐停止时单击按钮播放音乐
    public void OnMusic(View v) {
        if (isPlay == true) {           //如果音乐处于播放状态
            if (mediaPlayer != null) {
                mediaPlayer.stop();     //停止音乐播放
                //设置静音时背景音乐按钮的图标
                music_btn.setBackgroundResource(R.drawable.btn_music2);
                isPlay = false;        //设置音乐处于停止状态
            }
        } else {                   //如果音乐处于停止状态
            PlayMusic();
            //设置音乐播放时背景音乐按钮的图标
            music_btn.setBackgroundResource(R.drawable.btn_music1);
            isPlay = true;
        }
    }

    //游戏主界面停止时，背景音乐停止
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {    //音乐播放器不为空时
            mediaPlayer.stop();        //停止音乐播放
        }
    }

    //游戏主界面清空所占内存资源时，背景音乐停止并清空音乐资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {    //音乐播放器不为空时
            mediaPlayer.stop();
            mediaPlayer.release();    //清空音乐资源
            mediaPlayer = null;       //设置音乐播放器为空
        }
    }

    //该方法实现从其他界面返回游戏主界面时，根据音乐播放状态播放音乐
    @Override
    protected void onRestart() {
        super.onRestart();
        if (MainActivity.isPlay == true) {
            PlayMusic();
        }
    }
}  //MainActivity类尾部
