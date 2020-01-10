package com.example.demo.ui.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.example.demo.R;
import com.example.demo.database.Mail;
import com.example.demo.ui.home.PassportRecords;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SearchResult extends AppCompatActivity {
    private static final String[] COUNTRIES = new String[]{
            "日用品", "食品", "文件", "数码产品", "衣物", "其他"
    };
    private Mail result;
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
        result = (Mail) intent.getSerializableExtra("mail");
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

        Button btnPrint = findViewById(R.id.search_results_button_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                AndPermission.with(SearchResult.this)
                        .runtime()
                        .permission(
                                Permission.WRITE_EXTERNAL_STORAGE,
                                Permission.READ_EXTERNAL_STORAGE

                        )
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Toast toast = Toast.makeText(SearchResult.this, "授权成功", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                String path = Environment.getExternalStorageDirectory() + File.separator + result.getMail_id().toString() + ".pdf";
                                Toast toast11 = Toast.makeText(SearchResult.this, path, Toast.LENGTH_SHORT);
                                toast11.setGravity(Gravity.CENTER, 0, 0);
                                toast11.show();
                                PdfDocument document = new PdfDocument();
                                // ll_model是一个LinearLayout
                                LinearLayout ll_model = findViewById(R.id.search_result_mail_layout);
                                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(ll_model.getWidth(), ll_model.getHeight(), 1).create();
                                PdfDocument.Page page = document.startPage(pageInfo);
                                ll_model.draw(page.getCanvas());
                                document.finishPage(page);

                                try {

                                    File e = new File(path);
                                    if (e.exists()) {
                                        e.delete();
                                    }
                                    document.writeTo(new FileOutputStream(e));
                                    Toast toast1 = Toast.makeText(SearchResult.this, "已保存" +
                                            "" +
                                            "", Toast.LENGTH_SHORT);
                                    toast1.setGravity(Gravity.CENTER, 0, 0);
                                    toast1.show();
                                    //print("Test PDF",new PdfDocumentAdapter(getApplicationContext()),new PrintAttributes.Builder().build());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast toast1 = Toast.makeText(SearchResult.this, "请重试", Toast.LENGTH_SHORT);
                                    toast1.setGravity(Gravity.CENTER, 0, 0);
                                    toast1.show();
                                }
                                document.close();
                                String authority = "com.example.demo.provider";
                                File outputFile = new File(path);
                                Intent share = new Intent();
                                share.setAction(Intent.ACTION_VIEW);
                                share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri uri = FileProvider.getUriForFile(SearchResult.this, authority, outputFile);
                                //Uri uri = Uri.fromFile(outputFile);
                                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                share.setDataAndType(uri, "application/pdf");
                                share.putExtra(Intent.EXTRA_STREAM, uri);
                                startActivity(share);
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Toast toast = Toast.makeText(SearchResult.this, "授权失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        })
                        .start();
            }
        });
    }
}
