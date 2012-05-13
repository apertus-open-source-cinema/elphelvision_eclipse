/*! Copyright (C) 2009 Apertus, All Rights Reserved
 *! Author : Apertus Team
 *! Description: Main GUI layout class of the Elphel Vision viewfinder software for Elphel cameras.
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

//import com.sun.opengl.util.Animator;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JTextField;

public class ConnectDialog extends javax.swing.JPanel {

    private ElphelVision Parent;

    // private Animator IntroAnimator;

    public ConnectDialog(ElphelVision parent) {

	Parent = parent;

	try {
	    java.awt.EventQueue.invokeAndWait(new Runnable() {

		public void run() {
		    bg = new javax.swing.JPanel();
		    TitleBackground = new javax.swing.JPanel();
		    Title = new javax.swing.JLabel();
		    Image = new javax.swing.JLabel();

		    setBackground(new java.awt.Color(0, 0, 0));
		    setForeground(new java.awt.Color(255, 255, 255));
		    setPreferredSize(new java.awt.Dimension(1024, 600));

		    bg.setBackground(new java.awt.Color(0, 0, 0));
		    bg.setForeground(new java.awt.Color(0, 0, 0));
		    bg.setPreferredSize(new java.awt.Dimension(1024, 600));
		    bg.setLayout(new MigLayout("", "[1024px,grow]", "[233.00px][155px][55.00,grow 30,shrink 30][]"));

		    JPanel ConnectPanel = new JPanel();
		    ConnectPanel.setBackground(Color.BLACK);
		    bg.add(ConnectPanel, "cell 0 1,alignx center,growy");
		    ConnectPanel.setLayout(new MigLayout("", "[][][50px][][][]", "[][][][]"));
		    Camera1IPLabel = new javax.swing.JLabel();
		    ConnectPanel.add(Camera1IPLabel, "cell 0 0,grow");

		    Camera1IPLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
		    Camera1IPLabel.setForeground(new java.awt.Color(255, 255, 255));
		    Camera1IPLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		    Camera1IPLabel.setText("Camera IP: ");
		    CameraIP = new javax.swing.JTextField();
		    CameraIP.setPreferredSize(new Dimension(150, 20));
		    ConnectPanel.add(CameraIP, "cell 1 0,grow");

		    CameraIP.setText("192.168.0.9");
		    CameraIP.setMargin(new java.awt.Insets(0, 4, 0, 0));
		    IP_type = new EButton(Parent);
		    IP_type.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    	    
		    	}
		    });
		    ConnectPanel.add(IP_type, "cell 2 0,grow");

		    IP_type.setFont(new java.awt.Font("DejaVu Sans", 0, 10));
		    IP_type.setText("type");
		    IP_type.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			    IP_typeMouseClicked(evt);
			}
		    });
		    ConnectButton = new EButton(Parent);
		    ConnectButton.setSize(new Dimension(80, 35));
		    ConnectPanel.add(ConnectButton, "cell 3 0 1 2,grow");

		    ConnectButton.setText("Connect");
		    ConnectButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			    ConnectButtonMouseClicked(evt);
			}
		    });
		    ConnectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			    ConnectButtonActionPerformed(evt);
			}
		    });
		    Stereo3DButton = new EButton(Parent);
		    ConnectPanel.add(Stereo3DButton, "cell 0 1,grow");

		    Stereo3DButton.setBackground(new java.awt.Color(254, 254, 254));
		    Stereo3DButton.setText("Stereo 3D");
		    CameraIP2 = new javax.swing.JTextField();
		    ConnectPanel.add(CameraIP2, "cell 1 1,grow");

		    CameraIP2.setText("192.168.0.9");
		    CameraIP2.setEnabled(false);
		    CameraIP2.setMargin(new java.awt.Insets(0, 4, 0, 0));
		    IP2_type = new EButton(Parent);
		    ConnectPanel.add(IP2_type, "cell 2 1,grow");

		    IP2_type.setEnabled(false);
		    IP2_type.setFont(new java.awt.Font("DejaVu Sans", 0, 10));
		    IP2_type.setText("type");
		    VLCButton = new EButton(Parent);
		    VLCButton.setMaximumSize(new Dimension(80, 35));
		    ConnectPanel.add(VLCButton, "cell 0 3,alignx left,growy");
		    VLCButton.setText("VLC");
		    GstreamerButton = new EButton(Parent);
		    GstreamerButton.setToolTipText("Gstreamer is still experimental  - DONT USE IT");
		    ConnectPanel.add(GstreamerButton, "cell 1 3,alignx left,growy");

		    GstreamerButton.setText("Gstreamer");
		    jLabel1 = new javax.swing.JLabel();
		    ConnectPanel.add(jLabel1, "cell 2 3 2 1,grow");

		    jLabel1.setForeground(new java.awt.Color(255, 0, 0));
		    jLabel1.setText("Gstreamer is experimental");
		    GstreamerButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			    GstreamerButtonMouseClicked(evt);
			}
		    });
		    VLCButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			    VLCButtonMouseClicked(evt);
			}
		    });
		    IP2_type.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			    IP2_typeMouseClicked(evt);
			}
		    });
		    Stereo3DButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			    Stereo3DButtonMouseClicked(evt);
			}
		    });
		    setLayout(new BorderLayout(0, 0));

		    TitleBackground.setBackground(new java.awt.Color(0, 0, 0));

		    Title.setFont(new java.awt.Font("Tahoma", 0, 14));
		    Title.setForeground(new java.awt.Color(255, 255, 255));
		    Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		    Title.setText("Elphel Vision Alpha  V");

		    Image.setBackground(new java.awt.Color(0, 0, 0));
		    Image.setFont(new java.awt.Font("Tahoma", 0, 14));
		    Image.setForeground(new java.awt.Color(255, 255, 255));
		    Image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		    Image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/apertus.png")));

		    bg.add(TitleBackground, "cell 0 0,grow");
		    bg.setBackground(Parent.Settings.GetPanelBackgroundColor());
		    TitleBackground.setBackground(Parent.Settings.GetPanelBackgroundColor());
		    Title.setForeground(Parent.Settings.GetTextColor());

		    Title.setText("Elphel Vision Alpha " + Parent.getAppVersion());
		    TitleBackground.setLayout(new MigLayout("", "[979.00px,grow]", "[213px][25px]"));
		    TitleBackground.add(Image, "cell 0 0,alignx center,aligny center");
		    TitleBackground.add(Title, "cell 0 1,grow");
		    Camera1IPLabel.setForeground(Parent.Settings.GetTextColor());
		    VLCButton.setChecked(true);
		    ExitButton = new EButton(Parent);
		    bg.add(ExitButton, "cell 0 3,alignx right");

		    ExitButton.setForeground(new java.awt.Color(217, 2, 2));
		    ExitButton.setText("Exit");
		    add(bg);
		    ExitButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
			    ExitButtonMouseClicked(evt);
			}
		    });
		    if (Parent.Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
		    }
		    if (Parent.Settings.GetVideoPlayer() == streamVideoPlayer.GSTREAMER) {

		    }

		    // Title.setText("Elphel Vision Alpha V" +
		    // Parent.GetAppVersion());
		    // AnimationPanel.addGLEventListener(new
		    // JoglIntroAnimation());
		    // IntroAnimator = new Animator(AnimationPanel);
		    // IntroAnimator.start(); // JOGL is still troublesome so
		    // disabled for now

		}
	    });

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	new Thread() {

	    public void run() {
		Parent.WriteLogtoConsole("Looking for autosave.config to read IP");
		File AutoSaveFile = new File("autosave.config");

		if (AutoSaveFile.exists()) {
		    try {
			ArrayList IPs = Parent.Camera.ReadConfigFileIP("autosave.config");
			CameraIP.setText((String) IPs.get(0));
			Parent.WriteLogtoConsole("autosave.config found - IP loaded");
			if (IPs.size() > 1) {
			    Stereo3DButton.setChecked(true);
			    CameraIP2.setEnabled(true);
			    CameraIP2.setText((String) IPs.get(1));
			    Parent.WriteLogtoConsole("second IP found in autosave.config - enabling Stereo3D");
			}
		    } catch (FileNotFoundException ex) {
			Logger.getLogger(ConnectDialog.class.getName()).log(Level.SEVERE, null, ex);
		    }
		} else {
		    Parent.WriteWarningtoConsole("autosave.config not found: falling back to default.config");
		    try {
			String IP = (String) Parent.Camera.ReadConfigFileIP("default.config").get(0);
			if (IP != null) {
			    CameraIP.setText(IP);
			    Parent.WriteLogtoConsole("default.config found: read IP: " + IP);
			} else {
			    Parent.WriteWarningtoConsole("default.config not found: using 192.168.0.9 as default IP");
			}
		    } catch (FileNotFoundException ex) {
			Parent.WriteWarningtoConsole("default.config not found: using 192.168.0.9 as default IP");
			Logger.getLogger(ConnectDialog.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
	    }
	}.start();

	// Tests to deal with keyboard shortcuts
	ActionListener actionListener = new ActionListener() {

	    public void actionPerformed(ActionEvent actionEvent) {
		Parent.WriteLogtoConsole("keypressed: " + actionEvent.paramString());
	    }
	};
	KeyStroke up = KeyStroke.getKeyStroke('c');
	this.registerKeyboardAction(actionListener, "c childfocus", up, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	this.registerKeyboardAction(actionListener, "c focus", up, JComponent.WHEN_IN_FOCUSED_WINDOW);
	this.requestFocus();
    }

    private void Stereo3DButtonMouseClicked(java.awt.event.MouseEvent evt) {
	Stereo3DButton.ToggleChecked();

	if (Stereo3DButton.isChecked()) {
	    CameraIP2.setEnabled(true);
	    IP2_type.setEnabled(true);
	} else {
	    CameraIP2.setEnabled(false);
	    IP2_type.setEnabled(false);
	}
    }

    private void IP_typeMouseClicked(java.awt.event.MouseEvent evt) {
	Parent.IPInputDialog.Load("Camera IP", CameraIP.getText(), CameraIP, "ConnectCard");
	CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
	cl.show(Parent.GetCardManager(), "IPInputDialog");
    }

    boolean Fake3D = false; // for debugging 3D settings with a single camera

    private void ConnectButtonMouseClicked(java.awt.event.MouseEvent evt) {
	ConnectButton.setText("Connecting");
	new Thread() {

	    public void run() {
		if (!Parent.GetNoCameraParameter()) {
		    if (!Fake3D) {
			if (CameraIP.getText().equals(CameraIP2.getText())) {
			    Parent.WriteWarningtoConsole("Trying to connect to Dual Camera Stereo 3D setup with single IP, assuming single camera setup: "
				    + CameraIP.getText());
			    Stereo3DButton.setChecked(false);
			}
		    }

		    if (Stereo3DButton.isChecked()) {
			Parent.WriteLogtoConsole("Trying to connect to Dual Camera Stereo 3D setup: " + CameraIP.getText() + " and "
				+ CameraIP2.getText());
		    } else {
			Parent.WriteLogtoConsole("Trying to connect to: " + CameraIP.getText());
		    }
		}

		if (!Parent.GetNoCameraParameter()) {
		    try {
			if (Stereo3DButton.isChecked()) {
			    Parent.Camera.SetIP(new String[] { CameraIP.getText(), CameraIP2.getText() });
			} else {
			    Parent.Camera.SetIP(new String[] { CameraIP.getText() });
			}
			Parent.Camera.InitCameraConnection();
			for (int i = 0; i < Parent.Camera.GetIP().length; i++) {
			    if (Parent.Camera.PingCamera(i)) {
				Parent.WriteLogtoConsole("Connection to: " + Parent.Camera.GetIP()[i] + " established");
				while (!Parent.Camera.InitCameraServices(i)) {
				    Thread.sleep(500); // since we are in our
						       // own thread its safe
						       // to do this
				}

				Parent.WriteLogtoConsole("Checking Camera(" + Parent.Camera.GetIP()[i] + ") connected HDD");
				if (Parent.Camera.CheckHDD()) {
				    Parent.WriteLogtoConsole("HDD detected");
				} else {
				    Parent.WriteWarningtoConsole("HDD detection failed");
				}
			    } else {
				Parent.WriteErrortoConsole("ConnectButtonActionPerformed() Connecting to: " + CameraIP.getText() + " failed");
			    }
			}
			Parent.PostConnect();
			if (Stereo3DButton.isChecked() && !Fake3D) {
			    Parent.Camera.InitStereo3DSettings();
			}
			if (VLCButton.isChecked()) {
			    Parent.WriteLogtoConsole("Loading Main Window with VLC Player");
			    Parent.MaincardLayoutVLC.Load();
			    CardLayout cl = (CardLayout) (Parent.CardManager.getLayout());
			    cl.show(Parent.CardManager, "MainCardVLC");
			}
			if (GstreamerButton.isChecked()) {
			    Parent.WriteLogtoConsole("Loading Main Window with Gstreamer");
			    Parent.MaincardLayoutGST.Load();
			    CardLayout cl = (CardLayout) (Parent.CardManager.getLayout());
			    cl.show(Parent.CardManager, "MainCardGST");
			}
		    } catch (Exception e) {
			Parent.WriteErrortoConsole("ConnectButtonActionPerformed() Connecting failed: " + e.getMessage());
		    }
		} else {
		    // no camera connected
		    Parent.WriteLogtoConsole("Loading Main Window without connected camera");
		    Parent.MaincardLayoutVLC.Load();
		    CardLayout cl = (CardLayout) (Parent.CardManager.getLayout());
		    cl.show(Parent.CardManager, "MainCardVLC");
		}
	    }
	}.start();
    }

    private void GstreamerButtonMouseClicked(java.awt.event.MouseEvent evt) {
	VLCButton.setChecked(false);
	GstreamerButton.setChecked(true);
	Parent.Settings.SetVideoPlayer(streamVideoPlayer.GSTREAMER);
    }

    private void VLCButtonMouseClicked(java.awt.event.MouseEvent evt) {
	VLCButton.setChecked(true);
	GstreamerButton.setChecked(false);
	Parent.Settings.SetVideoPlayer(streamVideoPlayer.VLC);
    }

    private void IP2_typeMouseClicked(java.awt.event.MouseEvent evt) {
	Parent.IPInputDialog.Load("Camera 2 IP", CameraIP2.getText(), CameraIP2, "ConnectCard");
	CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
	cl.show(Parent.GetCardManager(), "IPInputDialog");
    }

    private void ExitButtonMouseClicked(java.awt.event.MouseEvent evt) {
	System.exit(0);
    }

    private void ConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {
	// TODO add your handling code here:
    }

    private javax.swing.JLabel Camera1IPLabel;
    private javax.swing.JTextField CameraIP;
    private javax.swing.JTextField CameraIP2;
    private EButton ConnectButton;
    private EButton ExitButton;
    private EButton GstreamerButton;
    private EButton IP2_type;
    private EButton IP_type;
    private javax.swing.JLabel Image;
    private EButton Stereo3DButton;
    private javax.swing.JLabel Title;
    private javax.swing.JPanel TitleBackground;
    private EButton VLCButton;
    private javax.swing.JPanel bg;
    private javax.swing.JLabel jLabel1;
}
