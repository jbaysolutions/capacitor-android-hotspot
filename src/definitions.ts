declare module '@capacitor/core' {
  interface PluginRegistry {
    AndroidHotspotPlugin: AndroidHotspotPluginPlugin;
  }
}

export interface AndroidHotspotPluginPlugin {

  echo(options: { value: string }): Promise<{ value: string }>

  hasPermissions(): Promise<{allPermissions: boolean, location: boolean, wifiState:boolean}>

  requestAndroidPermissions(): Promise<void>

  createHotspot(): Promise<object>;

  stopHotspot(): Promise<void>

  getHotspotConfig(): Promise<object>

  openLocationSettings(): Promise<void>
}
