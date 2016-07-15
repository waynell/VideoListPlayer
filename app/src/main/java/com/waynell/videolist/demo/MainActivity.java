package com.waynell.videolist.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.waynell.videolist.demo.activity.ListViewActivity;
import com.waynell.videolist.demo.activity.RecyclerViewActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.start_recycler_view)
    void startRecyclerView() {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    @OnClick(R.id.start_list_view)
    void startListView() {
        startActivity(new Intent(this, ListViewActivity.class));
    }

}
