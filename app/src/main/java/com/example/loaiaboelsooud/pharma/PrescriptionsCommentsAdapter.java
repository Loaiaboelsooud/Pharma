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
    private String uploaderName, uploaderAvatar, createdAt, prescriptionsImage;
    private static final int prescriptionsCommentsType = 0;
    private static final int prescriptionsImageType = 1;
    private OnPrescriptionsImageClickListener onPrescriptionsImageClickListener;

    public PrescriptionsCommentsAdapter(Context context, OnPrescriptionsImageClickListener onPrescriptionsImageClickListener, List<PrescriptionsComments> prescriptionsComments, String uploaderName, String uploaderAvatar, String createdAt,
                                        String prescriptionsImage) {
        this.context = context;
        this.prescriptionsComments = prescriptionsComments;
        this.uploaderName = uploaderName;
        this.uploaderAvatar = uploaderAvatar;
        this.createdAt = createdAt;
        this.prescriptionsImage = prescriptionsImage;
        this.onPrescriptionsImageClickListener = onPrescriptionsImageClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == prescriptionsCommentsType) {
            view = LayoutInflater.from(context).inflate(R.layout.prescriptions_comments_adapter_content, parent, false);
            return new PrescriptionsCommentsHolder(view);
        } else if (viewType == prescriptionsImageType) {
            view = LayoutInflater.from(context).inflate(R.layout.prescriptions_image_adapter_content, parent, false);
            return new PrescriptionsImageHolder(view, onPrescriptionsImageClickListener);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        if (itemType == 1) {
            ((PrescriptionsImageHolder) holder).uploaderName.setText(uploaderName);
            Glide.with(context).load(uploaderAvatar).
                    into(((PrescriptionsImageHolder) holder).uploaderAvatar);
            ((PrescriptionsImageHolder) holder).createdAt.setText(createdAt);
            Glide.with(context).load(prescriptionsImage).
                    into(((PrescriptionsImageHolder) holder).prescriptionImage);
        } else {
            final PrescriptionsComments prescriptionsComment = prescriptionsComments.get(position);
            ((PrescriptionsCommentsHolder) holder).commenterName.setText(prescriptionsComment.getUserResponse().getUser().getName());
            Glide.with(context).load((prescriptionsComment.getUserResponse().getUser().getAvatar())).
                    into(((PrescriptionsCommentsHolder) holder).commenterAvatar);
            ((PrescriptionsCommentsHolder) holder).description.setText(prescriptionsComment.getComment());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return prescriptionsImageType;
        } else {
            return prescriptionsCommentsType;
        }
    }

    @Override
    public int getItemCount() {
        if (prescriptionsComments != null)
            return prescriptionsComments.size();
        return 0;
    }

    public class PrescriptionsCommentsHolder extends RecyclerView.ViewHolder {
        private TextView commenterName, description;
        private ImageView commenterAvatar;

        public PrescriptionsCommentsHolder(View prescriptionsView) {
            super(prescriptionsView);
            commenterName = prescriptionsView.findViewById(R.id.prescription_commenter_name);
            commenterAvatar = prescriptionsView.findViewById(R.id.prescription_commenter_profile_picture);
            description = prescriptionsView.findViewById(R.id.presecription_comment);
        }
    }

    public class PrescriptionsImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView uploaderName, createdAt;
        private ImageView uploaderAvatar, prescriptionImage;
        private OnPrescriptionsImageClickListener onPrescriptionsImageClickListener;

        public PrescriptionsImageHolder(View prescriptionsImageView, OnPrescriptionsImageClickListener onPrescriptionsImageClickListener) {
            super(prescriptionsImageView);
            this.onPrescriptionsImageClickListener = onPrescriptionsImageClickListener;
            uploaderName = prescriptionsImageView.findViewById(R.id.prescription_uploader_name);
            uploaderAvatar = prescriptionsImageView.findViewById(R.id.prescription_uploader_profile_picture);
            createdAt = prescriptionsImageView.findViewById(R.id.prescription_created_at);
            prescriptionImage = prescriptionsImageView.findViewById(R.id.prescription_image);
            prescriptionsImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPrescriptionsImageClickListener.onPrescriptionsImageClick(prescriptionsImage);
        }
    }

    public interface OnPrescriptionsImageClickListener {
        void onPrescriptionsImageClick(String imageURI);
    }
}