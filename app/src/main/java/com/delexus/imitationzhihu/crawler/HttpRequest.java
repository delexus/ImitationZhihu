package com.delexus.imitationzhihu.crawler;

import android.os.AsyncTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by delexus on 2017/5/2.
 */

public class HttpRequest {

    private Connection mConnection;
    private Document mDocument;
    private static final String URL = "https://www.zhihu.com/explore/recommendations";

    private ArrayList<OnHttpResponse> mOnHttpResponseList;

    public HttpRequest() {
        mConnection = Jsoup.connect(URL);
        try {
            mDocument = mConnection.userAgent("Mozilla").post();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadRecommendationList() {
        final String selector = "#zh-recommend-list-full > div > div:nth-child(1) > h2 > a";
        new RequestTask().execute();
    }

    private class RequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return mDocument.select(params[0]).text();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mOnHttpResponseList.size() > 0) {
                for (OnHttpResponse response : mOnHttpResponseList) {
                    response.onResponse(s);
                }
            }
        }
    }

    public void addOnHttpResponse(OnHttpResponse response) {
        if (mOnHttpResponseList == null) {
            mOnHttpResponseList = new ArrayList<>();
        }
        if (response != null && !mOnHttpResponseList.contains(response)) {
            mOnHttpResponseList.add(response);
        }

    }

    public interface OnHttpResponse {
        void onResponse(String data);
    }
}
