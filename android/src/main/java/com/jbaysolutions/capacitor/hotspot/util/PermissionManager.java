package com.jbaysolutions.capacitor.hotspot.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {

    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
    };

    private static PermissionManager instance = new PermissionManager();

    public static PermissionManager getInstance() {
        return instance;
    }

    public static boolean canAccessLocation(Activity unityActivity) {
        return(hasPermission(unityActivity, Manifest.permission.ACCESS_FINE_LOCATION));
    }

    public static boolean canChangeWifiState(Activity unityActivity) {
        return(hasPermission(unityActivity, Manifest.permission.CHANGE_WIFI_STATE));
    }

    public static void makeRequestPermissions(Activity unityActivity) {
        ActivityCompat.requestPermissions(unityActivity, INITIAL_PERMS, 1111);

        // HANDLE HERE SOMEHOW ?

    }

    private static boolean hasPermission(Activity unityActivity, String perm) {
        return(PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(unityActivity, perm));
    }

}