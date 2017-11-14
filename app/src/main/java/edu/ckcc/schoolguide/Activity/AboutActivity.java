package edu.ckcc.schoolguide.Activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.ckcc.schoolguide.R;
 
public class AboutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MapView mapView;
    GoogleMap map;
    private double longtitue = 11.5728299;
    private double langtitute = 104.8863281;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ///////////////////////////////
		if (Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        }

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_toolbar);
        toolbar.setBackgroundColor(this.getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar();
        setTitle("About Us");
		
		//Show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		///////////////////////////////////
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d("ckcc" , "onMapReady");
                map = googleMap;
                addMarker();
            }
        }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void addMarker(){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("SchoolGuide");
        markerOptions.snippet("Our Location");
        LatLng latlng = new LatLng(longtitue, langtitute);
        markerOptions.position(latlng);
        markerOptions.draggable(false);
        map.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 15);
        map.animateCamera(cameraUpdate);
    }
}