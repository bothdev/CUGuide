package edu.ckcc.schoolguide.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import edu.ckcc.schoolguide.R;
import edu.ckcc.schoolguide.model.Article;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        /*
        String title = getIntent().getStringExtra("title");
        String imageUrl = getIntent().getStringExtra("image_url");
        */
        Article article = Global.selectedArticle;
        Toast.makeText(this, article.getTitle(), Toast.LENGTH_LONG).show();
    }
}