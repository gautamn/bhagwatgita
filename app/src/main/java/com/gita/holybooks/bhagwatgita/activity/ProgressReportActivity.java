package com.gita.holybooks.bhagwatgita.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gita.holybooks.bhagwatgita.R;
import com.gita.holybooks.bhagwatgita.util.DataUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ProgressReportActivity extends AppCompatActivity {


    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;

    private FrameLayout chartLayout;
    private PieChart pieChart ;
    private RelativeLayout shlokaStatsLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report);

        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Reading Progress");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        chartLayout = (FrameLayout) findViewById(R.id.chart_layout);
        pieChart = new PieChart(this);
        entries = new ArrayList<>();
        PieEntryLabels = new ArrayList<String>();

        entries.add(new BarEntry(2f, 0));
        entries.add(new BarEntry(4f, 1));

        PieEntryLabels.add("READ");
        PieEntryLabels.add("LEFT");

        pieDataSet = new PieDataSet(entries, "");
        pieData = new PieData(PieEntryLabels, pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(pieData);
        pieChart.setDescription(findReadShlokas()+"/ "+DataUtil.TOTAL_NUMBER_OF_SHLOKAS+" read shlokas");
        pieChart.setDescriptionTextSize(16f);
        pieChart.setDescriptionPosition( 750, 750);
        pieChart.animateY(500);
        pieChart.setRotationEnabled(false);

        chartLayout.addView(pieChart);
        chartLayout.setMinimumHeight(800);
        chartLayout.setBackgroundColor(Color.LTGRAY);

        ViewGroup.LayoutParams lp = pieChart.getLayoutParams();
        lp.height=800;
        pieChart.setLayoutParams(lp);

        List<String> tmpList = new ArrayList<>(18);

       for(int i=0;i<18;i++){

           tmpList.add("This is a test.         "+"12/25");

       }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.layout_list_view_chapter_progress, tmpList.toArray(new String[0]));




        ListView listView = (ListView) findViewById(R.id.chapter_progress_list);
        listView.setAdapter(adapter);
    }

    private int findReadShlokas(){

        int i = 0;

        for(int chapter=1; chapter<=12; chapter++){

            List<String> readShlokasInChapter = new ArrayList<>(DataUtil.SHLOKAS_IN_CHAPTER.length);
            String key = "chapterNumber_" + chapter;
            Gson gson = new Gson();
            SharedPreferences prefs = getSharedPreferences("USER_PROFILE", MODE_PRIVATE);
            String restoredText = prefs.getString(key, null);
            if (restoredText != null) {
                readShlokasInChapter = gson.fromJson(restoredText, ArrayList.class);
                if(readShlokasInChapter!=null)
                    i = i + readShlokasInChapter.size();
            }
        }
        return i;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    public void shareApp(View view) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Read Bhagwad Gita in English for free. Download now http://abc.com");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
