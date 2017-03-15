package com.delexus.imitationzhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ly-yangyuzhi on 2017/3/3.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioImageButton mBtnFeed, mBtnDiscover, mBtnNotify,
                        mBtnMessage, mBtnMore;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadData();
    }

    private void init() {
        mBtnFeed = (RadioImageButton) findViewById(R.id.btn_feed);
        mBtnDiscover = (RadioImageButton) findViewById(R.id.btn_discover);
        mBtnNotify = (RadioImageButton) findViewById(R.id.btn_notification);
        mBtnMessage = (RadioImageButton) findViewById(R.id.btn_message);
        mBtnMore = (RadioImageButton) findViewById(R.id.btn_more);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void loadData() {
        SimpleAdapter adapter = new SimpleAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_feed:


                break;
            case R.id.btn_discover:

                break;
            case R.id.btn_notification:

                break;
            case R.id.btn_message:

                break;
            case R.id.btn_more:

                break;
        }
    }

    private static class SimpleAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.mTextView.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 60;
        }
    }

    private static class MyHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public MyHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
