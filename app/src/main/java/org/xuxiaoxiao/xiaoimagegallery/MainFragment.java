package org.xuxiaoxiao.xiaoimagegallery;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by WuQiang on 2017/4/30.
 */

public class MainFragment extends Fragment {
    boolean boolean_folder;

    RecyclerView recyclerView;
    MediaAdapter mediaAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Model_images> mediaFileInfos = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_folder, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.media_folder_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        new MediaAsyncTask().execute("image");

        return view;
    }

    public class MediaAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            String type = params[0];
            try {
                mediaFileInfos = new ArrayList<>();
                fetchMedia();
                result = 1;

            } catch (Exception e) {
                e.printStackTrace();
                result = 0;
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            // setProgressBarIndeterminateVisibility(false);

            /* Download complete. Lets update UI */
            if (result == 1) {
                mediaAdapter = new MediaAdapter(getActivity(),mediaFileInfos);
                recyclerView.setAdapter(mediaAdapter);
            } else {
                Log.e("WQWQ", "Failed to fetch data!");
            }
        }
    }

    public Void fetchMedia() {
//    public ArrayList<Model_images> fetchMedia() {
        mediaFileInfos.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data,
                column_index_folder_name;
//                column_index_date_taken,
//                column_index_description,
//                column_index_displayName,
//                colunm_index_mimeType;


        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
//                MediaStore.Images.Media.DATE_TAKEN,
//                MediaStore.Images.Media.DESCRIPTION,
//                MediaStore.Images.Media.DISPLAY_NAME,
//                MediaStore.Images.Media.MIME_TYPE
        };

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getActivity().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");
        Log.d("WQWQ_ColumnCount",String.valueOf(cursor.getColumnCount()));
        Log.d("WQWQ_Count",String.valueOf(cursor.getCount()));
//        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//        column_index_date_taken = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
//        column_index_description = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DESCRIPTION);
//        column_index_displayName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
//        colunm_index_mimeType = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE);


        ArrayList<String> latest = new ArrayList<>();


        int lastestIndex = 0;
        Model_images latest_model = new Model_images();
        latest_model.setStr_folder("Latest");  // 创建一个名为"Latest"的文件夹
        mediaFileInfos.add(latest_model);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);  // 路径
//            Log.d("path", absolutePathOfImage);
//            Log.d("bucket", cursor.getString(column_index_folder_name));
//            Log.d("date", cursor.getString(column_index_date_taken));
//            Log.d("name", cursor.getString(column_index_displayName));
//            Log.d("type", cursor.getString(colunm_index_mimeType));
//            Log.d("des", cursor.getString(column_index_description));
            if (lastestIndex < 20) {
                latest.add(absolutePathOfImage); // 完整路径
            }
            lastestIndex++;


            for (int i = 0; i < mediaFileInfos.size(); i++) {
//                Log.d("size", String.valueOf(mediaFileInfos.size()));
                if (mediaFileInfos.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) { // 如果是文件夹
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }
            }


            if (boolean_folder) {

                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(mediaFileInfos.get(int_position).getAl_imagepath()); // 把原来的都读取出来
                al_path.add(absolutePathOfImage); // （ 完整路径 ） 把新的这个也加上
                mediaFileInfos.get(int_position).setAl_imagepath(al_path);  // 全部赋予

            } else {  // 如果不是文件夹，那就再创建一个新的文件夹
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage); // 完整路径
                Model_images obj_model = new Model_images();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));  // 创建一个文件夹
                obj_model.setAl_imagepath(al_path);

                mediaFileInfos.add(obj_model);
            }


        }

        latest_model.setAl_imagepath(latest);// 把最新的图片 .....
        latest = null;
//        lastestIndex = null;

//        for (int i = 0; i < mediaFileInfos.size(); i++) {
//            Log.e("FOLDER", mediaFileInfos.get(i).getStr_folder());
//            for (int j = 0; j < mediaFileInfos.get(i).getAl_imagepath().size(); j++) {
//                Log.e("FILE", mediaFileInfos.get(i).getAl_imagepath().get(j));
//            }
//        }
//        obj_adapter = new Adapter_PhotosFolder(getApplicationContext(), mediaFileInfos);
//        gv_folder.setAdapter(obj_adapter);
//        return mediaFileInfos;
        return null;
    }
}
