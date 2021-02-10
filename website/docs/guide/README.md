# Important 

Capacitor plugin to allow creating a local only hotspot that an application can use to communicate 
between co-located devices connected to the created WiFi hotspot.  

The network created by this method **WILL NOT HAVE INTERNET ACCESS**. This is an Android *limitation*, or design decision.  

Each application can make a single request for the hotspot, but multiple applications could be requesting 
the hotspot at the same time.  When multiple applications have successfully registered concurrently, 
they will be sharing the underlying hotspot.  

**This is a work in progress for an internal project**
