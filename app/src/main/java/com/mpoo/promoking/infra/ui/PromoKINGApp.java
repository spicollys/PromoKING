package com.mpoo.promoking.infra.ui;

import android.app.Application;
import android.content.Context;

public class PromoKINGApp extends Application {
    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
