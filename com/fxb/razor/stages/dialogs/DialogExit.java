package com.fxb.razor.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.utils.ui.*;
import com.fxb.razor.screens.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.math.*;
import com.fxb.razor.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class DialogExit extends BaseDialog
{
    TextureAtlas atlasStart;
    TextureAtlas atlasStore;
    private ButtonListener btnListener;
    MyImage imgMore;
    MyImage imgNo;
    MyImage imgTitle;
    MyImage imgYes;
    boolean isCloseDialog;
    boolean isShowAd;
    StartScreen startScreen;
    
    public DialogExit(final StartScreen startScreen) {
        this.isShowAd = true;
        this.isCloseDialog = false;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogExit.this.imgYes) {
                        DialogExit.this.yesHandle();
                        return;
                    }
                    if (listenerActor == DialogExit.this.imgNo) {
                        DialogExit.this.noHandle();
                        return;
                    }
                    if (listenerActor == DialogExit.this.imgMore) {
                        DialogExit.this.moreHandle();
                    }
                }
            }
        };
        this.startScreen = startScreen;
        this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        this.imgNo = this.addItem(this, this.atlasStart, "no", 6.0f, 0.0f, this.btnListener);
        this.imgMore = this.addItem(this, this.atlasStart, "more_game", 167.0f, 0.0f, this.btnListener);
        this.imgYes = this.addItem(this, this.atlasStart, "yes", 326.0f, 0.0f, this.btnListener);
        this.imgTitle = this.addItem(this, this.atlasStart, "exit", 101.0f, 368.0f);
        this.setShow();
    }
    
    private void closeDialog(final float n) {
        this.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, n, Interpolation.linear), Actions.run(new Runnable() {
            @Override
            public void run() {
                DialogExit.this.getShade().remove();
                DialogExit.this.remove();
            }
        })));
        this.isCloseDialog = true;
    }
    
    private void moreHandle() {
        this.imgShade.remove();
        this.remove();
        PlatformHandle.closeAd();
        this.startScreen.moreHandle();
    }
    
    private void setShow() {
        this.setTitlePos(this.isShowAd);
        this.setSize(this.imgYes.getRight(), this.imgTitle.getTop());
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setPosition((800.0f - this.getWidth()) / 2.0f, (480.0f - this.getHeight()) / 2.0f - 15.0f);
    }
    
    private void setTitlePos(final boolean b) {
        final MyImage imgTitle = this.imgTitle;
        float n;
        if (b) {
            n = 368.0f;
        }
        else {
            n = 69.0f;
        }
        imgTitle.setPosition(101.0f, n);
    }
    
    private void yesHandle() {
        Gdx.app.exit();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (!this.isCloseDialog) {
            if (this.isShowAd && !PlatformHandle.isShowingAd()) {
                this.setTitlePos(this.isShowAd = false);
                this.setShow();
            }
            if (!this.isShowAd && PlatformHandle.isShowingAd()) {
                this.isShowAd = true;
                this.setShow();
            }
        }
    }
    
    public void noHandle() {
        this.closeDialog(0.25f);
        PlatformHandle.closeAd();
    }
    
    public void openDialog(final Stage stage, final float n) {
        stage.addActor(this.getShade());
        final ScaleToAction scaleTo = Actions.scaleTo(1.0f, 1.0f, n, Interpolation.linear);
        this.setScale(0.0f);
        this.addAction(scaleTo);
        stage.addActor(this);
        this.isShowAd = true;
        this.isCloseDialog = false;
        this.setShow();
    }
}
