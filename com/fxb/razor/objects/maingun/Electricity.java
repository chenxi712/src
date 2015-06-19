package com.fxb.razor.objects.maingun;

import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.roles.*;
import com.badlogic.gdx.audio.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.fxb.razor.flash.*;

public class Electricity extends BaseMainGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    AnimationActor actorParticle;
    AnimationActor actorShot;
    AnimationActor actorSky;
    float cancelAttackTime;
    float checkGapTime;
    MyImage imgClick;
    boolean isPrepareAddBullet;
    float lastCheckTime;
    Vector2 pCross;
    Vector2 posBulletEnd;
    Vector2 posBulletStart;
    Vector2 posGroundLeft;
    Vector2 posGroundRight;
    private Runnable runAttack;
    private Runnable runGather;
    
    static {
        Electricity.PlayerNum = 1;
        Electricity.strRoot = "maingun/xml/";
        Electricity.strPath = new String[] { "maingun_total_6" };
        Electricity.strEffect = "effect/elec.pack";
        Electricity.strSound = "sound/weapon_electric.ogg";
    }
    
    public Electricity() {
        this.posBulletStart = new Vector2();
        this.posBulletEnd = new Vector2();
        this.pCross = new Vector2();
        this.isPrepareAddBullet = false;
        this.cancelAttackTime = 0.0f;
        this.lastCheckTime = -5.0f;
        this.checkGapTime = 0.03f;
        this.posGroundLeft = new Vector2(100.0f, 100.0f);
        this.posGroundRight = new Vector2(800.0f, 100.0f);
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.attackInterval = 1.6f;
        this.setGunVisible(false);
    }
    
    private void initRun() {
        this.runGather = new Runnable() {
            @Override
            public void run() {
                Electricity.this.actorParticle.setVisible(true);
                Electricity.this.actorParticle.setStart();
                Electricity.this.actorShot.setStart();
                Electricity.this.flashPlayers[0].play();
                Global.imgAim.addAction(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 0.6f), Actions.scaleTo(1.0f, 1.0f, 0.1f)));
            }
        };
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                Electricity.this.isPrepareAddBullet = false;
                Electricity.this.actorParticle.setVisible(false);
                Electricity.this.actorSky.setStart();
                SoundHandle.playForElectric();
                Electricity.this.duralHandle();
            }
        };
    }
    
    private boolean isOverlap(final BaseEnemy baseEnemy) {
        return baseEnemy.polygon != null && MyMethods.isSegPolyOverlap(this.posBulletStart, this.posBulletEnd, baseEnemy.polygon, this.pCross);
    }
    
    public static void loadElements() {
        for (int i = 0; i < Electricity.strPath.length; ++i) {
            Global.manager.load(Electricity.strRoot + Electricity.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Electricity.strEffect, TextureAtlas.class);
        Global.manager.load(Electricity.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Electricity.strPath.length; ++i) {
            Global.manager.unload(Electricity.strRoot + Electricity.strPath[i] + ".xml");
        }
        Global.manager.unload(Electricity.strEffect);
        Global.manager.unload(Electricity.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 5.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 5.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-6"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-6"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-6"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-6"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-6"));
        this.imgDrawf.setPosition(14.0f, 14.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(55.0f, 0.0f);
        this.imgGunFront.setPosition(62.0f, 11.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(24.0f, 12.0f);
        this.groupGun.setPosition(48.0f, 17.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-1");
        this.addActor(this.imgDrawf);
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(68.0f, 130.0f);
        this.setIcon(6);
        final TextureAtlas textureAtlas = Global.manager.get(Electricity.strEffect, TextureAtlas.class);
        (this.actorParticle = new AnimationActor(0.016666668f, textureAtlas.findRegions("elec_particle"))).setScale(1.0f);
        this.actorParticle.setPlayCount(3);
        this.actorParticle.setLoop(true);
        this.actorParticle.setPosition(this.imgGunFront.getRight() - this.actorParticle.getScaleX() * 95.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorParticle.getScaleY() * 105.0f);
        (this.actorShot = new AnimationActor(0.022222223f, textureAtlas.findRegions("elec_shot"))).setScale(1.0f);
        this.actorShot.setPosition(this.imgGunFront.getRight() - this.actorShot.getScaleX() * 95.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorShot.getScaleY() * 100.0f);
        (this.actorSky = new AnimationActor(0.033333335f, textureAtlas.findRegions("elec_sky"))).setScale(1.0f);
        this.actorSky.setLoop(true);
        this.actorSky.setPlayCount(2);
        this.actorSky.setScaleY(0.8f);
        this.actorSky.setPosition(this.imgGunFront.getRight() - this.actorSky.getScaleX() * 72.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorSky.getScaleY() * 100.0f);
        this.groupGun.addActor(this.actorShot);
        this.groupGun.addActor(this.actorParticle);
        this.actorSky.setChangeWidth(true);
        this.groupGun.addActor(this.actorSky);
        (this.imgClick = new MyImage(textureAtlas.findRegion("click"))).setOrigin(this.imgClick.getWidth() / 2.0f, this.imgClick.getHeight() / 2.0f);
        this.imgClick.setScale(0.5f);
        this.imgClick.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.3f, 0.3f, 0.1f), Actions.scaleTo(0.5f, 0.5f, 0.1f))));
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
        if (this.actorSky.isPlaying()) {
            if (this.curTime - this.lastCheckTime >= this.checkGapTime && this.workState == WorkState.State_Work) {
                this.checkCollision();
            }
        }
        else {
            this.imgClick.setVisible(false);
        }
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX() + 7.0f, this.getY());
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        this.isPrepareAddBullet = true;
        this.groupGun.addAction(Actions.sequence(Actions.delay(0.05f), Actions.run(this.runGather), Actions.delay(0.6f), Actions.run(this.runAttack)));
        this.cancelAttackTime = this.lastAttackTime;
        this.lastAttackTime = this.curTime;
    }
    
    public void cancelBullet() {
        if (this.isPrepareAddBullet) {
            this.groupGun.clearActions();
            this.actorShot.setOver();
            this.actorParticle.setOver();
            this.lastAttackTime = this.cancelAttackTime;
            this.flashPlayers[0].pause();
            this.flashPlayers[0].setFrameIndex(0);
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
            this.actorSky.setWidth(MyMethods.getDis(this.posBulletStart, this.pCross));
            this.imgClick.setVisible(true);
        }
        else {
            this.actorSky.setWidth(600.0f);
            this.imgClick.setVisible(false);
        }
        this.lastCheckTime = this.curTime;
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Electricity.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Electricity.strRoot + Electricity.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetTimeScale(3.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(67.0f, 32.0f);
        this.endFrame = 79;
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
        actor.setPosition(actor.getX() - 5.0f, actor.getY() - 6.0f);
    }
}
