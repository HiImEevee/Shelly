package com.shelly.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shelly.Models.ActivityStatus;
import com.shelly.R;
import com.shelly.Utils.BlurMethods;
import com.shelly.Utils.Constants;

import java.util.List;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {

    private List<ActivityStatus> mActivityList;
    private Context mContext;

    public ActivityListAdapter(List<ActivityStatus> mActivityList, Context mContext) {
        this.mActivityList = mActivityList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_activity, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ActivityStatus activityStatus = mActivityList.get(position);

        String auxString = "Day " + (mActivityList.get(position).getNumber()+1);
        holder.mActivityTitleTV.setText(auxString);

        auxString = "Progress: " + mActivityList.get(position).getProgress() + "%";
        holder.mActivityProgressTV.setText(auxString);

        holder.mActivityProgressBar.setProgress(mActivityList.get(position).getProgress());

        LayerDrawable mLayerDrawable = (LayerDrawable) mContext.getResources().getDrawable(R.drawable.bg_cardview_activity);
        GradientDrawable mDrawable = (GradientDrawable) mLayerDrawable.findDrawableByLayerId(mContext.getResources().getIdentifier("Gradient" + position % 5, "id", "com.shelly"));
        mDrawable.setCornerRadius(Constants.ACTIVITY_CARDVIEW_RADIUS);
        holder.mActivityCardView.setBackground(mDrawable);

        if(mActivityList.get(position).isLocked()) {
            holder.mLockedActivity.setVisibility(View.VISIBLE);
            Bitmap mBlurBitmap = BlurMethods.createBlurBitmap(holder.mActivityCardView, mContext);
            BlurMethods.setBackgroundOnView(holder.mActivityBlurIV, mBlurBitmap, mContext, Constants.ACTIVITY_CARDVIEW_RADIUS);
            holder.mActivityCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final SpringAnimation springAnimation = new SpringAnimation(holder.mLockIconIV, DynamicAnimation.ROTATION);
                    SpringForce  springForce = new SpringForce(0).setDampingRatio(0.1f).setStiffness(500f);
                    springAnimation.setSpring(springForce).setStartValue(-10).start();
                    Toast.makeText(mContext, "Activity locked.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.mActivityCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogActivityManager dialogActivityManager = new DialogActivityManager(mContext, activityStatus, holder.getAdapterPosition());
                    dialogActivityManager.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout mActivityCardView;
        public ImageView mActivityIconIV;
        public TextView mActivityTitleTV;
        public TextView mActivityProgressTV;
        public ProgressBar mActivityProgressBar;
        public ConstraintLayout mLockedActivity;
        public ImageView mActivityBlurIV;
        public ImageView mLockIconIV;

        public ViewHolder(View itemView) {
            super(itemView);
            mActivityCardView = itemView.findViewById(R.id.ActivityCardView);
            mActivityIconIV = itemView.findViewById(R.id.ActivityIconImageView);
            mActivityTitleTV = itemView.findViewById(R.id.ActivityTitleTextView);
            mActivityProgressTV = itemView.findViewById(R.id.ActivityProgressTextView);
            mActivityProgressBar = itemView.findViewById(R.id.ActivityProgressBar);
            mLockedActivity = itemView.findViewById(R.id.LockedActivity);
            mActivityBlurIV = itemView.findViewById(R.id.BluredImageView);
            mLockIconIV = itemView.findViewById(R.id.LockImageView);
        }
    }
}
