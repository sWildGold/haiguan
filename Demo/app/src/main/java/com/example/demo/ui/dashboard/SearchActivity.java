package com.example.demo.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;
import com.example.demo.database.DatabaseService;
import com.example.demo.database.Mail;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SearchActivity extends AppCompatActivity {
    String intent_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar_search);
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
        intent_info = intent.getStringExtra("class");
        final MaterialEditText editTextSearch = findViewById(R.id.search_edittext);
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editTextSearch.getText().toString();
                if (intent_info.equals("mail")) {
                    DatabaseService dbService = DatabaseService.getDbService();
                    Mail result = dbService.getMailData(number);
                    if (result.getMail_id() != null) {
                        Intent intent = new Intent(SearchActivity.this, SearchResult.class);
                        intent.putExtra("mail", result);
                        startActivity(intent);
                    } else {
                        Toast toast1 = Toast.makeText(SearchActivity.this, "未找到此编号", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();
                    }
                } else if (intent_info.equals("idcard")) {
                    DatabaseService dbService = DatabaseService.getDbService();
                    String s = dbService.getIdcardData(number);
                    if (s.equals("")) {
                        Toast toast1 = Toast.makeText(SearchActivity.this, "未找到此编号", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();
                    } else {
                        Intent intent = new Intent(SearchActivity.this, SearchResultByIdcardAndPassport.class);
                        intent.putExtra("class", "idcard");
                        intent.putExtra("idcard", s);
                        startActivity(intent);
                    }
                } else if (intent_info.equals("passport")) {
                    DatabaseService dbService = DatabaseService.getDbService();
                    String s = dbService.getPassportData(number);
                    if (s.equals("")) {
                        Toast toast1 = Toast.makeText(SearchActivity.this, "未找到此编号", Toast.LENGTH_SHORT);
                        toast1.setGravity(Gravity.CENTER, 0, 0);
                        toast1.show();
                    } else {
                        Intent intent = new Intent(SearchActivity.this, SearchResultByIdcardAndPassport.class);
                        intent.putExtra("class", "passport");
                        intent.putExtra("passport", s);
                        startActivity(intent);
                    }
                }

            }
        });
    }
}
