# Capacitor-Android-Hotspot

**Only for Android .... in case the name of the plugin is not explicit enough**

Capacitor plugin to allow creating a local only hotspot that an application can use to communicate 
between co-located devices connected to the created WiFi hotspot.  

The network created by this method **WILL NOT HAVE INTERNET ACCESS**. This is an Android *limitation*, or design decision.  

Each application can make a single request for the hotspot, but multiple applications could be requesting 
the hotspot at the same time.  When multiple applications have successfully registered concurrently, 
they will be sharing the underlying hotspot.  

**This is a work in progress for an internal project**

## How to use this

* Git clone this code
* Add the plugin to your Capacitor Android Project

```
yarn add file:/path/to/capacitor-android-hotspot
```

*  Do Android Plugin integration

```java
package com.example.android.project;

import android.os.Bundle;

import com.getcapacitor.BridgeActivity;
import com.getcapacitor.Plugin;

import java.util.ArrayList;

// ----------------- IMPORT THE PLUGIN ---------------------- 
import com.jbaysolutions.capacitor.hotspot.AndroidHotspotPlugin; 

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initializes the Bridge
    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
      // Additional plugins you've installed go here
      // Ex: add(TotallyAwesomePlugin.class);
      
      // -------------- ADD THE PLUGIN ------------- 
      add(AndroidHotspotPlugin.class);
    }});
  }
}
```

* Use it on your Capacitor Project:

```javascript
// Import bits
import { Plugins } from '@capacitor/core'
import 'capacitor-android-hotspot'
const { AndroidHotspotPlugin } = Plugins

// and then use the AndroidHotspotPlugin

AndroidHotspotPlugin.hasPermissions();
AndroidHotspotPlugin.requestAndroidPermissions();
AndroidHotspotPlugin.createHotspot();
AndroidHotspotPlugin.stopHotspot();
AndroidHotspotPlugin.getHotspotConfig();
AndroidHotspotPlugin.openLocationSettings();
```
