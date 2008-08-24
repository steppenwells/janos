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
package net.sf.janos.ui;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import net.sbbi.upnp.messages.UPNPResponseException;
import net.sf.janos.control.AVTransportListener;
import net.sf.janos.control.AVTransportService;
import net.sf.janos.control.SonosController;
import net.sf.janos.control.ZoneListSelectionListener;
import net.sf.janos.control.ZonePlayer;
import net.sf.janos.model.Entry;
import net.sf.janos.model.MediaInfo;
import net.sf.janos.model.PositionInfo;
import net.sf.janos.model.TransportInfo;
import net.sf.janos.model.xml.AVTransportEventHandler.AVTransportEventType;
import net.sf.janos.util.ui.ImageUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.xml.sax.SAXException;

/**
 * A UI component for displaying the now-playing and queue.
 * @author David Wheeler
 *
 */
public class QueueDisplay extends Composite implements ZoneListSelectionListener, AVTransportListener {

  private static final Log LOG = LogFactory.getLog(QueueDisplay.class);
  
  /**
   * The maximum number of items to display in the queue
   */
  public static final int QUEUE_LENGTH = 20;
  
  /**
   * The colour of the labels
   */
  private final Color LABEL_COLOR;
  
  /**
   * A reference to the SonosController
   */
  private SonosController controller;
  
  /**
   * The name of the artist of the currently playing track
   */
  private final Label trackArtist;

  /**
   * The name of the album of the currently playing track
   */
  private final Label trackAlbum;

  /**
   * The name of the currently playing track
   */
  private final Label trackName;
  
  /**
   * The label with the album artwork
   */
  private final Label artwork;
  
  /**
   * The queue
   */
  private final org.eclipse.swt.widgets.List queue;
  
  /**
   * The current zone
   */
  private ZonePlayer currentZone;

  private QueueMouseListener queueMouseListener;

  /**
   * Creates a new QueueDisplay
   * @param parent
   * @param style
   * @param controller
   */
  public QueueDisplay(Composite parent, int style, SonosController controller) {
    super(parent, style);
    this.controller = controller;
    LABEL_COLOR = new Color(parent.getDisplay(), new RGB(0, 0, 128));
    
    setLayout(new GridLayout(1, false));
    Group nowPlaying = new Group(this, SWT.NONE);
    nowPlaying.setText("Now Playing");
    nowPlaying.setForeground(LABEL_COLOR);
    nowPlaying.setLayout(new GridLayout(2, false));
    Label trackArtistLabel = new Label(nowPlaying, SWT.RIGHT);
    trackArtistLabel.setText("Artist: ");
    trackArtistLabel.setForeground(LABEL_COLOR);
    trackArtist = new Label(nowPlaying, SWT.LEFT);
    Label trackAlbumLabel = new Label(nowPlaying, SWT.RIGHT);
    trackAlbumLabel.setText("Album: ");
    trackAlbumLabel.setForeground(LABEL_COLOR);
    trackAlbum = new Label(nowPlaying, SWT.LEFT);
    Label trackNameLabel = new Label(nowPlaying, SWT.RIGHT);
    trackNameLabel.setText("Name: ");
    trackNameLabel.setForeground(LABEL_COLOR);
    trackName = new Label(nowPlaying, SWT.LEFT);
    
    artwork = new Label(nowPlaying, 0);
    GridData gd = new GridData();
    gd.horizontalSpan = 2;
    gd.widthHint = 128;
    gd.heightHint = 128;
    artwork.setLayoutData(gd);
    
    Group queueBox = new Group(this, SWT.NONE);
    queueBox.setText("Now Playing");
    queueBox.setForeground(LABEL_COLOR);
    queueBox.setLayout(new FillLayout());
    queue = new org.eclipse.swt.widgets.List(queueBox, SWT.SINGLE | SWT.V_SCROLL);
    queueMouseListener = new QueueMouseListener();
    queue.addMouseListener(queueMouseListener);
    GridData queueData = new GridData(GridData.FILL, GridData.FILL, true, true);
    queueBox.setLayoutData(queueData);
    nowPlaying.setData(queueData);
  }

  /**
   * {@inheritDoc}
   */
  public void zoneSelectionChangedTo(ZonePlayer newSelection) {
    showNowPlayingForZone(newSelection);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {
    showNowPlayingForZone(null);  
    super.dispose();
  }
  
  /**
   * Reloads the now-playing and queue to be that from the given zone.
   * @param zone
   */
  public void showNowPlayingForZone(ZonePlayer zone) {
    if (currentZone != null) {
      currentZone.getMediaRendererDevice().getAvTransportService().removeAvTransportListener(this);
    }
    currentZone = zone;
    if (zone != null) {
      zone.getMediaRendererDevice().getAvTransportService().addAvTransportListener(this);
    }
    trackAlbum.setText("");
    trackArtist.setText("");
    trackName.setText("");
    artwork.setImage(null);
    queue.setItems(new String[0]);
    redraw();
    controller.getExecutor().execute(new NowPlayingFetcher(zone));
  }

  /**
   * Asynchronously sets the now playing details to the given information. The
   * image is loaded in a background thread before being displayed.
   * 
   * @param artist
   * @param album
   * @param name
   * @param artworkUrl
   */
  protected void setNowPlayingAsync(final String artist, final String album, final String name, final URL artworkUrl) {
    getDisplay().asyncExec(new NowPlayingSetter(album, artworkUrl, artist, name));
    
  }
  
  /**
   * Signifies that a queue is being played at the provided positionInfo
   * @param posInfo
   * @param zone
   */
  private void setQueueEntry(PositionInfo posInfo, final ZonePlayer zone) {
    // TODO develop a queue model to display
    final List<Entry> queueEntries = zone.getMediaServerDevice().getContentDirectoryService().getQueue(posInfo.getTrackNum() -1,QUEUE_LENGTH + 1);
    if (queueEntries.size() > 0) {
      final Entry currentEntry = queueEntries.remove(0);
      URL albumArtUrl = null;
      try {
        albumArtUrl = currentEntry.getAlbumArtURL(zone);
      } catch (MalformedURLException e) {
        LOG.error("Could not get album art URL: ", e);
      }
      setNowPlayingAsync(currentEntry.getCreator(), currentEntry.getAlbum(), currentEntry.getTitle(), albumArtUrl);
      getDisplay().asyncExec(new QueueUpdater(queueEntries));
    }
  }

  /**
   * {@inheritDoc}
   */
  public void valuesChanged(Set<AVTransportEventType> events, AVTransportService source) {
    if (source == currentZone.getMediaRendererDevice().getAvTransportService()) {
      controller.getExecutor().execute(new NowPlayingFetcher(currentZone));
    }
  }
  
  /**
   * Updates the queue
   */
  private final class QueueUpdater implements Runnable {
    private final List<Entry> entries;

    private QueueUpdater(List<Entry> entries) {
      this.entries = entries;
    }

    public void run() {
      queue.removeAll();
      for (Entry entry : entries) {
        queue.add(entry.getTitle());
      }
      layout();
    }
  }

  /**
   * Sets the now playing labels
   */
  private final class NowPlayingSetter implements Runnable {
    private final String album;

    private final URL url;

    private final String artist;

    private final String name;

    private NowPlayingSetter(String album, URL url, String artist, String name) {
      this.album = album;
      this.url = url;
      this.artist = artist;
      this.name = name;
    }
    

    public void run() {
      trackArtist.setText(artist);
      trackAlbum.setText(album);
      trackName.setText(name);
      
      if (artwork.getImage() != null) {
        artwork.getImage().dispose();
      }
      artwork.setImage(null);
    
      // asynchronously load image
      if (url != null) {
        controller.getExecutor().execute(new AlbumArtworkFetcher(url));
      }
      layout();
    }
  }
  
  private final class AlbumArtworkFetcher implements Runnable {
    private URL url;
    public AlbumArtworkFetcher(URL url) {
      this.url = url;
    }
    public void run() {
      ImageData imageData;
      Image artworkImage = null;
      InputStream artworkStream = null;
      try {
        artworkStream = new BufferedInputStream(url.openStream());
        imageData = new ImageLoader().load(artworkStream)[0];
        Image tmpImage = new Image(getDisplay(), imageData);
        artworkImage = ImageUtilities.scaleImageTo(tmpImage, 128, 128);
        tmpImage.dispose();
      } catch (FileNotFoundException e) {
        // no artwork. TODO add default image
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } finally {
        if (artworkStream != null) {
          try {
            artworkStream.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    
      final Image image = artworkImage;
      getDisplay().asyncExec(new Runnable() {
        public void run() {
          Image oldImage = artwork.getImage();
          artwork.setImage(image);
          if ( oldImage != null) {
            oldImage.dispose();
          }
          layout();
        }
      });
    }
  }

  // TODO this is fine for now playing, but should do a query of the queue for
  // the rest of the queue.
  protected class NowPlayingFetcher implements Runnable {

    private ZonePlayer zone;
    public NowPlayingFetcher(ZonePlayer zone) {
      this.zone = zone;
    }
    public void run() {
      if (zone == null) {
        return;
      }
      try {
        MediaInfo mediaInfo = zone.getMediaRendererDevice().getAvTransportService().getMediaInfo();
        TransportInfo transportInfo = zone.getMediaRendererDevice().getAvTransportService().getTransportInfo();
        PositionInfo posInfo = zone.getMediaRendererDevice().getAvTransportService().getPositionInfo();
        String uri = mediaInfo.getCurrentURI();
        if (uri == null) {
          
        } else if (uri.startsWith("x-rincon-queue:")) {
          ZonePlayer currentZone = null;
          // a queue is playing
          for (ZonePlayer zp : controller.getZonePlayerModel().getAllZones()) {
            if (uri.substring(15).startsWith(zp.getRootDevice().getUDN().substring(5))) {
              // this is the zoneplayer
              currentZone = zp;
              break;
            }
          }
          if (currentZone == null) {
            // unknown queue
            LOG.error("unknown queue: " + uri);
            return;
          }
          
          setQueueEntry(posInfo, currentZone);          
        } else if (uri.startsWith("x-rincon:")){
          // We're streaming from another sonos
          String id = uri.substring(9);
          ZonePlayer zp = controller.getZonePlayerModel().getById(id);
          setQueueEntry(posInfo, zp);
        } else if (uri.startsWith("x-file-cifs:")) {
          // just playing one file
          setNowPlayingAsync(mediaInfo.getCurrentURIMetaData().getCreator(), 
              mediaInfo.getCurrentURIMetaData().getAlbum(), 
              mediaInfo.getCurrentURIMetaData().getTitle(), 
              mediaInfo.getCurrentURIMetaData().getAlbumArtUrl(currentZone));
          
        } else if (uri.startsWith("x-rincon-mp3radio:")) {
          // yep, it's the radio
          setNowPlayingAsync("Internet Radio", mediaInfo.getCurrentURI().substring(mediaInfo.getCurrentURI().lastIndexOf("://") + 1), mediaInfo.getCurrentURIMetaData().getTitle(), null);
        } // TODO else...
      } catch (IOException e) {
        // TODO Auto-generated catch block
        LOG.error("Couldn't load queue", e);
      } catch (UPNPResponseException e) {
        // TODO Auto-generated catch block
        LOG.error("Couldn't load queue", e);
      } catch (SAXException e) {
        // TODO Auto-generated catch block
        LOG.error("Couldn't load queue", e);
      }
    }
  }
  
  public class QueueMouseListener implements MouseListener {
    public void mouseDoubleClick(MouseEvent e) {
      int queueIndex = ((org.eclipse.swt.widgets.List)e.getSource()).getSelectionIndex();
      try {
        currentZone.playQueueEntry(queueIndex - 1);
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch (UPNPResponseException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }

    public void mouseDown(MouseEvent e) {
      // TODO Auto-generated method stub

    }

    public void mouseUp(MouseEvent e) {
      // TODO Auto-generated method stub

    }

  }

}
