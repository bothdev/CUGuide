package edu.ckcc.schoolguide.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import edu.ckcc.schoolguide.R;
import edu.ckcc.schoolguide.model.App;
import edu.ckcc.schoolguide.model.University;

public class UniversityDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        University university = Global.selectedUniversity;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_detail);

        ///////////////////////////////
        if (Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        }

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_uni_toolbar);
        toolbar.setBackgroundColor(this.getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar();
        setTitle(university.getTitle());

        //Show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ///////////////////////////////////

        Toast.makeText(this, university.getTitle(), Toast.LENGTH_SHORT).show();
        TextView textView = (TextView) findViewById(R.id.detail_uni);
        textView.setText(university.getDescription());

        imageLoader = App.getInstance(UniversityDetailActivity.this).getImageLoader();
        NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.image_uni);
        networkImageView.setScaleType(NetworkImageView.ScaleType.CENTER_CROP);
        networkImageView.setImageUrl(university.getPhotoUrl(), imageLoader);

        if(university.getPhotoUrl()==null) {
            networkImageView.setImageUrl(university.getImageUrl(),imageLoader);
        }
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
}