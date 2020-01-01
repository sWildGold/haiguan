package com.example.demo.ui.dashboard;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.demo.R;
import com.example.demo.database.DatabaseService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ChartByCountry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_by_country);
        Toolbar toolbar = findViewById(R.id.toolbar_country);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BarChart chart = findViewById(R.id.chart_by_country);
        List<BarEntry> entryList = new ArrayList<BarEntry>();
        String[] quarters = new String[12];

        int[] d = new int[12];
        DatabaseService dbService = DatabaseService.getDbService();
        Map<String, Integer> map = dbService.getAllMailDataByCountry();
// 通过ArrayList构造函数把map.entrySet()转换成list
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
// 通过比较器实现比较排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> mapping1, Map.Entry<String, Integer> mapping2) {
                return -mapping1.getValue().compareTo(mapping2.getValue());
            }
        });
        int i = 0;
        int num_qita = 0;
        int max_country = 6;
        for (Map.Entry<String, Integer> mapping : list) {
            if (i < max_country) {
                quarters[i] = mapping.getKey();
                d[i] = mapping.getValue();
                i++;
            } else {
                if (i == max_country) {
                    quarters[i] = "其他";
                    i++;
                }
                num_qita += mapping.getValue();
            }
        }
        d[max_country] = num_qita;
        final String[] Quarters = quarters;
        for (int j = 0; j < i; ++j) {
            entryList.add(new BarEntry(j, d[j]));
        }
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return Quarters[(int) value];
            }
        };
        BarDataSet dataSet = new BarDataSet(entryList, "件数");
        BarData Data = new BarData(dataSet);
        chart.setData(Data);
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        //YAxis leftaxis=chart.getAxisLeft();
        //leftaxis.setAxisMaximum(100);
        //leftaxis.setAxisMinimum(0);
        chart.getDescription().setEnabled(false);
        chart.invalidate();

//        String[][] DATA_TO_SHOW = { {quarters[0],convertZero2Null(d[0]),quarters[6],convertZero2Null(d[6])},{quarters[1],convertZero2Null(d[1]),quarters[7],convertZero2Null(d[7])},
//                {quarters[2],convertZero2Null(d[2]),quarters[8],convertZero2Null(d[8])},{quarters[3],convertZero2Null(d[3]),quarters[9],convertZero2Null(d[9])},
//                {quarters[4],convertZero2Null(d[4]),quarters[10],convertZero2Null(d[10])},{quarters[5],convertZero2Null(d[5]),quarters[11],convertZero2Null(d[11])}};
        String[][] DATA_TO_SHOW = new String[max_country + 1][];
        for (int j = 0; j < max_country + 1; ++j) {
            DATA_TO_SHOW[j] = new String[]{quarters[j], convertZero2Null(d[j])};
        }
        String[] HEADER_TO_SHOW = {"国家", "数量"};
        TableView<String[]> tableView = findViewById(R.id.tableView_country);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, HEADER_TO_SHOW));
        tableView.setHeaderBackgroundColor(getResources().getColor(R.color.header_color));
        tableView.setBackgroundColor(getResources().getColor(R.color.data_color));
    }


    String convertZero2Null(int i) {
        if (i == 0)
            return "";
        return String.valueOf(i);
    }
}
