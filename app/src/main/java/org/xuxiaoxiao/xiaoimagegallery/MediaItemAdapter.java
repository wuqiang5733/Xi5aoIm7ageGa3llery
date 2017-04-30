package org.xuxiaoxiao.xiaoimagegallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by WuQiang on 2017/4/30.
 */

public class MediaItemAdapter extends RecyclerView.Adapter <MediaItemAdapter.MediaItemViewHolder>{
    Context context;
    ArrayList<String> mediaItems = new ArrayList<>();

    public MediaItemAdapter(Context context, ArrayList<String> mediaItems) {
        this.context = context;
        this.mediaItems = mediaItems;
    }

    @Override
    public MediaItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);

        return new MediaItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaItemViewHolder holder, int position) {
        holder.imagePath.setText("4567890");

    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    public class MediaItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageItem;
        TextView imagePath;
        public MediaItemViewHolder(View itemView) {
            super(itemView);
            imageItem = (ImageView)itemView.findViewById(R.id.image_item);
            imagePath = (TextView)itemView.findViewById(R.id.item_path_text_view);
        }
    }
}
