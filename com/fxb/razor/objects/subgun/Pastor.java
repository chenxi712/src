package com.fxb.razor.objects.subgun;

import com.fxb.razor.flash.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.roles.*;
import com.fxb.razor.common.*;

public class Pastor extends BaseSubGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private float addDural;
    private float addPercent;
    private float addTime;
    private MyImage imgEffect;
    
    static {
        Pastor.PlayerNum = 1;
        Pastor.strRoot = "subgun/xml/";
        Pastor.strPath = new String[] { "pastor_total_1" };
        Pastor.strEffect = "subgun/pack/pastor.pack";
    }
    
    public Pastor() {
        this.addPercent = 0.2f;
        this.addDural = 0.5f;
        this.addTime = 0.0f;
        this.flashPlayers = new FlashPlayer[Pastor.PlayerNum];
        this.scale = 0.4f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Pastor.strRoot + Pastor.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true, true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Pastor.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setFrame(0, 39, 40, 79);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
        this.setPosition(82.0f, 67.0f);
        final TextureAtlas textureAtlas = Global.manager.get(Pastor.strEffect, TextureAtlas.class);
        (this.actorEffect = new AnimationActor(0.083333336f, textureAtlas.findRegions("pastor1"))).setOrigin(90.0f, 35.0f);
        this.actorEffect.setScale(1.0f);
        this.actorEffect.setPosition(120.0f - this.actorEffect.getOriginX(), 95.0f - this.actorEffect.getOriginY());
        (this.actorEffect2 = new AnimationActor(0.05f, textureAtlas.findRegions("pastor2"))).setOrigin(88.0f, 0.0f);
        this.actorEffect2.setScale(1.0f);
        this.actorEffect2.setPosition(120.0f - this.actorEffect2.getOriginX(), 75.0f - this.actorEffect2.getOriginY());
        this.actorEffect2.setLoop(true);
        this.subGunIcon.setIcon(Assets.atlasUiGame.findRegion("jiaxue"));
        this.attackInterval = 10.0f;
        this.setEnhanceLevel();
        this.initImgEffect();
    }
    
    private void initImgEffect() {
        (this.imgEffect = new MyImage(Assets.atlasArcher.findRegion("pastor_1_jiaoxuetuteng"))).setPosition(160.0f, 295.0f);
        this.imgEffect.setOrigin(this.imgEffect.getWidth() / 2.0f, this.imgEffect.getHeight() / 2.0f);
        this.imgEffect.setName("show_pastor");
    }
    
    public static void loadElements() {
        for (int i = 0; i < Pastor.strPath.length; ++i) {
            Global.manager.load(Pastor.strRoot + Pastor.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Pastor.strEffect, TextureAtlas.class);
    }
    
    private void showEffect() {
        this.imgEffect.clearActions();
        this.imgEffect.setScale(0.1f);
        this.imgEffect.addAction(Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 0.5f, Interpolation.swingOut), Actions.forever(Actions.sequence(Actions.scaleTo(0.7f, 0.7f, 0.5f), Actions.scaleTo(1.0f, 1.0f, 0.5f)))));
        Global.groupEffectPlayer.addActor(this.imgEffect);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Pastor.strPath.length; ++i) {
            Global.manager.unload(Pastor.strRoot + Pastor.strPath[i] + ".xml");
        }
        Global.manager.unload(Pastor.strEffect);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        if (this.addTime > 0.0f) {
            ((Player)this.getParent()).addHpPercent(this.addPercent * n / this.addDural);
            this.addTime -= n;
            if (this.addTime <= 0.0f) {
                final Actor actor = Global.groupEffectPlayer.findActor("show_pastor");
                if (actor != null) {
                    actor.remove();
                }
            }
        }
    }
    
    @Override
    public void attack() {
        super.attack();
        this.setAddDural();
        this.showEffect();
    }
    
    @Override
    public void checkState() {
        super.checkState();
    }
    
    public void setAddDural() {
        this.addTime = this.addDural;
    }
    
    @Override
    protected void setEnhanceLevel() {
        super.setEnhanceLevel();
        final int weaponEnhance = PreferHandle.readWeaponEnhance("Pastor");
        float n = 0.0f;
        if (weaponEnhance >= 1) {
            n = 0.0f + 0.1f;
        }
        float n2 = n;
        if (weaponEnhance >= 2) {
            n2 = n + 0.2f;
        }
        float n3 = n2;
        if (weaponEnhance >= 3) {
            n3 = n2 + 0.3f;
        }
        this.addPercent *= 1.0f + n3;
    }
}
