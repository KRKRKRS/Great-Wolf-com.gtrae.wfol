package com.gtrae.wfol;

import static com.gtrae.wfol.Parsergtrae.degtraecode;

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
    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public void getStatusAppsFlyerAndAppsId() {
        MyAppsFlyerListener MAF = new MyAppsFlyerListener();
        AppsFlyerLib.getInstance().init(degtraecode(GW.AFKgtraeey), MAF, GW.MAcontgtraeext);
        AppsFlyerLib.getInstance().start(GW.MAcontgtraeext);
        GW.AppsgtraeFl_Id = AppsFlyerLib.getInstance().getAppsFlyerUID(GW.MAcontgtraeext);

        new Thread(new Runnable() {
            public void run() {
                try {
                    GW.ADAD_ID_ID = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext()).getId();
                } catch (IOException e) {
                } catch (GooglePlayServicesRepairableException e) {
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        }).start();
    }

    static class MyAppsFlyerListener implements AppsFlyerConversionListener {
        @Override
        public void onConversionDataSuccess(Map<String, Object> map) {
            for (String attrName : map.keySet())
                GW.statusgtraeAppsFlyer = Objects.requireNonNull(map.get(degtraecode("YWZfc3RhdHVz"))).toString();
            if (GW.statusgtraeAppsFlyer.equals(degtraecode("Tm9uLW9yZ2FuaWM="))) {
                String campaignStr = Objects.requireNonNull(map.get(degtraecode("Y2FtcGFpZ24="))).toString();
                Parsergtrae parsergtraeStr = new Parsergtrae();
                GW.strAppgtraesFlyer = parsergtraeStr.pagtraerse(campaignStr);
            }
        }

        @Override
        public void onConversionDataFail(String s) {
        }

        @Override
        public void onAppOpenAttribution(Map<String, String> map) {
        }

        @Override
        public void onAttributionFailure(String s) {
        }
    }

}