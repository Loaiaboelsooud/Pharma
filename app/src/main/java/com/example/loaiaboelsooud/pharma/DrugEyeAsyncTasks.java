package com.example.loaiaboelsooud.pharma;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DrugEyeAsyncTasks {

    public static void insert(DrugEyeItem drugEyeItem, DrugItemDao drugItemDao) {
        new DrugEyeAsyncTasks.InsertDrugEyeItem(drugItemDao).execute(drugEyeItem);
    }

    public static LiveData<List<DrugEyeItem>> getAlternatives(String drugName, DrugItemDao drugItemDao) {

        return drugItemDao.findAlternatives(drugName);
    }

    public static LiveData<List<DrugEyeItem>> getAll(DrugItemDao drugItemDao) {

        return drugItemDao.getAll();
    }

    public static class InsertDrugEyeItem extends AsyncTask<DrugEyeItem, Void, Void> {
        private DrugItemDao drugItemDao;

        public InsertDrugEyeItem(DrugItemDao drugItemDao) {
            this.drugItemDao = drugItemDao;
        }

        @Override
        protected Void doInBackground(DrugEyeItem... drugEyeItems) {

            drugItemDao.insert(drugEyeItems[0]);
            return null;
        }
    }

    public static class FindAlternatives extends AsyncTask<String, DrugEyeItem, Void> {
        private DrugItemDao drugItemDao;

        public FindAlternatives(DrugItemDao drugItemDao) {
            this.drugItemDao = drugItemDao;
        }

        @Override
        protected Void doInBackground(String... drugName) {

            drugItemDao.findAlternatives(drugName[0]);
            return null;
        }

    }
}
