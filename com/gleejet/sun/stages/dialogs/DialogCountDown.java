package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.stages.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;

public class DialogCountDown extends BaseDialog
{
    float curTime;
    MyImage imgTime;
    boolean isClose;
    int regionLevel;
    
    public DialogCountDown() {
        this.isClose = false;
        this.imgBg = UiHandle.addItem(this, Assets.atlasUiGame, "daojishi", 0.0f, 0.0f);
        this.imgTime = UiHandle.addItem(this, Assets.atlasUiGame, "time3", 92.0f, 66.0f);
        this.imgShade.setColor(MyShade.colorLittleTran);
        this.setSizeOrigin();
        this.translate(40.0f, 15.0f);
        this.curTime = 2.99f;
        this.setTimeRegion();
    }
    
    private void closeDialog() {
        this.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.25f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                Global.gameState = Constant.GameState.Game_On;
                DialogCountDown.this.getShade().remove();
                ((GameStage)DialogCountDown.this.getStage()).addInstruction();
                DialogCountDown.this.remove();
            }
        })));
    }
    
    private int getIndex() {
        return (int)(this.curTime + 1.0f);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.curTime -= n;
        if (!this.isClose && this.curTime < 0.25f) {
            this.closeDialog();
            this.isClose = true;
        }
        if (this.getIndex() != this.regionLevel) {
            this.setTimeRegion();
        }
    }
    
    public void setTimeRegion() {
        this.regionLevel = this.getIndex();
        this.imgTime.setRegion(Assets.atlasUiGame.findRegion(StrHandle.get("time", this.regionLevel)));
    }
}
