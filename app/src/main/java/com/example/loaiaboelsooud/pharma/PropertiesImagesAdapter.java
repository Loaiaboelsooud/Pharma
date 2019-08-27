package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PropertiesImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private PropertiesImage propertiesImages;
    private User user;
    private OnPropertiesClickListener onPropertiesClickListener;

    public PropertiesImagesAdapter(Context context, PropertiesImage propertiesImages, OnPropertiesClickListener onPropertiesClickListener) {

        this.context = context;
        this.propertiesImages = propertiesImages;
        this.onPropertiesClickListener = onPropertiesClickListener;
    }

    @Override
    public PropertiesImagesAdapter.Properties onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.properties_images_adapter_content, parent, false);
        PropertiesImagesAdapter.Properties properties = new PropertiesImagesAdapter.Properties(row, onPropertiesClickListener);
        return properties;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final PropertiesImage propertiesImages = this.propertiesImages;
        if (propertiesImages.getData().size() != 0) {
            Glide.with(context).load((propertiesImages.getData().get(position).getUrl())).placeholder(R.drawable.ic_loading).
                    into(((PropertiesImagesAdapter.Properties) holder).picture);
        } else {
            Glide.with(context).clear(((PropertiesImagesAdapter.Properties) holder).picture);
        }

    }

    @Override
    public int getItemCount() {
        if (propertiesImages != null)
            return propertiesImages.getData().size();
        return 0;
    }

    public class Properties extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView picture;
        private OnPropertiesClickListener onPropertiesClickListener;


        public Properties(View propertiesView, OnPropertiesClickListener propertiesClickListener) {
            super(propertiesView);
            this.onPropertiesClickListener = propertiesClickListener;
            picture = propertiesView.findViewById(R.id.properties_image_adapter_picture);
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
