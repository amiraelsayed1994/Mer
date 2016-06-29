package com.example.amira.myapplication.FetchMovie;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.amira.myapplication.Adapters.GridAdapter;
import com.example.amira.myapplication.Data.Constants;
import com.example.amira.myapplication.Data.Movie;
import com.example.amira.myapplication.DetailsData.DetailFragment;
import com.example.amira.myapplication.DetailsData.Details;
import com.example.amira.myapplication.InterFace.Call;
import com.example.amira.myapplication.R;
import com.example.amira.myapplication.database.Database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public  class MovieFragment extends Fragment {
Toolbar toolbar;
    GridView grid;
    ArrayList<Movie> moviesList;
    private GridAdapter gridAdapter;

DetailFragment de;

    public MovieFragment() {
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

        setHasOptionsMenu(true);
        moviesList = new ArrayList<>();
        Log.d("movieFragment", "inside movie fragment");

    }

   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();
        if (id == R.id.actin_top_rated) {
            moviesList.clear();

            new TaskManag().execute(Constants.topRated);
            Toast.makeText(getActivity(), "Most Top Rated Movies ", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_popular) {
            moviesList.clear();

            new TaskManag().execute(Constants.sortType);
            Toast.makeText(getActivity(), "Most  Popular Movies ", Toast.LENGTH_SHORT).show();
            grid.setVisibility(View.VISIBLE);
            return true;

        }
        else if (id == R.id.action_fav) {
            Database d = new Database(getActivity());
            Cursor data = d.Fetch_all();
            if (data != null) {

                moviesList.clear();
                Movie movie;
                do {
                    movie = new Movie();
                    movie.setPoster(data.getString(data.getColumnIndex("poster")));
                    movie.setTitle(data.getString(data.getColumnIndex("title")));
                    movie.setOverview(data.getString(data.getColumnIndex("overview")));
                    movie.setReleaseDate(data.getString(data.getColumnIndex("relasedate")));
                    movie.setVoteAverage(data.getString(data.getColumnIndex("vote_average")));
                    movie.setId(data.getInt(data.getColumnIndex("movie_id")));
                    moviesList.add(movie);
                    grid.setAdapter(new GridAdapter(getActivity(), moviesList));
                    Toast.makeText(getActivity(), " List Of Your Favourite Movies ", Toast.LENGTH_SHORT).show();
                } while (data.moveToNext());

            }
            else
            {
                Toast.makeText(getActivity(), " You Dont Have any Favourite Movie Yet clickon Favourite Button tO Create Your list", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else if(id==R.id.My_Fav)
        {
            Database d = new Database(getActivity());
            Cursor data = d.Fetch_all();
            if (data != null) {

                moviesList.clear();
                Movie movie;
                do {
                    movie = new Movie();
                    movie.setPoster(data.getString(data.getColumnIndex("poster")));
                    movie.setTitle(data.getString(data.getColumnIndex("title")));
                    movie.setOverview(data.getString(data.getColumnIndex("overview")));
                    movie.setReleaseDate(data.getString(data.getColumnIndex("relasedate")));
                    movie.setVoteAverage(data.getString(data.getColumnIndex("vote_average")));
                    movie.setId(data.getInt(data.getColumnIndex("movie_id")));
                    moviesList.add(movie);
                    grid.setAdapter(new GridAdapter(getActivity(), moviesList));
                    Toast.makeText(getActivity(), " List Of Your Favourite Movies ", Toast.LENGTH_SHORT).show();
                } while (data.moveToNext());

            }
            else {
                Toast.makeText(getActivity(), " You Dont Have any Favourite Movie Yet clickon Favourite Button tO Create Your list", Toast.LENGTH_SHORT).show();
            }
            return  true;


        }

            return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        grid = (GridView) rootView.findViewById(R.id.gridView1);


        TaskManag taskManag = new TaskManag();
        taskManag.execute(Constants.sortType);
        //grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = getBundle(position);
                ((Call) (getActivity())).get(bundle);


                if (MainActivity.mTwoPane == false) {
                    Intent intent = new Intent(getActivity(), Details.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @NonNull
    private Bundle getBundle(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("over", moviesList.get(position).getOverview());//overview
        bundle.putString("title", moviesList.get(position).getTitle());//title
        bundle.putString("rate", moviesList.get(position).getVoteAverage());//avearge rate
        bundle.putString("date", moviesList.get(position).getReleaseDate());//relasedate
        bundle.putString("image", moviesList.get(position).getPoster());//path
        bundle.putInt("id", moviesList.get(position).getId());//id
        Log.e("GRID", bundle.toString());
        return bundle;
    }


    @Override
    public void onStart() {
        super.onStart();


    }

    public class TaskManag extends AsyncTask<String, Void, ArrayList<Movie>> {//a3ed tany async task
// awou

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {




                Constants.Movie_URL =
                        "https://api.themoviedb.org/3/movie/" + params[0] + "?api_key=" + Constants.api_key;
                Log.d("url", "movie url" + Constants.Movie_URL);
                URL url = new URL(Constants.Movie_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();


                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                Constants.fetchmovies = buffer.toString();
            } catch (IOException e) {

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        // Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }

            }


            try {
                Log.e("TAG", Constants.fetchmovies);
                return Getmovies(Constants.fetchmovies);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public ArrayList<Movie> Getmovies(String mo)
                throws JSONException {


            JSONObject obj = new JSONObject(mo);
            JSONArray jsonArray = obj.getJSONArray(Constants.RESULTS);

            Movie movie;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieData = jsonArray.getJSONObject(i);
                String moviePosterPath = movieData.getString(Constants.POSTER_PATH);

                movie = new Movie();
                movie.setTitle(movieData.getString(Constants.ORIGINAL_TITLE));
                movie.setPoster(moviePosterPath);
                movie.setOverview(movieData.getString(Constants.OVERVIEW));
                movie.setReleaseDate(movieData.getString(Constants.RELEASE_DATE));
                movie.setVoteAverage(movieData.getString(Constants.VOTE_AVG));
                movie.setId(movieData.getInt(Constants.id));
                moviesList.add(movie);

            }
            return moviesList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> posterPaths) {
            if (posterPaths != null) {

                gridAdapter = new GridAdapter(getActivity(), posterPaths);
                grid.setAdapter(gridAdapter);
                if (MainActivity.mTwoPane) {
                    ((Call) getActivity()).get(getBundle(0));//3shan ageb awoul movie
                }

            }
        }
    }





}

