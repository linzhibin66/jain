package com.example.administrator.test;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Administrator on 2017/10/10 0010.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "57b0a011d1", false);
    }
}
