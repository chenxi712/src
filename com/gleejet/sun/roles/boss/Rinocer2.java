package com.gleejet.sun.roles.boss;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;

public class Rinocer2 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int after1 = 151;
    private static final int after2 = 159;
    private static final int attack1 = 160;
    private static final int attack2 = 192;
    private static int curLevel = 0;
    private static final int die1 = 235;
    private static final int die2 = 253;
    private static final int dizzy1 = 111;
    private static final int dizzy2 = 150;
    private static final int front1 = 101;
    private static final int front2 = 110;
    private static final int idle1 = 61;
    private static final int idle2 = 100;
    private static final int run1 = 0;
    private static final int run2 = 61;
    private static final int skill1 = 193;
    private static final int skill2 = 234;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    private float[] verDizzy;
    private float[] verIdle;
    private float[] verSkill1;
    
    static {
        Rinocer2.strPath = new String[] { "rinocer_total_2" };
        Rinocer2.strAtlas = "boss/pack/rinocer.pack";
        Rinocer2.curLevel = 1;
    }
    
    public Rinocer2() {
        this.dizzyCount = 0;
    }
    
    private float getStartX(int n) {
        final int n2 = 0;
        switch (n) {
            default: {
                n = n2;
                break;
            }
            case 1: {
                n = 182;
                break;
            }
            case 2: {
                n = 155;
                break;
            }
            case 3: {
                n = 157;
                break;
            }
            case 4: {
                n = 132;
                break;
            }
        }
        return this.getX() + n;
    }
    
    private float getStartY(int n) {
        final int n2 = 0;
        switch (n) {
            default: {
                n = n2;
                break;
            }
            case 1: {
                n = 121;
                break;
            }
            case 2: {
                n = 126;
                break;
            }
            case 3: {
                n = 88;
                break;
            }
            case 4: {
                n = 93;
                break;
            }
        }
        return this.getY() + n;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < Rinocer2.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + Rinocer2.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Rinocer2.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < Rinocer2.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + Rinocer2.strPath[i] + ".xml");
        }
        Global.manager.unload(Rinocer2.strAtlas);
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
        this.flashPlayers[this.curIndex].setFrameIndex(235);
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
        return this.getTop() + 20.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.7f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + Rinocer2.strPath[i] + ".xml", FlashElements.class), Global.manager.get(Rinocer2.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
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
        this.verIdle = new float[] { 7.0f, 105.0f, 7.0f, 78.0f, 35.0f, 31.0f, 46.0f, 26.0f, 56.0f, 33.0f, 67.0f, 32.0f, 75.0f, 48.0f, 97.0f, 46.0f, 106.0f, 34.0f, 105.0f, 11.0f, 207.0f, 4.0f, 219.0f, 73.0f, 196.0f, 92.0f, 90.0f, 126.0f, 84.0f, 115.0f, 79.0f, 121.0f, 74.0f, 106.0f, 67.0f, 119.0f, 64.0f, 102.0f, 54.0f, 107.0f, 51.0f, 90.0f, 48.0f, 87.0f, 32.0f, 91.0f };
        MeshMethod.addVertices(this.verDizzy = new float[] { 5.0f, 97.0f, 8.0f, 73.0f, 44.0f, 29.0f, 56.0f, 26.0f, 82.0f, 54.0f, 91.0f, 42.0f, 87.0f, 12.0f, 186.0f, 7.0f, 212.0f, 47.0f, 193.0f, 76.0f, 108.0f, 130.0f, 81.0f, 104.0f, 73.0f, 126.0f, 71.0f, 110.0f, 59.0f, 122.0f, 61.0f, 102.0f, 49.0f, 110.0f, 49.0f, 88.0f, 25.0f, 89.0f }, 21.0f, -3.0f);
        MeshMethod.addVertices(this.verSkill1 = new float[] { 45.0f, 213.0f, 21.0f, 193.0f, 11.0f, 160.0f, 4.0f, 151.0f, 5.0f, 133.0f, 24.0f, 122.0f, 45.0f, 124.0f, 45.0f, 116.0f, 35.0f, 110.0f, 67.0f, 105.0f, 65.0f, 84.0f, 52.0f, 80.0f, 55.0f, 53.0f, 121.0f, 42.0f, 127.0f, 9.0f, 160.0f, 5.0f, 188.0f, 68.0f, 112.0f, 150.0f, 85.0f, 149.0f, 53.0f, 175.0f }, 21.0f, -3.0f);
        (this.polygon = new Polygon(this.verIdle)).setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 50.0f);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 80.0f - 27.0f, this.getY() - 4.0f);
        }
        this.checkBossCall();
    }
    
    public void addBullet(final int n) {
        if (this.currentTime - this.lastAttackTime < 0.06f) {
            return;
        }
        this.lastAttackTime = this.currentTime;
        final float startX = this.getStartX(n);
        final float dx = this.dx;
        final float startY = this.getStartY(n);
        final float dy = this.dy;
        final float bulletSpeed = this.bulletSpeed;
        final float random = MathUtils.random(0.9f, 1.1f);
        final float yIncrease = this.yIncrease;
        final float random2 = MathUtils.random(0.9f, 1.1f);
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(yIncrease * random2);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(160.0f), MathUtils.sinDeg(160.0f)).scl(bulletSpeed * random);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(startX + dx, startY + dy);
        MyMethods.setActorOrigin(bulletEnemy, 0.5f, 0.5f);
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
                if (this.getX() < 450.0f) {
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
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 192) {
                    this.setState();
                }
                if (this.getFrame() == 172) {
                    this.addBullet(1);
                    return;
                }
                if (this.getFrame() == 174) {
                    this.addBullet(3);
                    return;
                }
                if (this.getFrame() == 176) {
                    this.addBullet(2);
                    return;
                }
                if (this.getFrame() == 178) {
                    this.addBullet(4);
                    return;
                }
                break;
            }
            case Skill1: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 234) {
                    this.setState();
                }
                if (this.getFrame() == 214) {
                    this.addBullet(1);
                    return;
                }
                if (this.getFrame() == 216) {
                    this.addBullet(3);
                    return;
                }
                if (this.getFrame() == 218) {
                    this.addBullet(2);
                    return;
                }
                if (this.getFrame() == 220) {
                    this.addBullet(4);
                    return;
                }
                break;
            }
            case Dizzy_Front: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 110) {
                    this.bossState = Constant.BossState.Dizzy;
                    this.flashPlayers[this.curIndex].setFrameIndex(111);
                    this.polygon.setVertices(this.verDizzy);
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 150) {
                    break;
                }
                if (this.dizzyCount < 1) {
                    ++this.dizzyCount;
                    this.flashPlayers[this.curIndex].setFrameIndex(111);
                    return;
                }
                this.bossState = Constant.BossState.Dizzy_After;
                this.flashPlayers[this.curIndex].setFrameIndex(151);
                this.dizzyCount = 0;
            }
            case Dizzy_After: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 159) {
                    this.switchIdle();
                    return;
                }
                break;
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
            n = MathUtils.random(0, 8);
        }
        else {
            n = MathUtils.random(0, 7);
        }
        if (n <= 1) {
            this.switchIdle();
            return;
        }
        if (n <= 5) {
            this.switchAttack();
            return;
        }
        if (n <= 7) {
            this.switchSkill1();
            return;
        }
        this.switchDizzy();
    }
    
    public void switchAttack() {
        this.bossState = Constant.BossState.Attack;
        this.flashPlayers[this.curIndex].setFrameIndex(160);
    }
    
    public void switchDizzy() {
        this.bossState = Constant.BossState.Dizzy_Front;
        this.flashPlayers[this.curIndex].setFrameIndex(101);
        this.polygon.setVertices(this.verDizzy);
    }
    
    public void switchIdle() {
        this.bossState = Constant.BossState.Idle;
        this.flashPlayers[this.curIndex].setFrameIndex(61);
        this.polygon.setVertices(this.verIdle);
    }
    
    public void switchSkill1() {
        this.bossState = Constant.BossState.Skill1;
        this.flashPlayers[this.curIndex].setFrameIndex(193);
        this.polygon.setVertices(this.verSkill1);
    }
}
