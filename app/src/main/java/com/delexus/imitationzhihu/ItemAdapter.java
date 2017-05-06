package com.delexus.imitationzhihu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.delexus.imitationzhihu.crawler.ContentItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by delexus on 2017/5/4.
 */

public class ItemAdapter extends RecyclerView.Adapter {

    private static final String URL = "https://www.zhihu.com/explore/recommendations";
    private ArrayList<ContentItem> mContentItems;

    public ItemAdapter(ArrayList<ContentItem> contentItems) {
        mContentItems = contentItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        ContentItem contentItem = mContentItems.get(position);
        String imageSrc = contentItem.getImageSrc();
        if (imageSrc != null && !imageSrc.isEmpty()) {
            Picasso.with(itemHolder.itemView.getContext()).load(imageSrc).into(itemHolder.mCover);
            itemHolder.mCover.setVisibility(View.VISIBLE);
        } else {
            itemHolder.mCover.setVisibility(View.GONE);
        }
        itemHolder.mTitle.setText(contentItem.getTitle());
        itemHolder.mSummary.setText(contentItem.getSummary());
        itemHolder.mComment.setText(contentItem.getComment());
        itemHolder.mVote.setText(String.format("%s 赞同", contentItem.getVote()));
    }

    @Override
    public int getItemCount() {
        return mContentItems.size();
    }

    private static class ItemHolder extends RecyclerView.ViewHolder {

        public ImageView mCover;
        public TextView mTitle;
        public TextView mSummary;
        public TextView mComment;
        public TextView mVote;

        public ItemHolder(View itemView) {
            super(itemView);
            mCover = (ImageView) itemView.findViewById(R.id.cover);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mSummary = (TextView) itemView.findViewById(R.id.summary);
            mComment = (TextView) itemView.findViewById(R.id.comment);
            mVote = (TextView) itemView.findViewById(R.id.vote);
        }
    }
}
