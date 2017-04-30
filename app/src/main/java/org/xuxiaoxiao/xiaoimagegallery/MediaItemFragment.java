package org.xuxiaoxiao.xiaoimagegallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by WuQiang on 2017/4/30.
 */

public class MediaItemFragment extends Fragment {
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    MediaItemAdapter mediaItemAdapter;
    Button sendItemButton;
    String mediaFolderName;
    private static final String MEDIA_FOLDER_NAME =
            "org.xuxiaoxiao.MediaFolderName";

    public static MediaItemFragment newInstance(String mediaFolderName) {
        // 这个方法接收来自 Activity 的数据
        Bundle args = new Bundle();
        args.putString(MEDIA_FOLDER_NAME, mediaFolderName);

        MediaItemFragment fragment = new MediaItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaFolderName = getArguments().getString(MEDIA_FOLDER_NAME);

    }

    /*
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
        */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_item,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.media_item_recycler_view);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
//        mediaItemAdapter = new MediaItemAdapter(getActivity(),mediaItems);
        recyclerView.setAdapter(mediaItemAdapter);

        sendItemButton = (Button)view.findViewById(R.id.send_item_button);
        sendItemButton.setText(mediaFolderName);
        sendItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;

    }
}
