package com.arkitvora.categorizedgalleryview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TagSliderAdapter extends  RecyclerView.Adapter<TagSliderAdapter.ItemViewHolder>  {
    ArrayList<String> categoryName;
    ArrayList<Integer> categoryImageCount;
    Context context;

    public TagSliderAdapter(ArrayList<String> categoryName, ArrayList<Integer> categoryImageCount, Context context) {
        this.categoryName = categoryName;
        this.categoryImageCount = categoryImageCount;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.item_slider,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        holder.categoryName.setText(categoryName.get(position));
        final DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        holder.mStepPagerStrip.setPageCount(categoryImageCount.get(position));
        holder.mStepPagerStrip.setDotColors(R.color.step_pager_next_tab_color, R.color.step_pager_selected_tab_color, R.color.step_pager_selected_last_tab_color, R.color.step_pager_next_tab_color);
        if(position == 0 ) {
            holder.mStepPagerStrip.setCurrentPage(0);
        }
        else {
            holder.mStepPagerStrip.setCurrentPage(-1);

        }
    }

    @Override
    public int getItemCount() {
        return categoryName.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryName;
        private StepPagerStrip mStepPagerStrip;
        private LinearLayout parentLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mStepPagerStrip = (StepPagerStrip) itemView.findViewById(R.id.strip);
            categoryName = (TextView) itemView.findViewById(R.id.category_name);
            parentLayout= (LinearLayout) itemView.findViewById(R.id.parentLayout);
            final DisplayMetrics dm = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dm.widthPixels / 2 , LinearLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            parentLayout.setGravity(Gravity.CENTER_VERTICAL);
            parentLayout.setLayoutParams(params);
        }
    }

    public void setSelectedItem(RecyclerView.LayoutManager lm, int currentProject, int currentImageInProject) {

        if(lm.findViewByPosition(currentProject) != null) {
            ((StepPagerStrip) lm.findViewByPosition(currentProject).findViewById(R.id.strip)).setCurrentPage(currentImageInProject);
        }
    }

    public void clearPagerStrip(RecyclerView.LayoutManager lm, int currentProject) {

        if(lm.findViewByPosition(currentProject) != null) {
            ((StepPagerStrip) lm.findViewByPosition(currentProject).findViewById(R.id.strip)).setCurrentPage(-1);
        }
    }

}
