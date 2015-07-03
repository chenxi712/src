package com.gleejet.sun.roles;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.utils.Debug;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;

public class Turtle extends Actor
{
    private static int PlayerNum;
    private static int beattack1;
    private static int beattack2;
    private static int die1;
    private static int die2;
    private static int run1;
    private static int run2;
    private static String strAtlas;
    private static String[] strPath;
    private static String strRoot;
    private int curIndex;
    private float curTime;
    private FlashPlayer[] flashPlayers;
    private Vector2 pos;
    private float scale;
    private Constant.TurtleState state;
    
    static {
        Turtle.PlayerNum = 1;
        Turtle.strRoot = "turtle/xml/";
        Turtle.strPath = new String[] { "turtle_total_1" };
        Turtle.strAtlas = "turtle/pack/turtle.pack";
        Turtle.run1 = 0;
        Turtle.run2 = 39;
        Turtle.beattack1 = 40;
        Turtle.beattack2 = 57;
        Turtle.die1 = 58;
        Turtle.die2 = 88;
    }
    
    public Turtle() {
        this.state = Constant.TurtleState.Move;
        this.scale = 0.35f;
        this.pos = new Vector2();
        this.curIndex = 0;
        this.curTime = 0.0f;
        this.flashPlayers = new FlashPlayer[Turtle.PlayerNum];
        this.scale = 0.33f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Turtle.strRoot + Turtle.strPath[i] + ".xml", FlashElements.class), Global.manager.get(Turtle.strAtlas, TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Turtle.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(Turtle.run1);
        this.setPosition(-22.0f, -22.0f);
    }
    
    public static void loadElements() {
        for (int i = 0; i < Turtle.strPath.length; ++i) {
            Global.manager.load(Turtle.strRoot + Turtle.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Turtle.strAtlas, TextureAtlas.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Turtle.strPath.length; ++i) {
            Global.manager.unload(Turtle.strRoot + Turtle.strPath[i] + ".xml");
        }
        Global.manager.unload(Turtle.strAtlas);
    }
    
    @Override
    public void act(float n) {
        super.act(n);
        this.flashPlayers[this.curIndex].updateRunTime(n);
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
        this.curTime += n;
        this.checkState();
    }
    
    public void checkState() {
        switch (this.state) {
            case Move: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= Turtle.run2) {
                    this.switchMove();
                    return;
                }
                break;
            }
            case BeAttack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= Turtle.beattack2) {
                    this.switchMove();
                    return;
                }
                break;
            }
            case Dead: {
                if (this.flashPlayers[this.curIndex].isEnd() && !this.flashPlayers[this.curIndex].isStop()) {
                    this.flashPlayers[this.curIndex].stop();
                    return;
                }
                break;
            }
        }
    }
    
    @Override
    public void draw(SpriteBatch spriteBatch, float n) {
        super.draw(spriteBatch, n);
        Color color = spriteBatch.getColor();
        spriteBatch.setColor(((Player)this.getParent()).getColor());
        FlashPlayer flashPlayer = this.flashPlayers[this.curIndex];
        flashPlayer.drawFlashRotation(spriteBatch, flashPlayer.getWidth() / 2.0f, flashPlayer.getHeight() / 2.0f, this.getRotation());
        spriteBatch.setColor(color);
    }
    
    public void switchBeattack() {
        this.state = Constant.TurtleState.BeAttack;
        this.flashPlayers[this.curIndex].setFrameIndex(Turtle.beattack1);
    }
    
    public void switchDead() {
        this.state = Constant.TurtleState.Dead;
        this.flashPlayers[this.curIndex].setFrameIndex(Turtle.die1);
    }
    
    public void switchMove() {
        this.state = Constant.TurtleState.Move;
        this.flashPlayers[this.curIndex].setFrameIndex(Turtle.run1);
    }
}
