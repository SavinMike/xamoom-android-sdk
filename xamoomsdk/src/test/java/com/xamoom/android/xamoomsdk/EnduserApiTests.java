package com.xamoom.android.xamoomsdk;

import android.location.Location;
import android.text.TextUtils;

import com.xamoom.android.xamoomsdk.Resource.Content;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import at.rags.morpheus.Error;
import at.rags.morpheus.JsonApiObject;
import at.rags.morpheus.Morpheus;
import at.rags.morpheus.Resource;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnduserApiTests {
  private static final String TAG = EnduserApiTests.class.getSimpleName();

  private MockWebServer mMockWebServer;
  private EnduserApi mEnduserApi;
  private Morpheus mMockMorpheus;

  @Captor ArgumentCaptor<Map<String, String>> mMapArgumentCaptor;

  @Before
  public void setup() {
    mMockWebServer = new MockWebServer();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(mMockWebServer.url(""))
        .build();
    mEnduserApi = new EnduserApi(retrofit);

    mMockMorpheus = mock(Morpheus.class);
    mEnduserApi.setMorpheus(mMockMorpheus);
  }

  @After
  public void teardown() {
    try {
      mMockWebServer.shutdown();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testInitWithApikey() throws Exception {
    EnduserApi enduserApi = new EnduserApi("test");

    assertNotNull(enduserApi.getEnduserApiInterface());
    assertEquals(enduserApi.getSystemLanguage(), "en");
  }

  @Test
  public void testInitWithRestAdapter() throws Exception {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://www.xamoom.com/")
        .build();

    EnduserApi enduserApi = new EnduserApi(retrofit);

    assertNotNull(enduserApi.getEnduserApiInterface());
    assertEquals(enduserApi.getSystemLanguage(), "en");
  }

  @Test
  public void testGetContentWithIDSuccess() throws Exception {
    mMockWebServer.enqueue(new MockResponse().setBody(""));

    final Content[] checkContent = {null};

    Content content = new Content();
    content.setTitle("Test");

    JsonApiObject jsonApiObject = new JsonApiObject();
    jsonApiObject.setResource(content);

    when(mMockMorpheus.parse(anyString())).thenReturn(jsonApiObject);

    final Semaphore semaphore = new Semaphore(0);

    mEnduserApi.getContent("123456", new APICallback<Content, List<at.rags.morpheus.Error>>() {
      @Override
      public void finished(Content result) {
        checkContent[0] = result;
        semaphore.release();
      }

      @Override
      public void error(List<Error> error) {
        semaphore.release();
      }
    });

    semaphore.acquire();

    assertTrue(checkContent[0].getTitle().equals("Test"));
    RecordedRequest request1 = mMockWebServer.takeRequest();
    assertEquals("/contents/123456?lang=en", request1.getPath());
  }

  @Test
  public void testGetContentWithIDError() throws Exception {
    mMockWebServer.enqueue(new MockResponse().setBody(""));

    final Error[] checkError = {null};

    Error error = new Error();
    error.setTitle("Fail");
    ArrayList<Error> errors = new ArrayList<>();
    errors.add(error);

    JsonApiObject jsonApiObject = new JsonApiObject();
    jsonApiObject.setErrors(errors);

    when(mMockMorpheus.parse(anyString())).thenReturn(jsonApiObject);

    final Semaphore semaphore = new Semaphore(0);

    mEnduserApi.getContent("123456", new APICallback<Content, List<at.rags.morpheus.Error>>() {
      @Override
      public void finished(Content result) {
        semaphore.release();
      }

      @Override
      public void error(List<Error> error) {
        checkError[0] = error.get(0);
        semaphore.release();
      }
    });

    semaphore.acquire();

    assertTrue(checkError[0].getTitle().equals("Fail"));
    RecordedRequest request1 = mMockWebServer.takeRequest();
    assertEquals("/contents/123456?lang=en", request1.getPath());
  }

  @Test
  public void testGetContentWithIDParam() throws Exception {
    EnduserApi enduserApi = new EnduserApi("123456");
    EnduserApiInterface enduserApiInterface = mock(EnduserApiInterface.class);
    enduserApi.setEnduserApiInterface(enduserApiInterface);

    enduserApi.getContent("123456", EnumSet.of(ContentFlags.PREVIEW, ContentFlags.PRIVATE),
        new APICallback<Content, List<at.rags.morpheus.Error>>() {
          @Override
          public void finished(Content result) {
          }

          @Override
          public void error(List<Error> error) {
          }
        });

    HashMap<String, String> checkParams = new HashMap<>();
    checkParams.put("lang", "en");
    checkParams.put("preview", "true");
    checkParams.put("public-only", "true");

    verify(enduserApiInterface).getContent(eq("123456"), mMapArgumentCaptor.capture());
    assertTrue(mMapArgumentCaptor.getValue().equals(checkParams));
  }

  @Test
  public void testGetContentWithLocationIdentifierSuccess() throws Exception {
    mMockWebServer.enqueue(new MockResponse().setBody(""));

    final Content[] checkContent = {null};

    Content content = new Content();
    content.setTitle("Test");

    JsonApiObject jsonApiObject = new JsonApiObject();
    jsonApiObject.setResource(content);

    when(mMockMorpheus.parse(anyString())).thenReturn(jsonApiObject);

    final Semaphore semaphore = new Semaphore(0);

    mEnduserApi.getContentByLocationIdentifier("1234", new APICallback<Content, List<Error>>() {
      @Override
      public void finished(Content result) {
        checkContent[0] = result;
        semaphore.release();
      }

      @Override
      public void error(List<Error> error) {
        semaphore.release();
      }
    });

    semaphore.acquire();

    assertTrue(checkContent[0].getTitle().equals("Test"));
    RecordedRequest request1 = mMockWebServer.takeRequest();
    assertEquals("/contents?lang=en&filter[location-identifier]=1234", request1.getPath());
  }

  @Test
  public void testGetContentWithBeaconSuccess() throws Exception {
    mMockWebServer.enqueue(new MockResponse().setBody(""));

    final Content[] checkContent = {null};

    Content content = new Content();
    content.setTitle("Test");

    JsonApiObject jsonApiObject = new JsonApiObject();
    jsonApiObject.setResource(content);

    when(mMockMorpheus.parse(anyString())).thenReturn(jsonApiObject);

    final Semaphore semaphore = new Semaphore(0);

    mEnduserApi.getContentByBeacon(1, 2, new APICallback<Content, List<Error>>() {
      @Override
      public void finished(Content result) {
        checkContent[0] = result;
        semaphore.release();
      }

      @Override
      public void error(List<Error> error) {
        semaphore.release();
      }
    });

    semaphore.acquire();

    assertTrue(checkContent[0].getTitle().equals("Test"));
    RecordedRequest request1 = mMockWebServer.takeRequest();
    assertEquals("/contents?lang=en&filter[location-identifier]=1|2", request1.getPath());
  }


  @Test
  public void testGetContentsWithLocationSuccess() throws Exception {
    mMockWebServer.enqueue(new MockResponse().setBody(""));

    final List<Content> checkContents = new ArrayList<>();

    Content content = new Content();
    content.setTitle("Test");
    ArrayList<Resource> contents = new ArrayList<Resource>();
    contents.add(content);

    HashMap<String, Object> meta = new HashMap<>();
    meta.put("cursor", "1");
    meta.put("has-more", true);

    JsonApiObject jsonApiObject = new JsonApiObject();
    jsonApiObject.setResources(contents);
    jsonApiObject.setMeta(meta);

    Location location = mock(Location.class);
    when(location.getLatitude()).thenReturn(1.0);
    when(location.getLongitude()).thenReturn(2.0);

    when(mMockMorpheus.parse(anyString())).thenReturn(jsonApiObject);

    final Semaphore semaphore = new Semaphore(0);

    mEnduserApi.getContentsByLocation(location, 10, null, null, new APIListCallback<List<Content>, List<Error>>() {
      @Override
      public void finished(List<Content> result, String cursor, boolean hasMore) {
        checkContents.addAll(result);
        semaphore.release();
      }

      @Override
      public void error(List<Error> error) {
        semaphore.release();
      }
    });

    semaphore.acquire();

    assertTrue(checkContents.get(0).getTitle().equals("Test"));
    RecordedRequest request1 = mMockWebServer.takeRequest();
    assertEquals("/contents?lang=en&page[size]=10&filter[lat]=1.0&filter[lon]=2.0", request1.getPath());
  }

  @Test
  public void testGetContentsWithTagsSuccess() throws Exception {
    mMockWebServer.enqueue(new MockResponse().setBody(""));

    final List<Content> checkContents = new ArrayList<>();

    Content content = new Content();
    content.setTitle("Test");
    ArrayList<Resource> contents = new ArrayList<Resource>();
    contents.add(content);

    HashMap<String, Object> meta = new HashMap<>();
    meta.put("cursor", "1");
    meta.put("has-more", true);

    JsonApiObject jsonApiObject = new JsonApiObject();
    jsonApiObject.setResources(contents);
    jsonApiObject.setMeta(meta);

    List<String> tags = new ArrayList<>();
    tags.add("tag1");
    tags.add("tag2");

    when(mMockMorpheus.parse(anyString())).thenReturn(jsonApiObject);

    final Semaphore semaphore = new Semaphore(0);

    mEnduserApi.getContentsByTags(tags, 10, null, null, new APIListCallback<List<Content>, List<Error>>() {
      @Override
      public void finished(List<Content> result, String cursor, boolean hasMore) {
        checkContents.addAll(result);
        semaphore.release();
      }

      @Override
      public void error(List<Error> error) {
        semaphore.release();
      }
    });

    semaphore.acquire();

    assertTrue(checkContents.get(0).getTitle().equals("Test"));
    RecordedRequest request1 = mMockWebServer.takeRequest();
    assertEquals("/contents?lang=en&page[size]=10&filter[tags]=tag1,tag2", request1.getPath());
  }
}