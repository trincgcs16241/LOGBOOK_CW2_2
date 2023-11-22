package com.example.exercisetwocw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.exercisetwocw2.adapter.ImageAdapter;
import com.example.exercisetwocw2.database.AppDatabase;
import com.example.exercisetwocw2.database.model.ImageTable;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    private ImageAdapter imageAdapter;
    private ArrayList<ImageTable> images = new ArrayList<>();

    private MaterialButton btnImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "image-database").allowMainThreadQueries().build();
        initView();
        prepareImageList();
    }

    private void initView() {
        RecyclerView rvImages = findViewById(R.id.rvImages);
        imageAdapter = new ImageAdapter(images);
        rvImages.setAdapter(imageAdapter);

        btnImport = findViewById(R.id.btnImportImage);
        btnImport.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 999);
            } else {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 777);
            }
        });
    }



    private void prepareImageList() {
        images.clear();
        images.add(new ImageTable("", R.drawable.image_sample_1));
        images.add(new ImageTable("", R.drawable.image_sample_2));
        images.add(new ImageTable("", R.drawable.image_sample_3));
        images.add(new ImageTable("", R.drawable.image_sample_4));
        images.add(new ImageTable("", R.drawable.image_sample_5));
        images.add(new ImageTable("", R.drawable.image_sample_6));
        List<ImageTable> imageModels = appDatabase.imageDao().getAllImage();
        images.addAll(imageModels);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 777 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 999);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            ImageTable imageModel = new ImageTable(filePath, 0);
            appDatabase.imageDao().insert(imageModel);
            prepareImageList();
        }
    }
}