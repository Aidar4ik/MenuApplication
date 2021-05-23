package com.example.menuapplication.ui.likes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.menuapplication.ui.dashboard.RecieptModel;

import java.util.List;

@Dao
public interface LikesDao {
    @Insert
    void insert(LikesModel likesModel);

    @Query("DELETE FROM LikesModel WHERE id=:idLikes")
    void delete(int idLikes);

    @Query("DELETE FROM LikesModel")
    void deleteAll();

    @Query("SELECT * FROM likesmodel")
    List<LikesModel> getAll();
}
