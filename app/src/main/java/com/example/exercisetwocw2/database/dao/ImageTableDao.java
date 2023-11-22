package com.example.exercisetwocw2.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.exercisetwocw2.database.model.ImageTable;

import java.util.List;

@Dao
public interface ImageTableDao {
    @Insert
    void insert(ImageTable imageTable);

    @Query("SELECT * FROM ImageTable")
    List<ImageTable> getAllImage();
}
