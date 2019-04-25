package edu.ckcc.schoolguide.model;

import android.app.Activity;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class App {

    // Self instance
    private static App instance;

    // Network communication objects
    private RequestQueue queue;
    private ImageLoader.ImageCache imageCache;
    private ImageLoader imageLoader;

    // Data
    private Article[] articles;
    private University[] universities;
    private Event[] events;
    private Job[] jobs;
    private Scholarship[] scholarships;

    // Private constructor
    private App(){

    }

    public static App getInstance(Activity context) {
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

    public University[] getUniversities() { return universities; }

    public Event[] getEvents() { return events; }

    public Job[] getJobs() { return jobs; }

    public Scholarship[] getScholarship() { return scholarships; }

    public void setArticles(Article[] articles) {
        this.articles = articles;
    }

    public void setUniversities(University[] universities) { this.universities = universities; }

    public void setEvents(Event[] events) { this.events = events; }

    public void setJobs(Job[] jobs) { this.jobs = jobs; }

    public void setScholarships(Scholarship[] scholarships) { this.scholarships = scholarships; }
}
