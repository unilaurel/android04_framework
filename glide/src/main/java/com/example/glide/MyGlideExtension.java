package com.example.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

@GlideExtension
public class MyGlideExtension {
    private MyGlideExtension() {
    }

    //グローバル統一設定
    @GlideOption
    public static BaseRequestOptions<?> injectOptions(BaseRequestOptions<?> options) {
        return options.placeholder(R.drawable.ic_add)
                .error(R.drawable.ic_sad)
                .circleCrop()
        ;
    }
}
