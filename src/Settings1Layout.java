



import java.awt.CardLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Settings1Layout extends javax.swing.JPanel implements Runnable {

    ElphelVision Parent;
    Thread SettingsOVerviewUpdater;
    float UpdaterFPS = 0.5f;

    public Settings1Layout(ElphelVision parent) {
        Parent = parent;

        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {

                public void run() {
                    initComponents();
                    bg.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    ResolutionPanel.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    ResolutionLabel.setForeground(Parent.Settings.GetTextColor());
                    FPSPanel.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    JPEGQualityPanel.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    FPSLabel.setForeground(Parent.Settings.GetTextColor());
                    JPEGLabel.setForeground(Parent.Settings.GetTextColor());
                    ColorModeLabel.setForeground(Parent.Settings.GetTextColor());
                    ColorModePanel.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    RecordFormatPanel.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    WBLabel.setForeground(Parent.Settings.GetTextColor());
                    WBPanel.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    SettingsOverview.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    NavigationPanel.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    Experimental.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    CoringLabel.setForeground(Parent.Settings.GetTextColor());
                    ExperimentalLabel.setForeground(Parent.Settings.GetTextColor());
                    DatarateMonitor.setForeground(Parent.Settings.GetTextColor());
                    DatarateMonitor.setBackground(Parent.Settings.GetPanelBackgroundColor());
                    FormatLabel.setForeground(Parent.Settings.GetTextColor());
                    jLabel7.setForeground(Parent.Settings.GetTextColor());
                    jLabel8.setForeground(Parent.Settings.GetTextColor());
                    jLabel9.setForeground(Parent.Settings.GetTextColor());
                    jLabel10.setForeground(Parent.Settings.GetTextColor());
                    jLabel11.setForeground(Parent.Settings.GetTextColor());
                    jLabel12.setForeground(Parent.Settings.GetTextColor());
                    jLabel13.setForeground(Parent.Settings.GetTextColor());
                    jLabel14.setForeground(Parent.Settings.GetTextColor());
                    jLabel15.setForeground(Parent.Settings.GetTextColor());
                    jLabel18.setForeground(Parent.Settings.GetTextColor());
                    Overview_Resolution.setForeground(Parent.Settings.GetTextColor());
                    Overview_FPS.setForeground(Parent.Settings.GetTextColor());
                    Overview_JPEGQ.setForeground(Parent.Settings.GetTextColor());
                    Overview_ColorMode.setForeground(Parent.Settings.GetTextColor());
                    Overview_Format.setForeground(Parent.Settings.GetTextColor());
                    Overview_FreeSpace.setForeground(Parent.Settings.GetTextColor());
                    Overview_RecCapacity.setForeground(Parent.Settings.GetTextColor());
                    Overview_CoringIndexY.setForeground(Parent.Settings.GetTextColor());
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DatarateMonitor.SetParent(Parent);
    }

    private void startUpdater() {
        SettingsOVerviewUpdater = new Thread(this);
        SettingsOVerviewUpdater.start();
    }

    public void run() {
        if (!Parent.GetNoCameraParameter()) {
            while (Thread.currentThread() == SettingsOVerviewUpdater) {

                Overview_Resolution.setText(Parent.Camera.GetImageWidth() + " x " + Parent.Camera.GetImageHeight());

                if (Parent.Camera.GetFPSSkipFrames() != 0) {
                    Overview_FPS.setText(Utils.Round(Parent.Camera.GetFPS() / (1.0f + Parent.Camera.GetFPSSkipFrames()), 3) + " (FS)");
                } else if (Parent.Camera.GetFPSSkipSeconds() != 0) {
                    Overview_FPS.setText(Utils.Round((1.0f / Parent.Camera.GetFPSSkipSeconds()), 3) + " (SS)");
                } else {
                    Overview_FPS.setText(Parent.Camera.GetFPS() + "");
                }

                Overview_JPEGQ.setText(Parent.Camera.GetJPEGQuality() + " %");
                if (Parent.Camera.GetColorMode() == ColorMode.JP4) {
                    Overview_ColorMode.setText("JP4 RAW");
                } else if (Parent.Camera.GetColorMode() == ColorMode.JP46) {
                    Overview_ColorMode.setText("JP46 RAW");
                } else {
                    Overview_ColorMode.setText("RGB");
                }
                if (Parent.Camera.GetRecordFormat() == RecordFormat.MOV) {
                    Overview_Format.setText("Quicktime MOV");
                } else {
                    Overview_Format.setText("Image Sequence");
                }
                Overview_FreeSpace.setText(Utils.Round(Parent.Camera.GetFreeHDDSpace() / 1024.0f, 2) + " GB");
                float capacity = Parent.Camera.GetFreeHDDSpace() / ((float) (Parent.Camera.GetFrameSizeBytes()) / 1024.0f / 1024.0f * (float) (Parent.Camera.GetFPS()) * 3600.0f);
                Overview_RecCapacity.setText(Utils.Round(capacity, 2) + " h");
                Overview_CoringIndexY.setText(Parent.Camera.GetCoringIndexY() + "");
                Overview_CoringIndexC.setText(Parent.Camera.GetCoringIndexC() + "");

                repaint();

                try {
                    Thread.sleep((int) (1.0f / UpdaterFPS * 1000.0f));
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public void Load() {
        startUpdater();

        if (Parent.Camera.GetPreset() == CameraPreset.AMAX) {
            SmallHD.setChecked(false);
            Amax.setChecked(true);
            Cimax.setChecked(false);
            FullHD.setChecked(false);
            color_rbg.setEnabled(false);
            Custom.setChecked(false);
        }
        if (Parent.Camera.GetPreset() == CameraPreset.CIMAX) {
            SmallHD.setChecked(false);
            Amax.setChecked(false);
            Cimax.setChecked(true);
            FullHD.setChecked(false);
            color_rbg.setEnabled(false);
            Custom.setChecked(false);
        }
        if (Parent.Camera.GetPreset() == CameraPreset.FULLHD) {
            SmallHD.setChecked(false);
            Amax.setChecked(false);
            Cimax.setChecked(false);
            FullHD.setChecked(true);
            color_rbg.setEnabled(true);
            Custom.setChecked(false);
        }
        if (Parent.Camera.GetPreset() == CameraPreset.SMALLHD) {
            SmallHD.setChecked(true);
            Amax.setChecked(false);
            Cimax.setChecked(false);
            FullHD.setChecked(false);
            color_rbg.setEnabled(true);
            Custom.setChecked(false);
        }
        if (Parent.Camera.GetPreset() == CameraPreset.CUSTOM) {
            SmallHD.setChecked(false);
            Amax.setChecked(false);
            Cimax.setChecked(false);
            FullHD.setChecked(false);
            color_rbg.setEnabled(true);
            Custom.setChecked(true);
        }
        if (Parent.Camera.GetColorMode() == ColorMode.JP4) {
            color_rbg.setChecked(false);
            color_jp46.setChecked(false);
            color_jp4.setChecked(true);
        }
        if (Parent.Camera.GetColorMode() == ColorMode.JP46) {
            color_rbg.setChecked(false);
            color_jp46.setChecked(true);
            color_jp4.setChecked(false);
        }
        if (Parent.Camera.GetColorMode() == ColorMode.RGB) {
            color_rbg.setChecked(true);
            color_jp4.setChecked(false);
            color_jp46.setChecked(false);
        }
        if (Parent.Camera.GetRecordFormat() == RecordFormat.MOV) {
            FormatQuicktime.setChecked(true);
            FormatJPEGs.setChecked(false);
        }
        if (Parent.Camera.GetRecordFormat() == RecordFormat.JPEG) {
            FormatQuicktime.setChecked(false);
            FormatJPEGs.setChecked(true);
        }

        switch ((int) Utils.Round(Parent.Camera.GetFPS(), 1)) {
            case 24:
                fps24.setChecked(true);
                fps25.setChecked(false);
                fps30.setChecked(false);
                fps50.setChecked(false);
                fps60.setChecked(false);
                fpscustom.setChecked(false);
                break;

            case 25:
                fps24.setChecked(false);
                fps25.setChecked(true);
                fps30.setChecked(false);
                fps50.setChecked(false);
                fps60.setChecked(false);
                fpscustom.setChecked(false);
                break;

            case 30:
                fps24.setChecked(false);
                fps25.setChecked(false);
                fps30.setChecked(true);
                fps50.setChecked(false);
                fps60.setChecked(false);
                fpscustom.setChecked(false);
                break;

            case 50:
                fps24.setChecked(false);
                fps25.setChecked(false);
                fps30.setChecked(false);
                fps50.setChecked(true);
                fps60.setChecked(false);
                fpscustom.setChecked(false);
                break;

            case 60:
                fps24.setChecked(false);
                fps25.setChecked(false);
                fps30.setChecked(false);
                fps50.setChecked(false);
                fps60.setChecked(true);
                fpscustom.setChecked(false);
                break;

            default:
                fps24.setChecked(false);
                fps25.setChecked(false);
                fps30.setChecked(false);
                fps50.setChecked(false);
                fps60.setChecked(false);
                fpscustom.setChecked(true);
                break;
        }
        // override if Frameskip or Secondsskip is set
        if ((Parent.Camera.GetFPSSkipFrames() != 0) || (Parent.Camera.GetFPSSkipSeconds() != 0)) {
            fps24.setChecked(false);
            fps25.setChecked(false);
            fps30.setChecked(false);
            fps50.setChecked(false);
            fps60.setChecked(false);
            fpscustom.setChecked(true);
        }

        JPEGQualityButton.setValue("" + Parent.Camera.GetJPEGQuality());
        CoringValueYTextField.setText(String.valueOf(Parent.Camera.GetCoringIndexY()));
        CoringValueCTextField.setText(String.valueOf(Parent.Camera.GetCoringIndexC()));

        DatarateMonitor.startAnimator();
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        SettingsOverview = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Overview_Resolution = new javax.swing.JLabel();
        Overview_FPS = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Overview_JPEGQ = new javax.swing.JLabel();
        Overview_ColorMode = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Overview_Format = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Overview_FreeSpace = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Overview_RecCapacity = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        Overview_CoringIndexY = new javax.swing.JLabel();
        DatarateMonitor = new DatarateMonitor();
        jLabel19 = new javax.swing.JLabel();
        Overview_CoringIndexC = new javax.swing.JLabel();
        ResolutionPanel = new javax.swing.JPanel();
        ResolutionLabel = new javax.swing.JLabel();
        Amax = new EButton(Parent);
        Cimax = new EButton(Parent);
        FullHD = new EButton(Parent);
        SmallHD = new EButton(Parent);
        Custom = new EButton(Parent);
        Full = new EButton(Parent);
        ColorModePanel = new javax.swing.JPanel();
        ColorModeLabel = new javax.swing.JLabel();
        color_rbg = new EButton(Parent);
        color_jp4 = new EButton(Parent);
        color_jp46 = new EButton(Parent);
        FPSPanel = new javax.swing.JPanel();
        FPSLabel = new javax.swing.JLabel();
        fps24 = new EButton(Parent);
        fps25 = new EButton(Parent);
        fps30 = new EButton(Parent);
        fps50 = new EButton(Parent);
        fps60 = new EButton(Parent);
        fpscustom = new EButton(Parent);
        JPEGQualityPanel = new javax.swing.JPanel();
        JPEGLabel = new javax.swing.JLabel();
        JPEG_Plus = new EButton(Parent);
        JPEGQualityButton = new EButton(Parent);
        JPEG_Minus = new EButton(Parent);
        WBPanel = new javax.swing.JPanel();
        WBLabel = new javax.swing.JLabel();
        WBTungsten = new EButton(Parent);
        WBDaylight = new EButton(Parent);
        WBFlourescent = new EButton(Parent);
        WBAuto = new EButton(Parent);
        WBCloudy = new EButton(Parent);
        WBCustom = new EButton(Parent);
        RecordFormatPanel = new javax.swing.JPanel();
        FormatQuicktime = new EButton(Parent);
        FormatLabel = new javax.swing.JLabel();
        FormatJPEGs = new EButton(Parent);
        NavigationPanel = new javax.swing.JPanel();
        SettingsMenu1Button10 = new EButton(Parent);
        SettingsMenu2Button10 = new EButton(Parent);
        GuidesMenuButton10 = new EButton(Parent);
        SettingsMenu3Button10 = new EButton(Parent);
        PhotoSettingsMenu = new EButton(Parent);
        Experimental = new javax.swing.JPanel();
        CoringLabel = new javax.swing.JLabel();
        CoringValueYTextField = new javax.swing.JTextField();
        CoringSet = new EButton(Parent);
        CoringType = new EButton(Parent);
        ExperimentalLabel = new javax.swing.JLabel();
        HITSettings = new EButton(Parent);
        CoringLabel1 = new javax.swing.JLabel();
        CoringValueCTextField = new javax.swing.JTextField();
        CoringType1 = new EButton(Parent);
        CoringSet1 = new EButton(Parent);
        SettingsCancelButton = new EButton(Parent);
        TerminateButton = new EButton(Parent);

        bg.setBackground(new java.awt.Color(0, 0, 0));
        bg.setPreferredSize(new java.awt.Dimension(1024, 600));

        SettingsOverview.setBackground(new java.awt.Color(0, 0, 0));
        SettingsOverview.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(44, 44, 44), 1, true));
        SettingsOverview.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Settings Overview");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel8.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Resolution:");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel9.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("FPS:");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_Resolution.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_Resolution.setForeground(new java.awt.Color(255, 255, 255));
        Overview_Resolution.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_Resolution.setText("loading");
        Overview_Resolution.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_FPS.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_FPS.setForeground(new java.awt.Color(255, 255, 255));
        Overview_FPS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_FPS.setText("loading");
        Overview_FPS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel10.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("JPEG Quality:");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel11.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Color Mode:");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel12.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Format:");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_JPEGQ.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_JPEGQ.setForeground(new java.awt.Color(255, 255, 255));
        Overview_JPEGQ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_JPEGQ.setText("loading");
        Overview_JPEGQ.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_ColorMode.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_ColorMode.setForeground(new java.awt.Color(255, 255, 255));
        Overview_ColorMode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_ColorMode.setText("loading");
        Overview_ColorMode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel13.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Datarate:");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_Format.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_Format.setForeground(new java.awt.Color(255, 255, 255));
        Overview_Format.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_Format.setText("loading");
        Overview_Format.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel14.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Free Space:");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_FreeSpace.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_FreeSpace.setForeground(new java.awt.Color(255, 255, 255));
        Overview_FreeSpace.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_FreeSpace.setText("loading");
        Overview_FreeSpace.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel15.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Record Capacity:");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_RecCapacity.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_RecCapacity.setForeground(new java.awt.Color(255, 255, 255));
        Overview_RecCapacity.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_RecCapacity.setText("loading");
        Overview_RecCapacity.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel18.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Coring Index Y");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_CoringIndexY.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_CoringIndexY.setForeground(new java.awt.Color(255, 255, 255));
        Overview_CoringIndexY.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_CoringIndexY.setText("loading");
        Overview_CoringIndexY.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        DatarateMonitor.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout DatarateMonitorLayout = new javax.swing.GroupLayout(DatarateMonitor);
        DatarateMonitor.setLayout(DatarateMonitorLayout);
        DatarateMonitorLayout.setHorizontalGroup(
            DatarateMonitorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 134, Short.MAX_VALUE)
        );
        DatarateMonitorLayout.setVerticalGroup(
            DatarateMonitorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        jLabel19.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Coring Index C");
        jLabel19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Overview_CoringIndexC.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        Overview_CoringIndexC.setForeground(new java.awt.Color(255, 255, 255));
        Overview_CoringIndexC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Overview_CoringIndexC.setText("loading");
        Overview_CoringIndexC.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout SettingsOverviewLayout = new javax.swing.GroupLayout(SettingsOverview);
        SettingsOverview.setLayout(SettingsOverviewLayout);
        SettingsOverviewLayout.setHorizontalGroup(
            SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SettingsOverviewLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                        .addGap(164, 164, 164))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SettingsOverviewLayout.createSequentialGroup()
                        .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Overview_Resolution, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Overview_FPS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Overview_JPEGQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Overview_ColorMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Overview_Format, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Overview_FreeSpace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Overview_RecCapacity, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                        .addContainerGap(25, Short.MAX_VALUE))))
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
            .addGroup(SettingsOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
            .addGroup(SettingsOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SettingsOverviewLayout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(Overview_CoringIndexY, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                    .addGroup(SettingsOverviewLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
            .addGroup(SettingsOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DatarateMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
            .addGroup(SettingsOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SettingsOverviewLayout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(Overview_CoringIndexC, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                    .addGroup(SettingsOverviewLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        SettingsOverviewLayout.setVerticalGroup(
            SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsOverviewLayout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_Resolution, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_FPS, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_JPEGQ, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_ColorMode, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_Format, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_FreeSpace, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_RecCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_CoringIndexY, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SettingsOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Overview_CoringIndexC, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DatarateMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65))
        );

        ResolutionPanel.setBackground(java.awt.Color.black);
        ResolutionPanel.setPreferredSize(new java.awt.Dimension(80, 260));

        ResolutionLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        ResolutionLabel.setForeground(new java.awt.Color(255, 255, 255));
        ResolutionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ResolutionLabel.setText("Resolution");
        ResolutionLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        Amax.setText("AMAX");
        Amax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AmaxActionPerformed(evt);
            }
        });

        Cimax.setText("CIMAX");
        Cimax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CimaxActionPerformed(evt);
            }
        });

        FullHD.setText("1080p");
        FullHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FullHDActionPerformed(evt);
            }
        });

        SmallHD.setText("720p");
        SmallHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SmallHDActionPerformed(evt);
            }
        });

        Custom.setText("custom");
        Custom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustomActionPerformed(evt);
            }
        });

        Full.setText("Full");
        Full.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FullActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ResolutionPanelLayout = new javax.swing.GroupLayout(ResolutionPanel);
        ResolutionPanel.setLayout(ResolutionPanelLayout);
        ResolutionPanelLayout.setHorizontalGroup(
            ResolutionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ResolutionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(Custom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SmallHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(FullHD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Cimax, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Amax, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(ResolutionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(ResolutionLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Full, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ResolutionPanelLayout.setVerticalGroup(
            ResolutionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ResolutionPanelLayout.createSequentialGroup()
                .addComponent(ResolutionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Full, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Amax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Cimax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FullHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SmallHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Custom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ColorModePanel.setBackground(java.awt.Color.black);

        ColorModeLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        ColorModeLabel.setForeground(new java.awt.Color(255, 255, 255));
        ColorModeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ColorModeLabel.setText("Color Mode");
        ColorModeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        color_rbg.setIconImage(getClass().getResource("/media/rgb.png")); // NOI18N
        color_rbg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color_rbgActionPerformed(evt);
            }
        });

        color_jp4.setIconImage(getClass().getResource("/media/jp4.png")); // NOI18N
        color_jp4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color_jp4ActionPerformed(evt);
            }
        });

        color_jp46.setIconImage(getClass().getResource("/media/jp46.png")); // NOI18N
        color_jp46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color_jp46ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ColorModePanelLayout = new javax.swing.GroupLayout(ColorModePanel);
        ColorModePanel.setLayout(ColorModePanelLayout);
        ColorModePanelLayout.setHorizontalGroup(
            ColorModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ColorModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(ColorModeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(color_rbg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(color_jp46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(color_jp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        ColorModePanelLayout.setVerticalGroup(
            ColorModePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ColorModePanelLayout.createSequentialGroup()
                .addComponent(ColorModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(color_rbg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(color_jp46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(color_jp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        FPSPanel.setBackground(java.awt.Color.black);
        FPSPanel.setPreferredSize(new java.awt.Dimension(80, 280));

        FPSLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        FPSLabel.setForeground(new java.awt.Color(255, 255, 255));
        FPSLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FPSLabel.setText("FPS");
        FPSLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        fps24.setText("24");
        fps24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fps24ActionPerformed(evt);
            }
        });

        fps25.setText("25");
        fps25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fps25ActionPerformed(evt);
            }
        });

        fps30.setText("30");
        fps30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fps30ActionPerformed(evt);
            }
        });

        fps50.setText("50");
        fps50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fps50ActionPerformed(evt);
            }
        });

        fps60.setText("60");
        fps60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fps60ActionPerformed(evt);
            }
        });

        fpscustom.setText("custom");
        fpscustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fpscustomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FPSPanelLayout = new javax.swing.GroupLayout(FPSPanel);
        FPSPanel.setLayout(FPSPanelLayout);
        FPSPanelLayout.setHorizontalGroup(
            FPSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FPSLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
            .addComponent(fps24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fps25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fps30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fps50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fps60, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(fpscustom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        FPSPanelLayout.setVerticalGroup(
            FPSPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FPSPanelLayout.createSequentialGroup()
                .addComponent(FPSLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fps24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(fps25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fps30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fps50, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fps60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fpscustom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        JPEGQualityPanel.setBackground(new java.awt.Color(0, 0, 0));
        JPEGQualityPanel.setBorder(null);

        JPEGLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        JPEGLabel.setForeground(new java.awt.Color(255, 255, 255));
        JPEGLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JPEGLabel.setText("JPEG Quality");
        JPEGLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        JPEG_Plus.setText("+");
        JPEG_Plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JPEG_PlusActionPerformed(evt);
            }
        });

        JPEGQualityButton.setText("JPEG %");
        JPEGQualityButton.setAlignmentY(0.0F);
        JPEGQualityButton.setHorizontalAlignment(2);
        JPEGQualityButton.setHorizontalTextPosition(2);
        JPEGQualityButton.setIconTextGap(20);
        JPEGQualityButton.setMargin(new java.awt.Insets(0, 5, 0, 0));
        JPEGQualityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JPEGQualityButtonActionPerformed(evt);
            }
        });

        JPEG_Minus.setText("-");
        JPEG_Minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JPEG_MinusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout JPEGQualityPanelLayout = new javax.swing.GroupLayout(JPEGQualityPanel);
        JPEGQualityPanel.setLayout(JPEGQualityPanelLayout);
        JPEGQualityPanelLayout.setHorizontalGroup(
            JPEGQualityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPEGQualityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(JPEG_Plus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JPEGQualityButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(JPEG_Minus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(JPEGLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );
        JPEGQualityPanelLayout.setVerticalGroup(
            JPEGQualityPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPEGQualityPanelLayout.createSequentialGroup()
                .addComponent(JPEGLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JPEG_Plus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JPEGQualityButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(JPEG_Minus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        WBPanel.setBackground(new java.awt.Color(0, 0, 0));

        WBLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        WBLabel.setForeground(new java.awt.Color(255, 255, 255));
        WBLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WBLabel.setText("White Balance");
        WBLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        WBTungsten.setIconImage(getClass().getResource("/media/wb_tungsten.png")); // NOI18N
        WBTungsten.setAlignmentY(0.0F);
        WBTungsten.setHorizontalTextPosition(0);
        WBTungsten.setIconTextGap(20);
        WBTungsten.setMargin(new java.awt.Insets(0, 5, 0, 0));
        WBTungsten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WBTungstenActionPerformed(evt);
            }
        });

        WBDaylight.setAlignmentY(0.0F);
        WBDaylight.setHorizontalTextPosition(0);
        WBDaylight.setIconImage(getClass().getResource("/media/wb_daylight.png"));
        WBDaylight.setIconTextGap(20);
        WBDaylight.setMargin(new java.awt.Insets(0, 5, 0, 0));
        WBDaylight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WBDaylightActionPerformed(evt);
            }
        });

        WBFlourescent.setBorder(null);
        WBFlourescent.setIconImage(getClass().getResource("/media/wb_flourescent.png")); // NOI18N
        WBFlourescent.setAlignmentY(0.0F);
        WBFlourescent.setHorizontalTextPosition(0);
        WBFlourescent.setIconTextGap(20);
        WBFlourescent.setMargin(new java.awt.Insets(0, 5, 0, 0));
        WBFlourescent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WBFlourescentActionPerformed(evt);
            }
        });

        WBAuto.setText("Auto");
        WBAuto.setAlignmentY(0.0F);
        WBAuto.setHorizontalTextPosition(0);
        WBAuto.setIconTextGap(20);
        WBAuto.setMargin(new java.awt.Insets(0, 5, 0, 0));
        WBAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WBAutoActionPerformed(evt);
            }
        });

        WBCloudy.setIconImage(getClass().getResource("/media/wb_cloudy.png")); // NOI18N
        WBCloudy.setAlignmentY(0.0F);
        WBCloudy.setHorizontalTextPosition(0);
        WBCloudy.setIconTextGap(20);
        WBCloudy.setMargin(new java.awt.Insets(0, 5, 0, 0));
        WBCloudy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WBCloudyActionPerformed(evt);
            }
        });

        WBCustom.setText("Custom");
        WBCustom.setAlignmentY(0.0F);
        WBCustom.setHorizontalTextPosition(0);
        WBCustom.setIconTextGap(20);
        WBCustom.setMargin(new java.awt.Insets(0, 5, 0, 0));
        WBCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WBCustomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout WBPanelLayout = new javax.swing.GroupLayout(WBPanel);
        WBPanel.setLayout(WBPanelLayout);
        WBPanelLayout.setHorizontalGroup(
            WBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WBCustom, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
            .addComponent(WBCloudy, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
            .addComponent(WBFlourescent, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
            .addComponent(WBTungsten, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
            .addComponent(WBDaylight, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
            .addComponent(WBLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(WBAuto, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
        );
        WBPanelLayout.setVerticalGroup(
            WBPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WBPanelLayout.createSequentialGroup()
                .addComponent(WBLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WBAuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WBDaylight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WBTungsten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WBFlourescent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WBCloudy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(WBCustom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        RecordFormatPanel.setBackground(new java.awt.Color(0, 0, 0));

        FormatQuicktime.setText("Quicktime");
        FormatQuicktime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FormatQuicktimeActionPerformed(evt);
            }
        });

        FormatLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        FormatLabel.setForeground(new java.awt.Color(255, 255, 255));
        FormatLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FormatLabel.setText("Format");
        FormatLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        FormatJPEGs.setText("Image Seq.");
        FormatJPEGs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FormatJPEGsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout RecordFormatPanelLayout = new javax.swing.GroupLayout(RecordFormatPanel);
        RecordFormatPanel.setLayout(RecordFormatPanelLayout);
        RecordFormatPanelLayout.setHorizontalGroup(
            RecordFormatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FormatJPEGs, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
            .addComponent(FormatLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
            .addComponent(FormatQuicktime, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
        );
        RecordFormatPanelLayout.setVerticalGroup(
            RecordFormatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecordFormatPanelLayout.createSequentialGroup()
                .addComponent(FormatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FormatQuicktime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(FormatJPEGs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        NavigationPanel.setBackground(java.awt.Color.black);

        SettingsMenu1Button10.setText("Settings Tab 1");
        SettingsMenu1Button10.setChecked(true);
        SettingsMenu1Button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsMenu1Button10ActionPerformed(evt);
            }
        });

        SettingsMenu2Button10.setText("Settings Tab 2");
        SettingsMenu2Button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsMenu2Button10ActionPerformed(evt);
            }
        });

        GuidesMenuButton10.setText("Guides");
        GuidesMenuButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuidesMenuButton10ActionPerformed(evt);
            }
        });

        SettingsMenu3Button10.setText("Settings Tab 3");
        SettingsMenu3Button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsMenu3Button10ActionPerformed(evt);
            }
        });

        PhotoSettingsMenu.setText("Photo Settings");
        PhotoSettingsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhotoSettingsMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NavigationPanelLayout = new javax.swing.GroupLayout(NavigationPanel);
        NavigationPanel.setLayout(NavigationPanelLayout);
        NavigationPanelLayout.setHorizontalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationPanelLayout.createSequentialGroup()
                .addComponent(SettingsMenu1Button10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SettingsMenu2Button10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SettingsMenu3Button10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(GuidesMenuButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PhotoSettingsMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        NavigationPanelLayout.setVerticalGroup(
            NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(SettingsMenu1Button10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SettingsMenu2Button10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(SettingsMenu3Button10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(GuidesMenuButton10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(PhotoSettingsMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Experimental.setBackground(new java.awt.Color(0, 0, 0));
        Experimental.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(44, 44, 44), 1, true));
        Experimental.setForeground(new java.awt.Color(255, 255, 255));

        CoringLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        CoringLabel.setForeground(new java.awt.Color(255, 255, 255));
        CoringLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CoringLabel.setText("Coring Y");
        CoringLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        CoringSet.setText("Set");
        CoringSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoringSetActionPerformed(evt);
            }
        });

        CoringType.setText("type");
        CoringType.setFont(new java.awt.Font("DejaVu Sans", 0, 10));
        CoringType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoringTypeActionPerformed(evt);
            }
        });

        ExperimentalLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        ExperimentalLabel.setForeground(new java.awt.Color(255, 255, 255));
        ExperimentalLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ExperimentalLabel.setText("Experimental");
        ExperimentalLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        HITSettings.setText("HIT Settings");
        HITSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HITSettingsActionPerformed(evt);
            }
        });

        CoringLabel1.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        CoringLabel1.setForeground(new java.awt.Color(255, 255, 255));
        CoringLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CoringLabel1.setText("Coring C");
        CoringLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        CoringType1.setText("type");
        CoringType1.setFont(new java.awt.Font("DejaVu Sans", 0, 10));
        CoringType1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoringType1ActionPerformed(evt);
            }
        });

        CoringSet1.setText("Set");
        CoringSet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CoringSet1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ExperimentalLayout = new javax.swing.GroupLayout(Experimental);
        Experimental.setLayout(ExperimentalLayout);
        ExperimentalLayout.setHorizontalGroup(
            ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExperimentalLayout.createSequentialGroup()
                .addGroup(ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ExperimentalLayout.createSequentialGroup()
                        .addGroup(ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CoringLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CoringLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CoringValueYTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CoringValueCTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CoringType, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CoringType1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CoringSet, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CoringSet1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ExperimentalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ExperimentalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
                    .addGroup(ExperimentalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(HITSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        ExperimentalLayout.setVerticalGroup(
            ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExperimentalLayout.createSequentialGroup()
                .addComponent(ExperimentalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CoringLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CoringValueYTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CoringType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CoringSet, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ExperimentalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CoringLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CoringValueCTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CoringType1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CoringSet1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(HITSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        SettingsCancelButton.setText("Close");
        SettingsCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsCancelButtonActionPerformed(evt);
            }
        });

        TerminateButton.setForeground(new java.awt.Color(184, 2, 2));
        TerminateButton.setText("Quit ElphelVision");
        TerminateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NavigationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(ResolutionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FPSPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JPEGQualityPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ColorModePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RecordFormatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(WBPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Experimental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(SettingsOverview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(TerminateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SettingsCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(WBPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(bgLayout.createSequentialGroup()
                            .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ResolutionPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                    .addComponent(FPSPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                    .addComponent(ColorModePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                    .addComponent(JPEGQualityPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                                .addComponent(RecordFormatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Experimental, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(SettingsOverview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(TerminateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(SettingsCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(NavigationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                        .addGap(0, 0, 0))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void FullHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FullHDActionPerformed
        FullHD.setChecked(true);

        Cimax.setChecked(false);
        Amax.setChecked(false);
        SmallHD.setChecked(false);
        Custom.setChecked(false);
        Full.setChecked(false);

        Parent.Camera.SetPreset(CameraPreset.FULLHD);
    }//GEN-LAST:event_FullHDActionPerformed

    private void color_jp46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_color_jp46ActionPerformed
        color_jp46.setChecked(true);
        color_rbg.setChecked(false);
        color_jp4.setChecked(false);

        Parent.Camera.SetColorMode(ColorMode.JP46);
    }//GEN-LAST:event_color_jp46ActionPerformed

    private void CimaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CimaxActionPerformed
        Cimax.setChecked(true);

        Amax.setChecked(false);
        FullHD.setChecked(false);
        SmallHD.setChecked(false);
        Custom.setChecked(false);
        Full.setChecked(false);

        Parent.Camera.SetPreset(CameraPreset.CIMAX);
    }//GEN-LAST:event_CimaxActionPerformed

    private void AmaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AmaxActionPerformed
        Amax.setChecked(true);

        Cimax.setChecked(false);
        FullHD.setChecked(false);
        SmallHD.setChecked(false);
        Custom.setChecked(false);
        Full.setChecked(false);

        Parent.Camera.SetPreset(CameraPreset.AMAX);
    }//GEN-LAST:event_AmaxActionPerformed

    private void SmallHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SmallHDActionPerformed
        SmallHD.setChecked(true);
        Amax.setChecked(false);
        Cimax.setChecked(false);
        FullHD.setChecked(false);
        Custom.setChecked(false);
        Full.setChecked(false);

        Parent.Camera.SetPreset(CameraPreset.SMALLHD);
    }//GEN-LAST:event_SmallHDActionPerformed

    private void color_rbgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_color_rbgActionPerformed
        color_jp46.setChecked(false);
        color_jp4.setChecked(false);
        color_rbg.setChecked(true);

        Parent.Camera.SetColorMode(ColorMode.RGB);
    }//GEN-LAST:event_color_rbgActionPerformed

    private void fps30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fps30ActionPerformed
        fps24.setChecked(false);
        fps25.setChecked(false);
        fps30.setChecked(true);
        fps50.setChecked(false);
        fps60.setChecked(false);
        fpscustom.setChecked(false);

        Parent.Camera.SetFPS(30.0f);
    }//GEN-LAST:event_fps30ActionPerformed

    private void SettingsCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsCancelButtonActionPerformed
        try { // Save to config file
            Parent.Camera.WriteConfigFile("autosave.config");


        } catch (IOException ex) {
            Logger.getLogger(Settings1Layout.class.getName()).log(Level.SEVERE, null, ex);
        }

        Parent.LoadMainCard();
    }//GEN-LAST:event_SettingsCancelButtonActionPerformed

    private void CustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustomActionPerformed
        Parent.ResolutionSettingsCardLayout.Load();
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "CustomResolutionCard");
    }//GEN-LAST:event_CustomActionPerformed

    private void fps25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fps25ActionPerformed
        fps24.setChecked(false);
        fps25.setChecked(true);
        fps30.setChecked(false);
        fps50.setChecked(false);
        fps60.setChecked(false);
        fpscustom.setChecked(false);

        Parent.Camera.SetFPS(25.0f);
    }//GEN-LAST:event_fps25ActionPerformed

    private void fps24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fps24ActionPerformed
        fps24.setChecked(true);

        fps25.setChecked(false);
        fps30.setChecked(false);
        fps50.setChecked(false);
        fps60.setChecked(false);
        fpscustom.setChecked(false);

        Parent.Camera.SetFPS(24.0f);
    }//GEN-LAST:event_fps24ActionPerformed

    private void fps50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fps50ActionPerformed
        fps24.setChecked(false);
        fps25.setChecked(false);
        fps30.setChecked(false);
        fps50.setChecked(true);
        fps60.setChecked(false);
        fpscustom.setChecked(false);

        Parent.Camera.SetFPS(50.0f);
    }//GEN-LAST:event_fps50ActionPerformed

    private void fps60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fps60ActionPerformed
        fps24.setChecked(false);
        fps25.setChecked(false);
        fps30.setChecked(false);
        fps50.setChecked(false);
        fps60.setChecked(true);
        fpscustom.setChecked(false);

        Parent.Camera.SetFPS(60.0f);
    }//GEN-LAST:event_fps60ActionPerformed

    private void fpscustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fpscustomActionPerformed
        Parent.FPSSettingsCardLayout.Load();
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "CustomFPSCard");
    }//GEN-LAST:event_fpscustomActionPerformed

    private void WBTungstenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WBTungstenActionPerformed
        WBAuto.setChecked(false);
        WBDaylight.setChecked(false);
        WBTungsten.setChecked(true);
        WBCloudy.setChecked(false);
        WBFlourescent.setChecked(false);
        WBCustom.setChecked(false);

        Parent.Camera.SetWhiteBalance(WhiteBalance.TUNGSTEN);
    }//GEN-LAST:event_WBTungstenActionPerformed

    private void WBDaylightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WBDaylightActionPerformed
        WBAuto.setChecked(false);
        WBDaylight.setChecked(true);
        WBTungsten.setChecked(false);
        WBCloudy.setChecked(false);
        WBFlourescent.setChecked(false);
        WBCustom.setChecked(false);

        Parent.Camera.SetWhiteBalance(WhiteBalance.DAYLIGHT);
    }//GEN-LAST:event_WBDaylightActionPerformed

    private void WBFlourescentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WBFlourescentActionPerformed
        WBAuto.setChecked(false);
        WBDaylight.setChecked(false);
        WBTungsten.setChecked(false);
        WBCloudy.setChecked(false);
        WBFlourescent.setChecked(true);
        WBCustom.setChecked(false);

        Parent.Camera.SetWhiteBalance(WhiteBalance.FLOURESCENT);
    }//GEN-LAST:event_WBFlourescentActionPerformed

    private void WBAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WBAutoActionPerformed
        WBAuto.setChecked(true);
        WBDaylight.setChecked(false);
        WBTungsten.setChecked(false);
        WBCloudy.setChecked(false);
        WBFlourescent.setChecked(false);
        WBCustom.setChecked(false);

        Parent.Camera.SetWhiteBalance(WhiteBalance.AUTO);
    }//GEN-LAST:event_WBAutoActionPerformed

    private void WBCloudyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WBCloudyActionPerformed
        WBAuto.setChecked(false);
        WBDaylight.setChecked(false);
        WBTungsten.setChecked(false);
        WBCloudy.setChecked(true);
        WBFlourescent.setChecked(false);
        WBCustom.setChecked(false);

        Parent.Camera.SetWhiteBalance(WhiteBalance.CLOUDY);
    }//GEN-LAST:event_WBCloudyActionPerformed

    private void WBCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WBCustomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_WBCustomActionPerformed

    private void FormatJPEGsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FormatJPEGsActionPerformed
        FormatQuicktime.setChecked(false);
        FormatJPEGs.setChecked(true);

        Parent.Camera.SetRecordFormat(RecordFormat.JPEG);
    }//GEN-LAST:event_FormatJPEGsActionPerformed

    private void FormatQuicktimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FormatQuicktimeActionPerformed
        FormatQuicktime.setChecked(true);
        FormatJPEGs.setChecked(false);

        Parent.Camera.SetRecordFormat(RecordFormat.MOV);
    }//GEN-LAST:event_FormatQuicktimeActionPerformed

    private void FullActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FullActionPerformed
        Full.setChecked(true);

        FullHD.setChecked(false);
        Cimax.setChecked(false);
        Amax.setChecked(false);
        SmallHD.setChecked(false);
        Custom.setChecked(false);

        Parent.Camera.SetPreset(CameraPreset.FULL);
    }//GEN-LAST:event_FullActionPerformed

    private void TerminateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminateButtonActionPerformed
        if (JOptionPane.showConfirmDialog(new JFrame(), "Do you want to quit ElphelVision?", "Quit ElphelVision?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try { // Save to config file
                Parent.Camera.WriteConfigFile("autosave.config");


            } catch (IOException ex) {
                Logger.getLogger(Settings1Layout.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
    }//GEN-LAST:event_TerminateButtonActionPerformed

    private void SettingsMenu1Button10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsMenu1Button10ActionPerformed
}//GEN-LAST:event_SettingsMenu1Button10ActionPerformed

    private void SettingsMenu2Button10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsMenu2Button10ActionPerformed
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "Settings2Card");
        Parent.Settings2CardLayout.Load();
}//GEN-LAST:event_SettingsMenu2Button10ActionPerformed

    private void GuidesMenuButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuidesMenuButton10ActionPerformed
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "GuidesCard");
        Parent.GuidesPanel.Load();
}//GEN-LAST:event_GuidesMenuButton10ActionPerformed

    private void SettingsMenu3Button10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsMenu3Button10ActionPerformed
        Parent.Settings3CardLayout.Load();
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "Settings3Card");
        Parent.StopVideoPlayer();
}//GEN-LAST:event_SettingsMenu3Button10ActionPerformed

    private void JPEG_MinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JPEG_MinusActionPerformed
        Parent.Camera.SetJPEGQuality(Parent.Camera.GetJPEGQuality() - 1);
        JPEGQualityButton.setValue("" + Parent.Camera.GetJPEGQuality());
}//GEN-LAST:event_JPEG_MinusActionPerformed

    private void JPEGQualityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JPEGQualityButtonActionPerformed
}//GEN-LAST:event_JPEGQualityButtonActionPerformed

    private void JPEG_PlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JPEG_PlusActionPerformed
        Parent.Camera.SetJPEGQuality(Parent.Camera.GetJPEGQuality() + 1);
        JPEGQualityButton.setValue("" + Parent.Camera.GetJPEGQuality());
}//GEN-LAST:event_JPEG_PlusActionPerformed

    private void PhotoSettingsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhotoSettingsMenuActionPerformed
        Parent.PhotoSettingsCardLayout.Load();
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "PhotoSettings");
    }//GEN-LAST:event_PhotoSettingsMenuActionPerformed

    private void CoringSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoringSetActionPerformed
        Parent.Camera.SetCoringIndex(Integer.parseInt(CoringValueYTextField.getText()), Integer.parseInt(CoringValueCTextField.getText()));
    }//GEN-LAST:event_CoringSetActionPerformed

    private void CoringTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoringTypeActionPerformed
        Parent.NumberPanelInteger.Load("Coring", Integer.parseInt(CoringValueYTextField.getText()), CoringValueYTextField, "Settings1Card");
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "NumberpanelInteger");
}//GEN-LAST:event_CoringTypeActionPerformed

    private void color_jp4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_color_jp4ActionPerformed
        color_jp46.setChecked(false);
        color_rbg.setChecked(false);
        color_jp4.setChecked(true);

        Parent.Camera.SetColorMode(ColorMode.JP4);
    }//GEN-LAST:event_color_jp4ActionPerformed

    private void HITSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HITSettingsActionPerformed
        Parent.HITSettingsCardLayout.Load();
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "HITSettingsCard");
    }//GEN-LAST:event_HITSettingsActionPerformed

    private void CoringType1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoringType1ActionPerformed
        Parent.NumberPanelInteger.Load("Coring", Integer.parseInt(CoringValueCTextField.getText()), CoringValueCTextField, "Settings1Card");
        CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
        cl.show(Parent.GetCardManager(), "NumberpanelInteger");
    }//GEN-LAST:event_CoringType1ActionPerformed

    private void CoringSet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CoringSet1ActionPerformed
        Parent.Camera.SetCoringIndex(Integer.parseInt(CoringValueYTextField.getText()), Integer.parseInt(CoringValueCTextField.getText()));
    }//GEN-LAST:event_CoringSet1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private EButton Amax;
    private EButton Cimax;
    private javax.swing.JLabel ColorModeLabel;
    private javax.swing.JPanel ColorModePanel;
    private javax.swing.JLabel CoringLabel;
    private javax.swing.JLabel CoringLabel1;
    private EButton CoringSet;
    private EButton CoringSet1;
    private EButton CoringType;
    private EButton CoringType1;
    private javax.swing.JTextField CoringValueCTextField;
    private javax.swing.JTextField CoringValueYTextField;
    private EButton Custom;
    private DatarateMonitor DatarateMonitor;
    private javax.swing.JPanel Experimental;
    private javax.swing.JLabel ExperimentalLabel;
    private javax.swing.JLabel FPSLabel;
    private javax.swing.JPanel FPSPanel;
    private EButton FormatJPEGs;
    private javax.swing.JLabel FormatLabel;
    private EButton FormatQuicktime;
    private EButton Full;
    private EButton FullHD;
    private EButton GuidesMenuButton10;
    private EButton HITSettings;
    private javax.swing.JLabel JPEGLabel;
    private EButton JPEGQualityButton;
    private javax.swing.JPanel JPEGQualityPanel;
    private EButton JPEG_Minus;
    private EButton JPEG_Plus;
    private javax.swing.JPanel NavigationPanel;
    private javax.swing.JLabel Overview_ColorMode;
    private javax.swing.JLabel Overview_CoringIndexC;
    private javax.swing.JLabel Overview_CoringIndexY;
    private javax.swing.JLabel Overview_FPS;
    private javax.swing.JLabel Overview_Format;
    private javax.swing.JLabel Overview_FreeSpace;
    private javax.swing.JLabel Overview_JPEGQ;
    private javax.swing.JLabel Overview_RecCapacity;
    private javax.swing.JLabel Overview_Resolution;
    private EButton PhotoSettingsMenu;
    private javax.swing.JPanel RecordFormatPanel;
    private javax.swing.JLabel ResolutionLabel;
    private javax.swing.JPanel ResolutionPanel;
    private EButton SettingsCancelButton;
    private EButton SettingsMenu1Button10;
    private EButton SettingsMenu2Button10;
    private EButton SettingsMenu3Button10;
    private javax.swing.JPanel SettingsOverview;
    private EButton SmallHD;
    private EButton TerminateButton;
    private EButton WBAuto;
    private EButton WBCloudy;
    private EButton WBCustom;
    private EButton WBDaylight;
    private EButton WBFlourescent;
    private javax.swing.JLabel WBLabel;
    private javax.swing.JPanel WBPanel;
    private EButton WBTungsten;
    private javax.swing.JPanel bg;
    private EButton color_jp4;
    private EButton color_jp46;
    private EButton color_rbg;
    private EButton fps24;
    private EButton fps25;
    private EButton fps30;
    private EButton fps50;
    private EButton fps60;
    private EButton fpscustom;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
