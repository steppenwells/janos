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

import net.sbbi.upnp.services.UPNPService;

/**
 * For controlling the alarm clock service of a zone player.
 * 
 * NOTE: this class is incomplete.
 * 
 * @author David Wheeler
 *
 */
public class AlarmClockService extends AbstractService {

  protected AlarmClockService(UPNPService service) {
    super(service, ZonePlayerConstants.SONOS_SERVICE_ALARM_CLOCK);
  }
  
  /* TODO
   * <?xml version="1.0" encoding="utf-8" ?>
<scpd xmlns="urn:schemas-upnp-org:service-1-0">
    <specVersion>
        <major>1</major>
        <minor>0</minor>
    </specVersion>
    <serviceStateTable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_ISO8601Time</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_Recurrence</name>
        <dataType>string</dataType>
        <allowedValueList>
          <allowedValue>ONCE</allowedValue>
          <allowedValue>WEEKDAYS</allowedValue>
          <allowedValue>WEEKENDS</allowedValue>
          <allowedValue>DAILY</allowedValue>
        </allowedValueList>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_AlarmID</name>
        <dataType>ui4</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_AlarmList</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_AlarmEnabled</name>
        <dataType>boolean</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_AlarmProgramURI</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_AlarmProgramMetaData</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
          <name>A_ARG_TYPE_AlarmPlayMode</name>
          <dataType>string</dataType>
          <allowedValueList>
              <allowedValue>NORMAL</allowedValue>
              <allowedValue>REPEAT_ALL</allowedValue>
              <allowedValue>SHUFFLE_NOREPEAT</allowedValue>
              <allowedValue>SHUFFLE</allowedValue>
          </allowedValueList>
          <defaultValue>NORMAL</defaultValue>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_AlarmVolume</name>
        <dataType>ui2</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_AlarmIncludeLinkedZones</name>
        <dataType>boolean</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_AlarmRoomUUID</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_TimeZoneIndex</name>
        <dataType>i4</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_TimeZoneAutoAdjustDst</name>
        <dataType>boolean</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_TimeZoneInformation</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="no">
        <name>A_ARG_TYPE_TimeStamp</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="yes">
        <name>TimeZone</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="yes">
        <name>TimeServer</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="yes">
        <name>TimeGeneration</name>
        <dataType>ui4</dataType>
      </stateVariable>
      <stateVariable sendEvents="yes">
        <name>AlarmListVersion</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="yes">
        <name>DailyIndexRefreshTime</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="yes">
        <name>TimeFormat</name>
        <dataType>string</dataType>
      </stateVariable>
      <stateVariable sendEvents="yes">
        <name>DateFormat</name>
        <dataType>string</dataType>
      </stateVariable>
    </serviceStateTable>
    <actionList>
        <action>
            <name>SetFormat</name>
            <argumentList>
                <argument>
                    <name>DesiredTimeFormat</name>
                    <direction>in</direction>
                    <relatedStateVariable>TimeFormat</relatedStateVariable>
                </argument>
                <argument>
                    <name>DesiredDateFormat</name>
                    <direction>in</direction>
                    <relatedStateVariable>DateFormat</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>GetFormat</name>
            <argumentList>
                <argument>
                    <name>CurrentTimeFormat</name>
                    <direction>out</direction>
                    <relatedStateVariable>TimeFormat</relatedStateVariable>
                </argument>
                <argument>
                    <name>CurrentDateFormat</name>
                    <direction>out</direction>
                    <relatedStateVariable>DateFormat</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>SetTimeZone</name>
            <argumentList>
                <argument>
                    <name>DesiredTimeZone</name>
                    <direction>in</direction>
                    <relatedStateVariable>TimeZone</relatedStateVariable>
                </argument>
                <argument>
                    <name>Index</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_TimeZoneIndex</relatedStateVariable>
                </argument>
                <argument>
                    <name>AutoAdjustDst</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_TimeZoneAutoAdjustDst</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>GetTimeZone</name>
            <argumentList>
                <argument>
                    <name>CurrentTimeZone</name>
                    <direction>out</direction>
                    <relatedStateVariable>TimeZone</relatedStateVariable>
                </argument>
                <argument>
                    <name>Index</name>
                    <direction>out</direction>
                    <relatedStateVariable>A_ARG_TYPE_TimeZoneIndex</relatedStateVariable>
                </argument>
                <argument>
                    <name>AutoAdjustDst</name>
                    <direction>out</direction>
                    <relatedStateVariable>A_ARG_TYPE_TimeZoneAutoAdjustDst</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>SetTimeServer</name>
            <argumentList>
                <argument>
                    <name>DesiredTimeServer</name>
                    <direction>in</direction>
                    <relatedStateVariable>TimeServer</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>GetTimeServer</name>
            <argumentList>
                <argument>
                    <name>CurrentTimeServer</name>
                    <direction>out</direction>
                    <relatedStateVariable>TimeServer</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>SetTimeNow</name>
            <argumentList>
                <argument>
                    <name>DesiredTime</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_ISO8601Time</relatedStateVariable>
                </argument>
                <argument>
                    <name>TimeZoneForDesiredTime</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_TimeZoneInformation</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>GetHouseholdTimeAtStamp</name>
            <argumentList>
                <argument>
                    <name>TimeStamp</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_TimeStamp</relatedStateVariable>
                </argument>
                <argument>
                    <name>HouseholdUTCTime</name>
                    <direction>out</direction>
                    <relatedStateVariable>A_ARG_TYPE_ISO8601Time</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>GetTimeNow</name>
            <argumentList>
                <argument>
                    <name>CurrentUTCTime</name>
                    <direction>out</direction>
                    <relatedStateVariable>A_ARG_TYPE_ISO8601Time</relatedStateVariable>
                </argument>
                <argument>
                    <name>CurrentLocalTime</name>
                    <direction>out</direction>
                    <relatedStateVariable>A_ARG_TYPE_ISO8601Time</relatedStateVariable>
                </argument>
                <argument>
                    <name>CurrentTimeZone</name>
                    <direction>out</direction>
                    <relatedStateVariable>TimeZone</relatedStateVariable>
                </argument> 
                <argument>
                    <name>CurrentTimeGeneration</name>
                    <direction>out</direction>
                    <relatedStateVariable>TimeGeneration</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>CreateAlarm</name>
            <argumentList>
                <argument>
                    <name>StartLocalTime</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_ISO8601Time</relatedStateVariable>
                </argument>
                <argument>
                    <name>Duration</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_ISO8601Time</relatedStateVariable>
                </argument>
                <argument>
                    <name>Recurrence</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_Recurrence</relatedStateVariable>
                </argument>
                <argument>
                    <name>Enabled</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmEnabled</relatedStateVariable>
                </argument>
                <argument>
                    <name>RoomUUID</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmRoomUUID</relatedStateVariable>
                </argument>
                <argument>
                    <name>ProgramURI</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmProgramURI</relatedStateVariable>
                </argument>
                <argument>
                    <name>ProgramMetaData</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmProgramMetaData</relatedStateVariable>
                </argument>
                <argument>
                    <name>PlayMode</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmPlayMode</relatedStateVariable>
                </argument>
                <argument>
                    <name>Volume</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmVolume</relatedStateVariable>
                </argument>
                <argument>
                    <name>IncludeLinkedZones</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmIncludeLinkedZones</relatedStateVariable>
                </argument>
                <argument>
                    <name>AssignedID</name>
                    <direction>out</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmID</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>UpdateAlarm</name>
            <argumentList>
                <argument>
                    <name>ID</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmID</relatedStateVariable>
                </argument>
                <argument>
                    <name>StartLocalTime</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_ISO8601Time</relatedStateVariable>
                </argument>
                <argument>
                    <name>Duration</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_ISO8601Time</relatedStateVariable>
                </argument>
                <argument>
                    <name>Recurrence</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_Recurrence</relatedStateVariable>
                </argument>
                <argument>
                    <name>Enabled</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmEnabled</relatedStateVariable>
                </argument>
                <argument>
                    <name>RoomUUID</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmRoomUUID</relatedStateVariable>
                </argument>
                <argument>
                    <name>ProgramURI</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmProgramURI</relatedStateVariable>
                </argument>
                <argument>
                    <name>ProgramMetaData</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmProgramMetaData</relatedStateVariable>
                </argument>
                <argument>
                    <name>PlayMode</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmPlayMode</relatedStateVariable>
                </argument>
                <argument>
                    <name>Volume</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmVolume</relatedStateVariable>
                </argument>
                <argument>
                    <name>IncludeLinkedZones</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmIncludeLinkedZones</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>DestroyAlarm</name>
            <argumentList>
                <argument>
                    <name>ID</name>
                    <direction>in</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmID</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>ListAlarms</name>
            <argumentList>
                <argument>
                    <name>CurrentAlarmList</name>
                    <direction>out</direction>
                    <relatedStateVariable>A_ARG_TYPE_AlarmList</relatedStateVariable>
                </argument>
                <argument>
                    <name>CurrentAlarmListVersion</name>
                    <direction>out</direction>
                    <relatedStateVariable>AlarmListVersion</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>SetDailyIndexRefreshTime</name>
            <argumentList>
                <argument>
                    <name>DesiredDailyIndexRefreshTime</name>
                    <direction>in</direction>
                    <relatedStateVariable>DailyIndexRefreshTime</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
        <action>
            <name>GetDailyIndexRefreshTime</name>
            <argumentList>
                <argument>
                    <name>CurrentDailyIndexRefreshTime</name>
                    <direction>out</direction>
                    <relatedStateVariable>DailyIndexRefreshTime</relatedStateVariable>
                </argument>
            </argumentList>
        </action>
    </actionList>
</scpd>
   */
  
  public void handleStateVariableEvent(String varName, String newValue) {
    // TODO Auto-generated method stub
    
  }
}
