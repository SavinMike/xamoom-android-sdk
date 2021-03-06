/*
* Copyright (c) 2017 xamoom GmbH <apps@xamoom.com>
*
* Licensed under the MIT License (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at the root of this project.
*/

package com.xamoom.android.xamoomsdk.xamoomcontentblocks.Adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xamoom.android.xamoomcontentblocks.Adapters.ContentBlock9Adapter;
import com.xamoom.android.xamoomcontentblocks.ViewHolders.ContentBlock9ViewHolder;
import com.xamoom.android.xamoomcontentblocks.XamoomContentFragment;
import com.xamoom.android.xamoomsdk.BuildConfig;
import com.xamoom.android.xamoomsdk.Helper.ContentFragmentActivity;
import com.xamoom.android.xamoomsdk.R;
import com.xamoom.android.xamoomsdk.Resource.ContentBlock;
import com.xamoom.android.xamoomsdk.Resource.Style;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "AndroidManifest.xml")
public class ContentBlock9AdapterTest {

  Activity activity;

  private void addFragmentToActivity(Fragment fragment) {
    ContentFragmentActivity activity = Robolectric.buildActivity( ContentFragmentActivity.class )
        .create()
        .start()
        .resume()
        .get();

    FragmentManager fragmentManager = activity.getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(fragment, null);
    fragmentTransaction.commit();
  }

  @Before
  public void setup() {
    activity = Robolectric.buildActivity(Activity.class).create().get();
  }


  @Test
  public void testConstructor() {
    assertNotNull(new ContentBlock9Adapter());
  }

  @Test
  public void testIsForViewType() {
    List<ContentBlock> contentBlocks = new ArrayList();
    ContentBlock contentBlock = new ContentBlock();
    contentBlock.setBlockType(9);
    contentBlocks.add(contentBlock);

    ContentBlock9Adapter ContentBlock9Adapter = new ContentBlock9Adapter();

    assertTrue(ContentBlock9Adapter.isForViewType(contentBlocks, 0));
  }

  @Test
  public void testOnCreateViewHolder() {
    List<ContentBlock> contentBlocks = new ArrayList();
    ContentBlock contentBlock = new ContentBlock();
    contentBlock.setBlockType(9);
    contentBlocks.add(contentBlock);

    ContentBlock9Adapter adapter = new ContentBlock9Adapter();
    ViewGroup recycleView = (ViewGroup) View.inflate(activity, R.layout.content_block_9_layout, null);

    XamoomContentFragment fragment = XamoomContentFragment.newInstance("");
    addFragmentToActivity(fragment);

    RecyclerView.ViewHolder vh = adapter.onCreateViewHolder(recycleView, fragment, null, null, null, null, false, null, null);

    assertNotNull(vh);
    assertEquals(vh.getClass(), ContentBlock9ViewHolder.class);
  }

  @Test
  public void testOnBindViewHolder() {
    List<ContentBlock> contentBlocks = new ArrayList();
    ContentBlock contentBlock = new ContentBlock();
    contentBlock.setBlockType(9);
    contentBlocks.add(contentBlock);
    Style style = new Style();
    style.setForegroundFontColor("#000000");
    ContentBlock9ViewHolder mockViewholder = Mockito.mock(ContentBlock9ViewHolder.class);
    ContentBlock9Adapter adapter = new ContentBlock9Adapter();

    adapter.onBindViewHolder(contentBlocks, 0, mockViewholder, style, false);

    Mockito.verify(mockViewholder).setupContentBlock(eq(contentBlock), eq(false));
    Mockito.verify(mockViewholder).setStyle(eq(style));
  }
}
