package com.example.loaiaboelsooud.pharma;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DrugEyeAsyncTasks {

    public static void insert(DrugEyeItem drugEyeItem, DrugItemDao drugItemDao, OnInsertCompleted onInsertCompleted) {
        new DrugEyeAsyncTasks.InsertDrugEyeItem(drugItemDao, onInsertCompleted).execute(drugEyeItem);
    }

    public static void insertAll(List<DrugEyeItem> drugEyeItemList, DrugItemDao drugItemDao, OnInsertCompleted onInsertCompleted) {
        new DrugEyeAsyncTasks.InsertDrugEyeItems(drugItemDao, onInsertCompleted).execute(drugEyeItemList);
    }
    public static LiveData<List<DrugEyeItem>> getAlternatives(String drugName, DrugItemDao drugItemDao) {

        return drugItemDao.findAlternatives("%" + drugName.toUpperCase() + "%");
    }

    public static LiveData<List<DrugEyeItem>> getSimilarities(String drugName, DrugItemDao drugItemDao) {

        return drugItemDao.findSimilarities("%" + drugName.toUpperCase() + "%");
    }

    public static LiveData<List<DrugEyeItem>> getDrugEyeItems(String drugName, DrugItemDao drugItemDao) {

        return drugItemDao.findDrugItems(  drugName.toUpperCase() + "%");
    }

    public static LiveData<DrugEyeItem> getDrugEyeItem(String drugName, DrugItemDao drugItemDao) {

        return drugItemDao.findDrugItem(drugName.toUpperCase());
    }

    public static void deleteAll(DrugItemDao drugItemDao) {
        new DrugEyeAsyncTasks.DeleteDrugEyeItems(drugItemDao).execute();
    }

    public static LiveData<List<DrugEyeItem>> getAll(DrugItemDao drugItemDao) {

        return drugItemDao.getAll();
    }

    public static class InsertDrugEyeItem extends AsyncTask<DrugEyeItem, Void, OnInsertCompleted> {
        private DrugItemDao drugItemDao;
        private OnInsertCompleted onInsertCompleted;

        public InsertDrugEyeItem(DrugItemDao drugItemDao, OnInsertCompleted onInsertCompleted) {
            this.drugItemDao = drugItemDao;
            this.onInsertCompleted = onInsertCompleted;
        }

        @Override
        protected OnInsertCompleted doInBackground(DrugEyeItem... drugEyeItemList) {

            for (int i = 0; i < drugEyeItemList.length; i++) {
                drugItemDao.insert(drugEyeItemList[i]);
            }
            return null;
        }

        protected void onPostExecute(OnInsertCompleted onInsertCompleted) {
            this.onInsertCompleted.insertSuccess();
        }
    }

    public static class InsertDrugEyeItems extends AsyncTask<List<DrugEyeItem>, Void, OnInsertCompleted> {
        private DrugItemDao drugItemDao;
        private OnInsertCompleted onInsertCompleted;

        public InsertDrugEyeItems(DrugItemDao drugItemDao, OnInsertCompleted onInsertCompleted) {
            this.drugItemDao = drugItemDao;
            this.onInsertCompleted = onInsertCompleted;
        }

        @Override
        protected OnInsertCompleted doInBackground(List<DrugEyeItem>... lists) {
            for (int i = 0; i < lists.length; i++) {
                drugItemDao.insertAllDrugEyeItems(lists[i]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(OnInsertCompleted onInsertCompleted) {
            this.onInsertCompleted.insertSuccess();
        }
    }

    public static class DeleteDrugEyeItems extends AsyncTask<Integer, Void, Void> {
        private DrugItemDao drugItemDao;

        public DeleteDrugEyeItems(DrugItemDao drugItemDao) {
            this.drugItemDao = drugItemDao;
        }

        @Override
        protected Void doInBackground(Integer... ints) {
            drugItemDao.deleteAll();
            return null;
        }
    }

    public interface OnInsertCompleted {
        void insertSuccess();
    }

}
