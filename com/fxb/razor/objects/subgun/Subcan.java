package com.fxb.razor.objects.subgun;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.fxb.razor.flash.*;
import com.fxb.razor.objects.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;

public class Subcan extends BaseSubGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    private Array<TextureAtlas.AtlasRegion> arrRegionBullet;
    private Array<TextureAtlas.AtlasRegion> arrRegionShot;
    private float attackRadius;
    private float damage;
    private Vector2 position;
    
    static {
        Subcan.PlayerNum = 1;
        Subcan.strRoot = "subgun/xml/";
        Subcan.strPath = new String[] { "subcan_total_1" };
        Subcan.strEffect = "effect/missile.pack";
        Subcan.strSound = "sound/weapon_subcan.ogg";
    }
    
    public Subcan() {
        this.position = new Vector2();
        this.scale = 0.35f;
        this.flashPlayers = new FlashPlayer[Subcan.PlayerNum];
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Subcan.strRoot + Subcan.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Subcan.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setFrame(0, 39, 40, 89);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
        this.effectFrame = 71;
        this.setPosition(84.0f, 65.0f);
        final TextureAtlas textureAtlas = Global.manager.get(Subcan.strEffect, TextureAtlas.class);
        (this.arrRegionShot = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 0, 10);
        (this.arrRegionBullet = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 10, 3);
        Bullet10.arrRegionBullet = this.arrRegionBullet;
        (this.actorEffect = new AnimationActor(0.0625f, this.arrRegionShot)).setOrigin(50.0f, 50.0f);
        this.actorEffect.setScale(0.9f);
        this.actorEffect.setPosition(169.0f - this.actorEffect.getOriginX(), 93.0f - this.actorEffect.getOriginY());
        this.subGunIcon.setIcon(Assets.atlasUiGame.findRegion("dapao"));
        this.attackInterval = 5.0f;
        this.damage = 300.0f;
        this.attackRadius = 30.0f;
        this.setEnhanceLevel();
    }
    
    public static void loadElements() {
        for (int i = 0; i < Subcan.strPath.length; ++i) {
            Global.manager.load(Subcan.strRoot + Subcan.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Subcan.strEffect, TextureAtlas.class);
        Global.manager.load(Subcan.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Subcan.strPath.length; ++i) {
            Global.manager.unload(Subcan.strRoot + Subcan.strPath[i] + ".xml");
        }
        Global.manager.unload(Subcan.strEffect);
        Global.manager.unload(Subcan.strSound);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
    }
    
    public void addBullet() {
        final Bullet10 bullet10 = new Bullet10();
        bullet10.Clear();
        final TextureAtlas.AtlasRegion region = Assets.atlasMainGun.findRegion("paodan-2");
        bullet10.setRegion(region);
        bullet10.setSize(region.getRegionWidth(), region.getRegionHeight());
        bullet10.setOrigin(region.getRegionWidth() / 2.0f, region.getRegionHeight() / 2.0f);
        bullet10.setDamage(this.damage, this.damage, this.damage);
        bullet10.setYIncrease(0.2f);
        bullet10.setRadius(this.attackRadius);
        this.position.set(this.getRight(), this.getY());
        this.localToStageCoordinates(this.position);
        bullet10.GetSpeed().set(1.0f, 0.0f).nor().scl(12.0f);
        bullet10.setPosition(205.0f, 178.0f);
        Global.groupBulletPlayer.addActor(bullet10);
    }
    
    @Override
    public void attack() {
        super.attack();
        this.addBullet();
        SoundHandle.playForSubcan();
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
    }
    
    @Override
    protected void setEnhanceLevel() {
        super.setEnhanceLevel();
        final int weaponEnhance = PreferHandle.readWeaponEnhance("Subcan");
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
        this.damage *= 1.0f + n3;
    }
}
