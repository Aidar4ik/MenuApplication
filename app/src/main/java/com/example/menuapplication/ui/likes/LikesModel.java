package com.example.menuapplication.ui.likes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LikesModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String price;

    public LikesModel(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
