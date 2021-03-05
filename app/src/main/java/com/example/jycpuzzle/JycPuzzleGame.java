package com.example.jycpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class JycPuzzleGame extends AppCompatActivity {

    private JycGameView gameview;
    private  Main2Activity main2Activity;
    private TextView Jyc_tv_level, tv_time;

    private TextView Jyc_timerView;
    private long baseTimer;
    private JycApp app;
    private int time1;
    private ImageView Jyc_iv_help;
    private Map<Integer, Integer> puzzlePicture;
    private int puzzlePoint;
    ImageView imgsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        initView();

        time();

        String userName = app.getName();

        setTitle("当前玩家："+userName);

        Jyc_tv_level = (TextView) findViewById(R.id.tv_level);
        tv_time = (TextView) findViewById(R.id.tv_time);

        gameview = (JycGameView) findViewById(R.id.gameview);

        imgsl=findViewById(R.id.imgsl);
        Bitmap mBitmap = BitmapFactory.decodeFile(MainActivity.imgUrl);
        imgsl.setImageBitmap(mBitmap);

        //显示时间
        gameview.setTimeEnabled(true);

        gameview.setOnGamemListener(new JycGameView.GamePintuListener() {
            @Override
            public void nextLevel(final int nextLevel) {

                new AlertDialog.Builder(JycPuzzleGame.this).setTitle("拼图完成").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameview.nextLevel();
                        Jyc_tv_level.setText("关卡" + nextLevel);
                        app = (JycApp) getApplication();
//
//                        可以自动下一关功能Intent intent = new Intent(JycPuzzleGame.this, JycPuzzleGame.class);
//
//                        JycApp app = (JycApp) getApplication();
//                        int currentPuzzlePiece = app.getPuzzlePiece();
//                        app.setPuzzlePiece(currentPuzzlePiece+1);
//                        startActivity(intent);

                    }
                }).show();
            }

            @Override
            public void timechanged(int time) {
                //设置时间
                tv_time.setText("倒计时：" + time);
            }

            @Override
            public void gameOver() {
                new AlertDialog.Builder(JycPuzzleGame.this).setTitle("游戏结束").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameview.restartGame();
                    }
                }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
            }
        });
        Button btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(JycPuzzleGame.this,Main2Activity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void initData() {
        app = (JycApp) getApplication();
        puzzlePicture = app.getSentencePoint();
        puzzlePoint = app.getPuzzlePoint();
    }

    private void initView() {
        setContentView(R.layout.activity_jyc_puzzle_game);

    }

    private void time() {
        JycPuzzleGame.this.baseTimer = SystemClock.elapsedRealtime();
        Jyc_timerView = (TextView) this.findViewById(R.id.timerView);
        final Handler startTimehandler = new Handler(){
            public void handleMessage(Message msg) {
                if (null != Jyc_timerView) {
                    Jyc_timerView.setText((String) msg.obj);
                }
            }
        };
        new Timer("总时长计时器").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                time1 = app.getTime();

                int time = (int)((SystemClock.elapsedRealtime() - JycPuzzleGame.this.baseTimer) / 1000);
                System.out.println(""+time);

                app.setTime(time);

                String hh = new DecimalFormat("00").format(time / 3600);
                String mm = new DecimalFormat("00").format(time % 3600 / 60);
                String ss = new DecimalFormat("00").format(time % 60);
                String timeFormat = new String(hh + ":" + mm + ":" + ss);
                Message msg = new Message();
                msg.obj = timeFormat;
                startTimehandler.sendMessage(msg);
            }

        }, 0, 1000L);
    }




//    @Override
//    protected void onPause() {
//        super.onPause();
//        gameview.pauseGame();
//        app.setTime(0);
//        app.setScore("0");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        gameview.resumeGame();
//        app.setTime(0);
//        app.setScore("0");
//
//    }
}
