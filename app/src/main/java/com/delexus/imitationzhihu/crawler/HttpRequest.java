package com.delexus.imitationzhihu.crawler;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by delexus on 2017/5/2.
 */

public class HttpRequest {

    private static final String URL = "https://www.zhihu.com/explore/recommendations";

    private ArrayList<OnHttpResponseListener> mOnHttpResponseListenerList;

    private static final Object mLock = new Object();

    private static HttpRequest sInstance;

    private HttpRequest() {
    }

    public static HttpRequest getInstance() {
        if (sInstance == null) {
            synchronized (mLock) {
                sInstance = new HttpRequest();
                return sInstance;
            }
        }
        return sInstance;
    }

    public void loadRecommendationList() {
        new RequestTask().execute();
    }

    private class RequestTask extends AsyncTask<String, Void, ArrayList<ContentItem>> {

        @Override
        protected ArrayList<ContentItem> doInBackground(String... params) {
            try {
                Document document = Jsoup.connect(URL).userAgent("Mozilla").get();
                ArrayList<ContentItem> contentItems = new ArrayList<>();
                String selector;
                for (int i = 1; i <= 20; i++) {
                    ContentItem contentItem = new ContentItem();
                    selector = "#zh-recommend-list-full > div > div:nth-child(" + i + ")";
                    Elements elements = document.select(selector);
                    String dataType = elements.attr("data-type");
                    // 评论、简述、图片
                    String comment, summary, imageSrc;
                    if (dataType.equals("Answer")) {
                        comment = elements.select("div > div.zm-item-meta.answer-actions.clearfix.js-contentActions > "
                                + "div > a.meta-item.toggle-comment.js-toggleCommentBox").text();
                        summary = elements.select("div > div.zm-item-rich-text.expandable.js-collapse-body > div").text();
                        imageSrc = elements.select("div > div.zm-item-rich-text.expandable.js-collapse-body > div > img").attr("src");
                    } else { // Post
                        comment = elements.select("div > div.zm-item-meta.clearfix.js-contentActions > " +
                                "div > a.meta-item.toggle-comment.js-toggleCommentBox").text();
                        summary = elements.select("div > div.entry-body > div > div > div.zh-summary.summary.clearfix").text();
                        imageSrc = elements.select("div > div.entry-body > div > div > div > img").attr("src");
                    }
                    // 标题
                    String title = elements.select("h2 > a").text();
                    // 赞同数
                    String vote = elements.select("div > div.zm-item-vote > a").text();
                    // 作者
                    String author = elements.select("div > div.answer-head > div.zm-item-answer-author-info > "
                            + "span > span.author-link-line > a").text();

                    contentItem.setTitle(title);
                    contentItem.setVote(vote);
                    contentItem.setAuthor(author);
                    contentItem.setImageSrc(imageSrc);
                    contentItem.setSummary(summary);
                    contentItem.setComment(comment);
                    contentItems.add(contentItem);
                }
                return contentItems;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ContentItem> s) {
            super.onPostExecute(s);
            if (mOnHttpResponseListenerList.size() > 0) {
                for (OnHttpResponseListener response : mOnHttpResponseListenerList) {
                    response.onResponse(s);
                }
            }
        }
    }

    public void addOnHttpResponseListener(OnHttpResponseListener response) {
        if (mOnHttpResponseListenerList == null) {
            mOnHttpResponseListenerList = new ArrayList<>();
        }
        if (response != null && !mOnHttpResponseListenerList.contains(response)) {
            mOnHttpResponseListenerList.add(response);
        }

    }

    public interface OnHttpResponseListener {
        void onResponse(ArrayList<ContentItem> data);
    }
}
