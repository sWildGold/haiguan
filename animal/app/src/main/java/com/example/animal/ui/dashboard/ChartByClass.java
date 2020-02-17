package com.example.animal.ui.dashboard;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.animal.R;
import com.example.animal.database.DatabaseService;
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

public class ChartByClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_by_class);
        Toolbar toolbar = findViewById(R.id.toolbar_class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        BarChart chart = findViewById(R.id.chart_by_class);
        List<BarEntry> entryList = new ArrayList<BarEntry>();
        final String[] quarters = new String[]{"猪", "马", "牛", "羊", "禽类", "其他"};
        DatabaseService dbService = DatabaseService.getDbService();
        int[] d = dbService.getAllMailDataByClass();
        for (int i = 0; i < 6; ++i) {
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
        chart.getDescription().setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        //YAxis leftaxis=chart.getAxisLeft();
        //leftaxis.setAxisMaximum(100);
        //leftaxis.setAxisMinimum(0);

        chart.invalidate();

        String[][] DATA_TO_SHOW = {{"猪", String.valueOf(d[0])}, {"马", String.valueOf(d[1])},
                {"牛", String.valueOf(d[2])}, {"羊", String.valueOf(d[3])}, {"禽类", String.valueOf(d[4])}, {"其他", String.valueOf(d[5])}};
        String[] HEADER_TO_SHOW = {"类别", "数量",};
        TableView<String[]> tableView = findViewById(R.id.tableView_class);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, HEADER_TO_SHOW));
        tableView.setHeaderBackgroundColor(getResources().getColor(R.color.header_color));
        tableView.setBackgroundColor(getResources().getColor(R.color.data_color));

    }

}
