/*
* Copyright (c) 2017 xamoom GmbH <apps@xamoom.com>
*
* Licensed under the MIT License (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at the root of this project.
*/

package com.xamoom.android.xamoomsdk.xamoomsdk.Storage.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.suitebuilder.annotation.SmallTest;

import com.xamoom.android.xamoomsdk.BuildConfig;
import com.xamoom.android.xamoomsdk.Resource.Content;
import com.xamoom.android.xamoomsdk.Resource.Marker;
import com.xamoom.android.xamoomsdk.Resource.Spot;
import com.xamoom.android.xamoomsdk.Resource.System;
import com.xamoom.android.xamoomsdk.Storage.Database.ContentDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.Database.DatabaseHelper;
import com.xamoom.android.xamoomsdk.Storage.Database.MarkerDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.Database.SpotDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.Database.SystemDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.TableContracts.OfflineEnduserContract;

import org.bouncycastle.asn1.dvcs.Data;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SpotDatabaseAdapterTest {
  private SpotDatabaseAdapter mSpotDatabaseAdapter;
  private DatabaseHelper mMockedDatabaseHelper;
  private SQLiteDatabase mMockedDatabase;
  private Cursor mMockedCursor;
  private SystemDatabaseAdapter mMockedSystemDatabaseAdapter;
  private ContentDatabaseAdapter mMockedContentDatabaseadapter;
  private MarkerDatabaseAdapter mMockedMarkerDatabaseAdapter;

  @Before
  public void setup() {
    mSpotDatabaseAdapter = SpotDatabaseAdapter.getInstance(RuntimeEnvironment.application);
    mMockedDatabaseHelper = mock(DatabaseHelper.class);
    mMockedDatabase = mock(SQLiteDatabase.class);
    mSpotDatabaseAdapter.setDatabaseHelper(mMockedDatabaseHelper);
    mMockedContentDatabaseadapter = mock(ContentDatabaseAdapter.class);
    mMockedSystemDatabaseAdapter = mock(SystemDatabaseAdapter.class);
    mMockedMarkerDatabaseAdapter = mock(MarkerDatabaseAdapter.class);
    mSpotDatabaseAdapter.setContentDatabaseAdapter(mMockedContentDatabaseadapter);
    mSpotDatabaseAdapter.setSystemDatabaseAdapter(mMockedSystemDatabaseAdapter);
    mSpotDatabaseAdapter.setMarkerDatabaseAdapter(mMockedMarkerDatabaseAdapter);
    mMockedCursor = mock(Cursor.class);

    Mockito.stub(mMockedDatabaseHelper.getWritableDatabase())
        .toReturn(mMockedDatabase);
    Mockito.stub(mMockedDatabaseHelper.getReadableDatabase())
        .toReturn(mMockedDatabase);
    Mockito.stub(mMockedDatabase.query(anyString(), any(String[].class), anyString(),
        any(String[].class), anyString(), anyString(), anyString())).toReturn(mMockedCursor);
  }

  @Test
  public void testGetSpot() {
    Mockito.stub(mMockedCursor.getCount()).toReturn(1);
    Mockito.stub(mMockedCursor.moveToNext()).toReturn(true).toReturn(false);
    Mockito.stub(mMockedCursor.getColumnIndex(OfflineEnduserContract.SpotEntry.COLUMN_NAME_CUSTOM_META)).toReturn(1);
    Mockito.stub(mMockedCursor.getString(eq(1))).toReturn("{\"key\":\"value\"}");

    HashMap<String, String> customMeta = new HashMap<>();
    customMeta.put("key", "value");

    Spot spot = mSpotDatabaseAdapter.getSpot("1");

    Mockito.verify(mMockedDatabase).query(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
        any(String[].class), anyString(), any(String[].class), anyString(),
        anyString(), anyString());

    Assert.assertNotNull(spot);
    Assert.assertTrue(spot.getCustomMeta().values().containsAll(customMeta.values()));
  }

  @Test
  public void testGetSpotLong() {
    String query = OfflineEnduserContract.SpotEntry._ID + " = ?";

    Mockito.stub(mMockedCursor.getCount()).toReturn(1);
    Mockito.stub(mMockedCursor.moveToNext()).toReturn(true).toReturn(false);

    Spot spot = mSpotDatabaseAdapter.getSpot(1L);

    Mockito.verify(mMockedDatabase).query(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
        any(String[].class), eq(query), any(String[].class), anyString(),
        anyString(), anyString());

    Assert.assertNotNull(spot);
  }

  @Test
  public void testInsertOrUpdateNewEntity() {
    Spot spot = new Spot();
    spot.setId("1");

    Content content = new Content();
    content.setId("2");

    System system = new System();
    system.setId("3");

    spot.setSystem(system);
    spot.setContent(content);

    Marker marker = new Marker();
    marker.setId("4");
    ArrayList<Marker> markers = new ArrayList<>();
    markers.add(marker);
    spot.setMarkers(markers);

    HashMap<String, String> customMeta = new HashMap<>();
    customMeta.put("key", "value");
    spot.setCustomMeta(customMeta);

    ContentValues checkValues = new ContentValues();
    checkValues.put(OfflineEnduserContract.SpotEntry.COLUMN_NAME_JSON_ID, spot.getId());
    checkValues.put(OfflineEnduserContract.SpotEntry.COLUMN_NAME_NAME, spot.getName());
    checkValues.put(OfflineEnduserContract.SpotEntry.COLUMN_NAME_DESCRIPTION, spot.getDescription());
    checkValues.put(OfflineEnduserContract.SpotEntry.COLUMN_NAME_PUBLIC_IMAGE_URL, spot.getPublicImageUrl());
    checkValues.put(OfflineEnduserContract.SpotEntry.COLUMN_NAME_CATEGORY, spot.getCategory());
    checkValues.put(OfflineEnduserContract.SpotEntry.COLUMN_NAME_RELATION_SYSTEM, 0);
    checkValues.put(OfflineEnduserContract.SpotEntry.COLUMN_NAME_RELATION_CONTENT, 0);
    checkValues.put(OfflineEnduserContract.SpotEntry.COLUMN_NAME_CUSTOM_META,
        new JSONObject(spot.getCustomMeta()).toString());

    long row = mSpotDatabaseAdapter.insertOrUpdateSpot(spot);

    Mockito.verify(mMockedDatabase).query(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
        any(String[].class), anyString(), any(String[].class), anyString(),
        anyString(), anyString());

    Mockito.verify(mMockedDatabase).insert(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
          anyString(), argThat(contentValuesMatchesValues(checkValues)));

    Mockito.verify(mMockedContentDatabaseadapter).insertOrUpdateContent(
        Mockito.eq(content), anyBoolean(), anyLong());

    Mockito.verify(mMockedSystemDatabaseAdapter).insertOrUpdateSystem(
        Mockito.eq(system));

    Mockito.verify(mMockedMarkerDatabaseAdapter).insertOrUpdateMarker(eq(marker), anyLong());
  }

  Matcher<ContentValues> contentValuesMatchesValues(final ContentValues checkValues) {
    return new TypeSafeMatcher<ContentValues>() {
      public boolean matchesSafely(ContentValues values) {
        boolean matching = true;
        for (String key : checkValues.keySet()) {
          if (values.containsKey(key)) {
            String valueString = values.getAsString(key);
            String checkValueString = checkValues.getAsString(key);
            if (valueString == null && checkValueString == null) {
              continue;
            }
            if (valueString == null || checkValueString == null ||
                !valueString.equalsIgnoreCase(checkValueString)) {
              matching = false;
              break;
            }
          } else {
            matching = false;
            break;
          }
        }
        return matching;
      }
      public void describeTo(Description description) {
        description.appendText("contentValues matching");
      }
    };
  }

  @Test
  public void testInsertOrUpdateExistingEntity() {
    Spot spot = new Spot();
    spot.setId("1");

    Mockito.stub(mMockedDatabase.query(anyString(), any(String[].class), anyString(),
        any(String[].class), anyString(), anyString(), anyString())).toReturn(mMockedCursor);
    Mockito.stub(mMockedCursor.moveToFirst()).toReturn(true);
    Mockito.stub(mMockedCursor.getInt(anyInt())).toReturn(1);

    long row = mSpotDatabaseAdapter.insertOrUpdateSpot(spot);

    Mockito.verify(mMockedDatabase).query(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
        any(String[].class), anyString(), any(String[].class), anyString(),
        anyString(), anyString());

    Mockito.verify(mMockedDatabase).update(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
        any(ContentValues.class), anyString(), any(String[].class));
    Assert.assertNotEquals(row, -1L);
  }

  @Test
  public void testGetAllSpots() {
    Mockito.stub(mMockedCursor.getCount()).toReturn(2);
    Mockito.stub(mMockedCursor.moveToNext()).toReturn(true).toReturn(true).toReturn(false);

    ArrayList<Spot> spots = mSpotDatabaseAdapter.getAllSpots();

    Mockito.verify(mMockedDatabase).query(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
        any(String[].class), anyString(), any(String[].class), anyString(),
        anyString(), anyString());

    Assert.assertNotNull(spots);
    Assert.assertEquals(spots.size(), 2);
  }

  @Test
  public void testGetSpotsWithImage() {
    String selection = OfflineEnduserContract.SpotEntry.COLUMN_NAME_PUBLIC_IMAGE_URL + " = ?";

    Mockito.stub(mMockedCursor.getCount()).toReturn(2);
    Mockito.stub(mMockedCursor.moveToNext()).toReturn(true).toReturn(true).toReturn(false);

    ArrayList<Spot> spots = mSpotDatabaseAdapter.getSpotsWithImage("url");

    Mockito.verify(mMockedDatabase).query(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
        any(String[].class), eq(selection), any(String[].class), anyString(),
        anyString(), anyString());

    Assert.assertNotNull(spots);
    Assert.assertEquals(spots.size(), 2);
  }

  @Test
  public void testGetSpotsWithName() {
    String query = "LOWER(name) LIKE LOWER(?)";
    String[] args = new String[]{ "%test%" };

    Mockito.stub(mMockedCursor.getCount()).toReturn(2);
    Mockito.stub(mMockedCursor.moveToNext()).toReturn(true).toReturn(true).toReturn(false);

    ArrayList<Spot> spots = mSpotDatabaseAdapter.getSpots("test");

    Mockito.verify(mMockedDatabase).query(
        Mockito.eq(OfflineEnduserContract.SpotEntry.TABLE_NAME),
        any(String[].class), eq(query), any(String[].class), anyString(),
        anyString(), anyString());

    Assert.assertNotNull(spots);
    Assert.assertEquals(spots.size(), 2);
  }

  @Test
  public void testDelete() {
    Mockito.stub(mMockedDatabase.delete(anyString(), anyString(), any(String[].class))).toReturn(1);

    boolean deleted = mSpotDatabaseAdapter.deleteSpot("1");

    junit.framework.Assert.assertTrue(deleted);
  }

  @Test
  public void testDeleteFail() {
    Mockito.stub(mMockedDatabase.delete(anyString(), anyString(), any(String[].class))).toReturn(0);

    boolean deleted = mSpotDatabaseAdapter.deleteSpot("1");

    junit.framework.Assert.assertFalse(deleted);
  }
}
