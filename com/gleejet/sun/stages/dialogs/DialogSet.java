package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;

public class DialogSet extends BaseDialog
{
    TextureAtlas atlasLevelSelect;
    private ButtonListener btnListener;
    DialogCredits dialogCredits;
    MyImage imgCredits;
    MyImage imgOption;
    MyImage imgVoice;
    MyImage imgVoiceBg;
    MyImage imgVoiceIndicator;
    MyImage imgVoiceSlider;
    InputListener voicelistener;
    
    public DialogSet() {
        this.voicelistener = new InputListener() {
            float inputX0 = 0.0f;
            Vector2 pos = new Vector2();
            float posX0 = 0.0f;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor == DialogSet.this.imgVoiceSlider) {
                    DialogSet.this.imgVoiceIndicator.setX(listenerActor.getX() + n - DialogSet.this.imgVoiceIndicator.getWidth() / 2.0f);
                }
                else if (listenerActor == DialogSet.this.imgVoiceIndicator) {
                    this.pos.set(n, n2);
                    DialogSet.this.imgVoiceIndicator.localToStageCoordinates(this.pos);
                    this.posX0 = DialogSet.this.imgVoiceIndicator.getX();
                    this.inputX0 = this.pos.x;
                }
                DialogSet.this.setVoicePercent();
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, float x, float inputX0, final int n) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor == DialogSet.this.imgVoiceSlider) {
                    DialogSet.this.setIndicatorX(listenerActor.getX() + x - DialogSet.this.imgVoiceIndicator.getWidth() / 2.0f);
                }
                if (listenerActor == DialogSet.this.imgVoiceIndicator) {
                    this.pos.set(x, inputX0);
                    DialogSet.this.imgVoiceIndicator.localToStageCoordinates(this.pos);
                    x = this.pos.x;
                    inputX0 = this.inputX0;
                    DialogSet.this.setIndicatorX(x - inputX0 + this.posX0);
                }
                DialogSet.this.setVoicePercent();
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
            }
        };
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogSet.this.imgCredits) {
                        DialogHandle.openDialog(DialogSet.this.getStage(), DialogSet.this.dialogCredits);
                        return;
                    }
                    if (listenerActor == DialogSet.this.imgClose) {
                        DialogHandle.closeDialog(DialogSet.this, 0.35f);
                    }
                }
            }
        };
        this.atlasLevelSelect = Global.manager.get("ui/ui_level_select.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, Assets.atlasPauseOver, "waikuang", 0.0f, -1.0f);
        this.initClose(this.btnListener);
        this.imgVoiceBg = this.addItem(this, Assets.atlasPauseOver, "shengyinkuang", 42.0f, 114.0f);
        this.imgVoice = this.addItem(this, Assets.atlasPauseOver, "shengyin", 51.0f, 129.0f);
        this.imgVoiceSlider = this.addItem(this, Assets.atlasPauseOver, "shengyintiao", 108.0f, 137.0f, this.voicelistener);
        this.imgVoiceIndicator = this.addItem(this, Assets.atlasPauseOver, "huadong", 135.0f, 137.0f, this.voicelistener);
        this.addItem(this, this.atlasLevelSelect, "option", 26.0f, 196.0f);
        this.imgCredits = this.addItem(this, this.atlasLevelSelect, "credits", 135.0f, 42.0f, this.btnListener);
        this.dialogCredits = new DialogCredits();
        this.setSizeOrigin();
        this.setVoicePosition();
    }
    
    public void keyBack() {
        if (this.dialogCredits.getParent() != null) {
            this.dialogCredits.closeHandle();
            return;
        }
        DialogHandle.closeDialog(this, 0.35f);
    }
    
    public void setIndicatorX(final float n) {
        float x;
        if (n < this.imgVoiceSlider.getX()) {
            x = this.imgVoiceSlider.getX();
        }
        else {
            x = n;
            if (n > this.imgVoiceSlider.getRight() - this.imgVoiceIndicator.getWidth()) {
                x = this.imgVoiceSlider.getRight() - this.imgVoiceIndicator.getWidth();
            }
        }
        this.imgVoiceIndicator.setX(x);
    }
    
    public void setVoicePercent() {
        Global.soundVolume = (this.imgVoiceIndicator.getX() - this.imgVoiceSlider.getX()) / (this.imgVoiceSlider.getWidth() - this.imgVoiceIndicator.getWidth());
        MusicHandle.setVolume();
    }
    
    public void setVoicePosition() {
        this.imgVoiceIndicator.setX(this.imgVoiceSlider.getX() + (this.imgVoiceSlider.getWidth() - this.imgVoiceIndicator.getWidth()) * Global.soundVolume);
    }
}
