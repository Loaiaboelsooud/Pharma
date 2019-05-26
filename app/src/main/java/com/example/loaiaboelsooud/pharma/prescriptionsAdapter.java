package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class prescriptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PrescriptionsItem> prescriptionsItems;
    private User user;

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
        /*RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_pic_error)
                .priority(Priority.HIGH);
*/


        final PrescriptionsItem prescriptionsItem = prescriptionsItems.get(position);
        ((Prescriptions) holder).uploaderName.setText(prescriptionsItem.getUserResponse().getUser().getName());
        Glide.with(context).load((prescriptionsItem.getUserResponse().getUser().getAvatar())).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(((Prescriptions) holder).uploaderAvatar);
        Glide.with(context).load((prescriptionsItem.getImage())).placeholder(R.drawable.ic_loading).dontAnimate().
                into(((Prescriptions) holder).picture);
        ((Prescriptions) holder).description.setText(prescriptionsItem.getDescription());
        ((Prescriptions) holder).commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrescriptionsCommentsActivity.class);
                intent.putExtra("postId", prescriptionsItem.getId());
                intent.putExtra("picture", prescriptionsItem.getImage());
                context.startActivity(intent);
                // Toast.makeText(context, prescriptionsItem.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

  /*  public void addPrescriptionComment() {
        Intent intent = new Intent(ViewPrescriptionsActivity.this, PrescriptionsCommentsActivity.class);
        startActivity(intent);
        finish();

    }*/

    @Override
    public int getItemCount() {
        if (prescriptionsItems != null)
            return prescriptionsItems.size();
        return 0;
    }

    public class Prescriptions extends RecyclerView.ViewHolder {
        TextView uploaderName;
        ImageView uploaderAvatar;
        ImageView picture;
        TextView description;
        Button commentButton;


        public Prescriptions(View prescriptionsView) {
            super(prescriptionsView);
            uploaderName = prescriptionsView.findViewById(R.id.presecription_user_name);
            uploaderAvatar = prescriptionsView.findViewById(R.id.presecription_user_profile_picture);
            picture = prescriptionsView.findViewById(R.id.presecription_image);
            description = prescriptionsView.findViewById(R.id.presecription_description);
            commentButton = prescriptionsView.findViewById(R.id.presecription_comment_button);
        }
    }
}