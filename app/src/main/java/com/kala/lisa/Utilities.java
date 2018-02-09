package com.kala.lisa;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * Created by Kalaman on 16.12.2017.
 */

public class Utilities {
    private static SoundPool sp;
    private static int beepSoundID;

    /**
     * Plays a beep sound
     * Can be used after scanning a QR-Code
     * @param context
     */
    public static void playBeepSound(Context context) {
        if (sp == null) {
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
            beepSoundID = sp.load( context, R.raw.beep_sound, 1);
        }
        sp.play(beepSoundID, 1, 1, 0, 0, 1);
    }
}
