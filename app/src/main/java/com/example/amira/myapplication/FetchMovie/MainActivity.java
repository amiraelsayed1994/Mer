package com.example.amira.myapplication.FetchMovie;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amira.myapplication.DetailsData.DetailFragment;
import com.example.amira.myapplication.InterFace.Call;
import com.example.amira.myapplication.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends ActionBarActivity implements Call {

    public static boolean mTwoPane = false;
    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()

                        .replace(R.id.movie_detail_container, new DetailFragment())
                        .commit();
            }
        } else {
            mTwoPane = false;

            getSupportFragmentManager().beginTransaction()

                    .replace(R.id.container, new MovieFragment())
                    .commit();
        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.toast,
                    (ViewGroup) findViewById(R.id.relativeLayout1));

            Toast toast = new Toast(this);
            toast.setView(view);
            toast.show();
            return true;
        }
        else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED || connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED)
        {


            AlertDialog alertDialog = new AlertDialog.Builder(
                    MainActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Alert Dialog");

            // Setting Dialog Message
            alertDialog.setMessage("NO Internet Connection");
            alertDialog.setIcon(R.drawable.ic_check_circle_24dp);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(getApplicationContext(), "Check Your Connection ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }

            });

            // Showing Alert Message
            alertDialog.show();
            return false;
        }
        return false;
    }

    @Override
    public void get(Bundle bundle) {
        if (mTwoPane) {
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);
            Log.e("TAF", "inside get" + bundle.toString());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        } else {

            MovieFragment fragments = new MovieFragment();
            fragments.setArguments(bundle);
            Log.e("TAF", "inside get" + bundle.toString());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragments)
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    isInternetOn();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),

                Uri.parse("android-app://com.example.amira.movies/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "Main Page",
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),

                Uri.parse("android-app://com.example.amira.movies/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
