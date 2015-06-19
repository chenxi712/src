package com.fxb.razor.roles.ground;

import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class Bomber extends Ground
{
    private static final int PlayerNum = 2;
    private static int curLevel = 0;
    private static boolean[] loops;
    private static String[] strPath;
    private static String[] strPath1;
    private static String[] strPath2;
    private static String[] strPath3;
    private static String[][] strPaths;
    private static final String strRoot = "animation/bomber/";
    
    static {
        Bomber.loops = new boolean[] { true, false };
        Bomber.strPath1 = new String[] { "bomber1/bomber_run_1", "bomber1/bomber_die_1" };
        Bomber.strPath2 = new String[] { "bomber2/bomber_run_2", "bomber2/bomber_die_2" };
        Bomber.strPath3 = new String[] { "bomber3/bomber_run_3", "bomber3/bomber_die_3" };
        Bomber.strPaths = new String[][] { Bomber.strPath1, Bomber.strPath2, Bomber.strPath3 };
    }
    
    private void CheckState() {
        switch (this.state) {
            case Move_To: {
                this.translate(this.speed.x - Constant.tranSpeed, this.speed.y);
                if (this.getX() - this.player.getRight() < 0.0f) {
                    this.Die();
                    this.player.beAttacked(this.attackDamage);
                    this.player.beCollision(1, 1.2f);
                    Global.isAllKill = false;
                    return;
                }
                break;
            }
            case Dead: {
                this.translate(-Constant.tranSpeed, 0.0f);
                if (this.flashPlayers[this.curIndex].isEnd()) {
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
    
    public static void loadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Bomber.strPath.length; ++i) {
            Global.manager.load("animation/bomber/" + Bomber.strPath[i] + ".xml", FlashElements.class);
        }
    }
    
    public static void setCreateLevel(final int curLevel) {
        Bomber.curLevel = curLevel;
        Bomber.strPath = Bomber.strPaths[Bomber.curLevel - 1];
    }
    
    public static void unloadElements(int i) {
        setCreateLevel(i);
        for (i = 0; i < Bomber.strPath.length; ++i) {
            Global.manager.unload("animation/bomber/" + Bomber.strPath[i] + ".xml");
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
        Effect.addSmoke(this.getX() + this.getWidth() / 2.0f, this.getY() + 10.0f, 0.8f);
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
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get("animation/bomber/" + Bomber.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, Bomber.loops[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[1].setAlphaTime(0.0f);
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[this.curIndex].play();
        this.curIndex = 0;
    }
    
    @Override
    public void SetProperty() {
        this.setJsonValue(Bomber.curLevel);
        switch (Bomber.curLevel) {
            default: {}
            case 1: {
                this.polygon = new Polygon(new float[] { 29.0f, 54.0f, 22.0f, 47.0f, 10.0f, 46.0f, 4.0f, 36.0f, 20.0f, 21.0f, 19.0f, 6.0f, 41.0f, 5.0f, 44.0f, 34.0f, 52.0f, 55.0f });
            }
            case 2: {
                this.polygon = new Polygon(new float[] { 4.0f, 50.0f, 16.0f, 6.0f, 87.0f, 8.0f, 91.0f, 25.0f, 73.0f, 61.0f, 56.0f, 58.0f, 53.0f, 28.0f, 44.0f, 26.0f, 40.0f, 51.0f, 24.0f, 59.0f });
            }
            case 3: {
                this.polygon = new Polygon(new float[] { 3.0f, 68.0f, 11.0f, 17.0f, 19.0f, 18.0f, 23.0f, 8.0f, 85.0f, 8.0f, 98.0f, 17.0f, 85.0f, 41.0f, 85.0f, 60.0f, 66.0f, 59.0f, 52.0f, 64.0f, 51.0f, 83.0f, 26.0f, 83.0f });
            }
        }
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
                switch (this.type) {
                    default: {
                        return;
                    }
                    case Bomber1: {
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
                        }
                    }
                    case Bomber2: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() - 3.0f, this.getY());
                                return;
                            }
                        }
                    }
                    case Bomber3: {
                        switch (this.state) {
                            case Dead: {
                                break Label_0007;
                            }
                            default: {
                                return;
                            }
                            case Move_To: {
                                this.polygon.setPosition(this.getX() + 10.0f, this.getY());
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
