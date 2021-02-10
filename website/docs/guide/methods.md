# Methods

## hasPermissions()
 
Definition: `hasPermissions(): Promise<{allPermissions: boolean, location: boolean, wifiState:boolean}>`

```javascript
AndroidHotspotPlugin.hasPermissions()
    .then(answer => {
        console.log('All Permissions? ' + answer.allPermissions)
        console.log('Location Permissions? ' + answer.location)
        console.log('Wifi State Permissions? ' + answer.wifiState)
    })
```

## requestAndroidPermissions()
 
Definition: `requestAndroidPermissions(): Promise<void>`

Request missing permissions from the User.

```javascript
AndroidHotspotPlugin.requestAndroidPermissions()
```

## getIsLocationEnabled()

Definition: `getIsLocationEnabled(): Promise<{locationEnabled: boolean}>`

Use this method to check if the location services is enable.  

```javascript
AndroidHotspotPlugin.getIsLocationEnabled()
    .then(result => {
        console.log('Is location enabled? ' + result.locationEnabled);
    })
    .catch(error => {
        console.log('Error : ' + JSON.stringify(error))
    })
```

## openLocationSettings()

Definition: `openLocationSettings(): Promise<void>`

Opens the Location Services settings panel to the user. This is the only way to enable location Services. The User must do it himself.

```javascript
AndroidHotspotPlugin.openLocationSettings()
```

## createHotspot()

Definition: `createHotspot(): Promise<object>;`    

**THIS METHOD IS ASYNC!** 

```javascript
AndroidHotspotPlugin.createHotspot()
    .then((response) => {
        console.log('Settings : ' + JSON.stringify(response));
    })
    .catch(error => {
        console.log('Error : ' + JSON.stringify(error))
    })
```

## stopHotspot()

Definition: `stopHotspot(): Promise<void>`

```javascript
AndroidHotspotPlugin.stopHotspot()
```

## getHotspotConfig()

Definition: `getHotspotConfig(): Promise<object>`

```javascript
AndroidHotspotPlugin.getHotspotConfig()
    .then((response) => {
        console.log('Settings : ' + JSON.stringify(response));
    })
    .catch(error => {
        console.log('Error : ' + JSON.stringify(error))
    })
```

