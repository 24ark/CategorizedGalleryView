package com.arkitvora.categorizedgalleryview;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class GalleryFragment extends Fragment {
    private View rootView;
    private RecyclerView slider;
    private ArrayList<String> categoryNames;
    private ArrayList<Integer> categoryImageCount;
    private ArrayList<ImagePosition> imagePositionDetails;
    private ViewPager imageViewPager;
    private ArrayList<String> allImages;
    private ArrayList<GalleryData> galleryData;
    private TagSliderAdapter sliderAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int previousCategory = 0;
    private final static String GALLERY_DATA = "GALLERY_DATA";

    public GalleryFragment newInstance(ArrayList<GalleryData> galleryData) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(GALLERY_DATA , (ArrayList<? extends Parcelable>) galleryData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryData = getArguments().getParcelableArrayList(GALLERY_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.layout_horizontal, container, false);
        } else if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

        allImages = new ArrayList<>();
        imagePositionDetails = new ArrayList<>();
        categoryNames = new ArrayList<>();
        categoryImageCount = new ArrayList<>();

        for(int i = 0 ; i < galleryData.size() ; i++) {
            categoryNames.add(galleryData.get(i).getCategoryName());
            categoryImageCount.add(galleryData.get(i).getImagePaths().size());
            for(int j = 0 ; j < galleryData.get(i).getImagePaths().size() ; j++) {
                allImages.add(galleryData.get(i).getImagePaths().get(j));
                imagePositionDetails.add(new ImagePosition(i,j));
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSlider(view);
        setupViewPager(view);
    }



    private void setToolbarTitle(int i) {
        String toolBarTitle = String.valueOf(i + 1) + " Of " + String.valueOf(allImages.size());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(toolBarTitle);
    }

    private void setupSlider(View view) {
        slider = (RecyclerView) view.findViewById(R.id.list);
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        slider.setLayoutManager(linearLayoutManager);

        sliderAdapter = new TagSliderAdapter(categoryNames, categoryImageCount, getActivity());
        slider.setAdapter(sliderAdapter);

        slider.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        slider.smoothScrollToPosition(position);
                        int sum = 0;
                        for (int i = 0; i < position; i++) {
                            sum = sum + categoryImageCount.get(i);
                        }
                        imageViewPager.setCurrentItem(sum);
                    }
                })
        );


        int padding =  dm.widthPixels/4;
        slider.setPadding(padding, 0, padding, 0);
    }

    private void setupViewPager(View view) {
        imageViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        imageViewPager.setAdapter(new ImagesViewPagerAdapter(allImages, getActivity()));
        imageViewPager.setOffscreenPageLimit(1);
        imageViewPager.setPageTransformer(true, new DepthPageTransformer());

        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled ( int i, float v, int i2){
            }

            @Override
            public void onPageSelected(int i) {
                setToolbarTitle(i);

                sliderAdapter.setSelectedItem(linearLayoutManager, imagePositionDetails.get(i).getCategoryPosition(), imagePositionDetails.get(i).getPositionInCategory());
                if(previousCategory != imagePositionDetails.get(i).getCategoryPosition()) {
                    sliderAdapter.clearPagerStrip(linearLayoutManager, previousCategory);
                }
                previousCategory = imagePositionDetails.get(i).getCategoryPosition();
                slider.smoothScrollToPosition(imagePositionDetails.get(i).getCategoryPosition());

            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        };
        imageViewPager.addOnPageChangeListener(pageChangeListener);
    }
}

