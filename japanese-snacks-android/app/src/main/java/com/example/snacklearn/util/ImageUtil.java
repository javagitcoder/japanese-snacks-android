package com.example.snacklearn.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.snacklearn.config.AppConfig;

public class ImageUtil {

    public static void loadSnackImage(Context context, String imageName, ImageView imageView) {
        String baseUrl = AppConfig.getInstance(context).getImageBaseUrl();
        String imageUrl = baseUrl + imageName;

        Glide.with(context)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(imageView);
    }
}