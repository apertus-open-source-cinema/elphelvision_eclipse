/*! Copyright (C) 2009 Apertus, All Rights Reserved
 *! Author : Apertus Team
 *! Description: Main class of the Elphel Vision viewfinder software for Elphel cameras.
 *! Thanks to Adrian BER and his JJMplayer sources which helped greatly creating this.
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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ElphelVision extends Panel implements ActionListener, Runnable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8377501235045327984L;
    // private static final long serialVersionUID = 21L;
    String AppVersion = "0.58";
    Camera Camera; // class containing all camera specific information
    UserSettings Settings; // class containing user settings
    VLCPlayer VLCPlayer = null; // VLC Video player class dealing with video
				// streaming
    GstreamerPlayer GstreamerPlayer = null; // Gstreamer Video player class
					    // dealing with video streaming
    Thread ReadCameraDataAnimator;
    Thread InfoAreaAnimator;
    Thread HistogramAnimator;
    Thread DatarateAnimator;
    long winid = 0;
    float ReadCameradataFPS = 0.25f;
    float InfoAreaFPS = 0.25f;
    float HistogramFPS = 0.25f; 
    float DatarateFPS = 0.25f;
    JPanel CardManager;
    ConnectDialog ConnectCardLayout;
    MainLayoutGST MaincardLayoutGST;
    MainDialogVLC MaincardLayoutVLC;
    Settings1Layout Settings1CardLayout;
    Settings2Layout Settings2CardLayout;
    Settings3Layout Settings3CardLayout;
    ResolutionSettings ResolutionSettingsCardLayout;
    FPSSettings FPSSettingsCardLayout;
    HistogramSettingsDialog HistogramSettingsCardLayout;
    HITSettings HITSettingsCardLayout;
    NumericalInputPanelFloat NumberPanelFloat;
    NumericalInputPanelInteger NumberPanelInteger;
    NumericalInputPanelIP NumberPanelIP;
    GuidesLayout GuidesPanel;
    PlaybackLayout PlaybackCardLayout;
    PhotoSettingsLayout PhotoSettingsCardLayout;
    static boolean WindowDecorations = false;
    static boolean NoCameraParameter = false;
    Utils Utils;
    static int Debuglevel;

    public static void main(String[] args) {
	JFrame f = new JFrame();
	f.addWindowListener(new java.awt.event.WindowAdapter() {

	    @Override
	    public void windowClosing(java.awt.event.WindowEvent e) {
		System.exit(0);
	    };
	});

	ElphelVision EV = new ElphelVision();
	EV.ProcessArgs(args); // deal with start parameters
	EV.start();

	f.add(EV);
	if (!WindowDecorations) {
	    f.setUndecorated(true);
	}
	f.pack();
	if (!WindowDecorations) {
	    if (EV.Settings.isEVFullscreenMode()) {
		EV.WriteLogtoConsole("Starting in fullscreen mode");

		// for multi screen environments
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		// get size of each screen
		int[] ScreenWidth = new int[gs.length];
		int[] ScreenHeight = new int[gs.length];
		for (int i = 0; i < gs.length; i++) {
		    DisplayMode dm = gs[i].getDisplayMode();
		    ScreenWidth[i] = dm.getWidth();
		    ScreenHeight[i] = dm.getHeight();
		}

		GraphicsDevice gd = ge.getScreenDevices()[0];
		try {
		    if (gd.isFullScreenSupported()) {
			gd.setFullScreenWindow(f);
		    } else {
			EV.WriteLogtoConsole("Fullscreen mode not supported by graphics environment - setting size to screen dimensions as workaround");
			// always use 1st screen for now
			f.setSize(ScreenWidth[0], ScreenHeight[0]);
		    }
		} finally {
		    gd.setFullScreenWindow(null);
		}
	    } else {
		f.setSize(1024, 600);
	    }
	} else {
	    f.setSize(1024, 600 + 20); // add 20, seems enough for the Frame
				       // title,
	}
	f.setVisible(true);
    }

    public ElphelVision() {
	SetConsoleColor(Color.WHITE);
	System.out.println("=====================================================");
	SetConsoleColor(Color.CYAN);
	System.out.println(" ElphelVision - Apertus Viewfinder Software");
	System.out.println(" http://www.apertus.org");
	System.out.println(" Version: " + this.GetAppVersion());
	SetConsoleColor(Color.WHITE);
	System.out.println("=====================================================");
	System.out.println(" ");
	if (NoCameraParameter) {
	    System.out.println("Starting without a connected camera...");
	}

	Utils = new Utils(this);
	Settings = new UserSettings();

	// Load Style Definitions
	WriteLogtoConsole("Looking for default.style");
	File StyleFile = new File("default.style");

	if (StyleFile.exists()) {
	    try {
		if (Settings.LoadStyles("default.style")) {
		    WriteLogtoConsole("default.style found - Styles Loaded");
		} else {
		    WriteWarningtoConsole("Problem loading default.style");
		}
	    } catch (FileNotFoundException ex) {
		Logger.getLogger(ConnectDialog.class.getName()).log(Level.SEVERE, null, ex);
	    }
	} else {
	    WriteWarningtoConsole("default.style not found - falling back to default styles");
	}
	TestGraphicCapability();
    }

    private boolean isShapingSupported;
    private boolean isOpacityControlSupported;
    private boolean isTranslucencySupported;
    private GraphicsConfiguration translucencyCapableGC;

    public GraphicsConfiguration GetTranslucencyCapableGC() {
	return translucencyCapableGC;
    }

    public void TestGraphicCapability() {
	isShapingSupported = AWTUtilitiesWrapper.isTranslucencySupported(AWTUtilitiesWrapper.PERPIXEL_TRANSPARENT);
	isOpacityControlSupported = AWTUtilitiesWrapper.isTranslucencySupported(AWTUtilitiesWrapper.TRANSLUCENT);
	isTranslucencySupported = AWTUtilitiesWrapper.isTranslucencySupported(AWTUtilitiesWrapper.PERPIXEL_TRANSLUCENT);

	translucencyCapableGC = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	if (!AWTUtilitiesWrapper.isTranslucencyCapable(translucencyCapableGC)) {
	    translucencyCapableGC = null;

	    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] devices = env.getScreenDevices();

	    for (int i = 0; i < devices.length && translucencyCapableGC == null; i++) {
		GraphicsConfiguration[] configs = devices[i].getConfigurations();
		for (int j = 0; j < configs.length && translucencyCapableGC == null; j++) {
		    if (AWTUtilitiesWrapper.isTranslucencyCapable(configs[j])) {
			translucencyCapableGC = configs[j];
		    }
		}
	    }
	    if (translucencyCapableGC == null) {
		isTranslucencySupported = false;
	    }
	}

	if (!isTranslucencySupported) {
	    WriteWarningtoConsole("Translucency is not supported by current Graphics Environment");
	} else {
	    WriteLogtoConsole("Translucency is supported by current Graphics Environment");
	}
	if (!isOpacityControlSupported) {
	    WriteWarningtoConsole("OpacityControl is not supported by current Graphics Environment");
	} else {
	    WriteLogtoConsole("OpacityControl is supported by current Graphics Environment");
	}
	if (!isShapingSupported) {
	    WriteWarningtoConsole("PERPIXEL_TRANSPARENT is not supported by current Graphics Environment");
	} else {
	    WriteLogtoConsole("PERPIXEL_TRANSPARENT is supported by current Graphics Environment");
	}
    }

    public void SetDatarateReadFrequency(float newfrequency) {
	this.DatarateFPS = newfrequency;
    }

    public void SetHistogramReadFrequency(float newfrequency) {
	this.HistogramFPS = newfrequency;
    }

    public void SetCameraDataReadFrequency(float newfrequency) {
	this.ReadCameradataFPS = newfrequency;
	this.InfoAreaFPS = newfrequency;
    }

    public void destroy() {
	VLCPlayer.close();
    }

    public int GetDebuglevel() {
	return Debuglevel;
    }

    public void StartVideoPlayer() {
	if (NoCameraParameter) {
	    // TODO we have no test.avi yet
	    // VLCPlayer.PlayFakeStream();
	    // VLCPlayer.PlayLocalVideoFile("test.avi");
	    WriteLogtoConsole("Starting Dummy Video Stream");
	} else {
	    if (Settings.GetVideoPlayer() == streamVideoPlayer.GSTREAMER) {
		GstreamerPlayer.PlayVideoStream();
		WriteLogtoConsole("Starting GStreamer Video");
	    }
	    if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
		VLCPlayer.PlayVideoStream();
		WriteLogtoConsole("Starting VLC Video");
	    }
	}
    }

    public void StopVideoPlayer() {
	if (Settings.GetVideoPlayer() == streamVideoPlayer.GSTREAMER) {
	    GstreamerPlayer.close();
	}
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    VLCPlayer.close();
	}
    }

    private void ProcessArgs(String[] args) {
	Debuglevel = 2; // DEFAULT value

	for (int i = 0; i < args.length; i++) {
	    WindowDecorations = false;

	    if (args[i].equals("--help") || args[i].equals("-h")) {
		PrintHelp();
		System.exit(0);
	    }

	    if (args[i].equals("--no-camera")) {
		NoCameraParameter = true;
	    }

	    if (args[i].equals("--fullscreen")) {
		Settings.setEVFullscreenMode(true);
	    }

	    if (args[i].equals("--debuglevel")) {
		if (args[i + 1].equals("0")) {
		    Debuglevel = 0;
		}
		if (args[i + 1].equals("1")) {
		    Debuglevel = 1;
		}
		if (args[i + 1].equals("2")) {
		    Debuglevel = 2;
		}
		if (args[i + 1].equals("3")) {
		    Debuglevel = 3;
		}
	    }
	}
    }

    public boolean GetNoCameraParameter() {
	return NoCameraParameter;
    }

    static void PrintHelp() {
	System.out.println("ElphelVision Help: ");
	System.out.println("Arguments: ");
	System.out.println("\t-h, --help\tshow this help message.");
	System.out.println("\t--no-camera\tstart without a connected camera for testing purpose");
	System.out.println("\t--debuglevel N\t0 - none, 1 - only errors, 2 - errors and warnings (default), 3 - everything");
	System.out.println("\t--fullscreen \tstart ElphelVision in fullscreen mode");
    }

    public void SetConsoleColor(Color newcolor) {
	if (newcolor == Color.BLACK) {
	    System.out.print("\033[30m");
	} else if (newcolor == Color.WHITE) {
	    System.out.print("\033[39m");
	} else if (newcolor == Color.RED) {
	    System.out.print("\033[31m");
	} else if (newcolor == Color.GREEN) {
	    System.out.print("\033[32m");
	} else if (newcolor == Color.YELLOW) {
	    System.out.print("\033[33m");
	} else if (newcolor == Color.BLUE) {
	    System.out.print("\033[34m");
	} else if (newcolor == Color.MAGENTA) {
	    System.out.print("\033[35m");
	} else if (newcolor == Color.CYAN) {
	    System.out.print("\033[36m");
	}
	/*
	 * ANSI CODES: Black: \033[30m Red: \033[31m Green: \033[32m Yellow:
	 * \033[33m Blue: \033[34m Magenta: \033[35m Cyan: \033[36m White:
	 * \033[37m
	 */
    }

    public void WriteLogtoConsole(String log) {
	if (Debuglevel > 2) {
	    SetConsoleColor(Color.WHITE);
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.S");
	    System.out.println("[" + sdf.format(cal.getTime()) + "] LOG:\033[1m " + log + "\033[22m\033[0m");
	}
    }

    public void WriteWarningtoConsole(String log) {
	if (Debuglevel > 1) {
	    SetConsoleColor(Color.YELLOW);
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.S");
	    System.out.println("[" + sdf.format(cal.getTime()) + "] WARNING: \033[1m" + log + "\033[22m\033[0m");
	}
    }

    public void WriteErrortoConsole(String log) {
	if (Debuglevel > 0) {
	    SetConsoleColor(Color.RED);
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.S");
	    System.out.println("[" + sdf.format(cal.getTime()) + "] ERROR: \033[1m" + log + "\033[22m\033[0m");
	    SetConsoleColor(Color.WHITE);
	}
    }

    public JPanel GetCardManager() {
	return this.CardManager;
    }

    public String GetAppVersion() {
	return AppVersion;
    }

    public void start() {

	if (!NoCameraParameter) {
	    // Start Threads if we are not running with nocamera parameter
	    ReadCameraDataAnimator = new Thread(this);
	    HistogramAnimator = new Thread(this);
	    InfoAreaAnimator = new Thread(this);
	    DatarateAnimator = new Thread(this);
	}

	// Init everything
	Camera = new Camera(this);

	// Check host OS
	String osname = System.getProperty("os.name");
	if (osname.startsWith("Windows")) {
	    Settings.SetOS(OStype.Windows);
	} else {
	    Settings.SetOS(OStype.Linux);
	}

	// TODO: check if video players are installed and at proper version

	setLayout(new BorderLayout());

	GstreamerPlayer = new GstreamerPlayer(this);
	VLCPlayer = new VLCPlayer(this);

	// Create the panel that contains the "cards".
	CardManager = new JPanel(new CardLayout());

	ConnectCardLayout = new ConnectDialog(this);
	MaincardLayoutGST = new MainLayoutGST(this);
	MaincardLayoutVLC = new MainDialogVLC(this);
	Settings1CardLayout = new Settings1Layout(this);
	Settings2CardLayout = new Settings2Layout(this);
	Settings3CardLayout = new Settings3Layout(this);
	ResolutionSettingsCardLayout = new ResolutionSettings(this);
	HistogramSettingsCardLayout = new HistogramSettingsDialog(this);
	FPSSettingsCardLayout = new FPSSettings(this);
	HITSettingsCardLayout = new HITSettings(this);
	NumberPanelInteger = new NumericalInputPanelInteger(this);
	NumberPanelFloat = new NumericalInputPanelFloat(this);
	NumberPanelIP = new NumericalInputPanelIP(this);
	GuidesPanel = new GuidesLayout(this);
	PlaybackCardLayout = new PlaybackLayout(this);
	PhotoSettingsCardLayout = new PhotoSettingsLayout(this);

	CardManager.add(ConnectCardLayout, "ConnectCard");
	CardManager.add(MaincardLayoutGST, "MainCardGST");
	CardManager.add(MaincardLayoutVLC, "MainCardVLC");
	CardManager.add(Settings1CardLayout, "Settings1Card");
	CardManager.add(Settings2CardLayout, "Settings2Card");
	CardManager.add(Settings3CardLayout, "Settings3Card");
	CardManager.add(ResolutionSettingsCardLayout, "CustomResolutionCard");
	CardManager.add(FPSSettingsCardLayout, "CustomFPSCard");
	CardManager.add(HITSettingsCardLayout, "HITSettingsCard");
	CardManager.add(GuidesPanel, "GuidesCard");
	CardManager.add(NumberPanelFloat, "NumberpanelFloat");
	CardManager.add(NumberPanelIP, "NumberpanelIP");
	CardManager.add(NumberPanelInteger, "NumberpanelInteger");
	CardManager.add(PlaybackCardLayout, "PlaybackCard");
	CardManager.add(PhotoSettingsCardLayout, "PhotoSettings");
	CardManager.add(HistogramSettingsCardLayout, "HistogramSettings");

	add(CardManager);
    }

    public void LoadMainCard() {
	CardLayout cl = (CardLayout) (GetCardManager().getLayout());
	if (Settings.GetVideoPlayer() == streamVideoPlayer.GSTREAMER) {
	    MaincardLayoutGST.Load();
	    cl.show(GetCardManager(), "MainCardGST");
	}
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    MaincardLayoutVLC.Load();
	    cl.show(GetCardManager(), "MainCardVLC");
	}
    }

    public void UpdateOverlayPosition() {
	if (MaincardLayoutGST.isShowing() || MaincardLayoutVLC.isShowing()) {
	    if (Settings.GetVideoPlayer() == streamVideoPlayer.GSTREAMER) {
		MaincardLayoutGST.UpdateOverlayPosition();
	    }
	    if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
		MaincardLayoutVLC.UpdateOverlayPosition();
	    }
	}
    }

    public void PostConnect() {
	this.WriteLogtoConsole("looking for autosave.config to read camera parameters");
	if (LoadConfigFile("autosave.config")) {
	    this.WriteLogtoConsole("autosave.config loaded successfully");
	} else {
	    this.WriteWarningtoConsole("autosave.config not found, falling back to default.config");
	    if (LoadConfigFile("default.config")) {
		this.WriteLogtoConsole("default.config loaded successfully");
	    } else {
		this.WriteErrortoConsole("default.config not found");
	    }
	}

	// turn off autoexposure by default
	// TODO: make chooseable
	Camera.SetAutoExposure(false);
	if (!NoCameraParameter) {
	    if (!ReadCameraDataAnimator.isAlive()) {
		ReadCameraDataAnimator.start();
	    }
	    if (!HistogramAnimator.isAlive()) {
		HistogramAnimator.start();
	    }
	    if (!DatarateAnimator.isAlive()) {
		DatarateAnimator.start();
	    }

	    InitInfoArea();
	    if (!InfoAreaAnimator.isAlive()) {
		InfoAreaAnimator.start();
	    }
	}
	// run();
    }

    private boolean LoadConfigFile(String Filename) {
	File configfile = new File(Filename);
	if (configfile.exists()) {
	    try {
		Camera.ReadConfigFile(Filename);
		return true;
	    } catch (FileNotFoundException ex) {
		return false;
	    }
	} else {
	    return false;
	}
    }

    public void run() {
	if (!this.GetNoCameraParameter()) {
	    if (Camera.GetCameraConnectionEstablished()) {
		while (Thread.currentThread() == ReadCameraDataAnimator) {
		    ReadCameraData();
		    try {
			Thread.sleep((int) (1.0f / ReadCameradataFPS * 1000.0f));
		    } catch (InterruptedException e) {
			break;
		    }
		}

		while (Thread.currentThread() == HistogramAnimator) {
		    if (Camera != null) {
			Camera.ReadHistogram();
			if (MaincardLayoutGST != null) {
			    if (Settings.GetVideoPlayer() == streamVideoPlayer.GSTREAMER) {
				MaincardLayoutGST.RedrawHistogram();
			    }
			}
			if (MaincardLayoutVLC != null) {
			    if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
				MaincardLayoutVLC.RedrawHistogram();
			    }
			}
		    }
		    try {
			Thread.sleep((int) (1.0f / HistogramFPS * 1000.0f));
		    } catch (InterruptedException e) {
			break;
		    }
		}

		while (Thread.currentThread() == InfoAreaAnimator) {
		    if (Camera != null) {
			UpdateInfoArea();
		    }
		    try {
			Thread.sleep((int) (1.0f / InfoAreaFPS * 1000.0f));
		    } catch (InterruptedException e) {
			break;
		    }
		}

		while (Thread.currentThread() == DatarateAnimator) {
		    if (Camera != null) {
			Camera.ReadFrameSizeBytes();
		    }
		    try {
			Thread.sleep((int) (1.0f / DatarateFPS * 1000.0f));
		    } catch (InterruptedException e) {
			break;
		    }
		}
	    }
	}
    }

    public void ReadCameraData() {
	try {
	    Camera.UpdateCameraData();
	} catch (Exception ex) {
	}
    }

    Style StyleRed = null;
    Style StyleNormal = null;
    Style StyleBigFont = null;

    public void InitInfoArea() {
	// redesign
	StyledDocument ResolutionDoc = null;
	StyledDocument FPSDoc = null;
	StyledDocument WBDoc = null;
	StyledDocument QualityDoc = null;
	StyledDocument HDDDoc = null;
	StyledDocument RecordDoc = null;

	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    ResolutionDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaResolution().getDocument();
	    FPSDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaFPS().getDocument();
	    WBDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaWB().getDocument();
	    QualityDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaQuality().getDocument();
	    HDDDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaHDD().getDocument();
	    RecordDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaRecord().getDocument();
	}

	// Style definitions
	StyleBigFont = ResolutionDoc.addStyle("BigFont", null);
	StyleConstants.setForeground(StyleBigFont, Color.white);
	StyleConstants.setBold(StyleBigFont, true);
	StyleConstants.setFontFamily(StyleBigFont, "SansSerif");
	StyleConstants.setFontSize(StyleBigFont, 18);

	StyleNormal = ResolutionDoc.addStyle("NormalText", null);
	StyleConstants.setForeground(StyleNormal, Color.white);
	StyleConstants.setBold(StyleNormal, false);
	StyleConstants.setFontSize(StyleNormal, 10);

	StyleRed = ResolutionDoc.addStyle("RedNotice", null);
	StyleConstants.setForeground(StyleRed, Color.red);
	StyleConstants.setFontFamily(StyleRed, "SansSerif");
	StyleConstants.setBold(StyleRed, true);
	StyleConstants.setFontSize(StyleRed, 18);

	// center them
	MutableAttributeSet center = new SimpleAttributeSet();
	StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
	ResolutionDoc.setParagraphAttributes(0, 0, center, true);
	FPSDoc.setParagraphAttributes(0, 0, center, true);
	WBDoc.setParagraphAttributes(0, 0, center, true);
	QualityDoc.setParagraphAttributes(0, 0, center, true);
	HDDDoc.setParagraphAttributes(0, 0, center, true);
	RecordDoc.setParagraphAttributes(0, 0, center, true);

	// Notice Area
	StyledDocument docNotice = null;
	if (Settings.GetVideoPlayer() == streamVideoPlayer.GSTREAMER) {
	    docNotice = (StyledDocument) MaincardLayoutGST.GetNoticeTextPane().getDocument();
	}
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    docNotice = (StyledDocument) MaincardLayoutVLC.GetNoticeTextPane().getDocument();
	}
	Style NoticeStyleRed = docNotice.addStyle("RedNotice", null);
	StyleConstants.setForeground(NoticeStyleRed, Color.red);
	StyleConstants.setBold(NoticeStyleRed, true);

	MutableAttributeSet standardNotice = new SimpleAttributeSet();
	StyleConstants.setAlignment(standardNotice, StyleConstants.ALIGN_CENTER);
	docNotice.setParagraphAttributes(0, 0, standardNotice, true);
    }

    public void UpdateInfoArea() {
	// Resolution
	MaincardLayoutVLC.GetInfoAreaResolution().setText("");
	StyledDocument ResolutionDoc = null;
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    ResolutionDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaResolution().getDocument();
	}
	String InfoAreaResolution = "";
	if (Camera.GetPreset() == CameraPreset.FULL) {
	    InfoAreaResolution += "FULL\n";
	} else if (Camera.GetPreset() == CameraPreset.CIMAX) {
	    InfoAreaResolution += "CIMAX\n";
	} else if (Camera.GetPreset() == CameraPreset.AMAX) {
	    InfoAreaResolution += "AMAX\n";
	} else if (Camera.GetPreset() == CameraPreset.FULLHD) {
	    InfoAreaResolution += "1080p\n";
	} else if (Camera.GetPreset() == CameraPreset.SMALLHD) {
	    InfoAreaResolution += "720p\n";
	} else if (Camera.GetPreset() == CameraPreset.CUSTOM) {
	    InfoAreaResolution += "CUSTOM\n";
	}

	try {
	    ResolutionDoc.insertString(ResolutionDoc.getLength(), InfoAreaResolution, StyleBigFont);
	} catch (BadLocationException ex) {
	    Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	}
	InfoAreaResolution = Camera.GetImageWidth() + "x" + Camera.GetImageHeight();
	try {
	    ResolutionDoc.insertString(ResolutionDoc.getLength(), InfoAreaResolution, StyleNormal);
	} catch (BadLocationException ex) {
	    Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	}

	// FPS
	MaincardLayoutVLC.GetInfoAreaFPS().setText("");
	StyledDocument FPSDoc = null;
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    FPSDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaFPS().getDocument();
	}
	String InfoAreaFPSLine1 = "";
	String InfoAreaFPSLine2 = "";
	if (Camera.GetFPSSkipFrames() != 0) {
	    InfoAreaFPSLine1 += Utils.Round(Camera.GetFPS() / (1.0f + Camera.GetFPSSkipFrames()), 2) + " FPS\n";
	    InfoAreaFPSLine2 += "FrameSkip";
	} else if (Camera.GetFPSSkipSeconds() != 0) {
	    InfoAreaFPSLine1 += Utils.Round((1.0f / Camera.GetFPSSkipSeconds()), 2) + " FPS\n";
	    InfoAreaFPSLine2 += "SecondsSkip";
	} else {
	    InfoAreaFPSLine1 += Utils.Round(Camera.GetFPS(), 2) + " FPS\n";
	}

	if (Camera.getFrameTrigger() == Trigger.FREERUNNING) {
	    InfoAreaFPSLine2 += " Freerun";
	} else if (Camera.getFrameTrigger() == Trigger.TRIGGERED) {
	    InfoAreaFPSLine2 += " Trigger";
	}

	try {
	    FPSDoc.insertString(FPSDoc.getLength(), InfoAreaFPSLine1, StyleBigFont);
	    FPSDoc.insertString(FPSDoc.getLength(), InfoAreaFPSLine2, StyleNormal);
	} catch (BadLocationException ex) {
	    Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	}

	// WB
	MaincardLayoutVLC.GetInfoAreaWB().setText("");
	StyledDocument WBDoc = null;
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    WBDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaWB().getDocument();
	}
	String InfoAreaWBLine1 = "";
	String InfoAreaWBLine2 = "";

	InfoAreaWBLine1 += Camera.GetWhiteBalance() + "\n";
	InfoAreaWBLine2 += "WB";

	try {
	    WBDoc.insertString(WBDoc.getLength(), InfoAreaWBLine1, StyleBigFont);
	    WBDoc.insertString(WBDoc.getLength(), InfoAreaWBLine2, StyleNormal);
	} catch (BadLocationException ex) {
	    Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	}

	// Quality
	MaincardLayoutVLC.GetInfoAreaQuality().setText("");
	StyledDocument QualityDoc = null;
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    QualityDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaQuality().getDocument();
	}
	String InfoAreaQualityLine1 = "";
	String InfoAreaQualityLine2 = "";

	InfoAreaQualityLine1 += Camera.GetImageJPEGQuality() + " %\n";
	InfoAreaQualityLine2 += "Quality";

	try {
	    QualityDoc.insertString(QualityDoc.getLength(), InfoAreaQualityLine1, StyleBigFont);
	    QualityDoc.insertString(QualityDoc.getLength(), InfoAreaQualityLine2, StyleNormal);
	} catch (BadLocationException ex) {
	    Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	}

	// HDD
	MaincardLayoutVLC.GetInfoAreaHDD().setText("");
	StyledDocument HDDDoc = null;
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    HDDDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaHDD().getDocument();
	}
	String InfoAreaHDDLine1 = "";
	String InfoAreaHDDLine2 = "";
	if (Camera.GetFreeHDDSpace() == -1) {
	    InfoAreaHDDLine1 += "none\n"; // No HDD attached/detected
	    InfoAreaHDDLine2 += "no HDD found";
	    if (Settings.GetVideoPlayer() == streamVideoPlayer.GSTREAMER) {
		MaincardLayoutGST.EnableRecord(false); // disable Rec Button
	    }
	    if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
		MaincardLayoutVLC.EnableRecord(false); // disable Rec Button
	    }
	    try {
		HDDDoc.insertString(HDDDoc.getLength(), InfoAreaHDDLine1, StyleRed);
		HDDDoc.insertString(HDDDoc.getLength(), InfoAreaHDDLine2, StyleNormal);
	    } catch (BadLocationException ex) {
		Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	    }

	} else {
	    InfoAreaHDDLine1 += Camera.GetFreeHDDRatio() + " %\n"; //
	    InfoAreaHDDLine2 += "Free HDD Space";
	    try {
		HDDDoc.insertString(HDDDoc.getLength(), InfoAreaHDDLine1, StyleBigFont);
		HDDDoc.insertString(HDDDoc.getLength(), InfoAreaHDDLine2, StyleNormal);
	    } catch (BadLocationException ex) {
		Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

	// Record
	MaincardLayoutVLC.GetInfoAreaRecord().setText("");
	StyledDocument RecordDoc = null;
	if (Settings.GetVideoPlayer() == streamVideoPlayer.VLC) {
	    RecordDoc = (StyledDocument) MaincardLayoutVLC.GetInfoAreaRecord().getDocument();
	}
	String InfoAreaRecordLine1 = "";
	String InfoAreaRecordLine2 = "";

	if (Camera.GetCamogmState() == CamogmState.RECORDING) {
	    InfoAreaRecordLine1 += "Recording\n";
	    InfoAreaRecordLine2 += Camera.getRecordClipName() + " Frame#: " + Camera.GetRecordedFramesCount();
	    try {
		RecordDoc.insertString(RecordDoc.getLength(), InfoAreaRecordLine1, StyleRed);
		RecordDoc.insertString(RecordDoc.getLength(), InfoAreaRecordLine2, StyleNormal);
	    } catch (BadLocationException ex) {
		Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	    }
	} else {
	    InfoAreaRecordLine1 += "Standby\n";
	    InfoAreaRecordLine2 += "Record";
	    try {
		RecordDoc.insertString(RecordDoc.getLength(), InfoAreaRecordLine1, StyleBigFont);
		RecordDoc.insertString(RecordDoc.getLength(), InfoAreaRecordLine2, StyleNormal);
	    } catch (BadLocationException ex) {
		Logger.getLogger(ElphelVision.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    public void actionPerformed(ActionEvent e) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}
