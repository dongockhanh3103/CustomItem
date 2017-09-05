package com.example.ngockhanh.graphics2dandcustomview;

/**
 * Created by Ngoc Khanh on 9/4/2017.
 */

public class Logo {
    String title;

    int imageSource;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }
    public Logo(){
    }
    public Logo(String title,int imageSource){
        this.title=title;
        this.imageSource=imageSource;
    }





}
