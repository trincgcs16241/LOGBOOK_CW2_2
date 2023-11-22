package com.example.exercisetwocw2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.exercisetwocw2.database.dao.ImageTableDao;
import com.example.exercisetwocw2.database.model.ImageTable;

@Database(entities = {ImageTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ImageTableDao imageDao();
}
