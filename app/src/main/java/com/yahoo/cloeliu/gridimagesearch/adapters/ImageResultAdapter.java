package com.yahoo.cloeliu.gridimagesearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yahoo.cloeliu.gridimagesearch.R;
import com.yahoo.cloeliu.gridimagesearch.models.ImageResult;

import java.util.List;

/**
 * Created by cloeliu on 2015/8/1.
 */
public class ImageResultAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultAdapter(Context context, List<ImageResult> images) {
        super(context, android.R.layout.simple_list_item_1, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        ImageResult imageInfo = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_result_item, parent, false);
        }
        //TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        // clear
        ivImage.setImageResource(0);
        //set
        //tvTitle.setText(Html.fromHtml(imageInfo.title));
        Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);
        return convertView;
    }
}
