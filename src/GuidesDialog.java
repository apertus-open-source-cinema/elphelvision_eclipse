import java.awt.CardLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;

//TODO apply new settings when another button than close is pressed

public class GuidesDialog extends javax.swing.JPanel {

    ElphelVision Parent;

    public GuidesDialog(ElphelVision parent) {
	Parent = parent;

	try {
	    java.awt.EventQueue.invokeAndWait(new Runnable() {

		public void run() {
		    initComponents();
		}
	    });
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	guidespreview1.SetParent(parent);
    }

    public void Load() {
	CenterXButton.setChecked(Parent.Camera.GetGuides()[0]);
	OuterXButton.setChecked(Parent.Camera.GetGuides()[1]);
	ThirdsButton.setChecked(Parent.Camera.GetGuides()[2]);
	SafeAreaButton.setChecked(Parent.Camera.GetGuides()[3]);

	guidespreview1.SetOptions(CenterXButton.isChecked(), OuterXButton.isChecked(), ThirdsButton.isChecked(), SafeAreaButton.isChecked());
	RedrawPreviewArea();
    }

    private void RedrawPreviewArea() {
	guidespreview1.repaint();
    }

    private void initComponents() {
	bg = new javax.swing.JPanel();
	SettingsMenu3Button = new EButton(Parent);

	bg.setBackground(new java.awt.Color(0, 0, 0));
	bg.setPreferredSize(new java.awt.Dimension(1024, 600));
	setPreferredSize(new java.awt.Dimension(1024, 600));

	SettingsMenu3Button.setText("Settings Tab 3");
	SettingsMenu3Button.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		SettingsMenu3ButtonActionPerformed(evt);
	    }
	});
	add(bg);
	bg.setLayout(new MigLayout("", "[773.00px][10px][]", "[531.00][]"));
	guidespreview1 = new Guides();

	guidespreview1.setBackground(new java.awt.Color(76, 76, 76));
	bg.add(guidespreview1, "cell 0 0,grow");
	jPanel1 = new javax.swing.JPanel();
	
		jPanel1.setBackground(new java.awt.Color(0, 0, 0));
		bg.add(jPanel1, "cell 2 0,alignx left,aligny top");
		jPanel1.setLayout(new MigLayout("", "[80px][80px]", "[35px][]"));
		CenterXButton = new EButton(Parent);
		jPanel1.add(CenterXButton, "cell 0 0,growx,aligny top");
		
			CenterXButton.setText("Center X");
			ThirdsButton = new EButton(Parent);
			jPanel1.add(ThirdsButton, "cell 1 0,growx,aligny top");
			
				ThirdsButton.setText("Thirds");
				OuterXButton = new EButton(Parent);
				jPanel1.add(OuterXButton, "cell 0 1,growx,aligny top");
				
					OuterXButton.setText("Outer X");
					SafeAreaButton = new EButton(Parent);
					jPanel1.add(SafeAreaButton, "cell 1 1,growx,aligny top");
					
						SafeAreaButton.setText("Safe Area");
						SafeAreaButton.addActionListener(new java.awt.event.ActionListener() {
						    public void actionPerformed(java.awt.event.ActionEvent evt) {
							SafeAreaButtonActionPerformed(evt);
						    }
						});
						OuterXButton.addActionListener(new java.awt.event.ActionListener() {
						    public void actionPerformed(java.awt.event.ActionEvent evt) {
							OuterXButtonActionPerformed(evt);
						    }
						});
						ThirdsButton.addActionListener(new java.awt.event.ActionListener() {
						    public void actionPerformed(java.awt.event.ActionEvent evt) {
							ThirdsButtonActionPerformed(evt);
						    }
						});
						CenterXButton.addActionListener(new java.awt.event.ActionListener() {
						    public void actionPerformed(java.awt.event.ActionEvent evt) {
							CenterXButtonActionPerformed(evt);
						    }
						});
	NavigationPanel1 = new javax.swing.JPanel();
	
		NavigationPanel1.setBackground(java.awt.Color.black);
		bg.add(NavigationPanel1, "cell 0 1,alignx left,aligny top");
		NavigationPanel1.setLayout(new MigLayout("", "[140px][140px][][140px]", "[35px]"));
		SettingsMenu1Button1 = new EButton(Parent);
		NavigationPanel1.add(SettingsMenu1Button1, "cell 0 0,grow");
		
			SettingsMenu1Button1.setText("Settings Tab 1");
			SettingsMenu2Button1 = new EButton(Parent);
			NavigationPanel1.add(SettingsMenu2Button1, "cell 1 0,grow");
			
				SettingsMenu2Button1.setText("Settings Tab 2");
				GuidesMenuButton1 = new EButton(Parent);
				NavigationPanel1.add(GuidesMenuButton1, "cell 2 0,grow");
				
					GuidesMenuButton1.setText("Guides");
					GuidesMenuButton1.setChecked(true);
					GuidesMenuButton1.addActionListener(new java.awt.event.ActionListener() {
					    public void actionPerformed(java.awt.event.ActionEvent evt) {
						GuidesMenuButton1ActionPerformed(evt);
					    }
					});
				PhotoSettingsMenu1 = new EButton(Parent);
				NavigationPanel1.add(PhotoSettingsMenu1, "flowx,cell 3 0,grow");
				
					PhotoSettingsMenu1.setText("Photo Settings");
						PhotoSettingsMenu1.addActionListener(new java.awt.event.ActionListener() {
						    public void actionPerformed(java.awt.event.ActionEvent evt) {
							PhotoSettingsMenu1ActionPerformed(evt);
						    }
						});
						SettingsMenu2Button1.addActionListener(new java.awt.event.ActionListener() {
						    public void actionPerformed(java.awt.event.ActionEvent evt) {
							SettingsMenu2Button1ActionPerformed(evt);
						    }
						});
						SettingsMenu1Button1.addActionListener(new java.awt.event.ActionListener() {
						    public void actionPerformed(java.awt.event.ActionEvent evt) {
							SettingsMenu1Button1ActionPerformed(evt);
						    }
						});
	SettingsCancelButton = new EButton(Parent);
	bg.add(SettingsCancelButton, "cell 2 1,alignx right");
	
		SettingsCancelButton.setText("Close");
		SettingsCancelButton.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			SettingsCancelButtonActionPerformed(evt);
		    }
		});
    }

    private void SettingsCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SettingsCancelButtonActionPerformed
	Parent.Camera.SetGuides(CenterXButton.isChecked(), OuterXButton.isChecked(), ThirdsButton.isChecked(), SafeAreaButton.isChecked());

	try { // Save to config file
	    Parent.Camera.WriteConfigFile("autosave.config");
	} catch (IOException ex) {
	    Logger.getLogger(Settings1Layout.class.getName()).log(Level.SEVERE, null, ex);
	}

	Parent.LoadMainCard();
    }// GEN-LAST:event_SettingsCancelButtonActionPerformed

    private void CenterXButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CenterXButtonActionPerformed
	CenterXButton.setChecked(!CenterXButton.isChecked());
	guidespreview1.SetOptions(CenterXButton.isChecked(), OuterXButton.isChecked(), ThirdsButton.isChecked(), SafeAreaButton.isChecked());
	RedrawPreviewArea();
    }// GEN-LAST:event_CenterXButtonActionPerformed

    private void ThirdsButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ThirdsButtonActionPerformed
	ThirdsButton.setChecked(!ThirdsButton.isChecked());
	guidespreview1.SetOptions(CenterXButton.isChecked(), OuterXButton.isChecked(), ThirdsButton.isChecked(), SafeAreaButton.isChecked());
	RedrawPreviewArea();
    }// GEN-LAST:event_ThirdsButtonActionPerformed

    private void SafeAreaButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SafeAreaButtonActionPerformed
	SafeAreaButton.setChecked(!SafeAreaButton.isChecked());
	guidespreview1.SetOptions(CenterXButton.isChecked(), OuterXButton.isChecked(), ThirdsButton.isChecked(), SafeAreaButton.isChecked());
	RedrawPreviewArea();
    }// GEN-LAST:event_SafeAreaButtonActionPerformed

    private void OuterXButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_OuterXButtonActionPerformed
	OuterXButton.setChecked(!OuterXButton.isChecked());
	guidespreview1.SetOptions(CenterXButton.isChecked(), OuterXButton.isChecked(), ThirdsButton.isChecked(), SafeAreaButton.isChecked());
	RedrawPreviewArea();
    }// GEN-LAST:event_OuterXButtonActionPerformed

    private void SettingsMenu1Button1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SettingsMenu1Button1ActionPerformed
	CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
	cl.show(Parent.GetCardManager(), "Settings1Card");
	Parent.StopVideoPlayer();
    }// GEN-LAST:event_SettingsMenu1Button1ActionPerformed

    private void SettingsMenu2Button1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SettingsMenu2Button1ActionPerformed
	CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
	cl.show(Parent.GetCardManager(), "Settings2Card");
	Parent.Settings2CardLayout.Load();
    }// GEN-LAST:event_SettingsMenu2Button1ActionPerformed

    private void GuidesMenuButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_GuidesMenuButton1ActionPerformed
    }// GEN-LAST:event_GuidesMenuButton1ActionPerformed

    private void SettingsMenu3ButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_SettingsMenu3ButtonActionPerformed
	CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
	cl.show(Parent.GetCardManager(), "Settings3Card");
	Parent.Settings3CardLayout.Load();
    }// GEN-LAST:event_SettingsMenu3ButtonActionPerformed

    private void PhotoSettingsMenu1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PhotoSettingsMenu1ActionPerformed
	Parent.PhotoSettingsCardLayout.Load();
	CardLayout cl = (CardLayout) (Parent.GetCardManager().getLayout());
	cl.show(Parent.GetCardManager(), "PhotoSettings");
    }// GEN-LAST:event_PhotoSettingsMenu1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private EButton CenterXButton;
    private EButton GuidesMenuButton1;
    private javax.swing.JPanel NavigationPanel1;
    private EButton OuterXButton;
    private EButton PhotoSettingsMenu1;
    private EButton SafeAreaButton;
    private EButton SettingsCancelButton;
    private EButton SettingsMenu1Button1;
    private EButton SettingsMenu2Button1;
    private EButton SettingsMenu3Button;
    private EButton ThirdsButton;
    private javax.swing.JPanel bg;
    private Guides guidespreview1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
