# Capacitor-Android-Hotspot

*Only for Android .... in case the name of the plugin is not explicit enough*

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
```
