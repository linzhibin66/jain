package com.example.administrator.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends Activity implements BaseQuickAdapter.RequestLoadMoreListener{
 ArrayList<String > list = new ArrayList();
    Context context=RecyclerActivity.this;
    RecyclerView recyclerview;
    MyAdapter myAdapter;
    SwipeRefreshLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initView();
    }

    private void initView() {
        for (int i=0;i<10;i++){
            list.add((i+1)+"");
        }
        recyclerview =  findViewById(R.id.recyclerview);
        layout = findViewById(R.id.layout);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        myAdapter = new MyAdapter(R.layout.recyclerview_list_item,list);
        recyclerview.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(context,""+(position+1),Toast.LENGTH_SHORT).show();
            }
        });
        myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(context,"按钮："+(position+1),Toast.LENGTH_SHORT).show();
            }
        });

        //下拉刷新
        myAdapter.setUpFetchEnable(true);
        myAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {

                upfetch();
            }
        });

    }

    //加载更多
    @Override
    public void onLoadMoreRequested() {
        Log.i("TAG","加载更多");
      recyclerview.postDelayed(new Runnable() {
    @Override
    public void run() {
        loadMore();
    }
      },1000);
    }

    public void upfetch(){
        Log.i("TAG","下拉刷新");
        myAdapter.setEnableLoadMore(false);
        recyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    list.add((list.size()+i)+"");
                }
                myAdapter.addData(list);
                myAdapter.setEnableLoadMore(true);
                layout.setRefreshing(false);
            }
        }, 1000);
    }

    public void loadMore(){
        Log.i("TAG","加载更多");
        recyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    list.add((list.size()+i)+"");
                }
                myAdapter.addData(list);
                myAdapter.loadMoreComplete();
            }
        }, 1000);
    }
}
