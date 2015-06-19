package com.fxb.razor.roles.boss;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.flash.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.objects.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;

public class Rinocer1 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int after1 = 152;
    private static final int after2 = 161;
    private static final int attack1 = 162;
    private static final int attack2 = 174;
    private static int curLevel = 0;
    private static final int die1 = 176;
    private static final int die2 = 194;
    private static final int dizzy1 = 112;
    private static final int dizzy2 = 151;
    private static final int front1 = 101;
    private static final int front2 = 111;
    private static final int idle1 = 61;
    private static final int idle2 = 100;
    private static final int run1 = 0;
    private static final int run2 = 60;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    float[] verDizzy;
    float[] verIdle;
    
    static {
        Rinocer1.strPath = new String[] { "rinocer_total_1" };
        Rinocer1.strAtlas = "boss/pack/rinocer.pack";
        Rinocer1.curLevel = 1;
    }
    
    public Rinocer1() {
        this.dizzyCount = 0;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < Rinocer1.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + Rinocer1.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Rinocer1.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < Rinocer1.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + Rinocer1.strPath[i] + ".xml");
        }
        Global.manager.unload(Rinocer1.strAtlas);
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
        this.flashPlayers[this.curIndex].setFrameIndex(176);
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
        return 85.0f;
    }
    
    @Override
    public float GetDrawHpY() {
        return this.getTop() - 10.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.7f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + Rinocer1.strPath[i] + ".xml", FlashElements.class), Global.manager.get(Rinocer1.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
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
        this.verIdle = new float[] { 5.0f, 97.0f, 10.0f, 74.0f, 42.0f, 38.0f, 72.0f, 38.0f, 77.0f, 61.0f, 87.0f, 60.0f, 100.0f, 35.0f, 99.0f, 9.0f, 201.0f, 5.0f, 205.0f, 78.0f, 163.0f, 114.0f, 115.0f, 134.0f, 104.0f, 134.0f, 88.0f, 108.0f, 61.0f, 101.0f, 50.0f, 95.0f, 40.0f, 98.0f, 44.0f, 85.0f, 38.0f, 80.0f };
        MeshMethod.addVertices(this.verDizzy = new float[] { 4.0f, 83.0f, 15.0f, 59.0f, 51.0f, 32.0f, 62.0f, 30.0f, 64.0f, 36.0f, 86.0f, 39.0f, 80.0f, 11.0f, 177.0f, 7.0f, 197.0f, 46.0f, 167.0f, 98.0f, 119.0f, 137.0f, 74.0f, 104.0f, 56.0f, 98.0f, 48.0f, 92.0f, 38.0f, 91.0f, 44.0f, 79.0f, 40.0f, 73.0f }, 22.0f, 0.0f);
        (this.polygon = new Polygon(this.verIdle)).setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 25.0f);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 80.0f - 4.0f, this.getY() - 7.0f - 5.0f);
        }
        this.checkBossCall();
    }
    
    public void addBullet(final int n) {
        if (this.currentTime - this.lastAttackTime < 0.06f) {
            return;
        }
        this.lastAttackTime = this.currentTime;
        float n2;
        if (n == 1) {
            n2 = 192.0f;
        }
        else {
            n2 = 198.0f;
        }
        float n3;
        if (n == 1) {
            n3 = 115.0f;
        }
        else {
            n3 = 119.0f;
        }
        final float x = this.getX();
        final float dx = this.dx;
        final float y = this.getY();
        final float dy = this.dy;
        final float bulletSpeed = this.bulletSpeed;
        final float random = MathUtils.random(0.9f, 1.0f);
        final float yIncrease = this.yIncrease;
        final float random2 = MathUtils.random(0.9f, 1.0f);
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(yIncrease * random2);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(160.0f), MathUtils.sinDeg(160.0f)).scl(bulletSpeed * random);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(x + n2 + dx, y + n3 + dy);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        Global.groupBulletEnemy.addActor(bulletEnemy);
    }
    
    public void checkState() {
        switch (this.bossState) {
            case Run: {
                this.translate(this.speed.x, this.speed.y);
                this.getCurFlash().setPosition(this.getX(), this.getY() - Interpolation.pow3.apply(13.0f, 8.0f, this.getCurFlash().GetPlayPercent()));
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 60) {
                    this.flashPlayers[this.curIndex].setFrameIndex(0);
                }
                if (this.getX() < 480.0f) {
                    this.switchIdle();
                    return;
                }
                break;
            }
            case Idle: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 100) {
                    this.setState();
                    return;
                }
                break;
            }
            case Attack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 174) {
                    this.setState();
                }
                if (this.getFrame() == 171) {
                    this.addBullet(1);
                }
                if (this.getFrame() == 173) {
                    this.addBullet(2);
                    return;
                }
                break;
            }
            case Dizzy_Front: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 111) {
                    this.bossState = Constant.BossState.Dizzy;
                    this.flashPlayers[this.curIndex].setFrameIndex(112);
                    this.polygon.setVertices(this.verDizzy);
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 151) {
                    break;
                }
                if (this.dizzyCount < 1) {
                    ++this.dizzyCount;
                    this.flashPlayers[this.curIndex].setFrameIndex(112);
                    return;
                }
                this.bossState = Constant.BossState.Dizzy_After;
                this.flashPlayers[this.curIndex].setFrameIndex(152);
                this.dizzyCount = 0;
            }
            case Dizzy_After: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 161) {
                    this.switchIdle();
                    this.polygon.setVertices(this.verIdle);
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
        this.flashPlayers[this.curIndex].setFrameIndex(162);
    }
    
    public void switchDizzy() {
        this.bossState = Constant.BossState.Dizzy_Front;
        this.flashPlayers[this.curIndex].setFrameIndex(101);
    }
    
    public void switchIdle() {
        this.bossState = Constant.BossState.Idle;
        this.flashPlayers[this.curIndex].setFrameIndex(61);
    }
}
