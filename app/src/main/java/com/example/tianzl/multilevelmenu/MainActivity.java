package com.example.tianzl.multilevelmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expanList;
    private ExpandableListViewAdapter adapter;
    private static List<String> venueList1 = new ArrayList<>();
    private static List<String> venueList2 = new ArrayList<>();
    private static List<String> venueList3 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expanList= (ExpandableListView) findViewById(R.id.expan);
        adapter=new ExpandableListViewAdapter(this, getVenueMap());
        expanList.setAdapter(adapter);
        adapter.setOnItemClickListener(new ExpandableListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemParentClick(int parent) {
                //父item点击事件
                Toast.makeText(MainActivity.this, "父View的位置"+parent, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemChildClick(int parent, int child) {
                //子item点击事件
                Toast.makeText(MainActivity.this, "父View的位置"+parent+"子View的位置"+child, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public Map<String, List<String>> getVenueMap() {
        venueList1.clear();
        venueList2.clear();
        venueList3.clear();
        venueList1.add("1.1 什么是视听唱练课程");
        venueList1.add("1.2 什么是视听唱练课程");
        venueList1.add("1.3 什么是视听唱练课程");
        venueList2.add("2.1 什么是视听唱练课程");
        venueList2.add("2.2 什么是视听唱练课程");
        venueList2.add("2.3 什么是视听唱练课程");
        venueList3.add("3.1 什么是视听唱练课程");
        venueList3.add("3.2 什么是视听唱练课程");
        venueList3.add("3.3 什么是视听唱练课程");
        Map<String, List<String>> map = new HashMap<>();
        map.put("第一章 安详视听初级教程", venueList1);
        map.put("第二章 安详视听初级教程", venueList2);
        map.put("第三章 安详视听初级教程", venueList3);
        return map;
    }
}
