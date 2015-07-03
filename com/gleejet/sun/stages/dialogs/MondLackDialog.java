package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.screens.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;

public class MondLackDialog extends BaseDialog
{
    TextureAtlas atlasStore;
    BaseScreen baseScreen;
    private ButtonListener btnListener;
    DialogStore dialogStore;
    MyImage imgYes;
    boolean isSwitch;
    Label labelInfo;
    
    public MondLackDialog(final BaseScreen baseScreen) {
        this.isSwitch = false;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == MondLackDialog.this.imgYes) {
                        MondLackDialog.this.isSwitch = true;
                        MondLackDialog.this.closeDialog(MondLackDialog.this);
                        if (MondLackDialog.this.dialogStore != null) {
                            MondLackDialog.this.dialogStore.closeBuyWeaponDialog();
                        }
                    }
                    else if (listenerActor == MondLackDialog.this.imgClose) {
                        MondLackDialog.this.close();
                    }
                }
            }
        };
        this.baseScreen = baseScreen;
        this.init();
    }
    
    public MondLackDialog(final DialogStore dialogStore) {
        this.isSwitch = false;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == MondLackDialog.this.imgYes) {
                        MondLackDialog.this.isSwitch = true;
                        MondLackDialog.this.closeDialog(MondLackDialog.this);
                        if (MondLackDialog.this.dialogStore != null) {
                            MondLackDialog.this.dialogStore.closeBuyWeaponDialog();
                        }
                    }
                    else if (listenerActor == MondLackDialog.this.imgClose) {
                        MondLackDialog.this.close();
                    }
                }
            }
        };
        this.dialogStore = dialogStore;
        this.init();
    }
    
    private void closeDialog(final BaseDialog baseDialog) {
        baseDialog.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.25f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                baseDialog.getShade().remove();
                baseDialog.remove();
                MondLackDialog.this.switchItem();
            }
        })));
    }
    
    private void init() {
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, Assets.atlasPauseOver, "anniukuang2", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.imgYes = this.addItem(this, this.atlasStore, "yes", 90.0f, 15.0f, this.btnListener);
//        this.labelInfo = UiHandle.createRomanLabel(this, "Diamond is not enough. Would \nyou like to buy more?", 30.0f, 85.0f);
        this.labelInfo = UiHandle.createRomanLabel(this, Language.mondIsNotEnough(), 30.0f, 85.0f);
        this.imgShade.setColor(MyShade.colorLackTran);
        this.setSizeOrigin();
    }
    
    private void switchItem() {
        if (this.isSwitch) {
            if (this.baseScreen instanceof StartScreen) {
                ((StartScreen)this.baseScreen).openStore(1);
            }
            else if (this.dialogStore != null) {
                this.dialogStore.selectItem(1);
            }
        }
    }
    
    public void close() {
        this.isSwitch = false;
        this.closeDialog(this);
    }
}
