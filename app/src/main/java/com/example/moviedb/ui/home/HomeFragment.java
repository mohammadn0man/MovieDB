package com.example.moviedb.ui.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.adapter.MoviesAdapter;
import com.example.moviedb.api.Client;
import com.example.moviedb.api.Service;
import com.example.moviedb.models.Movie;
import com.example.moviedb.models.MoviesResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "ActivityHome";
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> movieList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        polulateView(root);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        return root;
    }


    private void polulateView(View v) {

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(getContext(), movieList);


        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadFromJSON();
    }

    private void loadFromJSON(){

        try{
            Client Client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiService.getPopularMovies(requireContext().getString(R.string.THE_MOVIE_DB_API_TOKEN));
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    Collections.sort(movies, Movie.BY_NAME_ALPHABETICAL);
                    recyclerView.setAdapter(new MoviesAdapter(getContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.e(TAG, "Error " + t.getMessage());
                    Toast.makeText(getContext(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}