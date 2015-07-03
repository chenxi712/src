package com.gleejet.sun.objects.maingun;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.utils.ui.*;

public class Laser extends BaseMainGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound1;
    private static String strSound2;
    AnimationActor actorShot;
    AnimationActor actorSky;
    private Array<TextureAtlas.AtlasRegion> arrRegionShot;
    private Array<TextureAtlas.AtlasRegion> arrRegionSky;
    float cancelAttackTime;
    float checkGapTime;
    MyImage imgClick;
    boolean isPrepareAddBullet;
    boolean isStopSound;
    float lastCheckTime;
    Vector2 pCross;
    Vector2 posBulletEnd;
    Vector2 posBulletStart;
    Vector2 posGroundLeft;
    Vector2 posGroundRight;
    private Runnable runAttack;
    private Runnable runGather;
    private Runnable runSound2;
    
    static {
        Laser.PlayerNum = 1;
        Laser.strRoot = "maingun/xml/";
        Laser.strPath = new String[] { "maingun_total_5" };
        Laser.strEffect = "effect/laser.pack";
        Laser.strSound1 = "sound/weapon_gather.ogg";
        Laser.strSound2 = "sound/weapon_laser.ogg";
    }
    
    public Laser() {
        this.posBulletStart = new Vector2();
        this.posBulletEnd = new Vector2();
        this.pCross = new Vector2();
        this.isPrepareAddBullet = false;
        this.cancelAttackTime = 0.0f;
        this.lastCheckTime = -5.0f;
        this.checkGapTime = 0.05f;
        this.posGroundLeft = new Vector2(100.0f, 100.0f);
        this.posGroundRight = new Vector2(800.0f, 100.0f);
        this.isStopSound = false;
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.setGunVisible(false);
    }
    
    private void initRun() {
        this.runGather = new Runnable() {
            @Override
            public void run() {
                Laser.this.isPrepareAddBullet = false;
                Laser.this.actorShot.setStart();
                Laser.this.flashPlayers[0].play();
                SoundHandle.playForGather(1.0f);
                Global.imgAim.addAction(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 0.8f), Actions.scaleTo(1.0f, 1.0f, 0.1f)));
            }
        };
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                Laser.this.actorSky.setStart();
                SoundHandle.playForLaser();
                Laser.this.isStopSound = false;
                Laser.this.duralHandle();
            }
        };
        this.runSound2 = new Runnable() {
            @Override
            public void run() {
                SoundHandle.playForLaser();
            }
        };
    }
    
    private boolean isOverlap(final BaseEnemy baseEnemy) {
        return baseEnemy.polygon != null && MyMethods.isSegPolyOverlap(this.posBulletStart, this.posBulletEnd, baseEnemy.polygon, this.pCross);
    }
    
    public static void loadElements() {
        for (int i = 0; i < Laser.strPath.length; ++i) {
            Global.manager.load(Laser.strRoot + Laser.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Laser.strEffect, TextureAtlas.class);
        Global.manager.load(Laser.strSound1, Sound.class);
        Global.manager.load(Laser.strSound2, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Laser.strPath.length; ++i) {
            Global.manager.unload(Laser.strRoot + Laser.strPath[i] + ".xml");
        }
        Global.manager.unload(Laser.strEffect);
        Global.manager.unload(Laser.strSound1);
        Global.manager.unload(Laser.strSound2);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 20.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 20.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.delay(0.15f), Actions.moveBy(-n, -n2, 0.75f), Actions.moveBy(n, n2, 0.05f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-5"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-5"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-5"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-5"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-5"));
        this.imgDrawf.setPosition(13.0f, 23.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(56.0f, 24.0f);
        this.imgGunFront.setPosition(42.0f, 0.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(24.0f, 12.0f);
        this.groupGun.setPosition(45.0f, 34.0f);
        this.addActor(this.imgDrawf);
        this.addActor(this.groupGun);
        this.setSize(120.0f, 70.0f);
        this.setPosition(65.0f, 120.0f);
        this.setIcon(5);
        final TextureAtlas textureAtlas = Global.manager.get(Laser.strEffect, TextureAtlas.class);
        (this.arrRegionShot = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 1, 26);
        (this.actorShot = new AnimationActor(0.8f / this.arrRegionShot.size, this.arrRegionShot)).setScale(1.0f);
        this.actorShot.setPosition(this.imgGunFront.getRight() - this.actorShot.getScaleX() * 40.0f - 10.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorShot.getScaleY() * 50.0f);
        (this.arrRegionSky = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 27, 4);
        this.arrRegionSky.add(textureAtlas.getRegions().get(28));
        this.arrRegionSky.add(textureAtlas.getRegions().get(27));
        (this.actorSky = new AnimationActor(0.055555556f, this.arrRegionSky)).setPosition(-38.0f, -53.0f);
        this.actorSky.setLoop(true);
        this.actorSky.setPlayCount(8);
        this.actorSky.setScaleY(0.7f);
        this.actorSky.addAction(Actions.alpha(0.5f));
        this.actorSky.setPosition(this.imgGunFront.getRight() - this.actorSky.getScaleX() * 38.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorSky.getScaleY() * 53.0f);
        this.actorSky.setChangeWidth(true);
        this.groupGun.addActor(this.actorShot);
        this.groupGun.addActor(this.actorSky);
        (this.imgClick = new MyImage(textureAtlas.findRegion("click"))).setOrigin(this.imgClick.getWidth() / 2.0f, this.imgClick.getHeight() / 2.0f);
        this.imgClick.setScale(0.7f);
        this.imgClick.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.4f, 0.4f, 0.1f), Actions.scaleTo(0.6f, 0.6f, 0.1f))));
        Global.groupEffectPlayer.addActor(this.imgClick);
        this.initRun();
    }
    
    public void SetProperty() {
        this.setJsonValue();
        this.setEnhanceLevel();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.actorShot.setPosition(this.imgGunFront.getRight() - 40.0f * this.actorShot.getScaleX() - 10.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - 50.0f * this.actorShot.getScaleY());
        if (this.actorSky.isPlaying() && this.workState == WorkState.State_Work) {
            if (this.curTime - this.lastCheckTime >= this.checkGapTime) {
                this.checkCollision();
            }
        }
        else {
            this.imgClick.setVisible(false);
            if (!this.isStopSound) {
                this.isStopSound = true;
            }
        }
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        this.isPrepareAddBullet = true;
        MyMethods.delayRun(this.groupGun, this.runSound2, 2.0f);
        this.groupGun.addAction(Actions.sequence(Actions.delay(0.03f), Actions.run(this.runGather), Actions.delay(0.8f), Actions.run(this.runAttack)));
        this.AddGunRecoil();
        this.cancelAttackTime = this.lastAttackTime;
        this.lastAttackTime = this.curTime;
    }
    
    public void cancelBullet() {
        if (this.isPrepareAddBullet) {
            this.groupGun.clearActions();
            this.actorShot.setOver();
            this.lastAttackTime = this.cancelAttackTime;
            this.flashPlayers[0].pause();
            this.flashPlayers[0].setFrameIndex(0);
            SoundHandle.cancelForGather();
        }
    }
    
    public void checkCollision() {
        Global.pStart = this.posBulletStart;
        Global.pEnd = this.posBulletEnd;
        this.posBulletStart.set(this.imgGunFront.getRight(), this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f);
        this.groupGun.localToStageCoordinates(this.posBulletStart);
        this.posBulletEnd.set(this.posBulletStart.x + MathUtils.cosDeg(this.curAngle) * 600.0f, this.posBulletStart.y + MathUtils.sinDeg(this.curAngle) * 600.0f);
        final boolean b = false;
        final boolean b2 = false;
        int n = 0;
        boolean b3;
        while (true) {
            b3 = b;
            if (n >= Global.arrEnemyCollision.size) {
                break;
            }
            final BaseEnemy baseEnemy = Global.arrEnemyCollision.get(n);
            if (baseEnemy.getCurrentHp() > 0.0f && this.isOverlap(baseEnemy)) {
                baseEnemy.BeAttacked(this.damageGround);
                b3 = true;
                break;
            }
            ++n;
        }
        boolean b4 = b2;
        if (!b3) {
            b4 = b2;
            if (Intersector.intersectSegments(this.posBulletStart, this.posBulletEnd, this.posGroundLeft, this.posGroundRight, this.pCross)) {
                b4 = true;
            }
        }
        if (b3 || b4) {
            Global.pCross = this.pCross;
            this.imgClick.setPosition(this.pCross.x - this.imgClick.getWidth() / 2.0f, this.pCross.y - this.imgClick.getHeight() / 2.0f);
            this.actorSky.setWidth(MyMethods.getDis(this.posBulletStart, this.pCross) + 15.0f);
            this.imgClick.setVisible(true);
        }
        else {
            this.actorSky.setWidth(600.0f);
            this.imgClick.setVisible(false);
        }
        this.lastCheckTime = this.curTime;
    }
    
    @Override
    public void checkState() {
        if (this.flashPlayers[this.curIndex].getFrameIndex() >= this.endFrame) {
            this.flashPlayers[this.curIndex].setFrameIndex(0);
            this.flashPlayers[this.curIndex].pause();
        }
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Laser.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Laser.strRoot + Laser.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetTimeScale(2.5f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(67.0f, 43.0f);
        this.endFrame = 51;
        this.flashPlayers[0].play();
        this.flashPlayers[0].pause();
    }
    
    @Override
    public void setAngle(final float n, final float n2) {
        super.setAngle(n, n2);
        this.posBulletStart.set(this.imgGunFront.getRight(), this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f);
        this.groupGun.localToStageCoordinates(this.posBulletStart);
        this.posBulletEnd.set(this.posBulletStart.x + MathUtils.cosDeg(this.curAngle) * 500.0f, this.posBulletStart.y + MathUtils.sinDeg(this.curAngle) * 500.0f);
    }
    
    @Override
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX(), actor.getY() - 10.0f);
    }
}
