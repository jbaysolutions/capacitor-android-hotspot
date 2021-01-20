package com.jbaysolutions.capacitor.hotspot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.jbaysolutions.capacitor.hotspot.util.PermissionManager;

@NativePlugin(
        requestCodes={AndroidHotspotPlugin.CREATE_HOTSPOT}
)
public class AndroidHotspotPlugin extends Plugin {

    protected static final String TAG = "JBAndroidHotspotPlugin";
    protected static final int CREATE_HOTSPOT = 9941;
    protected static final int REQUEST_HOTSPOT_PERMISSIONS = 9942;


    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
    };

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.success(ret);
    }

    @Override
    protected void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);


        PluginCall savedCall = getSavedCall();
        if (savedCall == null) {
            Log.d(TAG, "No stored plugin call for permissions request result");
            return;
        }

        for(int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Log.d(TAG, "User denied permission");
                savedCall.error("User denied Permission");
                return;
            }
        }

        if (requestCode == CREATE_HOTSPOT) {
            executeCreateHotspot(savedCall);
            return;
        }
        if (requestCode == REQUEST_HOTSPOT_PERMISSIONS) {
            savedCall.success();
            return;
        }
    }

    @PluginMethod()
    public  void hasPermissions(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("location", PermissionManager.canAccessLocation(getContext().getApplicationContext()));
        ret.put("wifiState", PermissionManager.canChangeWifiState(getContext().getApplicationContext()));
        ret.put("allPermissions", ret.getBool("location") && ret.getBool("wifiState") );
        call.success(ret);
    }

    @PluginMethod()
    public void requestAndroidPermissions(PluginCall call) {
        saveCall(call);
        pluginRequestPermissions(INITIAL_PERMS, REQUEST_HOTSPOT_PERMISSIONS);
    }

    @PluginMethod()
    public void createHotspot(PluginCall call) {
        saveCall(call);
        pluginRequestPermissions(INITIAL_PERMS, CREATE_HOTSPOT);
    }

    @PluginMethod()
    public void stopHotspot(PluginCall call) {
        if (hotspotReservation!=null) {
            try {
                hotspotReservation.close();
            } catch (Exception e) {}
            call.success();
            return;
        }
        call.error("No Hotspot Running");
    }

    @PluginMethod()
    public void getHotspotConfig(PluginCall call) {
        if (currentConfig!=null) {

            Log.v(TAG, "THE PASSWORD IS: "
                    + currentConfig.preSharedKey
                    + " \n SSID is : "
                    + currentConfig.SSID);

            JSObject ret = new JSObject();
            ret.put("ssid", currentConfig.SSID);
            ret.put("preSharedKey", currentConfig.preSharedKey);
            call.success(ret);
        }
        call.error("No Hotspot Running");
    }

    @PluginMethod()
    public void openLocationSettings(PluginCall call) {
        try {
            Intent requestLocationsToBeEnabled = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            requestLocationsToBeEnabled.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(requestLocationsToBeEnabled);
            call.success();
        } catch (Exception e) {
            e.printStackTrace();
            call.error("Problem opening Location Settings");
        }
    }



    public static WifiConfiguration currentConfig;
    static WifiManager.LocalOnlyHotspotReservation hotspotReservation;

    void executeCreateHotspot(final PluginCall call) {
        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(getContext().WIFI_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if (!isLocationEnabled(getContext().getApplicationContext())) {
                call.error("Location mode is not enabled");
                return;
            }

            wifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {

                @Override
                public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                    super.onStarted(reservation);
                    hotspotReservation = reservation;
                    currentConfig = hotspotReservation.getWifiConfiguration();

                    Log.v(TAG, "THE PASSWORD IS: "
                            + currentConfig.preSharedKey
                            + " \n SSID is : "
                            + currentConfig.SSID);

                    JSObject ret = new JSObject();
                    ret.put("ssid", currentConfig.SSID);
                    ret.put("preSharedKey", currentConfig.preSharedKey);
                    call.success(ret);
                }

                @Override
                public void onStopped() {
                    super.onStopped();
                    Log.v(TAG, "Local Hotspot Stopped");
                    hotspotReservation = null;
                    currentConfig =null;
                    call.error("Local Hotspot Stopped");
                }

                @Override
                public void onFailed(int reason) {
                    super.onFailed(reason);
                    Log.v(TAG, "Local Hotspot failed to start");
                    call.error("Local Hotspot failed to start with reason : " + reason);
                }
            }, new Handler());

        } else {
            JSObject ret = new JSObject();
            ret.put("error", true);
            ret.put("errorMessage", "Oreo minimum please.");
            call.success(ret);
        }
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
