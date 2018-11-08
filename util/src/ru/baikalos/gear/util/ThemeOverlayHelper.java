package ru.baikalos.gear.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import java.util.HashMap;

public class ThemeOverlayHelper {

    private static final String TAG = ThemeOverlayHelper.class.getSimpleName();

    private static final String[] DARK_COMMON_OVERLAYS = {
            "ru.baikalos.overlay.defaultdark.com.android.calculator2",
            "ru.baikalos.overlay.defaultdark.com.android.contacts",
            "ru.baikalos.overlay.defaultdark.com.android.deskclock",
            "ru.baikalos.overlay.defaultdark.com.android.dialer",
            "ru.baikalos.overlay.defaultdark.com.android.documentsui",
            "ru.baikalos.overlay.defaultdark.com.android.messaging",
            "ru.baikalos.overlay.defaultdark.com.android.packageinstaller",
            "ru.baikalos.overlay.defaultdark.com.android.phone",
            "ru.baikalos.overlay.defaultdark.com.android.server.telecom",
            "ru.baikalos.overlay.defaultdark.com.android.settings.intelligence",
            "ru.baikalos.overlay.defaultdark.com.android.settings",
    };

    private static final String[] DARK_OVERLAYS = {
            "ru.baikalos.overlay.defaultdark.android",
            "ru.baikalos.overlay.defaultdark.com.android.systemui",
    };

    private static final String[] BLACK_OVERLAYS = {
            "ru.baikalos.overlay.defaultblack.android",
            "ru.baikalos.overlay.defaultblack.com.android.systemui",
    };

    private static final String[] DARK_NOTIF_OVERLAYS = {
            "ru.baikalos.overlay.defaultdark.notif.android",
            "ru.baikalos.overlay.defaultdark.notif.com.android.systemui",
    };

    private static final String[] BLACK_NOTIF_OVERLAYS = {
            "ru.baikalos.overlay.defaultblack.notif.android",
            "ru.baikalos.overlay.defaultblack.notif.com.android.systemui",
    };

    private static final String[] DARK_TRANSPARENT_OVERLAYS = {
            "ru.baikalos.overlay.defaultdark.transparent.android",
            "ru.baikalos.overlay.defaultdark.transparent.com.android.systemui",
    };

    private static final String[] BLACK_TRANSPARENT_OVERLAYS = {
            "ru.baikalos.overlay.defaultblack.transparent.android",
            "ru.baikalos.overlay.defaultblack.transparent.com.android.systemui",
    };

    private static final String[] ACCENT_OVERLAYS = {
            "ru.baikalos.overlay.accent.amber.android",
            "ru.baikalos.overlay.accent.greenlight.android",
            "ru.baikalos.overlay.accent.lime.android",
            "ru.baikalos.overlay.accent.bluelight.android",
            "ru.baikalos.overlay.accent.cyan.android",
            "ru.baikalos.overlay.accent.denim.android",
            "ru.baikalos.overlay.accent.gold.android",
            "ru.baikalos.overlay.accent.orange.android",
            "ru.baikalos.overlay.accent.oxygen.android",
            "ru.baikalos.overlay.accent.pink.android",
            "ru.baikalos.overlay.accent.pixel.android",
            "ru.baikalos.overlay.accent.purple.android",
            "ru.baikalos.overlay.accent.red.android",
            "ru.baikalos.overlay.accent.teal.android",
            "ru.baikalos.overlay.accent.turquoise.android",
            "ru.baikalos.overlay.accent.yellow.android",
    };

    private static final HashMap<Integer, String> ACCENT_MAP = new HashMap();
    static {
        // Format: settings key, package name
        ACCENT_MAP.put(1, ACCENT_OVERLAYS[0]); // amber
        ACCENT_MAP.put(2, ACCENT_OVERLAYS[1]); // green light
        ACCENT_MAP.put(3, ACCENT_OVERLAYS[2]); // lime
        ACCENT_MAP.put(4, ACCENT_OVERLAYS[3]); // blue light
        ACCENT_MAP.put(5, ACCENT_OVERLAYS[4]); // cyan
        ACCENT_MAP.put(6, ACCENT_OVERLAYS[5]); // denim
        ACCENT_MAP.put(7, ACCENT_OVERLAYS[6]); // gold
        ACCENT_MAP.put(8, ACCENT_OVERLAYS[7]); // orange
        ACCENT_MAP.put(9, ACCENT_OVERLAYS[8]); // oxygen
        ACCENT_MAP.put(10, ACCENT_OVERLAYS[9]); // pink
        ACCENT_MAP.put(11, ACCENT_OVERLAYS[10]); // pixel
        ACCENT_MAP.put(12, ACCENT_OVERLAYS[11]); // purple
        ACCENT_MAP.put(13, ACCENT_OVERLAYS[12]); // red
        ACCENT_MAP.put(14, ACCENT_OVERLAYS[13]); // teal
        ACCENT_MAP.put(15, ACCENT_OVERLAYS[14]); // turquoise
        ACCENT_MAP.put(16, ACCENT_OVERLAYS[15]); // yellow
    }

    private ThemeOverlayHelper() {}

    public static boolean updateOverlays(Context context, IOverlayManager om, int userId) {
        boolean changed = false;
        ContentResolver resolver = context.getContentResolver();
        int baseTheme = Settings.System.getInt(resolver, Settings.System.THEMING_BASE, 0);
        boolean enabled = baseTheme == 1;
        for (String darkOverlay: DARK_OVERLAYS) {
            changed |= setOverlayEnabled(om, userId, darkOverlay, enabled);
        }
        enabled = baseTheme == 2;
        for (String blackOverlay: BLACK_OVERLAYS) {
            changed |= setOverlayEnabled(om, userId, blackOverlay, enabled);
        }
        enabled = baseTheme == 3;
        for (String blackOverlay: DARK_NOTIF_OVERLAYS) {
            changed |= setOverlayEnabled(om, userId, blackOverlay, enabled);
        }
        enabled = baseTheme == 4;
        for (String blackOverlay: BLACK_NOTIF_OVERLAYS) {
            changed |= setOverlayEnabled(om, userId, blackOverlay, enabled);
        }
        enabled = baseTheme == 5;
        for (String blackOverlay: DARK_TRANSPARENT_OVERLAYS) {
            changed |= setOverlayEnabled(om, userId, blackOverlay, enabled);
        }
        enabled = baseTheme == 6;
        for (String blackOverlay: BLACK_TRANSPARENT_OVERLAYS) {
            changed |= setOverlayEnabled(om, userId, blackOverlay, enabled);
        }
        enabled = baseTheme > 0 && baseTheme <= 6;
        for (String darkOverlay: DARK_COMMON_OVERLAYS) {
            changed |= setOverlayEnabled(om, userId, darkOverlay, enabled);
        }
        int accentSetting = Settings.System.getInt(resolver, Settings.System.THEMING_ACCENT, 0);
        String accentPackage = ACCENT_MAP.get(accentSetting);
        for (String accentOverlay: ACCENT_OVERLAYS) {
            changed |= setOverlayEnabled(om, userId, accentOverlay,
                    accentOverlay.equals(accentPackage));
        }
        return changed;
    }

    private static boolean setOverlayEnabled(IOverlayManager om, int userId, String overlay,
                                             boolean enabled) {
        boolean currentlyEnabled = isOverlayEnabled(om, userId, overlay);

        if (currentlyEnabled != enabled) {
            try {
                om.setEnabled(overlay, enabled, userId);
                return true;
            } catch (RemoteException e) {
                Log.w(TAG, "Can't change theme for " + overlay, e);
            }
        }
        return false;
    }

    private static boolean isOverlayEnabled(IOverlayManager om, int userId, String overlay) {
        OverlayInfo systemuiThemeInfo = null;
        try {
            systemuiThemeInfo = om.getOverlayInfo(overlay, userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return systemuiThemeInfo != null && systemuiThemeInfo.isEnabled();
    }

    public static boolean isUsingDarkTheme(IOverlayManager om, int userId) {
        return isOverlayEnabled(om, userId, DARK_OVERLAYS[0]) ||
                isOverlayEnabled(om, userId, BLACK_OVERLAYS[0]);
    }
}
