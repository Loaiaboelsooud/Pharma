package com.example.loaiaboelsooud.pharma;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {DrugEyeItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DrugItemDao drugItemDao();
}
