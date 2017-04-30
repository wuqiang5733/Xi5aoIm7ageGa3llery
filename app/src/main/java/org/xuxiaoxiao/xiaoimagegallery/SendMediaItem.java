package org.xuxiaoxiao.xiaoimagegallery;

import java.util.ArrayList;

/**
 * Created by WuQiang on 2017/4/30.
 */

public class SendMediaItem {
    ArrayList<String> mediaItems = new ArrayList<>();

    public SendMediaItem(ArrayList<String> mediaItems) {
        this.mediaItems = mediaItems;
    }

    public ArrayList<String> getMediaItems() {
        return mediaItems;
    }

    public void setMediaItems(ArrayList<String> mediaItems) {
        this.mediaItems = mediaItems;
    }
}
