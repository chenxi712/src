package com.gleejet.sun.objects.maingun;

import com.badlogic.gdx.graphics.glutils.*;
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

public class Acid extends BaseMainGun
{
    private static int PlayerNum;
    private static Array<TextureAtlas.AtlasRegion> arrRegionBreak;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    AnimationActor actorShot;
    private Array<TextureAtlas.AtlasRegion> arrRegionBullet;
    float duralTime;
    float gapTime;
    float lastAutoTime;
    Vector2 posOrigin;
    ShapeRenderer rend;
    private Runnable runAttack;
    
    static {
        Acid.PlayerNum = 1;
        Acid.strRoot = "maingun/xml/";
        Acid.strPath = new String[] { "maingun_total_8" };
        Acid.strEffect = "effect/acid.pack";
        Acid.strSound = "sound/weapon_acid.ogg";
    }
    
    public Acid() {
        this.posOrigin = new Vector2();
        this.rend = new ShapeRenderer();
        this.gapTime = 0.1f;
        this.duralTime = 0.5f;
        this.lastAutoTime = -5.0f;
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.setGunVisible(false);
    }
    
    public static Array<TextureAtlas.AtlasRegion> getArrRegionBreak() {
        return Acid.arrRegionBreak;
    }
    
    private void initRun() {
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 8; ++i) {
                    final Bullet8 bullet8 = Pools.obtain(Bullet8.class);
                    bullet8.Clear();
                    Acid.this.setBulletDamage(bullet8);
                    final TextureRegion region = Acid.this.getRegion();
                    bullet8.setRegion(region);
                    float n;
                    if (i % 2 == 0) {
                        n = MathUtils.random(0.35f, 0.5f);
                    }
                    else {
                        n = MathUtils.random(0.5f, 0.65f);
                    }
                    bullet8.setSize(region.getRegionWidth() * n, region.getRegionHeight() * n);
                    bullet8.setOrigin(bullet8.getWidth() / 2.0f, bullet8.getHeight() / 2.0f);
                    final float random = MathUtils.random(0.8f, 1.3f);
                    bullet8.setRadius(Acid.this.attackRadius);
                    bullet8.setYIncrease(Acid.this.yIncrease * random);
                    Acid.this.position.set(Acid.this.imgGunFront.getRight(), Acid.this.imgGunFront.getY() + Acid.this.imgGunFront.getHeight() / 2.0f - Acid.this.bulletHeight / 2.0f);
                    Acid.this.groupGun.localToStageCoordinates(Acid.this.position);
                    final float n2 = Acid.this.curAngle + MathUtils.random(-5.0f, 5.0f);
                    bullet8.GetSpeed().set(MathUtils.cosDeg(n2), MathUtils.sinDeg(n2)).nor().scl(Acid.this.bulletSpeed * random);
                    Acid.this.posOrigin.set(Acid.this.imgGunFront.getRight(), Acid.this.imgGunFront.getY() + Acid.this.imgGunFront.getHeight() / 2.0f);
                    Acid.this.groupGun.localToStageCoordinates(Acid.this.posOrigin);
                    bullet8.setPosition(Acid.this.position.x, Acid.this.position.y - 10.0f);
                    Global.groupBulletPlayer.addActor(bullet8);
                    Global.bullet = bullet8;
                }
            }
        };
    }
    
    public static void loadElements() {
        for (int i = 0; i < Acid.strPath.length; ++i) {
            Global.manager.load(Acid.strRoot + Acid.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Acid.strEffect, TextureAtlas.class);
        Global.manager.load(Acid.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Acid.strPath.length; ++i) {
            Global.manager.unload(Acid.strRoot + Acid.strPath[i] + ".xml");
        }
        Global.manager.unload(Acid.strEffect);
        Global.manager.unload(Acid.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 5.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 5.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-8"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-8"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-8"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-8"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-8"));
        this.imgDrawf.setPosition(6.0f, 31.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(55.0f, 23.0f);
        this.imgGunFront.setPosition(56.0f, 4.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(35.0f, 12.0f);
        this.groupGun.setPosition(30.0f, 44.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-1");
        this.addActor(this.imgDrawf);
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(72.0f, 111.0f);
        this.setIcon(8);
        final TextureAtlas textureAtlas = Global.manager.get(Acid.strEffect, TextureAtlas.class);
        Acid.arrRegionBreak = textureAtlas.findRegions("acid_break");
        (this.arrRegionBullet = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 15, 5);
        MyMethods.initPoolNum(Bullet8.class, 40);
        MyMethods.initPoolNum(Effect.EffectAcid.class, 10);
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
        if (this.curTime - this.lastAttackTime < this.duralTime && this.curTime - this.lastAutoTime >= this.gapTime && this.workState == WorkState.State_Work) {
            this.addBullet();
        }
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() + 3.0f);
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
        SoundHandle.playForAcid();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.initPoolNum(Bullet8.class, 0);
        MyMethods.initPoolNum(Effect.EffectAcid.class, 0);
    }
    
    public TextureRegion getRegion() {
        return this.arrRegionBullet.get(MathUtils.random(this.arrRegionBullet.size - 1));
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Acid.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Acid.strRoot + Acid.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(65.0f, 54.0f);
        this.endFrame = 6;
        this.flashPlayers[0].play();
        this.flashPlayers[0].pause();
    }
    
    @Override
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX(), actor.getY() - 28.0f);
    }
}
