package com.example.demo.ui.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.example.demo.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SearchResultByIdcardAndPassport extends AppCompatActivity {
    private String id;
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
            id=intent.getStringExtra("idcard_id");
            Bitmap bitmap = stringToBitmap(s);
            imageView.setImageBitmap(bitmap);
        } else {
            String s = intent.getStringExtra("passport");
            id=intent.getStringExtra("passport_id");
            Bitmap bitmap = stringToBitmap(s);
            imageView.setImageBitmap(bitmap);
        }

        Button btnPrint = findViewById(R.id.search_results_by_idcard_and_passport_button_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                AndPermission.with(SearchResultByIdcardAndPassport.this)
                        .runtime()
                        .permission(
                                Permission.WRITE_EXTERNAL_STORAGE,
                                Permission.READ_EXTERNAL_STORAGE

                        )
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Toast toast = Toast.makeText(SearchResultByIdcardAndPassport.this, "授权成功", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                String path = Environment.getExternalStorageDirectory() + File.separator + id + ".pdf";
                                Toast toast11 = Toast.makeText(SearchResultByIdcardAndPassport.this, path, Toast.LENGTH_SHORT);
                                toast11.setGravity(Gravity.CENTER, 0, 0);
                                toast11.show();
                                PdfDocument document = new PdfDocument();
                                // ll_model是一个LinearLayout
                                LinearLayout ll_model = findViewById(R.id.search_result_by_idcard_and_passport_llayout);
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
                                    Toast toast1 = Toast.makeText(SearchResultByIdcardAndPassport.this, "已保存" +
                                            "" +
                                            "", Toast.LENGTH_SHORT);
                                    toast1.setGravity(Gravity.CENTER, 0, 0);
                                    toast1.show();
                                    //print("Test PDF",new PdfDocumentAdapter(getApplicationContext()),new PrintAttributes.Builder().build());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast toast1 = Toast.makeText(SearchResultByIdcardAndPassport.this, "请重试", Toast.LENGTH_SHORT);
                                    toast1.setGravity(Gravity.CENTER, 0, 0);
                                    toast1.show();
                                }
                                document.close();
                                String authority = "com.example.demo.provider";
                                File outputFile = new File(path);
                                Intent share = new Intent();
                                share.setAction(Intent.ACTION_VIEW);
                                share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri uri = FileProvider.getUriForFile(SearchResultByIdcardAndPassport.this, authority, outputFile);
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
                                Toast toast = Toast.makeText(SearchResultByIdcardAndPassport.this, "授权失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        })
                        .start();
            }
        });

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
