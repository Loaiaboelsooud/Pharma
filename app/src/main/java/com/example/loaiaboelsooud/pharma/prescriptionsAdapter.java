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

public class prescriptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private List<PrescriptionsItem> prescriptionsItems;
    User user;

    public prescriptionsAdapter(Context context, List<PrescriptionsItem> prescriptionsItems) {

        this.context = context;
        this.prescriptionsItems = prescriptionsItems;

    }

    @Override
    public prescriptionsAdapter.Prescriptions onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.prescriptions_adapter_content, parent, false);
        Prescriptions prescriptions = new Prescriptions(row);
        return prescriptions;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PrescriptionsItem prescriptionsItem = prescriptionsItems.get(position);
        ((Prescriptions) holder).uploaderName.setText(prescriptionsItem.getUserResponse().getUser().getName());
        Glide.with(context).load((prescriptionsItem.getUserResponse().getUser().getAvatar())).
                into(((Prescriptions) holder).uploaderAvatar);
        Glide.with(context).load((prescriptionsItem.getImage())).
                into(((Prescriptions) holder).picture);
        ((Prescriptions) holder).description.setText(prescriptionsItem.getDescription());

    }

    @Override
    public int getItemCount() {
        return prescriptionsItems.size();
    }

    public class Prescriptions extends RecyclerView.ViewHolder {
        TextView uploaderName;
        ImageView uploaderAvatar;
        ImageView picture;
        TextView description;


        public Prescriptions(View prescriptionsView) {
            super(prescriptionsView);
            uploaderName = prescriptionsView.findViewById(R.id.presecription_user_name);
            uploaderAvatar = prescriptionsView.findViewById(R.id.presecription_user_profile_picture);
            picture = prescriptionsView.findViewById(R.id.presecription_image);
            description = prescriptionsView.findViewById(R.id.presecription_description);

        }
    }
}