package edu.ckcc.schoolguide.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import edu.ckcc.schoolguide.R;
import edu.ckcc.schoolguide.model.Scholarship;
import edu.ckcc.schoolguide.model.University;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set DrawerToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //Home menu start
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        int lastSeletedMenu = sharedPreferences.getInt("last-selected-menu",R.id.menu_home);
        MenuItem selectedItem = navigationView.getMenu().findItem(lastSeletedMenu);
        selectedItem.setCheckable(true);
        onNavigationItemSelected(selectedItem);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        if (id == R.id.menu_home){
            setTitle("Home");
        } else if(id == R.id.menu_university){
            intent = new Intent(this, UniversityActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_events){
            intent = new Intent(this, EventsActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_job){
            intent = new Intent(this, JobActivity.class);
            startActivity(intent);
        } else if(id == R.id.menu_scholarship){
            intent = new Intent(this, ScholarshipActivity.class);
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
        
        //Close drawer
        if(id == R.id.menu_home ){
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;
    }
}