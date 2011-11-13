
import java.awt.Color;
import java.io.File;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.util.Scanner;


/*! Copyright (C) 2009 Apertus, All Rights Reserved
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
enum OStype {

    Windows, Linux
}

enum streamVideoPlayer {

    VLC, GSTREAMER
}

/**
 *
 * Holds client-side user settings
 */
public class UserSettings {

    private OStype OS = null;
    private streamVideoPlayer StreamVideoPlayer = streamVideoPlayer.VLC;
    private boolean VideoStreamEnabled = true;
    private boolean VideoFullscreenMode = false;
    private boolean EVFullscreenMode = false;
    private Color ButtonBorderColor = new Color(70, 70, 70);
    private Color ButtonBorderColorChecked = new Color(255, 255, 255);
    private Color ButtonBorderColorDisabled = new Color(30, 30, 30);
    private Color ButtonBorderColorHighlighted = new Color(20, 80, 130);
    private Color ButtonTextColor = new Color(255, 255, 255);
    private Color ButtonTextColorChecked = new Color(0, 0, 0);
    private Color ButtonTextColorHighlighted = new Color(200, 200, 255);
    private Color ButtonTextColorDisabled = new Color(100, 100, 100);
    private Color ButtonBackgroundColorGradientStart = new Color(40, 40, 40);
    private Color ButtonBackgroundColorGradientEnd = new Color(0, 0, 0);
    //public static final Color ButtonBackgroundColorCheckedGradientStart = new Color(0, 155, 255);
    //public static final Color ButtonBackgroundColorCheckedGradientEnd = new Color(105, 175, 220);
    private Color ButtonBackgroundColorCheckedGradientStart = new Color(200, 200, 200);
    private Color ButtonBackgroundColorCheckedGradientEnd = new Color(255, 255, 255);
    private Color ButtonBackgroundColorDisabledGradientStart = new Color(20, 20, 20);
    private Color ButtonBackgroundColorDisabledGradientEnd = new Color(0, 0, 0);
    private Color ButtonBackgroundColorHighlightedGradientStart = new Color(50, 60, 100);
    private Color ButtonBackgroundColorHighlightedGradientEnd = new Color(0, 0, 0);
    private Color PanelBackgroundColor = new Color(0, 0, 0);
    private Color TextColor = new Color(255, 255, 255);
    private int ButtonCornerRadius = 12;
    private int ButtonFontSize = 11;
    private int ButtonBorderWidth = 2;
    private String ButtonFontName;
    private int ButtonFontWeight;

    UserSettings() {
    }

    public boolean LoadStyles(String FileName) throws FileNotFoundException {

        File ConfigFile = new File(FileName);

        if (!ConfigFile.exists()) {
            return false;
        }

        Scanner scanner1 = new Scanner(ConfigFile);
        try {
            //first use a Scanner to get each line
            while (scanner1.hasNextLine()) {
                //use a second Scanner to parse the content of each line
                Scanner scanner2 = new Scanner(scanner1.nextLine());
                scanner2.useDelimiter("=");
                if (scanner2.hasNext()) {
                    String name = scanner2.next();
                    if (!scanner2.hasNext()) {
                        break;
                    }
                    String value = scanner2.next();
                    if (name.trim().equals("ButtonBorderColor")) {
                        ButtonBorderColor = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBorderColorChecked")) {
                        ButtonBorderColorChecked = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonTextColor")) {
                        ButtonTextColor = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonTextColorChecked")) {
                        ButtonTextColorChecked = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBackgroundColorGradientStart")) {
                        ButtonBackgroundColorGradientStart = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBackgroundColorGradientEnd")) {
                        ButtonBackgroundColorGradientEnd = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBackgroundColorCheckedGradientStart")) {
                        ButtonBackgroundColorCheckedGradientStart = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBackgroundColorCheckedGradientEnd")) {
                        ButtonBackgroundColorCheckedGradientEnd = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonCornerRadius")) {
                        ButtonCornerRadius = Integer.parseInt(value.trim());
                    } else if (name.trim().equals("ButtonBorderWidth")) {
                        ButtonBorderWidth = Integer.parseInt(value.trim());
                    } else if (name.trim().equals("ButtonFontSize")) {
                        ButtonFontSize = Integer.parseInt(value.trim());
                    } else if (name.trim().equals("ButtonFont")) {
                        ButtonFontName = value.trim();
                    } else if (name.trim().equals("PanelBackgroundColor")) {
                        PanelBackgroundColor = ReadColorFromString(value);
                    } else if (name.trim().equals("TextColor")) {
                        TextColor = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBorderColorDisabled")) {
                        ButtonBorderColorDisabled = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBorderColorHighlighted")) {
                        ButtonBorderColorHighlighted = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonTextColorHighlighted")) {
                        ButtonTextColorHighlighted = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonTextColorDisabled")) {
                        ButtonTextColorDisabled = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBackgroundColorDisabledGradientStart")) {
                        ButtonBackgroundColorDisabledGradientStart = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBackgroundColorDisabledGradientEnd")) {
                        ButtonBackgroundColorDisabledGradientEnd = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBackgroundColorHighlightedGradientStart")) {
                        ButtonBackgroundColorHighlightedGradientStart = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonBackgroundColorHighlightedGradientEnd")) {
                        ButtonBackgroundColorHighlightedGradientEnd = ReadColorFromString(value);
                    } else if (name.trim().equals("ButtonFontWeight")) {
                        if (value.trim().equals("PLAIN")) {
                            ButtonFontWeight = Font.PLAIN;
                        } else if (value.trim().equals("BOLD")) {
                            ButtonFontWeight = Font.BOLD;
                        } else if (value.trim().equals("ITALIC")) {
                            ButtonFontWeight = Font.ITALIC;
                        }
                    } else {
                        //Empty or invalid line. Unable to process
                    }
                    scanner2.close();
                }
            }
        } finally {
            //ensure the underlying stream is always closed
            scanner1.close();
        }
        return true;
    }

    // This function reads a string such as "129,219,92" and converts it to an RGB Color
    private Color ReadColorFromString(String input) {
        int R = Integer.parseInt(input.trim().split(",")[0].trim());
        int G = Integer.parseInt(input.trim().split(",")[1].trim());
        int B = Integer.parseInt(input.trim().split(",")[2].trim());
        return new Color(R, G, B);
    }

    public Color GetButtonBorderColor() {
        return ButtonBorderColor;
    }

    public Color GetButtonBorderColorChecked() {
        return ButtonBorderColorChecked;
    }

    public Color GetButtonTextColor() {
        return ButtonTextColor;
    }

    public Color GetButtonTextColorChecked() {
        return ButtonTextColorChecked;
    }

    public Color GetButtonBackgroundColorGradientStart() {
        return ButtonBackgroundColorGradientStart;
    }

    public Color GetButtonBackgroundColorGradientEnd() {
        return ButtonBackgroundColorGradientEnd;
    }

    public Color GetButtonBackgroundColorCheckedGradientStart() {
        return ButtonBackgroundColorCheckedGradientStart;
    }

    public Color GetButtonBackgroundColorCheckedGradientEnd() {
        return ButtonBackgroundColorCheckedGradientEnd;
    }

    public int GetButtonCornerRadius() {
        return ButtonCornerRadius;
    }

    public int GetButtonBorderWidth() {
        return ButtonBorderWidth;
    }

    public int GetButtonFontSize() {
        return ButtonFontSize;
    }

    public String GetButtonFontName() {
        return ButtonFontName;
    }

    public int GetButtonFontWeight() {
        return ButtonFontWeight;
    }

    public Color GetPanelBackgroundColor() {
        return PanelBackgroundColor;
    }

    public Color GetTextColor() {
        return TextColor;
    }

    public void SetVideoPlayer(streamVideoPlayer input) {
        this.StreamVideoPlayer = input;
    }

    public streamVideoPlayer GetVideoPlayer() {
        return this.StreamVideoPlayer;
    }

    public void SetOS(OStype input) {
        this.OS = input;
    }

    public OStype GetOS() {
        return this.OS;
    }

    public boolean isVideoStreamEnabled() {
        return VideoStreamEnabled;
    }

    public void setVideoStreamEnabled(boolean VideoStreamEnabled) {
        this.VideoStreamEnabled = VideoStreamEnabled;
    }

    public Color getButtonTextColorDisabled() {
        return ButtonTextColorDisabled;
    }

    public Color getButtonBorderColorDisabled() {
        return ButtonBorderColorDisabled;
    }

    public Color getButtonTextColorHighlighted() {
        return ButtonTextColorHighlighted;
    }

    public Color getButtonBackgroundColorDisabledGradientEnd() {
        return ButtonBackgroundColorDisabledGradientEnd;
    }

    public Color getButtonBackgroundColorDisabledGradientStart() {
        return ButtonBackgroundColorDisabledGradientStart;
    }

    public Color getButtonBackgroundColorHighlightedGradientEnd() {
        return ButtonBackgroundColorHighlightedGradientEnd;
    }

    public Color getButtonBackgroundColorHighlightedGradientStart() {
        return ButtonBackgroundColorHighlightedGradientStart;
    }

    public Color getButtonBorderColorHighlighted() {
        return ButtonBorderColorHighlighted;
    }

    public boolean isVideoFullscreenMode() {
        return VideoFullscreenMode;
    }

    public void setVideoFullscreenMode(boolean VideoFullscreenMode) {
        this.VideoFullscreenMode = VideoFullscreenMode;
    }

    public boolean isEVFullscreenMode() {
        return EVFullscreenMode;
    }

    public void setEVFullscreenMode(boolean EVFullscreenMode) {
        this.EVFullscreenMode = EVFullscreenMode;
    }
}
