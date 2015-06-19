package com.fxb.razor.stages.dialogs;

import com.fxb.razor.utils.*;
import com.fxb.razor.utils.ui.*;
import com.fxb.razor.utils.action.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class DialogOver extends BaseDialog
{
    InputListener btnlistener;
    float curTime;
    MyImage imgBg;
    MyImage imgBg2;
    MyImage imgBtnBg;
    MyImage imgContinue;
    MyImage imgGrade;
    MyImage imgLose;
    MyImage[] imgStars;
    MyImage imgTran;
    MyImage imgWin;
    MyLabel labelCoin;
    MyLabel labelKill;
    float totalTime;
    
    public DialogOver() {
        this.curTime = 0.0f;
        this.totalTime = 2.0f;
        this.btnlistener = new InputListener() {
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                Constant.NextScreen nextScreen;
                if (Global.isEndlessMode) {
                    nextScreen = Constant.NextScreen.Start_Screen;
                }
                else {
                    nextScreen = Constant.NextScreen.Level_Small;
                }
                Global.nextScreen = nextScreen;
                Global.preScreen = Constant.NextScreen.Game_Screen;
                DialogOver.this.addFadeOutAction(DialogOver.this, 0.2f);
                return super.touchDown(inputEvent, n, n2, n3, n4);
            }
        };
        this.imgBg = this.addItem("waikuang", 0.0f, 26.0f);
        this.addItem("shiban", 45.0f, 0.0f);
        this.addItem("tongjidikuang", 94.0f, 165.0f);
        this.addItem("tongjidikuang", 94.0f, 93.0f);
        this.imgWin = this.addItem("victory", 26.0f, 219.0f);
        this.imgLose = this.addItem("failure", 26.0f, 219.0f);
        this.addItem("jinbi", 125.0f, 176.0f);
        this.addItem("tou", 127.0f, 105.0f);
        this.labelCoin = MyLabel.createLabel(this, StrHandle.get(Global.inCoinGet), 205.0f, 183.0f);
        this.labelKill = MyLabel.createLabel(this, StrHandle.get(Global.enemyKill), 205.0f, 111.0f);
        final float[] array2;
        final float[] array = array2 = new float[6];
        array2[0] = 100.0f;
        array2[1] = 27.0f;
        array2[2] = 165.0f;
        array2[3] = 31.0f;
        array2[4] = 228.0f;
        array2[5] = 28.0f;
        this.imgStars = new MyImage[3];
        for (int i = 0; i < 3; ++i) {
            MyMethods.setActorOrigin(this.imgStars[i] = this.addItem("xingxing", array[i * 2], array[i * 2 + 1]), 0.5f, 0.5f);
            this.imgStars[i].setVisible(false);
        }
        (this.imgShade = new MyShade()).setColor(MyShade.colorHalfTran);
        (this.imgContinue = UiHandle.addItem(this, Assets.atlasPauseOver, "tap", 78.0f, -35.0f)).addAction(BlinkAction.blink(1.2f));
        this.imgContinue.setVisible(false);
        (this.imgTran = new MyShade()).setColor(MyShade.colorTran);
        this.imgTran.setPosition(-250.0f, -110.0f);
        this.addActor(this.imgTran);
        this.setSize(387.0f, 296.0f);
        MyMethods.setActorOrigin(this, 0.5f, 0.5f);
    }
    
    private MyImage addItem(final String s, final float n, final float n2) {
        final MyImage myImage = new MyImage(Assets.atlasPauseOver.findRegion(s));
        myImage.setPosition(n, n2);
        this.addActor(myImage);
        return myImage;
    }
    
    private void addStarAction(final Actor actor, final float n, final float n2, final float n3, final float n4, final float n5) {
        actor.setScale(7.0f);
        actor.setPosition(n, n2);
        actor.setVisible(true);
        actor.addAction(Actions.sequence(Actions.parallel(Actions.scaleTo(1.0f, 1.0f, n5), Actions.moveTo(n3, n4, n5)), Actions.run(new Runnable() {
            @Override
            public void run() {
                SoundHandle.playForStar();
            }
        })));
    }
    
    private void setCoinKill() {
        this.labelCoin.setText(StrHandle.get(Global.inCoinGet));
        this.labelKill.setText(StrHandle.get(Global.enemyKill));
    }
    
    private void shakeDialog() {
        this.setRotation(-0.9f);
        this.addAction(Actions.sequence(Actions.delay(0.35f), Actions.rotateTo(0.9f, 0.025f), Actions.rotateTo(-0.9f, 0.025f), Actions.rotateTo(0.9f, 0.025f), Actions.rotateTo(-0.9f, 0.025f), Actions.rotateTo(0.0f, 0.025f / 2.0f)));
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.curTime += n;
        if (this.curTime <= this.totalTime) {
            this.labelCoin.setText(StrHandle.get((int)(Global.inCoinGet * this.curTime / this.totalTime)));
            this.labelKill.setText(StrHandle.get((int)(Global.enemyKill * this.curTime / this.totalTime)));
            return;
        }
        this.setCoinKill();
    }
    
    public MyImage getImgShade() {
        return this.imgShade;
    }
    
    public void setFail() {
        this.setCoinKill();
        this.imgWin.setVisible(false);
        this.imgWin.setVisible(true);
        this.setStar(0);
    }
    
    public void setStar(final int n) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DialogOver.this.addStarAction(DialogOver.this.imgStars[0], -180.0f, 37.0f, 100.0f, 27.0f, 0.35f);
                DialogOver.this.shakeDialog();
            }
        };
        final Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                DialogOver.this.addStarAction(DialogOver.this.imgStars[1], 164.0f, 21.0f, 164.0f, 31.0f, 0.35f);
                DialogOver.this.shakeDialog();
            }
        };
        final Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                DialogOver.this.addStarAction(DialogOver.this.imgStars[2], 508.0f, 37.0f, 228.0f, 28.0f, 0.35f);
                DialogOver.this.shakeDialog();
            }
        };
        final Runnable runnable4 = new Runnable() {
            @Override
            public void run() {
                DialogOver.this.imgContinue.setVisible(true);
                DialogOver.this.imgTran.addListener(DialogOver.this.btnlistener);
            }
        };
        if (n == 0) {
            MyMethods.sequenceAction(this, Actions.delay(0.5f + 0.7f), Actions.run(runnable4));
        }
        else {
            if (n == 1) {
                MyMethods.sequenceAction(this, Actions.delay(0.7f), Actions.run(runnable), Actions.run(runnable4));
                return;
            }
            if (n == 2) {
                MyMethods.sequenceAction(this, Actions.delay(0.7f), Actions.run(runnable), Actions.delay(0.7f), Actions.run(runnable2), Actions.run(runnable4));
                return;
            }
            if (n == 3) {
                MyMethods.sequenceAction(this, Actions.delay(0.7f), Actions.run(runnable), Actions.delay(0.7f), Actions.run(runnable2), Actions.delay(0.7f), Actions.run(runnable3), Actions.run(runnable4));
            }
        }
    }
    
    public void setWin() {
        this.setCoinKill();
        this.imgLose.setVisible(false);
        this.imgWin.setVisible(true);
    }
}
