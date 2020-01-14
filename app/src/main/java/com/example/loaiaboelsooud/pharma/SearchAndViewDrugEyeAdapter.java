
package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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
    private int row_index=-1;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

        final DrugEyeItem drugEyeItem = drugEyeItems.get(position);
        ((DrugEye) holder).name.setText(drugEyeItem.getName());
        ((DrugEye) holder).newPrice.setText(String.valueOf(drugEyeItem.getNewPrice()));
        ((DrugEye) holder).category.setText(drugEyeItem.getCategory());

        ((DrugEye) holder).drugEyeAdapterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            ((DrugEye) holder).drugEyeAdapterCardView.setCardBackgroundColor(Color.parseColor("#f8f8f8"));
            onDrugEyeClickListener.onDrugEyeClick(position);
        }
        else
        {
            ((DrugEye) holder).drugEyeAdapterCardView.setCardBackgroundColor(Color.parseColor("#dbdbdb"));
        }
    }

    @Override
    public int getItemCount() {
        if (drugEyeItems != null)
            return drugEyeItems.size();
        return 0;
    }

    public class DrugEye extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, newPrice, category;
        private CardView drugEyeAdapterCardView;
        private OnDrugEyeClickListener onDrugEyeClickListener;


        public DrugEye(View drugEyeView, OnDrugEyeClickListener onDrugEyeClickListener) {
            super(drugEyeView);
            this.onDrugEyeClickListener = onDrugEyeClickListener;
            name = drugEyeView.findViewById(R.id.drug_eye_adapter_name);
            newPrice = drugEyeView.findViewById(R.id.drug_eye_adapter_new_price);
            category = drugEyeView.findViewById(R.id.drug_eye_adapter_category);
            drugEyeAdapterCardView = drugEyeView.findViewById(R.id.drug_eye_adapter_card_view);
            drugEyeView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //drugEyeAdapterCardView.setCardBackgroundColor(Color.parseColor("#f8f8f8"));
            onDrugEyeClickListener.onDrugEyeClick(getAdapterPosition());
        }
    }

    public interface OnDrugEyeClickListener {
        void onDrugEyeClick(int drugEyePosition);
    }

}
