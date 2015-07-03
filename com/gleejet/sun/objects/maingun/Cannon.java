package com.gleejet.sun.objects.maingun;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.math.*;

public class Cannon extends BaseMainGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    AnimationActor actorShot;
    private Array<TextureAtlas.AtlasRegion> arrRegionShot;
    private Runnable runAttack;
    
    static {
        Cannon.PlayerNum = 1;
        Cannon.strRoot = "maingun/xml/";
        Cannon.strPath = new String[] { "maingun_total_2" };
        Cannon.strEffect = "effect/pipe.pack";
        Cannon.strSound = "sound/weapon_cannon.ogg";
    }
    
    public Cannon() {
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.setGunVisible(false);
    }
    
    private void initRun() {
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                final Bullet2 bulletDamage = Pools.obtain(Bullet2.class);
                bulletDamage.Clear();
                Cannon.this.setBulletDamage(bulletDamage);
                bulletDamage.setRegion(Cannon.this.regionBullet);
                bulletDamage.setSize(Cannon.this.bulletWidth, Cannon.this.bulletHeight);
                bulletDamage.setOrigin(Cannon.this.bulletWidth / 2.0f, Cannon.this.bulletHeight / 2.0f);
                bulletDamage.setRadius(Cannon.this.attackRadius);
                bulletDamage.setYIncrease(Cannon.this.yIncrease);
                Cannon.this.position.set(Cannon.this.imgGunFront.getRight(), Cannon.this.imgGunFront.getY() + Cannon.this.imgGunFront.getHeight() / 2.0f - Cannon.this.bulletHeight / 2.0f);
                Cannon.this.groupGun.localToStageCoordinates(Cannon.this.position);
                bulletDamage.GetSpeed().set(MathUtils.cosDeg(Cannon.this.curAngle), MathUtils.sinDeg(Cannon.this.curAngle)).nor().scl(Cannon.this.bulletSpeed);
                bulletDamage.setPosition(Cannon.this.position.x - 20.0f, Cannon.this.position.y - 5.0f);
                bulletDamage.polygon.setPosition(bulletDamage.getX(), bulletDamage.getY());
                Global.groupBulletPlayer.addActor(bulletDamage);
                Cannon.this.actorShot.setStart();
                Cannon.this.flashPlayers[0].play();
                SoundHandle.playForCannon();
            }
        };
    }
    
    public static void loadElements() {
        for (int i = 0; i < Cannon.strPath.length; ++i) {
            Global.manager.load(Cannon.strRoot + Cannon.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Cannon.strEffect, TextureAtlas.class);
        Global.manager.load(Cannon.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Cannon.strPath.length; ++i) {
            Global.manager.unload(Cannon.strRoot + Cannon.strPath[i] + ".xml");
        }
        Global.manager.unload(Cannon.strEffect);
        Global.manager.unload(Cannon.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 20.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 20.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.05f), Actions.moveBy(n, n2, 0.05f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-2"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-2"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-2"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-2"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-2"));
        this.imgDrawf.setPosition(15.0f, 18.0f);
        this.imgPlatform.setPosition(-4.0f, 0.0f);
        this.imgGunShelf.setPosition(60.0f, 3.0f);
        this.imgGunFront.setPosition(50.0f, 4.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.imgGunBody.setVisible(false);
        this.imgGunFront.setVisible(false);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(24.0f, 12.0f);
        this.groupGun.setPosition(47.0f, 23.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-2");
        this.addActor(this.groupGun);
        this.addActor(this.imgDrawf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(58.0f, 127.0f);
        this.setIcon(2);
        (this.arrRegionShot = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)Global.manager.get(Cannon.strEffect, TextureAtlas.class).getRegions(), 9, 2);
        (this.actorShot = new AnimationActor(0.05f, this.arrRegionShot)).setScale(0.7f);
        this.actorShot.setPosition(this.imgGunFront.getRight() - this.actorShot.getScaleX() * 55.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorShot.getScaleY() * 98.0f - 5.0f);
        this.groupGun.addActor(this.actorShot);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.addActor(this.imgGunBody);
        MyMethods.initPoolNum(Bullet2.class, 3);
        MyMethods.initPoolNum(Effect.EffectExplosion.class, 2);
        this.initRun();
    }
    
    public void SetProperty() {
        this.totalDural = 100.0f;
        this.curDural = 100.0f;
        this.totalResume = 10.0f;
        this.attackInterval = 1.0f;
        this.damageGround = 30.0f;
        final float damageGround = this.damageGround;
        this.damageAir = damageGround;
        this.damageBuild = damageGround;
        this.attackRadius = 80.0f;
        this.bulletSpeed = 15.0f;
        this.yIncrease = 0.16f;
        this.bulletCount = 1.0f;
        this.bulletWidth = this.regionBullet.getRegionWidth();
        this.bulletHeight = this.regionBullet.getRegionHeight();
        this.setJsonValue();
        this.setEnhanceLevel();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX() - 5.0f, this.getY());
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        MyMethods.delayRun(this.groupGun, this.runAttack, 0.05f);
        this.AddGunRecoil();
        this.lastAttackTime = this.curTime;
        this.isInputValid = false;
        this.duralHandle();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet2.class);
        MyMethods.clearPool(Effect.EffectExplosion.class);
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Cannon.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Cannon.strRoot + Cannon.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(73.0f, 35.0f);
        this.endFrame = 9;
        this.flashPlayers[0].play();
        this.flashPlayers[0].pause();
    }
    
    @Override
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX(), actor.getY() - 5.0f);
    }
}
