package com.example.demo.ui.home;

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
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.example.demo.R;
import com.example.demo.database.DatabaseService;
import com.example.demo.passport.AuthService;
import com.example.demo.passport.Base64Util;
import com.example.demo.passport.FileUtil;
import com.example.demo.passport.HttpUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wildma.pictureselector.PictureSelector;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class PassportRecords extends AppCompatActivity {
    //private PrintManager mgr = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mgr = (PrintManager) getSystemService(PRINT_SERVICE);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport_records);
        Toolbar toolbar = findViewById(R.id.toolbar_passport_records);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button scan_passport = findViewById(R.id.button_scan_passport);
        scan_passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector
                        .create(PassportRecords.this, PictureSelector.SELECT_REQUEST_CODE)
                        .selectPicture(false, 200, 200, 1, 1);
            }
        });
        Button save_idcard = findViewById(R.id.button_passport_records_save);
        save_idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = findViewById(R.id.linearLayout_passport);
                String s = bitmapToString(loadBitmapFromView(linearLayout));
                MaterialEditText editText = findViewById(R.id.passport_records_edittext1);
                String id = editText.getText().toString();
                DatabaseService dbService = DatabaseService.getDbService();
                int result = dbService.insertPassportData(id, s);
                if (result != -1) {
                    Toast toast1 = Toast.makeText(PassportRecords.this, "已保存", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                } else {
                    Toast toast1 = Toast.makeText(PassportRecords.this, "请重试", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.CENTER, 0, 0);
                    toast1.show();
                }
            }
        });
        Button btnPrint = findViewById(R.id.button_passport_records_print);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                AndPermission.with(PassportRecords.this)
                        .runtime()
                        .permission(
                                Permission.WRITE_EXTERNAL_STORAGE,
                                Permission.READ_EXTERNAL_STORAGE

                        )
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Toast toast = Toast.makeText(PassportRecords.this, "授权成功", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                MaterialEditText editText = findViewById(R.id.passport_records_edittext1);
                                String path = Environment.getExternalStorageDirectory() + File.separator + editText.getText().toString() +".pdf";
                                Toast toast11 = Toast.makeText(PassportRecords.this, path, Toast.LENGTH_SHORT);
                                toast11.setGravity(Gravity.CENTER, 0, 0);
                                toast11.show();
                                PdfDocument document = new PdfDocument();
                                // ll_model是一个LinearLayout
                                LinearLayout ll_model = findViewById(R.id.linearLayout_passport);
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
                                    Toast toast1 = Toast.makeText(PassportRecords.this, "已保存" +
                                            "" +
                                            "", Toast.LENGTH_SHORT);
                                    toast1.setGravity(Gravity.CENTER, 0, 0);
                                    toast1.show();
                                    //print("Test PDF",new PdfDocumentAdapter(getApplicationContext()),new PrintAttributes.Builder().build());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast toast1 = Toast.makeText(PassportRecords.this, "请重试", Toast.LENGTH_SHORT);
                                    toast1.setGravity(Gravity.CENTER, 0, 0);
                                    toast1.show();
                                }
                                document.close();
                                String authority = "com.example.demo.provider";
                                File outputFile = new File(path);
                                Intent share = new Intent();
                                share.setAction(Intent.ACTION_VIEW);
                                share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri uri = FileProvider.getUriForFile(PassportRecords.this, authority, outputFile);
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
                                Toast toast = Toast.makeText(PassportRecords.this, "授权失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        })
                        .start();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String filePath = data.getStringExtra(PictureSelector.PICTURE_PATH);

                //String filePath = Environment.getExternalStorageDirectory() + File.separator + "passport.jpg";
                String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/passport";
                String result = "失败";
                try {
                    File file = imageFactory(filePath);
                    byte[] imgData = FileUtil.readFileByBytes(file);
                    String imgStr = Base64Util.encode(imgData);
                    String imgParam = URLEncoder.encode(imgStr, "UTF-8");
                    String param = "&image=" + imgParam;

                    // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
                    String accessToken = AuthService.getAuth();

                    result = HttpUtil.post(url, accessToken, param);
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(PassportRecords.this, result, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                //国家码
                int indexOfCountryCode = result.indexOf("国家码");
                if (indexOfCountryCode != -1) {
                    int indexOfWords = result.indexOf("words", indexOfCountryCode);
                    int indexOfFirstColon = result.indexOf("\"", indexOfWords + 6);
                    int indexOfSecondColon = result.indexOf("\"", indexOfFirstColon + 1);
                    MaterialEditText edittext = findViewById(R.id.passport_records_edittext7);
                    edittext.setText(result.substring(indexOfFirstColon + 1, indexOfSecondColon));
                }

                //护照号码
                int indexOfPassportCode = result.indexOf("护照号码");
                if (indexOfPassportCode != -1) {
                    int indexOfWords = result.indexOf("words", indexOfPassportCode);
                    int indexOfFirstColon = result.indexOf("\"", indexOfWords + 6);
                    int indexOfSecondColon = result.indexOf("\"", indexOfFirstColon + 1);
                    MaterialEditText edittext = findViewById(R.id.passport_records_edittext1);
                    edittext.setText(result.substring(indexOfFirstColon + 1, indexOfSecondColon));
                }

                //有效期至
                int indexOfValidDate = result.indexOf("有效期至");
                if (indexOfValidDate != -1) {
                    int indexOfWords = result.indexOf("words", indexOfValidDate);
                    int indexOfFirstColon = result.indexOf("\"", indexOfWords + 6);
                    int indexOfSecondColon = result.indexOf("\"", indexOfFirstColon + 1);
                    String date = result.substring(indexOfFirstColon + 1, indexOfSecondColon);

                    MaterialEditText editTextDay = findViewById(R.id.passport_records_edittext6_3);
                    editTextDay.setText(date.substring(0, 2));

                    int indexOfyue = date.indexOf("月");
                    if (indexOfyue != -1) {
                        MaterialEditText editTextMonth = findViewById(R.id.passport_records_edittext6_2);
                        editTextMonth.setText(date.substring(2, indexOfyue));

                        MaterialEditText editTextYear = findViewById(R.id.passport_records_edittext6_1);
                        editTextYear.setText(date.substring(date.length() - 4));
                    }

                }

                //姓名
                int indexOfName = result.indexOf("姓名");
                if (indexOfName != -1) {
                    int indexOfWords = result.indexOf("words", indexOfName);
                    int indexOfFirstColon = result.indexOf("\"", indexOfWords + 6);
                    int indexOfSecondColon = result.indexOf("\"", indexOfFirstColon + 1);
                    MaterialEditText editTextFamilyName = findViewById(R.id.passport_records_edittext2_1);
                    editTextFamilyName.setText(result.substring(indexOfFirstColon, indexOfFirstColon + 1));
                    MaterialEditText editTextFirstName = findViewById(R.id.passport_records_edittext2_2);
                    editTextFirstName.setText(result.substring(indexOfFirstColon + 1, indexOfSecondColon));
                }

                //姓名拼音
                //暂时空着

            }
        }
    }

    private File imageFactory(String picPath) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(picPath, o);
        bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
        File root = getExternalCacheDir();
        File pic = new File(root, "test.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(pic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return pic;
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

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //private PrintJob print(String name, PrintDocumentAdapter adapter,PrintAttributes attrs) {
    //    startService(new Intent(this, PrintJobMonitorService.class));
    //    return (mgr.print(name, adapter, attrs));
    //}

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
}
