package com.example.loaiaboelsooud.pharma;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DrugItemDao {

    @Query("SELECT * FROM Drugeyeitem WHERE `Active Ingredients` = (SELECT `Active Ingredients` FROM Drugeyeitem where 'Drug Name' Like :drugName LIMIT 1 )")
    LiveData<List<DrugEyeItem>> findAlternatives(String drugName);

    @Query("SELECT * FROM Drugeyeitem WHERE `Category` = (SELECT `Active Ingredients` FROM Drugeyeitem where 'Drug Name' Like :drugName LIMIT 1 )")
    LiveData<List<DrugEyeItem>> findSimilarities(String drugName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DrugEyeItem... drugEyeItems);

    @Query("SELECT * FROM Drugeyeitem Limit 15")
    LiveData<List<DrugEyeItem>> getAll();

    @Delete
    void delete(DrugEyeItem drugEyeItem);
}
