package com.xamoom.android.xamoomsdk.xamoomsdk.Storage;

import com.xamoom.android.xamoomsdk.BuildConfig;
import com.xamoom.android.xamoomsdk.Resource.Content;
import com.xamoom.android.xamoomsdk.Resource.Menu;
import com.xamoom.android.xamoomsdk.Resource.Spot;
import com.xamoom.android.xamoomsdk.Resource.Style;
import com.xamoom.android.xamoomsdk.Resource.System;
import com.xamoom.android.xamoomsdk.Resource.SystemSetting;
import com.xamoom.android.xamoomsdk.Storage.Database.ContentDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.Database.MenuDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.Database.SettingDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.Database.SpotDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.Database.StyleDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.Database.SystemDatabaseAdapter;
import com.xamoom.android.xamoomsdk.Storage.DownloadManager;
import com.xamoom.android.xamoomsdk.Storage.OfflineStorageManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OfflineStorageManagerTest {

  private OfflineStorageManager mOfflineSavingManager;
  private DownloadManager mMockedDownloadManager;
  private ContentDatabaseAdapter mMockedContentDatabaseAdapter;
  private SpotDatabaseAdapter mMockedSpotDatabaseAdapter;
  private SystemDatabaseAdapter mMockedSystemDatabaseAdapter;
  private StyleDatabaseAdapter mMockedStyleDatabaseAdapter;
  private SettingDatabaseAdapter mMockedSettingDatabaseAdapter;
  private MenuDatabaseAdapter mMockedMenuDatabaseAdapter;

  @Before
  public void setup() {
    mOfflineSavingManager = OfflineStorageManager.getInstance(RuntimeEnvironment.application);
    mMockedDownloadManager = Mockito.mock(DownloadManager.class);
    mMockedContentDatabaseAdapter = Mockito.mock(ContentDatabaseAdapter.class);
    mMockedSpotDatabaseAdapter = Mockito.mock(SpotDatabaseAdapter.class);
    mMockedSystemDatabaseAdapter = Mockito.mock(SystemDatabaseAdapter.class);
    mMockedStyleDatabaseAdapter = Mockito.mock(StyleDatabaseAdapter.class);
    mMockedSettingDatabaseAdapter = Mockito.mock(SettingDatabaseAdapter.class);
    mMockedMenuDatabaseAdapter = Mockito.mock(MenuDatabaseAdapter.class);

    mOfflineSavingManager.setDownloadManager(mMockedDownloadManager);
    mOfflineSavingManager.setContentDatabaseAdapter(mMockedContentDatabaseAdapter);
    mOfflineSavingManager.setSpotDatabaseAdapter(mMockedSpotDatabaseAdapter);
    mOfflineSavingManager.setSystemDatabaseAdapter(mMockedSystemDatabaseAdapter);
    mOfflineSavingManager.setStyleDatabaseAdapter(mMockedStyleDatabaseAdapter);
    mOfflineSavingManager.setSettingDatabaseAdapter(mMockedSettingDatabaseAdapter);
    mOfflineSavingManager.setMenuDatabaseAdapter(mMockedMenuDatabaseAdapter);
  }

  @Test
  public void testSaveContent() throws MalformedURLException {
    Content content = new Content();
    content.setId("1");
    content.setPublicImageUrl("http://www.xamoom.com");

    Mockito.stub(mMockedContentDatabaseAdapter.insertOrUpdateContent(eq(content), eq(false),
        eq(-1))).toReturn(1L);

    boolean saved = false;
    try {
      saved = mOfflineSavingManager.saveContent(content, null);
    } catch (MalformedURLException e) {
      Assert.fail();
    }

    Assert.assertTrue(saved);
    Mockito.verify(mMockedContentDatabaseAdapter).insertOrUpdateContent(eq(content), eq(false), eq(-1L));
    Mockito.verify(mMockedDownloadManager).saveFileFromUrl(
        eq(new URL("http://www.xamoom.com")),
        eq(false),
        any(DownloadManager.OnDownloadManagerCompleted.class));
  }

  @Test
  public void testSaveSpot() throws MalformedURLException {
    Spot spot = new Spot();
    spot.setPublicImageUrl("http://www.xamoom.com");

    Mockito.stub(mMockedSpotDatabaseAdapter.insertOrUpdateSpot(eq(spot))).toReturn(1L);

    boolean saved = false;
    try {
      saved = mOfflineSavingManager.saveSpot(spot, null);
    } catch (MalformedURLException e) {
      Assert.fail();
    }

    Assert.assertTrue(saved);
    Mockito.verify(mMockedSpotDatabaseAdapter).insertOrUpdateSpot(eq(spot));
    Mockito.verify(mMockedDownloadManager).saveFileFromUrl(
        eq(new URL("http://www.xamoom.com")),
        eq(false),
        any(DownloadManager.OnDownloadManagerCompleted.class));
  }

  @Test
  public void testSaveSystem() {
    System system = new System();
    system.setId("1");

    Mockito.stub(mMockedSystemDatabaseAdapter.insertOrUpdateSystem(eq(system))).toReturn(1L);

    boolean saved = mOfflineSavingManager.saveSystem(system);

    Assert.assertTrue(saved);
    Mockito.verify(mMockedSystemDatabaseAdapter).insertOrUpdateSystem(eq(system));
  }

  @Test
  public void testSaveStyle() {
    Style style = new Style();
    style.setId("1");

    Mockito.stub(mMockedStyleDatabaseAdapter.insertOrUpdateStyle(eq(style), anyLong())).toReturn(1L);

    boolean saved = mOfflineSavingManager.saveStyle(style);

    Assert.assertTrue(saved);
    Mockito.verify(mMockedStyleDatabaseAdapter).insertOrUpdateStyle(eq(style), eq(-1L));
  }

  @Test
  public void testSaveSetting() {
    SystemSetting setting = new SystemSetting();
    setting.setId("1");

    Mockito.stub(mMockedSettingDatabaseAdapter.insertOrUpdateSetting(eq(setting), anyLong()))
        .toReturn(1L);

    boolean saved = mOfflineSavingManager.saveSetting(setting);

    Assert.assertTrue(saved);
    Mockito.verify(mMockedSettingDatabaseAdapter).insertOrUpdateSetting(eq(setting), eq(-1L));
  }

  @Test
  public void testSaveMenu() {
    Menu menu = new Menu();
    menu.setId("1");

    Mockito.stub(mMockedMenuDatabaseAdapter.insertOrUpdate(eq(menu), anyLong()))
        .toReturn(1L);

    boolean saved = mOfflineSavingManager.saveMenu(menu);

    Assert.assertTrue(saved);
    Mockito.verify(mMockedMenuDatabaseAdapter).insertOrUpdate(eq(menu), eq(-1L));
  }


}