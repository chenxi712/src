package com.fxb.razor.objects.subgun;

import com.badlogic.gdx.utils.*;
import com.fxb.razor.flash.*;
import com.fxb.razor.objects.*;
import com.fxb.razor.stages.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;

public class Bomb extends BaseSubGun
{
    private static int PlayerNum;
    public static Array<TextureAtlas.AtlasRegion> arrRegionBullet;
    public static Array<TextureAtlas.AtlasRegion> arrRegionShot;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private final float attackRadius;
    private final float damage;
    private int missileNum;
    
    static {
        Bomb.PlayerNum = 1;
        Bomb.strRoot = "subgun/xml/";
        Bomb.strPath = new String[] { "bomb_total_1" };
        Bomb.strEffect = "effect/missile.pack";
    }
    
    public Bomb() {
        this.attackRadius = 50.0f;
        this.damage = 300.0f;
        this.flashPlayers = new FlashPlayer[Bomb.PlayerNum];
        this.scale = 0.35f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Bomb.strRoot + Bomb.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Bomb.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setFrame(0, 39, 40, 84);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
        this.setPosition(82.0f, 67.0f);
        this.subGunIcon.setIcon(Assets.atlasUiGame.findRegion("hongzha"));
        final TextureAtlas textureAtlas = Global.manager.get(Bomb.strEffect, TextureAtlas.class);
        (Bomb.arrRegionShot = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 0, 10);
        (Bomb.arrRegionBullet = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 10, 3);
        Bullet10.arrRegionBullet = Bomb.arrRegionBullet;
        this.attackInterval = 15.0f;
        this.missileNum = 3;
        this.setEnhanceLevel();
    }
    
    public static void loadElements() {
        for (int i = 0; i < Bomb.strPath.length; ++i) {
            Global.manager.load(Bomb.strRoot + Bomb.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Bomb.strEffect, TextureAtlas.class);
    }
    
    private void shakeStage() {
        ((GameStage)this.getStage()).shake(3);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Bomb.strPath.length; ++i) {
            Global.manager.unload(Bomb.strRoot + Bomb.strPath[i] + ".xml");
        }
        Global.manager.unload(Bomb.strEffect);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
    }
    
    public void addBullet() {
        for (int i = 0; i < this.missileNum; ++i) {
            final Bullet10 bullet10 = new Bullet10();
            bullet10.Clear();
            final TextureAtlas.AtlasRegion region = Assets.atlasMainGun.findRegion("paodan-2");
            bullet10.setRegion(region);
            bullet10.setSize(region.getRegionWidth(), region.getRegionHeight());
            bullet10.setOrigin(region.getRegionWidth() / 2.0f, region.getRegionHeight() / 2.0f);
            bullet10.setDamage(300.0f, 300.0f, 300.0f);
            bullet10.setYIncrease(0.0f);
            bullet10.setRadius(50.0f);
            final float missileY = this.getMissileY(i);
            final float missileX = this.getMissileX(i);
            bullet10.GetSpeed().set(MathUtils.cosDeg(-50.0f), MathUtils.sinDeg(-50.0f)).nor().scl(18.0f);
            bullet10.setPosition(missileX, missileY);
            Global.groupBulletPlayer.addActor(bullet10);
        }
    }
    
    @Override
    public void attack() {
        super.attack();
        this.addBullet();
        this.shakeStage();
        SoundHandle.playForSubbomb();
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
    }
    
    public float getMissileX(final int n) {
        if (this.missileNum <= 4) {
            return n * 450.0f / (this.missileNum - 1) + 150.0f;
        }
        if (this.missileNum == 6) {
            if (n <= 3) {
                return n * 450.0f / 3.0f + 150.0f;
            }
            return (n - 4) * 200 + 300;
        }
        else {
            if (this.missileNum != 9) {
                return 0.0f;
            }
            if (n <= 3) {
                return n * 450.0f / 3.0f + 150.0f;
            }
            return 130.0f + 520.0f * (n - 4) / (this.missileNum - 4);
        }
    }
    
    public float getMissileY(final int n) {
        if (n < 4) {
            return 350.0f;
        }
        return 450.0f;
    }
    
    @Override
    protected void setEnhanceLevel() {
        super.setEnhanceLevel();
        final int weaponEnhance = PreferHandle.readWeaponEnhance("Bomb");
        if (weaponEnhance >= 1) {
            ++this.missileNum;
        }
        if (weaponEnhance >= 2) {
            this.missileNum += 2;
        }
        if (weaponEnhance >= 3) {
            this.missileNum += 3;
        }
    }
}
