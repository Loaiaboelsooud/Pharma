package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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
        ((Jobs) holder).position.setText(PharmaConstants.positionMapView.get(jobsItem.getPosition()).toUpperCase());
        ((Jobs) holder).workPlace.setText(PharmaConstants.workPlaceMapView.get(jobsItem.getWorkPlace()));
        ((Jobs) holder).city.setText(PharmaConstants.citiesMapView.get(jobsItem.getCity()));
        ((Jobs) holder).region.setText(PharmaConstants.regionsMapView.get(jobsItem.getRegion()));
        ((Jobs) holder).address.setText(jobsItem.getAddress());
        ((Jobs) holder).email.setText(jobsItem.getEmail());
        if (jobsItem.getMobileNumbers() != null && !jobsItem.getMobileNumbers().isEmpty()) {
            ((Jobs) holder).mobileNumber.setText(jobsItem.getMobileNumbers().get(0));
        }
        ((Jobs) holder).updatedAt.setText(PrefUtil.splitDateTime(jobsItem.getUpdatedAt()));
        if (jobsItem.getNegotiable()) {
            ((Jobs) holder).salary.setText(context.getResources().getString(R.string.job_negotiable).toUpperCase());
            ((Jobs) holder).salary.setTextColor(Color.argb(63, 51, 51, 51));
            ((Jobs) holder).LE_H.setVisibility(View.GONE);
        } else {
            ((Jobs) holder).salary.setText(String.valueOf((jobsItem.getMinSalary() + jobsItem.getMaxSalary()) / 2));
            ((Jobs) holder).salary.setTextColor(Color.parseColor("#333333"));
            ((Jobs) holder).LE_H.setVisibility(View.VISIBLE);
        }
        if (jobsItem.getStatus().equals(PharmaConstants.JOB_HIRING)) {
            ((Jobs) holder).status.setText(context.getResources().getString(R.string.job_hiring));
            ((Jobs) holder).status.setTextColor(Color.parseColor("#a7bd56"));
        } else if (jobsItem.getStatus().equals(PharmaConstants.JOB_CLOSED)) {
            ((Jobs) holder).status.setText(context.getResources().getString(R.string.job_closed));
            ((Jobs) holder).status.setTextColor(Color.parseColor("#ef0f44"));
        }
    }

    @Override
    public int getItemCount() {
        if (jobsItems != null)
            return jobsItems.size();
        return 0;
    }

    public class Jobs extends RecyclerView.ViewHolder {
        private TextView uploaderName, name, description, position, workPlace, city, region, address, mobileNumber, updatedAt, status, salary, email, LE_H;
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
            email = prescriptionsView.findViewById(R.id.job_adapter_email);
            mobileNumber = prescriptionsView.findViewById(R.id.job_adapter_mobile);
            updatedAt = prescriptionsView.findViewById(R.id.job_adapter_updated_at);
            status = prescriptionsView.findViewById(R.id.job_adapter_status);
            salary = prescriptionsView.findViewById(R.id.job_adapter_salary);
            LE_H = prescriptionsView.findViewById(R.id.job_adapter_LE_H);

        }
    }
}
