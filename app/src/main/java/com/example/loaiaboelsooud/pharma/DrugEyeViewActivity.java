package com.example.loaiaboelsooud.pharma;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

public class DrugEyeViewActivity extends NavMenuInt {
    private TextView id, name, activeIngredients, newPrice, oldPrice, company, category, packageSize, uses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_eye_view);
        intNavToolBar();
        name = findViewById(R.id.drug_eye_name);
        activeIngredients = findViewById(R.id.drug_eye_active_ingredients);
        newPrice = findViewById(R.id.drug_eye_new_price);
        oldPrice = findViewById(R.id.drug_eye_old_price);
        company = findViewById(R.id.drug_eye_company);
        category = findViewById(R.id.drug_eye_category);
        packageSize = findViewById(R.id.drug_eye_package_size);
        uses = findViewById(R.id.drug_eye_uses);
        final DrugItemDao drugItemDao = RoomDatabaseClient.getInstance().drugItemDao();
        DrugEyeAsyncTasks.getDrugEyeItem(getIntent().getStringExtra("drug_name"), drugItemDao).observe(this, new Observer<DrugEyeItem>() {
            @Override
            public void onChanged(@Nullable DrugEyeItem drugEyeItem) {
                name.setText(drugEyeItem.getName());
                activeIngredients.setText(drugEyeItem.getActiveIngredients());
                oldPrice.setText(String.valueOf(drugEyeItem.getOldPrice()));
                newPrice.setText(String.valueOf(drugEyeItem.getNewPrice()));
                company.setText(drugEyeItem.getCompany());
                category.setText(drugEyeItem.getCategory());
                packageSize.setText(drugEyeItem.getPackageSize());
                uses.setText(drugEyeItem.getUses());
            }
        });

    }
}
