package com.gleejet.sun.objects.maingun;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.utils.ui.*;

public class Freezefog extends BaseMainGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    AnimationActor actorParticle;
    AnimationActor actorShot;
    private Array<TextureAtlas.AtlasRegion> arrRegionBullet;
    float duralTime;
    float gapTime;
    float lastAutoTime;
    private Runnable runAttack;
    
    static {
        Freezefog.PlayerNum = 1;
        Freezefog.strRoot = "maingun/xml/";
        Freezefog.strPath = new String[] { "maingun_total_9" };
        Freezefog.strEffect = "effect/freeze.pack";
        Freezefog.strSound = "sound/weapon_ice.ogg";
    }
    
    public Freezefog() {
        this.gapTime = 0.1f;
        this.duralTime = 0.4f;
        this.lastAutoTime = -5.0f;
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.myclear();
        this.setGunVisible(false);
    }
    
    private void initRun() {
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; ++i) {
                    final Bullet9 bulletDamage = Pools.obtain(Bullet9.class);
                    bulletDamage.Clear();
                    Freezefog.this.setBulletDamage(bulletDamage);
                    final TextureRegion region = Freezefog.this.getRegion();
                    bulletDamage.setRegion(region);
                    float n;
                    if (i % 2 == 0) {
                        n = MathUtils.random(0.3f, 0.5f);
                    }
                    else {
                        n = MathUtils.random(0.7f, 0.9f);
                    }
                    bulletDamage.setSize(region.getRegionWidth() * n, region.getRegionHeight() * n);
                    bulletDamage.setOrigin(bulletDamage.getWidth() / 2.0f, bulletDamage.getHeight() / 2.0f);
                    final float random = MathUtils.random(0.8f, 1.3f);
                    bulletDamage.setRadius(Freezefog.this.attackRadius);
                    bulletDamage.setYIncrease(Freezefog.this.yIncrease * random);
                    Freezefog.this.position.set(Freezefog.this.imgGunFront.getRight(), Freezefog.this.imgGunFront.getY() + Freezefog.this.imgGunFront.getHeight() / 2.0f);
                    Freezefog.this.groupGun.localToStageCoordinates(Freezefog.this.position);
                    final float n2 = Freezefog.this.curAngle + MathUtils.random(-5.0f, 5.0f);
                    bulletDamage.GetSpeed().set(MathUtils.cosDeg(n2), MathUtils.sinDeg(n2)).nor().scl(Freezefog.this.bulletSpeed * random);
                    bulletDamage.setPosition(Freezefog.this.position.x, Freezefog.this.position.y);
                    Global.groupBulletPlayer.addActor(bulletDamage);
                }
                Freezefog.this.actorShot.setStart();
                Freezefog.this.actorParticle.setStart();
            }
        };
    }
    
    public static void loadElements() {
        for (int i = 0; i < Freezefog.strPath.length; ++i) {
            Global.manager.load(Freezefog.strRoot + Freezefog.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Freezefog.strEffect, TextureAtlas.class);
        Global.manager.load(Freezefog.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Freezefog.strPath.length; ++i) {
            Global.manager.unload(Freezefog.strRoot + Freezefog.strPath[i] + ".xml");
        }
        Global.manager.unload(Freezefog.strEffect);
        Global.manager.unload(Freezefog.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 5.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 5.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-9"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-9"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-9"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-9"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-9"));
        this.imgDrawf.setPosition(6.0f, 15.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(53.0f, -12.0f);
        this.imgGunFront.setPosition(9.0f, -3.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(24.0f, 12.0f);
        this.groupGun.setPosition(50.0f, 15.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-1");
        this.addActor(this.imgDrawf);
        this.addActor(this.groupGun);
        this.setSize(120.0f, 70.0f);
        this.setPosition(66.0f, 132.0f);
        this.setIcon(9);
        final TextureAtlas textureAtlas = Global.manager.get(Freezefog.strEffect, TextureAtlas.class);
        this.arrRegionBullet = textureAtlas.findRegions("freeze_bullet");
        (this.actorShot = new AnimationActor(0.05f, textureAtlas.findRegions("freeze_shot"))).setScale(1.0f);
        this.actorShot.setPosition(this.imgGunFront.getRight() - this.actorShot.getScaleX() * 34.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorShot.getScaleY() * 50.0f + 5.0f);
        this.groupGun.addActor(this.actorShot);
        (this.actorParticle = new AnimationActor(0.045454547f, textureAtlas.findRegions("freeze_particle"))).setScale(0.7f);
        this.actorParticle.setPosition(this.imgGunFront.getRight() - this.actorParticle.getScaleX() * 50.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorParticle.getScaleY() * 400.0f);
        this.groupGun.addActor(this.actorParticle);
        MyMethods.initPoolNum(Bullet9.class, 30);
        MyMethods.initPoolNum(Effect.EffectSmoke.class, 8);
        this.initRun();
    }
    
    public void SetProperty() {
        this.setJsonValue();
        this.setEnhanceLevel();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.curTime - this.lastAttackTime < this.duralTime && this.curTime - this.lastAutoTime >= this.gapTime && this.workState == WorkState.State_Work) {
            this.addBullet();
        }
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX() + 2.0f, this.getY() - 10.0f);
    }
    
    public void addBullet() {
        MyMethods.delayRun(this.groupGun, this.runAttack, 0.05f);
        this.AddGunRecoil();
        this.lastAutoTime = this.curTime;
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        this.lastAttackTime = this.curTime;
        this.flashPlayers[0].play();
        this.duralHandle();
        SoundHandle.playForFreezefog();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet9.class);
        MyMethods.clearPool(Effect.EffectSmoke.class);
    }
    
    public TextureRegion getRegion() {
        return this.arrRegionBullet.get(MathUtils.random(this.arrRegionBullet.size - 1));
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Freezefog.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Freezefog.strRoot + Freezefog.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(71.0f, 37.0f);
        this.endFrame = 6;
        this.flashPlayers[0].play();
        this.flashPlayers[0].pause();
    }
}
