package com.arkitvora.categorizedgalleryview;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import me.relex.photodraweeview.PhotoDraweeView;


public class ImagesViewPagerAdapter extends PagerAdapter {

    private final ArrayList<String> images;
    private Context context;

    public ImagesViewPagerAdapter(ArrayList<String> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_photoview, container, false);
        final PhotoDraweeView photoView = (PhotoDraweeView) view.findViewById(R.id.photo_view);
        photoView.setPhotoUri(Uri.parse(images.get(position)));
        container.addView(view, 0);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}

