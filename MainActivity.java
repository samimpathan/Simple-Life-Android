package com.samim.simplelife;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.graphics.Color;
import android.view.View;

public class MainActivity extends Activity {
    private WebView web;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(18, 92, 68));
        getWindow().setNavigationBarColor(Color.rgb(18, 92, 68));
        web = new WebView(this);
        web.setBackgroundColor(Color.rgb(247, 249, 248));
        web.setOverScrollMode(View.OVER_SCROLL_NEVER);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        WebView.setWebContentsDebuggingEnabled(false);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setAllowContentAccess(false);
        web.getSettings().setSupportMultipleWindows(false);
        web.addJavascriptInterface(new AndroidActions(), "SimpleLifeAndroid");
        setContentView(web);
        if (state == null) web.loadUrl("file:///android_asset/index.html");
        else web.restoreState(state);
    }

    private class AndroidActions {
        @JavascriptInterface public void email(String address, String subject, String body) {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Uri.encode(address)));
                i.putExtra(Intent.EXTRA_SUBJECT, subject == null ? "" : subject);
                i.putExtra(Intent.EXTRA_TEXT, body == null ? "" : body);
                launch(i, "No email app is installed.", "ایمیل اپلیکیشن پیدا نشو.");
            });
        }
        @JavascriptInterface public void call(String number) {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(number)));
                launch(i, "No phone app is available.", "د تلیفون اپلیکیشن پیدا نشو.");
            });
        }
        @JavascriptInterface public void backPressed() { runOnUiThread(() -> finish()); }
        @JavascriptInterface public void setDarkMode(boolean enabled) {
            runOnUiThread(() -> {
                int c = enabled ? Color.rgb(20, 27, 25) : Color.rgb(18, 92, 68);
                getWindow().setStatusBarColor(c);
                getWindow().setNavigationBarColor(c);
            });
        }
        @JavascriptInterface public void share(String title, String text) {
            runOnUiThread(() -> {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, title == null ? "Simple Life" : title);
                i.putExtra(Intent.EXTRA_TEXT, text == null ? "" : text);
                try { startActivity(Intent.createChooser(i, title == null ? "Share" : title)); }
                catch (ActivityNotFoundException e) { Toast.makeText(MainActivity.this, "No sharing app is available.", Toast.LENGTH_SHORT).show(); }
            });
        }
        private void launch(Intent i, String en, String ps) {
            try { startActivity(i); }
            catch (ActivityNotFoundException e) { Toast.makeText(MainActivity.this, en, Toast.LENGTH_SHORT).show(); }
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (web != null) web.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override public void onBackPressed() {
        if (web != null) web.evaluateJavascript("if(typeof page!=='undefined'&&page!=='home'){go('home')}else{window.SimpleLifeAndroid&&window.SimpleLifeAndroid.backPressed&&window.SimpleLifeAndroid.backPressed()}", null);
        else super.onBackPressed();
    }
}
