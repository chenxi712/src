package com.fxb.razor.roles.ground;

import com.fxb.razor.objects.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Flamecar extends Ground
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
        Flamecar.strPath1 = new String[] { "flamecar/flamecar_run_1", "flamecar/flamecar_attack_1" };
        Flamecar.strPaths = new String[][] { Flamecar.strPath1 };
        Flamecar.strAtlass = new String[] { "flamecar/flamecar_1" };
        Flamecar.strRegions = new String[] { "flamecar_1_shitou" };
    }
    
    public Flamecar() {
        this.pauseTime = 0.0f;
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Flamecar.strPath.length; ++i) {
            Global.manager.load("animation/machine/" + Flamecar.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Flamecar.curLevel = curLevel;
        Flamecar.strPath = Flamecar.strPaths[Flamecar.curLevel - 1];
        Flamecar.strRegion = Flamecar.strRegions[Flamecar.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Flamecar.strPath.length; ++i) {
            Global.manager.unload("animation/machine/" + Flamecar.strPath[i] + ".xml");
        }
    }
    
    public void AddBullet(float x, float width) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval || this.state != Constant.EnemyState.Attack || this.getCurFlash().getFrameIndex() != 7) {
            return;
        }
        x = this.getX();
        width = this.getWidth();
        final float y = this.getY();
        final float height = this.getHeight();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(MathUtils.random(0.95f, 1.05f) * this.yIncrease * Interpolation.linear.apply(1.0f, 2.2f, (700.0f - this.getX()) / 700.0f));
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(125.0f), MathUtils.sinDeg(125.0f)).scl(this.bulletSpeed);
        bulletEnemy.setPosition(x + width * 0.5f - 8.0f, y + height * 0.8f);
        bulletEnemy.setOrigin(bulletEnemy.getWidth() / 2.0f, bulletEnemy.getHeight() / 2.0f);
        bulletEnemy.isAddTrace = false;
        bulletEnemy.bulletType = Constant.BulletEnemyType.Machine;
        bulletEnemy.addAction(Actions.forever(Actions.rotateBy(360.0f, 0.85f)));
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
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY() + 10.0f, 0.8f);
        this.remove();
        SoundHandle.playForBomb();
    }
    
    public float GetBulletStartX() {
        return this.getRight() - this.getWidth() * 0.5f;
    }
    
    public float GetBulletStartY() {
        return this.getTop() - this.getHeight() * 0.2f;
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
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/machine/" + Flamecar.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, true })[i])).setScale(this.scale);
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
        this.setJsonValue(Flamecar.curLevel);
        this.regionBullet = Global.manager.get("game/archer.pack", TextureAtlas.class).findRegion(Flamecar.strRegion);
        this.attackInterval = 1.0f;
        this.polygon = new Polygon(new float[] { 3.0f, 46.0f, 7.0f, 23.0f, 16.0f, 19.0f, 22.0f, 5.0f, 97.0f, 4.0f, 105.0f, 16.0f, 106.0f, 45.0f, 111.0f, 54.0f, 97.0f, 96.0f, 59.0f, 86.0f, 64.0f, 66.0f, 49.0f, 69.0f, 47.0f, 60.0f, 24.0f, 60.0f, 20.0f, 52.0f });
    }
    
    @Override
    public void act(final float n) {
        this.AddBullet(0.5f, 0.35f);
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
                    this.polygon.setPosition(this.getX() - 2.0f, this.getY() + 2.0f);
                }
                case Attack: {
                    this.polygon.setPosition(this.getX() + 3.0f, this.getY());
                }
            }
        }
    }
}
