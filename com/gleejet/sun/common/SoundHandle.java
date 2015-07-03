package com.gleejet.sun.common;

import com.gleejet.sun.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.audio.*;

public class SoundHandle
{
    public static void cancelForGather() {
        SoundRunnable.cancelGather();
    }
    
    public static void init() {
    }
    
    public static void playForAcid() {
        setPlay("sound/weapon_acid.ogg", false);
    }
    
    public static void playForBomb() {
        setPlay("sound/dead_bomb_" + MathUtils.random(1, 3) + ".ogg", false);
    }
    
    public static void playForBoss() {
        setPlay("sound/dead_boss.ogg", false);
    }
    
    public static void playForButton2() {
        setPlay("sound/button2.ogg", false);
    }
    
    public static void playForButton3() {
        setPlay("sound/button3.ogg", false);
    }
    
    public static void playForCannon() {
        setPlay("sound/weapon_cannon.ogg", false);
    }
    
    public static void playForDragonDie() {
        setPlay("sound/dead_dragon_" + MathUtils.random(1, 2) + ".ogg", false);
    }
    
    public static void playForDrum() {
        setPlay("sound/weapon_drum.ogg", false);
    }
    
    public static void playForElectric() {
        setPlay("sound/weapon_electric.ogg", false);
    }
    
    public static void playForFlame() {
        setPlay("sound/weapon_flame.ogg", false);
    }
    
    public static void playForFootDie() {
        setPlay("sound/dead_foot_" + MathUtils.random(1, 3) + ".ogg", false);
    }
    
    public static void playForFreezefog() {
        setPlay("sound/weapon_ice.ogg", false);
    }
    
    public static void playForGather(final float n) {
        setPlay("sound/weapon_gather.ogg", true);
    }
    
    public static void playForGold() {
        setPlay("sound/gold.ogg", false);
    }
    
    public static void playForLaser() {
        setPlay("sound/weapon_laser.ogg", false);
    }
    
    public static void playForLeap() {
        setPlay("sound/weapon_leap.ogg", false);
    }
    
    public static void playForLevelup() {
        setPlay("sound/levelup.ogg", false);
    }
    
    public static void playForMissile() {
        setPlay("sound/weapon_missile.ogg", false);
    }
    
    public static void playForNumber() {
        setPlay("sound/number.ogg", false);
    }
    
    public static void playForPipe() {
        setPlay("sound/weapon_pipe.ogg", false);
    }
    
    public static void playForScatter() {
        setPlay("sound/weapon_scatter.ogg", false);
    }
    
    public static void playForShield() {
        setPlay("sound/weapon_shield.ogg", false);
    }
    
    public static void playForShock() {
        setPlay("sound/weapon_shock.ogg", false);
    }
    
    public static void playForStar() {
        setPlay("sound/star.ogg", false);
    }
    
    public static void playForSubbomb() {
        setPlay("sound/dead_bomb_3.ogg", false);
    }
    
    public static void playForSubcan() {
        setPlay("sound/weapon_subcan.ogg", false);
    }
    
    public static void playForTrack() {
        setPlay("sound/weapon_track.ogg", false);
    }
    
    public static void playForTree() {
        setPlay("sound/weapon_tree.ogg", false);
    }
    
    public static void playForWarning() {
        setPlay("sound/warning.ogg", false);
    }
    
    private static void setPlay(final String s, final boolean b) {
        Global.queueSound.offer(new SoundItem(Global.manager.get(s, Sound.class), b));
    }
    
    public static class SoundItem
    {
        public boolean isLaser;
        public Sound sound;
        
        public SoundItem(final Sound sound) {
            this(sound, false);
        }
        
        public SoundItem(final Sound sound, final boolean isLaser) {
            this.sound = sound;
            this.isLaser = isLaser;
        }
    }
}
