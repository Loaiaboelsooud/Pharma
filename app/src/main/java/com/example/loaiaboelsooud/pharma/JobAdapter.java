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

public class JobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<JobsItem> jobsItems;

    public JobAdapter(Context context, List<JobsItem> jobsItems) {
        this.context = context;
        this.jobsItems = jobsItems;
    }

    @Override
    public JobAdapter.Jobs onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        new PharmaConstants(this.context);
        View row = inflater.inflate(R.layout.job_adapter_content, parent, false);
        JobAdapter.Jobs job = new JobAdapter.Jobs(row);
        return job;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final JobsItem jobsItem = jobsItems.get(position);
        ((Jobs) holder).uploaderName.setText(jobsItem.getUserResponse().getUser().getName());
        Glide.with(context).load((jobsItem.getUserResponse().getUser().getAvatar())).
                placeholder(R.drawable.ic_loading).dontAnimate().
                into(((Jobs) holder).uploaderAvatar);
        ((Jobs) holder).name.setText(jobsItem.getName());
        ((Jobs) holder).description.setText(jobsItem.getDescription());
        ((Jobs) holder).position.setText(PharmaConstants.positionMapView.get(jobsItem.getPosition()));
        ((Jobs) holder).workPlace.setText(PharmaConstants.workPlaceMapView.get(jobsItem.getWorkPlace()));
        ((Jobs) holder).city.setText(PharmaConstants.citiesMapView.get(jobsItem.getCity()));
        ((Jobs) holder).region.setText(PharmaConstants.regionsMapView.get(jobsItem.getRegion()));
        ((Jobs) holder).address.setText(jobsItem.getAddress());
        ((Jobs) holder).mobileNumber.setText(jobsItem.getMobileNumbers().get(0));
        ((Jobs) holder).updatedAt.setText(PrefUtil.splitDateTime(jobsItem.getUpdatedAt()));
    }

    @Override
    public int getItemCount() {
        if (jobsItems != null)
            return jobsItems.size();
        return 0;
    }

    public class Jobs extends RecyclerView.ViewHolder {
        private TextView uploaderName, name, description, position, workPlace, city, region, address, mobileNumber, updatedAt;
        private ImageView uploaderAvatar;

        public Jobs(View prescriptionsView) {
            super(prescriptionsView);
            uploaderName = prescriptionsView.findViewById(R.id.job_adapter_user_name);
            uploaderAvatar = prescriptionsView.findViewById(R.id.job_adapter_user_profile_picture);
            name = prescriptionsView.findViewById(R.id.job_adapter_name);
            description = prescriptionsView.findViewById(R.id.job_adapter_description);
            position = prescriptionsView.findViewById(R.id.job_adapter_position);
            workPlace = prescriptionsView.findViewById(R.id.job_adapter_work_place);
            city = prescriptionsView.findViewById(R.id.job_adapter_city);
            region = prescriptionsView.findViewById(R.id.job_adapter_region);
            address = prescriptionsView.findViewById(R.id.job_adapter_address);
            mobileNumber = prescriptionsView.findViewById(R.id.job_adapter_mobile);
            updatedAt = prescriptionsView.findViewById(R.id.job_adapter_updated_at);
        }
    }
}
