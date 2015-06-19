package com.fxb.razor.stages.dialogs;

import com.fxb.razor.stages.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.fxb.razor.utils.action.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.screens.*;
import com.badlogic.gdx.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class DialogRevive extends BaseDialog
{
    private ButtonListener btnListener;
    float curTime;
    DialogStore dialogStore;
    GameStage gameStage;
    GroupBuy groupBuy;
    MyImage imgTime;
    int intTime;
    boolean isTimeRun;
    Label labelInfo;
    int mondNum;
    
    public DialogRevive(final GameStage gameStage) {
        this.isTimeRun = true;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogRevive.this.groupBuy) {
                        DialogRevive.this.buyHandle();
                        return;
                    }
                    if (listenerActor == DialogRevive.this.imgClose) {
                        DialogRevive.this.closeDialog();
                    }
                }
            }
        };
        this.gameStage = gameStage;
        this.imgBg = UiHandle.addItem(this, Assets.atlasInstruction, "kuang", 0.0f, 0.0f);
        this.imgClose = UiHandle.addItem(this, Assets.atlasPauseOver, "guanbi", this.imgBg.getWidth() - 43.0f, this.imgBg.getHeight() - 43.0f - 25.0f, this.btnListener);
        this.labelInfo = UiHandle.createRomanLabel(this, "Revive all heros?", 135.0f, 100.0f);
        this.imgTime = UiHandle.addItem(this, Assets.atlasInstruction, "number5", 144.0f, 48.0f);
        MyMethods.setActorOrigin(this.groupBuy = new GroupBuy(this, 215.0f, 36.0f), 0.5f, 0.5f);
        this.groupBuy.addAction(BlinkAction.scaleUpDown(0.97f, 1.05f, 0.6f));
        this.dialogStore = new DialogStore(this);
        this.setSizeOrigin();
    }
    
    private void buyHandle() {
        if (Global.totalMondNum >= this.mondNum) {
            this.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.25f, Interpolation.swingIn), Actions.run(new Runnable() {
                @Override
                public void run() {
                    if (DialogRevive.this.gameStage == null) {
                        Global.gameState = Constant.GameState.Game_On;
                        Global.nextScreen = Constant.NextScreen.Level_Small;
                        Global.preScreen = Constant.NextScreen.Game_Screen;
                        Global.game.setNextScreen(new LoadingScreen(Global.game));
                        return;
                    }
                    Global.totalMondNum -= DialogRevive.this.mondNum;
                    DialogRevive.this.gameStage.revive();
                    PreferHandle.writeCommon();
                    DialogRevive.this.getShade().remove();
                    DialogRevive.this.remove();
                }
            })));
            return;
        }
        this.dialogStore.openDialog(this.getStage());
        this.isTimeRun = false;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.dialogStore != null && this.dialogStore.getParent() != null) {
            this.isTimeRun = false;
        }
        else if (Global.totalMondNum < this.mondNum) {
            this.isTimeRun = true;
        }
        if (this.isTimeRun) {
            this.curTime -= n;
            if ((int)this.curTime != this.intTime) {
                this.intTime = (int)this.curTime;
                if (this.intTime != 0) {
                    this.imgTime.setRegion(Assets.atlasInstruction.findRegion(StrHandle.get("number", this.intTime)));
                    return;
                }
                this.closeDialog();
            }
        }
    }
    
    public void checkMondEnough() {
        this.groupBuy.setMondNum(this.mondNum);
    }
    
    public void closeDialog() {
        this.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.25f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                if (DialogRevive.this.gameStage == null) {
                    Global.gameState = Constant.GameState.Game_On;
                    Global.nextScreen = Constant.NextScreen.Level_Small;
                    Global.preScreen = Constant.NextScreen.Game_Screen;
                    Global.game.setNextScreen(new LoadingScreen(Global.game));
                    return;
                }
                Global.gameState = Constant.GameState.Game_On;
                DialogRevive.this.gameStage.levelLose();
                DialogRevive.this.getShade().remove();
                DialogRevive.this.remove();
                DialogRevive.this.isTimeRun = true;
            }
        })));
    }
    
    public DialogStore getDialogStore() {
        return this.dialogStore;
    }
    
    public void keyBack() {
        if (this.dialogStore != null && this.dialogStore.getParent() != null) {
            this.dialogStore.keyBack();
            return;
        }
        this.closeDialog();
    }
    
    public void setMondNum(final int mondNum) {
        this.mondNum = mondNum;
        this.labelInfo.setText("Revive all heros?");
        this.groupBuy.setMondNum(this.mondNum);
        this.curTime = 5.9f;
        this.isTimeRun = true;
    }
    
    private class GroupBuy extends MyGroup
    {
        MyImage imgBg;
        MyLabel labelMond;
        
        public GroupBuy(final Group group, final float n, final float n2) {
            this.imgBg = UiHandle.addItem(this, Assets.atlasInstruction, "mond_bg", 0.0f, 0.0f);
            this.labelMond = MyLabel.createLabel(this, "2", 40.0f, 8.0f);
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
            this.setPosition(n, n2);
            this.addListener(DialogRevive.this.btnListener);
            group.addActor(this);
        }
        
        public void setMondNum(final int n) {
            this.labelMond.setText(StrHandle.get(n));
        }
    }
}
