package com.gleejet.sun.roles.boss;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class BDragon2 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int attack1 = 93;
    private static final int attack2 = 130;
    private static int curLevel = 0;
    private static final int die1 = 241;
    private static final int die2 = 260;
    private static final int dizzy1 = 13;
    private static final int dizzy2 = 92;
    private static final int idle1 = 0;
    private static final int idle2 = 12;
    private static final int skill1 = 131;
    private static final int skill2 = 179;
    private static final int skill3 = 180;
    private static final int skill4 = 240;
    private static String strAtlas;
    private static String strAtlasFlame;
    private static String strAtlasWind;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    float lastFlameTime;
    private float lastWindTime;
    private float[] verAttack;
    private float[] verDizzy;
    private float[] verIdle;
    private float[] verSkill1;
    private float[] verSkill2;
    
    static {
        BDragon2.strPath = new String[] { "bdragon_total_2" };
        BDragon2.strAtlas = "boss/pack/bdragon.pack";
        BDragon2.curLevel = 1;
        BDragon2.strAtlasWind = "boss/effect/bdragon_wind.pack";
        BDragon2.strAtlasFlame = "boss/effect/bdragon_flame.pack";
    }
    
    public BDragon2() {
        this.dizzyCount = 0;
        this.lastWindTime = 0.0f;
        this.lastFlameTime = 0.0f;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < BDragon2.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + BDragon2.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(BDragon2.strAtlas, TextureAtlas.class);
        Global.manager.load(BDragon2.strAtlasWind, TextureAtlas.class);
        Global.manager.load(BDragon2.strAtlasFlame, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < BDragon2.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + BDragon2.strPath[i] + ".xml");
        }
        Global.manager.unload(BDragon2.strAtlas);
        Global.manager.unload(BDragon2.strAtlasWind);
        Global.manager.unload(BDragon2.strAtlasFlame);
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.bulletBaseRotation = 0.0f;
    }
    
    @Override
    public void Die() {
        this.currentHp = 0.0f;
        this.bossState = Constant.BossState.Dead;
        this.flashPlayers[this.curIndex].setFrameIndex(241);
    }
    
    public float GetCollisionDamage() {
        return 0.0f;
    }
    
    @Override
    public float GetDrawHpPercent() {
        return 0.9f;
    }
    
    @Override
    public float GetDrawHpStartX() {
        return 139.0f;
    }
    
    @Override
    public float GetDrawHpY() {
        return this.getTop() + 95.0f + 70.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.7f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + BDragon2.strPath[i] + ".xml", FlashElements.class), Global.manager.get(BDragon2.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
            this.flashPlayers[i].SetOrigin(this.flashPlayers[i].getWidth() / 2.0f, this.flashPlayers[i].getHeight() / 2.0f);
        }
        this.flashPlayers[0].setAlphaTime(1.5f);
        this.setSize(230.0f, 180.0f);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.curIndex = 0;
        this.flashPlayers[this.curIndex].play();
        this.bossState = Constant.BossState.Run;
        this.flashPlayers[this.curIndex].setFrameIndex(0);
        this.regionBullet = Assets.atlasBossBullet.findRegion("paodan9");
    }
    
    @Override
    public void SetProperty() {
        this.setBossValue();
        this.verIdle = new float[] { 19.0f, 93.0f, 7.0f, 56.0f, 24.0f, 49.0f, 48.0f, 72.0f, 67.0f, 71.0f, 73.0f, 39.0f, 139.0f, 6.0f, 139.0f, 45.0f, 168.0f, 24.0f, 198.0f, 18.0f, 144.0f, 88.0f, 102.0f, 109.0f, 83.0f, 104.0f, 62.0f, 118.0f, 34.0f, 120.0f, 21.0f, 106.0f };
        this.verDizzy = new float[] { 11.0f, 96.0f, 19.0f, 85.0f, 25.0f, 47.0f, 48.0f, 49.0f, 55.0f, 68.0f, 63.0f, 71.0f, 63.0f, 53.0f, 75.0f, 64.0f, 82.0f, 43.0f, 93.0f, 50.0f, 101.0f, 25.0f, 121.0f, 43.0f, 134.0f, 20.0f, 159.0f, 4.0f, 134.0f, 109.0f, 98.0f, 131.0f, 61.0f, 127.0f, 41.0f, 130.0f, 27.0f, 117.0f, 16.0f, 117.0f };
        this.verSkill1 = new float[] { 6.0f, 125.0f, 25.0f, 93.0f, 58.0f, 112.0f, 47.0f, 74.0f, 71.0f, 91.0f, 54.0f, 39.0f, 84.0f, 49.0f, 81.0f, 8.0f, 103.0f, 34.0f, 101.0f, 63.0f, 140.0f, 54.0f, 164.0f, 36.0f, 205.0f, 32.0f, 118.0f, 118.0f, 78.0f, 137.0f, 71.0f, 149.0f, 52.0f, 149.0f, 42.0f, 139.0f };
        this.verAttack = new float[] { 14.0f, 106.0f, 13.0f, 88.0f, 26.0f, 81.0f, 55.0f, 104.0f, 67.0f, 102.0f, 64.0f, 78.0f, 105.0f, 20.0f, 116.0f, 36.0f, 130.0f, 16.0f, 159.0f, 4.0f, 131.0f, 113.0f, 73.0f, 145.0f, 45.0f, 151.0f, 30.0f, 139.0f };
        this.verSkill2 = new float[] { 11.0f, 178.0f, 22.0f, 151.0f, 57.0f, 157.0f, 67.0f, 143.0f, 36.0f, 123.0f, 41.0f, 84.0f, 60.0f, 85.0f, 48.0f, 55.0f, 79.0f, 68.0f, 80.0f, 82.0f, 101.0f, 77.0f, 105.0f, 41.0f, 124.0f, 15.0f, 143.0f, 6.0f, 128.0f, 125.0f, 98.0f, 159.0f, 75.0f, 201.0f, 56.0f, 201.0f };
        MeshMethod.addVertices(this.verDizzy, 38.0f, -153.0f);
        MeshMethod.addVertices(this.verAttack, 26.0f, -23.0f);
        MeshMethod.addVertices(this.verSkill2, 50.0f, -20.0f);
        (this.polygon = new Polygon(this.verIdle)).setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 125.0f);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 80.0f + 49.0f, this.getY() - 7.0f + 152.0f);
        }
    }
    
    public void addBullet(final float n, final float n2) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval) {
            return;
        }
        final float x = this.getX();
        final float y = this.getY();
        this.player.getX();
        MathUtils.random(-0.1f, 0.1f);
        this.player.getWidth();
        this.player.getY();
        MathUtils.random(0.5f, 0.7f);
        this.player.getHeight();
        final float bulletSpeed = this.bulletSpeed;
        final float random = MathUtils.random(0.85f, 1.15f);
        final float yIncrease = this.yIncrease;
        final float random2 = MathUtils.random(0.85f, 1.15f);
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(yIncrease * random2);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(160.0f), MathUtils.sinDeg(160.0f)).nor().scl(bulletSpeed * random);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(x + n, y + n2);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        Global.groupBulletEnemy.addActor(bulletEnemy);
    }
    
    public void addFlame() {
        if (this.currentTime - this.lastFlameTime < 0.1f) {
            return;
        }
        this.lastFlameTime = this.currentTime;
        final AnimationActor animationActor = new AnimationActor(0.05f, Global.manager.get(BDragon2.strAtlasFlame, TextureAtlas.class).getRegions());
        animationActor.setScale(1.0f);
        animationActor.setOrigin(470.0f, 0.0f);
        animationActor.setPosition(this.getX() - 342.0f, 100.0f - animationActor.getOriginY() - 42.0f);
        animationActor.setStart();
        animationActor.isAutoFree = true;
        animationActor.setName("BDragonFlame");
        Global.groupEffectSmoke.addActor(animationActor);
        MyMethods.delayRun(animationActor, new Runnable() {
            @Override
            public void run() {
                BDragon2.this.player.beAttacked(100.0f);
                BDragon2.this.player.beCollision(1, 0.1f);
            }
        }, 0.2f);
    }
    
    public void addWind() {
        if (this.currentTime - this.lastWindTime < 0.1f) {
            return;
        }
        this.lastWindTime = this.currentTime;
        final AnimationActor animationActor = new AnimationActor(0.1f, Global.manager.get(BDragon2.strAtlasWind, TextureAtlas.class).getRegions());
        animationActor.setScale(0.55f);
        animationActor.setOrigin(150.0f, 25.0f);
        animationActor.setPosition(this.getX(), 100.0f - animationActor.getOriginY());
        animationActor.setLoop(true);
        animationActor.setPlayCount(4);
        final MoveByAction moveBy = Actions.moveBy(-400.0f, 0.0f, 0.9f);
        final RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run() {
                BDragon2.this.player.beAttacked(100.0f);
                BDragon2.this.player.beCollision(1, 0.1f);
            }
        });
        animationActor.addAction(moveBy);
        this.addAction(Actions.sequence(Actions.delay(0.5f), run));
        animationActor.setStart();
        animationActor.isAutoFree = true;
        animationActor.setName("BDragonWind");
        Global.groupEffectSmoke.addActor(animationActor);
    }
    
    public void checkState() {
        switch (this.bossState) {
            case Run: {
                this.translate(this.speed.x, this.speed.y);
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 12) {
                    this.flashPlayers[this.curIndex].setFrameIndex(0);
                }
                if (this.getX() < 400.0f) {
                    this.bossState = Constant.BossState.Idle;
                    return;
                }
                break;
            }
            case Idle: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 12) {
                    this.setState();
                    return;
                }
                break;
            }
            case Attack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 130) {
                    this.setState();
                }
                if (this.getFrame() == 109) {
                    this.addBullet(212.0f, 275.0f);
                    this.lastAttackTime = this.currentTime;
                    return;
                }
                break;
            }
            case Skill1: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 179) {
                    this.setState();
                }
                if (this.getFrame() == 154 || this.getFrame() == 158 || this.getFrame() == 162 || this.getFrame() == 166) {
                    this.addFlame();
                    return;
                }
                break;
            }
            case Skill2: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 240) {
                    this.setState();
                }
                if (this.getFrame() == 198 || this.getFrame() == 209 || this.getFrame() == 221) {
                    this.addWind();
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 92) {
                    break;
                }
                if (this.dizzyCount < 0) {
                    ++this.dizzyCount;
                    this.flashPlayers[this.curIndex].setFrameIndex(13);
                    return;
                }
                this.dizzyCount = 0;
                this.switchIdle();
            }
            case Dead: {
                if (this.flashPlayers[this.curIndex].isEnd()) {
                    if (!this.flashPlayers[this.curIndex].isStop()) {
                        this.flashPlayers[this.curIndex].stop();
                    }
                    this.deadHandle();
                    return;
                }
                break;
            }
        }
    }
    
    public void setState() {
        int n;
        if (this.bossAttackTime > 0.0f) {
            n = MathUtils.random(0, 12);
        }
        else {
            n = MathUtils.random(0, 10);
        }
        if (n <= 2) {
            this.switchIdle();
            return;
        }
        if (n <= 6) {
            this.switchAttack();
            return;
        }
        if (n <= 8) {
            this.switchSkill1();
            return;
        }
        if (n <= 10) {
            this.switchSkill2();
            return;
        }
        this.switchDizzy();
    }
    
    public void switchAttack() {
        this.bossState = Constant.BossState.Attack;
        this.flashPlayers[this.curIndex].setFrameIndex(93);
        this.polygon.setVertices(this.verAttack);
    }
    
    public void switchDizzy() {
        this.bossState = Constant.BossState.Dizzy;
        this.flashPlayers[this.curIndex].setFrameIndex(13);
        this.polygon.setVertices(this.verDizzy);
    }
    
    public void switchIdle() {
        this.bossState = Constant.BossState.Idle;
        this.flashPlayers[this.curIndex].setFrameIndex(0);
        this.polygon.setVertices(this.verIdle);
    }
    
    public void switchSkill1() {
        this.bossState = Constant.BossState.Skill1;
        this.flashPlayers[this.curIndex].setFrameIndex(131);
        this.polygon.setVertices(this.verSkill1);
    }
    
    public void switchSkill2() {
        this.bossState = Constant.BossState.Skill2;
        this.flashPlayers[this.curIndex].setFrameIndex(179);
        this.polygon.setVertices(this.verSkill2);
    }
}
