package edu.ckcc.schoolguide.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import edu.ckcc.schoolguide.Activity.ArticleDetailActivity;
import edu.ckcc.schoolguide.Activity.Global;
import edu.ckcc.schoolguide.R;
import edu.ckcc.schoolguide.model.App;
import edu.ckcc.schoolguide.model.Article;

public class EventsFragment extends Fragment{

    private RecyclerView rclEvents;
    private EventsFragment.ArticleAdapter articleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        rclEvents = (RecyclerView)rootView.findViewById(R.id.rcl_events);
        rclEvents.setLayoutManager(new LinearLayoutManager(getActivity()));

        articleAdapter = new EventsFragment.ArticleAdapter();
        rclEvents.setAdapter(articleAdapter);


        loadArticlesFromServer();

        /*if(App.getInstance(this).getArticles() == null){
            loadArticlesFromServer();
        }else{
            Article[] articles = App.getInstance(this).getArticles();
            articleAdapter.setArticles(articles);
        }*/
        return rootView;
    }

    private void loadArticlesFromServer(){
        String url = "https://schoolguideproject.000webhostapp.com/json/events.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest articlesRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Article[] articles = gson.fromJson(response, Article[].class);
                // Pass data to adapter for displaying
                articleAdapter.setArticles(articles);
                // Save data to Singleton for using later
                App.getInstance(getActivity()).setArticles(articles);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error while loading articles from server", Toast.LENGTH_LONG).show();
                Log.d("School Guide", "Load article error: " + error.getMessage());
            }
        });
        /*
        JsonArrayRequest articlesRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ckcc", "Load data success");
                Article[] articles = new Article[response.length()];
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject articleJson = response.getJSONObject(i);
                        int id = articleJson.getInt("_id");
                        String title = articleJson.getString("_title");
                        String imageUrl = articleJson.getString("_image_url");
                        Article article = new Article(title, 0, imageUrl);
                        articles[i] = article;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // Pass data to adapter for displaying
                articleAdapter.setArticles(articles);
                // Save data to Singleton for using later
                App.getInstance(NewsActivity.this).setArticles(articles);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewsActivity.this, "Error while loading articles from server", Toast.LENGTH_LONG).show();
                Log.d("ckcc", "Load article error: " + error.getMessage());
            }
        });
        */
        requestQueue.add(articlesRequest);
    }

    // Article Adapter
    class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDes;
        NetworkImageView imgArticle;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView)itemView.findViewById(R.id.txt_title);
            txtDes = (TextView)itemView.findViewById(R.id.txt_des);
            imgArticle = (NetworkImageView)itemView.findViewById(R.id.img_article);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Article article = articleAdapter.getArticles()[position];
                    Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
                    intent.putExtra("title", article.getTitle());
                    intent.putExtra("image_url", article.getImageUrl());
                    Global.selectedArticle = article;
                    startActivity(intent);
                }
            });
        }
    }

    class ArticleAdapter extends RecyclerView.Adapter<EventsFragment.ArticleViewHolder> {

        private  Article[] articles;

        public ArticleAdapter(){
            articles = new Article[0];
        }

        public void setArticles(Article[] articles) {
            this.articles = articles;
            notifyDataSetChanged();
        }

        public Article[] getArticles() {
            return articles;
        }

        @Override
        public EventsFragment.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewholder_article, parent, false);
            EventsFragment.ArticleViewHolder articleViewHolder = new EventsFragment.ArticleViewHolder(view);
            return articleViewHolder;
        }

        @Override
        public void onBindViewHolder(EventsFragment.ArticleViewHolder holder, int position) {
            Article article = articles[position];
            holder.txtTitle.setText(article.getTitle());
            holder.txtDes.setText(article.getDescription());
            // Display image using NetworkImageView
            ImageLoader imageLoader = App.getInstance(getActivity()).getImageLoader();
            holder.imgArticle.setDefaultImageResId(R.drawable.ic_picture);
            holder.imgArticle.setErrorImageResId(R.drawable.events);
            holder.imgArticle.setScaleType(NetworkImageView.ScaleType.CENTER_CROP);
            holder.imgArticle.setImageUrl(article.getImageUrl(), imageLoader);
        }

        @Override
        public int getItemCount() {
            return articles.length;
        }
    }
}