package com.xamoom.android.xamoomsdkexample;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xamoom.android.xamoomcontentblocks.Adapters.AdapterDelegate;
import com.xamoom.android.xamoomcontentblocks.ViewHolders.ContentBlock0ViewHolder;
import com.xamoom.android.xamoomcontentblocks.ViewHolders.ContentBlock3ViewHolder;
import com.xamoom.android.xamoomcontentblocks.XamoomContentFragment;
import com.xamoom.android.xamoomsdk.EnduserApi;
import com.xamoom.android.xamoomsdk.R;
import com.xamoom.android.xamoomsdk.Resource.ContentBlock;
import com.xamoom.android.xamoomsdk.Resource.Style;

import java.util.List;

public class CustomContentBlock0Adapter implements AdapterDelegate<List<ContentBlock>> {
  private static final int BLOCK_TYPE = 0;

  @Override
  public boolean isForViewType(@NonNull List<ContentBlock> items, int position) {
    ContentBlock cb = items.get(position);
    return cb.getBlockType() == BLOCK_TYPE;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(
      ViewGroup parent, Fragment fragment, EnduserApi enduserApi, String youtubeApiKey,
      LruCache bitmapCache, LruCache contentCache, boolean showContentLinks,
      ContentBlock3ViewHolder.OnContentBlock3ViewHolderInteractionListener onContentBlock3ViewHolderInteractionListener,
      XamoomContentFragment.OnXamoomContentFragmentInteractionListener onXamoomContentFragmentInteractionListener) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.content_block_0_layout, parent, false);
    return new ContentBlock0ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull List<ContentBlock> items, int position, @NonNull RecyclerView.ViewHolder holder, Style style) {
    ContentBlock cb = items.get(position);
    ContentBlock0ViewHolder newHolder = (ContentBlock0ViewHolder) holder;
    newHolder.setStyle(style);
    newHolder.setTextSize(10.0f);
    newHolder.setupContentBlock(cb);
  }

}