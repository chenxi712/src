package com.fxb.razor.objects.maingun;

import com.fxb.razor.objects.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.math.*;

public class Track extends BaseMainGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    AnimationActor actorShot;
    float lastSoundTime;
    private Runnable runAttack;
    private Runnable runShot;
    
    static {
        Track.PlayerNum = 1;
        Track.strRoot = "maingun/xml/";
        Track.strPath = new String[] { "maingun_total_b" };
        Track.strEffect = "effect/track.pack";
        Track.strSound = "sound/weapon_track.ogg";
    }
    
    public Track() {
        this.lastSoundTime = 0.0f;
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.myclear();
        this.setGunVisible(false);
    }
    
    private void initRun() {
        this.runShot = new Runnable() {
            @Override
            public void run() {
                Track.this.flashPlayers[0].play();
            }
        };
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                final Bullet11 bulletDamage = Pools.obtain(Bullet11.class);
                bulletDamage.Clear();
                bulletDamage.setRegion(Track.this.regionBullet);
                bulletDamage.setSize(Track.this.bulletWidth, Track.this.bulletHeight);
                bulletDamage.setOrigin(Track.this.bulletWidth / 2.0f, Track.this.bulletHeight / 2.0f);
                Track.this.setBulletDamage(bulletDamage);
                Track.this.position.set(Track.this.imgGunFront.getRight() + bulletDamage.getOriginX(), Track.this.imgGunFront.getY() + Track.this.imgGunFront.getHeight() / 2.0f);
                Track.this.groupGun.localToStageCoordinates(Track.this.position);
                final float n = Track.this.curAngle + MathUtils.random(-40, 40);
                bulletDamage.GetSpeed().set(MathUtils.cosDeg(n), MathUtils.sinDeg(n)).nor().scl(Track.this.bulletSpeed);
                bulletDamage.setSpeedValue(Track.this.bulletSpeed);
                bulletDamage.setPosition(Track.this.position.x - bulletDamage.getOriginX(), Track.this.position.y - bulletDamage.getOriginY());
                Global.groupBulletPlayer.addActor(bulletDamage);
                Track.this.actorShot.setStart();
                if (Track.this.curTime - Track.this.lastSoundTime > 0.4f) {
                    SoundHandle.playForTrack();
                    Track.this.lastSoundTime = Track.this.curTime;
                }
            }
        };
    }
    
    public static void loadElements() {
        for (int i = 0; i < Track.strPath.length; ++i) {
            Global.manager.load(Track.strRoot + Track.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Track.strEffect, TextureAtlas.class);
        Global.manager.load(Track.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Track.strPath.length; ++i) {
            Global.manager.unload(Track.strRoot + Track.strPath[i] + ".xml");
        }
        Global.manager.unload(Track.strEffect);
        Global.manager.unload(Track.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 5.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 5.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-11"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-11"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-11"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-11"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-11"));
        this.imgDrawf.setPosition(16.0f, 12.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(50.0f, 1.0f);
        this.imgGunFront.setPosition(33.0f, -4.5f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(22.0f, 10.0f);
        this.groupGun.setPosition(52.0f, 25.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-11");
        this.addActor(this.imgDrawf);
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(58.0f, 125.0f);
        this.setIcon(11);
        (this.actorShot = new AnimationActor(0.04f, Global.manager.get(Track.strEffect, TextureAtlas.class).getRegions())).setScale(0.6f);
        this.actorShot.setOrigin(76.0f, 70.0f);
        this.actorShot.setPosition(this.imgGunFront.getRight() - 76.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - 70.0f);
        this.groupGun.addActor(this.actorShot);
        MyMethods.initPoolNum(Bullet11.class, 10);
        MyMethods.initPoolNum(Effect.EffectSmoke.class, 10);
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
            this.groupGun.addAction(Actions.sequence(Actions.delay(0.05f), Actions.run(this.runShot), Actions.delay(0.1f), Actions.run(this.runAttack)));
            this.lastAttackTime = this.curTime;
            this.duralHandle();
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet11.class);
        MyMethods.clearPool(Effect.EffectSmoke.class);
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Track.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Track.strRoot + Track.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(70.0f, 34.0f);
        this.endFrame = 9;
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
        actor.setPosition(actor.getX() - 5.0f, actor.getY() - 8.0f);
    }
}
