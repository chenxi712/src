package com.gleejet.sun.objects.maingun;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.audio.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Leap extends BaseMainGun
{
    public static int MaxCollisionCount;
    private static int PlayerNum;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    private Runnable runAttack;
    
    static {
        Leap.PlayerNum = 1;
        Leap.strRoot = "maingun/xml/";
        Leap.strPath = new String[] { "maingun_total_d" };
        Leap.strSound = "sound/weapon_leap.ogg";
        Leap.MaxCollisionCount = 2;
    }
    
    public Leap() {
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
                final Bullet13 bulletDamage = new Bullet13();
                bulletDamage.Clear();
                bulletDamage.setRegion(Leap.this.regionBullet);
                bulletDamage.setSize(Leap.this.bulletWidth, Leap.this.bulletHeight);
                bulletDamage.setOrigin(Leap.this.bulletWidth / 2.0f, Leap.this.bulletHeight / 2.0f);
                Leap.this.setBulletDamage(bulletDamage);
                bulletDamage.setYIncrease(Leap.this.yIncrease);
                Leap.this.position.set(Leap.this.imgGunFront.getRight() + bulletDamage.getOriginX(), Leap.this.imgGunFront.getY() + Leap.this.imgGunFront.getHeight() / 2.0f + 5.0f);
                Leap.this.groupGun.localToStageCoordinates(Leap.this.position);
                final float n = Leap.this.curAngle + MathUtils.random(-3.0f, 3.0f);
                bulletDamage.GetSpeed().set(MathUtils.cosDeg(n), MathUtils.sinDeg(n)).nor().scl(Leap.this.bulletSpeed);
                bulletDamage.addAction(Actions.forever(Actions.rotateBy(-360.0f, 0.9f)));
                bulletDamage.setPosition(Leap.this.position.x - bulletDamage.getOriginX(), Leap.this.position.y - bulletDamage.getOriginY());
                Global.groupBulletPlayer.addActor(bulletDamage);
                Leap.this.flashPlayers[0].play();
                SoundHandle.playForLeap();
            }
        };
    }
    
    public static void loadElements() {
        for (int i = 0; i < Leap.strPath.length; ++i) {
            Global.manager.load(Leap.strRoot + Leap.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Leap.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Leap.strPath.length; ++i) {
            Global.manager.unload(Leap.strRoot + Leap.strPath[i] + ".xml");
        }
        Global.manager.unload(Leap.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 5.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 5.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-13"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-13"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-13"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-13"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-13"));
        this.imgDrawf.setPosition(4.0f, 28.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(51.0f, 0.0f);
        this.imgGunFront.setPosition(29.0f, -15.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(30.0f, 12.0f);
        this.groupGun.setPosition(49.0f, 22.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-13");
        this.addActor(this.imgDrawf);
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(58.0f, 123.0f);
        this.setIcon(13);
        MyMethods.initPoolNum(Bullet13.class, 5);
        MyMethods.initPoolNum(Effect.EffectSmoke.class, 5);
        this.initRun();
    }
    
    public void SetProperty() {
        this.bulletWidth = this.regionBullet.getRegionWidth();
        this.bulletHeight = this.regionBullet.getRegionHeight();
        this.setJsonValue();
        this.setEnhanceLevel();
        Leap.MaxCollisionCount = 2;
        if (PreferHandle.readWeaponEnhance("Leap") >= 2) {
            Leap.MaxCollisionCount = 3;
        }
        if (PreferHandle.readWeaponEnhance("Leap") >= 7) {
            Leap.MaxCollisionCount = 4;
        }
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX() + 5.0f, this.getY() - 2.0f);
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        MyMethods.delayRun(this.groupGun, this.runAttack, 0.05f);
        this.AddGunRecoil();
        this.lastAttackTime = this.curTime;
        this.duralHandle();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet13.class);
        MyMethods.clearPool(Effect.EffectSmoke.class);
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Leap.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Leap.strRoot + Leap.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(85.0f, 40.0f);
        this.endFrame = 5;
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
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX() - 6.0f, actor.getY() - 6.0f);
    }
}
