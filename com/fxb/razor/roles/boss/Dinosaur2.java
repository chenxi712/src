package com.fxb.razor.roles.boss;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.flash.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.objects.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;

public class Dinosaur2 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int after1 = 150;
    private static final int after2 = 159;
    private static final int attack1 = 160;
    private static final int attack2 = 190;
    private static int curLevel = 0;
    private static final int die1 = 295;
    private static final int die2 = 304;
    private static final int dizzy1 = 110;
    private static final int dizzy2 = 149;
    private static final int front1 = 102;
    private static final int front2 = 109;
    private static final int idle1 = 62;
    private static final int idle2 = 101;
    private static final int run1 = 0;
    private static final int run2 = 61;
    private static final int skill1 = 190;
    private static final int skill2 = 294;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    private float lastSkillTime;
    private float[] verDizzy;
    private float[] verIdle;
    
    static {
        Dinosaur2.strPath = new String[] { "dinosaur_total_2" };
        Dinosaur2.strAtlas = "boss/pack/dinosaur.pack";
        Dinosaur2.curLevel = 1;
    }
    
    public Dinosaur2() {
        this.dizzyCount = 0;
        this.lastSkillTime = 0.0f;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < Dinosaur2.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + Dinosaur2.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Dinosaur2.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < Dinosaur2.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + Dinosaur2.strPath[i] + ".xml");
        }
        Global.manager.unload(Dinosaur2.strAtlas);
    }
    
    private void useSkill1() {
        if (this.currentTime - this.lastSkillTime > 0.5f) {
            this.player.beAttacked(200.0f);
            this.player.beCollision(3, 2.0f);
            Effect.addSmoke(this.player.getX() + this.player.getWidth() / 2.0f + 20.0f, this.player.getY() + 30.0f, 1.0f);
            this.lastSkillTime = this.currentTime;
        }
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
        this.flashPlayers[this.curIndex].setFrameIndex(295);
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
        return 312.0f;
    }
    
    @Override
    public float GetDrawHpY() {
        return this.getTop() + 20.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.28f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + Dinosaur2.strPath[i] + ".xml", FlashElements.class), Global.manager.get(Dinosaur2.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
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
        this.verIdle = new float[] { 37.0f, 125.0f, 21.0f, 108.0f, 8.0f, 76.0f, 6.0f, 30.0f, 31.0f, 12.0f, 49.0f, 30.0f, 75.0f, 22.0f, 74.0f, 8.0f, 186.0f, 4.0f, 192.0f, 82.0f, 161.0f, 107.0f, 76.0f, 132.0f, 55.0f, 76.0f, 46.0f, 83.0f, 51.0f, 93.0f };
        MeshMethod.addVertices(this.verDizzy = new float[] { 20.0f, 103.0f, 9.0f, 80.0f, 8.0f, 51.0f, 18.0f, 9.0f, 45.0f, 2.0f, 191.0f, 19.0f, 205.0f, 57.0f, 156.0f, 101.0f, 69.0f, 119.0f, 50.0f, 67.0f }, 14.0f, -8.0f);
        (this.polygon = new Polygon(this.verIdle)).setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 25.0f);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 282.0f, this.getY() - 10.0f);
        }
    }
    
    public void addBullet(float n, float n2) {
        if (this.currentTime - this.lastAttackTime < 0.06f) {
            return;
        }
        this.lastAttackTime = this.currentTime;
        n += this.getX();
        n2 += this.getY();
        final float x = this.player.getX();
        final float random = MathUtils.random(-0.1f, 0.1f);
        final float width = this.player.getWidth();
        final float y = this.player.getY();
        final float random2 = MathUtils.random(0.5f, 0.7f);
        final float height = this.player.getHeight();
        final float random3 = MathUtils.random(0.95f, 1.05f);
        final float bulletSpeed = this.bulletSpeed;
        final float yIncrease = this.yIncrease;
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(yIncrease * random3);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        bulletEnemy.GetSpeed().set(x + (0.5f + random) * width - n, y + (0.7f + random2) * height - n2).nor().scl(bulletSpeed * random3);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(n, n2);
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
                if (this.getX() < 240.0f) {
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
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 190) {
                    this.setState();
                }
                if (this.getFrame() == 170) {
                    this.addBullet(420.0f, 130.0f);
                }
                if (this.getFrame() == 172) {
                    this.addBullet(428.0f, 130.0f);
                    return;
                }
                break;
            }
            case Skill1: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 294) {
                    this.setState();
                }
                if (this.flashPlayers[this.curIndex].getFrameIndex() == 229) {
                    this.useSkill1();
                    return;
                }
                break;
            }
            case Dizzy_Front: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 109) {
                    this.bossState = Constant.BossState.Dizzy;
                    this.flashPlayers[this.curIndex].setFrameIndex(110);
                    this.polygon.setVertices(this.verDizzy);
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 149) {
                    break;
                }
                if (this.dizzyCount < 1) {
                    ++this.dizzyCount;
                    this.flashPlayers[this.curIndex].setFrameIndex(110);
                    return;
                }
                this.bossState = Constant.BossState.Dizzy_After;
                this.flashPlayers[this.curIndex].setFrameIndex(150);
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
            n = MathUtils.random(0, 10);
        }
        else {
            n = MathUtils.random(0, 8);
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
        this.switchDizzy();
    }
    
    public void switchAttack() {
        this.bossState = Constant.BossState.Attack;
        this.flashPlayers[this.curIndex].setFrameIndex(160);
    }
    
    public void switchDizzy() {
        this.bossState = Constant.BossState.Dizzy_Front;
        this.flashPlayers[this.curIndex].setFrameIndex(102);
    }
    
    public void switchIdle() {
        this.bossState = Constant.BossState.Idle;
        this.flashPlayers[this.curIndex].setFrameIndex(62);
        this.polygon.setVertices(this.verIdle);
    }
    
    public void switchSkill1() {
        this.bossState = Constant.BossState.Skill1;
        this.flashPlayers[this.curIndex].setFrameIndex(190);
    }
}
