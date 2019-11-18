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

public class PromotionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PromotionsItem> promotionsItems;
    private User user;

    public PromotionsAdapter(Context context, List<PromotionsItem> promotionsItems) {

        this.context = context;
        this.promotionsItems = promotionsItems;

    }

    @Override
    public PromotionsAdapter.Promotions onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.promotions_adapter_content, parent, false);
        PromotionsAdapter.Promotions promotions = new PromotionsAdapter.Promotions(row);
        return promotions;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /*RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.ic_pic_error)
                .priority(Priority.HIGH);
*/


        final PromotionsItem promotionsItem = promotionsItems.get(position);
        ((PromotionsAdapter.Promotions) holder).name.setText(promotionsItem.getName());
        Glide.with(context).load((promotionsItem.getImage())).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(((Promotions) holder).picture);
        Glide.with(context).load((promotionsItem.getCompanyImage())).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(((Promotions) holder).companyProfilePicture);
        ((PromotionsAdapter.Promotions) holder).description.setText(promotionsItem.getDescription());
        ((Promotions) holder).createdAt.setText(PrefUtil.splitDateTime(promotionsItem.getCreatedAt()));
        ((Promotions) holder).companyName.setText(promotionsItem.getCompany());
    }

  /*  public void addPrescriptionComment() {
        Intent intent = new Intent(ViewPrescriptionsActivity.this, PrescriptionsCommentsActivity.class);
        startActivity(intent);
        finish();

    }*/

    @Override
    public int getItemCount() {
        if (promotionsItems != null)
            return promotionsItems.size();
        return 0;
    }

    public class Promotions extends RecyclerView.ViewHolder {
        private TextView name, description, companyName, createdAt;
        private ImageView picture, companyProfilePicture;


        public Promotions(View promotionsView) {
            super(promotionsView);
            name = promotionsView.findViewById(R.id.promotions_adapter_name);
            companyName = promotionsView.findViewById(R.id.promotions_adapter_company_name);
            companyProfilePicture = promotionsView.findViewById(R.id.promotions_adapter_company_profile_picture);
            picture = promotionsView.findViewById(R.id.promotions_adapter_image);
            createdAt = promotionsView.findViewById(R.id.promotions_adapter_created_at);
            description = promotionsView.findViewById(R.id.promotions_adapter_description);
        }
    }
}
