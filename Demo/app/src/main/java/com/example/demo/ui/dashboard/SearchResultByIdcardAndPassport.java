package com.example.demo.ui.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;

public class SearchResultByIdcardAndPassport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_by_idcard_and_passport);
        Toolbar toolbar = findViewById(R.id.toolbar_search_result_by_id_card_and_passport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageView = findViewById(R.id.imageView_search_result_by_idcard_and_passport);
        Intent intent = getIntent();
        String s_class = intent.getStringExtra("class");
        if (s_class.equals("idcard")) {
            String s = intent.getStringExtra("idcard");
            Bitmap bitmap = stringToBitmap(s);
            imageView.setImageBitmap(bitmap);
        } else {
            String s = intent.getStringExtra("passport");
            Bitmap bitmap = stringToBitmap(s);
            imageView.setImageBitmap(bitmap);
        }

    }

    public Bitmap stringToBitmap(String string) {
        //数据库中的String类型转换成Bitmap
        Bitmap bitmap;
        if (string != null) {
            byte[] bytes = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            return bitmap;
        } else {
            return null;
        }
    }
}
