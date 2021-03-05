package com.example.jycpuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.example.jycpuzzle.MainActivity.mColumn;

//自定义控件继承相对布局，实现点击监听接口
public class JycGameView extends RelativeLayout implements View.OnClickListener {

    Context mContext;

    SharedPreferences sp;

    //容器的内边距
    private int mPadding;
    //小图的距离
    private int mMagin = 3;
    //存储图片的，宽高 都是固定的，所以使用数组
    private ImageView[] mGameOintuItems;
    //宽度
    private int mItemWidth;
    //图片
    private Bitmap mBitmap;
    //用来切图后存储
    private List<JycImagePiece> mItemBitmaps;
    //标记
    private boolean once;
    //记录时间
    private int mTime;
    private int mWidth;
    //判断游戏是否成功
    private boolean isGameSuccess;
    //是否显示时间
    private boolean isTimeEnabled = false;
    private RelativeLayout mAnimLayout;
    private boolean isGameOver;
    private boolean isAniming;

    private static final int TIME_CHANGED = 10;
    private static final int NEXT_LEVEL = 11;
    private int checkPoint;
    private Map<Integer, Integer> pointPicture;
    private JycApp app;

    public void setOnGamemListener(GamePintuListener mListener) {

        this.mListener = mListener;
    }

    public GamePintuListener mListener;

    //每次都要从1开始
    private int level = 1;


    public void setTimeEnabled(boolean timeEnabled) {
        isTimeEnabled = timeEnabled;
    }

    public interface GamePintuListener {
        void nextLevel(int nextLevel);
        void timechanged(int time);
        void gameOver();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_CHANGED:
                    if (isGameSuccess || isGameOver ) {
                        return;
                    }

                    if (mListener != null) {
                        mListener.timechanged(mTime);
                        if (mTime == 0) {
                            isGameOver = true;
                            mListener.gameOver();
                            return;
                        }
                    }
                    mTime--;
                    handler.sendEmptyMessageDelayed(TIME_CHANGED, 1000);

                    break;
                case NEXT_LEVEL:

                    level = level + 1;

                    if (mListener != null) {

                        mListener.nextLevel(level);
                    } else {
                        nextLevel();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public JycGameView(Context context) {

        this(context, null);

        this.mContext = context;

    }

    public JycGameView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);


    }

    public JycGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mMagin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

        mPadding = min(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());
    }

    //确定当前布局的大小，设置成正方形
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //拿到高宽最小值
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());

        if (!once) {
            //进行切图和排序
            initBitmap();

            //设置imageview(item)的宽高等属性
            initItem();

            //根据关卡设置时间
            checkTimeEnable();

            once = true;

        }
        setMeasuredDimension(mWidth, mWidth);
    }

    private void checkTimeEnable() {
        if (isTimeEnabled) {
            countTimeBaseLevel();
            handler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    //根据当前等级设置时间
    private Activity a;

    private void countTimeBaseLevel() {
        mTime = (int) Math.pow(2, level) * 60;
    }

    //判断并且切图

    private void initBitmap() {
        //判断是否存在这张图片
        if (mBitmap == null) {
            mBitmap = BitmapFactory.decodeFile(MainActivity.imgUrl);
        }
        //进行裁剪
        mItemBitmaps = JycSplitterUtil.splitImage(mBitmap,mColumn);

        //裁剪玩后进行顺序打乱
        Collections.sort(mItemBitmaps, new Comparator<JycImagePiece>() {
            @Override
            public int compare(JycImagePiece lhs, JycImagePiece rhs) {

                //生成随机数，如果》0.5返回1否则返回-1
                return Math.random() > 0.5 ? 1 : -1;
            }
        });

    }


    private void initItem() {
        //（ 容器的宽度 - 内边距 * 2  - 间距  ） /  裁剪的数量
        mItemWidth = (mWidth - mPadding * 2 - mMagin * (mColumn - 1)) / mColumn;
        //几 * 几的图
        mGameOintuItems = new ImageView[mColumn * mColumn];

        //开始摆放图块
        for (int i = 0; i < mGameOintuItems.length; i++) {
            ImageView item = new ImageView(getContext());
            item.setOnClickListener(this);
            //设置图片
            item.setImageBitmap(mItemBitmaps.get(i).getBitmap());
            //保存
            mGameOintuItems[i] = item;
            //设置ID
            item.setId(i + 1);
            //设置Tag
            item.setTag(i + "_" + mItemBitmaps.get(i).getIndex());

            LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);

            //判断是否为最后一列
            if (i + 1 % mColumn != 0) {
                lp.rightMargin = mMagin;
            }

            //不是第一列
            if (i % mColumn != 0) {
                lp.addRule(RelativeLayout.RIGHT_OF, mGameOintuItems[i - 1].getId());
            }

            //如果不是第一行
            if ((i + 1) > mColumn) {
                lp.topMargin = mMagin;
                lp.addRule(RelativeLayout.BELOW, mGameOintuItems[i - mColumn].getId());
            }
            addView(item, lp);
        }
    }

    private int min(int... params) {
        int min = params[0];
        //遍历
        for (int param : params) {
            if (param < min) {
                min = param;
            }
        }
        return min;
    }

    //交换图片部分开始
    private ImageView mFirst;
    private ImageView mSecond;
    @Override
    public void onClick(View v) {
        //点击同一张则无效
        if (isAniming) {
            return;
        }

        //重复点击就去掉颜色
        if (mFirst == v) {
            mFirst.setColorFilter(null);
            mFirst = null;
            return;
        }

        if (mFirst == null) {
            mFirst = (ImageView) v;
            //设置选中效果
            mFirst.setColorFilter(Color.parseColor("#55FF0000"));
            //第二次点击
        } else {
            mSecond = (ImageView) v;
            //交换
            exchangeView();
        }
    }

    //判定函数
    private void checkSuccess() {
        boolean isSuccess = true;

        for (int i = 0; i < mGameOintuItems.length; i++) {

            ImageView imageView = mGameOintuItems[i];

            if (getImageIndex((String) imageView.getTag()) != i) {
                isSuccess = false;
            }
        }
        if (isSuccess) {

            isGameSuccess = true;

            handler.removeMessages(TIME_CHANGED);

            Log.i("tag", "成功");
            Toast.makeText(getContext(), "成功,进入下一关！", Toast.LENGTH_LONG).show();
            handler.sendEmptyMessage(NEXT_LEVEL);

        }
    }

    public int getImageIdByTag(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[0]);
    }

    //获取图片的tag
    public int getImageIndex(String tag) {
        String[] split = tag.split("_");
        return Integer.parseInt(split[1]);
    }



    //进入下一关功能
    public void nextLevel() {
        this.removeAllViews();
        mAnimLayout = null;
        mColumn++;
        isGameSuccess = false;
        checkTimeEnable();
        initBitmap();
        initItem();
    }

    //重新开始功能
    public void restartGame() {
        isGameOver = false;
        mColumn--;
        nextLevel();

    }

    public void getmColumn(int x){
        mColumn=x;
    }

    //交换时的动画
    private void setUpAnimLayout() {
        if (mAnimLayout == null) {
            mAnimLayout = new RelativeLayout(getContext());
            //添加到整体
            addView(mAnimLayout);
        }
    }
    private void exchangeView() {
        mFirst.setColorFilter(null);
        // 构造我们的动画层
        setUpAnimLayout();

        ImageView first = new ImageView(getContext());
        final Bitmap firstBitmap = mItemBitmaps.get(
                getImageIdByTag((String) mFirst.getTag())).getBitmap();
        first.setImageBitmap(firstBitmap);
        LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);
        lp.leftMargin = mFirst.getLeft() - mPadding;
        lp.topMargin = mFirst.getTop() - mPadding;
        first.setLayoutParams(lp);
        mAnimLayout.addView(first);

        ImageView second = new ImageView(getContext());
        final Bitmap secondBitmap = mItemBitmaps.get(
                getImageIdByTag((String) mSecond.getTag())).getBitmap();
        second.setImageBitmap(secondBitmap);
        LayoutParams lp2 = new LayoutParams(mItemWidth, mItemWidth);
        lp2.leftMargin = mSecond.getLeft() - mPadding;
        lp2.topMargin = mSecond.getTop() - mPadding;
        second.setLayoutParams(lp2);
        mAnimLayout.addView(second);

        // 动画效果
        TranslateAnimation anim = new TranslateAnimation(0, mSecond.getLeft()
                - mFirst.getLeft(), 0, mSecond.getTop() - mFirst.getTop());
        anim.setDuration(300);
        anim.setFillAfter(true);
        first.startAnimation(anim);

        TranslateAnimation animSecond = new TranslateAnimation(0,
                -mSecond.getLeft() + mFirst.getLeft(), 0, -mSecond.getTop()
                + mFirst.getTop());
        animSecond.setDuration(300);
        animSecond.setFillAfter(true);
        second.startAnimation(animSecond);

        // 动画监听
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFirst.setVisibility(View.INVISIBLE);
                mSecond.setVisibility(View.INVISIBLE);
                isAniming = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                String firstTag = (String) mFirst.getTag();
                String secondTag = (String) mSecond.getTag();

                mFirst.setImageBitmap(secondBitmap);
                mSecond.setImageBitmap(firstBitmap);

                mFirst.setTag(secondTag);
                mSecond.setTag(firstTag);

                mFirst.setVisibility(View.VISIBLE);
                mSecond.setVisibility(View.VISIBLE);

                mFirst = mSecond = null;
                mAnimLayout.removeAllViews();

                //每次都判断下是否成功
                checkSuccess();

                isAniming = false;
            }
        });
    }

}
