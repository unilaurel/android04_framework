package com.example.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class DemoJsObject {
    @JavascriptInterface
    public String print(String msg){
        Log.e("DemoJsObject", "print: msg"+msg );
        return "这是android的返回值DemoJsObject";
    }
}
