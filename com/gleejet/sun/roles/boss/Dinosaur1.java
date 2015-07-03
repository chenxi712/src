package com.gleejet.sun.roles.boss;

import com.badlogic.gdx.graphics.g2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.utils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;

public class Dinosaur1 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int after1 = 180;
    private static final int after2 = 189;
    private static final int attack1 = 102;
    private static final int attack2 = 131;
    private static int curLevel = 0;
    private static final int die1 = 190;
    private static final int die2 = 199;
    private static final int dizzy1 = 140;
    private static final int dizzy2 = 179;
    private static final int front1 = 132;
    private static final int front2 = 139;
    private static final int idle1 = 62;
    private static final int idle2 = 101;
    private static final int run1 = 0;
    private static final int run2 = 61;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    private float[] verDizzy;
    private float[] verIdle;
    
    static {
        Dinosaur1.strPath = new String[] { "dinosaur_total_1" };
        Dinosaur1.strAtlas = "boss/pack/dinosaur.pack";
        Dinosaur1.curLevel = 1;
    }
    
    public Dinosaur1() {
        this.dizzyCount = 0;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < Dinosaur1.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + Dinosaur1.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Dinosaur1.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < Dinosaur1.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + Dinosaur1.strPath[i] + ".xml");
        }
        Global.manager.unload(Dinosaur1.strAtlas);
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
        this.flashPlayers[this.curIndex].setFrameIndex(190);
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
        return 65.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.33333334f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + Dinosaur1.strPath[i] + ".xml", FlashElements.class), Global.manager.get(Dinosaur1.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
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
        this.regionBullet = Assets.atlasBossBullet.findRegion("paodan5");
    }
    
    @Override
    public void SetProperty() {
        this.setBossValue();
        this.verIdle = new float[] { 7.0f, 87.0f, 12.0f, 73.0f, 16.0f, 31.0f, 39.0f, 18.0f, 57.0f, 35.0f, 90.0f, 20.0f, 88.0f, 10.0f, 206.0f, 7.0f, 222.0f, 78.0f, 168.0f, 131.0f, 127.0f, 139.0f, 96.0f, 127.0f, 55.0f, 78.0f, 30.0f, 60.0f, 18.0f, 72.0f, 23.0f, 92.0f };
        MeshMethod.addVertices(this.verDizzy = new float[] { 4.0f, 61.0f, 16.0f, 46.0f, 34.0f, 13.0f, 204.0f, 11.0f, 233.0f, 52.0f, 182.0f, 113.0f, 154.0f, 132.0f, 115.0f, 134.0f, 90.0f, 123.0f, 51.0f, 80.0f, 36.0f, 47.0f, 19.0f, 52.0f, 15.0f, 71.0f }, 23.0f, -5.0f);
        (this.polygon = new Polygon(this.verIdle)).setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 25.0f);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 80.0f - 54.0f, this.getY() - 9.0f);
        }
    }
    
    public void addBullet(final float n, final float n2) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval) {
            return;
        }
        final float x = this.getX();
        final float y = this.getY();
        final float bulletSpeed = this.bulletSpeed;
        MathUtils.random(0.9f, 1.1f);
        final float yIncrease = this.yIncrease;
        final float random = MathUtils.random(0.9f, 0.95f);
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(yIncrease * random);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        final float n3 = 175.0f + MathUtils.random(-2.0f, 2.0f);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(n3), MathUtils.sinDeg(n3)).nor().scl(this.bulletSpeed);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(x + n + 40.0f, y + n2);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        Global.groupBulletEnemy.addActor(bulletEnemy);
    }
    
    public void checkState() {
        switch (this.bossState) {
            case Run: {
                this.translate(this.speed.x, this.speed.y);
                this.getCurFlash().setPosition(this.getX(), this.getY() - Interpolation.pow3.apply(13.0f, 8.0f, this.getCurFlash().GetPlayPercent()));
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 61) {
                    this.flashPlayers[this.curIndex].setFrameIndex(0);
                }
                if (this.getX() < 480.0f) {
                    this.switchIdle();
                    return;
                }
                break;
            }
            case Idle: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 101) {
                    this.setState();
                    return;
                }
                break;
            }
            case Attack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 131) {
                    this.setState();
                }
                if (this.getFrame() == 112) {
                    this.addBullet(123.0f, 130.0f);
                    this.lastAttackTime = this.currentTime;
                    return;
                }
                break;
            }
            case Dizzy_Front: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 139) {
                    this.bossState = Constant.BossState.Dizzy;
                    this.flashPlayers[this.curIndex].setFrameIndex(140);
                    this.polygon.setVertices(this.verDizzy);
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 179) {
                    break;
                }
                if (this.dizzyCount < 1) {
                    ++this.dizzyCount;
                    this.flashPlayers[this.curIndex].setFrameIndex(140);
                    return;
                }
                this.bossState = Constant.BossState.Dizzy_After;
                this.flashPlayers[this.curIndex].setFrameIndex(180);
                this.dizzyCount = 0;
            }
            case Dizzy_After: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 189) {
                    this.switchIdle();
                    return;
                }
                break;
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
            n = MathUtils.random(0, 6);
        }
        else {
            n = MathUtils.random(0, 5);
        }
        if (n <= 1) {
            this.switchIdle();
            return;
        }
        if (n <= 5) {
            this.switchAttack();
            return;
        }
        this.switchDizzy();
    }
    
    public void switchAttack() {
        this.bossState = Constant.BossState.Attack;
        this.flashPlayers[this.curIndex].setFrameIndex(102);
    }
    
    public void switchDizzy() {
        this.bossState = Constant.BossState.Dizzy_Front;
        this.flashPlayers[this.curIndex].setFrameIndex(132);
        this.polygon.setVertices(this.verDizzy);
    }
    
    public void switchIdle() {
        this.bossState = Constant.BossState.Idle;
        this.flashPlayers[this.curIndex].setFrameIndex(62);
        this.polygon.setVertices(this.verIdle);
    }
}
