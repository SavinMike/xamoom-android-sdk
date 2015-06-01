package com.xamoom.xamoom_android_sdk.xamoom_android_sdk.api.mapping;

import java.util.List;

/**
 * Created by raphaelseher on 28.05.15.
 */
public class ResponseMenu {

    private List<ResponseMenuItem> items;

    @Override
    public String toString() {
        return (String.format("{items: %s}", items));
    }
}

class ResponseMenuItem {

    private String itemLabel;
    private String contentId;

    @Override
    public String toString() {
        return (String.format("{itemLabel: %s, contentId: %s}", itemLabel, contentId));
    }
}
