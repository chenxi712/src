package com.gleejet.sun.roles.build;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.badlogic.gdx.math.*;

public class Artillery extends Build
{
    private static final int PlayerNum = 2;
    private static int curLevel = 0;
    private static String[] strAtlass;
    private static String strEffect;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[] strPath2;
    private static String[] strPath3;
    private static String[][] strPaths;
    private static final String strRoot = "animation/artillery/";
    private Array<TextureAtlas.AtlasRegion> arrRegionBullet;
    private Array<TextureAtlas.AtlasRegion> arrRegionShot;
    
    static {
        Artillery.strPath1 = new String[] { "artillery1/artillery_attack_1", "artillery1/artillery_die_1" };
        Artillery.strPath2 = new String[] { "artillery2/artillery_attack_2", "artillery2/artillery_die_2" };
        Artillery.strPath3 = new String[] { "artillery3/artillery_attack_3", "artillery3/artillery_die_3" };
        Artillery.strPaths = new String[][] { Artillery.strPath1, Artillery.strPath2, Artillery.strPath3 };
        Artillery.strAtlass = new String[] { "artillery1/artillery_1", "artillery2/artillery_2", "artillery3/artillery_3" };
        Artillery.strEffect = "effect/missile.pack";
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Artillery.strPath.length; ++i) {
            Global.manager.load("animation/artillery/" + Artillery.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Artillery.strEffect, TextureAtlas.class);
    }
    
    public static void setCreateLevel(final int curLevel) {
        Artillery.curLevel = curLevel;
        Artillery.strPath = Artillery.strPaths[Artillery.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Artillery.strPath.length; ++i) {
            Global.manager.unload("animation/artillery/" + Artillery.strPath[i] + ".xml");
        }
        Global.manager.load(Artillery.strEffect, TextureAtlas.class);
    }
    
    public void AddBullet(float x) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval || this.state != Constant.EnemyState.Attack || this.getCurFlash().GetPlayPercent() < x) {
            return;
        }
        x = this.getX();
        final float y = this.getY();
        this.player.getX();
        MathUtils.random(0.4f, 0.6f);
        this.player.getWidth();
        this.player.getY();
        MathUtils.random(0.6f, 0.9f);
        this.player.getHeight();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(MathUtils.random(0.9f, 1.1f) * this.yIncrease);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(162.0f), MathUtils.sinDeg(162.0f)).nor().scl(this.bulletSpeed);
        bulletEnemy.setPosition(x + 18.0f - 5.0f - bulletEnemy.getOriginX(), y + 45.0f - bulletEnemy.getOriginY());
        bulletEnemy.isAddTrace = false;
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
    }
    
    @Override
    public float GetDrawHpPercent() {
        return 0.8f;
    }
    
    @Override
    public float GetDrawHpStartX() {
        return 10.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[2];
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/artillery/" + Artillery.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[1].setAlphaTime(1.5f);
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].pause();
        this.curIndex = 0;
    }
    
    @Override
    public void SetProperty() {
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-10");
        this.setJsonValue(Artillery.curLevel);
        this.flashPlayers[0].SetTimeScale(this.flashPlayers[0].GetEndTime() / this.attackInterval);
        final TextureAtlas textureAtlas = Global.manager.get(Artillery.strEffect, TextureAtlas.class);
        (this.arrRegionShot = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 0, 10);
        (this.arrRegionBullet = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 10, 3);
        Bullet10.arrRegionBullet = this.arrRegionBullet;
        switch (Artillery.curLevel) {
            default: {}
            case 1: {
                this.polygon = new Polygon(new float[] { 6.0f, 51.0f, 5.0f, 36.0f, 20.0f, 32.0f, 4.0f, 17.0f, 4.0f, 7.0f, 14.0f, 3.0f, 91.0f, 4.0f, 95.0f, 16.0f, 91.0f, 53.0f, 74.0f, 54.0f, 52.0f, 44.0f, 15.0f, 58.0f });
            }
            case 2: {
                this.polygon = new Polygon(new float[] { 6.0f, 46.0f, 7.0f, 37.0f, 23.0f, 32.0f, 7.0f, 17.0f, 7.0f, 8.0f, 16.0f, 5.0f, 93.0f, 5.0f, 100.0f, 18.0f, 100.0f, 56.0f, 83.0f, 59.0f, 65.0f, 41.0f, 12.0f, 60.0f });
            }
            case 3: {
                this.polygon = new Polygon(new float[] { 7.0f, 53.0f, 7.0f, 38.0f, 24.0f, 34.0f, 7.0f, 18.0f, 7.0f, 8.0f, 16.0f, 5.0f, 93.0f, 5.0f, 94.0f, 38.0f, 89.0f, 60.0f, 64.0f, 57.0f, 55.0f, 48.0f, 16.0f, 65.0f });
            }
        }
    }
    
    @Override
    public void act(final float n) {
        this.AddBullet(0.8f);
        this.CheckState(false);
        super.act(n);
        this.setPolygonPosition();
    }
    
    public void setPolygonPosition() {
        Label_0007: {
            if (this.polygon != null) {
                final float x = Global.pAdd.x;
                final float y = Global.pAdd.y;
                switch (this.type) {
                    default: {
                        return;
                    }
                    case Artillery1: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() + 1.0f, this.getY());
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() + 1.0f, this.getY());
                                return;
                            }
                        }
                    }
                    case Artillery2: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() + 1.0f, this.getY() - 2.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX(), this.getY() - 2.0f);
                                return;
                            }
                        }
                    }
                    case Artillery3: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() - 2.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() - 2.0f);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
