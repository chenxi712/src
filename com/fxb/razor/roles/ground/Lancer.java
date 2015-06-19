package com.fxb.razor.roles.ground;

import com.fxb.razor.objects.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.math.*;

public class Lancer extends Ground
{
    private static final int PlayerNum = 3;
    private static int curLevel = 0;
    private static String[] strAtlass;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[] strPath2;
    private static String[] strPath3;
    private static String[][] strPaths;
    private static String strRegion;
    private static String[] strRegions;
    private static final String strRoot = "animation/lancer/";
    
    static {
        Lancer.strPath1 = new String[] { "lancer1/lancer_run_1", "lancer1/lancer_attack_1", "lancer1/lancer_die_1" };
        Lancer.strPath2 = new String[] { "lancer2/lancer_run_2", "lancer2/lancer_attack_2", "lancer2/lancer_die_2" };
        Lancer.strPath3 = new String[] { "lancer3/lancer_run_3", "lancer3/lancer_attack_3", "lancer3/lancer_die_3" };
        Lancer.strPaths = new String[][] { Lancer.strPath1, Lancer.strPath2, Lancer.strPath3 };
        Lancer.strAtlass = new String[] { "archer", "archer", "archer" };
        Lancer.strRegions = new String[] { "lancer_1_biaoqiang", "lancer_2_biaoqiang", "lancer_3_biaoqiang" };
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Lancer.strPath.length; ++i) {
            Global.manager.load("animation/lancer/" + Lancer.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Lancer.curLevel = curLevel;
        Lancer.strPath = Lancer.strPaths[Lancer.curLevel - 1];
        Lancer.strRegion = Lancer.strRegions[Lancer.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Lancer.strPath.length; ++i) {
            Global.manager.unload("animation/lancer/" + Lancer.strPath[i] + ".xml");
        }
    }
    
    public void AddBullet(float n) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval || this.state != Constant.EnemyState.Attack || this.getCurFlash().GetPlayPercent() < n) {
            return;
        }
        n = this.getX() + 6.0f;
        final float n2 = this.getY() + 13.0f;
        final float x = this.player.getX();
        final float random = MathUtils.random(0.4f, 0.6f);
        final float width = this.player.getWidth();
        final float y = this.player.getY();
        final float random2 = MathUtils.random(0.5f, 0.8f);
        final float height = this.player.getHeight();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(MathUtils.random(0.85f, 1.15f) * this.yIncrease);
        bulletEnemy.GetSpeed().set(x + random * width - n, y + random2 * height - n2).nor().scl(this.bulletSpeed);
        bulletEnemy.setPosition(n, n2);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        bulletEnemy.isAddTrace = false;
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.bulletBaseRotation = 90.0f;
        this.bulletScale = 0.8f;
    }
    
    public float GetCollisionDamage() {
        return 50.0f;
    }
    
    @Override
    public float GetDrawHpPercent() {
        return 0.7f;
    }
    
    @Override
    public float GetDrawHpStartX() {
        if (this.state == Constant.EnemyState.Move_Away) {
            return 2.0f;
        }
        return 20.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[Lancer.strPath.length];
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/lancer/" + Lancer.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, true, false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[2].setAlphaTime(1.5f);
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[this.curIndex].play();
        this.curIndex = 0;
        this.regionBullet = Assets.atlasArcher.findRegion("biaoqiang" + Lancer.curLevel);
    }
    
    @Override
    public void SetProperty() {
        this.setJsonValue(Lancer.curLevel);
        this.flashPlayers[1].SetTimeScale(this.flashPlayers[1].GetEndTime() / this.attackInterval);
        switch (Lancer.curLevel) {
            default: {}
            case 1: {
                this.polygon = new Polygon(new float[] { 8.0f, 50.0f, 3.0f, 41.0f, 12.0f, 3.0f, 29.0f, 3.0f, 26.0f, 49.0f });
            }
            case 2: {
                this.polygon = new Polygon(new float[] { 4.0f, 63.0f, 9.0f, 6.0f, 34.0f, 8.0f, 31.0f, 64.0f, 17.0f, 73.0f });
            }
            case 3: {
                this.polygon = new Polygon(new float[] { 7.0f, 52.0f, 8.0f, 7.0f, 34.0f, 2.0f, 32.0f, 58.0f, 17.0f, 67.0f });
            }
        }
    }
    
    @Override
    public void act(final float n) {
        this.AddBullet(0.5f);
        this.CheckState(false, false, 30);
        super.act(n);
        if (this.state == Constant.EnemyState.Dead) {
            this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY() - 8.0f);
        }
        this.setPolygonPosition();
    }
    
    public void setPolygonPosition() {
        if (this.polygon != null) {
            switch (this.state) {
                case Dead: {
                    break;
                }
                default: {}
                case Move_To: {
                    this.polygon.setPosition(this.getX() + 22.0f, this.getY() + 2.0f);
                }
                case Attack: {
                    this.polygon.setPosition(this.getX() + 22.0f, this.getY() + 2.0f);
                }
                case Move_Away: {
                    this.polygon.setPosition(this.getX() + 22.0f, this.getY() + 2.0f);
                }
            }
        }
    }
}
