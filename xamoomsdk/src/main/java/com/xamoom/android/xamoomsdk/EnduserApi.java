package com.xamoom.android.xamoomsdk;

import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.xamoom.android.xamoomsdk.Resource.Attributes.ContentAttributesMessage;
import com.xamoom.android.xamoomsdk.Resource.Attributes.ContentBlockAttributeMessage;
import com.xamoom.android.xamoomsdk.Resource.Base.DataMessage;
import com.xamoom.android.xamoomsdk.Resource.Base.EmptyMessage;
import com.xamoom.android.xamoomsdk.Resource.Base.JsonApiMessage;
import com.xamoom.android.xamoomsdk.Resource.Content;
import com.xamoom.android.xamoomsdk.Resource.Error.ErrorMessage;
import com.xamoom.android.xamoomsdk.Resource.Meta.PagingMeta;
import com.xamoom.android.xamoomsdk.Resource.Relationships.ContentRelationships;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * EnduserApi is the main part of the XamoomSDK. You can use it to send api request to
 * the xamoom cloud.
 *
 * Use {@link #EnduserApi(String)} to initialize.
 *
 * Change the requested language by setting {@link #mLanguage}. The users language is saved
 * in {@link #mSystemLanguage}.
 */
public class EnduserApi {
  private static final String TAG = EnduserApi.class.getSimpleName();
  private static final String API_URL = "https://xamoom-cloud.appspot.com/_api/v2/consumer";

  private EnduserApiInterface mEnduserApiInterface;
  private String mLanguage;
  private String mSystemLanguage;

  public EnduserApi(final String apikey) {
    RestAdapter restAdapter = new RestAdapter.Builder()
        .setEndpoint(API_URL)
        .setRequestInterceptor(new RequestInterceptor() {
          @Override
          public void intercept(RequestFacade request) {
            request.addHeader("APIKEY", apikey);
          }
        })
        .setConverter(new GsonConverter(new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()))
        .build();

    mEnduserApiInterface = restAdapter.create(EnduserApiInterface.class);
    mSystemLanguage = Locale.getDefault().getLanguage();
    mLanguage = mSystemLanguage;
  }

  public EnduserApi(RestAdapter restAdapter) {
    mEnduserApiInterface = restAdapter.create(EnduserApiInterface.class);
    mSystemLanguage = Locale.getDefault().getLanguage();
    mLanguage = mSystemLanguage;
  }

  /**
   * Get a content for a specific contentID.
   *
   * @param contentID ContentID from xamoom-cloud.
   * @param callback {@link APICallback}.
   */
  public void getContent(final String contentID, final APICallback<Content, ErrorMessage> callback) {
    Map<String, String> params = getUrlParameter();

    mEnduserApiInterface.getContent(contentID, params, new ResponseCallback<JsonApiMessage<EmptyMessage,
        DataMessage<ContentAttributesMessage, ContentRelationships>,
        List<DataMessage<ContentBlockAttributeMessage, EmptyMessage>>>>() {

      @Override
      public void success(JsonApiMessage<EmptyMessage, DataMessage<ContentAttributesMessage,
          ContentRelationships>, List<DataMessage<ContentBlockAttributeMessage, EmptyMessage>>>
                              jsonApiMessage, Response response) {
        Content content = JsonApiObjectGenerator.jsonToContent(jsonApiMessage);
        callback.finished(content);
      }

      @Override
      public void failure(ErrorMessage error) {
        callback.error(error);
      }
    });
  }

  /**
   * Get a content for a specific contentID with possible flags.
   *
   * @param contentID ContentID from xamoom-cloud.
   * @param contentFlags Different flags {@link ContentFlags}.
   * @param callback {@link APICallback}.
   */
  public void getContent(String contentID, EnumSet<ContentFlags> contentFlags, final APICallback<Content,
      ErrorMessage> callback) {
    Map<String, String> params = getUrlParameterContent(contentFlags);

    mEnduserApiInterface.getContent(contentID, params, new ResponseCallback<JsonApiMessage<EmptyMessage,
        DataMessage<ContentAttributesMessage, ContentRelationships>,
        List<DataMessage<ContentBlockAttributeMessage, EmptyMessage>>>>() {

      @Override
      public void success(JsonApiMessage<EmptyMessage, DataMessage<ContentAttributesMessage,
          ContentRelationships>, List<DataMessage<ContentBlockAttributeMessage, EmptyMessage>>>
                              jsonApiMessage, Response response) {

        Content content = JsonApiObjectGenerator.jsonToContent(jsonApiMessage);
        callback.finished(content);
      }

      @Override
      public void failure(ErrorMessage error) {
        callback.error(error);
      }
    });
  }

  /**
   * Get a content for a specific LocationIdentifier.
   *
   * @param locationIdentifier LocationIdentifier from QR or NFC.
   * @param callback {@link APICallback}.
   */
  public void getContentByLocationIdentifier(String locationIdentifier, final APICallback<Content,
      ErrorMessage> callback) {
    Map<String, String> params = getUrlParameter();
    params.put("filter[location-identifier]", locationIdentifier);

    mEnduserApiInterface.getContent(params, new ResponseCallback<JsonApiMessage<EmptyMessage,
        DataMessage<ContentAttributesMessage, ContentRelationships>,
        List<DataMessage<ContentBlockAttributeMessage, EmptyMessage>>>>() {
      @Override
      public void success(JsonApiMessage<EmptyMessage, DataMessage<ContentAttributesMessage,
          ContentRelationships>, List<DataMessage<ContentBlockAttributeMessage,
          EmptyMessage>>> jsonApiMessage, Response response) {
        Content content = JsonApiObjectGenerator.jsonToContent(jsonApiMessage);
        callback.finished(content);
      }

      @Override
      public void failure(ErrorMessage error) {
        callback.error(error);
      }
    });
  }

  /**
   * Get content for a specific beacon.
   *
   * @param major Beacon major ID.
   * @param minor Beacon minor ID.
   * @param callback {@link APICallback}.
   */
  public void getContentByBeacon(int major, int minor, final APICallback<Content, ErrorMessage>
      callback) {
    getContentByLocationIdentifier(String.format("%s|%s", major, minor), callback);
  }

  /**
   * Get list of contents with your location. Geofence radius is 40m.
   *
   * @param location Users location.
   * @param pageSize PageSize for returned contents. Default 100.
   * @param cursor Cursor for paging.
   * @param sortFlags {@link ContentSortFlags} to sort results.
   * @param callback {@link APIListCallback}.
   */
  public void getContentsByLocation(Location location, int pageSize, @Nullable String cursor,
      final EnumSet<ContentSortFlags> sortFlags, final APIListCallback<List<Content>, ErrorMessage> callback) {

    Map<String, String> params = getUrlParameterContentSort(sortFlags);
    params = addPagingToUrl(params, pageSize, cursor);
    params.put("filter[lat]", Double.toString(location.getLatitude()));
    params.put("filter[lon]", Double.toString(location.getLongitude()));

    mEnduserApiInterface.getContents(params, new ResponseCallback<JsonApiMessage<PagingMeta,
        List<DataMessage<ContentAttributesMessage, ContentRelationships>>,
        List<DataMessage<ContentBlockAttributeMessage, EmptyMessage>>>>() {
      @Override
      public void failure(ErrorMessage error) {
        callback.error(error);
      }

      @Override
      public void success(JsonApiMessage<PagingMeta, List<DataMessage<ContentAttributesMessage,
          ContentRelationships>>, List<DataMessage<ContentBlockAttributeMessage, EmptyMessage>>>
                              jsonApiMessage, Response response) {

        List<Content> contents = JsonApiObjectGenerator.jsonToContents(jsonApiMessage);
        String cursor = jsonApiMessage.getMeta().getCursor();
        boolean hasMore = jsonApiMessage.getMeta().hasMore();
        callback.finished(contents, cursor, hasMore);
      }
    });
  }

  private Map<String, String> getUrlParameter() {
    Map<String, String> params = new LinkedHashMap<>();
    params.put("lang", mLanguage);
    return params;
  }

  private Map<String, String> getUrlParameterContent(EnumSet<ContentFlags> contentFlags) {
    Map<String, String> params = getUrlParameter();

    if (contentFlags == null) {
      return params;
    }

    if (contentFlags.contains(ContentFlags.PREVIEW)) {
      params.put("preview", "true");
    }

    if (contentFlags.contains(ContentFlags.PRIVATE)) {
      params.put("public-only", "true");
    }

    return params;
  }

  private Map<String, String> getUrlParameterContentSort(EnumSet<ContentSortFlags> contentSortFlags) {
    Map<String, String> params = getUrlParameter();

    if (contentSortFlags == null) {
      return params;
    }

    ArrayList<String> sortParams = new ArrayList<String>();

    if (contentSortFlags.contains(ContentSortFlags.NAME)) {
      sortParams.add("name");
    }

    if (contentSortFlags.contains(ContentSortFlags.NAME_DESC)) {
      sortParams.add("-name");
    }

    params.put("sort", TextUtils.join(",", sortParams));
    return params;
  }

  private Map<String, String> addPagingToUrl( Map<String, String> params, int pageSize, String cursor) {
    params.put("page[size]", Integer.toString(pageSize));

    if (cursor != null) {
      params.put("page[cursor]", cursor);
    }

    return params;
  }

  //getters & setters

  public String getSystemLanguage() {
    return mSystemLanguage;
  }

  public String getLanguage() {
    return mLanguage;
  }

  public EnduserApiInterface getEnduserApiInterface() {
    return mEnduserApiInterface;
  }

  public void setLanguage(String language) {
    mLanguage = language;
  }

  public void setEnduserApiInterface(EnduserApiInterface enduserApiInterface) {
    mEnduserApiInterface = enduserApiInterface;
  }
}

