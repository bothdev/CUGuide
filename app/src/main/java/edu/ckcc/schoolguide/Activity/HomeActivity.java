package edu.ckcc.schoolguide.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.ckcc.schoolguide.R;
import edu.ckcc.schoolguide.Startup.MainActivity;
import edu.ckcc.schoolguide.model.App;
import edu.ckcc.schoolguide.model.Article;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //change status bar color
        if (Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        }

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        toolbar.setBackgroundColor(this.getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar();
        setTitle("School Guide");

        //Set Onclick Navigation Drawer
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set DrawerToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if(id == R.id.menu_university){
            onUniversityClick();
        } else if(id == R.id.menu_events){
            intent = new Intent(this, EventsActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_job){
            intent = new Intent(this, JobActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_scholarship){
            intent = new Intent(this, ScholarshipActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_bookmark){
            intent = new Intent(this, BookmarkActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_setting){
            intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_help){
            intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_about){
            intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void onUniversityClick() {
        drawerLayout.closeDrawers();
        UniversityFragment universityFragment = new UniversityFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, universityFragment);
        fragmentTransaction.commit();
    }
}