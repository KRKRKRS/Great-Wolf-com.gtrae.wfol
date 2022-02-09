package com.gtrae.wfol;

import static com.gtrae.wfol.Parser.decode;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MyService extends Service {
    private static String LOG_TAG = "MyApp";
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MyService getService () {
            return MyService.this;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return this.binder;
    }

    public void getStatusAppsFlyerAndAppsId () {
        MyAppsFlyerListener MAF = new MyAppsFlyerListener();
        AppsFlyerLib.getInstance().init(decode(GW.AFKey), MAF, GW.MAcontext);
        AppsFlyerLib.getInstance().start(GW.MAcontext);
        GW.AppsFl_Id = AppsFlyerLib.getInstance().getAppsFlyerUID(GW.MAcontext);

        new Thread(new Runnable() {
            public void run() {
               try {
                   GW.AD_ID = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext()).getId();
                } catch (IOException e) {
                   Log.i(LOG_TAG, e.toString());
                } catch (GooglePlayServicesRepairableException e) {
                   Log.i(LOG_TAG, e.toString());
               } catch (GooglePlayServicesNotAvailableException e) {
                   Log.i(LOG_TAG, e.toString());
               }
            }
        }).start();
    }

    static class MyAppsFlyerListener implements AppsFlyerConversionListener {
        @Override
        public void onConversionDataSuccess(Map<String, Object> map) {
            for (String attrName : map.keySet())
                GW.statusAppsFlyer = Objects.requireNonNull(map.get(decode("YWZfc3RhdHVz"))).toString();
             Log.i("MyApp", "statusAppsFlyer - " + GW.statusAppsFlyer);
            if (GW.statusAppsFlyer.equals(decode("Tm9uLW9yZ2FuaWM="))) {
                String campaignStr = Objects.requireNonNull(map.get(decode("Y2FtcGFpZ24="))).toString();
                Parser parserStr = new Parser();
                GW.strAppsFlyer = parserStr.parse(campaignStr);
            }
        }

        @Override
        public void onConversionDataFail(String s) {
            Log.i("MyApp", "AppsFl onConversionDataFail " + s);
        }

        @Override
        public void onAppOpenAttribution(Map<String, String> map) {
            Log.i("MyApp", "AppsFl onAppOpenAttribution");
        }

        @Override
        public void onAttributionFailure(String s) {
            Log.i("MyApp", "AppsFl onAttributionFailure" + s);
        }
    }

}