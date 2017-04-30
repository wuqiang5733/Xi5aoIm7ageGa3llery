package org.xuxiaoxiao.xiaoimagegallery;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by WuQiang on 2017/4/30.
 */

public class MediaItemFragment extends Fragment {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    MediaItemAdapter mediaItemAdapter;
    Button sendItemButton;
    ArrayList<String> mediaItems = new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);

        super.onDetach();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMediaItem(SendMediaItem event) {
        this.mediaItems = event.getMediaItems();
        Log.d("WQWQ","我收到啦");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_item,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.media_item_recycler_view);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        mediaItemAdapter = new MediaItemAdapter(getActivity(),mediaItems);
        recyclerView.setAdapter(mediaItemAdapter);

        sendItemButton = (Button)view.findViewById(R.id.send_item_button);
        sendItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;

    }
}
