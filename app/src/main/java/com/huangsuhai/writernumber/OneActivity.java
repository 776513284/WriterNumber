package com.huangsuhai.writernumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import util.mCustomProgressDialog;

/**
 * Created by HuangSuhai on 2018/1/4.
 */

public class OneActivity extends Activity {  //继承自Activity实现全屏显示  OneActivity类头部

    public mCustomProgressDialog mdialog;  //定义自定义对话框对象
    MediaPlayer mediaPlayer;  //定义音乐播放器对象

    private ImageView iv_frame;  //定义显示写数字的ImageView控件
    int i = 1;  //图片展示到第几张标记
    float x1;   //屏幕按下时X值
    float y1;   //屏幕按下时Y值
    float x2;   //屏幕离开时X值
    float y2;   //屏幕离开时Y值
    float x3;   //移动中的坐标X值
    float y3;   //移动中的坐标Y值
    int igvx;   //图片X坐标
    int igvy;   //图片y坐标
    int type = 0;   //是否可以书写标识 开关 1 开启 0 关闭
    int widthPixels;   //屏幕宽度
    int heightPixels;   //屏幕高度
    float scaleWidth;   //宽度的缩放比例
    float scaleHeight;   //高度的缩放比例
    Timer touchTimer = null;   //点击在虚拟按钮上后用于连续动作的计时器
    Bitmap arrdown;   //Bitmap图像处理
    boolean typedialog = true;   //dialog对话框状态
    private LinearLayout linearLayout = null;   //LinearLayout线性布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {  //创建的onCreate()方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);  //设置数字1功能界面的布局文件
        //如果游戏主界面设置背景音乐为播放音乐状态
        if (MainActivity.isPlay == true) {
            PlayMusic();  //调用播放音乐的方法
        }
        initView();  //创建并调用initView()方法
    }  //创建的onCreate()方法尾部

    public void OnYS(View v) {  //创建演示按钮，单击事件头部方法
        if (mdialog == null) {  //如果自定义对话框为空
            //实例化自定义对话框， 设置显示文字和动画文件
            mdialog = new mCustomProgressDialog(this, "演示中点击边缘取消演示动画",
                    R.drawable.frame1);
        }
        mdialog.show();  //显示对话框
    }  //创建演示按钮，单击事件方法尾部

    private void initView() {  //创建initView()方法头部
        //获取显示写数字的ImageView组件
        iv_frame = findViewById(R.id.iv_frame);
        //获取写数字区域的布局
        linearLayout = findViewById(R.id.LinearLayout1);
        //获取书写界面布局
        LinearLayout write_layout = findViewById(R.id.LinearLayout_number);
        //设置书写界面布局背景
        write_layout.setBackgroundResource(R.drawable.bg1);
        //获取屏幕宽度
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        //获取屏幕高度
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        //图片资源按1280*720准备，如果是其他分辨率，适应屏幕做准备
        scaleWidth = ((float) widthPixels / 720);
        scaleHeight = ((float) heightPixels / 1280);
        try {
            //通过输入流打开第一种图片
            InputStream is = getResources().getAssets().open("on1_1.png");  //注意全名.png
            //使用Bitmap解析第一张图片
            arrdown = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取布局的宽高信息
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_frame.
                getLayoutParams();
        //获取图片缩放后宽度
        layoutParams.width = (int) (arrdown.getWidth() * scaleWidth);
        //获取图片缩放后高度
        layoutParams.height = (int) (arrdown.getHeight() * scaleHeight);
        //根据图片缩放后的宽高，设置iv_frame的宽高
        iv_frame.setLayoutParams(layoutParams);
        lodimagep(1); //调用lodimagep()方法，进入页面后加载第一个图片
        linearLayout.setOnTouchListener(new View.OnTouchListener() {  //设置手势判断事件
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {  //手势按下判断的onTouch()方法
                switch (motionEvent.getAction()) {  //获取行动方式头部
                    case MotionEvent.ACTION_DOWN:  //手指按下事件
                        //获取手指按下时坐标
                        x1 = motionEvent.getX();  //手指按下的X坐标
                        y1 = motionEvent.getY();  //手指按下的Y坐标
                        igvx = iv_frame.getLeft();  //手指按下图片的X坐标
                        igvy = iv_frame.getTop();  //手指按下图片的Y坐标
                        //判断当手指按下的坐标大于图片位置的坐标时，证明手指按住移动，此时开启书写
                        if (x1 >= igvx && x1 <= igvx + (int)(arrdown.getWidth() * scaleWidth)
                                && y1 >= igvy && y1 <= igvy + (int)(arrdown.getHeight() * scaleHeight)) {
                            type = 1;  //开启书写
                        } else {
                            type = 0;  //否则关闭书写
                        }
                        break;
                    case MotionEvent.ACTION_MOVE: //手势移动中判断
                        igvx = iv_frame.getLeft();  //获取图片的X坐标
                        igvy = iv_frame.getTop();  //获取图片的Y坐标
                        x2 = motionEvent.getX();  //获取移动中手指在屏幕X坐标的位置
                        y2 = motionEvent.getY();  //获取移动中手指在屏幕Y坐标的位置
                        //下面根据手势滑动的位置进行图片加载处理滑动到不同位置时 加载不同图片
                        if (type == 1) {  //如果书写开启
                        //如果手指按下的X坐标大于等于图片的X坐标，或者小于等于缩放图片的X坐标时
                        if (x2 >= igvx && x2 <= igvx + (int)(arrdown.getWidth() * scaleWidth)) {
                            //如果当前手指按下的Y坐标大于等于图片的Y坐标，或者小于等于缩放图片的Y坐标
                            if (y2 >= igvy && y2 <= igvy + (int)(arrdown.getHeight() * scaleHeight) / 24) {
                                lodimagep(1);  //加载第一张显示图片
                            }
                            // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 2) {
                                lodimagep(2);            // 调用lodimagep()方法，加载第二张显示图片
                            }
                            // 如果当前手指按下的Y坐标小于等于缩放图片的Y坐标
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 3) {
                                lodimagep(3);            // 调用lodimagep()方法，加载第三张显示图片
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 4) {
                                lodimagep(4);            // 调用lodimagep()方法，加载第四张显示图片
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 5) {
                                lodimagep(5);            // 调用lodimagep()方法，加载第五张显示图片
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 6) {
                                lodimagep(6);            // 调用lodimagep()方法，加载第六张显示图片
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 7) {
                                lodimagep(7);            // 调用lodimagep()方法，加载第七张显示图片
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 8) {
                                lodimagep(8);            // 调用lodimagep()方法，加载第八张显示图片
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 9) {
                                lodimagep(9);            // 调用lodimagep()方法，加载第九张显示图片
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 10) {
                                lodimagep(10);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 11) {
                                lodimagep(11);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 12) {
                                lodimagep(12);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 13) {
                                lodimagep(13);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 14) {
                                lodimagep(14);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 15) {
                                lodimagep(15);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 16) {
                                lodimagep(16);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 17) {
                                lodimagep(17);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 18) {
                                lodimagep(18);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 19) {
                                lodimagep(19);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 20) {
                                lodimagep(20);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 21) {
                                lodimagep(21);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 22) {
                                lodimagep(22);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 23) {
                                lodimagep(23);
                            }
                            else if (y2 <= igvy + (int) (arrdown.getHeight() * scaleHeight) / 24 * 24) {
                                lodimagep(24);   //加载最后一张图片时，将在lodimagep()方法中调用书写完成对话框
                            }
                            else {
                                type = 0;         // 手指离开 设置书写关闭
                            }
                        }

                    }
                    break;
                    case MotionEvent.ACTION_UP:  //手势抬起判断
                        type = 0;  //手势关闭
                        //当手指离开时
                        if (touchTimer != null) {  //判断计时器是否为空
                            touchTimer.cancel();  //中断计时器
                            touchTimer = null;  //设置计时器为空
                        }
                        touchTimer = new Timer();  //初始化计时器
                        touchTimer.schedule(new TimerTask() {  //开启时间计时器
                            @Override
                            public void run() {
                                Thread thread = new Thread(new Runnable() {  //创建子线程
                                    @Override
                                    public void run() {
                                        //创建Message用于发送信息
                                        Message message = new Message();
                                        message.what = 2;  //message消息为2
                                        //发送消息给handler实现倒退显示图片
                                        mHandler.sendMessage(message);
                                    }
                                });
                                thread.start();  //开启线程
                            }
                        },300, 200);
                }  //获取行动方式尾部
                return true;
            }
        });
    }  //创建initView()方法尾部

    //递减显示帧图片的handler消息头部
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:  //当接收到手势抬起子线程消息时
                    jlodimage();  //调用资源图片倒退显示方法
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };  //递减显示帧图片的handler消息尾部

    private void jlodimage() {  //当手势抬起时数字资源图片倒退显示jlodimage()方法头部
        if (i == 25) {  //如果当前图片位置等于25
        } else if (i < 25) {  //否则如果当前图片小于25
            if (i > 1) {  //如果当前图片大于1
                i--;
            } else if (i == 1) {  //否则如果当前图片等于1
                i = 1;
                if (touchTimer != null) {  //判断计时器是否为空
                    touchTimer.cancel();  //中断计时器
                    touchTimer = null;  //设置计时器为空
                }
            }
            String name = "on1_" + i;  //图片的名称
            //获取图片资源
            int imgid = getResources().getIdentifier(name, "drawable",
                    "com.huangsuhai.writernumber");
            //给imageview设置图片
            iv_frame.setBackgroundResource(imgid);
        }
    }  //当手势抬起时数字资源图片倒退显示jlodimage()方法尾部


    private synchronized void lodimagep(int j) {  //lodimagep()方法头部
        i = j;                                       //当前图片位置
        if (i < 25) {
            String name = "on1_" + i;  //当前图片名称
            //获取图片资源id
            int imgid = getResources().getIdentifier(name, "drawable", "com.huangsuhai.writernumber");
            iv_frame.setBackgroundResource(imgid);
            i++;
        }
        if (j == 24) {  //如果当前图片位置为24
            if (typedialog) {  //没有对话框的情况下
                dialog();  //调用书写完成对话框方法
            }
        }
    }  //lodimagep()方法尾部

    protected void dialog() {  //完成后提示对话框头部
        typedialog = false;  //修改对话框状态
        AlertDialog.Builder builder = new AlertDialog.Builder(OneActivity.this);
        builder.setMessage("太棒了！书写完成！");  //设置对话框文本信息
        builder.setTitle("提示");  //设置对话框标题
        //设置对话框完成按钮单击事件头部
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();  //dialog消失
                typedialog = true;  //修改对话框状态
                finish();  //关闭当前页面
            }
        });  //对话框完成按钮单击事件尾部
        //设置对话框再来一次按钮单击事件头部
        builder.setNegativeButton("再来一次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                typedialog = true;
                i = 1;
                lodimagep(i);  //调用加载图片方法中的第一张图片
            }
        });  //对话框再来一次按钮单击事件尾部
        builder.create().show();  //创建并显示对话框
    }  //完成后提示对话框尾部

    private void PlayMusic() {  //播放背景音乐方法
        //创建音乐播放器对象并加载播放音乐文件
        mediaPlayer = MediaPlayer.create(this, R.raw.music1);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    //该方法实现数字1书写界面停止时，背景音乐停止播放
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    //该方法实现数字1书写界面清空所占内存资源时，背景音乐停止并清空音乐资源所占的内存
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}  //OneActivity类尾部
