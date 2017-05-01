package org.xuxiaoxiao.xiaoimagegallery;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by WuQiang on 2017/4/30.
 */

public class MediaFolderFragment extends Fragment {
    boolean boolean_folder;

    RecyclerView recyclerView;
    MediaFolderAdapter mediaAdapter;
    LinearLayoutManager linearLayoutManager;
//    ArrayList<Model_images> mediaFileInfos = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_folder, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.media_folder_recycler_view);
        Drawable divider = getResources().getDrawable(R.drawable.item_divider);

        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        new MediaAsyncTask().execute();

        return view;
    }

    public class MediaAsyncTask extends AsyncTask<Void, Void, ArrayList<MediaFolderModel>> {
//        ArrayList<MediaFolderModel> mediaFolderModels = new ArrayList<>();


        @Override
        protected ArrayList<MediaFolderModel> doInBackground(Void... params) {
            return fetchMediaFolder();
//            return mediaFolderModels;
        }

        @Override
        protected void onPostExecute(ArrayList<MediaFolderModel> mediaFolderModels) {
//            super.onPostExecute(mediaFolderModels);
            if (mediaFolderModels == null) {
                Toast.makeText(getActivity(), "没有可供选择的图片", Toast.LENGTH_SHORT).show();
            } else {
                mediaAdapter = new MediaFolderAdapter(getActivity(), mediaFolderModels);
                recyclerView.setAdapter(mediaAdapter);
            }
        }
    }

    public ArrayList<MediaFolderModel> fetchMediaFolder() {

        ArrayList<MediaFolderModel> mediaFolderModels = new ArrayList<>();
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;
        String tempMediaFolderName = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

//        String orderBy = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
        String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getActivity().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
        if (cursor.getCount() == 0) {
            return null;
        }
//        cursor = getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        cursor.moveToNext();
        // 下面这两句一定要在 cursor.moveToNext();  之后执行
        tempMediaFolderName = cursor.getString(column_index_folder_name); // 文件夹
        absolutePathOfImage = cursor.getString(column_index_data);  // 路径

        mediaFolderModels.add(new MediaFolderModel("latest", 25, absolutePathOfImage));
//        mediaFolderModels.add(new MediaFolderModel(tempMediaFolderName, 25, absolutePathOfImage));
        cursor.close();
//        projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        // 第二次查询
        orderBy = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
//        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getActivity().getContentResolver().query(uri, projection, null, null, orderBy);
//        cursor = getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        cursor.moveToNext();

        tempMediaFolderName = cursor.getString(column_index_folder_name); // 文件夹
        absolutePathOfImage = cursor.getString(column_index_data);  // 路径
        MediaFolderModel tempMediaFolderModel = new MediaFolderModel(tempMediaFolderName, 1, absolutePathOfImage);
        mediaFolderModels.add(tempMediaFolderModel);


        int folderItemIndex = 0;
        while (cursor.moveToNext()) {
            String temp = cursor.getString(column_index_folder_name);
            if (tempMediaFolderName.equals(temp)) { // 如果还是上一个文件夹
                folderItemIndex++;
                tempMediaFolderModel.setMediaItemSum(folderItemIndex + 1);
            } else {  // 如果不是原来的文件夹了
//                mediaFolderModels.add(new MediaFolderModel(tempMediaFolderName,folderItemIndex+1,absolutePathOfImage));
                tempMediaFolderName = cursor.getString(column_index_folder_name); // 文件夹
                absolutePathOfImage = cursor.getString(column_index_data);  // 路径
                tempMediaFolderModel = new MediaFolderModel(tempMediaFolderName, 1, absolutePathOfImage);
                mediaFolderModels.add(tempMediaFolderModel);

                folderItemIndex = 0;  // 重新设置元素索引
            }

        }

//        Log.d("WQWQ", String.valueOf(mediaFolderModels.size()));
//        Log.d("WQWQ", "+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
//
//        for (MediaFolderModel mediaFolderModel : mediaFolderModels) {
//            Log.d("WQWQ", mediaFolderModel.getFolderName());
//            Log.d("WQWQ", String.valueOf(mediaFolderModel.getMediaItemSum()));
//            Log.d("WQWQ", mediaFolderModel.getFirstItemImage());
//            Log.d("WQWQ", "+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+");
//        }

        cursor.close();
        return mediaFolderModels;
    }

}
