/*
* Copyright (c) 2017 xamoom GmbH <apps@xamoom.com>
*
* Licensed under the MIT License (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at the root of this project.
*/

package com.xamoom.android.xamoomsdk.Resource;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.rags.morpheus.Annotations.Relationship;
import at.rags.morpheus.Resource;

/**
 * xamoom Spot model.
 */
public class Spot extends Resource implements Parcelable {
  private String name;
  @SerializedName("image")
  private String publicImageUrl;
  private String description;
  private Location location;
  private List<String> tags;
  @Relationship("markers")
  private List<Marker> markers;
  @Relationship("system")
  private System system;
  @Relationship("content")
  private Content content;
  private int category;
  @SerializedName("custom-meta")
  private ArrayList<KeyValueObject> customMeta;

  public Spot() {
  }

  protected Spot(Parcel in) {
    this.setId(in.readString());
    name = in.readString();
    publicImageUrl = in.readString();
    description = in.readString();
    location = in.readParcelable(Location.class.getClassLoader());
    tags = in.createStringArrayList();
    markers = in.createTypedArrayList(Marker.CREATOR);
    system = in.readParcelable(System.class.getClassLoader());
    content = in.readParcelable(Content.class.getClassLoader());
    category = in.readInt();
  }

  public static final Creator<Spot> CREATOR = new Creator<Spot>() {
    @Override
    public Spot createFromParcel(Parcel in) {
      return new Spot(in);
    }

    @Override
    public Spot[] newArray(int size) {
      return new Spot[size];
    }
  };

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPublicImageUrl() {
    return publicImageUrl;
  }

  public void setPublicImageUrl(String publicImageUrl) {
    this.publicImageUrl = publicImageUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public List<Marker> getMarkers() {
    return markers;
  }

  public void setMarkers(List<Marker> markers) {
    this.markers = markers;
  }

  public System getSystem() {
    return system;
  }

  public void setSystem(System system) {
    this.system = system;
  }

  public Content getContent() {
    return content;
  }

  public void setContent(Content content) {
    this.content = content;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setCategory(int category) {
    this.category = category;
  }

  public int getCategory() {
    return category;
  }

  public HashMap<String, String> getCustomMeta() {
    if (customMeta == null) {
      return null;
    }

    HashMap<String, String> customMetaMap = new HashMap<>();
    for (KeyValueObject meta : customMeta) {
      customMetaMap.put(meta.getKey(), meta.getValue());
    }
    return customMetaMap;
  }

  public void setCustomMeta(HashMap<String, String> customMeta) {
    ArrayList<KeyValueObject> customMetaList = new ArrayList<>();

    for (Object o : customMeta.entrySet()) {
      Map.Entry pair = (Map.Entry) o;
      customMetaList.add(new KeyValueObject(pair.getKey().toString(),
          pair.getValue().toString()));
    }

    this.customMeta = customMetaList;
  }

  public void setCustomMeta(ArrayList<KeyValueObject> customMeta) {
    this.customMeta = customMeta;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.getId());
    dest.writeString(name);
    dest.writeString(publicImageUrl);
    dest.writeString(description);
    dest.writeParcelable(location, flags);
    dest.writeStringList(tags);
    dest.writeTypedList(markers);
    dest.writeParcelable(system, flags);
    dest.writeParcelable(content, flags);
    dest.writeInt(category);
  }
}
