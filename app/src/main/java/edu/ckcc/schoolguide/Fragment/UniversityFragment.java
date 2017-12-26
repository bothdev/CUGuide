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

        //loadUniversitiesFromServer();

        if(App.getInstance(getActivity()).getUniversities() == null){
            loadUniversitiesFromServer();
        }else{
            University[] universities = App.getInstance(getActivity()).getUniversities();
            universityAdapter.setUniversities(universities);
        }

        return rootView;
    }

    private void loadUniversitiesFromServer(){
        swipeRefreshLayout.setRefreshing(true);
        final String url = "https://schoolguideproject.000webhostapp.com/json/university.php";
        //final String url = "http://localhost/json/university.php";
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

                buidDialog(getActivity()).show();

                Toast.makeText(getActivity(), "Error while loading data from server", Toast.LENGTH_LONG).show();
                Log.d("School Guide", "Load data error: " + error.getMessage());
            }

            public AlertDialog.Builder buidDialog(Context context){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("No Data Connection");
                builder.setMessage("Error while loading data !!!");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return builder;
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
        NetworkImageView imgUni;

        public UniversityViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView)itemView.findViewById(R.id.txt_uni_title);
            txtTel = (TextView)itemView.findViewById(R.id.txt_uni_tel);
            txtEmail = (TextView) itemView.findViewById(R.id.txt_uni_Email);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_uni_address);
            imgUni = (NetworkImageView)itemView.findViewById(R.id.img_university);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    University university = universityAdapter.getUniversities()[position];
                    Intent intent = new Intent(getActivity(), UniversityDetailActivity.class);
                    intent.putExtra("title", university.getTitle());
                    intent.putExtra("image", university.getImage());
                    intent.putExtra("photo", university.getPhoto());
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
            holder.imgUni.setImageUrl(university.getImage(), imageLoader);
            holder.imgUni.setDefaultImageResId(R.drawable.ic_university_campus);
            holder.imgUni.setErrorImageResId(R.drawable.ic_university_campus);
            holder.imgUni.setScaleType(NetworkImageView.ScaleType.CENTER_CROP);
        }

        @Override
        public int getItemCount() { return universities.length; }
    }
}