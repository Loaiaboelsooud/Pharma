package com.example.loaiaboelsooud.pharma;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class DrugEyeItem {
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    int id;
    @SerializedName("Drug Name")
    @ColumnInfo(name = "Drug Name")
    String name;
    @SerializedName("Active Ingredients")
    @ColumnInfo(name = "Active Ingredients")
    String activeIngredients;
    @SerializedName("New Price")
    @ColumnInfo(name = "New Price")
    float newPrice;
    @SerializedName("Old Price")
    @ColumnInfo(name = "Old Price")
    float oldPrice;
    @SerializedName("Company")
    @ColumnInfo(name = "Company")
    String company;
    @SerializedName("Category")
    @ColumnInfo(name = "Category")
    String category;
    @SerializedName("Package Size")
    @ColumnInfo(name = "Package Size")
    String packageSize;
    @SerializedName("Uses")
    @ColumnInfo(name = "Uses")
    String uses;

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

    public String getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(String activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }
}
