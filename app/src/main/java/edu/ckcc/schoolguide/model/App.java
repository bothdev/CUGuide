package edu.ckcc.schoolguide.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Computer on 10/16/2017.
 */

public class App {

    // Self instance
    private static App instance;

    // Network communication objects
    private RequestQueue queue;
    private ImageLoader.ImageCache imageCache;
    private ImageLoader imageLoader;

    // Data
    private Article[] articles;

    // Private constructor
    private App(){

    }

    public static App getInstance(Context context) {
        if(instance == null) {
            instance = new App();
            instance.queue = Volley.newRequestQueue(context);
            instance.imageCache = new ImageLoader.ImageCache() {
                @Override
                public Bitmap getBitmap(String url) {
                    return null;
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {

                }
            };
            instance.imageLoader = new ImageLoader(instance.queue, instance.imageCache);
        }
        return instance;
    }

    public void addRequest(Request request){
        queue.add(request);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public Article[] getArticles() {
        return articles;
    }

    public void setArticles(Article[] articles) {
        this.articles = articles;
    }
}
