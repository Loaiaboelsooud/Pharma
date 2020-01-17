package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PropertiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PropertiesItem> propertiesItems;
    private User user;
    private OnPropertiesClickListener onPropertiesClickListener;
    private PharmaConstants pharmaConstants;

    public PropertiesAdapter(Context context, List<PropertiesItem> propertiesItems, OnPropertiesClickListener onPropertiesClickListener) {
        this.context = context;
        this.propertiesItems = propertiesItems;
        this.onPropertiesClickListener = onPropertiesClickListener;

    }

    @Override
    public PropertiesAdapter.Properties onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        new PharmaConstants(this.context);
        View row = inflater.inflate(R.layout.properties_adapter_content, parent, false);
        PropertiesAdapter.Properties properties = new PropertiesAdapter.Properties(row, onPropertiesClickListener);

        return properties;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final PropertiesItem propertiesItem = propertiesItems.get(position);
        ((Properties) holder).uploaderName.setText(propertiesItem.getUserResponse().getUser().getName());
        Glide.with(context).load((propertiesItem.getUserResponse().getUser().getAvatar() + "picture?width=250&height=250")).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(((Properties) holder).uploaderAvatar);
        if (propertiesItem.getImages().getData().size() != 0) {
            Glide.with(context).load((propertiesItem.getImages().getData().get(0).getUrl())).placeholder(R.drawable.ic_loading).
                    into(((PropertiesAdapter.Properties) holder).picture);
        } else {
            Glide.with(context).clear(((PropertiesAdapter.Properties) holder).picture);
        }

        if (pharmaConstants.listedForMapView.get(propertiesItem.getListedFor()) != null) {
            if (pharmaConstants.listedForMapView.get(propertiesItem.getListedFor()).equals(pharmaConstants.listedForArray[0])) {
                ((Properties) holder).listedFor.setTextColor(Color.parseColor("#EF0F45"));
                ((Properties) holder).priceUnit.setText(context.getResources().getString(R.string.LE));
            } else if (pharmaConstants.listedForMapView.get(propertiesItem.getListedFor()).equals(pharmaConstants.listedForArray[1])) {
                ((Properties) holder).listedFor.setTextColor(Color.parseColor("#ffb300"));
                ((Properties) holder).priceUnit.setText(context.getResources().getString(R.string.LE_Month));
            }
        }
        ((Properties) holder).propertiesName.setText(propertiesItem.getName().toUpperCase());
        ((Properties) holder).listedFor.setText(pharmaConstants.listedForMapView.get(propertiesItem.getListedFor()));
        ((Properties) holder).type.setText(pharmaConstants.typeMapView.get(propertiesItem.getType()));
        ((Properties) holder).updatedAt.setText(PrefUtil.splitDateTime(propertiesItem.getUpdatedAt()));
        ((Properties) holder).price.setText(String.valueOf(propertiesItem.getPrice()));
        ((Properties) holder).city.setText(pharmaConstants.citiesMapView.get(propertiesItem.getCity()));
        ((Properties) holder).region.setText(pharmaConstants.regionsMapView.get(propertiesItem.getRegion()));

        if (pharmaConstants.statusMapView.get(propertiesItem.getStatus()) != null) {
            if (propertiesItem.getStatus().equals(pharmaConstants.CLOSED)) {
                ((Properties) holder).status.setTextColor(Color.parseColor("#EF0F45"));
            } else if (propertiesItem.getStatus().equals(pharmaConstants.OPENED)) {
                ((Properties) holder).status.setTextColor(Color.parseColor("#a7bd56"));
            }
            ((Properties) holder).status.setText(pharmaConstants.statusMapView.get(propertiesItem.getStatus()));
        }

        if (propertiesItem.getArea() != null) {
            ((Properties) holder).area.setText(propertiesItem.getArea());
        } else {
            ((Properties) holder).areaLayout.setVisibility(View.GONE);
        }
        if (propertiesItem.getAverageDailyIncome() > 1) {
            ((Properties) holder).averageDailyIncome.setText(String.valueOf(propertiesItem.getAverageDailyIncome()));
        } else {
            ((Properties) holder).averageDailyIncomeLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        if (propertiesItems != null)
            return propertiesItems.size();
        return 0;
    }

    public class Properties extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView uploaderName, propertiesName, listedFor, type, updatedAt, city, region, area, price, averageDailyIncome, status, priceUnit;
        private ImageView uploaderAvatar, picture;
        private LinearLayout averageDailyIncomeLayout, areaLayout;
        private OnPropertiesClickListener onPropertiesClickListener;

        public Properties(View propertiesView, OnPropertiesClickListener propertiesClickListener) {
            super(propertiesView);
            this.onPropertiesClickListener = propertiesClickListener;
            uploaderName = propertiesView.findViewById(R.id.properties_user_name);
            uploaderAvatar = propertiesView.findViewById(R.id.properties_user_profile_picture);
            updatedAt = propertiesView.findViewById(R.id.properties_adapter_updated_at);
            picture = propertiesView.findViewById(R.id.properties_adapter_image);
            propertiesName = propertiesView.findViewById(R.id.properties_adapter_name);
            type = propertiesView.findViewById(R.id.properties_adapter_type);
            listedFor = propertiesView.findViewById(R.id.properties_adapter_listed_for);
            area = propertiesView.findViewById(R.id.properties_adapter_area);
            city = propertiesView.findViewById(R.id.properties_adapter_city);
            region = propertiesView.findViewById(R.id.properties_adapter_region);
            price = propertiesView.findViewById(R.id.properties_adapter_price);
            averageDailyIncome = propertiesView.findViewById(R.id.properties_adapter_average_daily_income);
            status = propertiesView.findViewById(R.id.properties_adapter_status);
            areaLayout = propertiesView.findViewById(R.id.properties_adapter_area_layout);
            averageDailyIncomeLayout = propertiesView.findViewById(R.id.properties_adapter_average_daily_income_layout);
            priceUnit = propertiesView.findViewById(R.id.properties_adapter_price_Unit);
            propertiesView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPropertiesClickListener.onPropertiesClick(getAdapterPosition());
        }
    }

    public interface OnPropertiesClickListener {
        void onPropertiesClick(int propertiesPosition);
    }

}
