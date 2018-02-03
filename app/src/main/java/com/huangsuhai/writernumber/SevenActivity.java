package com.huangsuhai.writernumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * Created by HuangSuhai on 2018/1/24.
 */

public class SevenActivity extends Activity {  //SevenActivity类头部

    private ImageView iv_frame;
    int i = 1;  //图片展示到第几张标记
    float x1;  //按下时X值
    float y1;  //按下时Y值
    float x2;  //离开时X值
    float y2;  //离开时Y值
    float x3;  //移动中X值
    float y3;  //移动中Y值
    int igvx;  //图片X值
    int igvy;  //图片Y值
    int type = 0;  //书写标识：1开启 0关闭
    int widthPixels;  //屏幕宽度
    int heightPixels;  //屏幕高度
    float scaleWidth;  //宽度缩放比例
    float scaleHeight;  //高度缩放比例
    Timer touchTimer = null;  //点击在虚拟按钮上后用于连续动作的计时器
    Bitmap arrdown;  //Bitmap图像处理
    boolean typedialog = true;  //dialog对话框状态
    private LinearLayout linearLayout = null;  //LinearLayout线性布局

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {  //onCreate()方法头部
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        initView();
    }  //onCreate()方法尾部

    private void initView() {  //创建initView()方法头部
        iv_frame = findViewById(R.id.iv_frame);
        linearLayout = findViewById(R.id.LinearLayout1);
        LinearLayout write_layout = findViewById(R.id.LinearLayout_number);
        write_layout.setBackgroundResource(R.drawable.bg7);
        widthPixels = this.getResources().getDisplayMetrics().widthPixels;
        heightPixels = this.getResources().getDisplayMetrics().heightPixels;
        scaleWidth = widthPixels / 720;
        scaleHeight = heightPixels / 1280;
        try {
            InputStream is = getResources().getAssets().open("on7_1.png");
            arrdown = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取布局的宽高信息
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_frame.getLayoutParams();
        layoutParams.width = (int) (arrdown.getWidth() * scaleWidth);
        layoutParams.height = (int) (arrdown.getHeight() * scaleHeight);
        //根据图片缩放后的宽高，设置iv_frame的宽高
        iv_frame.setLayoutParams(layoutParams);
        lodimagep(1);  //进入页面后加载第一个图片
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:  //按下事件
                        //获取手指按下的坐标
                        x1 = motionEvent.getX();
                        y1 = motionEvent.getY();
                        igvx = iv_frame.getLeft();
                        igvy = iv_frame.getTop();
                        if (x1>=igvx && x1<=igvx + arrdown.getWidth() * scaleWidth
                                && y1>=igvy && y1<=igvy + arrdown.getHeight() * scaleHeight) {
                            type = 1;  //开启书写
                        } else {
                            type = 0;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:  //移动中判断
                        igvx = iv_frame.getLeft();
                        igvy = iv_frame.getTop();
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();
                        //下面根据移动到不同位置进行判断
                        if (type == 1) {
                            if (x2 <= igvx + arrdown.getWidth() * scaleWidth && x2 >= igvx) {
                                if (y2 >= igvy && y2 <= igvy + (arrdown.getHeight() * scaleHeight)
                                        / 24) {
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
                        //当手指离开时
                        if (touchTimer != null) {
                            touchTimer.cancel();  //中断计时器
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
    }  //创建initView()方法尾部

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
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
            String name = "on7_" + i;
            int imgid = getResources().getIdentifier(name, "drawable",
                    "com.huangsuhai.writernumber");
            iv_frame.setBackgroundResource(imgid);
        }
    }

    private synchronized void lodimagep(int j) {  //lodimagep()方法头部
        i = j;  //当前图片位置
        if (i < 25) {
            String name = "on7_" + i;
            //获取图片资源id
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
    }  //lodimagep()方法尾部

    private void dialog() {
        typedialog = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("太棒了！书写完成！");
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
                lodimagep(i);
            }
        });
        builder.create().show();  //显示对话框
    }
}  //SevenActivity类尾部
