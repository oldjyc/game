package com.example.jycpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Main2Activity.this,MainActivity.class);
                EditText editText=findViewById(R.id.edit);
                String text=editText.getText().toString();
                //传递难度值并跳转
                intent.putExtra("data",text);
                startActivityForResult(intent,1);
            }
        });

    }
}
