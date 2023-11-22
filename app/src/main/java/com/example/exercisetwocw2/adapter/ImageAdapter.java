package com.example.exercisetwocw2.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.exercisetwocw2.R;
import com.example.exercisetwocw2.database.model.ImageTable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private RequestOptions requestOptions = new RequestOptions();


    private ArrayList<ImageTable> listImage = new ArrayList<>();

    public ImageAdapter(ArrayList<ImageTable> images) {
        this.listImage = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(30));
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageTable item = listImage.get(position);
        if (item.imageFilePath.isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(item.imgResFromAndroidRes).apply(requestOptions).into(holder.imageView);
        } else {
            File imgFile = new File(item.imageFilePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(holder.itemView.getContext())
                        .asBitmap()
                        .load(stream.toByteArray()).apply(requestOptions).into(holder.imageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgView);
        }
    }
}
