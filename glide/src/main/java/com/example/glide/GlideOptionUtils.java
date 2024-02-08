package com.example.glide;

import com.bumptech.glide.request.RequestOptions;

public class GlideOptionUtils {
    public static RequestOptions baseOptions(){
        return new RequestOptions()
                .placeholder(R.drawable.ic_add)
                .error(R.drawable.ic_sad);
    }

    public static RequestOptions circleCropOptions(){
        return baseOptions().circleCrop();
    }
}
