import { WebPlugin } from '@capacitor/core';
import { AndroidHotspotPluginPlugin } from './definitions';

export class AndroidHotspotPluginWeb extends WebPlugin implements AndroidHotspotPluginPlugin {
  constructor() {
    super({
      name: 'AndroidHotspotPlugin',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  // hasPermissions(): Promise<{value: boolean}> ;
  async hasPermissions(): Promise<{ allPermissions: boolean, location: boolean, wifiState:boolean}> {
    return {
      allPermissions: true,
      location: true,
      wifiState:true
    };
  }

  async getIsLocationEnabled(): Promise<{locationEnabled: boolean}> {
    return {
      locationEnabled: false
    }
  }

  // requestAndroidPermissions(): Promise<{}>;
  async requestAndroidPermissions(): Promise<void> {
    return;
  }

  //createHotspot(): Promise<{details: object}>;
  async createHotspot(): Promise<object> {
    console.log(' ------------------------------ EXECUTED ON WEB MODULE ------------------------------- ')
    return {
      ssid: 'FAKE_HOTSPOT',
      preSharedKey: 'FAKE_KEY',
    };
  }

  // stopHotspot(): Promise<void>
  async stopHotspot(): Promise<void> {
    return;
  }

  // openLocationSettings(): Promise<void>
  async openLocationSettings(): Promise<void> {
    return;
  }

  // getHotspotConfig(): Promise<{details: object}>;
  async getHotspotConfig(): Promise<object> {
    return {
      ssid: 'FAKE_HOTSPOT',
      preSharedKey: 'FAKE_KEY',
    };
  }
}

const AndroidHotspotPlugin = new AndroidHotspotPluginWeb();

export { AndroidHotspotPlugin };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(AndroidHotspotPlugin);
