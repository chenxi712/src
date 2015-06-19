package com.fxb.razor.objects.subgun;

import com.fxb.razor.flash.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.roles.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;

public class Invince extends BaseSubGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private float duralTime;
    private MyImage imgEffect;
    
    static {
        Invince.PlayerNum = 1;
        Invince.strRoot = "subgun/xml/";
        Invince.strPath = new String[] { "invince_total_1" };
        Invince.strEffect = "subgun/pack/invince.pack";
    }
    
    public Invince() {
        this.flashPlayers = new FlashPlayer[Invince.PlayerNum];
        this.scale = 0.35f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Invince.strRoot + Invince.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Invince.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setFrame(0, 39, 40, 89);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
        this.effectFrame = 45;
        this.setPosition(82.0f, 67.0f);
        final TextureAtlas textureAtlas = Global.manager.get(Invince.strEffect, TextureAtlas.class);
        (this.actorEffect = new AnimationActor(0.04f, textureAtlas.findRegions("invince1"))).setOrigin(58.0f, 45.0f);
        this.actorEffect.setScale(1.0f);
        this.actorEffect.setPosition(115.0f - this.actorEffect.getOriginX(), 97.0f - this.actorEffect.getOriginY());
        (this.actorEffect2 = new AnimationActor(0.125f, textureAtlas.findRegions("invince2"))).setOrigin(60.0f, 49.0f);
        this.actorEffect2.setScale(1.0f);
        this.actorEffect2.setPosition(117.0f - this.actorEffect2.getOriginX(), 101.0f - this.actorEffect2.getOriginY());
        this.subGunIcon.setIcon(Assets.atlasUiGame.findRegion("wudi"));
        this.initImgEffect();
        this.attackInterval = 20.0f;
        this.duralTime = 5.0f;
        this.setEnhanceLevel();
    }
    
    private void initImgEffect() {
        (this.imgEffect = new MyImage(Global.manager.get(Invince.strEffect, TextureAtlas.class).findRegion("fanghuzhao"))).setPosition(58.0f, 72.0f);
        this.imgEffect.setOrigin(this.imgEffect.getWidth() / 2.0f, this.imgEffect.getHeight() / 2.0f);
        this.imgEffect.setName("show_invince");
        this.imgEffect.setTouchable(Touchable.disabled);
    }
    
    public static void loadElements() {
        for (int i = 0; i < Invince.strPath.length; ++i) {
            Global.manager.load(Invince.strRoot + Invince.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Invince.strEffect, TextureAtlas.class);
    }
    
    private void showEffect() {
        this.imgEffect.clearActions();
        this.imgEffect.setScale(0.1f);
        this.imgEffect.addAction(Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 0.5f, Interpolation.swingOut), Actions.forever(Actions.sequence(Actions.scaleTo(0.9f, 0.9f, 0.5f), Actions.scaleTo(1.05f, 1.05f, 0.5f)))));
        Global.groupEffectPlayer.addActor(this.imgEffect);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Invince.strPath.length; ++i) {
            Global.manager.unload(Invince.strRoot + Invince.strPath[i] + ".xml");
        }
        Global.manager.unload(Invince.strEffect);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
    }
    
    @Override
    public void attack() {
        super.attack();
        ((Player)this.getParent()).setInvinceTime(this.duralTime);
    }
    
    @Override
    public void checkState() {
        super.checkState();
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
    }
    
    @Override
    protected void setEnhanceLevel() {
        super.setEnhanceLevel();
        final int weaponEnhance = PreferHandle.readWeaponEnhance("Invince");
        if (weaponEnhance >= 1) {
            this.attackInterval -= 2.0f;
        }
        if (weaponEnhance >= 2) {
            this.attackInterval -= 3.0f;
        }
        if (weaponEnhance >= 3) {
            this.attackInterval -= 5.0f;
        }
    }
}
