package com.gleejet.sun.roles.build;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.gleejet.sun.roles.ground.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;

public class Tower extends Build
{
    private static final int PlayerNum = 1;
    private static int curLevel = 0;
    private static String[] strAtlass1;
    private static String[] strAtlass2;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[] strPath2;
    private static String[] strPath3;
    private static String[][] strPaths;
    private static final String strPic1 = "animation/archer/archer1";
    private static final String strPic2 = "animation/tower/tower1";
    private static String strRegion;
    private static String[] strRegions;
    private static final String strRoot1 = "animation/archer/";
    private static final String strRoot2 = "animation/tower/";
    private Archer archer1;
    private Archer archer2;
    private float attackInterval1;
    private float attackInterval2;
    private float lastAttack1;
    private float lastAttack2;
    private TextureRegion regionBack;
    
    static {
        Tower.strPath1 = new String[] { "tower1/tower_die_1" };
        Tower.strPath2 = new String[] { "tower2/tower_die_2" };
        Tower.strPath3 = new String[] { "tower3/tower_die_3" };
        Tower.strPaths = new String[][] { Tower.strPath1, Tower.strPath2, Tower.strPath3 };
        Tower.strAtlass1 = new String[] { "archer1/archer_1", "archer2/archer_2", "archer3/archer_3" };
        Tower.strAtlass2 = new String[] { "tower1/tower_1", "tower2/tower_2", "tower3/tower_3" };
        Tower.strRegions = new String[] { "archer_1_jian", "archer_2_jian", "archer_3_jian" };
    }
    
    public Tower() {
        this.attackInterval1 = 0.75f;
        this.attackInterval2 = 0.65f;
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Tower.strPath.length; ++i) {
            if (i < Tower.strPath.length - 1) {}
            Global.manager.load("animation/tower/" + Tower.strPath[i] + ".xml", FlashElements.class);
        }
        if (Tower.curLevel == 1) {
            Archer.loadElements(1);
        }
        else {
            if (Tower.curLevel == 2) {
                Archer.loadElements(1);
                Archer.loadElements(2);
                return;
            }
            if (Tower.curLevel == 3) {
                Archer.loadElements(2);
                Archer.loadElements(3);
            }
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Tower.curLevel = curLevel;
        Tower.strPath = Tower.strPaths[Tower.curLevel - 1];
        Tower.strRegion = Tower.strRegions[Tower.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Tower.strPath.length; ++i) {
            if (i < Tower.strPath.length - 1) {}
            Global.manager.unload("animation/tower/" + Tower.strPath[i] + ".xml");
        }
        if (Tower.curLevel == 1) {
            Archer.unloadElements(1);
        }
        else {
            if (Tower.curLevel == 2) {
                Archer.unloadElements(1);
                Archer.unloadElements(2);
                return;
            }
            if (Tower.curLevel == 3) {
                Archer.unloadElements(2);
                Archer.unloadElements(3);
            }
        }
    }
    
    public void AddBullet(final float n, final float n2) {
        this.player.getX();
        MathUtils.random(-0.1f, 0.1f);
        this.player.getWidth();
        this.player.getY();
        MathUtils.random(0.0f, 0.5f);
        this.player.getHeight();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth() * 1.2f, this.regionBullet.getRegionHeight() * this.bulletScale * 1.2f);
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(MathUtils.random(0.9f, 1.1f) * this.yIncrease);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        final float n3 = 155.0f + 20.0f * ((800.0f - this.getX()) / 800.0f);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(n3), MathUtils.sinDeg(n3)).scl(this.bulletSpeed);
        bulletEnemy.setPosition(n, n2);
        bulletEnemy.setTracePointNum(16);
        bulletEnemy.isAddTrace = true;
        Global.groupBulletEnemy.addActor(bulletEnemy);
    }
    
    @Override
    protected void CheckState(final boolean b) {
        switch (this.state) {
            default: {
                this.translate(-Constant.tranSpeed, 0.0f);
                break;
            }
            case Move_To: {
                this.translate(-Constant.tranSpeed, 0.0f);
                if (800.0f - this.getX() <= this.getWidth() * 0.3f) {
                    break;
                }
                this.state = Constant.EnemyState.Connect;
                if (this.archer1 != null) {
                    this.archer1.getCurFlash().play();
                }
                if (this.archer2 != null) {
                    this.archer2.getCurFlash().play();
                    return;
                }
                break;
            }
            case Connect: {
                this.translate(-Constant.tranSpeed, 0.0f);
                if (this.archer1 != null && this.archer1.getCurFlash().GetPlayPercent() > 0.95f) {
                    this.archer1.SwitchNext();
                    if (this.archer2 != null) {
                        this.archer2.SwitchNext();
                    }
                    this.state = Constant.EnemyState.Attack;
                    this.lastAttack2 = this.currentTime - 0.3f;
                    return;
                }
                break;
            }
            case Attack: {
                this.translate(-Constant.tranSpeed, 0.0f);
                if (this.getX() - this.player.getRight() < 0.0f) {
                    this.state = Constant.EnemyState.Dead;
                    this.player.beAttacked(this.attackDamage * 5.0f);
                    this.player.beCollision(1, 1.5f);
                    this.Die();
                    Global.isAllKill = false;
                    return;
                }
                break;
            }
            case Dead: {
                this.translate(-Constant.tranSpeed, 0.0f);
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
    
    @Override
    public void Clear() {
        super.Clear();
        if (this.archer1 != null) {
            this.archer1.Clear();
            this.archer1.isDrawHp = false;
            this.archer1.SwitchNext();
            this.archer1.getCurFlash().pause();
        }
        if (this.archer2 != null) {
            this.archer2.Clear();
            this.archer2.isDrawHp = false;
            this.archer2.SwitchNext();
            this.archer2.getCurFlash().pause();
        }
    }
    
    @Override
    public void Die() {
        super.Die();
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY() + 10.0f, 0.7f);
        SoundHandle.playForBomb();
    }
    
    @Override
    public float GetDrawHpPercent() {
        return 0.9f;
    }
    
    @Override
    public float GetDrawHpStartX() {
        return 5.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[1];
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            String s;
            if (i < this.flashPlayers.length - 1) {
                s = "animation/archer/";
            }
            else {
                s = "animation/tower/";
            }
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(s + Tower.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false, true, false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[0].setAlphaTime(1.5f);
        this.scale *= 0.7f;
        this.setSize(80.0f, 160.0f);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.curIndex = 0;
        (this.regionBullet = new TextureRegion(Global.manager.get("game/archer.pack", TextureAtlas.class).findRegion(Tower.strRegion))).flip(true, false);
        if (Tower.curLevel == 1) {
            Archer.setCreateLevel(1);
            this.archer1 = new Archer();
        }
        else if (Tower.curLevel == 2) {
            Archer.setCreateLevel(1);
            this.archer1 = new Archer();
            Archer.setCreateLevel(2);
            this.archer2 = new Archer();
        }
        else if (Tower.curLevel == 3) {
            Archer.setCreateLevel(2);
            this.archer1 = new Archer();
            Archer.setCreateLevel(3);
            this.archer2 = new Archer();
        }
        if (this.archer1 != null) {
            this.archer1.isDrawHp = false;
            this.archer1.SwitchNext();
            this.archer1.getCurFlash().pause();
        }
        if (this.archer2 != null) {
            this.archer2.isDrawHp = false;
            this.archer2.SwitchNext();
            this.archer2.getCurFlash().pause();
        }
    }
    
    @Override
    public void SetProperty() {
        this.setJsonValue(Tower.curLevel);
        this.regionBack = Global.manager.get("game/archer.pack", TextureAtlas.class).findRegion("jianta" + Tower.curLevel);
        (this.polygon = new Polygon(new float[] { 6.0f, 123.0f, 9.0f, 89.0f, 9.0f, 89.0f, 17.0f, 89.0f, 17.0f, 89.0f, 10.0f, 11.0f, 10.0f, 11.0f, 10.0f, 11.0f, 58.0f, 11.0f, 57.0f, 156.0f, 41.0f, 157.0f, 36.0f, 122.0f })).setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void UpdateHp() {
        float n;
        if (this.getType() == Constant.EnemyType.Tower1) {
            n = this.getTop() - 4.0f;
        }
        else if (this.getType() == Constant.EnemyType.Tower2) {
            n = this.getTop() + 10.0f;
        }
        else {
            n = this.getTop() + 18.0f;
        }
        this.spriteHpBack.setSize(this.getWidth() * this.GetDrawHpPercent(), 4.0f);
        this.spriteHpBack.setPosition(this.getX() + this.GetDrawHpStartX(), n);
        this.spriteHpFront.setSize((this.getWidth() * this.GetDrawHpPercent() - 2.0f) * this.currentHp / this.maxHp, 2.0f);
        this.spriteHpFront.setPosition(this.getX() + this.GetDrawHpStartX() + 1.0f, n + 1.0f);
    }
    
    @Override
    public void act(final float n) {
        this.AddBullet(0.5f, 0.65f);
        this.CheckState(true);
        super.act(n);
        if (this.state == Constant.EnemyState.Dead) {
            this.getCurFlash().setPosition(this.getX(), this.getY() - 16.0f);
        }
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX(), this.getY() - 10.0f);
        }
        switch (this.type) {
            case Tower1: {
                this.archer1.getCurFlash().updateRunTime(n);
                this.archer1.getCurFlash().setPosition(this.getX() + 15.0f, this.getY() + this.regionBack.getRegionHeight() * 0.8f);
                break;
            }
            case Tower2: {
                this.archer1.getCurFlash().updateRunTime(n);
                this.archer1.getCurFlash().setPosition(this.getX(), this.getY() + this.regionBack.getRegionHeight() * 0.8f);
                this.archer2.getCurFlash().updateRunTime(n * 1.05f);
                this.archer2.getCurFlash().setPosition(this.getX() + 25.0f, this.getY() + this.regionBack.getRegionHeight() * 0.8f);
                break;
            }
            case Tower3: {
                this.archer1.getCurFlash().updateRunTime(n);
                this.archer1.getCurFlash().setPosition(this.getX() + 2.0f, this.getY() + this.regionBack.getRegionHeight() * 0.65f);
                this.archer2.getCurFlash().updateRunTime(n * 1.05f);
                this.archer2.getCurFlash().setPosition(this.getX() + 28.0f, this.getY() + this.regionBack.getRegionHeight() * 0.65f);
                break;
            }
        }
        if (this.archer1 != null) {
            this.addBullet1();
        }
        if (this.archer2 != null) {
            this.addBullet2();
        }
    }
    
    public void addBullet1() {
        if (this.currentTime - this.lastAttack1 < this.attackInterval1 || this.state != Constant.EnemyState.Attack || this.archer1.getCurFlash().GetPlayPercent() < 0.6f) {
            return;
        }
        this.AddBullet(this.archer1.getCurFlash().getPosition().x + 33.0f, this.archer1.getCurFlash().getPosition().y + 26.0f);
        this.lastAttack1 = this.currentTime;
        this.attackInterval1 = 0.7f * MathUtils.random(0.9f, 1.1f);
    }
    
    public void addBullet2() {
        if (this.currentTime - this.lastAttack2 < this.attackInterval2 || this.state != Constant.EnemyState.Attack || this.archer2.getCurFlash().GetPlayPercent() < 0.6f) {
            return;
        }
        this.AddBullet(this.archer1.getCurFlash().getPosition().x + 33.0f + 28.0f, this.archer1.getCurFlash().getPosition().y + 26.0f + this.dy);
        this.lastAttack2 = this.currentTime;
        this.attackInterval2 = MathUtils.random(0.9f, 1.1f) * 0.6f;
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        if (this.state == Constant.EnemyState.Dead) {
            super.draw(spriteBatch, n);
        }
        else {
            if (this.currentHp > 0.0f) {
                this.UpdateHp();
                this.spriteHpBack.draw(spriteBatch);
                this.spriteHpFront.draw(spriteBatch);
            }
            spriteBatch.getColor();
            if (this.attackTime > 0.0f) {
                spriteBatch.setColor(Color.GRAY);
            }
            if (this.archer1 != null) {
                this.archer1.draw(spriteBatch, n);
            }
            if (this.archer2 != null) {
                this.archer2.draw(spriteBatch, n);
            }
            spriteBatch.draw(this.regionBack, this.getX(), this.getY());
            if (this.attackTime > 0.0f) {
                spriteBatch.setColor(Color.WHITE);
            }
        }
    }
}
