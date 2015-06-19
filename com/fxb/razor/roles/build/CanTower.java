package com.fxb.razor.roles.build;

import com.fxb.razor.objects.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.math.*;

public class CanTower extends Build
{
    private static final int PlayerNum = 2;
    private static int curLevel = 0;
    private static String[] strAtlass;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[] strPath2;
    private static String[] strPath3;
    private static String[][] strPaths;
    private static final String strRoot = "animation/cannontower/";
    private float pauseTime;
    private float singleInterval;
    
    static {
        CanTower.strPath1 = new String[] { "cannontower1/cannontower_attack_1", "cannontower1/cannontower_die_1" };
        CanTower.strPath2 = new String[] { "cannontower2/cannontower_attack_2", "cannontower2/cannontower_die_2" };
        CanTower.strPath3 = new String[] { "cannontower3/cannontower_attack_3", "cannontower3/cannontower_die_3" };
        CanTower.strPaths = new String[][] { CanTower.strPath1, CanTower.strPath2, CanTower.strPath3 };
        CanTower.strAtlass = new String[] { "cannontower1/cannontower_1", "cannontower2/cannontower_2", "cannontower3/cannontower_3" };
    }
    
    public CanTower() {
        this.pauseTime = 0.5f;
        this.singleInterval = 0.1f;
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < CanTower.strPath.length; ++i) {
            Global.manager.load("animation/cannontower/" + CanTower.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        CanTower.curLevel = curLevel;
        CanTower.strPath = CanTower.strPaths[CanTower.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < CanTower.strPath.length; ++i) {
            Global.manager.unload("animation/cannontower/" + CanTower.strPath[i] + ".xml");
        }
    }
    
    public boolean AddBullet1(float x) {
        if (this.state != Constant.EnemyState.Attack || this.currentTime - this.lastAttackTime <= this.singleInterval) {
            return false;
        }
        x = this.getX();
        final float dx = this.dx;
        final float y = this.getY();
        final float height = this.getHeight();
        final float dy = this.dy;
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth() * this.bulletScale, this.regionBullet.getRegionHeight() * this.bulletScale);
        bulletEnemy.setDamage(this.attackDamage);
        bulletEnemy.setYIncrease(MathUtils.random(0.95f, 1.05f) * this.yIncrease * Interpolation.linear.apply(1.0f, 3.0f, (800.0f - this.getX()) / 800.0f));
        bulletEnemy.setOrigin(0.0f, 0.0f);
        final float n = MathUtils.random(178, 182);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(n), MathUtils.sinDeg(n)).nor().scl(this.bulletSpeed);
        bulletEnemy.setPosition(x + 15.0f + dx, y + height * 0.8f + dy);
        bulletEnemy.isAddTrace = false;
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
        return true;
    }
    
    @Override
    public void Die() {
        super.Die();
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY() + 10.0f, 0.7f);
        SoundHandle.playForBomb();
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
        this.scale *= 0.75f;
        this.flashPlayers = new FlashPlayer[2];
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/cannontower/" + CanTower.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[1].setAlphaTime(1.5f);
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].pause();
        this.curIndex = 0;
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-1");
    }
    
    @Override
    public void SetProperty() {
        this.setJsonValue(CanTower.curLevel);
        this.flashPlayers[0].SetTimeScale(this.flashPlayers[0].GetEndTime() / this.attackInterval);
        switch (CanTower.curLevel) {
            default: {}
            case 1: {
                this.polygon = new Polygon(new float[] { 6.0f, 112.0f, 52.0f, 104.0f, 50.0f, 95.0f, 45.0f, 92.0f, 46.0f, 86.0f, 57.0f, 79.0f, 50.0f, 5.0f, 107.0f, 4.0f, 106.0f, 119.0f, 113.0f, 130.0f, 103.0f, 137.0f, 88.0f, 125.0f, 5.0f, 122.0f });
            }
            case 2: {
                this.polygon = new Polygon(new float[] { 3.0f, 135.0f, 3.0f, 115.0f, 46.0f, 115.0f, 46.0f, 105.0f, 38.0f, 102.0f, 48.0f, 86.0f, 41.0f, 6.0f, 102.0f, 5.0f, 95.0f, 150.0f, 80.0f, 150.0f, 71.0f, 138.0f });
            }
            case 3: {
                this.polygon = new Polygon(new float[] { 5.0f, 137.0f, 6.0f, 117.0f, 41.0f, 110.0f, 41.0f, 104.0f, 35.0f, 98.0f, 47.0f, 86.0f, 41.0f, 5.0f, 99.0f, 5.0f, 96.0f, 149.0f, 78.0f, 150.0f, 73.0f, 142.0f });
            }
        }
    }
    
    @Override
    public void act(final float n) {
        this.addBullet();
        this.CheckState(false);
        if (this.state == Constant.EnemyState.Attack && this.getCurFlash().isPlaying() && this.getCurFlash().GetPlayPercent() > 0.95f) {
            this.getCurFlash().pause();
            this.pauseTime = 0.3f;
        }
        else if (this.state == Constant.EnemyState.Attack && !this.getCurFlash().isPlaying()) {
            this.pauseTime -= n;
            if (this.pauseTime < 0.0f) {
                this.getCurFlash().play();
            }
        }
        super.act(n);
        this.setPolygonPosition();
    }
    
    public void addBullet() {
        float n = 0.2f;
        final float n2 = 800.0f - this.getX();
        if (n2 > 100.0f) {
            n = 0.2f - 0.25f * n2 / 800.0f;
        }
        final int frameIndex = this.getCurFlash().getFrameIndex();
        switch (this.type) {
            case CanTower1: {
                if (frameIndex == 2 || frameIndex == 5 || frameIndex == 8) {
                    this.AddBullet1(n);
                    return;
                }
                break;
            }
            case CanTower2: {
                if (frameIndex == 4 || frameIndex == 8) {
                    this.AddBullet1(n);
                    return;
                }
                break;
            }
            case CanTower3: {
                if (frameIndex == 4) {
                    this.AddBullet1(n);
                    return;
                }
                break;
            }
        }
    }
    
    public void setPolygonPosition() {
        Label_0007: {
            if (this.polygon != null) {
                switch (this.type) {
                    default: {
                        return;
                    }
                    case CanTower1: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 4.0f, this.getY() - 2.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() - 4.0f, this.getY() - 2.0f);
                                return;
                            }
                        }
                    }
                    case CanTower2: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX(), this.getY() - 2.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX(), this.getY() - 2.0f);
                                return;
                            }
                        }
                    }
                    case CanTower3: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX(), this.getY() - 2.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX(), this.getY() - 2.0f);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
