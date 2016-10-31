package com.xamoom.android.xamoomsdk.Storage.TableContracts;

import android.provider.BaseColumns;
import android.provider.Settings;

import com.xamoom.android.xamoomsdk.Resource.ContentBlock;
import com.xamoom.android.xamoomsdk.Resource.Style;

import java.util.Set;

/**
 * Created by raphaelseher on 24/10/2016.
 */

public class OfflineEnduserContract {
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "OfflineEnduser.db";

  public static final String TEXT_TYPE = " TEXT";
  public static final String INTEGER_TYPE = " INTEGER";
  public static final String REAL_TYPE = " REAL";
  public static final String UNIQUE = " UNIQUE";
  public static final String COMMA_SEP = ",";

  private OfflineEnduserContract() {}

  /**
   * System
   */
  public static class SystemEntry implements BaseColumns {
    public static final String TABLE_NAME = "System";
    public static final String COLUMN_NAME_JSON_ID = "json_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_NAME_STYLE = "style";
    public static final String COLUMN_NAME_SYSTEMSETTING = "setting";
    public static final String COLUMN_NAME_MENU = "menu";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + SystemEntry.TABLE_NAME + " (" +
            SystemEntry._ID + " INTEGER PRIMARY KEY," +
            SystemEntry.COLUMN_NAME_JSON_ID + TEXT_TYPE + COMMA_SEP +
            SystemEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            SystemEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
            SystemEntry.COLUMN_NAME_STYLE + INTEGER_TYPE + COMMA_SEP +
            SystemEntry.COLUMN_NAME_SYSTEMSETTING + INTEGER_TYPE + COMMA_SEP +
            SystemEntry.COLUMN_NAME_MENU + INTEGER_TYPE + " )";

    public static  final String[] PROJECTION = {
          SystemEntry._ID,
          SystemEntry.COLUMN_NAME_JSON_ID,
          SystemEntry.COLUMN_NAME_NAME,
          SystemEntry.COLUMN_NAME_URL,
          SystemEntry.COLUMN_NAME_STYLE,
          SystemEntry.COLUMN_NAME_SYSTEMSETTING,
          SystemEntry.COLUMN_NAME_MENU
    };
  }

  /**
   * Style
   */
  public static class StyleEntry implements BaseColumns {
    public static final String TABLE_NAME = "Style";
    public static final String COLUMN_NAME_JSON_ID = "json_id";
    public static final String COLUMN_NAME_BACKGROUND_COLOR = "background_color";
    public static final String COLUMN_NAME_HIGHLIGHT_COLOR = "highlight_color";
    public static final String COLUMN_NAME_FOREGROUND_COLOR = "foreground_color";
    public static final String COLUMN_NAME_CHROME_HEADER_COLOR = "chrome_header_color";
    public static final String COLUMN_NAME_MAP_PIN = "map_pin";
    public static final String COLUMN_NAME_ICON = "icon";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + StyleEntry.TABLE_NAME + " (" +
            StyleEntry._ID + " INTEGER PRIMARY KEY," +
            StyleEntry.COLUMN_NAME_JSON_ID + TEXT_TYPE + COMMA_SEP +
            StyleEntry.COLUMN_NAME_BACKGROUND_COLOR + TEXT_TYPE + COMMA_SEP +
            StyleEntry.COLUMN_NAME_HIGHLIGHT_COLOR + TEXT_TYPE + COMMA_SEP +
            StyleEntry.COLUMN_NAME_FOREGROUND_COLOR + TEXT_TYPE + COMMA_SEP +
            StyleEntry.COLUMN_NAME_CHROME_HEADER_COLOR + TEXT_TYPE + COMMA_SEP +
            StyleEntry.COLUMN_NAME_MAP_PIN + TEXT_TYPE + COMMA_SEP +
            StyleEntry.COLUMN_NAME_ICON + TEXT_TYPE + " )";

    public static  final String[] PROJECTION = {
        StyleEntry._ID,
        StyleEntry.COLUMN_NAME_JSON_ID,
        StyleEntry.COLUMN_NAME_BACKGROUND_COLOR,
        StyleEntry.COLUMN_NAME_HIGHLIGHT_COLOR,
        StyleEntry.COLUMN_NAME_FOREGROUND_COLOR,
        StyleEntry.COLUMN_NAME_CHROME_HEADER_COLOR,
        StyleEntry.COLUMN_NAME_MAP_PIN,
        StyleEntry.COLUMN_NAME_ICON,
    };
  }

  /**
   * Settings
   */
  public static class SettingEntry implements BaseColumns {
    public static final String TABLE_NAME = "Settings";
    public static final String COLUMN_NAME_JSON_ID = "json_id";
    public static final String COLUMN_NAME_PLAYSTORE_ID = "playstore_id";
    public static final String COLUMN_NAME_APPSTORE_ID = "appstore_id";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + SettingEntry.TABLE_NAME + " (" +
            SettingEntry._ID + " INTEGER PRIMARY KEY," +
            SettingEntry.COLUMN_NAME_JSON_ID + TEXT_TYPE + COMMA_SEP +
            SettingEntry.COLUMN_NAME_PLAYSTORE_ID + TEXT_TYPE + COMMA_SEP +
            SettingEntry.COLUMN_NAME_APPSTORE_ID + TEXT_TYPE + " )";

    public static final String[] PROJECTION = {
        SettingEntry._ID,
        SettingEntry.COLUMN_NAME_JSON_ID,
        SettingEntry.COLUMN_NAME_PLAYSTORE_ID,
        SettingEntry.COLUMN_NAME_APPSTORE_ID
    };
  }

  /**
   * Menu
   */
  public static class MenuEntry implements BaseColumns {
    public static final String TABLE_NAME = "Menu";
    public static final String COLUMN_NAME_JSON_ID = "json_id";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + MenuEntry.TABLE_NAME + " (" +
            MenuEntry._ID + " INTEGER PRIMARY KEY," +
            MenuEntry.COLUMN_NAME_JSON_ID + TEXT_TYPE + " )";

    public static final String[] PROJECTION = {
        MenuEntry._ID,
        MenuEntry.COLUMN_NAME_JSON_ID
    };
  }

  /**
   * Content
   */
  public static class ContentEntry implements BaseColumns {
    public static final String TABLE_NAME = "Content";
    public static final String COLUMN_NAME_JSON_ID = "json_id";
    public static final String COLUMN_NAME_SYSTEM_RELATION = "systemRelation";
    public static final String COLUMN_NAME_MENU_RELATION = "menuRelation";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_LANGUAGE = "language";
    public static final String COLUMN_NAME_CATEGORY = "category";
    public static final String COLUMN_NAME_TAGS = "tags";
    public static final String COLUMN_NAME_PUBLIC_IMAGE_URL = "imageUrl";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + ContentEntry.TABLE_NAME + " (" +
            ContentEntry._ID + " INTEGER PRIMARY KEY," +
            ContentEntry.COLUMN_NAME_JSON_ID + TEXT_TYPE + COMMA_SEP +
            ContentEntry.COLUMN_NAME_SYSTEM_RELATION + TEXT_TYPE + COMMA_SEP +
            ContentEntry.COLUMN_NAME_MENU_RELATION + TEXT_TYPE + COMMA_SEP +
            ContentEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            ContentEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            ContentEntry.COLUMN_NAME_LANGUAGE + TEXT_TYPE + COMMA_SEP +
            ContentEntry.COLUMN_NAME_CATEGORY + INTEGER_TYPE + COMMA_SEP +
            ContentEntry.COLUMN_NAME_TAGS + TEXT_TYPE + COMMA_SEP +
            ContentEntry.COLUMN_NAME_PUBLIC_IMAGE_URL + TEXT_TYPE + " )";

    public static final String[] PROJECTION = {
        ContentEntry._ID,
        ContentEntry.COLUMN_NAME_JSON_ID,
        ContentEntry.COLUMN_NAME_SYSTEM_RELATION,
        ContentEntry.COLUMN_NAME_TITLE,
        ContentEntry.COLUMN_NAME_DESCRIPTION,
        ContentEntry.COLUMN_NAME_LANGUAGE,
        ContentEntry.COLUMN_NAME_CATEGORY,
        ContentEntry.COLUMN_NAME_TAGS,
        ContentEntry.COLUMN_NAME_PUBLIC_IMAGE_URL
    };
  }

  /**
   * ContentBlock
   */
  public static class ContentBlockEntry implements BaseColumns {
    public static final String TABLE_NAME = "ContentBlock";
    public static final String COLUMN_NAME_JSON_ID = "json_id";
    public static final String COLUMN_NAME_CONTENT_RELATION = "contentRelation";
    public static final String COLUMN_NAME_BLOCK_TYPE = "blockType";
    public static final String COLUMN_NAME_PUBLIC_STATUS = "publicStatus";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_TEXT = "text";
    public static final String COLUMN_NAME_ARTISTS = "artists";
    public static final String COLUMN_NAME_FILE_ID = "fileId";
    public static final String COLUMN_NAME_SOUNDCLOUD_URL = "soundcloudUrl";
    public static final String COLUMN_NAME_LINK_TYPE = "linkType";
    public static final String COLUMN_NAME_LINK_URL = "linkUrl";
    public static final String COLUMN_NAME_CONTENT_ID = "contentId";
    public static final String COLUMN_NAME_DOWNLOAD_TYPE = "downloadType";
    public static final String COLUMN_NAME_SPOT_MAP_TAGS = "spotMapTags";
    public static final String COLUMN_NAME_SCALE_X = "scaleX";
    public static final String COLUMN_NAME_VIDEO_URL = "videoUrl";
    public static final String COLUMN_NAME_SHOW_CONTENT_ON_SPOTMAP = "showContentOnSpotmap";
    public static final String COLUMN_NAME_ALT_TEXT = "altText";

    public static final String CREATE_TABLE =
        "CREATE TABLE " + ContentBlockEntry.TABLE_NAME + " (" +
            ContentBlockEntry._ID + " INTEGER PRIMARY KEY," +
            ContentBlockEntry.COLUMN_NAME_JSON_ID + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_CONTENT_RELATION + INTEGER_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_BLOCK_TYPE + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_PUBLIC_STATUS + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_TEXT + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_ARTISTS + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_FILE_ID + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_SOUNDCLOUD_URL + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_LINK_TYPE + INTEGER_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_LINK_URL + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_CONTENT_ID + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_DOWNLOAD_TYPE + INTEGER_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_SPOT_MAP_TAGS + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_SCALE_X + REAL_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_VIDEO_URL + TEXT_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_SHOW_CONTENT_ON_SPOTMAP + INTEGER_TYPE + COMMA_SEP +
            ContentBlockEntry.COLUMN_NAME_ALT_TEXT + TEXT_TYPE +  " )";

    public static final String[] PROJECTION = {
        ContentBlockEntry._ID,
        ContentBlockEntry.COLUMN_NAME_JSON_ID,
        ContentBlockEntry.COLUMN_NAME_BLOCK_TYPE,
        ContentBlockEntry.COLUMN_NAME_PUBLIC_STATUS,
        ContentBlockEntry.COLUMN_NAME_TITLE,
        ContentBlockEntry.COLUMN_NAME_TEXT,
        ContentBlockEntry.COLUMN_NAME_ARTISTS,
        ContentBlockEntry.COLUMN_NAME_FILE_ID,
        ContentBlockEntry.COLUMN_NAME_SOUNDCLOUD_URL,
        ContentBlockEntry.COLUMN_NAME_LINK_TYPE,
        ContentBlockEntry.COLUMN_NAME_LINK_URL,
        ContentBlockEntry.COLUMN_NAME_CONTENT_ID,
        ContentBlockEntry.COLUMN_NAME_DOWNLOAD_TYPE,
        ContentBlockEntry.COLUMN_NAME_SPOT_MAP_TAGS,
        ContentBlockEntry.COLUMN_NAME_SCALE_X,
        ContentBlockEntry.COLUMN_NAME_VIDEO_URL,
        ContentBlockEntry.COLUMN_NAME_SHOW_CONTENT_ON_SPOTMAP,
        ContentBlockEntry.COLUMN_NAME_ALT_TEXT };
  }
}