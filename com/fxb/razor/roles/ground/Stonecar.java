package com.fxb.razor.roles.ground;

import com.fxb.razor.objects.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Stonecar extends Ground
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
        Stonecar.strPath1 = new String[] { "stonecar/stonecar_run_1", "stonecar/stonecar_attack_1" };
        Stonecar.strPaths = new String[][] { Stonecar.strPath1 };
        Stonecar.strAtlass = new String[] { "stonecar/stonecar_1" };
        Stonecar.strRegions = new String[] { "stonecar_1_shitou" };
    }
    
    public Stonecar() {
        this.pauseTime = 0.0f;
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Stonecar.strPath.length; ++i) {
            Global.manager.load("animation/machine/" + Stonecar.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Stonecar.curLevel = curLevel;
        Stonecar.strPath = Stonecar.strPaths[Stonecar.curLevel - 1];
        Stonecar.strRegion = Stonecar.strRegions[Stonecar.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Stonecar.strPath.length; ++i) {
            Global.manager.unload("animation/machine/" + Stonecar.strPath[i] + ".xml");
        }
    }
    
    public void AddBullet() {
        if (this.currentTime - this.lastAttackTime < this.attackInterval || this.state != Constant.EnemyState.Attack || this.getCurFlash().getFrameIndex() != 8) {
            return;
        }
        final float x = this.getX();
        final float width = this.getWidth();
        final float dx = this.dx;
        final float y = this.getY();
        final float height = this.getHeight();
        final float dy = this.dy;
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(MathUtils.random(0.95f, 1.05f) * this.yIncrease * Interpolation.linear.apply(1.0f, 2.2f, (700.0f - this.getX()) / 700.0f));
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(135.0f), MathUtils.sinDeg(135.0f)).scl(this.bulletSpeed);
        bulletEnemy.setPosition(x + width * 0.7f + dx, y + height * 0.8f + dy);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        bulletEnemy.isAddTrace = false;
        bulletEnemy.bulletType = Constant.BulletEnemyType.Machine;
        bulletEnemy.addAction(Actions.forever(Actions.rotateBy(360.0f, 0.9f)));
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
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/machine/" + Stonecar.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, true })[i])).setScale(this.scale);
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
        this.setJsonValue(Stonecar.curLevel);
        this.regionBullet = Global.manager.get("game/archer.pack", TextureAtlas.class).findRegion(Stonecar.strRegion);
        this.attackInterval = 0.95f;
        this.polygon = new Polygon(new float[] { 2.0f, 48.0f, 6.0f, 28.0f, 15.0f, 21.0f, 19.0f, 6.0f, 96.0f, 4.0f, 105.0f, 17.0f, 118.0f, 33.0f, 119.0f, 51.0f, 99.0f, 56.0f, 93.0f, 84.0f, 86.0f, 82.0f, 83.0f, 65.0f, 54.0f, 68.0f, 43.0f, 41.0f, 36.0f, 61.0f, 22.0f, 61.0f, 19.0f, 52.0f });
    }
    
    @Override
    public void act(final float n) {
        this.AddBullet();
        this.CheckState(false, false, 20);
        super.act(n);
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
                    this.polygon.setPosition(this.getX() + 1.0f, this.getY() + 2.0f);
                }
                case Attack: {
                    this.polygon.setPosition(this.getX() + 8.0f + x, this.getY() + 2.0f + y);
                }
            }
        }
    }
}
