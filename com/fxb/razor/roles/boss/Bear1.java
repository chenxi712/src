package com.fxb.razor.roles.boss;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.flash.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.objects.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;

public class Bear1 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int after1 = 109;
    private static final int after2 = 117;
    private static final int attack1 = 118;
    private static final int attack2 = 168;
    private static final int die1 = 169;
    private static final int die2 = 191;
    private static final int dizzy1 = 69;
    private static final int dizzy2 = 108;
    private static final int front1 = 61;
    private static final int front2 = 69;
    private static final int idle1 = 31;
    private static final int idle2 = 60;
    private static final int run1 = 0;
    private static final int run2 = 30;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    private float[] vertices1;
    private float[] vertices2;
    
    static {
        Bear1.strPath = new String[] { "bear_total_1" };
        Bear1.strAtlas = "boss/pack/bear.pack";
    }
    
    public Bear1() {
        this.dizzyCount = 0;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < Bear1.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + Bear1.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Bear1.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < Bear1.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + Bear1.strPath[i] + ".xml");
        }
        Global.manager.unload(Bear1.strAtlas);
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
        this.flashPlayers[this.curIndex].setFrameIndex(169);
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
    
    public float GetTraceX() {
        return 1.0f;
    }
    
    public float GetTraceY() {
        return 0.5f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.33333334f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + Bear1.strPath[i] + ".xml", FlashElements.class), Global.manager.get(Bear1.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
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
        this.flashPlayers[this.curIndex].pause();
        this.regionBullet = Assets.atlasBossBullet.findRegion("paodan1");
    }
    
    @Override
    public void SetProperty() {
        this.vertices1 = new float[] { 7.0f, 72.0f, 15.0f, 38.0f, 64.0f, 38.0f, 61.0f, 8.0f, 192.0f, 10.0f, 195.0f, 111.0f, 164.0f, 125.0f, 110.0f, 108.0f, 82.0f, 110.0f, 46.0f, 79.0f, 46.0f, 79.0f };
        MeshMethod.addVertices(this.vertices2 = new float[] { 4.0f, 29.0f, 30.0f, 9.0f, 204.0f, 6.0f, 189.0f, 54.0f, 197.0f, 80.0f, 179.0f, 106.0f, 155.0f, 114.0f, 114.0f, 110.0f, 85.0f, 98.0f, 63.0f, 98.0f, 37.0f, 75.0f, 40.0f, 62.0f, 29.0f, 61.0f }, 20.0f, 0.0f);
        (this.polygon = new Polygon(this.vertices1)).setPosition(this.getX(), this.getY());
        this.bulletSpeed = 15.0f;
        this.yIncrease = 0.25f;
        this.attackInterval = 0.1f;
        this.setBossValue();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 25.0f);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 80.0f, this.getY() - 7.0f);
        }
    }
    
    public void addBullet(float random) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval) {
            return;
        }
        final float n = this.getX() + MathUtils.random(15, 18) + 175.0f;
        final float n2 = this.getY() + this.getHeight() * 0.3f + 56.0f;
        final float x = this.player.getX();
        final float random2 = MathUtils.random(-0.1f, 0.1f);
        final float width = this.player.getWidth();
        final float y = this.player.getY();
        random = MathUtils.random(random, 0.2f + random);
        final float height = this.player.getHeight();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(MathUtils.random(0.9f, 1.1f) * this.yIncrease);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        bulletEnemy.GetSpeed().set(x + (0.5f + random2) * width - n, y + (0.7f + random) * height - n2).nor().scl(this.bulletSpeed);
        random = 175.0f + MathUtils.random(-2.0f, 2.0f);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(random), MathUtils.sinDeg(random)).nor().scl(this.bulletSpeed);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(n, n2);
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
    }
    
    public void checkState() {
        switch (this.bossState) {
            case Run: {
                this.translate(this.speed.x, this.speed.y);
                this.getCurFlash().setPosition(this.getX(), this.getY() - Interpolation.pow3.apply(13.0f, 8.0f, this.getCurFlash().GetPlayPercent()));
                if (this.getX() < 680.0f) {
                    this.flashPlayers[this.curIndex].play();
                }
                if (Global.isUseRGBA4444) {
                    if (this.getX() < 470.0f) {
                        this.switchIdle();
                        return;
                    }
                    break;
                }
                else {
                    if (this.flashPlayers[this.curIndex].getFrameIndex() >= 30) {
                        this.switchIdle();
                        return;
                    }
                    break;
                }
            }
            case Idle: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 60) {
                    this.setState();
                    return;
                }
                break;
            }
            case Attack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 168) {
                    this.setState();
                }
                if (this.getFrame() == 122 || this.getFrame() == 136) {
                    this.addBullet(0.5f);
                    return;
                }
                break;
            }
            case Dizzy_Front: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 69) {
                    this.bossState = Constant.BossState.Dizzy;
                    this.flashPlayers[this.curIndex].setFrameIndex(69);
                    this.polygon.setVertices(this.vertices2);
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 108) {
                    break;
                }
                if (this.dizzyCount < 1) {
                    ++this.dizzyCount;
                    this.flashPlayers[this.curIndex].setFrameIndex(69);
                    return;
                }
                this.bossState = Constant.BossState.Dizzy_After;
                this.flashPlayers[this.curIndex].setFrameIndex(109);
                this.dizzyCount = 0;
            }
            case Dizzy_After: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 117) {
                    this.switchIdle();
                    this.polygon.setVertices(this.vertices1);
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
            n = MathUtils.random(0, 7);
        }
        else {
            n = MathUtils.random(0, 6);
        }
        if (n <= 2) {
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
        this.flashPlayers[this.curIndex].setFrameIndex(118);
    }
    
    public void switchDizzy() {
        this.bossState = Constant.BossState.Dizzy_Front;
        this.flashPlayers[this.curIndex].setFrameIndex(61);
    }
    
    public void switchIdle() {
        this.bossState = Constant.BossState.Idle;
        this.flashPlayers[this.curIndex].setFrameIndex(31);
    }
}
