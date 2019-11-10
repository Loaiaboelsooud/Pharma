package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        Glide.with(context).load((propertiesItem.getUserResponse().getUser().getAvatar())).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(((Properties) holder).uploaderAvatar);
        if (propertiesItem.getImages().getData().size() != 0) {
            Glide.with(context).load((propertiesItem.getImages().getData().get(0).getUrl())).placeholder(R.drawable.ic_loading).
                    into(((PropertiesAdapter.Properties) holder).picture);
        } else {
            Glide.with(context).clear(((PropertiesAdapter.Properties) holder).picture);
        }
        ((Properties) holder).propertiesName.setText(propertiesItem.getName());
        ((Properties) holder).listedFor.setText(pharmaConstants.listedForMapView.get(propertiesItem.getListedFor()));
        ((Properties) holder).type.setText(pharmaConstants.typeMapView.get(propertiesItem.getType()));
        ((Properties) holder).updatedAt.setText(PrefUtil.splitDateTime(propertiesItem.getUpdatedAt()));
    }

    @Override
    public int getItemCount() {
        if (propertiesItems != null)
            return propertiesItems.size();
        return 0;
    }

    public class Properties extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView uploaderName, propertiesName, listedFor, type, updatedAt;
        private ImageView uploaderAvatar, picture;
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
