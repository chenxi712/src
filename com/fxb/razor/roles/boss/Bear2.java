package com.fxb.razor.roles.boss;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.flash.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.objects.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;

public class Bear2 extends BaseBoss
{
    private static final int PlayerNum = 1;
    private static final int after1 = 111;
    private static final int after2 = 119;
    private static final int attack1 = 120;
    private static final int attack2 = 143;
    private static int curLevel = 0;
    private static final int die1 = 175;
    private static final int die2 = 197;
    private static final int dizzy1 = 71;
    private static final int dizzy2 = 110;
    private static final int front1 = 63;
    private static final int front2 = 70;
    private static final int idle1 = 32;
    private static final int idle2 = 62;
    private static final int run1 = 0;
    private static final int run2 = 31;
    private static final int skill1 = 144;
    private static final int skill2 = 174;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRegion;
    private static final String strRoot = "boss/xml/";
    private int dizzyCount;
    private float[] verDizzy;
    private float[] verIdle;
    private float[] verSkill1;
    
    static {
        Bear2.strPath = new String[] { "bear_total_2" };
        Bear2.strAtlas = "boss/pack/bear.pack";
        Bear2.curLevel = 1;
    }
    
    public Bear2() {
        this.dizzyCount = 0;
    }
    
    public static void loadElements(int i) {
        for (i = 0; i < Bear2.strPath.length; ++i) {
            Global.manager.load("boss/xml/" + Bear2.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Bear2.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements(int i) {
        for (i = 0; i < Bear2.strPath.length; ++i) {
            Global.manager.unload("boss/xml/" + Bear2.strPath[i] + ".xml");
        }
        Global.manager.unload(Bear2.strAtlas);
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
        this.flashPlayers[this.curIndex].setFrameIndex(175);
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
        return this.getTop() + 36.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.33333334f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("boss/xml/" + Bear2.strPath[i] + ".xml", FlashElements.class), Global.manager.get(Bear2.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
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
        this.regionBullet = Assets.atlasBossBullet.findRegion("paodan2");
    }
    
    @Override
    public void SetProperty() {
        this.setBossValue();
        this.moveSpeed = 3.0f;
        this.speed.set(-this.moveSpeed, 0.0f);
        this.verIdle = new float[] { 7.0f, 72.0f, 15.0f, 38.0f, 64.0f, 38.0f, 61.0f, 8.0f, 192.0f, 10.0f, 195.0f, 111.0f, 164.0f, 125.0f, 110.0f, 108.0f, 82.0f, 110.0f, 46.0f, 79.0f, 46.0f, 79.0f };
        this.verDizzy = new float[] { 5.0f, 32.0f, 27.0f, 13.0f, 212.0f, 8.0f, 194.0f, 53.0f, 195.0f, 102.0f, 148.0f, 118.0f, 129.0f, 119.0f, 72.0f, 99.0f, 36.0f, 66.0f };
        this.verSkill1 = new float[] { 9.0f, 213.0f, 9.0f, 147.0f, 21.0f, 104.0f, 61.0f, 102.0f, 52.0f, 85.0f, 82.0f, 51.0f, 91.0f, 28.0f, 89.0f, 15.0f, 81.0f, 7.0f, 132.0f, 5.0f, 136.0f, 38.0f, 174.0f, 80.0f, 165.0f, 116.0f, 70.0f, 186.0f, 62.0f, 205.0f, 27.0f, 211.0f, 18.0f, 217.0f };
        MeshMethod.addVertices(this.verDizzy, 20.0f, 0.0f);
        MeshMethod.addVertices(this.verSkill1, 68.0f, 0.0f);
        (this.polygon = new Polygon(this.verIdle)).setPosition(this.getX(), this.getY());
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
    
    public void addBullet(final int n) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval) {
            return;
        }
        float n2;
        if (n == 1) {
            n2 = this.getX() + 185.0f;
        }
        else {
            n2 = this.getX() + 205.0f;
        }
        float n3;
        if (n == 1) {
            n3 = this.getY() + 130.0f;
        }
        else {
            n3 = this.getY() + 180.0f;
        }
        float yIncrease;
        if (n == 1) {
            yIncrease = this.yIncrease;
        }
        else {
            yIncrease = this.yIncrease * 0.6f;
        }
        final float x = this.player.getX();
        final float random = MathUtils.random(-0.1f, 0.1f);
        final float width = this.player.getWidth();
        final float y = this.player.getY();
        final float random2 = MathUtils.random(0.5f, 0.7f);
        final float height = this.player.getHeight();
        final BulletEnemy bulletEnemy = new BulletEnemy();
        bulletEnemy.Clear();
        bulletEnemy.isAddTrace = false;
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(yIncrease);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        bulletEnemy.GetSpeed().set(x + (random + 0.5f) * width - n2, y + (random2 + 0.7f) * height - n3).nor().scl(this.bulletSpeed);
        bulletEnemy.bulletType = Constant.BulletEnemyType.Boss;
        bulletEnemy.setPosition(n2, n3);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
    }
    
    public void checkState() {
        switch (this.bossState) {
            case Run: {
                this.translate(this.speed.x, this.speed.y);
                this.getCurFlash().setPosition(this.getX(), this.getY() - Interpolation.pow3.apply(13.0f, 8.0f, this.getCurFlash().GetPlayPercent()));
                if (this.getX() < 665.0f) {
                    this.flashPlayers[this.curIndex].play();
                }
                if (Global.isUseRGBA4444) {
                    if (this.getX() < 455.0f) {
                        this.switchIdle();
                        return;
                    }
                    break;
                }
                else {
                    if (this.flashPlayers[this.curIndex].getFrameIndex() >= 31) {
                        this.switchIdle();
                        return;
                    }
                    break;
                }
            }
            case Idle: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 62) {
                    this.setState();
                    return;
                }
                break;
            }
            case Attack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 143) {
                    this.setState();
                }
                if (this.getFrame() == 125 || this.getFrame() == 138) {
                    this.addBullet(1);
                    return;
                }
                break;
            }
            case Skill1: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 174) {
                    this.setState();
                }
                if (this.getFrame() == 162) {
                    this.addBullet(2);
                    return;
                }
                break;
            }
            case Dizzy_Front: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 70) {
                    this.bossState = Constant.BossState.Dizzy;
                    this.flashPlayers[this.curIndex].setFrameIndex(71);
                    this.polygon.setVertices(this.verDizzy);
                    return;
                }
                break;
            }
            case Dizzy: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() < 110) {
                    break;
                }
                if (this.dizzyCount < 1) {
                    ++this.dizzyCount;
                    this.flashPlayers[this.curIndex].setFrameIndex(71);
                    return;
                }
                this.bossState = Constant.BossState.Dizzy_After;
                this.flashPlayers[this.curIndex].setFrameIndex(111);
                this.dizzyCount = 0;
            }
            case Dizzy_After: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= 119) {
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
        if (n <= 3) {
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
        this.flashPlayers[this.curIndex].setFrameIndex(120);
    }
    
    public void switchDizzy() {
        this.bossState = Constant.BossState.Dizzy_Front;
        this.flashPlayers[this.curIndex].setFrameIndex(63);
    }
    
    public void switchIdle() {
        this.bossState = Constant.BossState.Idle;
        this.flashPlayers[this.curIndex].setFrameIndex(32);
        this.polygon.setVertices(this.verIdle);
    }
    
    public void switchSkill1() {
        this.bossState = Constant.BossState.Skill1;
        this.flashPlayers[this.curIndex].setFrameIndex(144);
        this.polygon.setVertices(this.verSkill1);
    }
}
