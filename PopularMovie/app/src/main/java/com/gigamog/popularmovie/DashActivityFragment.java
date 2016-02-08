package com.gigamog.popularmovie;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gigamog.popularmovie.beans.PosterImageItem;
import com.gigamog.popularmovie.com.gigamog.popularmovie.adapter.CustomGridViewAdapter;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class DashActivityFragment extends Fragment {
    private final String LOG_TAG = DashActivityFragment.class.getSimpleName();
    CustomGridViewAdapter movieAdapter;
    ArrayList<PosterImageItem> posterImageItem;
    public DashActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posterImageItem = new ArrayList<PosterImageItem>();
//        for (int i = 0; i < 10; i++) {
//            PosterImageItem item = new PosterImageItem("http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "");
//            posterImageItem.add(item);
//        }

        FetchMovieTask fetch = new FetchMovieTask();
        fetch.execute("1","popularity.asc");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_dash, container, false);
        Context activityConext = getActivity();

        movieAdapter = new CustomGridViewAdapter(activityConext, R.layout.movie_poster, posterImageItem);
        GridView grid = (GridView) root.findViewById(R.id.posterGridView);
        grid.setAdapter(movieAdapter);

        return root;
    }
    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<PosterImageItem>> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();
        @Override
        protected ArrayList<PosterImageItem> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr = null;

            try {
                String PAGE = params[0];
                String SORT_BY = params[1];
                String apiKey = getResources().getString(R.string.movie_db_key);
                String movieDbUrl = getResources().getString(R.string.movie_db_url);
                Uri uri =  Uri.parse(movieDbUrl).buildUpon()
//                        .appendQueryParameter("zip", params[0] + ",us")
                        .appendQueryParameter("page", PAGE)
                        .appendQueryParameter("sort_by", SORT_BY)
                        .appendQueryParameter("api_key", apiKey)
                        .build();
                Log.v("Built URI", uri.toString());

                URL url = new URL(uri.toString());


                // Create the request to OpenWeatherMap, and open the connection
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
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
                Log.v("Success", movieJsonStr);

                return getMovieDataFromJson(movieJsonStr);


            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

        }

        private ArrayList<PosterImageItem> getMovieDataFromJson(String jsonString)throws JSONException {
            final String RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String OVERVIEW = "overview";
            final String TITLE = "title";
            final String ORIGINAL_TITLE = "original_title";
            final String POPULARITY = "popularity";
            final String VOTE_COUNT = "vote_count";
            final String VOTE_AVERAGE = "vote_average";
            final String RELEASE_DATE = "release_date";
            final String BACKDROP_PATh = "backdrop_path";
            String movie_db_picURL = "http://image.tmdb.org/t/p/w185";

            JSONObject movieJSON = new JSONObject(jsonString);
            JSONArray moviesArray = movieJSON.getJSONArray(RESULTS);

            for(int i=0; i< moviesArray.length(); i++){

                JSONObject currentJsonNode = moviesArray.getJSONObject(i);
                PosterImageItem item = new PosterImageItem(
                        movie_db_picURL+ currentJsonNode.getString(POSTER_PATH),
                        currentJsonNode.getString(TITLE),
                        currentJsonNode.getString(OVERVIEW),
                        currentJsonNode.getString(ORIGINAL_TITLE),
                        movie_db_picURL + currentJsonNode.getString(BACKDROP_PATh),
                currentJsonNode.getDouble(POPULARITY),
                currentJsonNode.getDouble(VOTE_AVERAGE),
                currentJsonNode.getInt(VOTE_COUNT),
                        currentJsonNode.getString(RELEASE_DATE));
                posterImageItem.add(item);
            }
            movieAdapter.addAll(posterImageItem);
            return posterImageItem;

        }



    }

}
