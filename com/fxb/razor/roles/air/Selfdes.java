package com.fxb.razor.roles.air;

import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Selfdes extends Air
{
    private static final int PlayerNum = 1;
    private static int curLevel = 0;
    private static String[] strAtlass;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[] strPath2;
    private static String[] strPath3;
    private static String[][] strPaths;
    private static final String strRoot = "animation/selfdes/";
    
    static {
        Selfdes.strPath1 = new String[] { "selfdes1/selfdes_run_1" };
        Selfdes.strPath2 = new String[] { "selfdes2/selfdes_run_2" };
        Selfdes.strPath3 = new String[] { "selfdes3/selfdes_run_3" };
        Selfdes.strPaths = new String[][] { Selfdes.strPath1, Selfdes.strPath2, Selfdes.strPath3 };
        Selfdes.strAtlass = new String[] { "selfdes1/selfdes_1", "selfdes2/selfdes_2", "selfdes3/selfdes_3" };
    }
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Selfdes.strPath.length; ++i) {
            Global.manager.load("animation/selfdes/" + Selfdes.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Selfdes.curLevel = curLevel;
        Selfdes.strPath = Selfdes.strPaths[Selfdes.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Selfdes.strPath.length; ++i) {
            Global.manager.unload("animation/selfdes/" + Selfdes.strPath[i] + ".xml");
        }
    }
    
    public void CheckState() {
        switch (this.state) {
            case Move_To: {
                this.speed.y = 2.8f * ((this.player.getY() + this.player.getHeight() / 2.0f - this.getY()) * 2.0f / 480.0f);
                this.translate(this.speed.x - Constant.tranSpeed, this.speed.y);
                if (this.player.getRight() - this.getX() > 20.0f) {
                    this.Die();
                    this.player.beAttacked(this.attackDamage);
                    this.player.beCollision(1, 1.2f);
                    Global.isAllKill = false;
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
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY() + 10.0f, 0.7f);
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
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/selfdes/" + Selfdes.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, false, true })[i])).setScale(this.scale);
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
        this.yIncrease = 0.04f;
        this.bulletBaseRotation = 0.0f;
        this.setJsonValue(Selfdes.curLevel);
        switch (Selfdes.curLevel) {
            case 1: {
                this.polygon = new Polygon(new float[] { 4.0f, 65.0f, 7.0f, 36.0f, 25.0f, 38.0f, 26.0f, 48.0f, 36.0f, 48.0f, 41.0f, 14.0f, 51.0f, 4.0f, 72.0f, 4.0f, 84.0f, 12.0f, 78.0f, 58.0f, 110.0f, 72.0f, 37.0f, 73.0f, 17.0f, 75.0f });
                break;
            }
            case 2: {
                this.polygon = new Polygon(new float[] { 6.0f, 64.0f, 6.0f, 42.0f, 18.0f, 36.0f, 36.0f, 42.0f, 42.0f, 36.0f, 45.0f, 10.0f, 75.0f, 1.0f, 90.0f, 14.0f, 84.0f, 53.0f, 114.0f, 66.0f, 40.0f, 77.0f, 16.0f, 76.0f });
                break;
            }
            case 3: {
                this.polygon = new Polygon(new float[] { 7.0f, 73.0f, 16.0f, 47.0f, 41.0f, 52.0f, 45.0f, 48.0f, 40.0f, 11.0f, 53.0f, 17.0f, 90.0f, 19.0f, 95.0f, 47.0f, 79.0f, 67.0f, 118.0f, 83.0f, 56.0f, 89.0f, 14.0f, 86.0f });
                break;
            }
        }
        this.polygon.setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        this.CheckState();
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
                    case Selfdes1: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() + 1.0f, this.getY() + 3.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX() + 1.0f, this.getY());
                                return;
                            }
                        }
                    }
                    case Selfdes2: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 3.0f, this.getY() + 5.0f);
                                return;
                            }
                            case Attack: {
                                this.polygon.setPosition(this.getX(), this.getY() - 2.0f);
                                return;
                            }
                        }
                    }
                    case Selfdes3: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 3.0f, this.getY() + 3.0f);
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
