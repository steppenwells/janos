/*
   Copyright 2007 David Wheeler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.sf.janos.control;

public class ZonePlayerConstants {

  public static final String SONOS_DEVICE_TYPE = "urn:schemas-upnp-org:device:ZonePlayer:1";
  public static final String MEDIA_SERVER_DEVICE_TYPE = "urn:schemas-upnp-org:device:MediaServer:1";
  public static final String MEDIA_RENDERER_DEVICE_TYPE = "urn:schemas-upnp-org:device:MediaRenderer:1";
  
  public static final String SONOS_SERVICE_ZONE_GROUP_TOPOLOGY = "urn:schemas-upnp-org:service:ZoneGroupTopology:1";
  public static final String SONOS_SERVICE_ZONE_GROUP_MANAGEMENT = "urn:schemas-upnp-org:service:GroupManagement:1";
  public static final String SONOS_SERVICE_CONTENT_DIRECTORY = "urn:schemas-upnp-org:service:ContentDirectory:1"; 
  public static final String SONOS_SERVICE_AV_TRANSPORT = "urn:schemas-upnp-org:service:AVTransport:1"; 
  public static final String SONOS_SERVICE_RENDERING_CONTROL = "urn:schemas-upnp-org:service:RenderingControl:1";
  public static final String SONOS_SERVICE_GROUP_MANAGEMENT = "urn:schemas-upnp-org:service:GroupManagement:1";
  public static final String SONOS_SERVICE_DEVICE_PROPERTIES = "urn:schemas-upnp-org:service:DeviceProperties:1";
  public static final String SONOS_SERVICE_SYSTEM_PROPERTIES = "urn:schemas-upnp-org:service:SystemProperties:1";
  public static final String SONOS_SERVICE_CONNECTION_MANAGER = "urn:schemas-upnp-org:service:ConnectionManager:1";
  public static final String SONOS_SERVICE_ALARM_CLOCK = "urn:schemas-upnp-org:service:AlarmClock:1";
  public static final String SONOS_SERVICE_AUDIO_IN = "urn:schemas-upnp-org:service:AudioIn:1";
  
  public static final String SONOS_VARIABLE_ZONE_GROUP_STATE = "ZoneGroupState";

  private ZonePlayerConstants() {
    // don't instantiate me
  }
}
