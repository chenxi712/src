package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;

public class DialogRate extends BaseDialog
{
    TextureAtlas atlasStart;
    private ButtonListener btnListener;
    MyImage imgRate;
    
    public DialogRate() {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogRate.this.imgRate) {
                        DialogRate.this.rateHandle();
                        return;
                    }
                    if (listenerActor == DialogRate.this.imgClose) {
                        DialogRate.this.closeHandle();
                    }
                }
            }
        };
        this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, Assets.atlasPauseOver, "waikuang", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.addItem(this, this.atlasStart, "wenzi", 39.0f, 88.0f);
        this.addItem(this, this.atlasStart, "rateus", 27.0f, 195.0f);
        (this.imgRate = this.addItem(this, this.atlasStart, "btn_rate", 45.0f, 30.0f, this.btnListener)).setX((this.imgBg.getWidth() - this.imgRate.getWidth()) / 2.0f);
        this.setSizeOrigin();
    }
    
    private void rateHandle() {
        Global.autoRateCount = 3;
        PreferHandle.writeCommon();
//        FlurryHandle.rate(Global.maxGameLevelEasy);
        PlatformHandle.goToRate();
    }
}
