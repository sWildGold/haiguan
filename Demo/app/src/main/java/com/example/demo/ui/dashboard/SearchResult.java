package com.example.demo.ui.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;
import com.example.demo.database.Mail;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class SearchResult extends AppCompatActivity {
    private static final String[] COUNTRIES = new String[]{
            "日用品", "食品", "文件", "数码产品", "衣物", "其他"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = findViewById(R.id.toolbar_search_result);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        Mail result = (Mail) intent.getSerializableExtra("mail");
        MaterialEditText editText;
        MaterialBetterSpinner spinner;
        CheckBox checkBox;

        //之后改为数据库操作
        editText = findViewById(R.id.search_results_edittext1);
        editText.setText(result.getMail_id());
        editText = findViewById(R.id.search_results_sender_info_edittext1);
        editText.setText(result.getSender_name());
        editText = findViewById(R.id.search_results_sender_info_edittext2);
        editText.setText(result.getSender_tel());
        editText = findViewById(R.id.search_results_sender_address_edittext1);
        editText.setText(result.getSending_country());
        editText = findViewById(R.id.search_results_sender_address_edittext2);
        editText.setText(result.getSending_district());
        editText = findViewById(R.id.search_results_sender_address_edittext4);
        editText.setText(result.getSending_district_in_detail());
        editText = findViewById(R.id.search_results_receiver_info_edittext1);
        editText.setText(result.getReceiver_name());
        editText = findViewById(R.id.search_results_receiver_info_edittext2);
        editText.setText(result.getReceiver_tel());
        editText = findViewById(R.id.search_results_receiver_address_edittext1);
        editText.setText(result.getReceiving_province());
        editText = findViewById(R.id.search_results_receiver_address_edittext2);
        editText.setText(result.getReceiving_city());
        editText = findViewById(R.id.search_results_receiver_address_edittext3);
        editText.setText(result.getReceiving_county());
        editText = findViewById(R.id.search_results_receiver_address_edittext4);
        editText.setText(result.getReceiving_district_in_detail());


        spinner = findViewById(R.id.search_results_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        spinner.setAdapter(adapter);
        if (result.getPackage_class() != null)
            spinner.getEditableText().append(result.getPackage_class());


        editText = findViewById(R.id.search_results_edittext4_2);
        Double d = result.getPackage_weight();
        if (result.getPackage_weight() != 0.)
            editText.setText(result.getPackage_weight().toString());
        checkBox = findViewById(R.id.search_results_checkBox5_1);
        if (result.getCheck_conclusion() != null) {
            if (result.getCheck_conclusion().equals("合格"))
                checkBox.setChecked(true);
            checkBox = findViewById(R.id.search_results_checkBox5_2);
            if (result.getCheck_conclusion().equals("截留"))
                checkBox.setChecked(true);
        }

        checkBox = findViewById(R.id.search_results_checkBox6_1);
        checkBox.setChecked(result.isIs_destroy());
        checkBox = findViewById(R.id.search_results_checkBox6_2);
        checkBox.setChecked(result.isIs_letgo());
        checkBox = findViewById(R.id.search_results_checkBox6_3);
        checkBox.setChecked(result.isIs_letcheck());
        checkBox = findViewById(R.id.search_results_checkBox6_4);
        checkBox.setChecked(result.isIs_letback());

        editText = findViewById(R.id.search_results_edittext7_1);
        editText.setText(result.getIn_storage_time());
        editText = findViewById(R.id.search_results_edittext7_2);
        editText.setText(result.getIn_storage_site());
        editText = findViewById(R.id.search_results_edittext8_1);
        editText.setText(result.getOut_storage_time());
        editText = findViewById(R.id.search_results_edittext8_2);
        editText.setText(result.getOut_storage_site());

        editText = findViewById(R.id.search_results_edittext10);
        editText.setText(result.getOperator());
        editText = findViewById(R.id.search_results_edittext_operating_time);
        editText.setText(result.getOperating_time());

        if (result.getPic_path() != null) {
            ImageView imageView = findViewById(R.id.search_results_image_show);
            Bitmap bitmap = BitmapFactory.decodeFile(result.getPic_path());
            imageView.setImageBitmap(bitmap);
        }

    }

}
