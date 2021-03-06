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
package net.sf.janos.model;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An immutable data transfer object containing the name and icon (URI) for a
 * zone player.
 * 
 * TODO: this class could use a real getIcon() that loads from a file depending
 * on the icon URI?
 * 
 * @author David Wheeler
 * 
 */
public class ZoneAttributes {
  
  private static final Log LOG = LogFactory.getLog(ZoneAttributes.class);
  
  private final String name;

  private final String icon;

  public ZoneAttributes(String name, String icon) {
    this.name = name;
    this.icon = icon;
    LOG.debug("Zone name: " + name + ", zone icon: " + icon);
  }

  /**
   * @return the name of the Zone.
   */
  public String getName() {
    return name;
  }

  /**
   * @return a string representation of the URI of the icon for the Zone.
   */
  public String getIcon() {
    return icon;
  }
}
