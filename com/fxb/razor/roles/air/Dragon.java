package com.fxb.razor.roles.air;

import com.badlogic.gdx.*;
import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.roles.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class Dragon extends Air
{
    private static final int PlayerNum = 1;
    private static int attack1 = 0;
    private static int attack2 = 0;
    private static int away1 = 0;
    private static int away2 = 0;
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
    private static final String strRoot = "animation/dragon/";
    private boolean isAddMine;
    
    static {
        Dragon.strPath1 = new String[] { "dragon1/dragon_total_1" };
        Dragon.strPath2 = new String[] { "dragon2/dragon_total_2" };
        Dragon.strPath3 = new String[] { "dragon3/dragon_total_3" };
        Dragon.strPaths = new String[][] { Dragon.strPath1, Dragon.strPath2, Dragon.strPath3 };
        Dragon.strAtlass = new String[] { "dragon1/dragon_1", "dragon2/dragon_2", "dragon3/dragon_3" };
        Dragon.strRegions = new String[] { "dragon_1_biaoqiang", "dragon_2_biaoqiang", "dragon_3_biaoqiang" };
        Dragon.run1 = 0;
        Dragon.run2 = 11;
        Dragon.attack1 = 12;
        Dragon.attack2 = 36;
        Dragon.away1 = 37;
        Dragon.away2 = 48;
    }
    
    public Dragon() {
        this.isAddMine = false;
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Dragon.strPath.length; ++i) {
            Global.manager.load("animation/dragon/" + Dragon.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Dragon.curLevel = curLevel;
        Dragon.strPath = Dragon.strPaths[Dragon.curLevel - 1];
        Dragon.strRegion = Dragon.strRegions[Dragon.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Dragon.strPath.length; ++i) {
            Global.manager.unload("animation/dragon/" + Dragon.strPath[i] + ".xml");
        }
    }
    
    public void CheckState() {
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
                if (this.getY() < 120.0f) {
                    this.isMoveUp = true;
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                else if (this.getTop() > 470.0f) {
                    this.isMoveUp = false;
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                if (this.flashPlayers[0].getFrameIndex() >= Dragon.run2) {
                    this.switchRun();
                }
                this.translate(this.speed.x - Constant.tranSpeed, this.speed.y);
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
                if (this.getY() < 120.0f) {
                    this.isMoveUp = true;
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                else if (this.getTop() > 470.0f) {
                    this.isMoveUp = false;
                    this.airMoveTime = MathUtils.random(2.0f, 3.0f);
                }
                this.translate(this.speed.x - Constant.tranSpeed, this.speed.y);
                if (this.flashPlayers[0].getFrameIndex() >= Dragon.attack2) {
                    this.switchAway();
                    return;
                }
                break;
            }
            case Move_Away: {
                this.speed.y = MathUtils.random(0.3f, 0.5f);
                this.translate(this.speed.x - Constant.tranSpeed, this.speed.y);
                if (this.flashPlayers[0].getFrameIndex() >= Dragon.away2) {
                    this.switchAway();
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
        this.currentHp = 0.0f;
        this.state = Constant.EnemyState.Dead;
        this.getCurFlash().stop();
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY() + 10.0f);
        this.deadHandle();
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
        this.flashPlayers = new FlashPlayer[1];
        this.scale = 0.28f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/dragon/" + Dragon.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, false, true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.curIndex = 0;
        this.flashPlayers[this.curIndex].play();
    }
    
    @Override
    public void SetProperty() {
        this.setJsonValue(Dragon.curLevel);
        (this.polygon = new Polygon(new float[] { 1.0f, 56.0f, 10.0f, 42.0f, 22.0f, 49.0f, 41.0f, 33.0f, 56.0f, 6.0f, 73.0f, 8.0f, 83.0f, 22.0f, 83.0f, 22.0f, 64.0f, 42.0f, 86.0f, 57.0f, 86.0f, 57.0f, 52.0f, 63.0f, 33.0f, 52.0f, 24.0f, 56.0f, 21.0f, 63.0f })).setPosition(this.getX(), this.getY());
        switch (Dragon.curLevel) {
            default: {}
            case 1: {
                this.polygon = new Polygon(new float[] { 3.0f, 73.0f, 15.0f, 54.0f, 27.0f, 62.0f, 54.0f, 43.0f, 73.0f, 11.0f, 95.0f, 15.0f, 104.0f, 39.0f, 84.0f, 60.0f, 116.0f, 75.0f, 62.0f, 85.0f, 44.0f, 69.0f, 25.0f, 80.0f });
            }
            case 2: {
                this.polygon = new Polygon(new float[] { 3.0f, 81.0f, 4.0f, 69.0f, 16.0f, 57.0f, 26.0f, 63.0f, 60.0f, 41.0f, 67.0f, 14.0f, 96.0f, 14.0f, 108.0f, 34.0f, 85.0f, 59.0f, 118.0f, 78.0f, 59.0f, 86.0f, 43.0f, 69.0f, 26.0f, 85.0f });
            }
            case 3: {
                this.polygon = new Polygon(new float[] { 4.0f, 83.0f, 10.0f, 68.0f, 27.0f, 69.0f, 63.0f, 38.0f, 72.0f, 22.0f, 96.0f, 21.0f, 110.0f, 44.0f, 85.0f, 66.0f, 114.0f, 88.0f, 56.0f, 90.0f, 43.0f, 76.0f, 25.0f, 91.0f });
            }
        }
    }
    
    @Override
    public void act(final float n) {
        this.CheckState();
        if (!this.isAddMine) {
            this.addMine();
        }
        super.act(n);
        this.setPolygonPosition();
    }
    
    public void addMine() {
        if (this.state != Constant.EnemyState.Attack || this.getCurFlash().getFrameIndex() < 24) {
            return;
        }
        Constant.EnemyType type = Constant.EnemyType.Mine1;
        if (this.type == Constant.EnemyType.Dragon1) {
            type = Constant.EnemyType.Mine1;
        }
        else if (this.type == Constant.EnemyType.Dragon2) {
            type = Constant.EnemyType.Mine2;
        }
        else if (this.type == Constant.EnemyType.Dragon3) {
            type = Constant.EnemyType.Mine3;
        }
        final StillEnemy stillEnemy = new StillEnemy();
        stillEnemy.Clear();
        stillEnemy.setType(type);
        stillEnemy.setPlayer(this.player);
        stillEnemy.setPosition(this.getX() + 20.0f, this.getY() - 10.0f);
        stillEnemy.addAction(Actions.moveTo(this.getX(), 85.0f, (this.getY() - 85.0f) / 800.0f, Interpolation.pow2In));
        Global.groupEnemy.addActor(stillEnemy);
        Global.arrEnemyCollision.add(stillEnemy);
        ++Global.totalEnemy;
        this.isAddMine = true;
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
                    case Dragon1: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() + 6.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() + 6.0f);
                                return;
                            }
                            case Move_Away: {
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() + 6.0f);
                                return;
                            }
                        }
                    }
                    case Dragon2: {
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
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() + 4.0f);
                                return;
                            }
                            case Move_Away: {
                                this.polygon.setPosition(this.getX() - 1.0f, this.getY() + 4.0f);
                                return;
                            }
                        }
                    }
                    case Dragon3: {
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
        this.flashPlayers[0].setFrameIndex(Dragon.attack1);
    }
    
    public void switchAway() {
        this.state = Constant.EnemyState.Move_Away;
        this.flashPlayers[0].setFrameIndex(Dragon.away1);
    }
    
    public void switchRun() {
        this.state = Constant.EnemyState.Move_To;
        this.flashPlayers[0].setFrameIndex(Dragon.run1);
        this.flashPlayers[0].SetTimeScale(1.0f);
    }
}
