package com.example.plant.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant.R;
import com.example.plant.database.DatabaseService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Knowledge2Activity extends AppCompatActivity {
    private List<HomeItem> list;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge2);
        Toolbar toolbar = findViewById(R.id.toolbar_knowledge2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerView view = findViewById(R.id.recyclerView_knowledge2);
        Intent intent = getIntent();
        setTitle("具体内容");
        initData(intent.getIntExtra("first_class", 0), intent.getIntExtra("second_class", 0));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        view.setLayoutManager(layoutManager);
        homeAdapter = new HomeAdapter(Knowledge2Activity.this, list);
        view.setAdapter(homeAdapter);
        homeAdapter.setItemListener(new HomeAdapter.onRecyclerItemClickerListener() {
            @Override
            public void onRecyclerItemClick(View view, Object data, int position) {
                HomeItem d = (HomeItem) data;
                Intent intent = new Intent(Knowledge2Activity.this, WordsActivity.class);
                intent.putExtra("title", d.getTitle());
                intent.putExtra("content", d.getContent());
                startActivity(intent);
            }
        });

    }

    private void initData(int i, int j) {
        list = new ArrayList<>();
        if (i == 0 || j == 0)
            return;
        DatabaseService dbService = DatabaseService.getDbService();
        Map<String, String> map = dbService.getAllKnowledgeData(i, j);
        // 通过ArrayList构造函数把map.entrySet()转换成list
        List<Map.Entry<String, String>> lst = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        // 通过比较器实现比较排序
        Collections.sort(lst, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> mapping1, Map.Entry<String, String> mapping2) {
                return mapping1.getKey().compareTo(mapping2.getKey());
            }
        });
        for (Map.Entry<String, String> mapping : lst) {
            String title = mapping.getKey();
            String content = mapping.getValue();
//            String short_content;
//            int max_char = 50;
//            if (content.length() > max_char) {
//                short_content = content.substring(0, max_char) + "...";
//
//            } else {
//                short_content = content;
//            }
            list.add(new HomeItem(title, content, ""));
        }
    }
}
