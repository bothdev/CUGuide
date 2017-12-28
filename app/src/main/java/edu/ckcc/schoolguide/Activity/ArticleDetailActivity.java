package edu.ckcc.schoolguide.Activity;

import android.net.Network;
import android.support.annotation.NonNull;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import edu.ckcc.schoolguide.R;
import edu.ckcc.schoolguide.model.App;
import edu.ckcc.schoolguide.model.Article;

public class ArticleDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Article article = Global.selectedArticle;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        ///////////////////////////////
        if (Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_article_toolbar);
        toolbar.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar();
        setTitle(article.getTitle());

        //Show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ///////////////////////////////////

        Toast.makeText(this, article.getTitle(), Toast.LENGTH_LONG).show();
        TextView textView = (TextView) findViewById(R.id.detail_article);
        textView.setText(article.getDescription());

        imageLoader = App.getInstance(ArticleDetailActivity.this).getImageLoader();
        NetworkImageView networkImageView = (NetworkImageView) findViewById(R.id.image_article);
        networkImageView.setScaleType(NetworkImageView.ScaleType.CENTER_CROP);
        networkImageView.setImageUrl(article.getPhotoUrl(), imageLoader);

        if(article.getPhotoUrl()==null) {
            networkImageView.setImageUrl(article.getImageUrl(),imageLoader);
        }

        TextView textView1 = (TextView) findViewById(R.id.detail_title);
        textView1.setText(article.getTitle());
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