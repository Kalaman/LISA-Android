package com.kala.lisa;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by Kalaman on 01.06.16.
 */
public class PreferenceManager {
    public static Context m_context;
    private static SharedPreferences m_sharedPref;
    private static SharedPreferences.Editor m_sharedPrefeditor;

    public PreferenceManager(Context context)
    {
        m_context = context;
        m_sharedPref = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        m_sharedPrefeditor =m_sharedPref.edit();
    }

    public static String readSharedString (String sharedKey)
    {
        return m_sharedPref.getString(sharedKey, null);
    }

    public static void saveSharedString (String sharedKey,String sharedValue)
    {
        m_sharedPrefeditor.putString(sharedKey, sharedValue);
        m_sharedPrefeditor.commit();
    }

    public static Boolean readSharedBoolean (String sharedKey)
    {
        return m_sharedPref.getBoolean(sharedKey, false);
    }

    public static void saveSharedBoolean (String sharedKey,Boolean sharedValue)
    {
        m_sharedPrefeditor.putBoolean(sharedKey, sharedValue);
        m_sharedPrefeditor.commit();
    }

    public static int readSharedInt(String sharedKey)
    {
        return m_sharedPref.getInt(sharedKey, -1);
    }

    public static void saveSharedInt (String sharedKey,int sharedValue)
    {
        m_sharedPrefeditor.putInt(sharedKey, sharedValue);
        m_sharedPrefeditor.commit();
    }

}
