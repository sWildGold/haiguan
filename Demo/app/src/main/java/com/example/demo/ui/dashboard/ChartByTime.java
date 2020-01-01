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
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ChartByTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_by_time);
        Toolbar toolbar = findViewById(R.id.toolbar_time);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BarChart chart = findViewById(R.id.chart_by_time);
        List<BarEntry> entryList = new ArrayList<BarEntry>();
        final String[] quarters = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
        DatabaseService dbService = DatabaseService.getDbService();
        int[] d = dbService.getAllMailDataByTime();
        for (int i = 0; i < 12; ++i) {
            entryList.add(new BarEntry(i, d[i]));
        }
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return quarters[(int) value];
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

        String[][] DATA_TO_SHOW = {{"1月", String.valueOf(d[0]), "7月", String.valueOf(d[6])}, {"2月", String.valueOf(d[1]), "8月", String.valueOf(d[7])}, {"3月", String.valueOf(d[2]), "9月", String.valueOf(d[8])},
                {"4月", String.valueOf(d[3]), "10月", String.valueOf(d[9])}, {"5月", String.valueOf(d[4]), "11月", String.valueOf(d[10])}, {"6月", String.valueOf(d[5]), "12月", String.valueOf(d[11])}};
        String[] HEADER_TO_SHOW = {"月份", "数量", "月份", "数量"};
        TableView<String[]> tableView = findViewById(R.id.tableView_time);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, HEADER_TO_SHOW));
        tableView.setHeaderBackgroundColor(getResources().getColor(R.color.header_color));
        tableView.setBackgroundColor(getResources().getColor(R.color.data_color));
    }

}
