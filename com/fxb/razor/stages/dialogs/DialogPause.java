package com.fxb.razor.stages.dialogs;

import com.fxb.razor.utils.ui.*;
import com.fxb.razor.utils.action.*;
import com.fxb.razor.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.common.*;

public class DialogPause extends BaseDialog
{
    InputListener btnlistener;
    MyImage imgAgain;
    MyImage imgBack;
    MyImage imgBtnBg1;
    MyImage imgBtnBg2;
    MyImage imgBtnBg3;
    MyImage imgContinue;
    MyImage imgVoice;
    MyImage imgVoiceBg;
    MyImage imgVoiceIndicator;
    MyImage imgVoiceSlider;
    InputListener voicelistener;
    
    public DialogPause() {
        this.voicelistener = new InputListener() {
            float inputX0 = 0.0f;
            Vector2 pos = new Vector2();
            float posX0 = 0.0f;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor == DialogPause.this.imgVoiceSlider) {
                    DialogPause.this.imgVoiceIndicator.setX(listenerActor.getX() + n - DialogPause.this.imgVoiceIndicator.getWidth() / 2.0f);
                }
                else if (listenerActor == DialogPause.this.imgVoiceIndicator) {
                    this.pos.set(n, n2);
                    DialogPause.this.imgVoiceIndicator.localToStageCoordinates(this.pos);
                    this.posX0 = DialogPause.this.imgVoiceIndicator.getX();
                    this.inputX0 = this.pos.x;
                }
                DialogPause.this.setVoicePercent();
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, float x, float inputX0, final int n) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor == DialogPause.this.imgVoiceSlider) {
                    DialogPause.this.setIndicatorX(listenerActor.getX() + x - DialogPause.this.imgVoiceIndicator.getWidth() / 2.0f);
                }
                if (listenerActor == DialogPause.this.imgVoiceIndicator) {
                    this.pos.set(x, inputX0);
                    DialogPause.this.imgVoiceIndicator.localToStageCoordinates(this.pos);
                    x = this.pos.x;
                    inputX0 = this.inputX0;
                    DialogPause.this.setIndicatorX(x - inputX0 + this.posX0);
                }
                DialogPause.this.setVoicePercent();
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
            }
        };
        this.btnlistener = new InputListener() {
            boolean isDown = false;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(TouchAction.downAction());
                return this.isDown = true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float n, final float n2, final int n3) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor.hit(n, n2, true) == null) {
                    this.isDown = false;
                    listenerActor.addAction(TouchAction.upAction());
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                if (this.isDown) {
                    inputEvent.getListenerActor().addAction(TouchAction.upAction());
                    SoundHandle.playForButton2();
                    if (inputEvent.getListenerActor() == DialogPause.this.imgContinue) {
                        DialogPause.this.continueHandle();
                        return;
                    }
                    if (inputEvent.getListenerActor() == DialogPause.this.imgBack) {
                        Constant.NextScreen nextScreen;
                        if (Global.isEndlessMode) {
                            nextScreen = Constant.NextScreen.Start_Screen;
                        }
                        else {
                            nextScreen = Constant.NextScreen.Level_Small;
                        }
                        Global.nextScreen = nextScreen;
                        Global.preScreen = Constant.NextScreen.Game_Screen;
                        DialogPause.this.addFadeOutAction(DialogPause.this, 0.2f);
                        PlatformHandle.closeFeatureView();
//                        if (Global.isEndlessMode) {
//                            FlurryHandle.levelChallengeEndless(Constant.PlayState.Exit, Global.getEndlessCoin());
//                            return;
//                        }
//                        if (Global.gameMode == Constant.GameMode.Easy) {
//                            FlurryHandle.levelChallengeEasy(Global.curGameLevelEasy, Constant.PlayState.Exit);
//                            return;
//                        }
//                        if (Global.gameMode == Constant.GameMode.Hard) {
//                            FlurryHandle.levelChallengeHard(Global.curGameLevelHard, Constant.PlayState.Exit);
//                        }
                    }
                    else if (inputEvent.getListenerActor() == DialogPause.this.imgAgain) {
                        Global.nextScreen = Constant.NextScreen.Game_Screen;
                        Global.preScreen = Constant.NextScreen.Game_Screen;
                        DialogPause.this.addFadeOutAction(DialogPause.this, 0.25f);
                        Global.isGameAgain = true;
                        PlatformHandle.closeFeatureView();
//                        if (Global.isEndlessMode) {
//                            FlurryHandle.levelChallengeEndless(Constant.PlayState.Restart, Global.getEndlessCoin());
//                            return;
//                        }
//                        if (Global.gameMode == Constant.GameMode.Easy) {
//                            FlurryHandle.levelChallengeEasy(Global.curGameLevelEasy, Constant.PlayState.Restart);
//                            return;
//                        }
//                        if (Global.gameMode == Constant.GameMode.Hard) {
//                            FlurryHandle.levelChallengeHard(Global.curGameLevelHard, Constant.PlayState.Restart);
//                        }
                    }
                }
            }
        };
        this.imgBg = this.addItem("waikuang", 0.0f, -1.0f);
        this.addItem("pause", 27.0f, 192.0f);
        this.imgVoiceBg = this.addItem("shengyinkuang", 42.0f, 114.0f);
        this.imgVoice = this.addItem("shengyin", 51.0f, 129.0f);
        (this.imgVoiceSlider = this.addItem("shengyintiao", 108.0f, 137.0f)).addListener(this.voicelistener);
        (this.imgVoiceIndicator = this.addItem("huadong", 135.0f, 137.0f)).addListener(this.voicelistener);
        this.setVoicePosition();
        this.imgBtnBg1 = this.addItem("anniudi", 47.0f, 33.0f);
        this.imgBtnBg2 = this.addItem("anniudi", 151.0f, 33.0f);
        this.imgBtnBg3 = this.addItem("anniudi", 254.0f, 33.0f);
        (this.imgAgain = this.addItem("conglai", 50.0f, 37.0f)).addListener(this.btnlistener);
        (this.imgBack = this.addItem("zhuyemian", 154.0f, 37.0f)).addListener(this.btnlistener);
        (this.imgContinue = this.addItem("jixu", 257.0f, 37.0f)).addListener(this.btnlistener);
        this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
    }
    
    public MyImage addItem(final String s, final float n, final float n2) {
        final MyImage myImage = new MyImage(Assets.atlasPauseOver.findRegion(s));
        myImage.setPosition(n, n2);
        this.addActor(myImage);
        return myImage;
    }
    
    public void continueHandle() {
        Global.gameState = Constant.GameState.Game_On;
        this.addAction(Actions.sequence(Actions.moveTo(this.getX(), 480.0f, 0.1f, Interpolation.bounceOut), Actions.removeActor()));
        this.imgShade.remove();
        PlatformHandle.closeFeatureView();
    }
    
    public MyImage getImgShade() {
        return this.imgShade;
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
