package com.gleejet.sun.roles.boss;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.utils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class BDragon1 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int attack1 = 85;
    private static final int attack2 = 122;
    private static int curLevel = 0;
    private static final int die1 = 123;
    private static final int die2 = 141;
    private static final int dizzy1 = 13;
    private static final int dizzy2 = 84;
    private static final int idle1 = 0;
    private static final int idle2 = 12;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    private float[] verAttack;
    private float[] verDizzy;
    private float[] verIdle;
    
    static {
        BDragon1.strPath = new String[] { "bdragon_total_1" };
        BDragon1.strAtlas = "boss/pack/bdragon.pack";
        BDragon1.curLevel = 1;
    }
    
    public BDragon1() {
        this.dizzyCount = 0;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < BDragon1.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + BDragon1.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(BDragon1.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < BDragon1.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + BDragon1.strPath[i] + ".xml");
        }
        Global.manager.unload(BDragon1.strAtlas);
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
        this.flashPlayers[this.curIndex].setFrameIndex(123);
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
        return 15.0f;
    }
    
    @Override
    public float GetDrawHpY() {
        return this.getTop() + 95.0f + 70.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.6f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + BDragon1.strPath[i] + ".xml", FlashElements.class), Global.manager.get(BDragon1.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
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
        this.verIdle = new float[] { 9.0f, 72.0f, 11.0f, 50.0f, 29.0f, 42.0f, 38.0f, 44.0f, 38.0f, 62.0f, 70.0f, 68.0f, 77.0f, 36.0f, 134.0f, 1.0f, 165.0f, 77.0f, 122.0f, 99.0f, 86.0f, 101.0f, 60.0f, 115.0f, 46.0f, 98.0f, 29.0f, 94.0f, 15.0f, 81.0f, 20.0f, 75.0f };
        this.verDizzy = new float[] { 8.0f, 98.0f, 4.0f, 77.0f, 10.0f, 73.0f, 10.0f, 67.0f, 3.0f, 65.0f, 11.0f, 58.0f, 18.0f, 49.0f, 33.0f, 51.0f, 38.0f, 58.0f, 33.0f, 77.0f, 49.0f, 78.0f, 51.0f, 59.0f, 92.0f, 29.0f, 102.0f, 71.0f, 119.0f, 29.0f, 148.0f, 7.0f, 119.0f, 116.0f, 67.0f, 138.0f, 39.0f, 114.0f, 32.0f, 139.0f, 28.0f, 118.0f, 22.0f, 131.0f, 18.0f, 104.0f };
        this.verAttack = new float[] { 9.0f, 111.0f, 9.0f, 89.0f, 26.0f, 84.0f, 35.0f, 101.0f, 59.0f, 95.0f, 88.0f, 40.0f, 95.0f, 46.0f, 101.0f, 23.0f, 116.0f, 38.0f, 128.0f, 20.0f, 163.0f, 7.0f, 130.0f, 109.0f, 91.0f, 136.0f, 74.0f, 147.0f, 64.0f, 137.0f, 65.0f, 153.0f, 47.0f, 133.0f, 32.0f, 135.0f, 17.0f, 122.0f, 18.0f, 113.0f };
        MeshMethod.addVertices(this.verDizzy, 59.0f, -166.0f);
        MeshMethod.addVertices(this.verAttack, 51.0f, -14.0f);
        (this.polygon = new Polygon(this.verIdle)).setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 70.0f);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 80.0f - 75.0f, this.getY() - 7.0f + 191.0f);
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
        final float random = MathUtils.random(0.9f, 1.1f);
        final float bulletSpeed = this.bulletSpeed;
        final float yIncrease = this.yIncrease;
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(yIncrease * random);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(160.0f), MathUtils.sinDeg(160.0f)).nor().scl(bulletSpeed * random);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(x + n, y + n2);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        Global.groupBulletEnemy.addActor(bulletEnemy);
    }
    
    public void checkState() {
        switch (this.bossState) {
            case Run: {
                this.translate(this.speed.x, this.speed.y);
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 12) {
                    this.flashPlayers[this.curIndex].setFrameIndex(0);
                }
                if (this.getX() < 480.0f) {
                    this.bossState = Constant.BossState.Idle;
                    return;
                }
                break;
            }
            case Idle: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 12) {
                    this.setState();
                }
            }
            case Attack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 122) {
                    this.setState();
                }
                if (this.getFrame() == 99) {
                    this.addBullet(127.0f, 285.0f);
                    this.lastAttackTime = this.currentTime;
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 84) {
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
                        System.out.println("end");
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
            n = MathUtils.random(0, 7);
        }
        else {
            n = MathUtils.random(0, 6);
        }
        if (n <= 1) {
            this.switchIdle();
            return;
        }
        if (n <= 6) {
            this.switchAttack();
            return;
        }
        this.switchDizzy();
    }
    
    public void switchAttack() {
        this.bossState = Constant.BossState.Attack;
        this.flashPlayers[this.curIndex].setFrameIndex(85);
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
}
