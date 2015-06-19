package com.fxb.razor.stages.dialogs;

import com.fxb.razor.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.utils.ui.*;

import java.util.*;

import com.fxb.razor.common.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.*;

public class DialogBonus extends BaseDialog
{
    TextureAtlas atlasStart;
    private ButtonListener btnListener;
    MyImage[] imgReceives;
    
    public DialogBonus() {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int i, final int n3) {
                super.touchUp(inputEvent, n, n2, i, n3);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    i = 0;
                    while (i < DialogBonus.this.imgReceives.length) {
                        if (listenerActor == DialogBonus.this.imgReceives[i]) {
                            if (i == DialogBonus.this.getBonus() - 1) {
                                DialogBonus.this.getReward(DialogBonus.this.getBonus());
                                DialogBonus.this.setReceiveLevel(DialogBonus.this.getBonus() - 1, true);
                                DialogBonus.this.imgClose.setVisible(true);
                            }
                            return;
                        }
                        else {
                            ++i;
                        }
                    }
                    if (listenerActor == DialogBonus.this.imgClose) {
                        DialogBonus.this.backHandle();
                    }
                }
            }
        };
        this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, this.atlasStart, "waikuang", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.addItem(this, this.atlasStart, "daily", 163.0f, 233.0f);
        final TextureAtlas.AtlasRegion region = this.atlasStart.findRegion("di1");
        final TextureAtlas.AtlasRegion region2 = this.atlasStart.findRegion("di2");
        final TextureRegion textureRegion = new TextureRegion(region);
        textureRegion.flip(true, false);
        this.addItem(this, this.atlasStart, "di1", 36.0f, 37.0f);
        for (float n = 55.0f; n < 611.0f; n += region2.getRegionWidth()) {
            this.addItem(this, this.atlasStart, "di2", n, 37.0f);
        }
        final MyImage myImage = new MyImage(textureRegion);
        myImage.setPosition(611.0f, 38.0f);
        this.addActor(myImage);
        for (int i = 0; i < 5; ++i) {
            this.addItem(this, this.atlasStart, "" + (i + 1), i * 116 + 46, 82.0f);
        }
        this.imgReceives = new MyImage[5];
        for (int j = 0; j < this.imgReceives.length; ++j) {
            (this.imgReceives[j] = this.addItem(this, this.atlasStart, "receive_off", j * 116 + 54, 47.0f)).addListener(this.btnListener);
        }
        (this.imgShade = new MyShade()).setColor(MyShade.colorHalfTran);
        this.imgClose.setVisible(false);
        this.setPosition(61.0f, 80.0f);
        this.setSizeOrigin();
    }
    
    private int getBonus() {
        return (Global.continualDays - 1) % 5 + 1;
    }
    
    private void getReward(final int n) {
        Global.totalCoinNum += (new int[] { 1000, 0, 2000, 3000, 0 })[n - 1];
        Global.totalMondNum += (new int[] { 0, 5, 0, 0, 10 })[n - 1];
        this.setBonus();
    }
    
    private void setBonus() {
        final long serverTime = PlatformHandle.getServerTime();
        final long n = (serverTime - Global.lastLoginRewardTime) / 1000L;
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(serverTime);
        instance.set(11, 23);
        instance.set(12, 59);
        instance.set(13, 59);
        Global.lastLoginRewardTime = instance.getTimeInMillis();
        PreferHandle.writeCommon();
    }
    
    private void setReceiveLevel(final int n, final boolean b) {
        for (int i = 0; i < this.imgReceives.length; ++i) {
            if (i < n) {
                this.imgReceives[i].setRegion(this.atlasStart.findRegion("lingquwanbi"));
                this.imgReceives[i].setTouchable(Touchable.disabled);
            }
            else if (i == n) {
                if (b) {
                    this.imgReceives[i].setRegion(this.atlasStart.findRegion("lingquwanbi"));
                    this.imgReceives[i].setTouchable(Touchable.disabled);
                }
                else {
                    this.imgReceives[i].setRegion(this.atlasStart.findRegion("receive_on"));
                    this.imgReceives[i].setTouchable(Touchable.enabled);
                }
            }
            else {
                this.imgReceives[i].setRegion(this.atlasStart.findRegion("receive_off"));
                this.imgReceives[i].setTouchable(Touchable.disabled);
            }
        }
    }
    
    public void backHandle() {
        if (!this.imgClose.isVisible()) {
            return;
        }
        this.addAction(Actions.sequence(Actions.moveTo(this.getX(), 480.0f, 0.1f, Interpolation.bounceOut), Actions.run(new Runnable() {
            @Override
            public void run() {
                DialogBonus.this.remove();
            }
        })));
        this.imgShade.remove();
    }
    
    public void checkDailyBonus() {
        final long n = (PlatformHandle.getServerTime() - Global.lastLoginRewardTime) / 1000L;
        if (n > 0L) {
            if (n < 86400L) {
            	++Global.continualDays;
//                FlurryHandle.dailyBonusContinous(++Global.continualDays);
                if (Global.continualDays >= 6) {
                    Global.continualDays = 1;
                }
            }
            else {
            	Global.continualDays = 1;
//                if (Global.continualDays > 0) {
//                    FlurryHandle.dailyBonusLost(Global.continualDays);
//                }
//                FlurryHandle.dailyBonusContinous(Global.continualDays = 1);
            }
            this.setReceiveLevel(this.getBonus() - 1, false);
            return;
        }
        this.setReceiveLevel(this.getBonus() - 1, true);
        this.imgClose.setVisible(true);
    }
}
