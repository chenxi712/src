package com.gleejet.sun.roles.ground;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Archer extends Ground
{
    private static final int PlayerNum = 4;
    private static int curLevel = 0;
    private static String[] strAtlass;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[] strPath2;
    private static String[] strPath3;
    private static String[][] strPaths;
    private static String strRegion;
    private static String[] strRegions;
    private static final String strRoot = "animation/archer/";
    
    static {
        Archer.strPath1 = new String[] { "archer1/archer_run_1", "archer1/archer_conn_1", "archer1/archer_attack_1", "archer1/archer_die_1" };
        Archer.strPath2 = new String[] { "archer2/archer_run_2", "archer2/archer_conn_2", "archer2/archer_attack_2", "archer2/archer_die_2" };
        Archer.strPath3 = new String[] { "archer3/archer_run_3", "archer3/archer_conn_3", "archer3/archer_attack_3", "archer3/archer_die_3" };
        Archer.strPaths = new String[][] { Archer.strPath1, Archer.strPath2, Archer.strPath3 };
        Archer.strAtlass = new String[] { "archer1.pack", "archer", "archer" };
        Archer.strRegions = new String[] { "archer_1_jian", "archer_2_jian", "archer_3_jian" };
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Archer.strPath.length; ++i) {
            Global.manager.load("animation/archer/" + Archer.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Archer.curLevel = curLevel;
        Archer.strPath = Archer.strPaths[Archer.curLevel - 1];
        Archer.strRegion = Archer.strRegions[Archer.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Archer.strPath.length; ++i) {
            Global.manager.unload("animation/archer/" + Archer.strPath[i] + ".xml");
        }
    }
    
    public void AddBullet(float random, float n) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval || this.state != Constant.EnemyState.Attack || this.getCurFlash().GetPlayPercent() < n) {
            return;
        }
        n = this.getX() + 20.0f + 30.0f - 13.0f;
        final float n2 = this.getY() + 10.0f + 11.0f - 4.0f;
        final float x = this.player.getX();
        final float random2 = MathUtils.random(-0.1f, 0.1f);
        final float width = this.player.getWidth();
        final float y = this.player.getY();
        random = MathUtils.random(random, random + 0.5f);
        final float height = this.player.getHeight();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth() * 1.2f, this.regionBullet.getRegionHeight() * 1.2f);
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(MathUtils.random(0.9f, 1.1f) * this.yIncrease);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        bulletEnemy.GetSpeed().set(x + (random2 + 0.5f) * width - n, y + (random + 0.5f) * height - n2).nor().scl(this.bulletSpeed);
        bulletEnemy.setPosition(n, n2);
        bulletEnemy.isAddTrace = true;
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.bulletBaseRotation = 0.0f;
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
        float n = 5.0f;
        if (this.state == Constant.EnemyState.Connect) {
            n = 5.0f + 10.0f * this.getCurFlash().GetPlayPercent();
        }
        else if (this.state == Constant.EnemyState.Attack) {
            return 15.0f;
        }
        return n;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[4];
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/archer/" + Archer.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, false, true, false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[3].setAlphaTime(1.5f);
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.curIndex = 0;
        this.flashPlayers[this.curIndex].play();
        (this.regionBullet = new TextureRegion(Global.manager.get("game/archer.pack", TextureAtlas.class).findRegion(Archer.strRegion))).flip(true, false);
    }
    
    @Override
    public void SetProperty() {
        this.setJsonValue(Archer.curLevel);
        this.flashPlayers[2].SetTimeScale(this.flashPlayers[2].GetEndTime() / this.attackInterval);
        switch (Archer.curLevel) {
            default: {}
            case 1: {
                this.polygon = new Polygon(new float[] { 16.0f, 49.0f, 8.0f, 3.0f, 34.0f, 3.0f, 43.0f, 50.0f });
            }
            case 2: {
                this.polygon = new Polygon(new float[] { 11.0f, 44.0f, 6.0f, 3.0f, 32.0f, 3.0f, 43.0f, 47.0f, 20.0f, 53.0f });
            }
            case 3: {
                this.polygon = new Polygon(new float[] { 11.0f, 46.0f, 7.0f, 3.0f, 34.0f, 2.0f, 40.0f, 53.0f, 15.0f, 54.0f });
            }
        }
    }
    
    @Override
    public void act(final float n) {
        this.AddBullet(0.5f, 0.65f);
        this.CheckState(true, true, 25);
        super.act(n);
        this.setPolygonPosition();
    }
    
    public void setPolygonPosition() {
        Label_0007: {
            if (this.polygon != null) {
                switch (this.type) {
                    default: {
                        return;
                    }
                    case Archer1: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 5.0f, this.getY() + 2.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() + 8.0f, this.getY());
                                return;
                            }
                            case Move_Away: {
                                this.polygon.setPosition(this.getX() - 4.0f, this.getY() + 2.0f);
                                return;
                            }
                        }
                    }
                    case Archer2: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 5.0f, this.getY() + 2.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() + 8.0f, this.getY());
                                return;
                            }
                            case Move_Away: {
                                this.polygon.setPosition(this.getX() - 4.0f, this.getY() + 2.0f);
                                return;
                            }
                        }
                    }
                    case Archer3: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() + 2.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() + 10.0f, this.getY());
                                return;
                            }
                            case Move_Away: {
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() + 2.0f);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
