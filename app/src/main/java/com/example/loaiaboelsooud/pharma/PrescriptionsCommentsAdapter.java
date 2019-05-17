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

public class PrescriptionsCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PrescriptionsComments> prescriptionsComments;

    public PrescriptionsCommentsAdapter(Context context, List<PrescriptionsComments> prescriptionsComments) {
        this.context = context;
        this.prescriptionsComments = prescriptionsComments;
    }

    @Override
    public PrescriptionsCommentsAdapter.Prescriptions onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.prescriptions_comments_adapter_content, parent, false);
        Prescriptions prescriptions = new Prescriptions(row);
        return prescriptions;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PrescriptionsComments prescriptionsComment = prescriptionsComments.get(position);
        ((Prescriptions) holder).uploaderName.setText(prescriptionsComment.getUserResponse().getUser().getName());
        Glide.with(context).load((prescriptionsComment.getUserResponse().getUser().getAvatar())).
                into(((Prescriptions) holder).uploaderAvatar);
        ((Prescriptions) holder).description.setText(prescriptionsComment.getComment());
    }

    @Override
    public int getItemCount() {
        if (prescriptionsComments != null)
            return prescriptionsComments.size();
        return 0;
    }

    public class Prescriptions extends RecyclerView.ViewHolder {
        private TextView uploaderName;
        private ImageView uploaderAvatar;
        private TextView description;

        public Prescriptions(View prescriptionsView) {
            super(prescriptionsView);
            uploaderName = prescriptionsView.findViewById(R.id.presecription_user_name);
            uploaderAvatar = prescriptionsView.findViewById(R.id.presecription_user_profile_picture);
            description = prescriptionsView.findViewById(R.id.presecription_comment);
        }
    }
}