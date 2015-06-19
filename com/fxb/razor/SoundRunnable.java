package com.fxb.razor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

import android.os.*;

import com.fxb.razor.common.*;

public class SoundRunnable implements Runnable
{
    public static long idGather;
    private static long lastSoundTime;
    public static Object lock;
    private static int loopCount;
    public static Sound soundGather;
    private static int timePlayGap;
    private final String[] slowSoundDevices;
    
    static {
        SoundRunnable.loopCount = 0;
        SoundRunnable.lastSoundTime = 0L;
        SoundRunnable.timePlayGap = 150;
        SoundRunnable.lock = new Object();
    }
    
    public SoundRunnable() {
        this.slowSoundDevices = new String[] { "GT-I9100", "olympus" };
        final String device = Build.DEVICE;
        final boolean b = false;
        final String[] slowSoundDevices = this.slowSoundDevices;
        final int length = slowSoundDevices.length;
        int n = 0;
        boolean b2;
        while (true) {
            b2 = b;
            if (n >= length) {
                break;
            }
            if (slowSoundDevices[n].equalsIgnoreCase(device)) {
                b2 = true;
                break;
            }
            ++n;
        }
        int timePlayGap;
        if (b2) {
            timePlayGap = 150;
        }
        else {
            timePlayGap = 50;
        }
        SoundRunnable.timePlayGap = timePlayGap;
    }
    
    public static void cancelGather() {
        synchronized (SoundRunnable.lock) {
            if (SoundRunnable.soundGather != null) {
                SoundRunnable.soundGather.stop(SoundRunnable.idGather);
            }
        }
    }
    
    public static float getSoundVolume() {
        return Global.soundVolume * 0.7f;
    }
    
    @Override
    public void run() {
    Label_0096_Outer:
        while (true) {
            try {
                if (Global.createEnemyArrayState == 1) {
                    Global.createEnemyArrayState = 3;
                    Control.levelClear();
                }
                if (Global.queueSound.size() > 0) {
                    final long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis - SoundRunnable.lastSoundTime > SoundRunnable.timePlayGap) {
                        final SoundHandle.SoundItem soundItem = Global.queueSound.take();
                        while (true) {
                            Label_0114: {
                                if (!soundItem.isLaser) {
                                    break Label_0114;
                                }
                                synchronized (SoundRunnable.lock) {
                                    SoundRunnable.soundGather = soundItem.sound;
                                    SoundRunnable.idGather = soundItem.sound.play(getSoundVolume());
                                    // monitorexit(SoundRunnable.lock)
                                    SoundRunnable.lastSoundTime = currentTimeMillis;
                                    Thread.sleep(50L);
                                    continue Label_0096_Outer;
                                }
                            }
                            soundItem.sound.play(getSoundVolume());
                            break;
                        }
                    }
                    continue;
                }
                else if (Global.isEndlessMode && Global.endlessHashState > 1) {
                    switch (Global.endlessHashState) {
                        case 2: {
                            Global.endlessHashState = -1;
                            Control.levelClearForEndless2();
                            continue;
                        }
                        case 3: {
                            Global.endlessHashState = -1;
                            Control.levelClearForEndless3();
                            continue;
                        }
                        case 4: {
                            Global.endlessHashState = -1;
                            Control.levelClearForEndless4();
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                else if (Global.bossCallState > 0) {
                    switch (Global.bossCallState) {
                        case 1: {
                            Global.bossCallState = -2;
                            Control.bossCall(1);
                            continue;
                        }
                        case 2: {
                            Global.bossCallState = -2;
                            Control.bossCall(2);
                            continue;
                        }
                        case 3: {
                            Global.bossCallState = -2;
                            Control.bossCall(3);
                            continue;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                else {
                    Thread.sleep(50L);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
    }
}
