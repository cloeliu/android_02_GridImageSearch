package com.yahoo.cloeliu.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.cloeliu.gridimagesearch.R;
import com.yahoo.cloeliu.gridimagesearch.adapters.ImageResultAdapter;
import com.yahoo.cloeliu.gridimagesearch.fragments.SearchAttributeDialog;
import com.yahoo.cloeliu.gridimagesearch.listeners.EndlessScrollListener;
import com.yahoo.cloeliu.gridimagesearch.models.ImageResult;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity implements SearchAttributeDialog.SearchAttributeDialogListener {

    private EditText etQuery;
    private GridView gvResults;

    private String query = "";
    private String filter = "";
    private String searchApi = " https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

    private ArrayList<ImageResult> imageResults;
    private ImageResultAdapter aImageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();

        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult image = imageResults.get(position);
                i.putExtra("image", image);
                startActivity(i);
            }
        });

        // Attach the listener to the AdapterView onCreate
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });

    }

    private void showSearchAttributeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        SearchAttributeDialog searchAttributeDialog = SearchAttributeDialog.newInstance("Some Title");
        searchAttributeDialog.show(fm, "search_attribute_dialog");
    }

    private void customLoadMoreDataFromApi(int page) {
        if(page > 8) {
            return;
        }
        int start = (page - 1) * 8;
        AsyncHttpClient client = new AsyncHttpClient();
        String url = searchApi + query + "&rsz=8" + "&start=" + String.valueOf(start);
        if(!filter.equals("")) {
            url = url + filter;
        }
        Log.d("Debug-2", "page=" + String.valueOf(page) + "&start=" + String.valueOf(start));
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Debug-2", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onImageSearch(View view) {
        query = etQuery.getText().toString();
        String url = searchApi + query + "&rsz=8";
        if(!filter.equals("")) {
            url = url + filter;
        }
        Log.d("Debug", "query url=" + url);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Debug", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    // another way to add elements
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    //imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    //aImageResults.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void onFilterClick(MenuItem item) {
        showSearchAttributeDialog();
    }

    @Override
    public void onFinishSearchAttributeDialog(String type, String color, String size, String site) {
        // make filter query string
        if(!type.equals("")) {
            filter += "&imgtype=" + type;
        }
        if(!color.equals("")) {
            filter += "&imgcolor=" + color;
        }
        if(!size.equals("")) {
            filter += "&imgsz=" + size;
        }
        if(!site.equals("")) {
            filter += "&as_sitesearch=" + site;
        }
    }
}
