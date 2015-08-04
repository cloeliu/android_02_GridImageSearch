package com.yahoo.cloeliu.gridimagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.cloeliu.gridimagesearch.R;
import com.yahoo.cloeliu.gridimagesearch.models.ImageResult;

public class ImageDisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        // hide action bar
        //this.getActionBar().hide();
        // load image
        ImageResult image = (ImageResult) getIntent().getSerializableExtra("image");
        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);
        Picasso.with(this).load(image.fullUrl).into(ivImage);
        // load text
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(Html.fromHtml(image.title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
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
}
