package com.example.exercisetwocw2.database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ImageTable")
public class ImageTable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String imageFilePath;

    public int imgResFromAndroidRes;

    public ImageTable(String imageFilePath, int imgResFromAndroidRes) {
        this.imageFilePath = imageFilePath;
        this.imgResFromAndroidRes = imgResFromAndroidRes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public int getImgResFromAndroidRes() {
        return imgResFromAndroidRes;
    }

    public void setImgResFromAndroidRes(int imgResFromAndroidRes) {
        this.imgResFromAndroidRes = imgResFromAndroidRes;
    }
}
