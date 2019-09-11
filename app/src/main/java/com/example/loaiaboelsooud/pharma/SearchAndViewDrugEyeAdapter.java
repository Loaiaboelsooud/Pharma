
package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SearchAndViewDrugEyeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DrugEyeItem> drugEyeItems;
    private OnDrugEyeClickListener onDrugEyeClickListener;
    private PharmaConstants pharmaConstants;

    public SearchAndViewDrugEyeAdapter(Context context, List<DrugEyeItem> drugEyeItems, OnDrugEyeClickListener onDrugEyeClickListener) {
        this.context = context;
        this.drugEyeItems = drugEyeItems;
        this.onDrugEyeClickListener = onDrugEyeClickListener;
    }

    @Override
    public SearchAndViewDrugEyeAdapter.DrugEye onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        PharmaConstants pharmaConstants = new PharmaConstants(this.context);
        View row = inflater.inflate(R.layout.drug_eye_adapter_content, parent, false);
        SearchAndViewDrugEyeAdapter.DrugEye drugEye = new SearchAndViewDrugEyeAdapter.DrugEye(row, onDrugEyeClickListener);
        return drugEye;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final DrugEyeItem drugEyeItem = drugEyeItems.get(position);
        ((DrugEye) holder).name.setText(String.valueOf(drugEyeItem.getId()));
        ((DrugEye) holder).name.setText(drugEyeItem.getName());
        ((DrugEye) holder).activeIngredients.setText(drugEyeItem.getActiveIngredients());
        ((DrugEye) holder).oldPrice.setText(String.valueOf(drugEyeItem.getOldPrice()));
        ((DrugEye) holder).newPrice.setText(String.valueOf(drugEyeItem.getNewPrice()));
        ((DrugEye) holder).company.setText(drugEyeItem.getCompany());
        ((DrugEye) holder).category.setText(drugEyeItem.getCategory());
        ((DrugEye) holder).packageSize.setText(drugEyeItem.getPackageSize());
        ((DrugEye) holder).uses.setText(drugEyeItem.getUses());
    }

    @Override
    public int getItemCount() {
        if (drugEyeItems != null)
            return drugEyeItems.size();
        return 0;
    }

    public class DrugEye extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView id, name, activeIngredients, newPrice, oldPrice, company, category, packageSize, uses;
        private OnDrugEyeClickListener onDrugEyeClickListener;


        public DrugEye(View drugEyeView, OnDrugEyeClickListener onDrugEyeClickListener) {
            super(drugEyeView);
            this.onDrugEyeClickListener = onDrugEyeClickListener;
            id = drugEyeView.findViewById(R.id.drug_eye_adapter_id);
            name = drugEyeView.findViewById(R.id.drug_eye_adapter_name);
            activeIngredients = drugEyeView.findViewById(R.id.drug_eye_adapter_active_ingredients);
            newPrice = drugEyeView.findViewById(R.id.drug_eye_adapter_new_price);
            oldPrice = drugEyeView.findViewById(R.id.drug_eye_adapter_old_price);
            company = drugEyeView.findViewById(R.id.drug_eye_adapter_company);
            category = drugEyeView.findViewById(R.id.drug_eye_adapter_category);
            packageSize = drugEyeView.findViewById(R.id.drug_eye_adapter_package_size);
            uses = drugEyeView.findViewById(R.id.drug_eye_adapter_uses);
            drugEyeView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDrugEyeClickListener.onDrugEyeClick(getAdapterPosition());
        }
    }

    public interface OnDrugEyeClickListener {
        void onDrugEyeClick(int drugEyePosition);
    }

}
