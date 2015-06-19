package com.fxb.razor.objects.subgun;

import com.fxb.razor.flash.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;

public class Guinsoo extends BaseSubGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    
    static {
        Guinsoo.PlayerNum = 1;
        Guinsoo.strRoot = "subgun/xml/";
        Guinsoo.strPath = new String[] { "guinsoo_total_1" };
        Guinsoo.strEffect = "subgun/pack/guinsoo.pack";
    }
    
    public Guinsoo() {
        this.flashPlayers = new FlashPlayer[Guinsoo.PlayerNum];
        this.scale = 0.35f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Guinsoo.strRoot + Guinsoo.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Guinsoo.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setFrame(60, 99, 0, 59);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
        this.effectFrame = 8;
        this.setPosition(82.0f, 67.0f);
        (this.actorEffect = new AnimationActor(0.05f, Global.manager.get(Guinsoo.strEffect, TextureAtlas.class).getRegions())).setOrigin(113.0f, 87.0f);
        this.actorEffect.setScale(1.0f);
        this.actorEffect.setRotation(-15.0f);
        this.actorEffect.setPosition(107.0f - this.actorEffect.getOriginX(), 105.0f - this.actorEffect.getOriginY());
        this.subGunIcon.setIcon(Assets.atlasUiGame.findRegion("bianyang"));
    }
    
    public static void loadElements() {
        for (int i = 0; i < Guinsoo.strPath.length; ++i) {
            Global.manager.load(Guinsoo.strRoot + Guinsoo.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Guinsoo.strEffect, TextureAtlas.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Guinsoo.strPath.length; ++i) {
            Global.manager.unload(Guinsoo.strRoot + Guinsoo.strPath[i] + ".xml");
        }
        Global.manager.unload(Guinsoo.strEffect);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
    }
    
    @Override
    protected void setEnhanceLevel() {
        super.setEnhanceLevel();
        PreferHandle.readWeaponEnhance("Guisoon");
    }
}
