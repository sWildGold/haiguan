package com.example.animal.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.example.animal.R;
import com.example.animal.database.DatabaseService;
import com.example.animal.database.Mail;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import com.wildma.pictureselector.PictureSelector;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MailRecords extends AppCompatActivity {
    private static final String[] COUNTRIES = new String[]{"猪", "马", "牛", "羊", "禽类", "其他"};
    //private PrintManager mgr = null;
    private ImageView imageView;
    private Button btnCamera;
    private Button btnSave;
    private String picturePath;
    private Button btnPrint;
    private CheckBox hege,jieliu,fangxing,xiaohui,songyang,tuiyun;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //mgr = (PrintManager) getSystemService(PRINT_SERVICE);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_records);
        Toolbar toolbar = findViewById(R.id.toolbar_mail_records);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hege=findViewById(R.id.checkBox5_1);
        jieliu=findViewById(R.id.checkBox5_2);
        fangxing=findViewById(R.id.checkBox6_2);
        xiaohui=findViewById(R.id.checkBox6_1);
        songyang=findViewById(R.id.checkBox6_3);
        tuiyun=findViewById(R.id.checkBox6_4);
        hege.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    fangxing.setChecked(true);
                    jieliu.setEnabled(false);
                    xiaohui.setEnabled(false);
                    songyang.setEnabled(false);
                    tuiyun.setEnabled(false);
                }
                else{
                    fangxing.setChecked(false);
                    jieliu.setEnabled(true);
                    xiaohui.setEnabled(true);
                    songyang.setEnabled(true);
                    tuiyun.setEnabled(true);
                }
            }
        });
        jieliu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hege.setEnabled(false);
                    fangxing.setEnabled(false);
                }
                else{
                    hege.setEnabled(true);
                    fangxing.setEnabled(true);
                }
            }
        });
        fangxing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    jieliu.setEnabled(false);
                    xiaohui.setEnabled(false);
                    songyang.setEnabled(false);
                    tuiyun.setEnabled(false);
                    hege.setChecked(true);
                }
                else{
                    hege.setChecked(false);
                    jieliu.setEnabled(true);
                    xiaohui.setEnabled(true);
                    songyang.setEnabled(true);
                    tuiyun.setEnabled(true);

                }
            }
        });
        xiaohui.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hege.setEnabled(false);
                    fangxing.setEnabled(false);
                    songyang.setEnabled(false);
                    tuiyun.setEnabled(false);
                    jieliu.setChecked(true);
                }
                else{
                    tuiyun.setEnabled(true);
                    songyang.setEnabled(true);
                }
            }
        });
        songyang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hege.setEnabled(false);
                    fangxing.setEnabled(false);
                    xiaohui.setEnabled(false);
                    tuiyun.setEnabled(false);
                    jieliu.setChecked(true);
                }
                else{
                    xiaohui.setEnabled(true);
                    tuiyun.setEnabled(true);
                }
            }
        });
        tuiyun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hege.setEnabled(false);
                    fangxing.setEnabled(false);
                    xiaohui.setEnabled(false);
                    songyang.setEnabled(false);
                    jieliu.setChecked(true);
                }
                else{
                    xiaohui.setEnabled(true);
                    songyang.setEnabled(true);
                }
            }
        });
        btnCamera = findViewById(R.id.button_camera);
        imageView = findViewById(R.id.image_show);
        btnPrint = findViewById(R.id.button_mail_records_print);
        btnSave = findViewById(R.id.button_mail_records_save);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(MailRecords.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(false, 200, 200, 1, 1);

            }
        });
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                AndPermission.with(MailRecords.this).runtime().permission(
                        Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE
                ).onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        MaterialEditText editText;
                        editText = findViewById(R.id.mail_records_edittext7_1);
                        String in_storage_time=editText.getText().toString();
                        boolean in_storage_time_is_number=true;
                        if(in_storage_time.length()!=0){
                            if(in_storage_time.length()!=8)
                                in_storage_time_is_number=false;
                            else{
                                for(int i=0;i<in_storage_time.length();++i){
                                    int j=in_storage_time.charAt(i)-'0';
                                    if(j>=0 && j<10)
                                        continue;
                                    else{
                                        in_storage_time_is_number=false;
                                        break;
                                    }
                                }
                            }
                        }
                        if(in_storage_time_is_number==false){
                            Toast toast1 = Toast.makeText(MailRecords.this, "入库时间格式错误", Toast.LENGTH_SHORT);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.show();
                            return;
                        }

                        editText = findViewById(R.id.mail_records_edittext8_1);
                        String out_storage_time=editText.getText().toString();
                        boolean out_storage_time_is_number=true;
                        if(out_storage_time.length()!=0){
                            if(out_storage_time.length()!=8)
                                out_storage_time_is_number=false;
                            else{
                                for(int i=0;i<out_storage_time.length();++i){
                                    int j=out_storage_time.charAt(i)-'0';
                                    if(j>=0 && j<10)
                                        continue;
                                    else{
                                        out_storage_time_is_number=false;
                                        break;
                                    }
                                }
                            }
                        }

                        if(out_storage_time_is_number==false){
                            Toast toast1 = Toast.makeText(MailRecords.this, "出库时间格式错误", Toast.LENGTH_SHORT);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.show();
                            return;
                        }
                        Toast toast = Toast.makeText(MailRecords.this, "授权成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        editText = findViewById(R.id.mail_records_edittext1);
                        String path = Environment.getExternalStorageDirectory() + File.separator + editText.getText().toString() +".pdf";
                        //String path = Environment.getExternalStorageDirectory() + File.separator + "Record.pdf";
                        Toast toast11 = Toast.makeText(MailRecords.this, path, Toast.LENGTH_SHORT);
                        toast11.setGravity(Gravity.CENTER, 0, 0);
                        toast11.show();
                        PdfDocument document = new PdfDocument();
                        // ll_model是一个LinearLayout
                        LinearLayout ll_model = findViewById(R.id.mail_layout);
                        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(ll_model.getWidth(), ll_model.getHeight(), 1).create();
                        PdfDocument.Page page = document.startPage(pageInfo);
                        ll_model.draw(page.getCanvas());
                        document.finishPage(page);
                        try {
                            File e = new File(path);
                            if (e.exists()) {e.delete();}
                            document.writeTo(new FileOutputStream(e));
                            Toast toast1 = Toast.makeText(MailRecords.this, "已保存", Toast.LENGTH_SHORT);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast toast1 = Toast.makeText(MailRecords.this, "请重试", Toast.LENGTH_SHORT);
                            toast1.setGravity(Gravity.CENTER, 0, 0);
                            toast1.show();
                        }
                        document.close();
                        String authority = "com.example.animal.provider";
                        File outputFile = new File(path);
                        Intent share = new Intent();
                        share.setAction(Intent.ACTION_VIEW);
                        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = FileProvider.getUriForFile(MailRecords.this, authority, outputFile);
                        //Uri uri = Uri.fromFile(outputFile);
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        share.setDataAndType(uri, "application/pdf");
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        startActivity(share);
                    }
                }).onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Toast toast = Toast.makeText(MailRecords.this, "授权失败", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }).start();


            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        MaterialBetterSpinner textView = findViewById(R.id.mail_records_spinner);
        textView.setAdapter(adapter);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id="", pic="",klass="",time="",country="";
                DatabaseService dbserver = DatabaseService.getDbService();
                MaterialEditText editText;
                editText = findViewById(R.id.mail_records_edittext1);
                id=editText.getText().toString();
                editText = findViewById(R.id.mail_records_sender_address_edittext1);
                country=editText.getText().toString();
                MaterialBetterSpinner spinner = findViewById(R.id.mail_records_spinner);
                klass=spinner.getText().toString();
                editText = findViewById(R.id.mail_records_edittext7_1);
                String in_storage_time=editText.getText().toString();
                boolean in_storage_time_is_number=true;
                if(in_storage_time.length()!=0){
                    if(in_storage_time.length()!=8)
                        in_storage_time_is_number=false;
                    else{
                        for(int i=0;i<in_storage_time.length();++i){
                            int j=in_storage_time.charAt(i)-'0';
                            if(j>=0 && j<10)
                                continue;
                            else{
                                in_storage_time_is_number=false;
                                break;
                            }
                        }
                    }
                    if(in_storage_time_is_number==false){
                        Toast toast1 = Toast.makeText(MailRecords.this, "入库时间格式错误", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();
                        return;
                    }
                    in_storage_time=in_storage_time.substring(0,4)+"."+in_storage_time.substring(4,6)+"."+in_storage_time.substring(6,8);
                }
                time=in_storage_time;
                String out_storage_time=editText.getText().toString();
                boolean out_storage_time_is_number=true;
                if(out_storage_time.length()!=0){
                    if(out_storage_time.length()!=8)
                        out_storage_time_is_number=false;
                    else{
                        for(int i=0;i<out_storage_time.length();++i){
                            int j=out_storage_time.charAt(i)-'0';
                            if(j>=0 && j<10)
                                continue;
                            else{
                                out_storage_time_is_number=false;
                                break;
                            }
                        }
                    }
                    if(out_storage_time_is_number==false){
                        Toast toast1 = Toast.makeText(MailRecords.this, "出库时间格式错误", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();
                        return;
                    }
                    out_storage_time=out_storage_time.substring(0,4)+"."+out_storage_time.substring(4,6)+"."+out_storage_time.substring(6,8);
                }
                LinearLayout linearLayout=findViewById(R.id.mail_layout);
                pic=bitmapToString(loadBitmapFromView(linearLayout));
                if (dbserver.insertMailData(id,pic,klass,time,country) == 1) {
                    Toast toast1 = Toast.makeText(MailRecords.this, "已保存", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                } else {
                    Toast toast1 = Toast.makeText(MailRecords.this, "请重试", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                }
            }
        });
    }


    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //private PrintJob print(String name, PrintDocumentAdapter adapter, PrintAttributes attrs) {
    //    startService(new Intent(this, PrintJobMonitorService.class));
    //    return (mgr.print(name, adapter, attrs));
    //}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                imageView.setImageBitmap(bitmap);

            }
        }
    }
    public String bitmapToString(Bitmap bitmap) {
        //用户在活动中上传的图片转换成String进行存储
        String string;
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytes = stream.toByteArray();// 转为byte数组
            string = Base64.encodeToString(bytes, Base64.DEFAULT);
            return string;
        } else {
            return "";
        }
    }
    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }
}

