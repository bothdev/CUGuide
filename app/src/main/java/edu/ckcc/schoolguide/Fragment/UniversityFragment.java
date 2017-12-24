package edu.ckcc.schoolguide.Fragment;

import android.app.Fragment;
import android.content.Context;
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
import edu.ckcc.schoolguide.Activity.UniversityDetailActivity;
import edu.ckcc.schoolguide.R;
import edu.ckcc.schoolguide.model.App;
import edu.ckcc.schoolguide.model.University;

public class UniversityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView rclUniversity;
    private UniversityFragment.UniversityAdapter universityAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment, container, false);

        rclUniversity = (RecyclerView)rootView.findViewById(R.id.rcl_view);
        rclUniversity.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_ly);
        swipeRefreshLayout.setOnRefreshListener(this);

        universityAdapter = new UniversityFragment.UniversityAdapter();
        rclUniversity.setAdapter(universityAdapter);

        loadUniversitiesFromServer();
        return rootView;
    }

    private void loadUniversitiesFromServer(){
        swipeRefreshLayout.setRefreshing(true);
        String url = "https://schoolguideproject.000webhostapp.com/json/university.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest universitiesRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                University[] universities = gson.fromJson(response, University[].class);

                // Pass data to adapter for displaying
                universityAdapter.setUniversities(universities);
                // Save data to Singleton for using later
                App.getInstance(getActivity()).setUniversities(universities);
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Error while loading articles from server", Toast.LENGTH_LONG).show();
                Log.d("School Guide", "Load article error: " + error.getMessage());
            }
        });

        requestQueue.add(universitiesRequest);
    }

    @Override
    public void onRefresh() {
        loadUniversitiesFromServer();
    }

    // University Adapter
    class UniversityViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtTel;
        TextView txtEmail;
        TextView txtAddress;
        NetworkImageView imgArticle;

        public UniversityViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView)itemView.findViewById(R.id.txt_title);
            txtTel = (TextView)itemView.findViewById(R.id.txt_number);
            txtEmail = (TextView) itemView.findViewById(R.id.txt_Email_address);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address);
            imgArticle = (NetworkImageView)itemView.findViewById(R.id.img_article);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    University university = universityAdapter.getUniversities()[position];
                    Intent intent = new Intent(getActivity(), UniversityDetailActivity.class);
                    intent.putExtra("title", university.getTitle());
                    intent.putExtra("imageUrl", university.getImageUrl());
                    intent.putExtra("photoUrl", university.getPhotoUrl());
                    intent.putExtra("description", university.getDescription());
                    intent.putExtra("tel", university.getTel());
                    intent.putExtra("email", university.getEmail());
                    intent.putExtra("address", university.getAddress());
                    Global.selectedUniversity = university;
                    startActivity(intent);
                }
            });
        }
    }

    class UniversityAdapter extends RecyclerView.Adapter<UniversityFragment.UniversityViewHolder> {

        private  University[] universities;

        public UniversityAdapter(){
            universities = new University[0];
        }

        public void setUniversities(University[] universities) {
            this.universities = universities;
            notifyDataSetChanged();
        }

        public University[] getUniversities() {
            return universities;
        }

        @Override
        public UniversityFragment.UniversityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewholder_university, parent, false);
            UniversityFragment.UniversityViewHolder universityViewHolder = new UniversityFragment.UniversityViewHolder(view);
            return universityViewHolder;
        }

        @Override
        public void onBindViewHolder(UniversityFragment.UniversityViewHolder holder, int position) {
            University university = universities[position];
            holder.txtTitle.setText(university.getTitle());
            holder.txtTel.setText(university.getTel());
            holder.txtEmail.setText(university.getEmail());
            holder.txtAddress.setText(university.getAddress());
            // Display image using NetworkImageView
            ImageLoader imageLoader = App.getInstance(getActivity()).getImageLoader();
            holder.imgArticle.setDefaultImageResId(R.drawable.ic_picture);
            holder.imgArticle.setErrorImageResId(R.drawable.ic_broken_image);
            holder.imgArticle.setScaleType(NetworkImageView.ScaleType.CENTER_CROP);
            holder.imgArticle.setImageUrl(university.getImageUrl(), imageLoader);
        }

        @Override
        public int getItemCount() { return universities.length; }
    }
}