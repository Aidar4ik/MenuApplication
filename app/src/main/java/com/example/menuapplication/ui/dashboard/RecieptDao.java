package com.example.menuapplication.ui.dashboard;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.menuapplication.ui.price.PriceModel;

import java.util.List;

@Dao
public interface RecieptDao {

    @Insert
    void insert(RecieptModel reciept);

    @Query("DELETE FROM RecieptModel WHERE id=:idReciept")
    void delete(int idReciept);

    @Query("DELETE FROM RecieptModel")
    void deleteAll();

    @Query("SELECT * FROM recieptmodel")
    List<RecieptModel> getAll();
}
