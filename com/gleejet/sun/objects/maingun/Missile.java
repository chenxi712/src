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

public class Missile extends BaseMainGun
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
        Missile.PlayerNum = 1;
        Missile.strRoot = "maingun/xml/";
        Missile.strPath = new String[] { "maingun_total_a" };
        Missile.strEffect = "effect/missile.pack";
        Missile.strSound = "sound/weapon_missile.ogg";
    }
    
    public Missile() {
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
                final Bullet10 bulletDamage = Pools.obtain(Bullet10.class);
                bulletDamage.Clear();
                bulletDamage.setRegion(Missile.this.regionBullet);
                bulletDamage.setSize(Missile.this.bulletWidth, Missile.this.bulletHeight);
                bulletDamage.setOrigin(Missile.this.bulletWidth / 2.0f, Missile.this.bulletHeight / 2.0f);
                Missile.this.setBulletDamage(bulletDamage);
                bulletDamage.setYIncrease(Missile.this.yIncrease);
                bulletDamage.setRadius(Missile.this.attackRadius);
                Missile.this.position.set(Missile.this.imgGunFront.getRight() + bulletDamage.getOriginX(), Missile.this.imgGunFront.getY() + Missile.this.imgGunFront.getHeight() / 2.0f + 5.0f);
                Missile.this.groupGun.localToStageCoordinates(Missile.this.position);
                bulletDamage.GetSpeed().set(MathUtils.cosDeg(Missile.this.curAngle), MathUtils.sinDeg(Missile.this.curAngle)).nor().scl(Missile.this.bulletSpeed);
                bulletDamage.setPosition(Missile.this.position.x - bulletDamage.getOriginX(), Missile.this.position.y - bulletDamage.getOriginY());
                Global.groupBulletPlayer.addActor(bulletDamage);
                Missile.this.actorShot.setStart();
                bulletDamage.actorSpark.setStart();
                Missile.this.flashPlayers[0].play();
                SoundHandle.playForMissile();
            }
        };
    }
    
    public static void loadElements() {
        for (int i = 0; i < Missile.strPath.length; ++i) {
            Global.manager.load(Missile.strRoot + Missile.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Missile.strEffect, TextureAtlas.class);
        Global.manager.load(Missile.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Missile.strPath.length; ++i) {
            Global.manager.unload(Missile.strRoot + Missile.strPath[i] + ".xml");
        }
        Global.manager.unload(Missile.strEffect);
        Global.manager.unload(Missile.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 5.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 5.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-10"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-10"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-10"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-10"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-10"));
        this.imgDrawf.setPosition(21.0f, 20.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(71.0f, -1.0f);
        this.imgGunFront.setPosition(59.0f, 8.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(40.0f, 14.0f);
        this.groupGun.setPosition(53.0f, 24.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-10");
        this.addActor(this.imgDrawf);
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(54.0f, 125.0f);
        this.setIcon(10);
        final TextureAtlas textureAtlas = Global.manager.get(Missile.strEffect, TextureAtlas.class);
        (this.arrRegionShot = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 0, 10);
        (Bullet10.arrRegionBullet = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 10, 3);
        Pools.get(Bullet10.class).clear();
        (this.actorShot = new AnimationActor(0.033333335f, this.arrRegionShot)).setOrigin(50.0f, 50.0f);
        this.actorShot.setPosition(this.imgGunFront.getRight() - 50.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - 50.0f);
        this.groupGun.addActor(this.actorShot);
        MyMethods.initPoolNum(Bullet10.class, 3);
        MyMethods.initPoolNum(Effect.EffectExplosion.class, 0);
        this.initRun();
    }
    
    public void SetProperty() {
        this.bulletWidth = this.regionBullet.getRegionWidth();
        this.bulletHeight = this.regionBullet.getRegionHeight();
        this.setJsonValue();
        this.setEnhanceLevel();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX() + 2.0f, this.getY());
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (this.isCanAttack() && n - this.position.x >= 0.0f) {
            MyMethods.delayRun(this.groupGun, this.runAttack, 0.05f);
            this.AddGunRecoil();
            this.lastAttackTime = this.curTime;
            this.duralHandle();
        }
    }
    
    @Override
    public void checkState() {
        if (this.flashPlayers[this.curIndex].getFrameIndex() >= 14) {
            this.flashPlayers[this.curIndex].setFrameIndex(0);
            this.flashPlayers[this.curIndex].pause();
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet9.class);
        MyMethods.clearPool(Effect.EffectSmoke.class);
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Missile.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Missile.strRoot + Missile.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(85.0f, 40.0f);
        this.flashPlayers[0].play();
        this.flashPlayers[0].pause();
    }
    
    @Override
    public void myclear() {
        this.clearActions();
        this.lastAttackTime = 0.0f;
        this.curTime = 0.0f;
    }
    
    @Override
    public void setAngle(final float n, final float n2) {
        super.setAngle(n, n2);
    }
    
    @Override
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX() - 10.0f, actor.getY() - 5.0f);
    }
}
