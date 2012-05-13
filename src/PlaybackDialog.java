/*! Copyright (C) 2009-2012 Apertus, All Rights Reserved
 *! Author : Apertus Team
-----------------------------------------------------------------------------**
 *!
 *!  This program is free software: you can redistribute it and/or modify
 *!  it under the terms of the GNU General Public License as published by
 *!  the Free Software Foundation, either version 3 of the License, or
 *!  (at your option) any later version.
 *!
 *!  This program is distributed in the hope that it will be useful,
 *!  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *!  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *!  GNU General Public License for more details.
 *!
 *!  You should have received a copy of the GNU General Public License
 *!  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *!
-----------------------------------------------------------------------------**/

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import net.miginfocom.swing.MigLayout;

public class PlaybackDialog extends javax.swing.JPanel {

    private static final long serialVersionUID = -7433613978413247324L;

    private class VideoFileListModel extends AbstractListModel {

	private static final long serialVersionUID = -78906942360451820L;
	private ArrayList<VideoFile> VideoFilesList;

	VideoFileListModel() {
	    VideoFilesList = new ArrayList<VideoFile>();
	}

	public int getSize() {
	    return VideoFilesList.size();
	}

	public Object getElementAt(int i) {
	    return VideoFilesList.get(i).getName();
	}

	public Object getVideoFileAt(int i) {
	    return VideoFilesList.get(i);
	}

	private void clear() {
	    VideoFilesList.clear();
	}

	private void addElement(VideoFile file) {
	    VideoFilesList.add(file);
	    int index = VideoFilesList.indexOf(file);
	    fireContentsChanged(this, index, index);
	}
    }

    class VideoListCellRenderer extends JLabel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1067784511398029588L;
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	public VideoListCellRenderer() {
	    // Don't paint behind the component
	    setOpaque(true);
	}

	// Set the attributes of the
	// class and return a reference
	public Component getListCellRendererComponent(JList list, Object value, // value
										// to
										// display
		int index, // cell index
		boolean iss, // is selected
		boolean chf) // cell has focus?
	{

	    // JLabel renderer = (JLabel)
	    // defaultRenderer.getListCellRendererComponent(list, value, index,
	    // iss, chf);
	    // Set the text and
	    // background color for rendering
	    setText((String) value);
	    setBackground(Color.WHITE);

	    // Set a border if the
	    // list item is selected
	    if (iss) {
		setBackground(new Color(186, 206, 244, 100));
	    } else {
		setBackground(Color.WHITE);
	    }
	    return this;
	}
    }

    ElphelVision Parent;
    private VideoFileListModel videoFileListModel;
    private VideoListCellRenderer CellRenderer;

    private javax.swing.JList FilesList;
    private EButton ListFiles;
    private EButton SettingsCancelButton;
    private javax.swing.JPanel VideoFrame;
    private javax.swing.JPanel bg;
    private EButton PlayButton;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Canvas vlcoverlay;

    public PlaybackDialog(ElphelVision parent) {
	Parent = parent;
	videoFileListModel = new VideoFileListModel();
	CellRenderer = new VideoListCellRenderer();
	try {
	    java.awt.EventQueue.invokeAndWait(new Runnable() {

		public void run() {
		    bg = new javax.swing.JPanel();
		    VideoFrame = new javax.swing.JPanel();
		    vlcoverlay = new java.awt.Canvas();
		    SettingsCancelButton = new EButton(Parent);
		    ListFiles = new EButton(Parent);
		    jScrollPane1 = new javax.swing.JScrollPane();
		    FilesList = new javax.swing.JList();

		    bg.setBackground(new java.awt.Color(0, 0, 0));
		    bg.setPreferredSize(new java.awt.Dimension(1024, 600));

		    //VideoFrame.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));

		    vlcoverlay.setBackground(java.awt.Color.darkGray);
		    vlcoverlay.setForeground(new java.awt.Color(153, 65, 65));


		    SettingsCancelButton.setText("Close");
		    SettingsCancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			    SettingsCancelButtonActionPerformed(evt);
			}
		    });

		    ListFiles.setText("list");
		    ListFiles.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			    ListFilesActionPerformed(evt);
			}
		    });

		    FilesList.setModel(videoFileListModel);
		    FilesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		    FilesList.setCellRenderer(CellRenderer);
		    jScrollPane1.setViewportView(FilesList);
		    PlayButton = new EButton(Parent);

		    PlayButton.setText("Play");
		    PlayButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			    PlayButtonActionPerformed(evt);
			}
		    });
		    add(bg);
		    bg.setLayout(new MigLayout("", "[242px][80px][592px][80px]", "[535px][35px]"));
		    bg.add(ListFiles, "flowx,cell 0 1,growx,aligny top");
		    bg.add(jScrollPane1, "cell 0 0,grow");
		    bg.add(SettingsCancelButton, "cell 3 1,alignx left,aligny top");
		    bg.add(VideoFrame, "cell 1 0 3 1,alignx left,aligny top");
		    bg.add(PlayButton, "cell 0 1,alignx left,aligny top");
		}
	    });
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    public void Load() {
	Parent.VLCPlayer.SetCanvas(vlcoverlay);
    }

    private void SettingsCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SettingsCancelButtonActionPerformed
	Parent.StopVideoPlayer();
	Parent.LoadMainCard();
    }// GEN-LAST:event_SettingsCancelButtonActionPerformed

    private void PlayButtonActionPerformed(java.awt.event.ActionEvent evt) {
	if (Parent.Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    if (!((VideoFile) videoFileListModel.getVideoFileAt(FilesList.getSelectedIndex())).getPath().equalsIgnoreCase("")) {
		Parent.WriteLogtoConsole("Playing Video Clip: " + "/hdd/"
			+ ((VideoFile) videoFileListModel.getVideoFileAt(FilesList.getSelectedIndex())).getPath());
		Parent.VLCPlayer.PlayVideoFile("/hdd/" + ((VideoFile) videoFileListModel.getVideoFileAt(FilesList.getSelectedIndex())).getPath());
	    }
	}
    }

    private void UpdateFilesList() {
	videoFileListModel.clear();
	for (int i = 0; i < Parent.Camera.GetVideoFilesList().size(); i++) {
	    videoFileListModel.addElement(((VideoFile) (Parent.Camera.GetVideoFilesList().get(i))));
	}
    }

    private void ListFilesActionPerformed(java.awt.event.ActionEvent evt) {
	Parent.WriteLogtoConsole("Reading Video Clip List");
	try {
	    Parent.Camera.ReadCameraFileList();
	} catch (Exception ex) {
	    Logger.getLogger(PlaybackDialog.class.getName()).log(Level.SEVERE, null, ex);
	}
	UpdateFilesList();
    }
}
