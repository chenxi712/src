package com.gleejet.sun.roles.ground;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.objects.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Freezecar extends Ground
{
    private static final int PlayerNum = 2;
    private static int curLevel = 0;
    private static String[] strAtlass;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[][] strPaths;
    private static String strRegion;
    private static String[] strRegions;
    private static final String strRoot = "animation/machine/";
    float pauseTime;
    
    static {
        Freezecar.strPath1 = new String[] { "freezecar/freezecar_run_1", "freezecar/freezecar_attack_1" };
        Freezecar.strPaths = new String[][] { Freezecar.strPath1 };
        Freezecar.strAtlass = new String[] { "freezecar/freezecar_1" };
        Freezecar.strRegions = new String[] { "freezecar_1_shitou" };
    }
    
    public Freezecar() {
        this.pauseTime = 0.0f;
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Freezecar.strPath.length; ++i) {
            Global.manager.load("animation/machine/" + Freezecar.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Freezecar.curLevel = curLevel;
        Freezecar.strPath = Freezecar.strPaths[Freezecar.curLevel - 1];
        Freezecar.strRegion = Freezecar.strRegions[Freezecar.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Freezecar.strPath.length; ++i) {
            Global.manager.unload("animation/machine/" + Freezecar.strPath[i] + ".xml");
        }
    }
    
    public void AddBullet(float x) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval || this.state != Constant.EnemyState.Attack || this.getCurFlash().getFrameIndex() != 16) {
            return;
        }
        x = this.getX();
        final float n = MathUtils.random(5, 8);
        final float y = this.getY();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(0.0f);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        bulletEnemy.GetSpeed().set(-this.bulletSpeed, 0.0f);
        bulletEnemy.setPosition(x + n, y + 5.0f);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        bulletEnemy.isAddTrace = false;
        bulletEnemy.bulletType = Constant.BulletEnemyType.Freezecar;
        bulletEnemy.addAction(Actions.forever(Actions.rotateBy(360.0f, 1.0f)));
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.bulletBaseRotation = 0.0f;
    }
    
    @Override
    public void Die() {
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY() + 10.0f, 0.9f);
        this.remove();
        SoundHandle.playForBomb();
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
        this.flashPlayers = new FlashPlayer[2];
        this.scale *= 0.75f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/machine/" + Freezecar.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[this.curIndex].play();
        this.curIndex = 0;
    }
    
    @Override
    public void SetProperty() {
        this.setJsonValue(Freezecar.curLevel);
        this.regionBullet = Global.manager.get("game/archer.pack", TextureAtlas.class).findRegion(Freezecar.strRegion);
        this.yIncrease = 0.0f;
        this.bulletBaseRotation = 0.0f;
        this.attackInterval = 0.95f;
        this.polygon = new Polygon(new float[] { 5.0f, 41.0f, 17.0f, 27.0f, 26.0f, 7.0f, 102.0f, 6.0f, 119.0f, 33.0f, 128.0f, 45.0f, 126.0f, 69.0f, 102.0f, 71.0f, 97.0f, 80.0f, 79.0f, 82.0f, 77.0f, 67.0f, 51.0f, 67.0f, 40.0f, 54.0f, 24.0f, 54.0f, 17.0f, 43.0f });
    }
    
    @Override
    public void act(final float n) {
        this.CheckState(false, false, 20);
        super.act(n);
        this.AddBullet(0.55f);
        this.setPolygonPosition();
        if (this.state == Constant.EnemyState.Attack && this.getCurFlash().isPlaying() && this.getCurFlash().GetPlayPercent() > 0.95f) {
            this.getCurFlash().pause();
            this.pauseTime = this.attackInterval - this.getCurFlash().GetEndTime();
        }
        else if (this.state == Constant.EnemyState.Attack && !this.getCurFlash().isPlaying()) {
            this.pauseTime -= n;
            if (this.pauseTime <= 0.0f) {
                this.getCurFlash().play();
            }
        }
    }
    
    public void setPolygonPosition() {
        if (this.polygon != null) {
            final float x = Global.pAdd.x;
            final float y = Global.pAdd.y;
            switch (this.state) {
                case Dead: {
                    break;
                }
                default: {}
                case Move_To: {
                    this.polygon.setPosition(this.getX() - 2.0f, this.getY() + 2.0f);
                }
                case Attack: {
                    this.polygon.setPosition(this.getX() + 3.0f, this.getY());
                }
            }
        }
    }
}
