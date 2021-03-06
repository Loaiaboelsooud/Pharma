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

public class PrescriptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PrescriptionsItem> prescriptionsItems;
    private OnPrescriptionsClickListener onPrescriptionsClickListener;

    public PrescriptionsAdapter(Context context, List<PrescriptionsItem> prescriptionsItems, OnPrescriptionsClickListener onPrescriptionsClickListener) {
        this.context = context;
        this.prescriptionsItems = prescriptionsItems;
        this.onPrescriptionsClickListener = onPrescriptionsClickListener;
    }

    @Override
    public PrescriptionsAdapter.Prescriptions onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.prescriptions_adapter_content, parent, false);
        return new Prescriptions(row, onPrescriptionsClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PrescriptionsItem prescriptionsItem = prescriptionsItems.get(position);
        ((Prescriptions) holder).uploaderName.setText(prescriptionsItem.getUserResponse().getUser().getName());
        Glide.with(context).load((prescriptionsItem.getUserResponse().getUser().getAvatar() + "picture?width=250&height=250")).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(((Prescriptions) holder).uploaderAvatar);
        Glide.with(context).load((prescriptionsItem.getImage())).placeholder(R.drawable.ic_loading).
                into(((Prescriptions) holder).picture);
        ((Prescriptions) holder).description.setText(prescriptionsItem.getDescription());
        ((Prescriptions) holder).createdAt.setText(PrefUtil.splitDateTime(prescriptionsItem.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        if (prescriptionsItems != null)
            return prescriptionsItems.size();
        return 0;
    }

    public class Prescriptions extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView uploaderName, description, createdAt;
        private ImageView uploaderAvatar, picture;
        private OnPrescriptionsClickListener onPrescriptionsClickListener;

        public Prescriptions(View prescriptionsView, OnPrescriptionsClickListener onPrescriptionsClickListener) {
            super(prescriptionsView);
            this.onPrescriptionsClickListener = onPrescriptionsClickListener;
            uploaderName = prescriptionsView.findViewById(R.id.prescription_user_name);
            uploaderAvatar = prescriptionsView.findViewById(R.id.prescription_user_profile_picture);
            picture = prescriptionsView.findViewById(R.id.prescription_image);
            description = prescriptionsView.findViewById(R.id.presecription_description);
            createdAt = prescriptionsView.findViewById(R.id.prescription_created_at);
            prescriptionsView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPrescriptionsClickListener.onPrescriptionsClick(getAdapterPosition());
        }
    }

    public interface OnPrescriptionsClickListener {
        void onPrescriptionsClick(int prescriptionPosition);
    }
}