package com.example.demo.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.example.demo.database.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class Knowledge1Activity extends AppCompatActivity {
    private List<HomeItem> list;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge1);
        Toolbar toolbar = findViewById(R.id.toolbar_knowledge1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView view = findViewById(R.id.recyclerView_knowledge1);
        Intent intent = getIntent();
        int classNumber = intent.getIntExtra("class", 0);
        if (classNumber == 1) {
            setTitle("邮件监管");
        } else if (classNumber == 2) {
            setTitle("国家及行业标准");
        } else if (classNumber == 3) {
            setTitle("邮件处理");
        } else if (classNumber == 4) {
            setTitle("法律法规");
        }
        //setTitle("小知识"+String.valueOf(intent.getIntExtra("class",0)));
        initData(intent.getIntExtra("class", 0));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        view.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(Knowledge1Activity.this, list);
        view.setAdapter(homeAdapter);
        homeAdapter.setItemListener(new HomeAdapter.onRecyclerItemClickerListener() {
            @Override
            public void onRecyclerItemClick(View view, Object data, int position) {
                HomeItem d = (HomeItem) data;
                Intent intent = new Intent(Knowledge1Activity.this, Knowledge2Activity.class);
                String class_info = d.getContent();
                int pos = class_info.indexOf(",");
                int first_class = Integer.parseInt(class_info.substring(0, pos));
                int second_class = Integer.parseInt(class_info.substring(pos + 1));
                intent.putExtra("first_class", first_class);
                intent.putExtra("second_class", second_class + 1);
                startActivity(intent);
            }
        });

    }

    private void initData(int i) {
        list = new ArrayList<HomeItem>();
        if (i == 0)
            return;
        DatabaseService dbService = DatabaseService.getDbService();
        List<String> lst = dbService.getSecondChapterData(i);
        for (int j = 0; j < lst.size(); ++j) {
            list.add(new HomeItem(lst.get(j), i + "," + j, ""));
        }
    }
}
