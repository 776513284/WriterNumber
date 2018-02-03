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
 * Created by HuangSuhai on 2018/2/1.
 */

public class TwoActivity extends Activity {  //TwoActivity类头部

    public mCustomProgressDialog mdialog;  //定义对话框对象
    MediaPlayer mediaPlayer;

    private ImageView iv_frame;  //定义书写数字的ImageView控件
    int i = 1;  //图片展示到第几张标记
    float x1;  //屏幕按下时的X值
    float y1;
    float x2;  //屏幕离开时的X值
    float y2;
    float x3;  //移动中的坐标的X值
    float y3;
    int igvx;
    int igvy;
    int type = 0;
    int widthPixels;
    int heightPixels;
    float scaleWidth;
    float scaleHeight;
    Timer touchTimer = null;  //用于连续动作的计时器
    Bitmap arrdown;  //Bitmap图像处理
    boolean typedialog = true;  //dialog对话框状态
    private LinearLayout linearLayout = null;  //LinearLayout线性布局


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {  //onCreate()方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        if (MainActivity.isPlay == true) {
            PlayMusic();
        }
        initView();
    }

    public void OnYS(View v) {
        if (mdialog == null) {
            mdialog = new mCustomProgressDialog(this, "演示中点击边缘取消演示动画",
                    R.drawable.frame2);
            mdialog.show();
        }
    }

    private void PlayMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.music2);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    private void initView() {  //initView()方法头部
        iv_frame = findViewById(R.id.iv_frame);
        linearLayout = findViewById(R.id.LinearLayout1);
        LinearLayout write_layout = findViewById(R.id.LinearLayout_number);
        write_layout.setBackgroundResource(R.drawable.bg2);
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        scaleWidth = widthPixels / 720;
        scaleHeight = heightPixels / 1280;
        try {
            InputStream is = getResources().getAssets().open("on2_1.png");
            arrdown = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取布局的宽高信息
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_frame.
                getLayoutParams();
        layoutParams.width = (int) (arrdown.getWidth() * scaleWidth);
        layoutParams.height = (int) (arrdown.getHeight() * scaleHeight);
        iv_frame.setLayoutParams(layoutParams);
        lodimagep(1);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //获取手指按下时的坐标
                        x1 = motionEvent.getX();
                        y1 = motionEvent.getY();
                        igvx = iv_frame.getLeft();
                        igvy = iv_frame.getTop();
                        if (x1>=igvx && x1<=igvx+arrdown.getWidth()*scaleWidth
                                && y1 >=igvy && y1<=igvy+arrdown.getHeight()*scaleHeight) {
                            type = 1;
                        } else {
                            type = 0;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();
                        if (type == 1) {
                            if (x2>=igvx && x2<=igvx+arrdown.getWidth()*scaleWidth) {
                                if (y2>=igvy && y2<=igvy+arrdown.getHeight()*scaleHeight/24) {
                                    lodimagep(1);
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
                                } else {
                                    type = 0;
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        type = 0;
                        if (touchTimer != null) {
                            touchTimer.cancel();
                            touchTimer = null;
                        }
                        touchTimer = new Timer();
                        touchTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message message = new Message();
                                        message.what = 2;
                                        mHandler.sendMessage(message);
                                    }
                                });
                                thread.start();
                            }
                        }, 300, 200);
                }
                return true;
            }
        });
    }  //initView()方法尾部

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    jlodimage();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void jlodimage() {
        if (i == 25) {
        } else if (i < 25) {
            if (i > 1) {
                i--;
            } else if (i == 1) {
                i = 1;
                if (touchTimer != null) {
                    touchTimer.cancel();
                    touchTimer = null;
                }
            }
            String name = "on2_" + i;
            int imgid = getResources().getIdentifier(name, "drawable",
                    "com.huangsuhai.writernumber");
            iv_frame.setBackgroundResource(imgid);
        }
    }

    private synchronized void lodimagep(int j) {
        i = j;
        if (i < 25) {
            String name = "on2_" + i;
            int imgid = getResources().getIdentifier(name, "drawable",
                    "com.huangsuhai.writernumber");
            iv_frame.setBackgroundResource(imgid);
            i++;
        }
        if (j == 24) {
            if (typedialog) {
                dialog();
            }
        }
    }

    private void dialog() {
        typedialog = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("太棒了！书写完成！");
        builder.setTitle("提示");
        builder.setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                typedialog = true;
                finish();
            }
        });
        builder.setNegativeButton("再来一次", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                typedialog = true;
                i = 1;
                lodimagep(1);
            }
        });
        builder.create().show();
    }
}  //TwoActivity类尾部
