package com.example.menuapplication.ui.likes;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.menuapplication.ui.dashboard.RecieptModel;

@Database(entities = {LikesModel.class}, version = 2)
public abstract class AddDatabaseLikes extends RoomDatabase {
    public abstract LikesDao likesDao();
}
