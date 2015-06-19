package com.fxb.razor.objects.subgun;

import com.fxb.razor.flash.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.roles.*;
import com.fxb.razor.common.*;

public class Drum extends BaseSubGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    private float addPercent;
    private float duralTime;
    private MyImage imgEffect;
    
    static {
        Drum.PlayerNum = 1;
        Drum.strRoot = "subgun/xml/";
        Drum.strPath = new String[] { "drum_total_1" };
        Drum.strEffect = "subgun/pack/drum.pack";
        Drum.strSound = "sound/weapon_drum.ogg";
    }
    
    public Drum() {
        this.flashPlayers = new FlashPlayer[Drum.PlayerNum];
        this.scale = 0.35f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Drum.strRoot + Drum.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Drum.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setFrame(0, 39, 40, 89);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
        this.effectFrame = 49;
        this.setPosition(82.0f, 70.0f);
        (this.actorEffect = new AnimationActor(0.09090909f, Global.manager.get(Drum.strEffect, TextureAtlas.class).getRegions())).setOrigin(270.0f, 82.0f);
        this.actorEffect.setScale(0.35f);
        this.actorEffect.setLoop(true);
        this.actorEffect.setPlayCount(4);
        this.actorEffect.setPosition(131.0f - this.actorEffect.getOriginX(), 100.0f - this.actorEffect.getOriginY());
        this.subGunIcon.setIcon(Assets.atlasUiGame.findRegion("jiagong"));
        this.attackInterval = 15.0f;
        this.addPercent = 0.2f;
        this.duralTime = 7.0f;
        this.setEnhanceLevel();
        this.initImgEffect();
    }
    
    private void initImgEffect() {
        (this.imgEffect = new MyImage(Assets.atlasArcher.findRegion("drum_1_gongjituteng"))).setPosition(150.0f, 295.0f);
        this.imgEffect.setOrigin(this.imgEffect.getWidth() / 2.0f, this.imgEffect.getHeight() / 2.0f);
        this.imgEffect.setName("show_drum");
    }
    
    public static void loadElements() {
        for (int i = 0; i < Drum.strPath.length; ++i) {
            Global.manager.load(Drum.strRoot + Drum.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Drum.strEffect, TextureAtlas.class);
        Global.manager.load(Drum.strSound, Sound.class);
    }
    
    private void showEffect() {
        this.imgEffect.clearActions();
        this.imgEffect.setScale(0.1f);
        this.imgEffect.addAction(Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 0.5f, Interpolation.swingOut), Actions.forever(Actions.sequence(Actions.scaleTo(0.7f, 0.7f, 0.5f), Actions.scaleTo(1.0f, 1.0f, 0.5f)))));
        Global.groupEffectPlayer.addActor(this.imgEffect);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Drum.strPath.length; ++i) {
            Global.manager.unload(Drum.strRoot + Drum.strPath[i] + ".xml");
        }
        Global.manager.unload(Drum.strEffect);
        Global.manager.unload(Drum.strSound);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
    }
    
    @Override
    public void attack() {
        super.attack();
        ((Player)this.getParent()).setDrum(this.addPercent, this.duralTime);
        this.showEffect();
        SoundHandle.playForDrum();
    }
    
    @Override
    protected void setEnhanceLevel() {
        super.setEnhanceLevel();
        final int weaponEnhance = PreferHandle.readWeaponEnhance("Drum");
        if (weaponEnhance >= 1) {
            this.addPercent += 0.1f;
        }
        if (weaponEnhance >= 2) {
            this.addPercent += 0.2f;
        }
        if (weaponEnhance >= 3) {
            this.addPercent += 0.3f;
        }
    }
}
