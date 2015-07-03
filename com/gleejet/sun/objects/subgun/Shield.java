package com.gleejet.sun.objects.subgun;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.utils.ui.*;

public class Shield extends BaseSubGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    private float duralTime;
    private MyImage imgEffect;
    private float shieldDamage;
    
    static {
        Shield.PlayerNum = 1;
        Shield.strRoot = "subgun/xml/";
        Shield.strPath = new String[] { "shield_total_1" };
        Shield.strEffect = "subgun/pack/shield.pack";
        Shield.strSound = "sound/weapon_shield.ogg";
    }
    
    public Shield() {
        this.scale = 0.35f;
        this.flashPlayers = new FlashPlayer[Shield.PlayerNum];
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Shield.strRoot + Shield.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Shield.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setFrame(0, 39, 40, 79);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
        this.effectFrame = 53;
        this.setPosition(90.0f, 67.0f);
        (this.actorEffect = new AnimationActor(0.0625f, Global.manager.get(Shield.strEffect, TextureAtlas.class).getRegions())).setOrigin(90.0f, 45.0f);
        this.actorEffect.setPosition(127.0f - this.actorEffect.getOriginX(), 97.0f - this.actorEffect.getOriginY());
        this.subGunIcon.setIcon(Assets.atlasUiGame.findRegion("jiafang"));
        this.attackInterval = 15.0f;
        this.shieldDamage = 300.0f;
        this.duralTime = 5.0f;
        this.setEnhanceLevel();
        this.initImgEffect();
    }
    
    private void initImgEffect() {
        (this.imgEffect = new MyImage(Assets.atlasArcher.findRegion("shield_1_fangyututeng"))).setPosition(170.0f, 295.0f);
        this.imgEffect.setOrigin(this.imgEffect.getWidth() / 2.0f, this.imgEffect.getHeight() / 2.0f);
        this.imgEffect.setName("show_shield");
    }
    
    public static void loadElements() {
        for (int i = 0; i < Shield.strPath.length; ++i) {
            Global.manager.load(Shield.strRoot + Shield.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Shield.strEffect, TextureAtlas.class);
        Global.manager.load(Shield.strSound, Sound.class);
    }
    
    private void showEffect() {
        this.imgEffect.clearActions();
        this.imgEffect.setScale(0.1f);
        this.imgEffect.addAction(Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 0.5f, Interpolation.swingOut), Actions.forever(Actions.sequence(Actions.scaleTo(0.7f, 0.7f, 0.5f), Actions.scaleTo(1.0f, 1.0f, 0.5f)))));
        Global.groupEffectPlayer.addActor(this.imgEffect);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Shield.strPath.length; ++i) {
            Global.manager.unload(Shield.strRoot + Shield.strPath[i] + ".xml");
        }
        Global.manager.unload(Shield.strEffect);
        Global.manager.unload(Shield.strSound);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
    }
    
    @Override
    public void attack() {
        super.attack();
        ((Player)this.getParent()).setShield(this.shieldDamage, this.duralTime);
        this.showEffect();
        SoundHandle.playForShield();
    }
    
    @Override
    protected void setEnhanceLevel() {
        super.setEnhanceLevel();
        final int weaponEnhance = PreferHandle.readWeaponEnhance("Shield");
        float n = 0.0f;
        if (weaponEnhance >= 1) {
            n = 0.0f + 0.1f;
        }
        float n2 = n;
        if (weaponEnhance >= 2) {
            n2 = n + 0.2f;
        }
        float n3 = n2;
        if (weaponEnhance >= 3) {
            n3 = n2 + 0.3f;
        }
        this.shieldDamage *= 1.0f + n3;
    }
}
