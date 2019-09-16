package com.example.loaiaboelsooud.pharma;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DrugItemDao {

    @Query("SELECT * FROM Drugeyeitem WHERE `Active Ingredients` LIKE (SELECT `Active Ingredients` FROM Drugeyeitem where `Drug Name` LIKE  :drugName  LIMIT 1 )")
    LiveData<List<DrugEyeItem>> findAlternatives(String drugName);

    @Query("SELECT * FROM Drugeyeitem WHERE `Category` LIKE (SELECT `Category` FROM Drugeyeitem where `Drug Name` Like  :drugName LIMIT 1 )")
    LiveData<List<DrugEyeItem>> findSimilarities(String drugName);

    @Query("SELECT * FROM Drugeyeitem WHERE `Drug Name` LIKE :drugName")
    LiveData<List<DrugEyeItem>> findDrugItems(String drugName);

    @Query("SELECT * FROM Drugeyeitem WHERE `Drug Name` is :drugName LIMIT 1")
    LiveData<DrugEyeItem> findDrugItem(String drugName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DrugEyeItem... drugEyeItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllDrugEyeItems(List<DrugEyeItem> drugEyeItemList);

    @Query("SELECT * FROM Drugeyeitem Limit 15")
    LiveData<List<DrugEyeItem>> getAll();

    @Query("DELETE from DrugEyeItem")
    void deleteAll();


}
