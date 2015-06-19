package com.fxb.razor.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.utils.ui.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.screens.*;

public class DialogSpeSold extends BaseDialog
{
    TextureAtlas atlasStart;
    BaseScreen baseScreen;
    private ButtonListener btnListener;
    MyImage imgMond;
    MyLabel labelBlink;
    MyLabel labelTime1;
    MyLabel labelTime2;
    int lastMinutes;
    MondLackDialog mondLackDialog;
    int mondNum;
    
    public DialogSpeSold(final BaseScreen baseScreen, final MondLackDialog mondLackDialog) {
        this.mondNum = 10;
        this.lastMinutes = 2880;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogSpeSold.this.imgMond) {
                        if (Global.totalMondNum >= 10.0f) {
                            DialogSpeSold.this.buyHandle();
                            return;
                        }
                        DialogHandle.openDialog(DialogSpeSold.this.getStage(), DialogSpeSold.this.mondLackDialog, 0.35f);
                    }
                    else if (listenerActor == DialogSpeSold.this.imgClose) {
                        DialogSpeSold.this.closeHandle();
                    }
                }
            }
        };
        this.baseScreen = baseScreen;
        this.mondLackDialog = mondLackDialog;
        this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, this.atlasStart, "limited", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.imgClose.translate(0.0f, -42.0f);
        this.imgMond = this.addItem(this, this.atlasStart, "mond10", 261.0f, 40.0f, this.btnListener);
        this.labelTime1 = MyLabel.createLabel(this, "47", 160.0f, 12.0f);
        this.labelTime2 = MyLabel.createLabel(this, "59", 196.0f, 12.0f);
        (this.labelBlink = MyLabel.createLabel(this, ":", 188.0f, 14.0f)).addAction(Actions.forever(Actions.sequence(Actions.delay(0.5f), Actions.visible(false), Actions.delay(0.5f), Actions.visible(true))));
        this.setSizeOrigin();
    }
    
    private void buyHandle() {
        Global.totalMondNum -= 10.0f;
        Global.arrMainGunGet.add("Flame");
        if (this.baseScreen instanceof StartScreen) {
            ((StartScreen)this.baseScreen).checkSpeSold();
        }
        DialogHandle.closeDialog(this, 0.35f);
        this.imgClose.setVisible(false);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        final int lastMinutes = (172800 - (int)(System.currentTimeMillis() / 1000L - Global.speStartTime)) / 60;
        if (lastMinutes != this.lastMinutes) {
            this.labelTime1.setText(String.format("%02d", lastMinutes / 60));
            this.labelTime2.setText(String.format("%02d", lastMinutes % 60));
            this.lastMinutes = lastMinutes;
        }
    }
    
    @Override
    public void closeHandle() {
        super.closeHandle();
        Global.isSpeSoldShowing = false;
    }
    
    public void keyBack() {
        if (this.mondLackDialog.getParent() != null) {
            this.mondLackDialog.close();
            return;
        }
        this.closeHandle();
    }
}
