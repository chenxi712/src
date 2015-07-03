package com.gleejet.sun.objects;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.stages.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class Flag extends StillEnemy
{
    Array<TextureAtlas.AtlasRegion> arrRegion;
    TextureAtlas atlasGameNew;
    TextureAtlas atlasScene;
    AnimationActor flam1;
    AnimationActor flam2;
    Group group;
    MyImage imgBg;
    MyImage imgBox;
    MyImage imgCover;
    MyImage imgDoor;
    MyImage imgDoorBg;
    MyImage imgFlag2;
    MyImage imgWeapon;
    
    public Flag() {
        this.atlasGameNew = Global.manager.get("ui/ui_game_new.pack", TextureAtlas.class);
        this.atlasScene = Global.manager.get("scene/scene" + Global.sceneLevel + ".pack", TextureAtlas.class);
        (this.group = new Group()).setTransform(false);
        final int sceneLevel = Global.sceneLevel;
        this.imgDoorBg = UiHandle.addItem(this.group, this.atlasScene, StrHandle.get("hou", sceneLevel), 101.0f, 0.0f);
        this.imgDoor = UiHandle.addItem(this.group, this.atlasScene, StrHandle.get("men", sceneLevel), 110.0f, 0.0f);
        UiHandle.addItem(this.group, this.atlasGameNew, "qi-1", 145.0f, 210.0f);
        this.imgFlag2 = UiHandle.addItem(this.group, this.atlasGameNew, "qi-2", 225.0f, 267.0f);
        UiHandle.addItem(this.group, this.atlasScene, StrHandle.get("chengbao", sceneLevel), 0.0f, 0.0f);
        UiHandle.addItem(this.group, this.atlasGameNew, "huopen", 87.0f, 31.0f);
        UiHandle.addItem(this.group, this.atlasGameNew, "huopen", 193.0f, 31.0f);
        this.arrRegion = new Array<TextureAtlas.AtlasRegion>();
        for (int i = 0; i < 7; ++i) {
            this.arrRegion.add(this.atlasGameNew.findRegion(StrHandle.get("huo-", i + 1)));
        }
        this.flam1 = this.addFlame(this.group, 82.0f, 34.0f, 0.125f);
        this.flam2 = this.addFlame(this.group, 188.0f, 34.0f, 0.13333334f);
        this.waveFlag(this.imgFlag2);
        if (Global.isUnlockWeapon()) {
            this.imgBox = UiHandle.addItem(this.group, this.atlasGameNew, "baoxiangguan", 270.0f, 0.0f);
            this.imgCover = UiHandle.addItem(this.group, this.atlasGameNew, "guang", 230.0f, -55.0f);
            this.imgWeapon = UiHandle.addItem(this.group, this.atlasGameNew, Global.getUnlockName(), 230.0f, 0.0f);
            this.imgCover.setVisible(false);
            this.imgWeapon.setVisible(false);
        }
    }
    
    private AnimationActor addFlame(final Group group, final float n, final float n2, final float n3) {
        final AnimationActor animationActor = new AnimationActor(n3, this.arrRegion);
        animationActor.setPosition(n, n2);
        group.addActor(animationActor);
        animationActor.setStart();
        animationActor.setLoop(true);
        return animationActor;
    }
    
    private void waveFlag(final Actor actor) {
        MyMethods.setActorOrigin(actor, 0.0f, 0.5f);
        actor.clearActions();
        actor.setRotation(-3.0f);
        actor.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(3.0f, 0.8f), Actions.rotateTo(-3.0f, 0.8f))));
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.group.act(n);
        this.group.setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
        this.group.draw(spriteBatch, n);
    }
    
    public Group getGroup() {
        return this.group;
    }
    
    public void levelOver() {
        if (Global.isUnlockWeapon()) {
            this.imgBox.setRegion(this.atlasGameNew.findRegion("baoxiangkai"));
            MyMethods.delayRun(this, new Runnable() {
                @Override
                public void run() {
                    Flag.this.showWeapon();
                }
            }, 0.5f);
            return;
        }
        this.levelWin();
    }
    
    public void levelWin() {
        this.imgDoor.addAction(Actions.sequence(Actions.moveTo(this.imgDoor.getX(), this.imgDoor.getHeight(), 0.6f), Actions.delay(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((GameStage)Flag.this.getStage()).levelWin();
            }
        })));
    }
    
    public void setFlagPos(final float n, final float n2) {
        this.setPosition(n, n2);
        this.group.setPosition(n, n2);
    }
    
    public void showWeapon() {
        Global.gameState = Constant.GameState.Game_Unlock;
        this.imgCover.setVisible(true);
        MyMethods.setActorOrigin(this.imgCover, 0.5f, 0.5f);
        this.imgCover.setScale(0.0f);
        this.imgCover.addAction(Actions.sequence(Actions.parallel(Actions.scaleTo(0.95f, 0.95f, 0.5f), Actions.moveTo(235.0f, 110.0f, 0.5f)), Actions.scaleTo(1.0f, 1.0f, 0.1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                Flag.this.imgWeapon.setVisible(true);
                Flag.this.imgWeapon.setColor(1.0f, 1.0f, 1.0f, 0.0f);
                Flag.this.imgWeapon.setPosition(Flag.this.imgCover.getX(), Flag.this.imgCover.getY());
                final RepeatAction repeat = Actions.repeat(2, Actions.sequence(Actions.moveBy(0.0f, 15.0f, 0.6f), Actions.moveBy(0.0f, -15.0f, 0.6f)));
                final RepeatAction repeat2 = Actions.repeat(2, Actions.sequence(Actions.moveBy(0.0f, 15.0f, 0.6f), Actions.moveBy(0.0f, -15.0f, 0.6f)));
                final RepeatAction repeat3 = Actions.repeat(2, Actions.sequence(Actions.moveBy(0.0f, 15.0f, 0.6f), Actions.moveBy(0.0f, -15.0f, 0.6f)));
                final ParallelAction parallel = Actions.parallel(Actions.fadeOut(2.4f), repeat);
                final ParallelAction parallel2 = Actions.parallel(Actions.fadeIn(2.4f), repeat2);
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Flag.this.levelWin();
                    }
                };
                Flag.this.imgCover.addAction(parallel);
                Flag.this.imgWeapon.addAction(Actions.sequence(parallel2, repeat3, Actions.run(runnable)));
                SoundHandle.playForLevelup();
            }
        })));
    }
}
