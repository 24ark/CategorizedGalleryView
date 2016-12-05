package com.arkitvora.categorizedgalleryview;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categorized gallery view");
        initGalleryFragment();
    }

    private void initGalleryFragment() {
        GalleryFragment galleryFragment = new GalleryFragment().newInstance(getGalleryData());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.content_frame, galleryFragment , "GALLERY_FRAGMENT").commit();
    }

    private ArrayList<GalleryData> getGalleryData() {
        ArrayList<GalleryData> galleryData = new ArrayList<>();
        ArrayList<String> beachImageSet = new ArrayList<>(Arrays.asList("https://images.pexels.com/photos/29724/pexels-photo-29724.jpg?h=350&auto=compress" , "https://images.pexels.com/photos/68672/beach-beverage-caribbean-cocktail-68672.jpeg?h=350&auto=compress","https://images.pexels.com/photos/108074/pexels-photo-108074.jpeg?h=350&auto=compress&cs=tinysrgb", "https://images.pexels.com/photos/26331/pexels-photo-26331.jpg?h=350&auto=compress&cs=tinysrgb"));
        ArrayList<String> mountainImageSet = new ArrayList<>(Arrays.asList("https://images.pexels.com/photos/115045/pexels-photo-115045.jpeg?h=350&auto=compress","https://images.pexels.com/photos/7039/pexels-photo.jpeg?h=350&auto=compress", "https://images.pexels.com/photos/67517/pexels-photo-67517.jpeg?h=350&auto=compress"));
        ArrayList<String> forestImageSet = new ArrayList<>(Arrays.asList("https://images.pexels.com/photos/94616/pexels-photo-94616.jpeg?h=350&auto=compress", "https://images.pexels.com/photos/24586/pexels-photo-24586.jpg?h=350&auto=compress", "https://images.pexels.com/photos/240125/pexels-photo-240125.jpeg?h=350&auto=compress&cs=tinysrgb"));
        ArrayList<String> desertImageSet = new ArrayList<>(Arrays.asList("https://images.pexels.com/photos/109031/pexels-photo-109031.jpeg?h=350&auto=compress&cs=tinysrgb", "https://images.pexels.com/photos/60013/desert-drought-dehydrated-clay-soil-60013.jpeg?h=350&auto=compress&cs=tinysrgb","https://images.pexels.com/photos/6496/man-person-jumping-desert.jpg?h=350&auto=compress","https://images.pexels.com/photos/28051/pexels-photo-28051.jpg?h=350&auto=compress","https://images.pexels.com/photos/71241/pexels-photo-71241.jpeg?h=350&auto=compress"));
        GalleryData beachesDataSet = new GalleryData("Beaches" , beachImageSet);
        GalleryData mountainsDataSet = new GalleryData("Mountains" , mountainImageSet);
        GalleryData forestsDataSet = new GalleryData("Forests" , forestImageSet);
        GalleryData desertsDataSet = new GalleryData("Deserts" , desertImageSet);
        galleryData.add(beachesDataSet);
        galleryData.add(mountainsDataSet);
        galleryData.add(forestsDataSet);
        galleryData.add(desertsDataSet);
        return galleryData;
    }
}
