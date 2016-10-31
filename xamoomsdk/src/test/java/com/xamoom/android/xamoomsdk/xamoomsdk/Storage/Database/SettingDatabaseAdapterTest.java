package com.xamoom.android.xamoomsdk.xamoomsdk.Storage.Database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xamoom.android.xamoomsdk.BuildConfig;
import com.xamoom.android.xamoomsdk.Resource.Content;
import com.xamoom.android.xamoomsdk.Resource.System;
import com.xamoom.android.xamoomsdk.Resource.SystemSetting;
import com.xamoom.android.xamoomsdk.Storage.Database.DatabaseHelper;
import com.xamoom.android.xamoomsdk.Storage.Database.SettingDatabaseAdapter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.util.SQLiteLibraryLoader;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.stub;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SettingDatabaseAdapterTest {

  private SettingDatabaseAdapter mSettingDatabaseAdapter;
  private DatabaseHelper mMockedDatabaseHelper;
  private SQLiteDatabase mMockedDatabase;
  private Cursor mMockedCursor;

  @Before
  public void setup() {
    mSettingDatabaseAdapter = new SettingDatabaseAdapter(RuntimeEnvironment.application);
    mMockedDatabaseHelper = mock(DatabaseHelper.class);
    mMockedDatabase = mock(SQLiteDatabase.class);
    mMockedCursor = mock(Cursor.class);

    mSettingDatabaseAdapter.setDatabaseHelper(mMockedDatabaseHelper);

    Mockito.stub(mMockedDatabaseHelper.getWritableDatabase())
        .toReturn(mMockedDatabase);
    Mockito.stub(mMockedDatabaseHelper.getReadableDatabase())
        .toReturn(mMockedDatabase);
  }

  @Test
  public void testGetSystemSetting() {
    Mockito.stub(mMockedDatabase.query(anyString(),
        any(String[].class), anyString(), any(String[].class),
        anyString(), anyString(), anyString()))
        .toReturn(mMockedCursor);

    stub(mMockedCursor.moveToFirst()).toReturn(true);
    stub(mMockedCursor.getString(0)).toReturn("1");

    SystemSetting savedSetting =
        mSettingDatabaseAdapter.getSystemSetting("1");

    Assert.assertNotNull(savedSetting);
    Assert.assertEquals(savedSetting.getId(), "1");
  }

  @Test
  public void testInsertOrUpdateSettingWithNewSetting() {
    SystemSetting setting = new SystemSetting();
    setting.setId("1");

    Mockito.stub(mMockedDatabase.query(anyString(),
        any(String[].class), anyString(), any(String[].class),
        anyString(), anyString(), anyString()))
        .toReturn(mMockedCursor);
    stub(mMockedCursor.moveToFirst()).toReturn(true);
    stub(mMockedCursor.getLong(0)).toReturn(-1L);

    mSettingDatabaseAdapter.insertOrUpdateSetting(setting);

    Mockito.verify(mMockedDatabase).insert(anyString(), anyString(),
        any(ContentValues.class));
  }

  @Test
  public void testInsertOrUpdateSettingWithExistingSetting() {
    SystemSetting setting = new SystemSetting();
    setting.setId("1");

    Mockito.stub(mMockedDatabase.query(anyString(),
        any(String[].class), anyString(), any(String[].class),
        anyString(), anyString(), anyString()))
        .toReturn(mMockedCursor);
    stub(mMockedCursor.moveToFirst()).toReturn(true);
    stub(mMockedCursor.getLong(0)).toReturn(0L);

    mSettingDatabaseAdapter.insertOrUpdateSetting(setting);

    Mockito.verify(mMockedDatabase).update(anyString(), any(ContentValues.class),
        anyString(), any(String[].class));
  }
}