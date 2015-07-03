package com.gleejet.sun.common;

import com.badlogic.gdx.audio.*;

public class MusicHandle
{
    public static Music music;
    
    private static float getMusicVolume() {
        return Math.min(2.5f * Global.soundVolume, 1.0f);
    }
    
    public static void init() {
        MusicHandle.music = null;
    }
    
    public static void pause() {
        if (MusicHandle.music != null) {
            MusicHandle.music.pause();
        }
    }
    
    public static void playForBattle() {
        setPlay("music/battle.ogg", true);
    }
    
    public static void playForLose() {
        setPlay("music/lose.ogg", false);
    }
    
    public static void playForMenu() {
        setPlay("music/menu.ogg", true);
    }
    
    public static void playForWin() {
        setPlay("music/win.ogg", false);
    }
    
    private static void replay(final String s, final boolean looping) {
        if (MusicHandle.music != null) {
            MusicHandle.music.stop();
        }
        (MusicHandle.music = Global.manager.get(s, Music.class)).setVolume(getMusicVolume());
        MusicHandle.music.setLooping(looping);
        MusicHandle.music.play();
    }
    
    public static void replayForBattle() {
        replay("music/battle.ogg", true);
    }
    
    public static void resume() {
        if (MusicHandle.music != null) {
            MusicHandle.music.play();
        }
    }
    
    private static void setPlay(final String s, final boolean looping) {
        final Music music = Global.manager.get(s, Music.class);
        if (MusicHandle.music == music) {
            return;
        }
        if (MusicHandle.music != null) {
            MusicHandle.music.stop();
        }
        (MusicHandle.music = music).setVolume(Global.soundVolume);
        MusicHandle.music.setLooping(looping);
        MusicHandle.music.play();
    }
    
    public static void setVolume() {
        if (MusicHandle.music != null) {
            MusicHandle.music.setVolume(getMusicVolume());
        }
    }
}
