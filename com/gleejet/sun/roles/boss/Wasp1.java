package com.gleejet.sun.roles.boss;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.utils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class Wasp1 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int attack1 = 139;
    private static final int attack2 = 162;
    private static int curLevel = 0;
    private static final int die1 = 163;
    private static final int die2 = 181;
    private static final int dizzy1 = 40;
    private static final int dizzy2 = 138;
    private static final int idle1 = 0;
    private static final int idle2 = 39;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    private float[] verDizzy;
    private float[] verIdle;
    
    static {
        Wasp1.strPath = new String[] { "wasp_total_1" };
        Wasp1.strAtlas = "boss/pack/wasp.pack";
        Wasp1.curLevel = 1;
    }
    
    public Wasp1() {
        this.dizzyCount = 0;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < Wasp1.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + Wasp1.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Wasp1.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < Wasp1.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + Wasp1.strPath[i] + ".xml");
        }
        Global.manager.unload(Wasp1.strAtlas);
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
        this.flashPlayers[this.curIndex].setFrameIndex(163);
    }
    
    public float GetCollisionDamage() {
        return 0.0f;
    }
    
    @Override
    public float GetDrawHpPercent() {
        return 0.65f;
    }
    
    @Override
    public float GetDrawHpStartX() {
        return 20.0f;
    }
    
    @Override
    public float GetDrawHpY() {
        return this.getTop() + 35.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.41f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + Wasp1.strPath[i] + ".xml", FlashElements.class), Global.manager.get(Wasp1.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
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
        this.regionBullet = Assets.atlasBossBullet.findRegion("paodan3");
    }
    
    @Override
    public void SetProperty() {
        this.setBossValue();
        this.verIdle = new float[] { 6.0f, 85.0f, 13.0f, 65.0f, 21.0f, 62.0f, 25.0f, 80.0f, 41.0f, 81.0f, 61.0f, 73.0f, 78.0f, 76.0f, 69.0f, 51.0f, 47.0f, 37.0f, 45.0f, 34.0f, 30.0f, 33.0f, 30.0f, 12.0f, 45.0f, 11.0f, 49.0f, 8.0f, 73.0f, 4.0f, 93.0f, 11.0f, 105.0f, 32.0f, 96.0f, 77.0f, 74.0f, 102.0f, 41.0f, 102.0f, 28.0f, 108.0f, 16.0f, 101.0f };
        MeshMethod.addVertices(this.verDizzy = new float[] { 12.0f, 71.0f, 17.0f, 44.0f, 35.0f, 38.0f, 44.0f, 39.0f, 33.0f, 56.0f, 71.0f, 75.0f, 69.0f, 49.0f, 51.0f, 26.0f, 37.0f, 21.0f, 44.0f, 2.0f, 89.0f, 7.0f, 105.0f, 22.0f, 101.0f, 79.0f, 60.0f, 97.0f, 47.0f, 94.0f, 30.0f, 79.0f, 20.0f, 81.0f }, 14.0f, -52.0f);
        (this.polygon = new Polygon(this.verIdle)).setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 300.0f);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 18.0f, this.getY() + 21.0f);
        }
    }
    
    public void addBullet() {
        if (this.currentTime - this.lastAttackTime < this.attackInterval) {
            return;
        }
        final float n = this.getX() + 50.0f;
        final float n2 = this.getY() + 37.0f;
        final float x = this.player.getX();
        final float random = MathUtils.random(-0.1f, 0.1f);
        final float width = this.player.getWidth();
        final float y = this.player.getY();
        final float random2 = MathUtils.random(0.5f, 0.7f);
        final float height = this.player.getHeight();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(this.yIncrease);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        bulletEnemy.GetSpeed().set(x + (random + 0.5f) * width - n, y + (random2 + 0.7f) * height - n2).nor().scl(this.bulletSpeed);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(n, n2);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
    }
    
    public void checkState() {
        switch (this.bossState) {
            case Run: {
                this.translate(this.speed.x, this.speed.y);
                if (this.getX() < 550.0f) {
                    this.switchIdle();
                    return;
                }
                break;
            }
            case Idle: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 39) {
                    this.setState();
                    return;
                }
                break;
            }
            case Attack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 162) {
                    this.setState();
                }
                if (this.getFrame() == 147) {
                    this.addBullet();
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 138) {
                    break;
                }
                if (this.dizzyCount < 0) {
                    ++this.dizzyCount;
                    this.flashPlayers[this.curIndex].setFrameIndex(40);
                    return;
                }
                this.switchIdle();
                this.dizzyCount = 0;
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
        this.flashPlayers[this.curIndex].setFrameIndex(139);
    }
    
    public void switchDizzy() {
        this.bossState = Constant.BossState.Dizzy;
        this.flashPlayers[this.curIndex].setFrameIndex(40);
        this.polygon.setVertices(this.verDizzy);
    }
    
    public void switchIdle() {
        this.bossState = Constant.BossState.Idle;
        this.flashPlayers[this.curIndex].setFrameIndex(0);
        this.polygon.setVertices(this.verIdle);
    }
}
