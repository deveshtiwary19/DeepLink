package com.example.deeplinkexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;

    //Following is the object for the web view
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigning id to the webview]
        webView=findViewById(R.id.webview_home);
        floatingActionButton=findViewById(R.id.btn);

        webView.setWebViewClient(new MyBrowser());

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(new MyJavaScriptInterface(),"android");

        //The initial url
        webView.loadUrl("https://bcetdgp.ac.in/");




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webView.loadUrl("https://www.google.com/");
                Log.d("webView", webView.toString());

            }
        });







    }

    //Following is the class to handle the java script interface for the app in webview
    //We need the following interface as this helps us to keep a track on url
    class MyJavaScriptInterface {
        @JavascriptInterface
        public void onUrlChange(String url) {
            Log.d("hydrated", "onUrlChange" + url);
            if(url.contains("www.google.com")){
                //Menas, that www.google.com is opened
                Toast.makeText(getApplicationContext(), "Deep Link found in Webview", Toast.LENGTH_SHORT).show();
            }
        }
    }




    //The web view client class
    class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            Toast.makeText(MainActivity.this, "Function is called", Toast.LENGTH_SHORT).show();

            //Url print
            Log.d("url", request.getUrl().toString());
            Log.d("webView", view.toString());

            if (request.getUrl().toString().contains("https://www.google.com/")){

                Toast.makeText(MainActivity.this, "The link is opened", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(request.getUrl());

                startActivity(intent);

                return true;

            }

            //view.loadUrl(request.getUrl().toString());


            return false;
        }
    }






}


