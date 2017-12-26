package edu.ckcc.schoolguide.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import edu.ckcc.schoolguide.Activity.ScholarshipDetailActivity;
import edu.ckcc.schoolguide.R;
import edu.ckcc.schoolguide.model.App;
import edu.ckcc.schoolguide.model.Article;
import edu.ckcc.schoolguide.model.Scholarship;

public class ScholarshipFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView rclScholarship;
    private ScholarshipFragment.ArticleAdapter articleAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment, container, false);

        rclScholarship = (RecyclerView)rootView.findViewById(R.id.rcl_view);
        rclScholarship.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_ly);
        swipeRefreshLayout.setOnRefreshListener(this);

        articleAdapter = new ScholarshipFragment.ArticleAdapter();
        rclScholarship.setAdapter(articleAdapter);

        //loadArticlesFromServer();

        if(App.getInstance(getActivity()).getScholarship() == null){
            loadArticlesFromServer();
        }else{
            Scholarship[] articles = App.getInstance(getActivity()).getScholarship();
            articleAdapter.setScholarships(articles);
        }
        return rootView;
    }

    private void loadArticlesFromServer(){
        swipeRefreshLayout.setRefreshing(true);
        final String url = "https://schoolguideproject.000webhostapp.com/json/scholarship.php";
        //String url = "http://localhost/json/scholarship.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest articlesRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Scholarship[] articles = gson.fromJson(response, Scholarship[].class);
                // Pass data to adapter for displaying
                articleAdapter.setScholarships(articles);
                // Save data to Singleton for using later
                App.getInstance(getActivity()).setScholarships(articles);
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);

                buidDialog(getActivity()).show();

                Toast.makeText(getActivity(), "Error while loading articles from server", Toast.LENGTH_LONG).show();
                Log.d("School Guide", "Load article error: " + error.getMessage());
            }

            public AlertDialog.Builder buidDialog(Context context){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("No Internet Connection");
                builder.setMessage("Please turn on the internet");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return builder;
            }
        });

        requestQueue.add(articlesRequest);
    }

    @Override
    public void onRefresh() {
        loadArticlesFromServer();
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
                    Scholarship article = articleAdapter.getScholarships()[position];
                    Intent intent = new Intent(getActivity(), ScholarshipDetailActivity.class);
                    intent.putExtra("title", article.getTitle());
                    intent.putExtra("image_url", article.getImageUrl());
                    Global.selectedScholarship = article;
                    startActivity(intent);
                }
            });
        }
    }

    class ArticleAdapter extends RecyclerView.Adapter<ScholarshipFragment.ArticleViewHolder> {

        private  Scholarship[] articles;

        public ArticleAdapter(){
            articles = new Scholarship[0];
        }

        public void setScholarships(Scholarship[] articles) {
            this.articles = articles;
            notifyDataSetChanged();
        }

        public Scholarship[] getScholarships() {
            return articles;
        }

        @Override
        public ScholarshipFragment.ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewholder_article, parent, false);
            ScholarshipFragment.ArticleViewHolder articleViewHolder = new ScholarshipFragment.ArticleViewHolder(view);
            return articleViewHolder;
        }

        @Override
        public void onBindViewHolder(ScholarshipFragment.ArticleViewHolder holder, int position) {
            Scholarship article = articles[position];
            holder.txtTitle.setText(article.getTitle());
            holder.txtDes.setText(article.getDescription());
            // Display image using NetworkImageView
            ImageLoader imageLoader = App.getInstance(getActivity()).getImageLoader();
            holder.imgArticle.setDefaultImageResId(R.drawable.schoolarship);
            holder.imgArticle.setErrorImageResId(R.drawable.schoolarship);
            holder.imgArticle.setScaleType(NetworkImageView.ScaleType.CENTER_CROP);
            holder.imgArticle.setImageUrl(article.getImageUrl(), imageLoader);
        }

        @Override
        public int getItemCount() {
            return articles.length;
        }
    }
}