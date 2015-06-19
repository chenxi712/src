package com.fxb.razor.roles.air;

import com.fxb.razor.objects.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.*;
import com.badlogic.gdx.math.*;

public class Airforce extends Air
{
    private static final int PlayerNum = 2;
    private static int attack1 = 0;
    private static int attack2 = 0;
    private static int curLevel = 0;
    private static int run1 = 0;
    private static int run2 = 0;
    private static String[] strAtlass;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[] strPath2;
    private static String[] strPath3;
    private static String[][] strPaths;
    private static String strRegion;
    private static String[] strRegions;
    private static final String strRoot = "animation/aircraft/";
    float startDeadTime;
    
    static {
        Airforce.strPath1 = new String[] { "aircraft1/aircraft_total_1", "aircraft1/aircraft_die_1" };
        Airforce.strPath2 = new String[] { "aircraft2/aircraft_total_2", "aircraft2/aircraft_die_2" };
        Airforce.strPath3 = new String[] { "aircraft3/aircraft_total_3", "aircraft3/aircraft_die_3" };
        Airforce.strPaths = new String[][] { Airforce.strPath1, Airforce.strPath2, Airforce.strPath3 };
        Airforce.strAtlass = new String[] { "aircraft1/aircraft_1", "aircraft2/aircraft_2", "aircraft3/aircraft_3" };
        Airforce.strRegions = new String[] { "aircraft_1_biaoqiang", "aircraft_2_biaoqiang", "aircraft_3_biaoqiang" };
        Airforce.run1 = 0;
        Airforce.run2 = 11;
        Airforce.attack1 = 12;
        Airforce.attack2 = 36;
    }
    
    public Airforce() {
        this.startDeadTime = 0.0f;
    }
    
    private boolean isAttackValid() {
        final boolean b = false;
        if (this.state != Constant.EnemyState.Attack) {
            final boolean b2 = b;
            if (this.state != Constant.EnemyState.Move_Away) {
                return b2;
            }
        }
        boolean b2 = b;
        if (this.getX() > 250.0f) {
            b2 = true;
        }
        return b2;
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Airforce.strPath.length; ++i) {
            Global.manager.load("animation/aircraft/" + Airforce.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Airforce.curLevel = curLevel;
        Airforce.strPath = Airforce.strPaths[Airforce.curLevel - 1];
        Airforce.strRegion = Airforce.strRegions[Airforce.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Airforce.strPath.length; ++i) {
            Global.manager.unload("animation/aircraft/" + Airforce.strPath[i] + ".xml");
        }
    }
    
    public void AddBullet(float yIncrease) {
        if (this.currentTime - this.lastAttackTime < this.attackInterval || this.getCurFlash().GetPlayPercent() < yIncrease) {
            return;
        }
        final float x = this.getX();
        final float y = this.getY();
        this.player.getX();
        MathUtils.random(0.4f, 0.6f);
        this.player.getWidth();
        this.player.getY();
        MathUtils.random(0.3f, 0.7f);
        this.player.getHeight();
        final BulletEnemy bulletEnemy = Pools.obtain(BulletEnemy.class);
        bulletEnemy.Clear();
        bulletEnemy.setRegion(this.regionBullet);
        bulletEnemy.setSize(this.regionBullet.getRegionWidth(), this.regionBullet.getRegionHeight());
        bulletEnemy.setDamage(this.attackDamage);
        final float n = yIncrease = MathUtils.random(0.9f, 1.1f) * this.yIncrease;
        if (this.getY() > 200.0f) {
            yIncrease = n + (this.getY() - 200.0f) / 100.0f * n;
        }
        bulletEnemy.setYIncrease(yIncrease);
        bulletEnemy.setOrigin(0.0f, 0.0f);
        bulletEnemy.GetSpeed().set(MathUtils.cosDeg(190.0f), MathUtils.sinDeg(190.0f)).nor().scl(this.bulletSpeed);
        bulletEnemy.setPosition(x + 65.0f, y + 32.0f);
        bulletEnemy.isAddTrace = true;
        bulletEnemy.setTracePointNum(11);
        Global.groupBulletEnemy.addActor(bulletEnemy);
        this.lastAttackTime = this.currentTime;
    }
    
    protected void CheckState(final boolean b, final boolean b2) {
        switch (this.state) {
            case Move_To: {
                this.airMoveTime -= Gdx.graphics.getDeltaTime();
                final Vector2 speed = this.speed;
                final float random = MathUtils.random(0.2f, 0.5f);
                int n;
                if (this.isMoveUp) {
                    n = 1;
                }
                else {
                    n = -1;
                }
                speed.y = n * random;
                if (this.airMoveTime < 0.0f) {
                    this.isMoveUp = MathUtils.randomBoolean();
                    this.airMoveTime = MathUtils.random(2.0f, 4.0f);
                }
                if (this.getY() < 240.0f) {
                    this.isMoveUp = true;
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                else if (this.getTop() > 470.0f) {
                    this.isMoveUp = false;
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                if (this.flashPlayers[0].getFrameIndex() >= Airforce.run2) {
                    this.switchRun();
                }
                this.translate(this.speed.x * 2.0f - Constant.tranSpeed, this.speed.y);
                if (this.GetDisToPlayer() <= this.maxAttackDistance * 50.0f) {
                    this.switchAttack();
                    return;
                }
                break;
            }
            case Attack: {
                final Vector2 speed2 = this.speed;
                final float random2 = MathUtils.random(0.25f, 0.35f);
                int n2;
                if (this.isMoveUp) {
                    n2 = 1;
                }
                else {
                    n2 = -1;
                }
                speed2.y = n2 * random2;
                this.airMoveTime -= Gdx.graphics.getDeltaTime();
                if (this.airMoveTime < 0.0f) {
                    this.isMoveUp = MathUtils.randomBoolean();
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                if (this.getY() < 190.0f) {
                    this.isMoveUp = true;
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                else if (this.getTop() > 470.0f) {
                    this.isMoveUp = false;
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                if (this.flashPlayers[0].getFrameIndex() >= Airforce.attack2) {
                    this.switchAttack();
                }
                this.translate(this.speed.x - Constant.tranSpeed, this.speed.y);
                if (b2 && this.getX() < 240.0f) {
                    this.state = Constant.EnemyState.Move_Away;
                    return;
                }
                break;
            }
            case Move_Away: {
                this.speed.y = MathUtils.random(0.3f, 0.5f);
                this.translate(this.speed.x * 0.2f - Constant.tranSpeed, this.speed.y);
                if (this.flashPlayers[0].getFrameIndex() >= Airforce.run2) {
                    this.switchAway();
                    return;
                }
                break;
            }
            case Dead: {
                if (!this.isStartDead) {
                    this.isStartDead = true;
                    this.speed.y = 1.5f;
                    this.startDeadTime = this.currentTime;
                    if (this.getCurFlash().GetEndTime() / ((this.getY() - 80.0f) / this.speed.y / 25.0f) < 1.0f) {}
                }
                else if (this.getCurFlash().GetPlayPercent() > 0.25f) {
                    if (this.getY() > 90.0f) {
                        final Vector2 speed3 = this.speed;
                        speed3.y += 0.35f;
                        this.getCurFlash().pause();
                    }
                    else {
                        this.speed.y = 0.0f;
                        this.getCurFlash().play();
                    }
                }
                this.translate(-Constant.tranSpeed, -this.speed.y);
                if (this.flashPlayers[this.curIndex].isEnd() || this.currentTime - this.startDeadTime > 2.5f) {
                    if (!this.flashPlayers[this.curIndex].isStop()) {
                        this.flashPlayers[this.curIndex].stop();
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
        this.bulletBaseRotation = 0.0f;
    }
    
    @Override
    public void Die() {
        super.Die();
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY() + 10.0f);
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
        return 5.0f;
    }
    
    @Override
    public void InitFlash() {
        this.flashPlayers = new FlashPlayer[2];
        this.scale = 0.25f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/aircraft/" + Airforce.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[1].setAlphaTime(1.5f);
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.curIndex = 0;
        this.flashPlayers[this.curIndex].play();
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-1");
    }
    
    @Override
    public void MoveAway() {
        this.state = Constant.EnemyState.Move_Away;
        this.flashPlayers[this.curIndex].stop();
        this.curIndex = 0;
        this.flashPlayers[this.curIndex].rePlay();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void SetProperty() {
        this.setJsonValue(Airforce.curLevel);
        final float[] array2;
        final float[] array = array2 = new float[24];
        array2[0] = 1.0f;
        array2[1] = 77.0f;
        array2[2] = 13.0f;
        array2[3] = 58.0f;
        array2[4] = 25.0f;
        array2[5] = 64.0f;
        array2[6] = 55.0f;
        array2[7] = 43.0f;
        array2[8] = 60.0f;
        array2[9] = 11.0f;
        array2[10] = 88.0f;
        array2[11] = 4.0f;
        array2[12] = 103.0f;
        array2[13] = 22.0f;
        array2[14] = 84.0f;
        array2[15] = 62.0f;
        array2[16] = 119.0f;
        array2[17] = 81.0f;
        array2[18] = 58.0f;
        array2[19] = 83.0f;
        array2[20] = 43.0f;
        array2[21] = 72.0f;
        array2[22] = 27.0f;
        array2[23] = 85.0f;
        final float[] array4;
        final float[] array3 = array4 = new float[24];
        array4[0] = 4.0f;
        array4[1] = 83.0f;
        array4[2] = 5.0f;
        array4[3] = 71.0f;
        array4[4] = 17.0f;
        array4[5] = 61.0f;
        array4[6] = 27.0f;
        array4[7] = 67.0f;
        array4[8] = 59.0f;
        array4[9] = 48.0f;
        array4[10] = 67.0f;
        array4[11] = 15.0f;
        array4[12] = 99.0f;
        array4[13] = 11.0f;
        array4[14] = 110.0f;
        array4[15] = 38.0f;
        array4[16] = 86.0f;
        array4[17] = 66.0f;
        array4[18] = 120.0f;
        array4[19] = 85.0f;
        array4[20] = 64.0f;
        array4[21] = 90.0f;
        array4[22] = 28.0f;
        array4[23] = 89.0f;
        final float[] array6;
        final float[] array5 = array6 = new float[24];
        array6[0] = 5.0f;
        array6[1] = 85.0f;
        array6[2] = 13.0f;
        array6[3] = 66.0f;
        array6[4] = 29.0f;
        array6[5] = 71.0f;
        array6[6] = 57.0f;
        array6[7] = 51.0f;
        array6[8] = 63.0f;
        array6[9] = 20.0f;
        array6[10] = 95.0f;
        array6[11] = 12.0f;
        array6[12] = 103.0f;
        array6[13] = 47.0f;
        array6[14] = 86.0f;
        array6[15] = 69.0f;
        array6[16] = 129.0f;
        array6[17] = 91.0f;
        array6[18] = 63.0f;
        array6[19] = 95.0f;
        array6[20] = 45.0f;
        array6[21] = 80.0f;
        array6[22] = 26.0f;
        array6[23] = 92.0f;
        MeshMethod.scaleVertices(array, 0.89285713f);
        MeshMethod.scaleVertices(array3, 0.89285713f);
        MeshMethod.scaleVertices(array5, 0.89285713f);
        switch (Airforce.curLevel) {
            default: {}
            case 1: {
                this.polygon = new Polygon(array);
            }
            case 2: {
                this.polygon = new Polygon(array3);
            }
            case 3: {
                this.polygon = new Polygon(array5);
            }
        }
    }
    
    @Override
    public void act(final float n) {
        if (this.state == Constant.EnemyState.Attack) {
            this.AddBullet(0.43243244f);
        }
        this.CheckState(false, true);
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
                    case Airforce1: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX(), this.getY());
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX(), this.getY());
                                return;
                            }
                            case Move_Away: {
                                this.polygon.setPosition(this.getX(), this.getY());
                                return;
                            }
                        }
                    }
                    case Airforce2: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 5.0f, this.getY() + 2.0f - 3.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() - 3.0f, this.getY() - 2.0f);
                                return;
                            }
                            case Move_Away: {
                                this.polygon.setPosition(this.getX() - 4.0f, this.getY() + 2.0f);
                                return;
                            }
                        }
                    }
                    case Airforce3: {
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
                                this.polygon.setPosition(this.getX(), this.getY());
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
    
    public void switchAttack() {
        this.state = Constant.EnemyState.Attack;
        this.flashPlayers[0].setFrameIndex(Airforce.attack1);
        this.flashPlayers[0].SetTimeScale((Airforce.attack2 - Airforce.run2) * 0.04f / this.attackInterval);
    }
    
    public void switchAway() {
        this.state = Constant.EnemyState.Move_Away;
        this.flashPlayers[0].setFrameIndex(Airforce.run1);
        this.flashPlayers[0].SetTimeScale(1.0f);
    }
    
    public void switchRun() {
        this.state = Constant.EnemyState.Move_To;
        this.flashPlayers[0].setFrameIndex(Airforce.run1);
        this.flashPlayers[0].SetTimeScale(1.0f);
    }
}
