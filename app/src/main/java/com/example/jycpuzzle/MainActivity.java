package com.example.jycpuzzle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static int mColumn;
    private RecyclerView Jyc_RvMain;
    private SharedPreferences Jyc_sp;
    private Button btn_sel;
    public static String imgUrl =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String text = intent.getStringExtra("data");
        mColumn = Integer.parseInt(text);
        initView();

    }


    private void getUrl() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 2 && data != null) {

            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            imgUrl=images.get(0);
            Intent intent = new Intent(MainActivity.this, JycPuzzleGame.class);
            startActivity(intent);

        }

    }

    private void initData() {
        //得到共享参数
        Jyc_sp = this.getSharedPreferences("checkPoint", MODE_PRIVATE);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        Jyc_RvMain.setLayoutManager(staggeredGridLayoutManager);

        Jyc_RvMain.setAdapter(new JycStaggerAdapter(MainActivity.this, new JycStaggerAdapter.OnItemClickListener() {

            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(MainActivity.this, JycPuzzleGame.class);

                JycApp app = (JycApp) getApplication();
                app.setPuzzlePoint(pos);
                intent.putExtra("puzzlePoint", pos);
                Log.e("init", "onClick: " + pos);
                startActivity(intent);

                String checkPoint = Jyc_sp.getString("checkPoint", "0");

            }
        }));
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        setTitle("Jyc");
        btn_sel = (Button) findViewById(R.id.btn_sel);
        btn_sel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sel:
                //进入管理器选择图片
                ImageSelectorUtils.openPhoto(MainActivity.this, 2, true, 0);
                break;
        }
    }
}
