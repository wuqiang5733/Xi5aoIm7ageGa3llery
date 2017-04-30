package org.xuxiaoxiao.xiaoimagegallery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by WuQiang on 2017/4/30.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {
//    ArrayList<Model_images> mediaFileInfos = new ArrayList<>();
    ArrayList<MediaFolderModel> mediaFolderModels = new ArrayList<>();

    Context context;

    public MediaAdapter(Context context, ArrayList<MediaFolderModel> mediaFolderModels) {
        this.mediaFolderModels = mediaFolderModels;
        this.context = context;
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_floder, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        holder.folderName.setText(mediaFolderModels.get(position).getFolderName());
//        holder.tv_foldersize.setText(mediaFileInfos.get(position).getAl_imagepath().size()+"");


        Glide.with(context).load("file://" + mediaFolderModels.get(position).getFirstItemImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.folderImage);
//        Log.d("WQWQ", "file://" + mediaFileInfos.get(position).getAl_imagepath().get(0));
//        holder.bind(mediaFileInfos.get(position).getAl_imagepath());
    }

    @Override
    public int getItemCount() {
        return mediaFolderModels.size();
    }

    public class MediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView folderImage;
        TextView folderName;
        ArrayList<String> mediaItems = new ArrayList<>();

        public MediaViewHolder(View itemView) {
            super(itemView);
            folderImage = (ImageView) itemView.findViewById(R.id.media_floder_image_view);
            folderName = (TextView) itemView.findViewById(R.id.media_folder_name_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,MediaItemActiviey.class);
            context.startActivity(intent);
            EventBus.getDefault().post(new SendMediaItem(mediaItems));
        }

        public void bind(ArrayList<String> al_imagepath) {
            this.mediaItems = al_imagepath;
        }
    }
}
